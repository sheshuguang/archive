package com.yapu.archive.dao.itf;

import java.util.List;

import com.yapu.archive.entity.SysDocserver;
import com.yapu.archive.entity.SysDocserverExample;

public interface SysDocserverDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_DOCSERVER
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    int countByExample(SysDocserverExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_DOCSERVER
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    int deleteByExample(SysDocserverExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_DOCSERVER
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    int deleteByPrimaryKey(String docserverid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_DOCSERVER
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    void insert(SysDocserver record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_DOCSERVER
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    void insertSelective(SysDocserver record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_DOCSERVER
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    List selectByExample(SysDocserverExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_DOCSERVER
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    SysDocserver selectByPrimaryKey(String docserverid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_DOCSERVER
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    int updateByExampleSelective(SysDocserver record, SysDocserverExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_DOCSERVER
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    int updateByExample(SysDocserver record, SysDocserverExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_DOCSERVER
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    int updateByPrimaryKeySelective(SysDocserver record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_DOCSERVER
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    int updateByPrimaryKey(SysDocserver record);
}