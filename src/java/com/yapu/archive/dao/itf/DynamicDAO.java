package com.yapu.archive.dao.itf;

import java.util.List;

import com.yapu.archive.entity.DynamicExample;

public interface DynamicDAO {

	List selectByExample(DynamicExample example);
	/**
	 * 执行create、update等操作
	 * @param sql
	 * @return
	 */
	boolean update(String sql);
	
	boolean insert(String sql);
	
	int delete(String sql);
}
