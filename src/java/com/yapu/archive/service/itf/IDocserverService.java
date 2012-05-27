package com.yapu.archive.service.itf;

import java.util.List;

import com.yapu.archive.entity.SysDocserver;
import com.yapu.archive.entity.SysDocserverExample;

public interface IDocserverService {

	//=============增、删、改、查==================
	/**
	 * 插入新文件服务器
	 * @param docserver			文件服务器实体对象
	 * @return				正确处理返回true，失败返回false
	 */
	Boolean insertDocserver(SysDocserver docserver);
	/**
	 * 更新文件服务器。以参数实体对象的id为条件更新。
	 * @param docserver			文件服务器实体对象
	 * @return				更新的数量
	 */
	int updateDocserver(SysDocserver docserver);
	/**
	 * 更新服务器。
	 * @param record		实体对象
	 * @param example		条件
	 * @return
	 */
	int updateDocserver(SysDocserver record, SysDocserverExample example);
	/**
	 * 删除文件服务器。
	 * 删除服务器配置信息，并不删除电子文件。方便换服务器。需要开发重新配置没有服务器的电子文件。
	 * @param docserverID			文件服务器ID
	 * @return
	 */
	int deleteDocserver(String docserverID);
	/**
	 * 删除文件服务器。
	 * @param docserver			文件服务器实体对象
	 * @return
	 */
	int deleteDocserver(SysDocserver docserver);
	/**
	 * 按主键值查找文件服务器。
	 * @param docserverID			文件服务器id
	 * @return
	 */
	SysDocserver selectByPrimaryKey(String docserverID);
	/**
	 * 不分页查询.直接返回全部列表。
	 * @param docserver			文件服务器实体对象
	 * @return
	 */
	List<SysDocserver> selectByWhereNotPage(SysDocserverExample example);
	
	//===========================================
	
	//============相关的操作=====================
}
