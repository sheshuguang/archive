package com.yapu.archive.dao.impl;


import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.yapu.archive.dao.itf.SysTempletDAO;
import com.yapu.archive.entity.SysTemplet;
import com.yapu.archive.entity.SysTempletExample;

public class SysTempletDAOImpl extends SqlMapClientDaoSupport implements SysTempletDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_TEMPLET
     *
     * @ibatorgenerated Sun Nov 14 02:07:28 GMT+08:00 2010
     */
    public SysTempletDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_TEMPLET
     *
     * @ibatorgenerated Sun Nov 14 02:07:28 GMT+08:00 2010
     */
    public int countByExample(SysTempletExample example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("SYS_TEMPLET.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_TEMPLET
     *
     * @ibatorgenerated Sun Nov 14 02:07:28 GMT+08:00 2010
     */
    public int deleteByExample(SysTempletExample example) {
        int rows = getSqlMapClientTemplate().delete("SYS_TEMPLET.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_TEMPLET
     *
     * @ibatorgenerated Sun Nov 14 02:07:28 GMT+08:00 2010
     */
    public int deleteByPrimaryKey(String templetid) {
        SysTemplet key = new SysTemplet();
        key.setTempletid(templetid);
        int rows = getSqlMapClientTemplate().delete("SYS_TEMPLET.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_TEMPLET
     *
     * @ibatorgenerated Sun Nov 14 02:07:28 GMT+08:00 2010
     */
    public void insert(SysTemplet record) {
        getSqlMapClientTemplate().insert("SYS_TEMPLET.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_TEMPLET
     *
     * @ibatorgenerated Sun Nov 14 02:07:28 GMT+08:00 2010
     */
    public void insertSelective(SysTemplet record) {
        getSqlMapClientTemplate().insert("SYS_TEMPLET.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_TEMPLET
     *
     * @ibatorgenerated Sun Nov 14 02:07:28 GMT+08:00 2010
     */
    public List selectByExample(SysTempletExample example) {
        List list = getSqlMapClientTemplate().queryForList("SYS_TEMPLET.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_TEMPLET
     *
     * @ibatorgenerated Sun Nov 14 02:07:28 GMT+08:00 2010
     */
    public SysTemplet selectByPrimaryKey(String templetid) {
        SysTemplet key = new SysTemplet();
        key.setTempletid(templetid);
        SysTemplet record = (SysTemplet) getSqlMapClientTemplate().queryForObject("SYS_TEMPLET.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_TEMPLET
     *
     * @ibatorgenerated Sun Nov 14 02:07:28 GMT+08:00 2010
     */
    public int updateByExampleSelective(SysTemplet record, SysTempletExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("SYS_TEMPLET.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_TEMPLET
     *
     * @ibatorgenerated Sun Nov 14 02:07:28 GMT+08:00 2010
     */
    public int updateByExample(SysTemplet record, SysTempletExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("SYS_TEMPLET.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_TEMPLET
     *
     * @ibatorgenerated Sun Nov 14 02:07:28 GMT+08:00 2010
     */
    public int updateByPrimaryKeySelective(SysTemplet record) {
        int rows = getSqlMapClientTemplate().update("SYS_TEMPLET.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_TEMPLET
     *
     * @ibatorgenerated Sun Nov 14 02:07:28 GMT+08:00 2010
     */
    public int updateByPrimaryKey(SysTemplet record) {
        int rows = getSqlMapClientTemplate().update("SYS_TEMPLET.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table SYS_TEMPLET
     *
     * @ibatorgenerated Sun Nov 14 02:07:28 GMT+08:00 2010
     */
    private static class UpdateByExampleParms extends SysTempletExample {
        private Object record;

        public UpdateByExampleParms(Object record, SysTempletExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}