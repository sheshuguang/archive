package com.yapu.system.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysOperatelogExample {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table SYS_OPERATELOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    protected String orderByClause;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table SYS_OPERATELOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    protected List oredCriteria;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_OPERATELOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public SysOperatelogExample() {
        oredCriteria = new ArrayList();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_OPERATELOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    protected SysOperatelogExample(SysOperatelogExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_OPERATELOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_OPERATELOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_OPERATELOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_OPERATELOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_OPERATELOG
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
     * This method corresponds to the database table SYS_OPERATELOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_OPERATELOG
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table SYS_OPERATELOG
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

        public Criteria andOperatelogidIsNull() {
            addCriterion("OPERATELOGID is null");
            return this;
        }

        public Criteria andOperatelogidIsNotNull() {
            addCriterion("OPERATELOGID is not null");
            return this;
        }

        public Criteria andOperatelogidEqualTo(String value) {
            addCriterion("OPERATELOGID =", value, "operatelogid");
            return this;
        }

        public Criteria andOperatelogidNotEqualTo(String value) {
            addCriterion("OPERATELOGID <>", value, "operatelogid");
            return this;
        }

        public Criteria andOperatelogidGreaterThan(String value) {
            addCriterion("OPERATELOGID >", value, "operatelogid");
            return this;
        }

        public Criteria andOperatelogidGreaterThanOrEqualTo(String value) {
            addCriterion("OPERATELOGID >=", value, "operatelogid");
            return this;
        }

        public Criteria andOperatelogidLessThan(String value) {
            addCriterion("OPERATELOGID <", value, "operatelogid");
            return this;
        }

        public Criteria andOperatelogidLessThanOrEqualTo(String value) {
            addCriterion("OPERATELOGID <=", value, "operatelogid");
            return this;
        }

        public Criteria andOperatelogidLike(String value) {
            addCriterion("OPERATELOGID like", value, "operatelogid");
            return this;
        }

        public Criteria andOperatelogidNotLike(String value) {
            addCriterion("OPERATELOGID not like", value, "operatelogid");
            return this;
        }

        public Criteria andOperatelogidIn(List values) {
            addCriterion("OPERATELOGID in", values, "operatelogid");
            return this;
        }

        public Criteria andOperatelogidNotIn(List values) {
            addCriterion("OPERATELOGID not in", values, "operatelogid");
            return this;
        }

        public Criteria andOperatelogidBetween(String value1, String value2) {
            addCriterion("OPERATELOGID between", value1, value2, "operatelogid");
            return this;
        }

        public Criteria andOperatelogidNotBetween(String value1, String value2) {
            addCriterion("OPERATELOGID not between", value1, value2, "operatelogid");
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

        public Criteria andFunnameIsNull() {
            addCriterion("FUNNAME is null");
            return this;
        }

        public Criteria andFunnameIsNotNull() {
            addCriterion("FUNNAME is not null");
            return this;
        }

        public Criteria andFunnameEqualTo(String value) {
            addCriterion("FUNNAME =", value, "funname");
            return this;
        }

        public Criteria andFunnameNotEqualTo(String value) {
            addCriterion("FUNNAME <>", value, "funname");
            return this;
        }

        public Criteria andFunnameGreaterThan(String value) {
            addCriterion("FUNNAME >", value, "funname");
            return this;
        }

        public Criteria andFunnameGreaterThanOrEqualTo(String value) {
            addCriterion("FUNNAME >=", value, "funname");
            return this;
        }

        public Criteria andFunnameLessThan(String value) {
            addCriterion("FUNNAME <", value, "funname");
            return this;
        }

        public Criteria andFunnameLessThanOrEqualTo(String value) {
            addCriterion("FUNNAME <=", value, "funname");
            return this;
        }

        public Criteria andFunnameLike(String value) {
            addCriterion("FUNNAME like", value, "funname");
            return this;
        }

        public Criteria andFunnameNotLike(String value) {
            addCriterion("FUNNAME not like", value, "funname");
            return this;
        }

        public Criteria andFunnameIn(List values) {
            addCriterion("FUNNAME in", values, "funname");
            return this;
        }

        public Criteria andFunnameNotIn(List values) {
            addCriterion("FUNNAME not in", values, "funname");
            return this;
        }

        public Criteria andFunnameBetween(String value1, String value2) {
            addCriterion("FUNNAME between", value1, value2, "funname");
            return this;
        }

        public Criteria andFunnameNotBetween(String value1, String value2) {
            addCriterion("FUNNAME not between", value1, value2, "funname");
            return this;
        }

        public Criteria andOperatetimeIsNull() {
            addCriterion("OPERATETIME is null");
            return this;
        }

        public Criteria andOperatetimeIsNotNull() {
            addCriterion("OPERATETIME is not null");
            return this;
        }

        public Criteria andOperatetimeEqualTo(String value) {
            addCriterion("OPERATETIME =", value, "operatetime");
            return this;
        }

        public Criteria andOperatetimeNotEqualTo(String value) {
            addCriterion("OPERATETIME <>", value, "operatetime");
            return this;
        }

        public Criteria andOperatetimeGreaterThan(String value) {
            addCriterion("OPERATETIME >", value, "operatetime");
            return this;
        }

        public Criteria andOperatetimeGreaterThanOrEqualTo(String value) {
            addCriterion("OPERATETIME >=", value, "operatetime");
            return this;
        }

        public Criteria andOperatetimeLessThan(String value) {
            addCriterion("OPERATETIME <", value, "operatetime");
            return this;
        }

        public Criteria andOperatetimeLessThanOrEqualTo(String value) {
            addCriterion("OPERATETIME <=", value, "operatetime");
            return this;
        }

        public Criteria andOperatetimeLike(String value) {
            addCriterion("OPERATETIME like", value, "operatetime");
            return this;
        }

        public Criteria andOperatetimeNotLike(String value) {
            addCriterion("OPERATETIME not like", value, "operatetime");
            return this;
        }

        public Criteria andOperatetimeIn(List values) {
            addCriterion("OPERATETIME in", values, "operatetime");
            return this;
        }

        public Criteria andOperatetimeNotIn(List values) {
            addCriterion("OPERATETIME not in", values, "operatetime");
            return this;
        }

        public Criteria andOperatetimeBetween(String value1, String value2) {
            addCriterion("OPERATETIME between", value1, value2, "operatetime");
            return this;
        }

        public Criteria andOperatetimeNotBetween(String value1, String value2) {
            addCriterion("OPERATETIME not between", value1, value2, "operatetime");
            return this;
        }

        public Criteria andLogdocIsNull() {
            addCriterion("LOGDOC is null");
            return this;
        }

        public Criteria andLogdocIsNotNull() {
            addCriterion("LOGDOC is not null");
            return this;
        }

        public Criteria andLogdocEqualTo(String value) {
            addCriterion("LOGDOC =", value, "logdoc");
            return this;
        }

        public Criteria andLogdocNotEqualTo(String value) {
            addCriterion("LOGDOC <>", value, "logdoc");
            return this;
        }

        public Criteria andLogdocGreaterThan(String value) {
            addCriterion("LOGDOC >", value, "logdoc");
            return this;
        }

        public Criteria andLogdocGreaterThanOrEqualTo(String value) {
            addCriterion("LOGDOC >=", value, "logdoc");
            return this;
        }

        public Criteria andLogdocLessThan(String value) {
            addCriterion("LOGDOC <", value, "logdoc");
            return this;
        }

        public Criteria andLogdocLessThanOrEqualTo(String value) {
            addCriterion("LOGDOC <=", value, "logdoc");
            return this;
        }

        public Criteria andLogdocLike(String value) {
            addCriterion("LOGDOC like", value, "logdoc");
            return this;
        }

        public Criteria andLogdocNotLike(String value) {
            addCriterion("LOGDOC not like", value, "logdoc");
            return this;
        }

        public Criteria andLogdocIn(List values) {
            addCriterion("LOGDOC in", values, "logdoc");
            return this;
        }

        public Criteria andLogdocNotIn(List values) {
            addCriterion("LOGDOC not in", values, "logdoc");
            return this;
        }

        public Criteria andLogdocBetween(String value1, String value2) {
            addCriterion("LOGDOC between", value1, value2, "logdoc");
            return this;
        }

        public Criteria andLogdocNotBetween(String value1, String value2) {
            addCriterion("LOGDOC not between", value1, value2, "logdoc");
            return this;
        }
    }
}