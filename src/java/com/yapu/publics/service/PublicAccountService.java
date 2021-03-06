package com.yapu.publics.service;

/*
 * 帐户表的继承类，在外部操作帐户表与其他外接功能的关联
 * 2011-11-08
 * wangf
 */

import java.util.ArrayList;
import java.util.List;

import com.yapu.archive.dao.itf.SysAccountTreeDAO;
import com.yapu.archive.dao.itf.SysTreeDAO;
import com.yapu.archive.entity.SysAccountTree;
import com.yapu.archive.entity.SysAccountTreeExample;
import com.yapu.archive.entity.SysTree;
import com.yapu.archive.entity.SysTreeExample;
import com.yapu.system.entity.SysAccount;
import com.yapu.system.service.impl.AccountService;

public class PublicAccountService extends AccountService {
	
	private SysAccountTreeDAO accounttreeDao;
	private SysTreeDAO treeDao;
	
	/*
	 * 部分重写删除帐户操作。
	 * @see com.yapu.system.service.impl.AccountService#deleteAccount(java.lang.String)
	 */
	public int deleteAccount(String accountID) {
		if (null != accountID) {
			List<String> accountIDList = new ArrayList<String>();
			accountIDList.add(accountID);
			//删除账户与资源树的关联
			SysAccountTreeExample accountTreeExample = new SysAccountTreeExample();
			accountTreeExample.createCriteria().andAccountidIn(accountIDList);
			accounttreeDao.deleteByExample(accountTreeExample);
			//执行父类删除帐户
			return super.deleteAccount(accountID);
		}
		return 0;
	}
	
	/**
	 * 更新账户的资源树关联
	 * @param list	账户与资源树关系表实体的集合。
	 * @return 					tree or false
	 */
	public Boolean insertAccountOfTree(List<SysAccountTree> list) {
		if (null != list && list.size() > 0) {
			try {
				for (int i=0;i<list.size();i++) {
					SysAccountTree accountTree = list.get(i);
					accounttreeDao.insertSelective(accountTree);
				}
				return true;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
			
		}
		return false;
	}
	/**
	 * 更新账户与资源树的关联内容
	 * @param accountTree
	 * @return
	 */
	public int updateAccountOfTree(SysAccountTree accountTree) {
		
		if (null != accountTree) {
			try {
				return accounttreeDao.updateByPrimaryKeySelective(accountTree);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.impl.AccountService#updateAccountOfTree(com.yapu.archive.entity.SysAccountTree, com.yapu.archive.entity.SysAccountTreeExample)
	 */
	public int updateAccountOfTree(SysAccountTree record,SysAccountTreeExample ex) {
		if (null != ex) {
			try {
				return accounttreeDao.updateByExampleSelective(record, ex);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return 0;
	}
	
	/**
	 * 删除账户与资源树的关联
	 * @param accountTree
	 * @return
	 */
	public Boolean deleteAccountOfTree(List<SysAccountTree> list) {
		
		if (list.size() > 0) {
			try {
				for (int i=0;i<list.size();i++) {
					SysAccountTree accountTree = list.get(i);
					accounttreeDao.deleteByPrimaryKey(accountTree.getAccountTreeId());
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
	 * @see com.yapu.system.service.impl.AccountService#deleteAccountOfTree(com.yapu.archive.entity.SysAccountTreeExample)
	 */
	public Boolean deleteAccountOfTree(SysAccountTreeExample example) {
		try {
			accounttreeDao.deleteByExample(example);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return true;
	}
	
	
	/**
	 * 得到账户能管理的资源树节点
	 * @param account
	 * @return
	 */
	public List<SysAccountTree> getAccountOfTree(SysAccount account) {
		if (null != account) {
			//1、首先按账户id，得到该账户与资源树关系表集合
			SysAccountTreeExample accountTreeExample = new SysAccountTreeExample();
			accountTreeExample.createCriteria().andAccountidEqualTo(account.getAccountid());
			List<SysAccountTree> accountTreeList = accounttreeDao.selectByExample(accountTreeExample);
			return accountTreeList;
//			//如果该账户有跟资源树关联
//			if (null != accountTreeList && accountTreeList.size() >0 ) {
//				List<String> treeIDList = new ArrayList<String>();
//				//得到tree的id集合
//				for (int i=0;i<accountTreeList.size();i++) {
//					treeIDList.add(accountTreeList.get(i).getTreeid());
//				}
//				SysTreeExample treeExample = new SysTreeExample();
//				treeExample.createCriteria().andTreeidIn(treeIDList);
//				
//				List<SysTree> treeList = treeDao.selectByExample(treeExample);
//				return treeList;
//			}
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.impl.AccountService#getTree(java.lang.String)
	 */
	public List<SysTree> getTree(String accountid) {
		if (null != accountid) {
			//1、首先按账户id，得到该账户与资源树关系表集合
			SysAccountTreeExample accountTreeExample = new SysAccountTreeExample();
			accountTreeExample.createCriteria().andAccountidEqualTo(accountid);
			List<SysAccountTree> accountTreeList = accounttreeDao.selectByExample(accountTreeExample);
			//如果该账户有跟资源树关联
			if (null != accountTreeList && accountTreeList.size() >0 ) {
				List<String> treeIDList = new ArrayList<String>();
				//得到tree的id集合
				for (int i=0;i<accountTreeList.size();i++) {
					treeIDList.add(accountTreeList.get(i).getTreeid());
				}
				SysTreeExample treeExample = new SysTreeExample();
				treeExample.createCriteria().andTreeidIn(treeIDList);
				
				List<SysTree> treeList = treeDao.selectByExample(treeExample);
				return treeList;
			}
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.impl.AccountService#getAccountOfTree(java.lang.String)
	 */
	public SysAccountTree getAccountOfTree(String id) {
		if (null != id && !"".equals(id)) {
			SysAccountTree o = accounttreeDao.selectByPrimaryKey(id);
			return o;
		}
		return null;
	}

	public void setAccounttreeDao(SysAccountTreeDAO accounttreeDao) {
		this.accounttreeDao = accounttreeDao;
	}
	public void setTreeDao(SysTreeDAO treeDao) {
		this.treeDao = treeDao;
	}
	
}
