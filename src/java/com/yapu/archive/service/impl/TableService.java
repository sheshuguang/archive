package com.yapu.archive.service.impl;

import java.util.List;

import com.yapu.archive.dao.itf.SysTableDAO;
import com.yapu.archive.dao.itf.SysTempletfieldDAO;
import com.yapu.archive.entity.SysTable;
import com.yapu.archive.entity.SysTableExample;
import com.yapu.archive.entity.SysTempletfield;
import com.yapu.archive.entity.SysTempletfieldExample;
import com.yapu.archive.service.itf.ITableService;

public class TableService implements ITableService {
	
	private SysTableDAO tableDao;
	private SysTempletfieldDAO templetfieldDao;
	
	public SysTable selectByPrimaryKey(String tableID) {
		if (null != tableID && !"".equals(tableID)) {
			return tableDao.selectByPrimaryKey(tableID);
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ITableService#selectByWhereNotPage(com.yapu.system.entity.SysTable)
	 */
	public List<SysTable> selectByWhereNotPage(SysTableExample ste) {
			return tableDao.selectByExample(ste);
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ITableService#getTableOfTempletfield(com.yapu.system.entity.SysTable)
	 */
	public List<SysTempletfield> getTableOfTempletfield(SysTable table) {
		if (null != table) {
			SysTempletfieldExample templetfieldExample = new SysTempletfieldExample();
			templetfieldExample.createCriteria().andTableidEqualTo(table.getTableid());
			return templetfieldDao.selectByExample(templetfieldExample);
		}
		return null;
	}
	public void setTableDao(SysTableDAO tableDao) {
		this.tableDao = tableDao;
	}
	public void setTempletfieldDao(SysTempletfieldDAO templetfieldDao) {
		this.templetfieldDao = templetfieldDao;
	}
}
