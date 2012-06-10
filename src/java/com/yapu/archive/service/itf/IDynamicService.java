package com.yapu.archive.service.itf;

import java.util.List;

import com.yapu.archive.entity.DynamicExample;

/**
 * 动态表专用服务接口
 * 
 * @author  Administrator
 * @date	2010-3-30
 */

public interface IDynamicService {

	/**
	 * 根据条件对象，查询
	 * @param example
	 * @return
	 */
	@SuppressWarnings("unchecked")
	List selectByExample(DynamicExample example);
	
	boolean update(String sql);
	
//	boolean insert(String sql);
	boolean insert(List<String> sqlList);
	
	int delete(String sql);
	
}
