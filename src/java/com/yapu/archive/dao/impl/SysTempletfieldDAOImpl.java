package com.yapu.archive.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.yapu.archive.dao.itf.SysTempletfieldDAO;
import com.yapu.archive.entity.SysTempletfield;
import com.yapu.archive.entity.SysTempletfieldExample;

public class SysTempletfieldDAOImpl extends SqlMapClientDaoSupport implements SysTempletfieldDAO {

    public SysTempletfieldDAOImpl() {
        super();
    }

    public int countByExample(SysTempletfieldExample example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("SYS_TEMPLETFIELD.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(SysTempletfieldExample example) {
        int rows = getSqlMapClientTemplate().delete("SYS_TEMPLETFIELD.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(String templetfieldid) {
        SysTempletfield key = new SysTempletfield();
        key.setTempletfieldid(templetfieldid);
        int rows = getSqlMapClientTemplate().delete("SYS_TEMPLETFIELD.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(SysTempletfield record) {
        getSqlMapClientTemplate().insert("SYS_TEMPLETFIELD.ibatorgenerated_insert", record);
    }

    public void insertSelective(SysTempletfield record) {
        getSqlMapClientTemplate().insert("SYS_TEMPLETFIELD.ibatorgenerated_insertSelective", record);
    }

    public List<SysTempletfield> selectByExample(SysTempletfieldExample example) {
        List<SysTempletfield> list = getSqlMapClientTemplate().queryForList("SYS_TEMPLETFIELD.ibatorgenerated_selectByExample", example);
        return list;
    }

    public SysTempletfield selectByPrimaryKey(String templetfieldid) {
        SysTempletfield key = new SysTempletfield();
        key.setTempletfieldid(templetfieldid);
        SysTempletfield record = (SysTempletfield) getSqlMapClientTemplate().queryForObject("SYS_TEMPLETFIELD.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(SysTempletfield record, SysTempletfieldExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("SYS_TEMPLETFIELD.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(SysTempletfield record, SysTempletfieldExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("SYS_TEMPLETFIELD.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(SysTempletfield record) {
        int rows = getSqlMapClientTemplate().update("SYS_TEMPLETFIELD.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(SysTempletfield record) {
        int rows = getSqlMapClientTemplate().update("SYS_TEMPLETFIELD.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    private static class UpdateByExampleParms extends SysTempletfieldExample {
        private Object record;

        public UpdateByExampleParms(Object record, SysTempletfieldExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}