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
	 * @param accountTreeList	账户与资源树关系表实体的集合。
	 * @return 					tree or false
	 */
	public Boolean updateAccountOfTree(List<SysAccountTree> accountTreeList) {
		if (null != accountTreeList && accountTreeList.size() > 0) {
			try {
				for (int i=0;i<accountTreeList.size();i++) {
					SysAccountTree accountTree = accountTreeList.get(i);
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
	public Boolean updateAccountOfTree(SysAccountTree accountTree) {
		if (null != accountTree) {
			try {
				accounttreeDao.updateByPrimaryKey(accountTree);
				return true;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		return false;
	}
	/**
	 * 删除账户与资源树的关联
	 * @param accountTree
	 * @return
	 */
	public int deleteAccountOfTree(SysAccountTree accountTree) {
		if (null != accountTree) {
			try {
				return accounttreeDao.deleteByPrimaryKey(accountTree.getAccountTreeId());
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
		return 0;
	}
	
	
	/**
	 * 得到账户能管理的资源树节点
	 * @param account
	 * @return
	 */
	public List<SysTree> getAccountOfTree(SysAccount account) {
		if (null != account) {
			//1、首先按账户id，得到该账户与资源树关系表集合
			SysAccountTreeExample accountTreeExample = new SysAccountTreeExample();
			accountTreeExample.createCriteria().andAccountidEqualTo(account.getAccountid());
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

	public void setAccounttreeDao(SysAccountTreeDAO accounttreeDao) {
		this.accounttreeDao = accounttreeDao;
	}
	public void setTreeDao(SysTreeDAO treeDao) {
		this.treeDao = treeDao;
	}
	
}
