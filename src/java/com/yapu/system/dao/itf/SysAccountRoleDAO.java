package com.yapu.system.dao.itf;

import java.util.List;

import com.yapu.system.entity.SysAccountRole;
import com.yapu.system.entity.SysAccountRoleExample;

public interface SysAccountRoleDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ACCOUNT_ROLE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    int countByExample(SysAccountRoleExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ACCOUNT_ROLE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    int deleteByExample(SysAccountRoleExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ACCOUNT_ROLE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    int deleteByPrimaryKey(String accountRoleId);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ACCOUNT_ROLE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    void insert(SysAccountRole record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ACCOUNT_ROLE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    void insertSelective(SysAccountRole record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ACCOUNT_ROLE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    List selectByExample(SysAccountRoleExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ACCOUNT_ROLE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    SysAccountRole selectByPrimaryKey(String accountRoleId);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ACCOUNT_ROLE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    int updateByExampleSelective(SysAccountRole record, SysAccountRoleExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ACCOUNT_ROLE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    int updateByExample(SysAccountRole record, SysAccountRoleExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ACCOUNT_ROLE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    int updateByPrimaryKeySelective(SysAccountRole record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ACCOUNT_ROLE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    int updateByPrimaryKey(SysAccountRole record);
}