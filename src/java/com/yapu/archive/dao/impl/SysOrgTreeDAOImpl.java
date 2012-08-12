package com.yapu.archive.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.yapu.archive.dao.itf.SysOrgTreeDAO;
import com.yapu.archive.entity.SysOrgTree;
import com.yapu.archive.entity.SysOrgTreeExample;

public class SysOrgTreeDAOImpl extends SqlMapClientDaoSupport implements SysOrgTreeDAO {

    
    public SysOrgTreeDAOImpl() {
        super();
    }

    public int countByExample(SysOrgTreeExample example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("SYS_ORG_TREE.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(SysOrgTreeExample example) {
        int rows = getSqlMapClientTemplate().delete("SYS_ORG_TREE.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(String orgTreeId) {
        SysOrgTree key = new SysOrgTree();
        key.setOrgTreeId(orgTreeId);
        int rows = getSqlMapClientTemplate().delete("SYS_ORG_TREE.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(SysOrgTree record) {
        getSqlMapClientTemplate().insert("SYS_ORG_TREE.ibatorgenerated_insert", record);
    }

    public void insertSelective(SysOrgTree record) {
        getSqlMapClientTemplate().insert("SYS_ORG_TREE.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(SysOrgTreeExample example) {
        List list = getSqlMapClientTemplate().queryForList("SYS_ORG_TREE.ibatorgenerated_selectByExample", example);
        return list;
    }

    public SysOrgTree selectByPrimaryKey(String orgTreeId) {
        SysOrgTree key = new SysOrgTree();
        key.setOrgTreeId(orgTreeId);
        SysOrgTree record = (SysOrgTree) getSqlMapClientTemplate().queryForObject("SYS_ORG_TREE.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(SysOrgTree record, SysOrgTreeExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("SYS_ORG_TREE.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(SysOrgTree record, SysOrgTreeExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("SYS_ORG_TREE.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(SysOrgTree record) {
        int rows = getSqlMapClientTemplate().update("SYS_ORG_TREE.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(SysOrgTree record) {
        int rows = getSqlMapClientTemplate().update("SYS_ORG_TREE.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    private static class UpdateByExampleParms extends SysOrgTreeExample {
        private Object record;

        public UpdateByExampleParms(Object record, SysOrgTreeExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}