package com.yapu.system.dao.itf;

import java.util.HashMap;
import java.util.List;

import com.yapu.system.entity.SysLoginlog;
import com.yapu.system.entity.SysLoginlogExample;

public interface SysLoginlogDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_LOGINLOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    int countByExample(SysLoginlogExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_LOGINLOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    int deleteByExample(SysLoginlogExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_LOGINLOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    int deleteByPrimaryKey(String loginlogid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_LOGINLOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    void insert(SysLoginlog record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_LOGINLOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    void insertSelective(SysLoginlog record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_LOGINLOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    List selectByExample(SysLoginlogExample example);
    
    /**
     * 手动加入的分页语句
     * @param map
     * @return
     */
    List selectByMapPage(HashMap map);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_LOGINLOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    SysLoginlog selectByPrimaryKey(String loginlogid);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_LOGINLOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    int updateByExampleSelective(SysLoginlog record, SysLoginlogExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_LOGINLOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    int updateByExample(SysLoginlog record, SysLoginlogExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_LOGINLOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    int updateByPrimaryKeySelective(SysLoginlog record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_LOGINLOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    int updateByPrimaryKey(SysLoginlog record);
}