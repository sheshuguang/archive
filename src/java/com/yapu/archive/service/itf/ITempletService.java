package com.yapu.archive.service.itf;
/**
 *  模板服务
 * @author wangf
 * @date	2010-11-13
 *
 */


import java.util.List;

import com.yapu.archive.entity.SysTemplet;
import com.yapu.archive.entity.SysTempletExample;



public interface ITempletService {
	
	//=============增、删、改、查==================
	/**
	 * 插入新模板
	 * @param templet			模板实体对象
	 * @param copyTempletid		copy模板id
	 * @return					true or false
	 */
	boolean insertTemplet(SysTemplet templet,String copyTempletid);
	/**
	 * 更新模板。以参数实体对象的id为条件更新。
	 * @param templet			模板实体对象
	 * @return					
	 */
	int updateTemplet(SysTemplet templet);
	/**
	 * 批量更新模板。以组实体对象的ID值为条件更新。
	 * @param templetList		模板实体对象的List集合。
	 * @return
	 */
	int updateTemplet(List<SysTemplet> templetList);
	/**
	 * 删除模板。
	 * 删除模板将同时删除模板关联的 资源树、资源树与模板关联、资源树与组关联、资源树与账户关联
	 * 、表名、字段、代码
	 * @param templetID			模板ID
	 * @return
	 */
	int deleteTemplet(String templetID);
	/**
	 * 删除模板。
	 * 删除模板将同时删除模板关联的 资源树、资源树与模板关联、资源树与组关联、资源树与账户关联
	 * 、表名、字段、代码
	 * @param templet			模板实体对象
	 * @return
	 */
	int deleteTemplet(SysTemplet templet);
	/**
	 * 按主键值查找模板。
	 * @param templetID			模板id
	 * @return
	 */
	SysTemplet selectByPrimaryKey(String templetID);
	/**
	 * 不分页查询
	 * @param templet			模板实体对象
	 * @return
	 */
	List<SysTemplet> selectByWhereNotPage(SysTempletExample set);
	
	//===========================================
	
	//============组相关的操作=====================
	

}
