package com.yapu.archive.service.itf;

import java.util.List;

import com.yapu.archive.entity.SysCode;
import com.yapu.archive.entity.SysTempletfield;
import com.yapu.archive.entity.SysTempletfieldExample;

public interface ITempletfieldService {

	//=============增、删、改、查==================
	/**
	 * 插入新模板条目
	 * @param templetfield			模板条目实体对象
	 * @return				正确处理返回true，失败返回false
	 */
	Boolean insertTempletfield(SysTempletfield templetfield);
	/**
	 * 批量插入模板条目
	 * @param templetfieldList		模板条目实体对象的list集合
	 * @return
	 */
	Boolean insertTempletfield(List<SysTempletfield> templetfieldList);
	/**
	 * 更新模板条目。以参数实体对象的id为条件更新。
	 * @param templetfield			模板条目实体对象
	 * @return				更新的数量
	 */
	int updateTempletfield(SysTempletfield templetfield);
	/**
	 * 批量更新模板条目。以组实体对象的ID值为条件更新。
	 * @param templetfieldList		模板条目实体对象的List集合。
	 * @return
	 */
	int updateTempletfield(List<SysTempletfield> templetfieldList);
	/**
	 * 删除模板条目。
	 * @param templetfieldID			模板条目ID
	 * @return
	 */
	int deleteTempletfield(String templetfieldID);
	/**
	 * 删除模板条目。
	 * @param templetfield			模板条目实体对象
	 * @return
	 */
	int deleteTempletfield(SysTempletfield templetfield);
	/**
	 * 按主键值查找模板条目。
	 * @param templetfieldID			模板条目id
	 * @return
	 */
	SysTempletfield selectByPrimaryKey(String templetfieldID);
	/**
	 * 不分页查询
	 * @param templetfield			
	 * @return
	 */
	List<SysTempletfield> selectByWhereNotPage(SysTempletfieldExample ste);
	
	//===========================================
	
	//============相关的操作=====================
	
	/**
	 * 查询字段代码
	 * @param templetfieldid
	 */
	List<SysCode> getTempletfieldOfCode(String templetfieldid); 
}
