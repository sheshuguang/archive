package com.yapu.system.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.yapu.system.dao.itf.SysOrgDAO;
import com.yapu.system.entity.SysOrg;
import com.yapu.system.entity.SysOrgExample;

public class SysOrgDAOImpl extends SqlMapClientDaoSupport implements SysOrgDAO {

    
    public SysOrgDAOImpl() {
        super();
    }

    public int countByExample(SysOrgExample example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("SYS_ORG.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(SysOrgExample example) {
        int rows = getSqlMapClientTemplate().delete("SYS_ORG.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(String orgid) {
        SysOrg key = new SysOrg();
        key.setOrgid(orgid);
        int rows = getSqlMapClientTemplate().delete("SYS_ORG.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(SysOrg record) {
        getSqlMapClientTemplate().insert("SYS_ORG.ibatorgenerated_insert", record);
    }

    public void insertSelective(SysOrg record) {
        getSqlMapClientTemplate().insert("SYS_ORG.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(SysOrgExample example) {
        List list = getSqlMapClientTemplate().queryForList("SYS_ORG.ibatorgenerated_selectByExample", example);
        return list;
    }

    public SysOrg selectByPrimaryKey(String orgid) {
        SysOrg key = new SysOrg();
        key.setOrgid(orgid);
        SysOrg record = (SysOrg) getSqlMapClientTemplate().queryForObject("SYS_ORG.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(SysOrg record, SysOrgExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("SYS_ORG.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(SysOrg record, SysOrgExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("SYS_ORG.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(SysOrg record) {
        int rows = getSqlMapClientTemplate().update("SYS_ORG.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(SysOrg record) {
        int rows = getSqlMapClientTemplate().update("SYS_ORG.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    private static class UpdateByExampleParms extends SysOrgExample {
        private Object record;

        public UpdateByExampleParms(Object record, SysOrgExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}