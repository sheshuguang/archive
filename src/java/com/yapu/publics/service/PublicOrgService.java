package com.yapu.publics.service;

/**
 * 帐户组Service的继承类，在System包外部处理业务逻辑与帐户组的关联。
 * OrgService作为父类，实现了帐户组的基本操作，包括与帐户、角色等底层的关系处理。
 */
import java.util.ArrayList;
import java.util.List;

import com.yapu.archive.dao.itf.SysOrgTreeDAO;
import com.yapu.archive.dao.itf.SysTreeDAO;
import com.yapu.archive.entity.SysOrgTree;
import com.yapu.archive.entity.SysOrgTreeExample;
import com.yapu.archive.entity.SysTree;
import com.yapu.archive.entity.SysTreeExample;
import com.yapu.system.entity.SysOrg;
import com.yapu.system.service.impl.OrgService;

public class PublicOrgService extends OrgService {
	
	private SysOrgTreeDAO orgtreeDao;
	private SysTreeDAO treeDao;

	/*
	 * (non-Javadoc)继承类重写删除组。删除组与业务层的关联。再调用父类，删除底层组。
	 * @see com.yapu.system.service.impl.OrgService#deleteOrg(java.lang.String)
	 */
	public int deleteOrg(String orgID) {
		if (null != orgID) {
			List<String> orgIDList = new ArrayList<String>();
			orgIDList.add(orgID);
			//删除组与资源树的关联
			SysOrgTreeExample orgTreeExample = new SysOrgTreeExample();
			orgTreeExample.createCriteria().andOrgidIn(orgIDList);
			orgtreeDao.deleteByExample(orgTreeExample);
			
			//调用父类，删除底层组。
			return super.deleteOrg(orgID);
		}
		return 0;
	}
	
	/**
	 * 得到组能管理的资源树节点
	 * @param org
	 * @return
	 */
	public List<SysOrgTree> getOrgOfTree(SysOrg org) {
		if (null != org) {
			//1、首先组id，得到该组与资源树关系表集合
			SysOrgTreeExample orgTreeExample = new SysOrgTreeExample();
			orgTreeExample.createCriteria().andOrgidEqualTo(org.getOrgid());
			List<SysOrgTree> orgTreeList = orgtreeDao.selectByExample(orgTreeExample);
			return orgTreeList;
//			//如果该账户有跟资源树关联
//			if (null != orgTreeList && orgTreeList.size() >0 ) {
//				List<String> treeIDList = new ArrayList<String>();
//				//得到tree的id集合
//				for (int i=0;i<orgTreeList.size();i++) {
//					treeIDList.add(orgTreeList.get(i).getTreeid());
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
	 * @see com.yapu.system.service.impl.OrgService#getTree(java.lang.String)
	 */
	public List<SysTree> getTree(String id) {
		if (null != id) {
			//1、首先组id，得到该组与资源树关系表集合
			SysOrgTreeExample orgTreeExample = new SysOrgTreeExample();
			orgTreeExample.createCriteria().andOrgidEqualTo(id);
			List<SysOrgTree> orgTreeList = orgtreeDao.selectByExample(orgTreeExample);
			//如果该账户有跟资源树关联
			if (null != orgTreeList && orgTreeList.size() >0 ) {
				List<String> treeIDList = new ArrayList<String>();
				//得到tree的id集合
				for (int i=0;i<orgTreeList.size();i++) {
					treeIDList.add(orgTreeList.get(i).getTreeid());
				}
				SysTreeExample treeExample = new SysTreeExample();
				treeExample.createCriteria().andTreeidIn(treeIDList);
				
				List<SysTree> treeList = treeDao.selectByExample(treeExample);
				return treeList;
			}
		}
		return null;
	}
	
	
	/**
	 * 设置组与资源的关联
	 * @param orgTreeList
	 * @return
	 */
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.impl.OrgService#insertOrgOfTree(java.util.List)
	 */
	public Boolean insertOrgOfTree(List<SysOrgTree> orgTreeList) {
		if (orgTreeList.size() > 0) {
			try {
				for (int i=0;i<orgTreeList.size();i++) {
					SysOrgTree orgTree = orgTreeList.get(i);
					orgtreeDao.insertSelective(orgTree);
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
	 * 删除组与资源树的关联
	 * @param orgTree
	 * @return
	 */
	
	public Boolean deleteOrgOfTree(List<SysOrgTree> list) {
		if (list.size() > 0) {
			try {
				for (int i=0;i<list.size();i++) {
					SysOrgTree orgTree = list.get(i);
					orgtreeDao.deleteByPrimaryKey(orgTree.getOrgTreeId());
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
	 * @see com.yapu.system.service.impl.OrgService#deleteOrgOfTree(com.yapu.archive.entity.SysOrgTreeExample)
	 */
	public Boolean deleteOrgOfTree(SysOrgTreeExample example) {
		try {
			orgtreeDao.deleteByExample(example);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return true;
	}
	
	
	/**
	 * 更新组与资源树的关联
	 * @param orgTree
	 * @return
	 */
	public int updateOrgOfTree(SysOrgTree orgTree) {
		if (null != orgTree) {
			try {
				return orgtreeDao.updateByPrimaryKeySelective(orgTree);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
		return 0;
	}
	
	public int updateOrgOfTree(SysOrgTree record,SysOrgTreeExample ex) {
		if (null != ex) {
			try {
				return orgtreeDao.updateByExampleSelective(record, ex);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return 0;
	}
	
	public SysOrgTree getOrgOfTree(String id) {
		if (null != id && !"".equals(id)) {
			SysOrgTree o = orgtreeDao.selectByPrimaryKey(id);
			return o;
		}
		return null;
	}
	
	

	public void setOrgtreeDao(SysOrgTreeDAO orgtreeDao) {
		this.orgtreeDao = orgtreeDao;
	}
	public void setTreeDao(SysTreeDAO treeDao) {
		this.treeDao = treeDao;
	}
	
}
