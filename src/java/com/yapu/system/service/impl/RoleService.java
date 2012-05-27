package com.yapu.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.yapu.system.dao.itf.SysAccountDAO;
import com.yapu.system.dao.itf.SysAccountRoleDAO;
import com.yapu.system.dao.itf.SysFunctionDAO;
import com.yapu.system.dao.itf.SysOrgDAO;
import com.yapu.system.dao.itf.SysOrgRoleDAO;
import com.yapu.system.dao.itf.SysRoleDAO;
import com.yapu.system.dao.itf.SysRoleFunctionDAO;
import com.yapu.system.entity.SysAccount;
import com.yapu.system.entity.SysAccountExample;
import com.yapu.system.entity.SysAccountRole;
import com.yapu.system.entity.SysAccountRoleExample;
import com.yapu.system.entity.SysFunction;
import com.yapu.system.entity.SysFunctionExample;
import com.yapu.system.entity.SysOrg;
import com.yapu.system.entity.SysOrgExample;
import com.yapu.system.entity.SysOrgRole;
import com.yapu.system.entity.SysOrgRoleExample;
import com.yapu.system.entity.SysRole;
import com.yapu.system.entity.SysRoleExample;
import com.yapu.system.entity.SysRoleFunction;
import com.yapu.system.entity.SysRoleFunctionExample;
import com.yapu.system.entity.SysRoleFunctionExample.Criteria;
import com.yapu.system.service.itf.IRoleService;



public class RoleService implements IRoleService {
	
	private SysRoleDAO roleDao;
	private SysAccountRoleDAO accountroleDao;
	private SysAccountDAO accountDao;
	private SysOrgRoleDAO orgroleDao;
	private SysOrgDAO orgDao;
	private SysRoleFunctionDAO rolefunctionDao;
	private SysFunctionDAO functionDao;
	
	
	
	/**
	 * 删除角色之前，首先删除角色的各种关联关系
	 * @param roleIDList
	 * @return
	 */
	private Boolean beforeDeleteRole(List<String> roleIDList) {
		try {
			//1、删除角色与账户关联
			SysAccountRoleExample accountRoleExample = new SysAccountRoleExample();
			accountRoleExample.createCriteria().andRoleidIn(roleIDList);
			accountroleDao.deleteByExample(accountRoleExample);
			
			//2、删除角色与组的关联
			SysOrgRoleExample orgRoleExample = new SysOrgRoleExample();
			orgRoleExample.createCriteria().andRoleidIn(roleIDList);
			orgroleDao.deleteByExample(orgRoleExample);
			//3、删除角色与功能的关联
			SysRoleFunctionExample roleFunctionExample = new SysRoleFunctionExample();
			roleFunctionExample.createCriteria().andRoleidIn(roleIDList);
			rolefunctionDao.deleteByExample(roleFunctionExample);
			
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
//	/**
//	 * 拆分角色实体对象。为了查询
//	 * @param role
//	 * @return
//	 */
//	private SysRoleExample splitRoleEntity(SysRole role) {
//		if (null != role) {
//			SysRoleExample roleExample = new SysRoleExample();
//			SysRoleExample.Criteria criteria = roleExample.createCriteria();
//			if (null != role.getRolename()) {
//				criteria.andRolenameLike("%" + role.getRolename() + "%");
//			}
//			return roleExample;
//		}
//		return null;
//	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IRoleService#deleteRole(java.lang.Long)
	 */
	public int deleteRole(String roleID) {
		if (null != roleID && !"".equals(roleID)) {
			List<String> roleIDList = new ArrayList<String>();
			roleIDList.add(roleID);
			if (beforeDeleteRole(roleIDList)) {
				return roleDao.deleteByPrimaryKey(roleID);
			}
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IRoleService#deleteRole(com.yapu.system.entity.SysRole)
	 */
	public int deleteRole(SysRole role) {
		if (null != role) {
			return deleteRole(role.getRoleid());
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IRoleService#deleteRole(java.util.List)
	 */
	public int deleteRole(List<String> roleIDList) {
		if (null == roleIDList || 0 >= roleIDList.size() ) {
			return 0;
		}
		if (beforeDeleteRole(roleIDList)) {
			SysRoleExample roleExample = new SysRoleExample();
			roleExample.createCriteria().andRoleidIn(roleIDList);
			return roleDao.deleteByExample(roleExample);
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IRoleService#insertRole(com.yapu.system.entity.SysRole)
	 */
	public Boolean insertRole(SysRole role) {
		if (null != role) {
			try {
				roleDao.insertSelective(role);
				return true;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		return false;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IRoleService#insertRole(java.util.List)
	 */
	public Boolean insertRole(List<SysRole> roleList) {
		if (null != roleList && roleList.size() > 0) {
			try {
				for (int i=0;i<roleList.size();i++) {
					insertRole(roleList.get(i));
				}
				return true;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		return false;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IRoleService#rowCount(com.yapu.system.entity.SysRoleExample)
	 */
	public int rowCount(SysRoleExample example) {
		if (null != example) {
			return roleDao.countByExample(example);
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IRoleService#selectByPrimaryKey(java.lang.String)
	 */
	public SysRole selectByPrimaryKey(String roleID) {
		return roleDao.selectByPrimaryKey(roleID);
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IRoleService#selectByWhereNotPage(com.yapu.system.entity.SysRoleExample)
	 */
	public List<SysRole> selectByWhereNotPage(SysRoleExample example) {
		if (null != example) {
			return roleDao.selectByExample(example);
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IRoleService#updateRole(com.yapu.system.entity.SysRole)
	 */
	public int updateRole(SysRole role) {
		try {
			if (null != role) {
				return roleDao.updateByPrimaryKeySelective(role);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IRoleService#updateRole(java.util.List)
	 */
	public int updateRole(List<SysRole> roleList) {
		int num = 0;
		if (null != roleList && 0 < roleList.size()) {
			for (int i=0;i<roleList.size();i++) {
				try {
					updateRole(roleList.get(i));
					num += 1;
				} catch (Exception e) {
					System.out.println(e.getMessage());
					return 0;
				}
			}
			return num;
		}
		return 0;
	}
	
	
	//===========================================
	
	//============角色相关的操作=====================
	
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IRoleService#getRoleOfAccount(com.yapu.system.entity.SysRole)
	 */
	public List<SysAccount> getRoleOfAccount(SysRole role) {
		if (null != role) {
			//根据角色id，得到账户与角色关系表实体对象list集合
			SysAccountRoleExample accountroleExample = new SysAccountRoleExample();
			accountroleExample.createCriteria().andRoleidEqualTo(role.getRoleid());
			List<SysAccountRole> accountRoleList = accountroleDao.selectByExample(accountroleExample);
			//如果角色下有账户
			if (null != accountRoleList && accountRoleList.size() > 0) {
				//创建账户id集合
				List<String> accountIDList = new ArrayList<String>();
				for (int i=0;i<accountRoleList.size();i++) {
					accountIDList.add(accountRoleList.get(i).getAccountid());
				}
				SysAccountExample accountExample = new SysAccountExample();
				accountExample.createCriteria().andAccountidIn(accountIDList);
				List<SysAccount> accountList = accountDao.selectByExample(accountExample);
				
				return accountList;
			}
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IRoleService#getRoleOfOrg(com.yapu.system.entity.SysRole)
	 */
	public List<SysOrg> getRoleOfOrg(SysRole role) {
		if (null != role) {
			//得到角色与组关联表的list
			SysOrgRoleExample orgRoleExample = new SysOrgRoleExample();
			orgRoleExample.createCriteria().andRoleidEqualTo(role.getRoleid());
			List<SysOrgRole> orgRoleList = orgroleDao.selectByExample(orgRoleExample);
			if (null != orgRoleList && orgRoleList.size() >0 ) {
				//创建组id集合
				List<String> orgIDList = new ArrayList<String>();
				for (int i=0;i<orgRoleList.size();i++) {
					orgIDList.add(orgRoleList.get(i).getOrgid());
				}
				if (orgIDList.size() > 0) {
					SysOrgExample orgExample = new SysOrgExample();
					orgExample.createCriteria().andOrgidIn(orgIDList);
					List<SysOrg> orgList = orgDao.selectByExample(orgExample);
					return orgList;
				}
			}
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IRoleService#getRoleOfFunction(com.yapu.system.entity.SysRole)
	 */
	public List<SysFunction> getRoleOfFunction(SysRole role) {
		if (null != role) {
			SysRoleFunctionExample roleFunctionExample = new SysRoleFunctionExample();
			roleFunctionExample.createCriteria().andRoleidEqualTo(role.getRoleid());
			
			List<SysRoleFunction> roleFunctionList = rolefunctionDao.selectByExample(roleFunctionExample);
			if (null != roleFunctionList && roleFunctionList.size() >0) {
				//创建功能id集合
				List<String> functionIDList = new ArrayList<String>();
				for (int i=0;i<roleFunctionList.size();i++) {
					functionIDList.add(roleFunctionList.get(i).getFunctionid());
				}
				
				if (functionIDList.size() > 0) {
					SysFunctionExample functionExample = new SysFunctionExample();
					functionExample.createCriteria().andFunctionidIn(functionIDList);
					List<SysFunction> functionList = functionDao.selectByExample(functionExample);
					
					if (null != functionList && functionList.size()>0) {
						return functionList;
					}
					else {
						return null;
					}
				}
			}
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IRoleService#deleteRoleOfFunction(com.yapu.system.entity.SysRoleFunction)
	 */
	public int deleteRoleOfFunction(SysRoleFunction roleFunction) {
		if (null != roleFunction) {
			try {
				return rolefunctionDao.deleteByPrimaryKey(roleFunction.getRoleFunctionId());
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
		return 0;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IRoleService#insertRoleOfFunction(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Boolean insertRoleOfFunction(String roleid,String functionids) {
		
		if (null != roleid && !"".equals(roleid)) {
			try {
				//首先删除角色与功能已经存在的关联。删除后再添加
				SysRoleFunctionExample srfe = new SysRoleFunctionExample();
				Criteria criteria =  srfe.createCriteria();
				criteria.andRoleidEqualTo(roleid);
				rolefunctionDao.deleteByExample(srfe);
				
				if (null == functionids || "".equals(functionids)) {
					return true;
				}
				String[] functionidArray = functionids.split(",");
				
				for (int i=0;i<functionidArray.length;i++) {
					SysRoleFunction roleFunction = new SysRoleFunction();
					roleFunction.setRoleFunctionId(UUID.randomUUID().toString());
					roleFunction.setFunctionid(functionidArray[i]);
					roleFunction.setRoleid(roleid);
					rolefunctionDao.insertSelective(roleFunction);
				}
				return true;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
			
		}
		return false;
	}
	
	
	public void setRoleDao(SysRoleDAO roleDao) {
		this.roleDao = roleDao;
	}
	public void setAccountroleDao(SysAccountRoleDAO accountroleDao) {
		this.accountroleDao = accountroleDao;
	}
	public void setAccountDao(SysAccountDAO accountDao) {
		this.accountDao = accountDao;
	}
	public void setOrgroleDao(SysOrgRoleDAO orgroleDao) {
		this.orgroleDao = orgroleDao;
	}
	public void setOrgDao(SysOrgDAO orgDao) {
		this.orgDao = orgDao;
	}
	public void setRolefunctionDao(SysRoleFunctionDAO rolefunctionDao) {
		this.rolefunctionDao = rolefunctionDao;
	}
	public void setFunctionDao(SysFunctionDAO functionDao) {
		this.functionDao = functionDao;
	}
}
