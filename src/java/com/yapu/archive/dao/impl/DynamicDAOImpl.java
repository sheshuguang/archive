/**
 * 
 */
package com.yapu.archive.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.yapu.archive.dao.itf.DynamicDAO;
import com.yapu.archive.entity.DynamicExample;
import com.yapu.archive.entity.DynamicExample.Criteria;

/**
 * @author 
 *
 */
public class DynamicDAOImpl extends SqlMapClientDaoSupport implements DynamicDAO{
	
	/*
	 * (non-Javadoc)
	 * @see com.yapu.system.dao.itf.DynamicDAO#postUpdate(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public boolean update(String sql) {
		HashMap map = new HashMap();
		map.put("sql", sql);
		try {
			int num = getSqlMapClientTemplate().update("Dynamic.updateSql", map);
			if (num > 0) {
				return true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return false;
	}
	
	public int delete(String sql) {
		HashMap map = new HashMap();
		map.put("sql", sql);
		try {
			return getSqlMapClientTemplate().update("Dynamic.deleteSql", map);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean insert(String sql) {
		HashMap map = new HashMap();
		map.put("sql", sql);
		getSqlMapClientTemplate().insert("Dynamic.insertSql", map);
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public List selectByExample(DynamicExample example) {
		//判断参数是否为空
		if (null == example ) {
			return null;
		}
		
		//去创建sql语句
		String sql = createQuerySql(example);
		
		HashMap map = new HashMap();
		map.put("sql", sql);
		List list = getSqlMapClientTemplate().queryForList("Dynamic.selectBySql", map);
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	private String createQuerySql(DynamicExample example) {
		StringBuilder sql = new StringBuilder("select * from ").append(example.getTableName());
		if (null != example && example.getOredCriteria().size() > 0) {
			sql.append(" where (");
			for (int i=0;i < example.getOredCriteria().size();i++) {
				Criteria criteria = (Criteria) example.getOredCriteria().get(i);
				if (criteria.isValid()) {
					if (i > 0) {
						sql.append(" or (");
					}
					if (criteria.getCriteriaWithoutValue().size() > 0) {
						for (int j = 0;j < criteria.getCriteriaWithoutValue().size();j++ ) {
							sql.append(criteria.getCriteriaWithoutValue().get(j));
							sql.append(" and ");
						}
						
					}
					if (criteria.getCriteriaWithSingleValue().size() > 0) {
						for (int j = 0;j < criteria.getCriteriaWithSingleValue().size();j++) {
							HashMap map = (HashMap) criteria.getCriteriaWithSingleValue().get(j);
							sql.append(map.get("condition").toString() + map.get("value").toString());
							sql.append(" and ");
						}
					}
					if (criteria.getCriteriaWithListValue().size() > 0) {
						for (int j = 0;j < criteria.getCriteriaWithListValue().size();j++) {
							HashMap map = (HashMap) criteria.getCriteriaWithListValue().get(j);
							sql.append(map.get("condition") + "(" + map.get("value") + ")");
							sql.append(" and ");
							
						}
					}
					if (criteria.getCriteriaWithBetweenValue().size() > 0) {
						
					}
					sql.delete(sql.length() - 4, sql.length()).append(")");
				}
			}
		}
		if (null != example.getOrderByClause()) {
			sql.append(" ").append(example.getOrderByClause());
		}
		return sql.toString();
	}

}
