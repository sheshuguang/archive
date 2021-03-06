package com.yapu.system.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysRoleExample {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table SYS_ROLE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    protected String orderByClause;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table SYS_ROLE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    protected List oredCriteria;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ROLE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public SysRoleExample() {
        oredCriteria = new ArrayList();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ROLE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    protected SysRoleExample(SysRoleExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ROLE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ROLE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ROLE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ROLE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ROLE
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
     * This method corresponds to the database table SYS_ROLE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ROLE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table SYS_ROLE
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

        public Criteria andRoleidIsNull() {
            addCriterion("ROLEID is null");
            return this;
        }

        public Criteria andRoleidIsNotNull() {
            addCriterion("ROLEID is not null");
            return this;
        }

        public Criteria andRoleidEqualTo(String value) {
            addCriterion("ROLEID =", value, "roleid");
            return this;
        }

        public Criteria andRoleidNotEqualTo(String value) {
            addCriterion("ROLEID <>", value, "roleid");
            return this;
        }

        public Criteria andRoleidGreaterThan(String value) {
            addCriterion("ROLEID >", value, "roleid");
            return this;
        }

        public Criteria andRoleidGreaterThanOrEqualTo(String value) {
            addCriterion("ROLEID >=", value, "roleid");
            return this;
        }

        public Criteria andRoleidLessThan(String value) {
            addCriterion("ROLEID <", value, "roleid");
            return this;
        }

        public Criteria andRoleidLessThanOrEqualTo(String value) {
            addCriterion("ROLEID <=", value, "roleid");
            return this;
        }

        public Criteria andRoleidLike(String value) {
            addCriterion("ROLEID like", value, "roleid");
            return this;
        }

        public Criteria andRoleidNotLike(String value) {
            addCriterion("ROLEID not like", value, "roleid");
            return this;
        }

        public Criteria andRoleidIn(List values) {
            addCriterion("ROLEID in", values, "roleid");
            return this;
        }

        public Criteria andRoleidNotIn(List values) {
            addCriterion("ROLEID not in", values, "roleid");
            return this;
        }

        public Criteria andRoleidBetween(String value1, String value2) {
            addCriterion("ROLEID between", value1, value2, "roleid");
            return this;
        }

        public Criteria andRoleidNotBetween(String value1, String value2) {
            addCriterion("ROLEID not between", value1, value2, "roleid");
            return this;
        }

        public Criteria andRolenameIsNull() {
            addCriterion("ROLENAME is null");
            return this;
        }

        public Criteria andRolenameIsNotNull() {
            addCriterion("ROLENAME is not null");
            return this;
        }

        public Criteria andRolenameEqualTo(String value) {
            addCriterion("ROLENAME =", value, "rolename");
            return this;
        }

        public Criteria andRolenameNotEqualTo(String value) {
            addCriterion("ROLENAME <>", value, "rolename");
            return this;
        }

        public Criteria andRolenameGreaterThan(String value) {
            addCriterion("ROLENAME >", value, "rolename");
            return this;
        }

        public Criteria andRolenameGreaterThanOrEqualTo(String value) {
            addCriterion("ROLENAME >=", value, "rolename");
            return this;
        }

        public Criteria andRolenameLessThan(String value) {
            addCriterion("ROLENAME <", value, "rolename");
            return this;
        }

        public Criteria andRolenameLessThanOrEqualTo(String value) {
            addCriterion("ROLENAME <=", value, "rolename");
            return this;
        }

        public Criteria andRolenameLike(String value) {
            addCriterion("ROLENAME like", value, "rolename");
            return this;
        }

        public Criteria andRolenameNotLike(String value) {
            addCriterion("ROLENAME not like", value, "rolename");
            return this;
        }

        public Criteria andRolenameIn(List values) {
            addCriterion("ROLENAME in", values, "rolename");
            return this;
        }

        public Criteria andRolenameNotIn(List values) {
            addCriterion("ROLENAME not in", values, "rolename");
            return this;
        }

        public Criteria andRolenameBetween(String value1, String value2) {
            addCriterion("ROLENAME between", value1, value2, "rolename");
            return this;
        }

        public Criteria andRolenameNotBetween(String value1, String value2) {
            addCriterion("ROLENAME not between", value1, value2, "rolename");
            return this;
        }

        public Criteria andRolememoIsNull() {
            addCriterion("ROLEMEMO is null");
            return this;
        }

        public Criteria andRolememoIsNotNull() {
            addCriterion("ROLEMEMO is not null");
            return this;
        }

        public Criteria andRolememoEqualTo(String value) {
            addCriterion("ROLEMEMO =", value, "rolememo");
            return this;
        }

        public Criteria andRolememoNotEqualTo(String value) {
            addCriterion("ROLEMEMO <>", value, "rolememo");
            return this;
        }

        public Criteria andRolememoGreaterThan(String value) {
            addCriterion("ROLEMEMO >", value, "rolememo");
            return this;
        }

        public Criteria andRolememoGreaterThanOrEqualTo(String value) {
            addCriterion("ROLEMEMO >=", value, "rolememo");
            return this;
        }

        public Criteria andRolememoLessThan(String value) {
            addCriterion("ROLEMEMO <", value, "rolememo");
            return this;
        }

        public Criteria andRolememoLessThanOrEqualTo(String value) {
            addCriterion("ROLEMEMO <=", value, "rolememo");
            return this;
        }

        public Criteria andRolememoLike(String value) {
            addCriterion("ROLEMEMO like", value, "rolememo");
            return this;
        }

        public Criteria andRolememoNotLike(String value) {
            addCriterion("ROLEMEMO not like", value, "rolememo");
            return this;
        }

        public Criteria andRolememoIn(List values) {
            addCriterion("ROLEMEMO in", values, "rolememo");
            return this;
        }

        public Criteria andRolememoNotIn(List values) {
            addCriterion("ROLEMEMO not in", values, "rolememo");
            return this;
        }

        public Criteria andRolememoBetween(String value1, String value2) {
            addCriterion("ROLEMEMO between", value1, value2, "rolememo");
            return this;
        }

        public Criteria andRolememoNotBetween(String value1, String value2) {
            addCriterion("ROLEMEMO not between", value1, value2, "rolememo");
            return this;
        }
    }
}