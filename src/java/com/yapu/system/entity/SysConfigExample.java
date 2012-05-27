package com.yapu.system.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysConfigExample {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table SYS_CONFIG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    protected String orderByClause;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table SYS_CONFIG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    protected List oredCriteria;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_CONFIG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public SysConfigExample() {
        oredCriteria = new ArrayList();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_CONFIG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    protected SysConfigExample(SysConfigExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_CONFIG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_CONFIG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_CONFIG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_CONFIG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_CONFIG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_CONFIG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_CONFIG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table SYS_CONFIG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public static class Criteria {
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

        public Criteria andConfigidIsNull() {
            addCriterion("CONFIGID is null");
            return this;
        }

        public Criteria andConfigidIsNotNull() {
            addCriterion("CONFIGID is not null");
            return this;
        }

        public Criteria andConfigidEqualTo(String value) {
            addCriterion("CONFIGID =", value, "configid");
            return this;
        }

        public Criteria andConfigidNotEqualTo(String value) {
            addCriterion("CONFIGID <>", value, "configid");
            return this;
        }

        public Criteria andConfigidGreaterThan(String value) {
            addCriterion("CONFIGID >", value, "configid");
            return this;
        }

        public Criteria andConfigidGreaterThanOrEqualTo(String value) {
            addCriterion("CONFIGID >=", value, "configid");
            return this;
        }

        public Criteria andConfigidLessThan(String value) {
            addCriterion("CONFIGID <", value, "configid");
            return this;
        }

        public Criteria andConfigidLessThanOrEqualTo(String value) {
            addCriterion("CONFIGID <=", value, "configid");
            return this;
        }

        public Criteria andConfigidLike(String value) {
            addCriterion("CONFIGID like", value, "configid");
            return this;
        }

        public Criteria andConfigidNotLike(String value) {
            addCriterion("CONFIGID not like", value, "configid");
            return this;
        }

        public Criteria andConfigidIn(List values) {
            addCriterion("CONFIGID in", values, "configid");
            return this;
        }

        public Criteria andConfigidNotIn(List values) {
            addCriterion("CONFIGID not in", values, "configid");
            return this;
        }

        public Criteria andConfigidBetween(String value1, String value2) {
            addCriterion("CONFIGID between", value1, value2, "configid");
            return this;
        }

        public Criteria andConfigidNotBetween(String value1, String value2) {
            addCriterion("CONFIGID not between", value1, value2, "configid");
            return this;
        }

        public Criteria andConfigkeyIsNull() {
            addCriterion("CONFIGKEY is null");
            return this;
        }

        public Criteria andConfigkeyIsNotNull() {
            addCriterion("CONFIGKEY is not null");
            return this;
        }

        public Criteria andConfigkeyEqualTo(String value) {
            addCriterion("CONFIGKEY =", value, "configkey");
            return this;
        }

        public Criteria andConfigkeyNotEqualTo(String value) {
            addCriterion("CONFIGKEY <>", value, "configkey");
            return this;
        }

        public Criteria andConfigkeyGreaterThan(String value) {
            addCriterion("CONFIGKEY >", value, "configkey");
            return this;
        }

        public Criteria andConfigkeyGreaterThanOrEqualTo(String value) {
            addCriterion("CONFIGKEY >=", value, "configkey");
            return this;
        }

        public Criteria andConfigkeyLessThan(String value) {
            addCriterion("CONFIGKEY <", value, "configkey");
            return this;
        }

        public Criteria andConfigkeyLessThanOrEqualTo(String value) {
            addCriterion("CONFIGKEY <=", value, "configkey");
            return this;
        }

        public Criteria andConfigkeyLike(String value) {
            addCriterion("CONFIGKEY like", value, "configkey");
            return this;
        }

        public Criteria andConfigkeyNotLike(String value) {
            addCriterion("CONFIGKEY not like", value, "configkey");
            return this;
        }

        public Criteria andConfigkeyIn(List values) {
            addCriterion("CONFIGKEY in", values, "configkey");
            return this;
        }

        public Criteria andConfigkeyNotIn(List values) {
            addCriterion("CONFIGKEY not in", values, "configkey");
            return this;
        }

        public Criteria andConfigkeyBetween(String value1, String value2) {
            addCriterion("CONFIGKEY between", value1, value2, "configkey");
            return this;
        }

        public Criteria andConfigkeyNotBetween(String value1, String value2) {
            addCriterion("CONFIGKEY not between", value1, value2, "configkey");
            return this;
        }

        public Criteria andConfigvalueIsNull() {
            addCriterion("CONFIGVALUE is null");
            return this;
        }

        public Criteria andConfigvalueIsNotNull() {
            addCriterion("CONFIGVALUE is not null");
            return this;
        }

        public Criteria andConfigvalueEqualTo(String value) {
            addCriterion("CONFIGVALUE =", value, "configvalue");
            return this;
        }

        public Criteria andConfigvalueNotEqualTo(String value) {
            addCriterion("CONFIGVALUE <>", value, "configvalue");
            return this;
        }

        public Criteria andConfigvalueGreaterThan(String value) {
            addCriterion("CONFIGVALUE >", value, "configvalue");
            return this;
        }

        public Criteria andConfigvalueGreaterThanOrEqualTo(String value) {
            addCriterion("CONFIGVALUE >=", value, "configvalue");
            return this;
        }

        public Criteria andConfigvalueLessThan(String value) {
            addCriterion("CONFIGVALUE <", value, "configvalue");
            return this;
        }

        public Criteria andConfigvalueLessThanOrEqualTo(String value) {
            addCriterion("CONFIGVALUE <=", value, "configvalue");
            return this;
        }

        public Criteria andConfigvalueLike(String value) {
            addCriterion("CONFIGVALUE like", value, "configvalue");
            return this;
        }

        public Criteria andConfigvalueNotLike(String value) {
            addCriterion("CONFIGVALUE not like", value, "configvalue");
            return this;
        }

        public Criteria andConfigvalueIn(List values) {
            addCriterion("CONFIGVALUE in", values, "configvalue");
            return this;
        }

        public Criteria andConfigvalueNotIn(List values) {
            addCriterion("CONFIGVALUE not in", values, "configvalue");
            return this;
        }

        public Criteria andConfigvalueBetween(String value1, String value2) {
            addCriterion("CONFIGVALUE between", value1, value2, "configvalue");
            return this;
        }

        public Criteria andConfigvalueNotBetween(String value1, String value2) {
            addCriterion("CONFIGVALUE not between", value1, value2, "configvalue");
            return this;
        }

        public Criteria andConfigmemoIsNull() {
            addCriterion("CONFIGMEMO is null");
            return this;
        }

        public Criteria andConfigmemoIsNotNull() {
            addCriterion("CONFIGMEMO is not null");
            return this;
        }

        public Criteria andConfigmemoEqualTo(String value) {
            addCriterion("CONFIGMEMO =", value, "configmemo");
            return this;
        }

        public Criteria andConfigmemoNotEqualTo(String value) {
            addCriterion("CONFIGMEMO <>", value, "configmemo");
            return this;
        }

        public Criteria andConfigmemoGreaterThan(String value) {
            addCriterion("CONFIGMEMO >", value, "configmemo");
            return this;
        }

        public Criteria andConfigmemoGreaterThanOrEqualTo(String value) {
            addCriterion("CONFIGMEMO >=", value, "configmemo");
            return this;
        }

        public Criteria andConfigmemoLessThan(String value) {
            addCriterion("CONFIGMEMO <", value, "configmemo");
            return this;
        }

        public Criteria andConfigmemoLessThanOrEqualTo(String value) {
            addCriterion("CONFIGMEMO <=", value, "configmemo");
            return this;
        }

        public Criteria andConfigmemoLike(String value) {
            addCriterion("CONFIGMEMO like", value, "configmemo");
            return this;
        }

        public Criteria andConfigmemoNotLike(String value) {
            addCriterion("CONFIGMEMO not like", value, "configmemo");
            return this;
        }

        public Criteria andConfigmemoIn(List values) {
            addCriterion("CONFIGMEMO in", values, "configmemo");
            return this;
        }

        public Criteria andConfigmemoNotIn(List values) {
            addCriterion("CONFIGMEMO not in", values, "configmemo");
            return this;
        }

        public Criteria andConfigmemoBetween(String value1, String value2) {
            addCriterion("CONFIGMEMO between", value1, value2, "configmemo");
            return this;
        }

        public Criteria andConfigmemoNotBetween(String value1, String value2) {
            addCriterion("CONFIGMEMO not between", value1, value2, "configmemo");
            return this;
        }
        
        public Criteria andConfignameIsNull() {
            addCriterion("CONFIGNAME is null");
            return this;
        }

        public Criteria andConfignameIsNotNull() {
            addCriterion("CONFIGNAME is not null");
            return this;
        }

        public Criteria andConfignameEqualTo(String value) {
            addCriterion("CONFIGNAME =", value, "configname");
            return this;
        }

        public Criteria andConfignameNotEqualTo(String value) {
            addCriterion("CONFIGNAME <>", value, "configname");
            return this;
        }

        public Criteria andConfignameLike(String value) {
            addCriterion("CONFIGNAME like", value, "configname");
            return this;
        }

        public Criteria andConfignameNotLike(String value) {
            addCriterion("CONFIGNAME not like", value, "configname");
            return this;
        }
    }
}