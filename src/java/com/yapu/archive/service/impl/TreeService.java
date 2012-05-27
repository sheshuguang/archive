package com.yapu.archive.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.yapu.archive.dao.itf.DynamicDAO;
import com.yapu.archive.dao.itf.SysAccountTreeDAO;
import com.yapu.archive.dao.itf.SysOrgTreeDAO;
import com.yapu.archive.dao.itf.SysTableDAO;
import com.yapu.archive.dao.itf.SysTempletDAO;
import com.yapu.archive.dao.itf.SysTempletfieldDAO;
import com.yapu.archive.dao.itf.SysTreeDAO;
import com.yapu.archive.dao.itf.SysTreeTempletDAO;
import com.yapu.archive.entity.SysAccountTreeExample;
import com.yapu.archive.entity.SysOrgTreeExample;
import com.yapu.archive.entity.SysTable;
import com.yapu.archive.entity.SysTableExample;
import com.yapu.archive.entity.SysTemplet;
import com.yapu.archive.entity.SysTempletfield;
import com.yapu.archive.entity.SysTempletfieldExample;
import com.yapu.archive.entity.SysTree;
import com.yapu.archive.entity.SysTreeExample;
import com.yapu.archive.entity.SysTreeTemplet;
import com.yapu.archive.entity.SysTreeTempletExample;
import com.yapu.archive.service.itf.ITreeService;

public class TreeService implements ITreeService {
	
	private SysTreeDAO treeDao;
	private SysAccountTreeDAO accounttreeDao;
	private SysOrgTreeDAO orgtreeDao;
	private SysTreeTempletDAO treetempletDao;
	private SysTempletDAO templetDao;
	private SysTableDAO tableDao;
	private SysTempletfieldDAO templetfieldDao;
	private DynamicDAO dynamicDao;
	
	/**
	 * 删除资源树节点前，删除资源树的各种关联
	 * @param treeidList
	 */
	private Boolean beforeDeleteTree(List<String> treeidList) {
		if (null != treeidList && treeidList.size() >0) {
			try {
				//1、删除资源树与账户关联
				SysAccountTreeExample accountTreeExample = new SysAccountTreeExample();
//				accountTreeExample.createCriteria().andTreeidEqualTo(treeid);
				accountTreeExample.createCriteria().andTreeidIn(treeidList);
				accounttreeDao.deleteByExample(accountTreeExample);
				
				//2、删除资源树与组的关联
				SysOrgTreeExample orgTreeExample = new SysOrgTreeExample();
				orgTreeExample.createCriteria().andTreeidIn(treeidList);
//				orgTreeExample.createCriteria().andTreeidEqualTo(treeid);
				orgtreeDao.deleteByExample(orgTreeExample);
				
				//3删除动态表关联数据
//				SysTree tree = new SysTree();
//				tree.setTreeid(treeidList.get(0).toString());
				List<SysTable> tableList = getTreeOfTable(treeidList.get(0).toString());
				String treeidArray ="";
				for (int i=0;i<treeidList.size();i++) {
					treeidArray += "'" + treeidList.get(i).toString() + "',";
				}
				treeidArray = treeidArray.substring(0,treeidArray.length() -1);
				
				//TODO 删除挂接的物理文件  可以考虑删除条目时，不删除物理文件，将物理文件的状态设置为临时，做一个物理文件管理，可以在线挂接
				//或者询问用户直接删除还是作为临时文件处理
				
				for (int j =0;j<tableList.size();j++) {
					String sql = createDeleteSql(tableList.get(j),treeidArray);
					dynamicDao.update(sql);
				}
				
				//4、删除资源树与模板的关联
				SysTreeTempletExample treeTempletExample = new SysTreeTempletExample();
//				treeTempletExample.createCriteria().andTreeidEqualTo(treeid);
				treeTempletExample.createCriteria().andTreeidIn(treeidList);
				treetempletDao.deleteByExample(treeTempletExample);
				
				return true;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		return false;
	}
	
	private String createDeleteSql(SysTable table,String treeidArray) {
		StringBuffer sql = new StringBuffer();
		if (null != table) {
//			DELETE FROM a_eb0478aa_01 WHERE ID='aa'
			sql.append("delete from ");
			sql.append(table.getTablename());
			sql.append(" where treeid in (").append(treeidArray).append(")");
		}
		return sql.toString();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ITreeService#deleteTree(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public int deleteTree(String treeID) {
		if (null != treeID && !"".equals(treeID)) {
			//得到删除节点及该节点下子节点的id集合
			SysTree tree = treeDao.selectByPrimaryKey(treeID);
			List<String> treeidList = new ArrayList<String>();
			if (null != tree) {
				SysTreeExample ste = new SysTreeExample();
				ste.createCriteria().andTreenodeLike(tree.getTreenode() + "%");
				
				List<SysTree> treeList = treeDao.selectByExample(ste);
				
				if (null != treeList && treeList.size() >0 ) {
					for (int i = 0;i<treeList.size();i++) {
						treeidList.add(treeList.get(i).getTreeid());
					}
				}
			}
			
			//首先删除各种关联
			if (beforeDeleteTree(treeidList)) {
				SysTreeExample ste = new SysTreeExample();
				ste.createCriteria().andTreeidIn(treeidList);
				return treeDao.deleteByExample(ste);
//				return treeDao.deleteByPrimar?yKey(treeID);
			}
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ITreeService#deleteTree(com.yapu.system.entity.SysTree)
	 */
	public int deleteTree(SysTree tree) {
		return deleteTree(tree.getTreeid());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ITreeService#insertTree(com.yapu.system.entity.SysTree)
	 */
	public Boolean insertTree(SysTree tree) {
		if (null != tree) {
			try {
				treeDao.insertSelective(tree);
				
				//得到父节点对应的模板
				SysTemplet templet = getTreeOfTemplet(tree.getParentid());
				
				SysTreeTemplet treeTemplet = new SysTreeTemplet();
				treeTemplet.setTreeTempletId(UUID.randomUUID().toString());
				treeTemplet.setTreeid(tree.getTreeid());
				treeTemplet.setTempletid(templet.getTempletid());
				setTreeOfTemplet(treeTemplet);
				
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
	 * @see com.yapu.system.service.itf.ITreeService#insertTree(java.util.List)
	 */
	public Boolean insertTree(List<SysTree> treeList) {
		if (null != treeList && treeList.size() > 0) {
			try {
				for (int i=0;i<treeList.size();i++) {
					insertTree(treeList.get(i));
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
	 * @see com.yapu.system.service.itf.ITreeService#selectByPrimaryKey(java.lang.String)
	 */
	public SysTree selectByPrimaryKey(String treeID) {
		return treeDao.selectByPrimaryKey(treeID);
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.archive.service.itf.ITreeService#selectByWhereNotPage(com.yapu.archive.entity.SysTreeExample)
	 */
	@SuppressWarnings("unchecked")
	public List<SysTree> selectByWhereNotPage(SysTreeExample ste) {
			return treeDao.selectByExample(ste);
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ITreeService#updateTree(com.yapu.system.entity.SysTree)
	 */
	public int updateTree(SysTree tree) {
		try {
			if (null != tree) {
				return treeDao.updateByPrimaryKeySelective(tree);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ITreeService#updateTree(java.util.List)
	 */
	public int updateTree(List<SysTree> treeList) {
		int num = 0;
		if (null != treeList && 0 < treeList.size()) {
			for (int i=0;i<treeList.size();i++) {
				try {
					updateTree(treeList.get(i));
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
	
	//============资源树相关的操作=====================//
	
	/*
	 * 
	 */
	public SysTemplet getTreeOfTemplet(String treeid) {
		if (!"".equals(treeid)) {
			SysTreeTempletExample treeTempletExample = new SysTreeTempletExample();
			treeTempletExample.createCriteria().andTreeidEqualTo(treeid);
			List<SysTreeTemplet> treeTempletList = treetempletDao.selectByExample(treeTempletExample);
			
			if (null != treeTempletList && treeTempletList.size() == 1) {
				return templetDao.selectByPrimaryKey(treeTempletList.get(0).getTempletid());
			}
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ITreeService#setTreeOfTemplet(com.yapu.system.entity.SysTreeTemplet)
	 */
	public Boolean setTreeOfTemplet(SysTreeTemplet treeTemplet) {
		if (null != treeTemplet) {
			try {
				treetempletDao.insertSelective(treeTemplet);
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
	 * @see com.yapu.archive.service.itf.ITreeService#getTreeOfTable(java.lang.String)
	 */
	public List<SysTable> getTreeOfTable(String treeid) {
		SysTemplet templet = getTreeOfTemplet(treeid);
		if (null != templet) {
			SysTableExample tableExample = new SysTableExample();
			tableExample.createCriteria().andTempletidEqualTo(templet.getTempletid());
			return tableDao.selectByExample(tableExample);
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.archive.service.itf.ITreeService#getTreeOfTempletfield(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<SysTempletfield> getTreeOfTempletfield(String treeid,
			String tableType) {
		List<SysTable> tableList = getTreeOfTable(treeid);
		if (null != tableList && tableList.size() >0 ) {
			for (int i=0;i<tableList.size();i++) {
				if (tableList.get(i).getTabletype().equals(tableType)) {
					SysTempletfieldExample templetfieldExample = new SysTempletfieldExample();
					templetfieldExample.createCriteria().andTableidEqualTo(tableList.get(i).getTableid());
					templetfieldExample.setOrderByClause("SORT");
					return templetfieldDao.selectByExample(templetfieldExample);
				}
			}
		}
		return null;
	}
	
	
	
	

	public void setTreeDao(SysTreeDAO treeDao) {
		this.treeDao = treeDao;
	}
	public void setAccounttreeDao(SysAccountTreeDAO accounttreeDao) {
		this.accounttreeDao = accounttreeDao;
	}
	public void setOrgtreeDao(SysOrgTreeDAO orgtreeDao) {
		this.orgtreeDao = orgtreeDao;
	}
	public void setTreetempletDao(SysTreeTempletDAO treetempletDao) {
		this.treetempletDao = treetempletDao;
	}
	public void setTempletDao(SysTempletDAO templetDao) {
		this.templetDao = templetDao;
	}
	public void setTableDao(SysTableDAO tableDao) {
		this.tableDao = tableDao;
	}
	public void setTempletfieldDao(SysTempletfieldDAO templetfieldDao) {
		this.templetfieldDao = templetfieldDao;
	}
	public void setDynamicDao(DynamicDAO dynamicDao) {
		this.dynamicDao = dynamicDao;
	}
	
}
