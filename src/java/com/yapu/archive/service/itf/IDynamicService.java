package com.yapu.archive.service.itf;

import java.util.HashMap;
import java.util.List;

import com.yapu.archive.entity.DynamicExample;
import com.yapu.archive.entity.SysTempletfield;

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

    /**
     * insert到动态档案表
     * @param list
     * @return
     */
    boolean insert(List<HashMap<String,String>> list,String tableName,List<SysTempletfield> fieldList);

    boolean update(List<HashMap<String,String>> list,String tableName,List<SysTempletfield> fieldList);

    /**
     * 执行传入的sql 进行更新
     * @param sqlList
     * @return
     */
	boolean update(List<String> sqlList);

    /**
     * 执行传入的sql语句进行插入
     * @param sqlList
     * @return
     */
	boolean insert(List<String> sqlList);
	
	int delete(String sql);
	
}
