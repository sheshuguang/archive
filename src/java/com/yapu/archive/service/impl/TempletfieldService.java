package com.yapu.archive.service.impl;

import java.util.List;

import com.yapu.archive.dao.itf.DynamicDAO;
import com.yapu.archive.dao.itf.SysCodeDAO;
import com.yapu.archive.dao.itf.SysTableDAO;
import com.yapu.archive.dao.itf.SysTempletDAO;
import com.yapu.archive.dao.itf.SysTempletfieldDAO;
import com.yapu.archive.entity.SysCode;
import com.yapu.archive.entity.SysCodeExample;
import com.yapu.archive.entity.SysTable;
import com.yapu.archive.entity.SysTemplet;
import com.yapu.archive.entity.SysTempletfield;
import com.yapu.archive.entity.SysTempletfieldExample;
import com.yapu.archive.service.itf.ITempletfieldService;

public class TempletfieldService implements ITempletfieldService {
	
	private SysTempletfieldDAO templetfieldDao;
	private SysCodeDAO codeDao;
	private SysTableDAO tableDao;
	private DynamicDAO dynamicDao;
	private SysTempletDAO templetDao;
	
	
	/**
	 * 删除字段时，先删除字段的外键关联
	 * @param templetfieldID
	 * @return
	 */
	private Boolean beforeDelete(String templetfieldID) {
		try {
			//1、删除字段与代码表关联
			SysCodeExample codeExample = new SysCodeExample();
			codeExample.createCriteria().andTempletfieldidEqualTo(templetfieldID);
			codeDao.deleteByExample(codeExample);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ITempletfieldService#deleteTempletfield(java.lang.String)
	 */
	public int deleteTempletfield(String templetfieldID) {
		if (null != templetfieldID) {
			if (beforeDelete(templetfieldID)) {
				//判断是否应该删除真实表字段
				SysTempletfield field = templetfieldDao.selectByPrimaryKey(templetfieldID);
				SysTable table = tableDao.selectByPrimaryKey(field.getTableid());
				SysTemplet templet = templetDao.selectByPrimaryKey(table.getTempletid());
				if (!"CA".equals(templet.getTemplettype()) && !"CF".equals(templet.getTemplettype())) {
					StringBuffer sql = new StringBuffer();
//					ALTER TABLE a_cb34ff4_02 DROP FieldName
					sql.append("alter table ");
					sql.append(table.getTablename());
					sql.append(" drop ");
					sql.append(field.getEnglishname());
					dynamicDao.update(sql.toString());
				}
				return templetfieldDao.deleteByPrimaryKey(templetfieldID);
			}
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ITempletfieldService#deleteTempletfield(com.yapu.system.entity.SysTempletfield)
	 */
	public int deleteTempletfield(SysTempletfield templetfield) {
		if (null != templetfield) {
			return deleteTempletfield(templetfield.getTempletfieldid());
		}
		return 0;
	}
	/**
	 * 创建字段时，添加到真实表
	 * @param templetfield
	 * @return
	 */
	private boolean createField(SysTempletfield templetfield) {
		//得到表名
		SysTable table = tableDao.selectByPrimaryKey(templetfield.getTableid());
		
		//判断是什么模板，如果是基础模板（虚拟的表），不更新真实表
		SysTemplet templet = templetDao.selectByPrimaryKey(table.getTempletid());
		if ("CA".equals(templet.getTemplettype()) || "CF".equals(templet.getTemplettype())) {
			return false;
		}
		
		if (null != table) {
			StringBuffer sql = new StringBuffer();
//			ALTER TABLE a_d004359_02 ADD FieldName VARCHAR(30) 
			sql.append("alter table ");
			sql.append(table.getTablename());
			sql.append(" add ");
			sql.append(templetfield.getEnglishname());
			sql.append(" ");
			sql.append(templetfield.getFieldtype());
			sql.append("(");
			sql.append(templetfield.getFieldsize());
			sql.append(")");
			dynamicDao.update(sql.toString());
			return true;
		}
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ITempletfieldService#insertTempletfield(com.yapu.system.entity.SysTempletfield)
	 */
	public Boolean insertTempletfield(SysTempletfield templetfield) {
		if (null != templetfield) {
			try {
				//为真实表创建字段
				createField(templetfield);
				templetfieldDao.insertSelective(templetfield);
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
	 * @see com.yapu.system.service.itf.ITempletfieldService#insertTempletfield(java.util.List)
	 */
	public Boolean insertTempletfield(List<SysTempletfield> templetfieldList) {
		if (null != templetfieldList && templetfieldList.size() >0) {
			for (int i=0;i<templetfieldList.size();i++) {
				insertTempletfield(templetfieldList.get(i));
			}
			return true;
		}
		return false;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ITempletfieldService#selectByPrimaryKey(java.lang.String)
	 */
	public SysTempletfield selectByPrimaryKey(String templetfieldID) {
		if (null != templetfieldID ) {
			return templetfieldDao.selectByPrimaryKey(templetfieldID);
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ITempletfieldService#selectByWhereNotPage(com.yapu.system.entity.SysTempletfieldExample)
	 */
	public List<SysTempletfield> selectByWhereNotPage(SysTempletfieldExample ste) {
		return templetfieldDao.selectByExample(ste);
	}
	
	private boolean updateField(SysTempletfield field) {
		//根据字段id得到原始实体
		SysTempletfield oldField = templetfieldDao.selectByPrimaryKey(field.getTempletfieldid());
		//得到表名
		SysTable table = tableDao.selectByPrimaryKey(oldField.getTableid());
		//判断是什么模板，如果是基础模板（虚拟的表），不更新真实表
		SysTemplet templet = templetDao.selectByPrimaryKey(table.getTempletid());
		if ("CA".equals(templet.getTemplettype()) || "CF".equals(templet.getTemplettype())) {
			return false;
		}
		
		if (null != table) {
			StringBuffer sql = new StringBuffer();
//			ALTER TABLE a_d004359_02 CHANGE DDF DDFg VARCHAR(300)
			sql.append("alter table ");
			sql.append(table.getTablename());
			sql.append(" CHANGE ");
			sql.append(oldField.getEnglishname());
			sql.append(" ");
			sql.append(oldField.getEnglishname());
			sql.append(" ");
			sql.append(oldField.getFieldtype());
			sql.append("(");
			sql.append(field.getFieldsize());
			sql.append(")");
			dynamicDao.update(sql.toString());

			return true;
		}
		return false;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ITempletfieldService#updateTempletfield(com.yapu.system.entity.SysTempletfield)
	 */
	public int updateTempletfield(SysTempletfield templetfield) {
		if (null != templetfield) {
//			try {
				//更新真实表字段
				if (updateField(templetfield)) {
					return templetfieldDao.updateByPrimaryKeySelective(templetfield);
				}
//			} catch (Exception e) {
//				System.out.println(e.getMessage());
//				return 0;
//			}
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ITempletfieldService#updateTempletfield(java.util.List)
	 */
	public int updateTempletfield(List<SysTempletfield> templetfieldList) {
		int num =0;
		if (null != templetfieldList && templetfieldList.size()>0) {
			for (int i=0;i<templetfieldList.size();i++) {
				updateTempletfield(templetfieldList.get(i));
				num += 1; 
			}
			return num;
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.archive.service.itf.ITempletfieldService#getTempletfieldOfCode(java.lang.String)
	 */
	public List<SysCode> getTempletfieldOfCode(String templetfieldid) {
		if (null == templetfieldid && "".equals(templetfieldid)) {
			return null;
		}
		SysCodeExample example = new SysCodeExample();
		example.createCriteria().andTempletfieldidEqualTo(templetfieldid);
		example.setOrderByClause("codeorder");
		List<SysCode> codeList = codeDao.selectByExample(example);
		
		return codeList;
	}
	
	
	public void setTempletfieldDao(SysTempletfieldDAO templetfieldDao) {
		this.templetfieldDao = templetfieldDao;
	}
	public void setCodeDao(SysCodeDAO codeDao) {
		this.codeDao = codeDao;
	}
	public void setTableDao(SysTableDAO tableDao) {
		this.tableDao = tableDao;
	}
	public void setDynamicDao(DynamicDAO dynamicDao) {
		this.dynamicDao = dynamicDao;
	}

	public void setTempletDao(SysTempletDAO templetDao) {
		this.templetDao = templetDao;
	}

}
