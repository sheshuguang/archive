package com.yapu.system.service.itf;

/**
 * 账户表操作服务类接口
 * 
 * @data 		2010-10-30
 * @author 		wangf
 * @version 1.0
 */
import java.util.List;

import com.yapu.archive.entity.SysAccountTree;
import com.yapu.archive.entity.SysAccountTreeExample;
import com.yapu.system.entity.SysAccount;
import com.yapu.system.entity.SysAccountExample;
import com.yapu.system.entity.SysOrg;
import com.yapu.system.entity.SysRole;

public interface IAccountService {
	
	//===================账户基本操作=====================
	/**
	 * 插入新账户
	 * @param 	account			账户实体对象
	 * @return					true or false					
	 */
	Boolean insertAccount(SysAccount account,String parentOrgId);
	/**
	 * 批量插入新帐户
	 * @param accountList		账户实体对象List集合
	 * @return
	 */
	Boolean insertAccount(List<SysAccount> accountList,String parentOrgId);
	/**
	 * 更新账户。传入的实体对象ID为条件更新账户。
	 * @param 	account			账户实体对象
	 * @return					
	 */
	int updateAccount(SysAccount account);
	/**
	 * 批量更新账户。
	 * @param accountList		账户实体对象的List集合。
	 * @return					
	 */
	int updateAccount(List<SysAccount> accountList);
	/**
	 * 删除账户。
	 * @param accountID			账户ID。
	 * @return					
	 */
	int deleteAccount(String accountID);
	/**
	 * 删除账户
	 * @param account			账户实体对象
	 * @return
	 */
	int deleteAccount(SysAccount account);
	/**
	 * 批量删除账户。
	 * @param accountIDList		账户ID的List集合。例如[1,2,3]
	 * @return
	 */
	int deleteAccount(List<String> accountIDList);
	/**
	 * 按主键值查询
	 * @param accountID			账户id
	 * @return					返回实体对象
	 */
	SysAccount selectByPrimaryKey(String accountID);
	/**
	 * 按主键值查询。
	 * @param accountIDList		账户id的List集合
	 * @return
	 */
	List<SysAccount> selectByPrimaryKey(List<String> accountIDList);
	/**
	 * 不分页按实体类的属性查询。操作符是like。null、“”不查
	 * @param account
	 * @return
	 */
	List<SysAccount> selectByWhereNotPage(SysAccountExample example);
	/**
	 * 分页按实体类的属性值查询
	 * @param account			实体对象
	 * @return
	 */
	List<SysAccount> selectByWherePage(SysAccountExample example);
	/**
	 * 得到总行数
	 * @param example
	 * @return
	 */
	int rowCount(SysAccountExample example);
	
	//========================================================
	
	//===========账户关联的操作=================================
	
	
	//==========================登录============================//
	/**
	 * 帐户登录验证
	 * @param account			包含账户名和密码的账户实体
	 * @return					返回true,flase
	 */
	SysAccount login(SysAccount account);
	
	//==========================账户与组操作============================//
	/**
	 * 得到账户的组
	 * @param account			账户实体对象
	 * @return					账户组实体对象
	 */
	SysOrg getAccountOfOrg(SysAccount account);
	/**
	 * 设置账户的新组
	 * @param accountIDList		账户id的list
	 * @param orgID				新组的id
	 */
	Boolean updateAccountOfOrg(List<String> accountIDList,String orgID);
	
	//==========================账户与角色操作============================//
	/**
	 * 得到账户的角色
	 * @param account
	 * @return
	 */
	SysRole getAccountOfRole(SysAccount account);
	/**
	 * 设置账户的角色。
	 * 1、根据参数account的账户id，查找账户是否已经有角色。
	 * 2、有角色就更新角色id为参数roleid。没有就创建。
	 * @param account			账户实体对象
	 * @param roleID			角色id
	 * @return
	 */
	Boolean updateAccountOfRole(SysAccount account,String roleID);
	
	/**
	 * 设置账户的角色
	 * @param accountIDList		账户id的list
	 * @param roleID			角色id
	 * @return
	 */
	Boolean updateAccountOfRole(List<String> accountIDList,String roleID);
	
	/**
	 * 移除帐户的角色
	 * @return
	 */
	boolean deleteAccountOfRole(SysAccount account);
	
	//======================组与档案树=======================
	/**
	 * 根据组对象，得到组对应的档案树列表。这个方法在accountservice的继承类publicaccountservice里实现
	 * @param account
	 * @return
	 */
	List<SysAccountTree> getAccountOfTree(SysAccount account);
	
	Boolean deleteAccountOfTree(List<SysAccountTree> list);
	
	Boolean deleteAccountOfTree(SysAccountTreeExample example);
	
	Boolean insertAccountOfTree(List<SysAccountTree> list);
	
	int updateAccountOfTree(SysAccountTree AccountTree);
	
	SysAccountTree getAccountOfTree(String id);
	
	int updateAccountOfTree(SysAccountTree record,SysAccountTreeExample ex);
	
}
