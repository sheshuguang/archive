package com.yapu.archive.service.itf;

import java.util.HashMap;
import java.util.List;

import com.yapu.archive.entity.SysCode;
import com.yapu.archive.entity.SysCodeExample;

public interface ICodeService {
	
	//===================代码基本操作=====================
	/**
	 * 保存。参数map里包含string 和list  ，string为"inserted updated deleted"字符串。list里包含相应的code实体。因为事物的原因，
	 * 保存代码要更新templetfield，调用templetfieldservice，如果在action里调用，则没有回滚。
	 */
	Boolean save(HashMap<String,List<SysCode>> map,String templetfieldid);
	/**
	 * 插入新代码
	 * @param 	code			代码实体对象
	 * @return					true or false					
	 */
	Boolean insertCode(SysCode code);
	/**
	 * 批量插入新代码
	 * @param codeList		代码实体对象List集合
	 * @return
	 */
	Boolean insertCode(List<SysCode> codeList);
	/**
	 * 更新代码。传入的实体对象ID为条件更新代码。
	 * @param 	code			代码实体对象
	 * @return					
	 */
	int updateCode(SysCode code);
	/**
	 * 批量更新代码。
	 * @param codeList		代码实体对象的List集合。
	 * @return					
	 */
	int updateCode(List<SysCode> codeList);
	/**
	 * 删除代码。
	 * @param codeID			代码ID。
	 * @return					
	 */
	int deleteCode(String codeID);
	/**
	 * 删除代码
	 * @param code			代码实体对象
	 * @return
	 */
	int deleteCode(SysCode code);
	/**
	 * 按主键值查询
	 * @param codeID			代码id
	 * @return					返回实体对象
	 */
	SysCode selectByPrimaryKey(String codeID);
	/**
	 * 不分页按实体类的属性查询。操作符是like。null、“”不查
	 * @param code
	 * @return
	 */
	List<SysCode> selectByWhereNotPage(SysCodeExample example);
	
	//========================================================
	
	//===========代码关联的操作=================================
}
