package com.yapu.archive.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.yapu.archive.dao.itf.ArCollectionDAO;
import com.yapu.archive.dao.itf.ArReleaseDAO;
import com.yapu.archive.dao.itf.DynamicDAO;
import com.yapu.archive.dao.itf.SysAccountTreeDAO;
import com.yapu.archive.dao.itf.SysCodeDAO;
import com.yapu.archive.dao.itf.SysDocDAO;
import com.yapu.archive.dao.itf.SysOrgTreeDAO;
import com.yapu.archive.dao.itf.SysTableDAO;
import com.yapu.archive.dao.itf.SysTempletDAO;
import com.yapu.archive.dao.itf.SysTempletfieldDAO;
import com.yapu.archive.dao.itf.SysTreeDAO;
import com.yapu.archive.dao.itf.SysTreeTempletDAO;
import com.yapu.archive.entity.ArCollectionExample;
import com.yapu.archive.entity.ArReleaseExample;
import com.yapu.archive.entity.SysAccountTreeExample;
import com.yapu.archive.entity.SysCode;
import com.yapu.archive.entity.SysCodeExample;
import com.yapu.archive.entity.SysDoc;
import com.yapu.archive.entity.SysDocExample;
import com.yapu.archive.entity.SysOrgTreeExample;
import com.yapu.archive.entity.SysTable;
import com.yapu.archive.entity.SysTableExample;
import com.yapu.archive.entity.SysTemplet;
import com.yapu.archive.entity.SysTempletExample;
import com.yapu.archive.entity.SysTempletfield;
import com.yapu.archive.entity.SysTempletfieldExample;
import com.yapu.archive.entity.SysTree;
import com.yapu.archive.entity.SysTreeExample;
import com.yapu.archive.entity.SysTreeTemplet;
import com.yapu.archive.entity.SysTreeTempletExample;
import com.yapu.archive.service.itf.ITempletService;

public class TempletService implements ITempletService {
	
	private SysTempletDAO templetDao;
	private SysTempletfieldDAO templetfieldDao;
	private SysTreeTempletDAO	treetempletDao;
	
	private SysTableDAO tableDao;
	private SysDocDAO docDao;
	private SysCodeDAO codeDao;
	private SysAccountTreeDAO accounttreeDao;
	private SysOrgTreeDAO orgtreeDao;
	private SysTreeDAO treeDao;
	private ArCollectionDAO collectionDao;
	private ArReleaseDAO releaseDao;
	private DynamicDAO dynamicDao;
	
	/**
	 * 删除模板前，删除模板的关联表
	 * @param templet
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean beforeDeleteTemplet(String templetid) {
		try {
			//1、删除电子文件 、 字段代码、字段、表名、删除物理表
			//1.1 删除电子文件
			SysTableExample tableExample = new SysTableExample();
			tableExample.createCriteria().andTempletidEqualTo(templetid);
			//得到模板对应的表名
			List<SysTable> tableList = tableDao.selectByExample(tableExample);
			
			//根据表名id得到全文表内容
			for (int i=0;i<tableList.size();i++) {
				SysDocExample docExample = new SysDocExample();
				docExample.createCriteria().andTableidEqualTo(tableList.get(i).getTableid());
				List<SysDoc> docList = docDao.selectByExample(docExample);
				//TODO 删除物理文件
				if (null != docList && docList.size() > 0) {
					for (int j=0;j<docList.size();j++) {
						
					}
				}
				//删除全文表内容
				docDao.deleteByExample(docExample);
			}
			
			//1.2 删除字段对应的代码、字段表内容
			for (int i=0;i<tableList.size();i++) {
				//得到有代码的字段
				SysTempletfieldExample templetfieldExample = new SysTempletfieldExample();
				SysTempletfieldExample.Criteria  criteria = templetfieldExample.createCriteria();
				criteria.andTableidEqualTo(tableList.get(i).getTableid());
				criteria.andIscodeEqualTo(1);
				List<SysTempletfield> templetfieldIsCodeList = templetfieldDao.selectByExample(templetfieldExample);
				if (null != templetfieldIsCodeList && templetfieldIsCodeList.size() >0) {
					//得到字段id集合
					List<String> templetfieldIDList = new ArrayList<String>();
					for (int j=0;j<templetfieldIsCodeList.size();j++) {
						templetfieldIDList.add(templetfieldIsCodeList.get(j).getTempletfieldid());
					}
					SysCodeExample codeExample = new SysCodeExample();
					codeExample.createCriteria().andTempletfieldidIn(templetfieldIDList);
					codeDao.deleteByExample(codeExample);
				}
				
				//删除字段表内容
				templetfieldExample.clear();
				templetfieldExample.createCriteria().andTableidEqualTo(tableList.get(i).getTableid());
				templetfieldDao.deleteByExample(templetfieldExample);
			}
			
			//删除收藏夹  删除发布
			for (int i=0;i<tableList.size();i++) {
				ArCollectionExample collectionExample = new ArCollectionExample();
				collectionExample.createCriteria().andTableidEqualTo(tableList.get(i).getTableid());
				
				ArReleaseExample releaseExample = new ArReleaseExample();
				releaseExample.createCriteria().andTableidEqualTo(tableList.get(i).getTableid());
				
				try {
					collectionDao.deleteByExample(collectionExample);
					releaseDao.deleteByExample(releaseExample);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			
			//1.3 删除物理表
			for (int i=0;i<tableList.size();i++) {
				StringBuffer sql = new StringBuffer();
				sql.append("drop table ").append(tableList.get(i).getTablename());
				dynamicDao.update(sql.toString());
			}
			//删除表名管理表内容
			tableDao.deleteByExample(tableExample);
			
			//2、删除模板与资源树关联、删除资源树模板相关内容
			//2.1 删除模板与资源树的关联
			SysTreeTempletExample treeTempletExample = new SysTreeTempletExample();
			treeTempletExample.createCriteria().andTempletidEqualTo(templetid);
//			treetempletDao.deleteByExample(treeTempletExample);
			
			List<SysTreeTemplet> treeTempletList = treetempletDao.selectByExample(treeTempletExample);
			
			//2.2循环得到资源树id集合，用来批量删除账户（账户组）与资源树的关联。
			List<String> treeIDList = new ArrayList<String>();
			for (int i=0;i<treeTempletList.size();i++) {
				treeIDList.add(treeTempletList.get(i).getTreeid());
			}
//			//删除资源树及各种关联
			deleteTree(treeIDList);
			
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	/**
	 * 删除资源树节点前，删除资源树的各种关联
	 * @param tree
	 */
	private Boolean deleteTree(List<String> treeIDList) {
		if (null != treeIDList && treeIDList.size() > 0) {
			try {
				//1、删除资源树与账户关联
				SysAccountTreeExample accountTreeExample = new SysAccountTreeExample();
				accountTreeExample.createCriteria().andTreeidIn(treeIDList);
				accounttreeDao.deleteByExample(accountTreeExample);
				
				//2、删除资源树与组的关联
				SysOrgTreeExample orgTreeExample = new SysOrgTreeExample();
				orgTreeExample.createCriteria().andTreeidIn(treeIDList);
				orgtreeDao.deleteByExample(orgTreeExample);
				//3、删除资源树与模板的关联
				SysTreeTempletExample treeTempletExample = new SysTreeTempletExample();
				treeTempletExample.createCriteria().andTreeidIn(treeIDList);
				treetempletDao.deleteByExample(treeTempletExample);
				//4、删除资源树内容
				SysTreeExample treeExample = new SysTreeExample();
				treeExample.createCriteria().andTreeidIn(treeIDList);
				treeDao.deleteByExample(treeExample);
				
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
	 * @see com.yapu.system.service.itf.ITempletService#deleteTemplet(java.lang.String)
	 */
	public int deleteTemplet(String templetID) {
		if (null != templetID && !"".equals(templetID)) {
			//首先删除各种关联
			if (beforeDeleteTemplet(templetID)) {
				return templetDao.deleteByPrimaryKey(templetID);
			}
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ITempletService#deleteTemplet(com.yapu.system.entity.SysTemplet)
	 */
	public int deleteTemplet(SysTemplet templet) {
		if (null != templet) {
			if (beforeDeleteTemplet(templet.getTempletid())) {
				return templetDao.deleteByPrimaryKey(templet.getTempletid());
			}
		}
		return 0;
	}
	/**
	 * 创建表结构
	 * @param templet
	 * @return
	 */
	private boolean createTable(SysTemplet templet,String copyTempletid) {
		if (null == templet || "".equals(copyTempletid)) {
			return false;
		}
		//取得模板对应的表
		SysTableExample ste = new SysTableExample();
		ste.createCriteria().andTempletidEqualTo(copyTempletid);
		//得到参照的基础模板
		List<SysTable> tableList = tableDao.selectByExample(ste);
		for (int i=0;i<tableList.size();i++) {
			//插入表名管理表
			SysTable newTable = new SysTable();
			newTable.setTableid(UUID.randomUUID().toString());
			if ("A".equals(templet.getTemplettype()) && tableList.get(i).getTabletype().equals("01")) {
				newTable.setTablelabel(templet.getTempletname()+ "_案卷级");
			}
			else {
				newTable.setTablelabel(templet.getTempletname() + "_文件级");
			}
			if ("F".equals(templet.getTemplettype())) {
				newTable.setTablename("F_" + templet.getTempletid().substring(0, 8) + "_" + tableList.get(i).getTabletype());
			}
			else {
				newTable.setTablename("A_" + templet.getTempletid().substring(0, 8) + "_" + tableList.get(i).getTabletype());
			}
			newTable.setTempletid(templet.getTempletid());
			newTable.setTabletype(tableList.get(i).getTabletype());
			//插入
			tableDao.insertSelective(newTable);
			
			//根据参照模板的字段，得到创建新表的sql语句
			SysTempletfieldExample stfe = new SysTempletfieldExample();
			stfe.createCriteria().andTableidEqualTo(tableList.get(i).getTableid());
			List<SysTempletfield> templetfieldList = templetfieldDao.selectByExample(stfe);
			if (null != templetfieldList && templetfieldList.size() > 0) {
				String sql = createSql(templetfieldList);
				sql = "create table " + newTable.getTablename() + " (" + sql + ")";
				dynamicDao.update(sql.toString());
				
				//将模板字段，作为新表的字段插入字段管理表
				for (int j=0;j<templetfieldList.size();j++) {
					String oldTempletfieldid = templetfieldList.get(j).getTempletfieldid();
					SysTempletfield templetfield = templetfieldList.get(j);
					templetfield.setTempletfieldid(UUID.randomUUID().toString());
					templetfield.setTableid(newTable.getTableid());
					templetfieldDao.insertSelective(templetfield);
					//判断字段是否有代码
					if (templetfield.getIscode() == 1) {
						//得到代码
						SysCodeExample sce = new SysCodeExample();
						sce.createCriteria().andTempletfieldidEqualTo(oldTempletfieldid);
						
						List<SysCode> codeList = codeDao.selectByExample(sce);
						if (codeList.size() > 0) {
							for (int z=0;z<codeList.size();z++) {
								SysCode code = codeList.get(z);
								code.setCodeid(UUID.randomUUID().toString());
								code.setTempletfieldid(templetfield.getTempletfieldid());
								codeDao.insertSelective(code);
							}
						}
					}
				}
			}
		}
//		
//		
//		
//			//得到模板类型，用来取字段。
//			String templetType = templet.getTemplettype();
//			if (templetType.equals("A")) {
//				//如果是标准档案，取得标准模板对应的表
//				SysTableExample ste = new SysTableExample();
//				ste.createCriteria().andTempletidEqualTo("1");
//				//得到参照的基础模板
//				List<SysTable> tableList = tableDao.selectByExample(ste);
//				for (int i=0;i<tableList.size();i++) {
//					//插入表名管理表
//					SysTable newTable = new SysTable();
//					newTable.setTableid(UUID.randomUUID().toString());
//					if (tableList.get(i).getTabletype().equals("01")) {
//						newTable.setTablelabel(templet.getTempletname()+ "_案卷级");
//					}
//					else {
//						newTable.setTablelabel(templet.getTempletname() + "_文件级");
//					}
//					newTable.setTablename("A_" + templet.getTempletid().substring(0, 8) + "_" + tableList.get(i).getTabletype());
//					newTable.setTempletid(templet.getTempletid());
//					newTable.setTabletype(tableList.get(i).getTabletype());
//					//插入
//					tableDao.insertSelective(newTable);
//					
//					//根据参照模板的字段，得到创建新表的sql语句
//					SysTempletfieldExample stfe = new SysTempletfieldExample();
//					stfe.createCriteria().andTableidEqualTo(tableList.get(i).getTableid());
//					List<SysTempletfield> templetfieldList = templetfieldDao.selectByExample(stfe);
//					if (null != templetfieldList && !"".equals(templetfieldList)) {
//						String sql = createSql(templetfieldList);
//						sql = "create table " + newTable.getTablename() + " (" + sql + ")";
//						dynamicDao.postUpdate(sql.toString());
//						
//						//将模板字段，作为新表的字段插入字段管理表
//						for (int j=0;j<templetfieldList.size();j++) {
//							SysTempletfield templetfield = templetfieldList.get(j);
//							templetfield.setTempletfieldid(UUID.randomUUID().toString());
//							templetfield.setTableid(newTable.getTableid());
//							templetfieldDao.insertSelective(templetfield);
//						}
//						//判断字段是否有代码
//						if (templetfield.getIscode() == 1) {
//							//得到代码
//							SysCodeExample sce = new SysCodeExample();
//							sce.createCriteria().andTempletfieldidEqualTo(templetfieldList.get(j).getTempletfieldid());
//							
//							List<SysCode> codeList = codeDao.selectByExample(sce);
//							if (codeList.size() > 0) {
//								for (int z=0;z<codeList.size();z++) {
//									SysCode code = codeList.get(z);
//									code.setCodeid(UUID.randomUUID().toString());
//									code.setTempletfieldid(templetfield.getTempletfieldid());
//									codeDao.insertSelective(code);
//								}
//							}
//						}
//					}
//				}
//			}
//			else {
//				//如果是文件档案，取得文件模板对应的表
//				SysTableExample ste = new SysTableExample();
//				ste.createCriteria().andTempletidEqualTo("2");
//				//得到参照的基础模板
//				List<SysTable> tableList = tableDao.selectByExample(ste);
//				for (int i=0;i<tableList.size();i++) {
//					//插入表名管理表
//					SysTable newTable = new SysTable();
//					newTable.setTableid(UUID.randomUUID().toString());
//					newTable.setTablelabel(templet.getTempletname() + "_文件级");
//					newTable.setTablename("F_" + templet.getTempletid().substring(0, 8) + "_" + tableList.get(i).getTabletype());
//					newTable.setTempletid(templet.getTempletid());
//					newTable.setTabletype(tableList.get(i).getTabletype());
//					//插入
//					tableDao.insertSelective(newTable);
//					
//					//根据参照模板的字段，得到创建新表的sql语句
//					SysTempletfieldExample stfe = new SysTempletfieldExample();
//					stfe.createCriteria().andTableidEqualTo(tableList.get(i).getTableid());
//					List<SysTempletfield> templetfieldList = templetfieldDao.selectByExample(stfe);
//					if (null != templetfieldList && !"".equals(templetfieldList)) {
//						String sql = createSql(templetfieldList);
//						sql = "create table " + newTable.getTablename() + " (" + sql + ")";
//						dynamicDao.postUpdate(sql.toString());
//						
//						//将模板字段，作为新表的字段插入字段管理表
//						for (int j=0;j<templetfieldList.size();j++) {
//							SysTempletfield templetfield = templetfieldList.get(j);
//							templetfield.setTempletfieldid(UUID.randomUUID().toString());
//							templetfield.setTableid(newTable.getTableid());
//							templetfieldDao.insertSelective(templetfield);
//							
//							//判断字段是否有代码
//							if (templetfield.getIscode() == 1) {
//								//得到代码
//								SysCodeExample sce = new SysCodeExample();
//								sce.createCriteria().andTempletfieldidEqualTo(templetfieldList.get(j).getTempletfieldid());
//								
//								List<SysCode> codeList = codeDao.selectByExample(sce);
//								if (codeList.size() > 0) {
//									for (int z=0;z<codeList.size();z++) {
//										SysCode code = codeList.get(z);
//										code.setCodeid(UUID.randomUUID().toString());
//										code.setTempletfieldid(templetfield.getTempletfieldid());
//										codeDao.insertSelective(code);
//									}
//								}
//							}
//						}
//					}
//				}
//			}
			return true;
	}
	
	private String createSql(List<SysTempletfield> fieldList) {
		if (null == fieldList || 0 >= fieldList.size()) {
			return "";
		}
		StringBuffer sql = new StringBuffer();
		for (int i=0;i<fieldList.size();i++) {
			SysTempletfield templetfield = fieldList.get(i);
			String enname = templetfield.getEnglishname();
			String type = templetfield.getFieldtype();
			int size = templetfield.getFieldsize();
			int ispk = templetfield.getIspk();
			int isrequire = templetfield.getIsrequire();
			
			
			
			sql.append(enname).append(" ");
			sql.append(type).append("(").append(size).append(")");
			
			if (1 == isrequire) {
				sql.append(" not null");
			}
			
			if (1 == ispk) {
				sql.append(",primary key (").append(enname).append(")");
			}
			sql.append(",");
		}
		String str = sql.substring(0,sql.length() - 1);
		return str;
	}
	/**
	 * 创建模板的同时，创建档案树节点、节点与模板的对应。
	 * @param templet
	 * @return
	 */
	private boolean createTree(SysTemplet templet) {
		String templetType = templet.getTemplettype();
		
		if ("A".equals(templetType) || "F".equals(templetType)) {
			SysTree tree = new SysTree();
			tree.setTreeid(UUID.randomUUID().toString());
			tree.setTreename(templet.getTempletname());
			tree.setParentid("0");
			
			String node = tree.getTreeid().substring(0, 8);
			
			tree.setTreenode("0#" + node);
			tree.setTreetype("F");
			treeDao.insertSelective(tree);
			
			SysTreeTemplet treeTemplet = new SysTreeTemplet();
			treeTemplet.setTreeTempletId(UUID.randomUUID().toString());
			treeTemplet.setTreeid(tree.getTreeid());
			treeTemplet.setTempletid(templet.getTempletid());
			treetempletDao.insertSelective(treeTemplet);
			
			return true;
		}
		
		return false;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.archive.service.itf.ITempletService#insertTemplet(com.yapu.archive.entity.SysTemplet, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public boolean insertTemplet(SysTemplet templet,String copyTempletid) {
		//新建模板后，要创建真实表
		if (null != templet) {
			try {
				//1 得到参考模板的类型，创建模板
				SysTempletExample example = new SysTempletExample();
				example.createCriteria().andTempletidEqualTo(copyTempletid);
				List<SysTemplet> copyList = templetDao.selectByExample(example);
				if (copyList.size() != 1) {
					return false;
				}
				String type = copyList.get(0).getTemplettype();
				if ("CA".equals(type)) {
					type = "A";
				}
				else if("CF".equals(type)) {
					type = "F";
				}
				templet.setTemplettype(type);
				templetDao.insertSelective(templet);
				//2 创建表名管理表\ 创建真实表 \插入模板的字段 \插入代码表
				createTable(templet,copyTempletid);
				//3创建档案节点\ 档案节点与模板关联
				createTree(templet);
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
	 * @see com.yapu.system.service.itf.ITempletService#selectByPrimaryKey(java.lang.String)
	 */
	public SysTemplet selectByPrimaryKey(String templetID) {
		return templetDao.selectByPrimaryKey(templetID);
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.archive.service.itf.ITempletService#selectByWhereNotPage(com.yapu.archive.entity.SysTempletExample)
	 */
	@SuppressWarnings("unchecked")
	public List<SysTemplet> selectByWhereNotPage(SysTempletExample set) {
		if (null != set ) {
			return templetDao.selectByExample(set);
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ITempletService#updateTemplet(com.yapu.system.entity.SysTemplet)
	 */
	public int updateTemplet(SysTemplet templet) {
		if (null != templet) {
			try {
				int n = templetDao.updateByPrimaryKeySelective(templet);
				if (n > 0) {
					//更新table表
					SysTableExample ste = new SysTableExample();
					ste.createCriteria().andTempletidEqualTo(templet.getTempletid());
					List<SysTable> tableList = tableDao.selectByExample(ste);
					for (int i =0;i<tableList.size();i++) {
						SysTable table = tableList.get(i);
						if (table.getTablename().substring(0,1).equals("A")) {
							if (table.getTabletype().equals("01")) {
								table.setTablelabel(templet.getTempletname() + "_案卷级");
							}
							else {
								table.setTablelabel(templet.getTempletname() + "_文件级");
							}
						}
						else {
							table.setTablelabel(templet.getTempletname() + "_文件级");
						}
						tableDao.updateByPrimaryKeySelective(table);
					}
				}
				return n;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ITempletService#updateTemplet(java.util.List)
	 */
	public int updateTemplet(List<SysTemplet> templetList) {
		int num = 0;
		if (null != templetList && 0 < templetList.size()) {
			for (int i=0;i<templetList.size();i++) {
				try {
					updateTemplet(templetList.get(i));
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
	public void setTempletDao(SysTempletDAO templetDao) {
		this.templetDao = templetDao;
	}
	public void setTempletfieldDao(SysTempletfieldDAO templetfieldDao) {
		this.templetfieldDao = templetfieldDao;
	}
	public void setTreetempletDao(SysTreeTempletDAO treetempletDao) {
		this.treetempletDao = treetempletDao;
	}
	public void setTableDao(SysTableDAO tableDao) {
		this.tableDao = tableDao;
	}
	public void setDocDao(SysDocDAO docDao) {
		this.docDao = docDao;
	}
	public void setCodeDao(SysCodeDAO codeDao) {
		this.codeDao = codeDao;
	}
	public void setAccounttreeDao(SysAccountTreeDAO accounttreeDao) {
		this.accounttreeDao = accounttreeDao;
	}
	public void setOrgtreeDao(SysOrgTreeDAO orgtreeDao) {
		this.orgtreeDao = orgtreeDao;
	}
	public void setTreeDao(SysTreeDAO treeDao) {
		this.treeDao = treeDao;
	}
	public void setCollectionDao(ArCollectionDAO collectionDao) {
		this.collectionDao = collectionDao;
	}
	public void setReleaseDao(ArReleaseDAO releaseDao) {
		this.releaseDao = releaseDao;
	}
	public void setDynamicDao(DynamicDAO dynamicDao) {
		this.dynamicDao = dynamicDao;
	}
	
}
