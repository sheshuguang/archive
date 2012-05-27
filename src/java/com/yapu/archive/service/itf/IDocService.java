package com.yapu.archive.service.itf;

import java.util.List;

import com.yapu.archive.entity.SysDoc;
import com.yapu.archive.entity.SysDocExample;

public interface IDocService {

	//===================电子文件基本操作=====================
	/**
	 * 插入新电子文件
	 * @param 	doc			电子文件实体对象
	 * @return					true or false					
	 */
	Boolean insertDoc(SysDoc doc);
	/**
	 * 更新电子文件。传入的实体对象ID为条件更新电子文件。
	 * @param 	doc			电子文件实体对象
	 * @return					
	 */
	int updateDoc(SysDoc doc);
	/**
	 * 删除电子文件。
	 * @param docID			电子文件ID。
	 * @return					
	 */
	int deleteDoc(String docID);
	/**
	 * 删除电子文件。 
	 * @param doc			电子文件实体对象
	 * @return
	 */
	int deleteDoc(SysDoc doc);
	/**
	 * 批量删除电子文件。
	 * @param docIDList		电子文件ID的List集合。
	 * @return
	 */
	int deleteDoc(List<String> docIDList);
	/**
	 * 按主键值查询
	 * @param docID			电子文件id
	 * @return					返回实体对象
	 */
	SysDoc selectByPrimaryKey(String docID);
	/**
	 * 不分页按实体类的属性查询。操作符是like。null、“”不查
	 * @param example
	 * @return
	 */
	List<SysDoc> selectByWhereNotPage(SysDocExample example);
	
	/**
	 * 得到总行数
	 * @param example
	 * @return
	 */
	int rowCount(SysDocExample example);
	
	//========================================================
	
	//===========电子文件关联的操作=================================
}
