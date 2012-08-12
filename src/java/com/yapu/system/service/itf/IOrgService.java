package com.yapu.system.service.itf;
/**
 * 账户组操作服务类
 * 
 * @author 	wangf
 * @date	2010-11-7
 * @version 1.0
 */

import java.util.List;

import com.yapu.archive.entity.SysOrgTree;
import com.yapu.archive.entity.SysOrgTreeExample;
import com.yapu.archive.entity.SysTree;
import com.yapu.system.entity.SysAccount;
import com.yapu.system.entity.SysOrg;
import com.yapu.system.entity.SysOrgExample;
import com.yapu.system.entity.SysRole;

public interface IOrgService {
	
	
	//=============增、删、改、查==================
	/**
	 * 插入新账户组
	 * @param org			账户组实体对象
	 * @return				正确处理返回true，失败返回false
	 */
	Boolean insertOrg(SysOrg org);
	/**
	 * 批量插入账户组
	 * @param orgList		账户组实体对象的list集合
	 * @return
	 */
	Boolean insertOrg(List<SysOrg> orgList);
	/**
	 * 更新账户组。以参数实体对象的id为条件更新。
	 * @param org			账户组实体对象
	 * @return				更新的数量
	 */
	int updateOrg(SysOrg org);
	/**
	 * 批量更新账户组。以组实体对象的ID值为条件更新。
	 * @param orgList		账户组实体对象的List集合。
	 * @return
	 */
	int updateOrg(List<SysOrg> orgList);
	/**
	 * 删除账户组。
	 * @param orgID			账户组ID
	 * @return
	 */
	int deleteOrg(String orgID);
	/**
	 * 删除账户组。
	 * @param org			账户组实体对象
	 * @return
	 */
	int deleteOrg(SysOrg org);
	/**
	 * 批量删除账户组。
	 * @param orgIDList		账户组id的List集合。
	 * @return
	 */
	int deleteOrg(List<String> orgIDList);
	/**
	 * 按主键值查找账户组。
	 * @param orgID			账户组id
	 * @return
	 */
	SysOrg selectByPrimaryKey(String orgID);
	/**
	 * 不分页查询
	 * @param org			账户组实体对象
	 * @return
	 */
	List<SysOrg> selectByWhereNotPage(SysOrgExample example);
	/**
	 * 查询总行数
	 * @param example			
	 * @return
	 */
	int rowCount(SysOrgExample example);
	
	//============组相关的操作=====================
	
	//==========================组与账户操作============================//
	/**
	 * 得到组下的账户List
	 * @param org			账户组实体对象
	 * @return				账户list集合
	 */
	List<SysAccount> getOrgOfAccount(SysOrg org);
	
	//==========================组与角色操作============================//
	/**
	 * 得到组的角色
	 * @param org			账户组实体对象
	 * @return				角色实体对象
	 */
	SysRole getOrgOfRole(SysOrg org);
	/**
	 * 设置账户组的角色。
	 * 1、判断orgid是否已经设置过角色。没有设置过就创建组与角色关系表新纪录。
	 * 2、设置过则更新角色为参数orleID
	 * @param org
	 * @param roleID
	 * @return
	 */
	Boolean updateOrgOfRole(SysOrg org,String roleID);
	/**
	 * 设置账户组的角色
	 * @param orgIDList
	 * @return
	 */
	Boolean updateOrgOfRole(List<String> orgIDList,String roleID);
	
	/**
	 * 删除帐户组的角色
	 * @param org
	 * @return
	 */
	boolean deleteOrgOfRole(SysOrg org);
	
	//======================组与档案树=======================
	/**
	 * 根据组对象，得到组对应的档案树列表。这个方法在orgservice的继承类publicorgservice里实现
	 * @param org
	 * @return
	 */
	List<SysOrgTree> getOrgOfTree(SysOrg org);
	
	Boolean deleteOrgOfTree(List<SysOrgTree> list);
	
	Boolean deleteOrgOfTree(SysOrgTreeExample example);
	
	Boolean insertOrgOfTree(List<SysOrgTree> list);
	
	int updateOrgOfTree(SysOrgTree orgTree);
	
	SysOrgTree getOrgOfTree(String id);
	
	List<SysTree> getTree(String id);
	
	int updateOrgOfTree(SysOrgTree record,SysOrgTreeExample ex);
	
}
