package com.yapu.archive.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.yapu.archive.dao.itf.SysOrgTreeDAO;
import com.yapu.archive.entity.SysOrgTree;
import com.yapu.archive.entity.SysOrgTreeExample;

public class SysOrgTreeDAOImpl extends SqlMapClientDaoSupport implements SysOrgTreeDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ORG_TREE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public SysOrgTreeDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ORG_TREE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public int countByExample(SysOrgTreeExample example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("SYS_ORG_TREE.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ORG_TREE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public int deleteByExample(SysOrgTreeExample example) {
        int rows = getSqlMapClientTemplate().delete("SYS_ORG_TREE.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ORG_TREE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public int deleteByPrimaryKey(String orgTreeId) {
        SysOrgTree key = new SysOrgTree();
        key.setOrgTreeId(orgTreeId);
        int rows = getSqlMapClientTemplate().delete("SYS_ORG_TREE.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ORG_TREE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public void insert(SysOrgTree record) {
        getSqlMapClientTemplate().insert("SYS_ORG_TREE.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ORG_TREE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public void insertSelective(SysOrgTree record) {
        getSqlMapClientTemplate().insert("SYS_ORG_TREE.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ORG_TREE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public List selectByExample(SysOrgTreeExample example) {
        List list = getSqlMapClientTemplate().queryForList("SYS_ORG_TREE.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ORG_TREE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public SysOrgTree selectByPrimaryKey(String orgTreeId) {
        SysOrgTree key = new SysOrgTree();
        key.setOrgTreeId(orgTreeId);
        SysOrgTree record = (SysOrgTree) getSqlMapClientTemplate().queryForObject("SYS_ORG_TREE.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ORG_TREE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public int updateByExampleSelective(SysOrgTree record, SysOrgTreeExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("SYS_ORG_TREE.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ORG_TREE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public int updateByExample(SysOrgTree record, SysOrgTreeExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("SYS_ORG_TREE.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ORG_TREE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public int updateByPrimaryKeySelective(SysOrgTree record) {
        int rows = getSqlMapClientTemplate().update("SYS_ORG_TREE.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ORG_TREE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public int updateByPrimaryKey(SysOrgTree record) {
        int rows = getSqlMapClientTemplate().update("SYS_ORG_TREE.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table SYS_ORG_TREE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
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