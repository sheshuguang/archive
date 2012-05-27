package com.yapu.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.yapu.system.dao.itf.SysAccountDAO;
import com.yapu.system.dao.itf.SysAccountOrgDAO;
import com.yapu.system.dao.itf.SysOrgDAO;
import com.yapu.system.dao.itf.SysOrgRoleDAO;
import com.yapu.system.dao.itf.SysRoleDAO;
import com.yapu.system.entity.SysAccount;
import com.yapu.system.entity.SysAccountExample;
import com.yapu.system.entity.SysAccountOrg;
import com.yapu.system.entity.SysAccountOrgExample;
import com.yapu.system.entity.SysOrg;
import com.yapu.system.entity.SysOrgExample;
import com.yapu.system.entity.SysOrgRole;
import com.yapu.system.entity.SysOrgRoleExample;
import com.yapu.system.entity.SysRole;
import com.yapu.system.service.itf.IOrgService;

public class OrgService implements IOrgService {
	
	private SysOrgDAO orgDao;
	private SysAccountDAO accountDao;
	private SysRoleDAO roleDao;
	private SysAccountOrgDAO accountorgDao;
	private SysOrgRoleDAO orgroleDao;
	
	
	
	/**
	 * 删除组之前，首先删除组的各种关联关系
	 * @param orgIDList
	 * @return
	 */
	private Boolean beforeDeleteOrg(List<String> orgIDList) {
		try {
			//1、将组下的账户移动到默认组下。
			SysAccountOrgExample accountOrgExample = new SysAccountOrgExample();
			accountOrgExample.createCriteria().andOrgidIn(orgIDList);
			SysAccountOrg accountOrg = new SysAccountOrg();
			accountOrg.setOrgid("2");
			accountorgDao.updateByExampleSelective(accountOrg, accountOrgExample);
			
			//2、将组下的组移动到默认组下
			SysOrgExample orgExample = new SysOrgExample();
			orgExample.createCriteria().andParentidIn(orgIDList);
			SysOrg org = new SysOrg();
			org.setParentid("2");
			orgDao.updateByExampleSelective(org, orgExample);
			
			//3、删除组与角色的关联
			SysOrgRoleExample orgRoleExample = new SysOrgRoleExample();
			orgRoleExample.createCriteria().andOrgidIn(orgIDList);
			orgroleDao.deleteByExample(orgRoleExample);
			
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
//	/**
//	 * 拆分组实体对象。为了查询
//	 * @param org
//	 * @return
//	 */
//	private SysOrgExample splitOrgEntity(SysOrg org) {
//		if (null != org) {
//			SysOrgExample orgExample = new SysOrgExample();
//			SysOrgExample.Criteria criteria = orgExample.createCriteria();
//			if (null != org.getOrgname()) {
//				criteria.andOrgnameLike("%" + org.getOrgname() + "%");
//				return orgExample;
//			}if (null != org.getParentid()){
//				criteria.andParentidEqualTo(org.getParentid());
//				return orgExample;
//			}
//			else {
//				return null;
//			}
//		}
//		return null;
//	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IOrgService#deleteOrg(java.lang.Long)
	 */
	public int deleteOrg(String orgID) {
		if (null != orgID) {
			List<String> orgIDList = new ArrayList<String>();
			orgIDList.add(orgID);
			if (beforeDeleteOrg(orgIDList)) {
				return orgDao.deleteByPrimaryKey(orgID);
			}
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IOrgService#deleteOrg(com.yapu.system.entity.SysOrg)
	 */
	public int deleteOrg(SysOrg org) {
		if (null != org) {
			return deleteOrg(org.getOrgid());
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IOrgService#deleteOrg(java.util.List)
	 */
	public int deleteOrg(List<String> orgIDList) {
		if (null == orgIDList || 0 >= orgIDList.size() ) {
			return 0;
		}
		if (beforeDeleteOrg(orgIDList)) {
			SysOrgExample orgExample = new SysOrgExample();
			orgExample.createCriteria().andOrgidIn(orgIDList);
			return orgDao.deleteByExample(orgExample);
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IOrgService#insertOrg(com.yapu.system.entity.SysOrg)
	 */
	public Boolean insertOrg(SysOrg org) {
		if (null != org) {
			try {
				orgDao.insertSelective(org);
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
	 * @see com.yapu.system.service.itf.IOrgService#insertOrg(java.util.List)
	 */
	public Boolean insertOrg(List<SysOrg> orgList) {
		if (null != orgList && orgList.size() > 0) {
			try {
				for (int i=0;i<orgList.size();i++) {
					insertOrg(orgList.get(i));
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
	 * @see com.yapu.system.service.itf.IOrgService#rowCount(com.yapu.system.entity.SysOrgExample)
	 */
	public int rowCount(SysOrgExample example) {
		if (null != example) {
			return orgDao.countByExample(example);
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IOrgService#selectByPrimaryKey(java.lang.String)
	 */
	public SysOrg selectByPrimaryKey(String orgID) {
		return orgDao.selectByPrimaryKey(orgID);
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IOrgService#selectByWhereNotPage(com.yapu.system.entity.SysOrgExample)
	 */
	public List<SysOrg> selectByWhereNotPage(SysOrgExample example) {
		if (null != example) {
			return orgDao.selectByExample(example);
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IOrgService#updateOrg(com.yapu.system.entity.SysOrg)
	 */
	public int updateOrg(SysOrg org) {
		try {
			if (null != org) {
				return orgDao.updateByPrimaryKeySelective(org);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IOrgService#updateOrg(java.util.List)
	 */
	public int updateOrg(List<SysOrg> orgList) {
		int num = 0;
		if (null != orgList && 0 < orgList.size()) {
			for (int i=0;i<orgList.size();i++) {
				try {
					updateOrg(orgList.get(i));
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
	
	//============组相关的操作=====================
	
	public List<SysAccount> getOrgOfAccount(SysOrg org) {
		List<SysAccount> accountList = new ArrayList<SysAccount>();
		if (null != org) {
			//根据组id，得到账户与组关系表实体对象list集合
			SysAccountOrgExample accountorgExample = new SysAccountOrgExample();
			accountorgExample.createCriteria().andOrgidEqualTo(org.getOrgid());
			List<SysAccountOrg> accountOrgList = accountorgDao.selectByExample(accountorgExample);
			//如果组下有账户
			if (null != accountOrgList && accountOrgList.size() > 0) {
				//创建账户id集合
				List<String> accountIDList = new ArrayList<String>();
				for (int i=0;i<accountOrgList.size();i++) {
					accountIDList.add(accountOrgList.get(i).getAccountid());
				}
				SysAccountExample accountExample = new SysAccountExample();
				accountExample.createCriteria().andAccountidIn(accountIDList);
				accountList = accountDao.selectByExample(accountExample);
				
				//return accountList;
			}
		}
		return accountList;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IOrgService#getOrgOfRole(com.yapu.system.entity.SysOrg)
	 */
	public SysRole getOrgOfRole(SysOrg org) {
		//根据组id,得到组与角色关系表对象
		SysOrgRoleExample orgRoleExample = new SysOrgRoleExample();
		orgRoleExample.createCriteria().andOrgidEqualTo(org.getOrgid());
		List<SysOrgRole> orgRoleList = orgroleDao.selectByExample(orgRoleExample);
		if (null != orgRoleList && orgRoleList.size() > 0) {
			//得到对于的角色实体返回
			SysOrgRole orgRole = orgRoleList.get(0);
			if (null != orgRole) {
				return roleDao.selectByPrimaryKey(orgRole.getRoleid());
			}
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IOrgService#setOrgOfRole(com.yapu.system.entity.SysOrg, java.lang.Long)
	 */
	public Boolean setOrgOfRole(SysOrg org, String roleID) {
		
		if (null != org) {
			SysOrgRoleExample orgRoleExample = new SysOrgRoleExample();
			orgRoleExample.createCriteria().andOrgidEqualTo(org.getOrgid());
			
			//如果rileid是空，就等于去掉组与角色的对应
			if (null == roleID || "".equals(roleID)) {
				orgroleDao.deleteByExample(orgRoleExample);
			}
			else {
				List<SysOrgRole> orgRoleList = orgroleDao.selectByExample(orgRoleExample);
				if (null != orgRoleList && orgRoleList.size() == 1) {
					SysOrgRole orgRole = orgRoleList.get(0);
					if (null != orgRole) {
						orgRole.setRoleid(roleID);
						orgroleDao.updateByPrimaryKeySelective(orgRole);
					}
				}
				else {
					SysOrgRole orgRole = new SysOrgRole();
					orgRole.setOrgRoleId(UUID.randomUUID().toString());
					orgRole.setRoleid(roleID);
					orgRole.setOrgid(org.getOrgid());
					orgroleDao.insertSelective(orgRole);
				}
			}
			return true;
		}
		return false;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IOrgService#setOrgOfRole(java.util.List, java.lang.Long)
	 */
	public Boolean setOrgOfRole(List<String> orgIDList, String roleID) {
		if (null != orgIDList && orgIDList.size() >0) {
			if (null != roleID && !"".equals(roleID)) {
				for (int i=0;i<orgIDList.size();i++) {
					SysOrg org = new SysOrg();
					org.setOrgid(orgIDList.get(i));
					setOrgOfRole(org,roleID);
				}
				return true;
			}
		}
		return false;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IOrgService#deleteOrgOfRole(com.yapu.system.entity.SysOrg)
	 */
	public boolean deleteOrgOfRole(SysOrg org) {
		if (null != org) {
			SysOrgRoleExample ore = new SysOrgRoleExample();
			ore.createCriteria().andOrgidEqualTo(org.getOrgid());
			try {
				if (orgroleDao.deleteByExample(ore) > 0) {
					return true;
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		return false;
	}
	
	public void setOrgDao(SysOrgDAO orgDao) {
		this.orgDao = orgDao;
	}
	public void setAccountorgDao(SysAccountOrgDAO accountorgDao) {
		this.accountorgDao = accountorgDao;
	}
	public void setOrgroleDao(SysOrgRoleDAO orgroleDao) {
		this.orgroleDao = orgroleDao;
	}
	public void setAccountDao(SysAccountDAO accountDao) {
		this.accountDao = accountDao;
	}
	public void setRoleDao(SysRoleDAO roleDao) {
		this.roleDao = roleDao;
	}
}
