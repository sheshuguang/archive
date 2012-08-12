package com.yapu.system.service.impl;
/**
 * 账户表操作服务类
 * 
 * @date 		2010-10-30
 * @author 		wangf
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.yapu.archive.entity.SysAccountTree;
import com.yapu.archive.entity.SysAccountTreeExample;
import com.yapu.system.dao.itf.SysAccountDAO;
import com.yapu.system.dao.itf.SysAccountOrgDAO;
import com.yapu.system.dao.itf.SysAccountRoleDAO;
import com.yapu.system.dao.itf.SysOrgDAO;
import com.yapu.system.dao.itf.SysRoleDAO;
import com.yapu.system.entity.SysAccount;
import com.yapu.system.entity.SysAccountExample;
import com.yapu.system.entity.SysAccountOrg;
import com.yapu.system.entity.SysAccountOrgExample;
import com.yapu.system.entity.SysAccountRole;
import com.yapu.system.entity.SysAccountRoleExample;
import com.yapu.system.entity.SysOrg;
import com.yapu.system.entity.SysRole;
import com.yapu.system.service.itf.IAccountService;
import com.yapu.system.util.MD5;

public class AccountService implements IAccountService {
	
	private SysAccountDAO accountDao;
	private SysOrgDAO orgDao;
	private SysRoleDAO roleDao;
	private SysAccountOrgDAO accountorgDao;
	private SysAccountRoleDAO accountroleDao;
	
	/**
	 * 删除账户时，先删除账户的外键关联
	 * @param accountidList
	 * @return
	 */
	private Boolean beforeDeleteAccount(List<String> accountidList) {
		try {
			//1、删除账户与组的关联
			SysAccountOrgExample accountOrgExample = new SysAccountOrgExample();
			accountOrgExample.createCriteria().andAccountidIn(accountidList);
			accountorgDao.deleteByExample(accountOrgExample);
			//2、删除账户与角色关联
			SysAccountRoleExample accountRoleExample = new SysAccountRoleExample();
			accountRoleExample.createCriteria().andAccountidIn(accountidList);
			accountroleDao.deleteByExample(accountRoleExample);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IAccountService#deleteAccount(java.lang.String)
	 */
	public int deleteAccount(String accountID) {
		if (null != accountID) {
			//删除账户前，删除账户的外键关系
			List<String> accountIDList = new ArrayList<String>();
			accountIDList.add(accountID);
			if (beforeDeleteAccount(accountIDList)) {
				return accountDao.deleteByPrimaryKey(accountID);
			}
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IAccountService#deleteAccount(com.yapu.system.entity.SysAccount)
	 */
	public int deleteAccount(SysAccount account) {
		if (null != account) {
			return deleteAccount(account.getAccountid());
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IAccountService#deleteAccount(java.util.List)
	 */
	public int deleteAccount(List<String> accountIDList) {
		if (null != accountIDList && accountIDList.size() > 0) {
			//删除账户前，删除账户的外键关系
			if (beforeDeleteAccount(accountIDList)) {
				SysAccountExample accountExample = new SysAccountExample();
				accountExample.createCriteria().andAccountidIn(accountIDList);
				return accountDao.deleteByExample(accountExample);
			}
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IAccountService#insertAccount(com.yapu.system.entity.SysAccount, java.lang.String)
	 */
	public Boolean insertAccount(SysAccount account,String parentOrgId) {
		if (null != account) {
			try {
				//插入帐户
				accountDao.insertSelective(account);
				//插入帐户与组对应
				SysAccountOrg accountOrg = new SysAccountOrg();
				if (null != parentOrgId && !"".equals(parentOrgId)) {
					accountOrg.setOrgid(parentOrgId);
				}
				else {
					accountOrg.setOrgid("2");
				}
				accountOrg.setAccountid(account.getAccountid());
				accountOrg.setAccountOrgId(UUID.randomUUID().toString());
				accountorgDao.insert(accountOrg);
				
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
	 * @see com.yapu.system.service.itf.IAccountService#insertAccount(java.util.List, java.lang.String)
	 */
	public Boolean insertAccount(List<SysAccount> accountList,String parentOrgId) {
		if (null != accountList && accountList.size() > 0) {
			try {
				for (SysAccount account : accountList) {
					insertAccount(account,parentOrgId);
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
	 * @see com.yapu.system.service.itf.IAccountService#login(com.yapu.system.entity.SysAccount)
	 */
	public SysAccount login(SysAccount accountParam) {
		if (null == accountParam) {
			return null;
		}
		String accountCode = accountParam.getAccountcode();
		String password = accountParam.getPassword();
		
		SysAccountExample accountExample = new SysAccountExample();
		accountExample.createCriteria().andAccountcodeEqualTo(accountCode);
		List<?> tmpList = accountDao.selectByExample(accountExample);
		
		if (tmpList.size() != 1){
			return null;
		}
		
		SysAccount account = (SysAccount) tmpList.get(0);
		
		//将输入的密码与Pojo里的密码MD5后对比，如果不匹配，说明密码不对
		if (!MD5.encode(password).equals(account.getPassword())){
			return null;
		}else{
			//判断登录账户是否绑定用户信息
			//TODO 得到用户基本信息。之后怎么处理？
//			if (null != account.getUserid() || account.getUserid() >0 ) {
//				//根据账户绑定的用户id，查找用户信息。
//				HashMap userMap = findUserByUserId(account.getUserid());
//			}
//			else {
//				
//			}
			
			
			//将账户对象插入VO
			
			//将用户信息对象插入vo
//			accountVo.setUserMap(userMap);
//			String username = (String) ((HashMap)((List)userMap.get("List")).get(0)).get("USERNAME");
//			Long userid = (Long) ((HashMap)((List)userMap.get("rowList")).get(0)).get("USERID");
//			getRequest().setAttribute("username", username);
//			getRequest().setAttribute("userid", userid);
			//在账户登录时，将账户所有信息和权限都读取到userVo里，这样，后面用到时，就不用读取了，
			
			//得到权限规则：这个规则管理员可以去设置，有这样几种选项：
			//1、用户权限 + 所属组权限
			//2、用户权限
			
			//TODO 得到账户角色对象
			
			//TODO 得到角色管理功能对象
			
			//TODO 得到账户组对象
			
			//TODO 得到组的角色对象
			
			//TODO 得到组
			
			//TODO 得到账户权限
			
			
			//用户登录成功，将用户实体存入session
//			getHttpSession().setAttribute(Constants.user_in_session, accountVo);
			return account;
		}
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IAccountService#rowCount(com.yapu.system.entity.SysAccount)
	 */
	public int rowCount(SysAccountExample example) {
		return accountDao.countByExample(example);
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IAccountService#selectByPrimaryKey(java.lang.String)
	 */
	public SysAccount selectByPrimaryKey(String accountID) {
		return accountDao.selectByPrimaryKey(accountID);
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IAccountService#selectByPrimaryKey(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public List<SysAccount> selectByPrimaryKey(List<String> accountIDList) {
		SysAccountExample accountExample = new SysAccountExample();
		accountExample.createCriteria().andAccountidIn(accountIDList);
		return accountDao.selectByExample(accountExample);
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IAccountService#selectByWhereNotPage(com.yapu.system.entity.SysAccountExample)
	 */
	public List<SysAccount> selectByWhereNotPage(SysAccountExample example) {
		return accountDao.selectByExample(example);
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IAccountService#selectByWherePage(com.yapu.system.entity.SysAccountExample)
	 */
	@SuppressWarnings("unchecked")
	public List<SysAccount> selectByWherePage(SysAccountExample example) {
//		//如果实体、分页大小、开始行号为空，不查询，直接返回null
//		if (null == account || null == account.getPageSize() || null == account.getStartRow()) {
//			return null;
//		}
//		HashMap map = new HashMap();
//		map.put("PAGESIZE", account.getPageSize());
//		map.put("STARTROW", account.getStartRow());
//		map.put("ORDERBY", account.getSortRules());
//		
//		StringBuffer whereStr = new StringBuffer();
//		if (null != account.getAccountcode()) {
//			whereStr.append("accountcode like '%" + account.getAccountcode() + "%' and ");
//		}
//		if (null != account.getAccountstate()) {
//			whereStr.append(" accountstate = " + account.getAccountstate() + " and ");
//		}
//		if (null != account.getAccountmemo()) {
//			whereStr.append("accountmemo like '%" + account.getAccountmemo() + "%' and ");
//		}
//		String where = "";
//		if (whereStr.length() > 0 ) {
//			where = whereStr.substring(0, whereStr.length() - 4);
//			
//		}
//		map.put("WHEREVALUE", where);
		List accountList = accountDao.selectByMapPage(example);
		return accountList;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IAccountService#updateAccount(com.yapu.system.entity.SysAccount)
	 */
	public int updateAccount(SysAccount account) {
		try {
			return accountDao.updateByPrimaryKeySelective(account);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IAccountService#updateAccount(java.util.List)
	 */
	public int updateAccount(List<SysAccount> accountList) {
		int num = 0;
		if (null != accountList && 0 < accountList.size()) {
			for (int i=0;i<accountList.size();i++) {
				try {
					updateAccount(accountList.get(i));
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
	
	/*
	 * 
	 */
	public int updateAccountState(List<?> accountIDList, int stateValue) {
		SysAccountExample aae = new SysAccountExample();
		aae.createCriteria().andAccountidIn(accountIDList);
		
		SysAccount aa = new SysAccount();
		aa.setAccountstate(stateValue);
		return accountDao.updateByExampleSelective(aa, aae);
	}
	
	//===========账户关联的操作=================================
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IAccountService#getAccountOfOrg(com.yapu.system.entity.SysAccount)
	 */
	public SysOrg getAccountOfOrg(SysAccount account) {
		SysAccountOrgExample accountOrgExample = new SysAccountOrgExample();
		accountOrgExample.createCriteria().andAccountidEqualTo(account.getAccountid());
		
		List<SysAccountOrg> accountorgList = accountorgDao.selectByExample(accountOrgExample);
		if (null != accountorgList && accountorgList.size() == 1) {
			//得到账户与组关系表实体
			SysAccountOrg accountOrg = accountorgList.get(0);
			//得到组对象
			return orgDao.selectByPrimaryKey(accountOrg.getOrgid());
		}
		else {
			return null;
		}
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IAccountService#getAccountOfRole(com.yapu.system.entity.SysAccount)
	 */
	public SysRole getAccountOfRole(SysAccount account) {
		SysAccountRoleExample accountRoleExample = new SysAccountRoleExample();
		accountRoleExample.createCriteria().andAccountidEqualTo(account.getAccountid());
		List<SysAccountRole> accountroleList = accountroleDao.selectByExample(accountRoleExample);
		if (null != accountroleList && accountroleList.size() > 0) {
			SysAccountRole accountRole = accountroleList.get(0);
			return roleDao.selectByPrimaryKey(accountRole.getRoleid());
		}
		else {
			return null;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IAccountService#setAccountOfOrg(java.util.List, java.lang.Long)
	 */
	public Boolean updateAccountOfOrg(List<String> accountIDList, String orgID) {
		if (null != accountIDList && accountIDList.size() >0 ) {
			if (null != orgID && !"".equals(orgID)) {
				try {
					SysAccountOrgExample accountOrgExample = new SysAccountOrgExample();
					accountOrgExample.createCriteria().andAccountidIn(accountIDList);
					
					SysAccountOrg accountOrg = new SysAccountOrg();
					accountOrg.setOrgid(orgID);
					int num = accountorgDao.updateByExampleSelective(accountOrg, accountOrgExample);
					if (num > 0) {
						return true;
					}
					else {
						return false;
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
					return false;
				}
			}
		}
		return false;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IAccountService#setAccountOfRole(com.yapu.system.entity.SysAccount, java.lang.Long)
	 */
	public Boolean updateAccountOfRole(SysAccount account, String roleID) {
		try {
			if (null != account && !"".equals(roleID)) {
				SysAccountRoleExample accountRoleExample = new SysAccountRoleExample();
				accountRoleExample.createCriteria().andAccountidEqualTo(account.getAccountid());
				List<SysAccountRole> accountRoleList = accountroleDao.selectByExample(accountRoleExample);
				
				SysAccountRole accountRole = new SysAccountRole();
				accountRole.setRoleid(roleID);
				//如果该账户没有设置过角色。则创建
				if (null == accountRoleList || 0 >= accountRoleList.size()) {
					accountRole.setAccountRoleId(UUID.randomUUID().toString());
					accountRole.setAccountid(account.getAccountid());
					accountroleDao.insertSelective(accountRole);
					return true;
				}
				else {
					//如果该账户已经设置过角色，更新
					int updateNum = accountroleDao.updateByExampleSelective(accountRole, accountRoleExample);
					if (updateNum >0 ) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IAccountService#setAccountOfRole(java.util.List, java.lang.Long)
	 */
	public Boolean updateAccountOfRole(List<String> accountIDList, String roleID) {
		if (null != accountIDList && accountIDList.size() >0 ) {
			if (null != roleID && !"".equals(roleID)) {
				for (int i=0;i<accountIDList.size();i++) {
					SysAccount account = new SysAccount();
					account.setAccountid(accountIDList.get(i));
					updateAccountOfRole(account,roleID);
				}
				return true;
			}
		}
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IAccountService#deleteAccountOfRole(com.yapu.system.entity.SysAccount)
	 */
	public boolean deleteAccountOfRole(SysAccount account) {
		if (null != account) {
			SysAccountRoleExample are = new SysAccountRoleExample();
			are.createCriteria().andAccountidEqualTo(account.getAccountid());
			try {
				if (accountroleDao.deleteByExample(are) > 0 ) {
					return true;
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
			
		}
		return false;
	}
	
	/*
	 * 取动态表例子
	 */
//	public HashMap findUserByUserId(Long userId) {
//		
//		List<ArTemplet> templetList = templetService.findTempletByTempletTableName("Sys_USER");
//		
//		if (null == templetList || templetList.size() <= 0) {
//			return null;
//		}
//		
//		List<ArTempletfield> templetFieldList = templetService.findTempletFieldByTempletId(templetList.get(0).getTempletid());
//		
//		if (null == templetFieldList || templetList.size() <=0) {
//			return null;
//		}
//		DynamicExample example = new DynamicExample();
//		example.setTableName("ar_user");
//		example.setFieldList(templetFieldList);
//		example.createCriteria().andEqualTo("userid", userId);
//		List userList = dynamicService.selectByExample(example);
//		
//		HashMap userMap = new HashMap();
//		
//		userMap.put("List", userList);
//		userMap.put("Field", templetFieldList);
//		
//		//1、根据userid得到账户与用户信息关系表对象。
//		//如果账户与用户对应上了。
//		
//		//2、根据关系表对象得到用户信息id
//		//3、根据用户信息id得到用户信息list
//		return userMap;
//	}
	
	
	
	public void setAccountDao(SysAccountDAO accountDao) {
		this.accountDao = accountDao;
	}
	public void setOrgDao(SysOrgDAO orgDao) {
		this.orgDao = orgDao;
	}
	public void setAccountorgDao(SysAccountOrgDAO accountorgDao) {
		this.accountorgDao = accountorgDao;
	}
	public void setAccountroleDao(SysAccountRoleDAO accountroleDao) {
		this.accountroleDao = accountroleDao;
	}
	public void setRoleDao(SysRoleDAO roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public List<SysAccountTree> getAccountOfTree(SysAccount account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteAccountOfTree(List<SysAccountTree> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteAccountOfTree(SysAccountTreeExample example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean insertAccountOfTree(List<SysAccountTree> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateAccountOfTree(SysAccountTree AccountTree) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SysAccountTree getAccountOfTree(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateAccountOfTree(SysAccountTree record,
			SysAccountTreeExample ex) {
		// TODO Auto-generated method stub
		return 0;
	}
}
