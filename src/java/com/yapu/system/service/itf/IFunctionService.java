package com.yapu.system.service.itf;

import java.util.List;

import com.yapu.system.entity.SysFunction;
import com.yapu.system.entity.SysFunctionExample;

/**
 * 系统功能服务类。系统功能一般不会添加和删除。
 * @author wangf
 * @data	2010-11-13
 *
 */

public interface IFunctionService {
	/**
	 * 插入新的系统功能
	 * @param function
	 * @return
	 */
	Boolean insertFunction(SysFunction function);
	/**
	 * 更新系统功能
	 * @param function
	 * @return
	 */
	int updateFunction(SysFunction function);
	/**
	 * 删除系统功能
	 * @param function
	 * @return
	 */
	int deleteFunction(SysFunction function);
	/**
	 * 根据功能表主键，查询
	 * @param functionID
	 * @return
	 */
	SysFunction selectByPrimaryKey(String functionID);
	/**
	 * 不分页查询
	 * @param function
	 * @return
	 */
	List<SysFunction> selectByWhereNotPage(SysFunctionExample example);

}
