package com.yapu.archive.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.yapu.archive.dao.itf.ArCollectionDAO;
import com.yapu.archive.entity.ArCollection;
import com.yapu.archive.entity.ArCollectionExample;

public class ArCollectionDAOImpl extends SqlMapClientDaoSupport implements ArCollectionDAO {

    
    public ArCollectionDAOImpl() {
        super();
    }

    public int countByExample(ArCollectionExample example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("AR_COLLECTION.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(ArCollectionExample example) {
        int rows = getSqlMapClientTemplate().delete("AR_COLLECTION.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(String collectionid) {
        ArCollection key = new ArCollection();
        key.setCollectionid(collectionid);
        int rows = getSqlMapClientTemplate().delete("AR_COLLECTION.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(ArCollection record) {
        getSqlMapClientTemplate().insert("AR_COLLECTION.ibatorgenerated_insert", record);
    }

    public void insertSelective(ArCollection record) {
        getSqlMapClientTemplate().insert("AR_COLLECTION.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(ArCollectionExample example) {
        List list = getSqlMapClientTemplate().queryForList("AR_COLLECTION.ibatorgenerated_selectByExample", example);
        return list;
    }
    /*
     * (non-Javadoc)
     * @see com.yapu.system.dao.itf.ArCollectionDAO#selectByMapPage(java.util.HashMap)
     */
    public List selectByMapPage(HashMap map) {
    	List list = getSqlMapClientTemplate().queryForList("AR_COLLECTION.ibatorgenerated_selectByExamplePage", map);
		return list;
	}

    public ArCollection selectByPrimaryKey(String collectionid) {
        ArCollection key = new ArCollection();
        key.setCollectionid(collectionid);
        ArCollection record = (ArCollection) getSqlMapClientTemplate().queryForObject("AR_COLLECTION.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(ArCollection record, ArCollectionExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("AR_COLLECTION.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(ArCollection record, ArCollectionExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("AR_COLLECTION.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(ArCollection record) {
        int rows = getSqlMapClientTemplate().update("AR_COLLECTION.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(ArCollection record) {
        int rows = getSqlMapClientTemplate().update("AR_COLLECTION.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    private static class UpdateByExampleParms extends ArCollectionExample {
        private Object record;

        public UpdateByExampleParms(Object record, ArCollectionExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}