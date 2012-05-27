package com.yapu.system.service.itf;
/**
 * 日志服务类。包括登录日志、操作日志、错误日志  三种日志。
 * @author wangf
 * @date	2010-11-19
 *
 */

import java.util.List;

import com.yapu.system.entity.SysErrorlog;
import com.yapu.system.entity.SysLoginlog;
import com.yapu.system.entity.SysOperatelog;

public interface ILogService {

	//===================登录日志基本操作=====================
	/**
	 * 插入新登录日志
	 * @param 	loginlog			登录日志实体对象
	 * @return					true or false					
	 */
	Boolean insertLoginlog(SysLoginlog loginlog);
	/**
	 * 删除登录日志。
	 * @param loginlogID			登录日志ID。
	 * @return					
	 */
	int deleteLoginlog(String loginlogID);
	/**
	 * 删除登录日志
	 * @param loginlog			登录日志实体对象
	 * @return
	 */
	int deleteLoginlog(SysLoginlog loginlog);
	/**
	 * 批量删除登录日志。
	 * @param loginlogIDList		登录日志ID的List集合。例如[1,2,3]
	 * @return
	 */
	int deleteLoginlog(List<String> loginlogIDList);
	/**
	 * 按主键值查询
	 * @param loginlogID			登录日志id
	 * @return					返回实体对象
	 */
	SysLoginlog selectLoginlogByPrimaryKey(String loginlogID);
	/**
	 * 按主键值查询。
	 * @param loginlogIDList		登录日志id的List集合
	 * @return
	 */
	List<SysLoginlog> selectLoginlogByPrimaryKey(List<String> loginlogIDList);
	/**
	 * 不分页按实体类的属性查询。操作符是like。null、“”不查
	 * @param loginlog
	 * @return
	 */
	List<SysLoginlog> selectLoginlogByWhereNotPage(SysLoginlog loginlog);
	/**
	 * 分页按实体类的属性值查询
	 * @param loginlog			实体对象
	 * @return
	 */
	List<SysLoginlog> selectLoginlogByWherePage(SysLoginlog loginlog);
	/**
	 * 得到总行数
	 * @param loginlog			登录日志实体。查询是like还是=？
	 * @return
	 */
	int rowLoginlogCount(SysLoginlog loginlog);
	
	//========================================================
	
	//===================操作日志基本操作=====================
	/**
	 * 插入新操作日志
	 * @param 	operatelog			操作日志实体对象
	 * @return					true or false					
	 */
	Boolean insertOperatelog(SysOperatelog operatelog);
	/**
	 * 删除操作日志。
	 * @param operatelogID			操作日志ID。
	 * @return					
	 */
	int deleteOperatelog(String operatelogID);
	/**
	 * 删除操作日志
	 * @param operatelog			操作日志实体对象
	 * @return
	 */
	int deleteOperatelog(SysOperatelog operatelog);
	/**
	 * 批量删除操作日志。
	 * @param operatelogIDList		操作日志ID的List集合。
	 * @return
	 */
	int deleteOperatelog(List<String> operatelogIDList);
	/**
	 * 按主键值查询
	 * @param operatelogID			操作日志id
	 * @return					返回实体对象
	 */
	SysOperatelog selectOperatelogByPrimaryKey(String operatelogID);
	/**
	 * 按主键值查询。
	 * @param operatelogIDList		操作日志id的List集合
	 * @return
	 */
	List<SysOperatelog> selectOperatelogByPrimaryKey(List<String> operatelogIDList);
	/**
	 * 不分页按实体类的属性查询。操作符是like。null、“”不查
	 * @param operatelog
	 * @return
	 */
	List<SysOperatelog> selectOperatelogByWhereNotPage(SysOperatelog operatelog);
	/**
	 * 分页按实体类的属性值查询
	 * @param operatelog			实体对象
	 * @return
	 */
	List<SysOperatelog> selectOperatelogByWherePage(SysOperatelog operatelog);
	/**
	 * 得到总行数
	 * @param operatelog			操作日志实体。
	 * @return
	 */
	int rowOperatelogCount(SysOperatelog operatelog);
	
	
	//===================错误日志基本操作=====================
	/**
	 * 插入新错误日志
	 * @param 	errorlog			错误日志实体对象
	 * @return					true or false					
	 */
	Boolean insertErrorlog(SysErrorlog errorlog);
	/**
	 * 删除错误日志。
	 * @param errorlogID			错误日志ID。
	 * @return					
	 */
	int deleteErrorlog(String errorlogID);
	/**
	 * 删除错误日志
	 * @param errorlog			错误日志实体对象
	 * @return
	 */
	int deleteErrorlog(SysErrorlog errorlog);
	/**
	 * 批量删除错误日志。
	 * @param errorlogIDList		错误日志ID的List集合。例如[1,2,3]
	 * @return
	 */
	int deleteErrorlog(List<String> errorlogIDList);
	/**
	 * 按主键值查询
	 * @param errorlogID			错误日志id
	 * @return					返回实体对象
	 */
	SysErrorlog selectErrorlogByPrimaryKey(String errorlogID);
	/**
	 * 按主键值查询。
	 * @param errorlogIDList		错误日志id的List集合
	 * @return
	 */
	List<SysErrorlog> selectErrorlogByPrimaryKey(List<String> errorlogIDList);
	/**
	 * 不分页按实体类的属性查询。操作符是like。null、“”不查
	 * @param errorlog
	 * @return
	 */
	List<SysErrorlog> selectErrorlogByWhereNotPage(SysErrorlog errorlog);
	/**
	 * 分页按实体类的属性值查询
	 * @param errorlog			实体对象
	 * @return
	 */
	List<SysErrorlog> selectErrorlogByWherePage(SysErrorlog errorlog);
	/**
	 * 得到总行数
	 * @param errorlog			错误日志实体。查询是like还是=？
	 * @return
	 */
	int rowCount(SysErrorlog errorlog);
	
	//========================================================
}
