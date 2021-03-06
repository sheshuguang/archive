package com.yapu.system.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysLoginlogExample {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table SYS_LOGINLOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    protected String orderByClause;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table SYS_LOGINLOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    protected List oredCriteria;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_LOGINLOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public SysLoginlogExample() {
        oredCriteria = new ArrayList();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_LOGINLOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    protected SysLoginlogExample(SysLoginlogExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_LOGINLOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_LOGINLOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_LOGINLOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_LOGINLOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_LOGINLOG
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
     * This method corresponds to the database table SYS_LOGINLOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_LOGINLOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table SYS_LOGINLOG
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

        public Criteria andLoginlogidIsNull() {
            addCriterion("LOGINLOGID is null");
            return this;
        }

        public Criteria andLoginlogidIsNotNull() {
            addCriterion("LOGINLOGID is not null");
            return this;
        }

        public Criteria andLoginlogidEqualTo(String value) {
            addCriterion("LOGINLOGID =", value, "loginlogid");
            return this;
        }

        public Criteria andLoginlogidNotEqualTo(String value) {
            addCriterion("LOGINLOGID <>", value, "loginlogid");
            return this;
        }

        public Criteria andLoginlogidGreaterThan(String value) {
            addCriterion("LOGINLOGID >", value, "loginlogid");
            return this;
        }

        public Criteria andLoginlogidGreaterThanOrEqualTo(String value) {
            addCriterion("LOGINLOGID >=", value, "loginlogid");
            return this;
        }

        public Criteria andLoginlogidLessThan(String value) {
            addCriterion("LOGINLOGID <", value, "loginlogid");
            return this;
        }

        public Criteria andLoginlogidLessThanOrEqualTo(String value) {
            addCriterion("LOGINLOGID <=", value, "loginlogid");
            return this;
        }

        public Criteria andLoginlogidLike(String value) {
            addCriterion("LOGINLOGID like", value, "loginlogid");
            return this;
        }

        public Criteria andLoginlogidNotLike(String value) {
            addCriterion("LOGINLOGID not like", value, "loginlogid");
            return this;
        }

        public Criteria andLoginlogidIn(List values) {
            addCriterion("LOGINLOGID in", values, "loginlogid");
            return this;
        }

        public Criteria andLoginlogidNotIn(List values) {
            addCriterion("LOGINLOGID not in", values, "loginlogid");
            return this;
        }

        public Criteria andLoginlogidBetween(String value1, String value2) {
            addCriterion("LOGINLOGID between", value1, value2, "loginlogid");
            return this;
        }

        public Criteria andLoginlogidNotBetween(String value1, String value2) {
            addCriterion("LOGINLOGID not between", value1, value2, "loginlogid");
            return this;
        }

        public Criteria andAccountcodeIsNull() {
            addCriterion("ACCOUNTCODE is null");
            return this;
        }

        public Criteria andAccountcodeIsNotNull() {
            addCriterion("ACCOUNTCODE is not null");
            return this;
        }

        public Criteria andAccountcodeEqualTo(String value) {
            addCriterion("ACCOUNTCODE =", value, "accountcode");
            return this;
        }

        public Criteria andAccountcodeNotEqualTo(String value) {
            addCriterion("ACCOUNTCODE <>", value, "accountcode");
            return this;
        }

        public Criteria andAccountcodeGreaterThan(String value) {
            addCriterion("ACCOUNTCODE >", value, "accountcode");
            return this;
        }

        public Criteria andAccountcodeGreaterThanOrEqualTo(String value) {
            addCriterion("ACCOUNTCODE >=", value, "accountcode");
            return this;
        }

        public Criteria andAccountcodeLessThan(String value) {
            addCriterion("ACCOUNTCODE <", value, "accountcode");
            return this;
        }

        public Criteria andAccountcodeLessThanOrEqualTo(String value) {
            addCriterion("ACCOUNTCODE <=", value, "accountcode");
            return this;
        }

        public Criteria andAccountcodeLike(String value) {
            addCriterion("ACCOUNTCODE like", value, "accountcode");
            return this;
        }

        public Criteria andAccountcodeNotLike(String value) {
            addCriterion("ACCOUNTCODE not like", value, "accountcode");
            return this;
        }

        public Criteria andAccountcodeIn(List values) {
            addCriterion("ACCOUNTCODE in", values, "accountcode");
            return this;
        }

        public Criteria andAccountcodeNotIn(List values) {
            addCriterion("ACCOUNTCODE not in", values, "accountcode");
            return this;
        }

        public Criteria andAccountcodeBetween(String value1, String value2) {
            addCriterion("ACCOUNTCODE between", value1, value2, "accountcode");
            return this;
        }

        public Criteria andAccountcodeNotBetween(String value1, String value2) {
            addCriterion("ACCOUNTCODE not between", value1, value2, "accountcode");
            return this;
        }

        public Criteria andUsernameIsNull() {
            addCriterion("USERNAME is null");
            return this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("USERNAME is not null");
            return this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("USERNAME =", value, "username");
            return this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("USERNAME <>", value, "username");
            return this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("USERNAME >", value, "username");
            return this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("USERNAME >=", value, "username");
            return this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("USERNAME <", value, "username");
            return this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("USERNAME <=", value, "username");
            return this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("USERNAME like", value, "username");
            return this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("USERNAME not like", value, "username");
            return this;
        }

        public Criteria andUsernameIn(List values) {
            addCriterion("USERNAME in", values, "username");
            return this;
        }

        public Criteria andUsernameNotIn(List values) {
            addCriterion("USERNAME not in", values, "username");
            return this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("USERNAME between", value1, value2, "username");
            return this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("USERNAME not between", value1, value2, "username");
            return this;
        }

        public Criteria andLogintimeIsNull() {
            addCriterion("LOGINTIME is null");
            return this;
        }

        public Criteria andLogintimeIsNotNull() {
            addCriterion("LOGINTIME is not null");
            return this;
        }

        public Criteria andLogintimeEqualTo(String value) {
            addCriterion("LOGINTIME =", value, "logintime");
            return this;
        }

        public Criteria andLogintimeNotEqualTo(String value) {
            addCriterion("LOGINTIME <>", value, "logintime");
            return this;
        }

        public Criteria andLogintimeGreaterThan(String value) {
            addCriterion("LOGINTIME >", value, "logintime");
            return this;
        }

        public Criteria andLogintimeGreaterThanOrEqualTo(String value) {
            addCriterion("LOGINTIME >=", value, "logintime");
            return this;
        }

        public Criteria andLogintimeLessThan(String value) {
            addCriterion("LOGINTIME <", value, "logintime");
            return this;
        }

        public Criteria andLogintimeLessThanOrEqualTo(String value) {
            addCriterion("LOGINTIME <=", value, "logintime");
            return this;
        }

        public Criteria andLogintimeLike(String value) {
            addCriterion("LOGINTIME like", value, "logintime");
            return this;
        }

        public Criteria andLogintimeNotLike(String value) {
            addCriterion("LOGINTIME not like", value, "logintime");
            return this;
        }

        public Criteria andLogintimeIn(List values) {
            addCriterion("LOGINTIME in", values, "logintime");
            return this;
        }

        public Criteria andLogintimeNotIn(List values) {
            addCriterion("LOGINTIME not in", values, "logintime");
            return this;
        }

        public Criteria andLogintimeBetween(String value1, String value2) {
            addCriterion("LOGINTIME between", value1, value2, "logintime");
            return this;
        }

        public Criteria andLogintimeNotBetween(String value1, String value2) {
            addCriterion("LOGINTIME not between", value1, value2, "logintime");
            return this;
        }

        public Criteria andOuttimeIsNull() {
            addCriterion("OUTTIME is null");
            return this;
        }

        public Criteria andOuttimeIsNotNull() {
            addCriterion("OUTTIME is not null");
            return this;
        }

        public Criteria andOuttimeEqualTo(String value) {
            addCriterion("OUTTIME =", value, "outtime");
            return this;
        }

        public Criteria andOuttimeNotEqualTo(String value) {
            addCriterion("OUTTIME <>", value, "outtime");
            return this;
        }

        public Criteria andOuttimeGreaterThan(String value) {
            addCriterion("OUTTIME >", value, "outtime");
            return this;
        }

        public Criteria andOuttimeGreaterThanOrEqualTo(String value) {
            addCriterion("OUTTIME >=", value, "outtime");
            return this;
        }

        public Criteria andOuttimeLessThan(String value) {
            addCriterion("OUTTIME <", value, "outtime");
            return this;
        }

        public Criteria andOuttimeLessThanOrEqualTo(String value) {
            addCriterion("OUTTIME <=", value, "outtime");
            return this;
        }

        public Criteria andOuttimeLike(String value) {
            addCriterion("OUTTIME like", value, "outtime");
            return this;
        }

        public Criteria andOuttimeNotLike(String value) {
            addCriterion("OUTTIME not like", value, "outtime");
            return this;
        }

        public Criteria andOuttimeIn(List values) {
            addCriterion("OUTTIME in", values, "outtime");
            return this;
        }

        public Criteria andOuttimeNotIn(List values) {
            addCriterion("OUTTIME not in", values, "outtime");
            return this;
        }

        public Criteria andOuttimeBetween(String value1, String value2) {
            addCriterion("OUTTIME between", value1, value2, "outtime");
            return this;
        }

        public Criteria andOuttimeNotBetween(String value1, String value2) {
            addCriterion("OUTTIME not between", value1, value2, "outtime");
            return this;
        }

        public Criteria andLoginipIsNull() {
            addCriterion("LOGINIP is null");
            return this;
        }

        public Criteria andLoginipIsNotNull() {
            addCriterion("LOGINIP is not null");
            return this;
        }

        public Criteria andLoginipEqualTo(String value) {
            addCriterion("LOGINIP =", value, "loginip");
            return this;
        }

        public Criteria andLoginipNotEqualTo(String value) {
            addCriterion("LOGINIP <>", value, "loginip");
            return this;
        }

        public Criteria andLoginipGreaterThan(String value) {
            addCriterion("LOGINIP >", value, "loginip");
            return this;
        }

        public Criteria andLoginipGreaterThanOrEqualTo(String value) {
            addCriterion("LOGINIP >=", value, "loginip");
            return this;
        }

        public Criteria andLoginipLessThan(String value) {
            addCriterion("LOGINIP <", value, "loginip");
            return this;
        }

        public Criteria andLoginipLessThanOrEqualTo(String value) {
            addCriterion("LOGINIP <=", value, "loginip");
            return this;
        }

        public Criteria andLoginipLike(String value) {
            addCriterion("LOGINIP like", value, "loginip");
            return this;
        }

        public Criteria andLoginipNotLike(String value) {
            addCriterion("LOGINIP not like", value, "loginip");
            return this;
        }

        public Criteria andLoginipIn(List values) {
            addCriterion("LOGINIP in", values, "loginip");
            return this;
        }

        public Criteria andLoginipNotIn(List values) {
            addCriterion("LOGINIP not in", values, "loginip");
            return this;
        }

        public Criteria andLoginipBetween(String value1, String value2) {
            addCriterion("LOGINIP between", value1, value2, "loginip");
            return this;
        }

        public Criteria andLoginipNotBetween(String value1, String value2) {
            addCriterion("LOGINIP not between", value1, value2, "loginip");
            return this;
        }
    }
}