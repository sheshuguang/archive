package com.yapu.archive.dao.itf;

import java.util.List;

import com.yapu.archive.entity.SysTemplet;
import com.yapu.archive.entity.SysTempletExample;

public interface SysTempletDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_TEMPLET
     *
     * @ibatorgenerated Sun Nov 14 02:07:28 GMT+08:00 2010
     */
    int countByExample(SysTempletExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_TEMPLET
     *
     * @ibatorgenerated Sun Nov 14 02:07:28 GMT+08:00 2010
     */
    int deleteByExample(SysTempletExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_TEMPLET
     *
     * @ibatorgenerated Sun Nov 14 02:07:28 GMT+08:00 2010
     */
    int deleteByPrimaryKey(String templetid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_TEMPLET
     *
     * @ibatorgenerated Sun Nov 14 02:07:28 GMT+08:00 2010
     */
    void insert(SysTemplet record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_TEMPLET
     *
     * @ibatorgenerated Sun Nov 14 02:07:28 GMT+08:00 2010
     */
    void insertSelective(SysTemplet record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_TEMPLET
     *
     * @ibatorgenerated Sun Nov 14 02:07:28 GMT+08:00 2010
     */
    List selectByExample(SysTempletExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_TEMPLET
     *
     * @ibatorgenerated Sun Nov 14 02:07:28 GMT+08:00 2010
     */
    SysTemplet selectByPrimaryKey(String templetid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_TEMPLET
     *
     * @ibatorgenerated Sun Nov 14 02:07:28 GMT+08:00 2010
     */
    int updateByExampleSelective(SysTemplet record, SysTempletExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_TEMPLET
     *
     * @ibatorgenerated Sun Nov 14 02:07:28 GMT+08:00 2010
     */
    int updateByExample(SysTemplet record, SysTempletExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_TEMPLET
     *
     * @ibatorgenerated Sun Nov 14 02:07:28 GMT+08:00 2010
     */
    int updateByPrimaryKeySelective(SysTemplet record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_TEMPLET
     *
     * @ibatorgenerated Sun Nov 14 02:07:28 GMT+08:00 2010
     */
    int updateByPrimaryKey(SysTemplet record);
}