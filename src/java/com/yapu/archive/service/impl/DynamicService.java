package com.yapu.archive.service.impl;

import java.util.List;

import com.yapu.archive.dao.impl.DynamicDAOImpl;
import com.yapu.archive.entity.DynamicExample;
import com.yapu.archive.service.itf.IDynamicService;

public class DynamicService implements IDynamicService {
	
	private DynamicDAOImpl dynamicDao;

	@SuppressWarnings("unchecked")
	public List selectByExample(DynamicExample example) {
		return dynamicDao.selectByExample(example);
	}
	
	public boolean insert(String sql) {
		return dynamicDao.insert(sql);
	}

	public boolean update(String sql) {
		return dynamicDao.update(sql);
	}
	
	public int delete(String sql) {
		return dynamicDao.delete(sql);
	}
	
	public void setDynamicDao(DynamicDAOImpl dynamicDao) {
		this.dynamicDao = dynamicDao;
	}

	
	
}
