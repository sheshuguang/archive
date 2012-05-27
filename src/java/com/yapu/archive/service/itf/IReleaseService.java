package com.yapu.archive.service.itf;
/**
 * 发布功能服务类
 * @author wangf
 * @date	2010-11-19
 */

import java.util.List;

import com.yapu.archive.entity.ArRelease;

public interface IReleaseService {

	//===================发布基本操作=====================
	/**
	 * 插入新发布
	 * @param 	release			发布实体对象
	 * @return					true or false					
	 */
	Boolean insertRelease(ArRelease release);
	/**
	 * 批量插入新发布
	 * @param releaseList		发布实体对象List集合
	 * @return
	 */
	Boolean insertRelease(List<ArRelease> releaseList);
	/**
	 * 更新发布。传入的实体对象ID为条件更新发布。
	 * @param 	release			发布实体对象
	 * @return					
	 */
	int updateRelease(ArRelease release);
	/**
	 * 删除发布。
	 * @param releaseID			发布ID。
	 * @return					
	 */
	int deleteRelease(String releaseID);
	/**
	 * 删除发布
	 * @param release			发布实体对象
	 * @return
	 */
	int deleteRelease(ArRelease release);
	/**
	 * 批量删除发布。
	 * @param releaseIDList		发布ID的List集合。例如[1,2,3]
	 * @return
	 */
	int deleteRelease(List<String> releaseIDList);
	/**
	 * 按主键值查询
	 * @param releaseID			发布id
	 * @return					返回实体对象
	 */
	ArRelease selectByPrimaryKey(String releaseID);
	/**
	 * 不分页按实体类的属性查询。操作符是like。null、“”不查
	 * @param release
	 * @return
	 */
	List<ArRelease> selectByWhereNotPage(ArRelease release);
	/**
	 * 分页按实体类的属性值查询
	 * @param release			实体对象
	 * @return
	 */
	List<ArRelease> selectByWherePage(ArRelease release);
	/**
	 * 得到总行数
	 * @param release			发布实体。查询是like还是=？
	 * @return
	 */
	int rowCount(ArRelease release);
	
	//========================================================
	
	//===========发布关联的操作=================================
}
