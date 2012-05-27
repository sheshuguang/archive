package com.yapu.archive.service.itf;

import java.util.List;

import com.yapu.archive.entity.SysTable;
import com.yapu.archive.entity.SysTableExample;
import com.yapu.archive.entity.SysTempletfield;

public interface ITableService {

	//===================表名管理表基本操作=====================
	/**
	 * 插入新表名
	 * @param 	table			表名实体对象
	 * @return					true or false					
	 */
//	Boolean insertTable(SysTable table);
	/**
	 * 更新表名。传入的实体对象ID为条件更新表名。
	 * @param 	table			表名实体对象
	 * @return					
	 */
//	int updateTable(SysTable table);
	/**
	 * 删除表名。
	 * @param tableID			表名ID。
	 * @return					
	 */
//	int deleteTable(String tableID);
	/**
	 * 删除表名
	 * @param table			表名实体对象
	 * @return
	 */
//	int deleteTable(SysTable table);
	/**
	 * 按主键值查询
	 * @param tableID			表名id
	 * @return					返回实体对象
	 */
	SysTable selectByPrimaryKey(String tableID);
	/**
	 * 不分页按实体类的属性查询。操作符是like。null、“”不查
	 * @param table
	 * @return
	 */
	List<SysTable> selectByWhereNotPage(SysTableExample ste);
	
	//========================================================
	
	//===========表名关联的操作=================================
	/**
	 * 通过表，得到表字段集合
	 * @param	table
	 * @return
	 */
	List<SysTempletfield> getTableOfTempletfield(SysTable table);
}
