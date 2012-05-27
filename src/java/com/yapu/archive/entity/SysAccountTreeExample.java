package com.yapu.archive.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysAccountTreeExample {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table SYS_ACCOUNT_TREE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    protected String orderByClause;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table SYS_ACCOUNT_TREE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    protected List oredCriteria;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ACCOUNT_TREE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public SysAccountTreeExample() {
        oredCriteria = new ArrayList();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ACCOUNT_TREE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    protected SysAccountTreeExample(SysAccountTreeExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ACCOUNT_TREE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ACCOUNT_TREE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ACCOUNT_TREE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ACCOUNT_TREE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ACCOUNT_TREE
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
     * This method corresponds to the database table SYS_ACCOUNT_TREE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ACCOUNT_TREE
     *
     * @ibatorgenerated Fri Nov 12 20:49:06 GMT+08:00 2010
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table SYS_ACCOUNT_TREE
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

        public Criteria andAccountTreeIdIsNull() {
            addCriterion("ACCOUNT_TREE_ID is null");
            return this;
        }

        public Criteria andAccountTreeIdIsNotNull() {
            addCriterion("ACCOUNT_TREE_ID is not null");
            return this;
        }

        public Criteria andAccountTreeIdEqualTo(String value) {
            addCriterion("ACCOUNT_TREE_ID =", value, "accountTreeId");
            return this;
        }

        public Criteria andAccountTreeIdNotEqualTo(String value) {
            addCriterion("ACCOUNT_TREE_ID <>", value, "accountTreeId");
            return this;
        }

        public Criteria andAccountTreeIdGreaterThan(String value) {
            addCriterion("ACCOUNT_TREE_ID >", value, "accountTreeId");
            return this;
        }

        public Criteria andAccountTreeIdGreaterThanOrEqualTo(String value) {
            addCriterion("ACCOUNT_TREE_ID >=", value, "accountTreeId");
            return this;
        }

        public Criteria andAccountTreeIdLessThan(String value) {
            addCriterion("ACCOUNT_TREE_ID <", value, "accountTreeId");
            return this;
        }

        public Criteria andAccountTreeIdLessThanOrEqualTo(String value) {
            addCriterion("ACCOUNT_TREE_ID <=", value, "accountTreeId");
            return this;
        }

        public Criteria andAccountTreeIdLike(String value) {
            addCriterion("ACCOUNT_TREE_ID like", value, "accountTreeId");
            return this;
        }

        public Criteria andAccountTreeIdNotLike(String value) {
            addCriterion("ACCOUNT_TREE_ID not like", value, "accountTreeId");
            return this;
        }

        public Criteria andAccountTreeIdIn(List values) {
            addCriterion("ACCOUNT_TREE_ID in", values, "accountTreeId");
            return this;
        }

        public Criteria andAccountTreeIdNotIn(List values) {
            addCriterion("ACCOUNT_TREE_ID not in", values, "accountTreeId");
            return this;
        }

        public Criteria andAccountTreeIdBetween(String value1, String value2) {
            addCriterion("ACCOUNT_TREE_ID between", value1, value2, "accountTreeId");
            return this;
        }

        public Criteria andAccountTreeIdNotBetween(String value1, String value2) {
            addCriterion("ACCOUNT_TREE_ID not between", value1, value2, "accountTreeId");
            return this;
        }

        public Criteria andTreeidIsNull() {
            addCriterion("TREEID is null");
            return this;
        }

        public Criteria andTreeidIsNotNull() {
            addCriterion("TREEID is not null");
            return this;
        }

        public Criteria andTreeidEqualTo(String value) {
            addCriterion("TREEID =", value, "treeid");
            return this;
        }

        public Criteria andTreeidNotEqualTo(String value) {
            addCriterion("TREEID <>", value, "treeid");
            return this;
        }

        public Criteria andTreeidGreaterThan(String value) {
            addCriterion("TREEID >", value, "treeid");
            return this;
        }

        public Criteria andTreeidGreaterThanOrEqualTo(String value) {
            addCriterion("TREEID >=", value, "treeid");
            return this;
        }

        public Criteria andTreeidLessThan(String value) {
            addCriterion("TREEID <", value, "treeid");
            return this;
        }

        public Criteria andTreeidLessThanOrEqualTo(String value) {
            addCriterion("TREEID <=", value, "treeid");
            return this;
        }

        public Criteria andTreeidLike(String value) {
            addCriterion("TREEID like", value, "treeid");
            return this;
        }

        public Criteria andTreeidNotLike(String value) {
            addCriterion("TREEID not like", value, "treeid");
            return this;
        }

        public Criteria andTreeidIn(List values) {
            addCriterion("TREEID in", values, "treeid");
            return this;
        }

        public Criteria andTreeidNotIn(List values) {
            addCriterion("TREEID not in", values, "treeid");
            return this;
        }

        public Criteria andTreeidBetween(String value1, String value2) {
            addCriterion("TREEID between", value1, value2, "treeid");
            return this;
        }

        public Criteria andTreeidNotBetween(String value1, String value2) {
            addCriterion("TREEID not between", value1, value2, "treeid");
            return this;
        }

        public Criteria andAccountidIsNull() {
            addCriterion("ACCOUNTID is null");
            return this;
        }

        public Criteria andAccountidIsNotNull() {
            addCriterion("ACCOUNTID is not null");
            return this;
        }

        public Criteria andAccountidEqualTo(String value) {
            addCriterion("ACCOUNTID =", value, "accountid");
            return this;
        }

        public Criteria andAccountidNotEqualTo(String value) {
            addCriterion("ACCOUNTID <>", value, "accountid");
            return this;
        }

        public Criteria andAccountidGreaterThan(String value) {
            addCriterion("ACCOUNTID >", value, "accountid");
            return this;
        }

        public Criteria andAccountidGreaterThanOrEqualTo(String value) {
            addCriterion("ACCOUNTID >=", value, "accountid");
            return this;
        }

        public Criteria andAccountidLessThan(String value) {
            addCriterion("ACCOUNTID <", value, "accountid");
            return this;
        }

        public Criteria andAccountidLessThanOrEqualTo(String value) {
            addCriterion("ACCOUNTID <=", value, "accountid");
            return this;
        }

        public Criteria andAccountidLike(String value) {
            addCriterion("ACCOUNTID like", value, "accountid");
            return this;
        }

        public Criteria andAccountidNotLike(String value) {
            addCriterion("ACCOUNTID not like", value, "accountid");
            return this;
        }

        public Criteria andAccountidIn(List values) {
            addCriterion("ACCOUNTID in", values, "accountid");
            return this;
        }

        public Criteria andAccountidNotIn(List values) {
            addCriterion("ACCOUNTID not in", values, "accountid");
            return this;
        }

        public Criteria andAccountidBetween(String value1, String value2) {
            addCriterion("ACCOUNTID between", value1, value2, "accountid");
            return this;
        }

        public Criteria andAccountidNotBetween(String value1, String value2) {
            addCriterion("ACCOUNTID not between", value1, value2, "accountid");
            return this;
        }

        public Criteria andFilescanIsNull() {
            addCriterion("FILESCAN is null");
            return this;
        }

        public Criteria andFilescanIsNotNull() {
            addCriterion("FILESCAN is not null");
            return this;
        }

        public Criteria andFilescanEqualTo(Integer value) {
            addCriterion("FILESCAN =", value, "filescan");
            return this;
        }

        public Criteria andFilescanNotEqualTo(Integer value) {
            addCriterion("FILESCAN <>", value, "filescan");
            return this;
        }

        public Criteria andFilescanGreaterThan(Integer value) {
            addCriterion("FILESCAN >", value, "filescan");
            return this;
        }

        public Criteria andFilescanGreaterThanOrEqualTo(Integer value) {
            addCriterion("FILESCAN >=", value, "filescan");
            return this;
        }

        public Criteria andFilescanLessThan(Integer value) {
            addCriterion("FILESCAN <", value, "filescan");
            return this;
        }

        public Criteria andFilescanLessThanOrEqualTo(Integer value) {
            addCriterion("FILESCAN <=", value, "filescan");
            return this;
        }

        public Criteria andFilescanIn(List values) {
            addCriterion("FILESCAN in", values, "filescan");
            return this;
        }

        public Criteria andFilescanNotIn(List values) {
            addCriterion("FILESCAN not in", values, "filescan");
            return this;
        }

        public Criteria andFilescanBetween(Integer value1, Integer value2) {
            addCriterion("FILESCAN between", value1, value2, "filescan");
            return this;
        }

        public Criteria andFilescanNotBetween(Integer value1, Integer value2) {
            addCriterion("FILESCAN not between", value1, value2, "filescan");
            return this;
        }

        public Criteria andFiledownIsNull() {
            addCriterion("FILEDOWN is null");
            return this;
        }

        public Criteria andFiledownIsNotNull() {
            addCriterion("FILEDOWN is not null");
            return this;
        }

        public Criteria andFiledownEqualTo(Integer value) {
            addCriterion("FILEDOWN =", value, "filedown");
            return this;
        }

        public Criteria andFiledownNotEqualTo(Integer value) {
            addCriterion("FILEDOWN <>", value, "filedown");
            return this;
        }

        public Criteria andFiledownGreaterThan(Integer value) {
            addCriterion("FILEDOWN >", value, "filedown");
            return this;
        }

        public Criteria andFiledownGreaterThanOrEqualTo(Integer value) {
            addCriterion("FILEDOWN >=", value, "filedown");
            return this;
        }

        public Criteria andFiledownLessThan(Integer value) {
            addCriterion("FILEDOWN <", value, "filedown");
            return this;
        }

        public Criteria andFiledownLessThanOrEqualTo(Integer value) {
            addCriterion("FILEDOWN <=", value, "filedown");
            return this;
        }

        public Criteria andFiledownIn(List values) {
            addCriterion("FILEDOWN in", values, "filedown");
            return this;
        }

        public Criteria andFiledownNotIn(List values) {
            addCriterion("FILEDOWN not in", values, "filedown");
            return this;
        }

        public Criteria andFiledownBetween(Integer value1, Integer value2) {
            addCriterion("FILEDOWN between", value1, value2, "filedown");
            return this;
        }

        public Criteria andFiledownNotBetween(Integer value1, Integer value2) {
            addCriterion("FILEDOWN not between", value1, value2, "filedown");
            return this;
        }

        public Criteria andFileprintIsNull() {
            addCriterion("FILEPRINT is null");
            return this;
        }

        public Criteria andFileprintIsNotNull() {
            addCriterion("FILEPRINT is not null");
            return this;
        }

        public Criteria andFileprintEqualTo(Integer value) {
            addCriterion("FILEPRINT =", value, "fileprint");
            return this;
        }

        public Criteria andFileprintNotEqualTo(Integer value) {
            addCriterion("FILEPRINT <>", value, "fileprint");
            return this;
        }

        public Criteria andFileprintGreaterThan(Integer value) {
            addCriterion("FILEPRINT >", value, "fileprint");
            return this;
        }

        public Criteria andFileprintGreaterThanOrEqualTo(Integer value) {
            addCriterion("FILEPRINT >=", value, "fileprint");
            return this;
        }

        public Criteria andFileprintLessThan(Integer value) {
            addCriterion("FILEPRINT <", value, "fileprint");
            return this;
        }

        public Criteria andFileprintLessThanOrEqualTo(Integer value) {
            addCriterion("FILEPRINT <=", value, "fileprint");
            return this;
        }

        public Criteria andFileprintIn(List values) {
            addCriterion("FILEPRINT in", values, "fileprint");
            return this;
        }

        public Criteria andFileprintNotIn(List values) {
            addCriterion("FILEPRINT not in", values, "fileprint");
            return this;
        }

        public Criteria andFileprintBetween(Integer value1, Integer value2) {
            addCriterion("FILEPRINT between", value1, value2, "fileprint");
            return this;
        }

        public Criteria andFileprintNotBetween(Integer value1, Integer value2) {
            addCriterion("FILEPRINT not between", value1, value2, "fileprint");
            return this;
        }
    }
}