package com.yapu.system.service.itf;

import java.util.List;

import com.yapu.system.entity.SysConfig;
import com.yapu.system.entity.SysConfigExample;

/**
 * 系统配置类服务
 * @author 	wangf
 * @date	2010-11-13
 *
 */

public interface IConfigService {
	
	//=============增、删、改、查==================
	/**
	 * 插入新配置
	 * @param	config
	 */
	Boolean insertConfig(SysConfig config);
	/**
	 * 更新配置
	 * @param config
	 * @return
	 */
	int updateConfig(SysConfig config);
	/**
	 * 批量更新配置
	 * @param orgList
	 * @return
	 */
	int updateConfig(List<SysConfig> configList);
	/**
	 * 删除配置
	 * @param configID
	 * @return
	 */
	int deleteConfig(String configID);
	/**
	 * 删除配置
	 * @param org
	 * @return
	 */
	int deleteConfig(SysConfig config);
	/**
	 * 按主键值查找账户组。
	 * @param orgID			账户组id
	 * @return
	 */
	SysConfig selectByPrimaryKey(String configID);
	/**
	 * 不分页查询
	 * @param org			账户组实体对象
	 * @return
	 */
	List<SysConfig> selectByWhereNotPage(SysConfigExample example);
	
	//===========================================

}
