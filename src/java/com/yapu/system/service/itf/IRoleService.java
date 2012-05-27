package com.yapu.system.service.itf;
/**
 * 角色操作服务类
 * 
 * @author 	wangf
 * @date	2010-11-7
 * @version 1.0
 */

import java.util.List;

import com.yapu.system.entity.SysAccount;
import com.yapu.system.entity.SysFunction;
import com.yapu.system.entity.SysOrg;
import com.yapu.system.entity.SysRole;
import com.yapu.system.entity.SysRoleExample;
import com.yapu.system.entity.SysRoleFunction;

public interface IRoleService {
	
	
	//=============增、删、改、查==================
	/**
	 * 插入新角色
	 * @param role			角色实体对象
	 * @return				正确处理返回true，失败返回false
	 */
	Boolean insertRole(SysRole role);
	/**
	 * 批量插入角色
	 * @param roleList		角色实体对象的list集合
	 * @return
	 */
	Boolean insertRole(List<SysRole> roleList);
	/**
	 * 更新角色。以实体对象的id为条件更新。
	 * @param role			角色实体对象
	 * @return				更新的数量
	 */
	int updateRole(SysRole role);
	/**
	 * 批量更新角色。以角色实体对象的ID值为条件更新。
	 * @param roleList		角色实体对象的List集合。
	 * @return
	 */
	int updateRole(List<SysRole> roleList);
	/**
	 * 删除角色。
	 * @param roleID			角色ID
	 * @return
	 */
	int deleteRole(String roleID);
	/**
	 * 删除角色。
	 * @param role			角色实体对象
	 * @return
	 */
	int deleteRole(SysRole role);
	/**
	 * 批量删除角色。
	 * @param roleIDList		角色id的List集合。
	 * @return
	 */
	int deleteRole(List<String> roleIDList);
	/**
	 * 按主键值查找角色。
	 * @param roleID			角色id
	 * @return
	 */
	SysRole selectByPrimaryKey(String roleID);
	/**
	 * 不分页查询
	 * @param example
	 * @return
	 */
	List<SysRole> selectByWhereNotPage(SysRoleExample example);
	/**
	 * 查询总行数
	 * @param example
	 * @return
	 */
	int rowCount(SysRoleExample example);
	
	//===========================================
	
	//============组相关的操作=====================
	
	//==========================角色与账户操作============================//
	/**
	 * 得到角色下的账户List
	 * @param role			角色实体对象
	 * @return				账户list集合
	 */
	List<SysAccount> getRoleOfAccount(SysRole role);
	
	//==========================角色与组操作============================//
	/**
	 * 得到角色下所有组
	 * @param role			角色实体对象
	 * @return				角色实体对象
	 */
	List<SysOrg> getRoleOfOrg(SysRole role);
	
	//==========================角色与功能操作============================//
	/**
	 * 得到角色对应的功能列表
	 * @param role			角色实体对象
	 */
	List<SysFunction> getRoleOfFunction(SysRole role);
	/**
	 * 设置角色与功能的关联
	 * @param roleid
	 * @param allShowRoleid
	 * @param functionids
	 * @return
	 */
	Boolean insertRoleOfFunction(String roleid,String functionids);
	/**
	 * 删除角色与功能的关联
	 * @param roleFunction
	 * @return
	 */
	int deleteRoleOfFunction(SysRoleFunction roleFunction);
	

}
