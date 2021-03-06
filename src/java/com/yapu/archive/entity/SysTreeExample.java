package com.yapu.archive.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysTreeExample {
    
    protected String orderByClause;

    protected List oredCriteria;

    public SysTreeExample() {
        oredCriteria = new ArrayList();
    }

    protected SysTreeExample(SysTreeExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public List getOredCriteria() {
        return oredCriteria;
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

        public Criteria andParentidIsNull() {
            addCriterion("PARENTID is null");
            return this;
        }

        public Criteria andParentidIsNotNull() {
            addCriterion("PARENTID is not null");
            return this;
        }

        public Criteria andParentidEqualTo(String value) {
            addCriterion("PARENTID =", value, "parentid");
            return this;
        }

        public Criteria andParentidNotEqualTo(String value) {
            addCriterion("PARENTID <>", value, "parentid");
            return this;
        }

        public Criteria andParentidGreaterThan(String value) {
            addCriterion("PARENTID >", value, "parentid");
            return this;
        }

        public Criteria andParentidGreaterThanOrEqualTo(String value) {
            addCriterion("PARENTID >=", value, "parentid");
            return this;
        }

        public Criteria andParentidLessThan(String value) {
            addCriterion("PARENTID <", value, "parentid");
            return this;
        }

        public Criteria andParentidLessThanOrEqualTo(String value) {
            addCriterion("PARENTID <=", value, "parentid");
            return this;
        }

        public Criteria andParentidLike(String value) {
            addCriterion("PARENTID like", value, "parentid");
            return this;
        }

        public Criteria andParentidNotLike(String value) {
            addCriterion("PARENTID not like", value, "parentid");
            return this;
        }

        public Criteria andParentidIn(List values) {
            addCriterion("PARENTID in", values, "parentid");
            return this;
        }

        public Criteria andParentidNotIn(List values) {
            addCriterion("PARENTID not in", values, "parentid");
            return this;
        }

        public Criteria andParentidBetween(String value1, String value2) {
            addCriterion("PARENTID between", value1, value2, "parentid");
            return this;
        }

        public Criteria andParentidNotBetween(String value1, String value2) {
            addCriterion("PARENTID not between", value1, value2, "parentid");
            return this;
        }

        public Criteria andTreenameIsNull() {
            addCriterion("TREENAME is null");
            return this;
        }

        public Criteria andTreenameIsNotNull() {
            addCriterion("TREENAME is not null");
            return this;
        }

        public Criteria andTreenameEqualTo(String value) {
            addCriterion("TREENAME =", value, "treename");
            return this;
        }

        public Criteria andTreenameNotEqualTo(String value) {
            addCriterion("TREENAME <>", value, "treename");
            return this;
        }

        public Criteria andTreenameGreaterThan(String value) {
            addCriterion("TREENAME >", value, "treename");
            return this;
        }

        public Criteria andTreenameGreaterThanOrEqualTo(String value) {
            addCriterion("TREENAME >=", value, "treename");
            return this;
        }

        public Criteria andTreenameLessThan(String value) {
            addCriterion("TREENAME <", value, "treename");
            return this;
        }

        public Criteria andTreenameLessThanOrEqualTo(String value) {
            addCriterion("TREENAME <=", value, "treename");
            return this;
        }

        public Criteria andTreenameLike(String value) {
            addCriterion("TREENAME like", value, "treename");
            return this;
        }

        public Criteria andTreenameNotLike(String value) {
            addCriterion("TREENAME not like", value, "treename");
            return this;
        }

        public Criteria andTreenameIn(List values) {
            addCriterion("TREENAME in", values, "treename");
            return this;
        }

        public Criteria andTreenameNotIn(List values) {
            addCriterion("TREENAME not in", values, "treename");
            return this;
        }

        public Criteria andTreenameBetween(String value1, String value2) {
            addCriterion("TREENAME between", value1, value2, "treename");
            return this;
        }

        public Criteria andTreenameNotBetween(String value1, String value2) {
            addCriterion("TREENAME not between", value1, value2, "treename");
            return this;
        }

        public Criteria andTreetypeIsNull() {
            addCriterion("TREETYPE is null");
            return this;
        }

        public Criteria andTreetypeIsNotNull() {
            addCriterion("TREETYPE is not null");
            return this;
        }

        public Criteria andTreetypeEqualTo(String value) {
            addCriterion("TREETYPE =", value, "treetype");
            return this;
        }

        public Criteria andTreetypeNotEqualTo(String value) {
            addCriterion("TREETYPE <>", value, "treetype");
            return this;
        }

        public Criteria andTreetypeGreaterThan(String value) {
            addCriterion("TREETYPE >", value, "treetype");
            return this;
        }

        public Criteria andTreetypeGreaterThanOrEqualTo(String value) {
            addCriterion("TREETYPE >=", value, "treetype");
            return this;
        }

        public Criteria andTreetypeLessThan(String value) {
            addCriterion("TREETYPE <", value, "treetype");
            return this;
        }

        public Criteria andTreetypeLessThanOrEqualTo(String value) {
            addCriterion("TREETYPE <=", value, "treetype");
            return this;
        }

        public Criteria andTreetypeLike(String value) {
            addCriterion("TREETYPE like", value, "treetype");
            return this;
        }

        public Criteria andTreetypeNotLike(String value) {
            addCriterion("TREETYPE not like", value, "treetype");
            return this;
        }

        public Criteria andTreetypeIn(List values) {
            addCriterion("TREETYPE in", values, "treetype");
            return this;
        }

        public Criteria andTreetypeNotIn(List values) {
            addCriterion("TREETYPE not in", values, "treetype");
            return this;
        }

        public Criteria andTreetypeBetween(String value1, String value2) {
            addCriterion("TREETYPE between", value1, value2, "treetype");
            return this;
        }

        public Criteria andTreetypeNotBetween(String value1, String value2) {
            addCriterion("TREETYPE not between", value1, value2, "treetype");
            return this;
        }

        public Criteria andTreenodeIsNull() {
            addCriterion("TREENODE is null");
            return this;
        }

        public Criteria andTreenodeIsNotNull() {
            addCriterion("TREENODE is not null");
            return this;
        }

        public Criteria andTreenodeEqualTo(String value) {
            addCriterion("TREENODE =", value, "treenode");
            return this;
        }

        public Criteria andTreenodeNotEqualTo(String value) {
            addCriterion("TREENODE <>", value, "treenode");
            return this;
        }

        public Criteria andTreenodeGreaterThan(String value) {
            addCriterion("TREENODE >", value, "treenode");
            return this;
        }

        public Criteria andTreenodeGreaterThanOrEqualTo(String value) {
            addCriterion("TREENODE >=", value, "treenode");
            return this;
        }

        public Criteria andTreenodeLessThan(String value) {
            addCriterion("TREENODE <", value, "treenode");
            return this;
        }

        public Criteria andTreenodeLessThanOrEqualTo(String value) {
            addCriterion("TREENODE <=", value, "treenode");
            return this;
        }

        public Criteria andTreenodeLike(String value) {
            addCriterion("TREENODE like", value, "treenode");
            return this;
        }

        public Criteria andTreenodeNotLike(String value) {
            addCriterion("TREENODE not like", value, "treenode");
            return this;
        }

        public Criteria andTreenodeIn(List values) {
            addCriterion("TREENODE in", values, "treenode");
            return this;
        }

        public Criteria andTreenodeNotIn(List values) {
            addCriterion("TREENODE not in", values, "treenode");
            return this;
        }

        public Criteria andTreenodeBetween(String value1, String value2) {
            addCriterion("TREENODE between", value1, value2, "treenode");
            return this;
        }

        public Criteria andTreenodeNotBetween(String value1, String value2) {
            addCriterion("TREENODE not between", value1, value2, "treenode");
            return this;
        }
    }
}