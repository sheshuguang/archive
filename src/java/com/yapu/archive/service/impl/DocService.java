package com.yapu.archive.service.impl;
/**
 * 电子文件管理服务类
 * @author wangf
 * @date	2010-11-18
 */
import java.util.List;

import com.yapu.archive.dao.itf.DynamicDAO;
import com.yapu.archive.dao.itf.SysDocDAO;
import com.yapu.archive.dao.itf.SysTableDAO;
import com.yapu.archive.entity.SysDoc;
import com.yapu.archive.entity.SysDocExample;
import com.yapu.archive.entity.SysTable;
import com.yapu.archive.service.itf.IDocService;

public class DocService implements IDocService {
	
	private SysDocDAO docDao;
	private SysTableDAO tableDao;
	private DynamicDAO dynamicDao;

	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IDocService#deleteDoc(java.lang.String)
	 */
	public int deleteDoc(String docID) {
		if (null != docID && !"".equals(docID)) {
			//删除电子文件记录前，要删除物理文件
			SysDoc doc = docDao.selectByPrimaryKey(docID);
			if (null != doc) {
				try {
					//TODO 删除物理文件
					
					//删除文件记录
					return docDao.deleteByPrimaryKey(docID);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					return 0;
				}
			}
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IDocService#deleteDoc(com.yapu.system.entity.SysDoc)
	 */
	public int deleteDoc(SysDoc doc) {
		if (null != doc) {
			return deleteDoc(doc.getDocid());
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IDocService#deleteDoc(java.util.List)
	 */
	public int deleteDoc(List<String> docIDList) {
		if (null != docIDList && docIDList.size() >0) {
			int num=0;
			for (int i=0;i<docIDList.size();i++) {
				deleteDoc(docIDList.get(i));
				num += 1;
			}
			return num;
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IDocService#insertDoc(com.yapu.system.entity.SysDoc)
	 */
	public Boolean insertDoc(SysDoc doc) {
		if (null != doc) {
			try {
				docDao.insertSelective(doc);
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
	 * @see com.yapu.system.service.itf.IDocService#selectByPrimaryKey(java.lang.String)
	 */
	public SysDoc selectByPrimaryKey(String docID) {
		if (null != docID && !"".equals(docID)) {
			return docDao.selectByPrimaryKey(docID);
		}
		return null;
	}
	/*

	 */
	public List<SysDoc> selectByWhereNotPage(SysDocExample example) {
		return docDao.selectByExample(example);
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.IDocService#updateDoc(com.yapu.system.entity.SysDoc)
	 */
	public int updateDoc(SysDoc doc) {
		if (null != doc) {
			try {
				return docDao.updateByPrimaryKeySelective(doc);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.archive.service.itf.IDocService#updateDoc(java.util.List)
	 */
	@Override
	public int updateDoc(List<SysDoc> docList) {
		if (docList.size() >0) {
			//存储fileid有值的
			StringBuffer whereYesStr = new StringBuffer();
			StringBuffer whereNoStr = new StringBuffer();
			String tableid = "";
			for (SysDoc doc :docList) {
				try {
					tableid = doc.getTableid();
					updateDoc(doc);
					if (null == doc.getFileid() || "".equals(doc.getFileid())) {
						whereNoStr.append("'").append(doc.getFileid()).append("',");
					}
					else {
						whereYesStr.append("'").append(doc.getFileid()).append("',");
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
					return 0;
				}
			}
			if (whereYesStr.length() > 0) {
				whereYesStr.deleteCharAt(whereYesStr.length() - 1);
				updateArchiveDoc(tableid,whereYesStr.toString());
			}
			
			if (whereNoStr.length() > 0) {
				whereNoStr.deleteCharAt(whereYesStr.length() - 1);
			}
			
			return docList.size();
		}
		
		return 0;
	}
	/*
	 * 更新档案表的全文标识
	 */
	private void updateArchiveDoc(String tableid,String where) {
		//得到表名
		SysTable table = tableDao.selectByPrimaryKey(tableid);
		//创建sql语句
		StringBuffer sql = new StringBuffer();
		sql.append("update ").append(table.getTablename()).append(" set isdoc = 1 where id in (").append(where).append(" )");
		dynamicDao.update(sql.toString());
		
	}

	
	
	/*

	 */
	public int rowCount(SysDocExample example) {
		return docDao.countByExample(example);
	}
	public void setDocDao(SysDocDAO docDao) {
		this.docDao = docDao;
	}
	public void setTableDao(SysTableDAO tableDao) {
		this.tableDao = tableDao;
	}
	public void setDynamicDao(DynamicDAO dynamicDao) {
		this.dynamicDao = dynamicDao;
	}
	
}
