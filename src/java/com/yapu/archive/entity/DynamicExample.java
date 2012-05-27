package com.yapu.archive.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicExample {
	
	//排序规则
	protected String orderByClause;
	//存储Criteria实例对象
	protected List oredCriteria;
	//动态表名
	protected String tableName;
	//字段List
	protected List fieldList;
	//默认构造函数
	public DynamicExample() {
		oredCriteria = new ArrayList();
		fieldList = new ArrayList();
	}
	//带本身参数的构造函数
	public DynamicExample(DynamicExample example) {
		this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
        this.tableName = example.tableName;
        this.fieldList = example.fieldList;
	}

	//get and set 
	public String getOrderByClause() {
		return orderByClause;
	}
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}
	public List getOredCriteria() {
		return oredCriteria;
	}
	public void setOredCriteria(List oredCriteria) {
		this.oredCriteria = oredCriteria;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List getFieldList() {
		return fieldList;
	}
	public void setFieldList(List fieldList) {
		this.fieldList = fieldList;
	}
	
	
	public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }
	

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
    }
	
	/**
	 * 内部类，用来存储动态字段查询条件。
	 * @author Administrator
	 *
	 */
	public static class Criteria {
		//不为空
        protected List criteriaWithoutValue;

        protected List criteriaWithSingleValue;

        protected List criteriaWithListValue;

        protected List criteriaWithBetweenValue;

        protected Criteria() {
            super();
            criteriaWithoutValue = new ArrayList();
            criteriaWithSingleValue = new ArrayList();
            criteriaWithListValue = new ArrayList();
            criteriaWithBetweenValue = new ArrayList();
        }

        public boolean isValid() {
            return criteriaWithoutValue.size() > 0
                || criteriaWithSingleValue.size() > 0
                || criteriaWithListValue.size() > 0
                || criteriaWithBetweenValue.size() > 0;
        }

        public List getCriteriaWithoutValue() {
            return criteriaWithoutValue;
        }

        public List getCriteriaWithSingleValue() {
            return criteriaWithSingleValue;
        }

        public List getCriteriaWithListValue() {
            return criteriaWithListValue;
        }

        public List getCriteriaWithBetweenValue() {
            return criteriaWithBetweenValue;
        }

        protected void addCriterion(String condition) {
        	
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteriaWithoutValue.add(condition);
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("value", value);
            criteriaWithSingleValue.add(map);
        }

        protected void addCriterion(String condition, List values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("values", values);
            criteriaWithListValue.add(map);
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            List list = new ArrayList();
            list.add(value1);
            list.add(value2);
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("values", list);
            criteriaWithBetweenValue.add(map);
        }
        
        public Criteria andIsNull(String fieldName) {
            addCriterion(fieldName + " is null");
            return this;
        }

        public Criteria andIsNotNull(String fieldName) {
            addCriterion(fieldName + " is not null");
            return this;
        }

        public Criteria andEqualTo(String fieldName,long value) {
            addCriterion(fieldName + "=", value, fieldName);
            return this;
        }
        
        public Criteria andEqualTo(String fieldName,int value) {
            addCriterion(fieldName + "=", value, fieldName);
            return this;
        }
        
        public Criteria andEqualTo(String fieldName,String value) {
        	addCriterion(fieldName + "=", "'" + value + "'", fieldName);
            return this;
        }

        public Criteria andNotEqualTo(String fieldName,Long value) {
            addCriterion(fieldName + "<>", value, fieldName);
            return this;
        }

        public Criteria andGreaterThan(String fieldName,Long value) {
            addCriterion(fieldName + " >", value, fieldName);
            return this;
        }

        public Criteria andGreaterThanOrEqualTo(String fieldName,Long value) {
            addCriterion(fieldName + " >=", value, fieldName);
            return this;
        }

        public Criteria andLessThan(String fieldName,Long value) {
            addCriterion(fieldName + " <", value, fieldName);
            return this;
        }

        public Criteria andLessThanOrEqualTo(String fieldName,Long value) {
            addCriterion(fieldName + " <=", value, fieldName);
            return this;
        }

        public Criteria andIn(String fieldName,List values) {
            addCriterion(fieldName + " in", values, fieldName);
            return this;
        }

        public Criteria andNotIn(String fieldName,List values) {
            addCriterion(fieldName + " not in", values, fieldName);
            return this;
        }

        public Criteria andBetween(String fieldName,Long value1, Long value2) {
            addCriterion(fieldName + " between", value1, value2, fieldName);
            return this;
        }

        public Criteria andNotBetween(String fieldName,Long value1, Long value2) {
            addCriterion(fieldName + " not between", value1, value2, fieldName);
            return this;
        }

    }

}
