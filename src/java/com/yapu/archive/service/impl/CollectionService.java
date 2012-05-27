package com.yapu.archive.service.impl;

import java.util.HashMap;
import java.util.List;

import com.yapu.archive.dao.itf.ArCollectionDAO;
import com.yapu.archive.entity.ArCollection;
import com.yapu.archive.entity.ArCollectionExample;
import com.yapu.archive.service.itf.ICollectionService;

public class CollectionService implements ICollectionService {
	
	private ArCollectionDAO collectionDao;
	
	
	/**
	 * 拆分实体对象，返回CollectionExample类。
	 * @param collection
	 * @return
	 */
	@SuppressWarnings("unused")
	private ArCollectionExample splitCollectionEntity(ArCollection collection) {
		ArCollectionExample collectionExample = new ArCollectionExample();
		ArCollectionExample.Criteria criteria = collectionExample.createCriteria();
		if (null != collection) {
			if (null != collection.getTableid()) {
				criteria.andTableidEqualTo(collection.getTableid());
			}
			if (null != collection.getFileid()) {
				criteria.andFileidEqualTo(collection.getFileid());
			}
			return collectionExample;
		}
		return collectionExample;
	}

	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ICollectionService#deleteCollection(java.lang.String)
	 */
	public int deleteCollection(String collectionID) {
		if (null != collectionID && !"".equals(collectionID)) {
			try {
				return collectionDao.deleteByPrimaryKey(collectionID);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ICollectionService#deleteCollection(com.yapu.system.entity.ArCollection)
	 */
	public int deleteCollection(ArCollection collection) {
		if (null != collection) {
			return deleteCollection(collection.getCollectionid());
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ICollectionService#deleteCollection(java.util.List)
	 */
	public int deleteCollection(List<String> collectionIDList) {
		if (null != collectionIDList && collectionIDList.size() > 0) {
			int num = 0;
			for (int i=0;i<collectionIDList.size();i++) {
				deleteCollection(collectionIDList.get(i));
				num += 1;
			}
			return num;
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ICollectionService#insertCollection(com.yapu.system.entity.ArCollection)
	 */
	public Boolean insertCollection(ArCollection collection) {
		if (null != collection ) {
			try {
				collectionDao.insertSelective(collection);
				return true;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		return false;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ICollectionService#rowCount(com.yapu.system.entity.ArCollection)
	 */
	public int rowCount(ArCollection collection) {
		if (null != collection) {
			ArCollectionExample collectionExample = splitCollectionEntity(collection);
			if (null != collectionExample) {
				return collectionDao.countByExample(collectionExample);
			}
		}
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ICollectionService#selectByPrimaryKey(java.lang.String)
	 */
	public ArCollection selectByPrimaryKey(String collectionID) {
		if (null != collectionID && !"".equals(collectionID)) {
			return collectionDao.selectByPrimaryKey(collectionID);
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ICollectionService#selectByWhereNotPage(com.yapu.system.entity.ArCollection)
	 */
	public List<ArCollection> selectByWhereNotPage(ArCollection collection) {
		if (null != collection) {
			ArCollectionExample collectionExample = splitCollectionEntity(collection);
			if (null != collectionExample) {
				return collectionDao.selectByExample(collectionExample);
			}
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ICollectionService#selectByWherePage(com.yapu.system.entity.ArCollection)
	 */
	public List<ArCollection> selectByWherePage(ArCollection collection) {
		//如果实体、分页大小、开始行号为空，不查询，直接返回null
		if (null == collection || null == collection.getPageSize() || null == collection.getStartRow()) {
			return null;
		}
		HashMap map = new HashMap();
		map.put("PAGESIZE", collection.getPageSize());
		map.put("STARTROW", collection.getStartRow());
		map.put("ORDERBY", collection.getSortRules());
		
		StringBuffer whereStr = new StringBuffer();
		if (null != collection.getTableid()) {
			whereStr.append("TABLEID = '" + collection.getTableid() + "' and ");
		}
		if (null != collection.getFileid()) {
			whereStr.append(" FILEID = '" + collection.getFileid() + "' and ");
		}
		
		if (whereStr.length() > 0 ) {
			String where = whereStr.substring(0, whereStr.length() - 4);
			map.put("WHEREVALUE", where);
		}
		List accountList = collectionDao.selectByMapPage(map);
		return accountList;
	}
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.service.itf.ICollectionService#updateCollection(com.yapu.system.entity.ArCollection)
	 */
	public int updateCollection(ArCollection collection) {
		if (null != collection) {
			try {
				return collectionDao.updateByPrimaryKeySelective(collection);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
		return 0;
	}

	public void setCollectionDao(ArCollectionDAO collectionDao) {
		this.collectionDao = collectionDao;
	}

}
