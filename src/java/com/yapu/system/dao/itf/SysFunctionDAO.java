package com.yapu.system.dao.itf;

import java.util.List;

import com.yapu.system.entity.SysFunction;
import com.yapu.system.entity.SysFunctionExample;

public interface SysFunctionDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_FUNCTION
     *
     * @ibatorgenerated Sat Nov 13 12:15:56 GMT+08:00 2010
     */
    int countByExample(SysFunctionExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_FUNCTION
     *
     * @ibatorgenerated Sat Nov 13 12:15:56 GMT+08:00 2010
     */
    int deleteByExample(SysFunctionExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_FUNCTION
     *
     * @ibatorgenerated Sat Nov 13 12:15:56 GMT+08:00 2010
     */
    int deleteByPrimaryKey(String functionid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_FUNCTION
     *
     * @ibatorgenerated Sat Nov 13 12:15:56 GMT+08:00 2010
     */
    void insert(SysFunction record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_FUNCTION
     *
     * @ibatorgenerated Sat Nov 13 12:15:56 GMT+08:00 2010
     */
    void insertSelective(SysFunction record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_FUNCTION
     *
     * @ibatorgenerated Sat Nov 13 12:15:56 GMT+08:00 2010
     */
    List selectByExample(SysFunctionExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_FUNCTION
     *
     * @ibatorgenerated Sat Nov 13 12:15:56 GMT+08:00 2010
     */
    SysFunction selectByPrimaryKey(String functionid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_FUNCTION
     *
     * @ibatorgenerated Sat Nov 13 12:15:56 GMT+08:00 2010
     */
    int updateByExampleSelective(SysFunction record, SysFunctionExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_FUNCTION
     *
     * @ibatorgenerated Sat Nov 13 12:15:56 GMT+08:00 2010
     */
    int updateByExample(SysFunction record, SysFunctionExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_FUNCTION
     *
     * @ibatorgenerated Sat Nov 13 12:15:56 GMT+08:00 2010
     */
    int updateByPrimaryKeySelective(SysFunction record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_FUNCTION
     *
     * @ibatorgenerated Sat Nov 13 12:15:56 GMT+08:00 2010
     */
    int updateByPrimaryKey(SysFunction record);
}