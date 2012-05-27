package com.yapu.archive.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysTempletfieldExample {
   
    protected String orderByClause;
    protected List oredCriteria;

    public SysTempletfieldExample() {
        oredCriteria = new ArrayList();
    }

    protected SysTempletfieldExample(SysTempletfieldExample example) {
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

        public Criteria andTempletfieldidIsNull() {
            addCriterion("TEMPLETFIELDID is null");
            return this;
        }

        public Criteria andTempletfieldidIsNotNull() {
            addCriterion("TEMPLETFIELDID is not null");
            return this;
        }

        public Criteria andTempletfieldidEqualTo(String value) {
            addCriterion("TEMPLETFIELDID =", value, "templetfieldid");
            return this;
        }

        public Criteria andTempletfieldidNotEqualTo(String value) {
            addCriterion("TEMPLETFIELDID <>", value, "templetfieldid");
            return this;
        }

        public Criteria andTempletfieldidGreaterThan(String value) {
            addCriterion("TEMPLETFIELDID >", value, "templetfieldid");
            return this;
        }

        public Criteria andTempletfieldidGreaterThanOrEqualTo(String value) {
            addCriterion("TEMPLETFIELDID >=", value, "templetfieldid");
            return this;
        }

        public Criteria andTempletfieldidLessThan(String value) {
            addCriterion("TEMPLETFIELDID <", value, "templetfieldid");
            return this;
        }

        public Criteria andTempletfieldidLessThanOrEqualTo(String value) {
            addCriterion("TEMPLETFIELDID <=", value, "templetfieldid");
            return this;
        }

        public Criteria andTempletfieldidLike(String value) {
            addCriterion("TEMPLETFIELDID like", value, "templetfieldid");
            return this;
        }

        public Criteria andTempletfieldidNotLike(String value) {
            addCriterion("TEMPLETFIELDID not like", value, "templetfieldid");
            return this;
        }

        public Criteria andTempletfieldidIn(List values) {
            addCriterion("TEMPLETFIELDID in", values, "templetfieldid");
            return this;
        }

        public Criteria andTempletfieldidNotIn(List values) {
            addCriterion("TEMPLETFIELDID not in", values, "templetfieldid");
            return this;
        }

        public Criteria andTempletfieldidBetween(String value1, String value2) {
            addCriterion("TEMPLETFIELDID between", value1, value2, "templetfieldid");
            return this;
        }

        public Criteria andTempletfieldidNotBetween(String value1, String value2) {
            addCriterion("TEMPLETFIELDID not between", value1, value2, "templetfieldid");
            return this;
        }

        public Criteria andEnglishnameIsNull() {
            addCriterion("ENGLISHNAME is null");
            return this;
        }

        public Criteria andEnglishnameIsNotNull() {
            addCriterion("ENGLISHNAME is not null");
            return this;
        }

        public Criteria andEnglishnameEqualTo(String value) {
            addCriterion("ENGLISHNAME =", value, "englishname");
            return this;
        }

        public Criteria andEnglishnameNotEqualTo(String value) {
            addCriterion("ENGLISHNAME <>", value, "englishname");
            return this;
        }

        public Criteria andEnglishnameGreaterThan(String value) {
            addCriterion("ENGLISHNAME >", value, "englishname");
            return this;
        }

        public Criteria andEnglishnameGreaterThanOrEqualTo(String value) {
            addCriterion("ENGLISHNAME >=", value, "englishname");
            return this;
        }

        public Criteria andEnglishnameLessThan(String value) {
            addCriterion("ENGLISHNAME <", value, "englishname");
            return this;
        }

        public Criteria andEnglishnameLessThanOrEqualTo(String value) {
            addCriterion("ENGLISHNAME <=", value, "englishname");
            return this;
        }

        public Criteria andEnglishnameLike(String value) {
            addCriterion("ENGLISHNAME like", value, "englishname");
            return this;
        }

        public Criteria andEnglishnameNotLike(String value) {
            addCriterion("ENGLISHNAME not like", value, "englishname");
            return this;
        }

        public Criteria andEnglishnameIn(List values) {
            addCriterion("ENGLISHNAME in", values, "englishname");
            return this;
        }

        public Criteria andEnglishnameNotIn(List values) {
            addCriterion("ENGLISHNAME not in", values, "englishname");
            return this;
        }

        public Criteria andEnglishnameBetween(String value1, String value2) {
            addCriterion("ENGLISHNAME between", value1, value2, "englishname");
            return this;
        }

        public Criteria andEnglishnameNotBetween(String value1, String value2) {
            addCriterion("ENGLISHNAME not between", value1, value2, "englishname");
            return this;
        }

        public Criteria andChinesenameIsNull() {
            addCriterion("CHINESENAME is null");
            return this;
        }

        public Criteria andChinesenameIsNotNull() {
            addCriterion("CHINESENAME is not null");
            return this;
        }

        public Criteria andChinesenameEqualTo(String value) {
            addCriterion("CHINESENAME =", value, "chinesename");
            return this;
        }

        public Criteria andChinesenameNotEqualTo(String value) {
            addCriterion("CHINESENAME <>", value, "chinesename");
            return this;
        }

        public Criteria andChinesenameGreaterThan(String value) {
            addCriterion("CHINESENAME >", value, "chinesename");
            return this;
        }

        public Criteria andChinesenameGreaterThanOrEqualTo(String value) {
            addCriterion("CHINESENAME >=", value, "chinesename");
            return this;
        }

        public Criteria andChinesenameLessThan(String value) {
            addCriterion("CHINESENAME <", value, "chinesename");
            return this;
        }

        public Criteria andChinesenameLessThanOrEqualTo(String value) {
            addCriterion("CHINESENAME <=", value, "chinesename");
            return this;
        }

        public Criteria andChinesenameLike(String value) {
            addCriterion("CHINESENAME like", value, "chinesename");
            return this;
        }

        public Criteria andChinesenameNotLike(String value) {
            addCriterion("CHINESENAME not like", value, "chinesename");
            return this;
        }

        public Criteria andChinesenameIn(List values) {
            addCriterion("CHINESENAME in", values, "chinesename");
            return this;
        }

        public Criteria andChinesenameNotIn(List values) {
            addCriterion("CHINESENAME not in", values, "chinesename");
            return this;
        }

        public Criteria andChinesenameBetween(String value1, String value2) {
            addCriterion("CHINESENAME between", value1, value2, "chinesename");
            return this;
        }

        public Criteria andChinesenameNotBetween(String value1, String value2) {
            addCriterion("CHINESENAME not between", value1, value2, "chinesename");
            return this;
        }

        public Criteria andFieldsizeIsNull() {
            addCriterion("FIELDSIZE is null");
            return this;
        }

        public Criteria andFieldsizeIsNotNull() {
            addCriterion("FIELDSIZE is not null");
            return this;
        }

        public Criteria andFieldsizeEqualTo(Integer value) {
            addCriterion("FIELDSIZE =", value, "fieldsize");
            return this;
        }

        public Criteria andFieldsizeNotEqualTo(Integer value) {
            addCriterion("FIELDSIZE <>", value, "fieldsize");
            return this;
        }

        public Criteria andFieldsizeGreaterThan(Integer value) {
            addCriterion("FIELDSIZE >", value, "fieldsize");
            return this;
        }

        public Criteria andFieldsizeGreaterThanOrEqualTo(Integer value) {
            addCriterion("FIELDSIZE >=", value, "fieldsize");
            return this;
        }

        public Criteria andFieldsizeLessThan(Integer value) {
            addCriterion("FIELDSIZE <", value, "fieldsize");
            return this;
        }

        public Criteria andFieldsizeLessThanOrEqualTo(Integer value) {
            addCriterion("FIELDSIZE <=", value, "fieldsize");
            return this;
        }

        public Criteria andFieldsizeIn(List values) {
            addCriterion("FIELDSIZE in", values, "fieldsize");
            return this;
        }

        public Criteria andFieldsizeNotIn(List values) {
            addCriterion("FIELDSIZE not in", values, "fieldsize");
            return this;
        }

        public Criteria andFieldsizeBetween(Integer value1, Integer value2) {
            addCriterion("FIELDSIZE between", value1, value2, "fieldsize");
            return this;
        }

        public Criteria andFieldsizeNotBetween(Integer value1, Integer value2) {
            addCriterion("FIELDSIZE not between", value1, value2, "fieldsize");
            return this;
        }

        public Criteria andFieldtypeIsNull() {
            addCriterion("FIELDTYPE is null");
            return this;
        }

        public Criteria andFieldtypeIsNotNull() {
            addCriterion("FIELDTYPE is not null");
            return this;
        }

        public Criteria andFieldtypeEqualTo(String value) {
            addCriterion("FIELDTYPE =", value, "fieldtype");
            return this;
        }

        public Criteria andFieldtypeNotEqualTo(String value) {
            addCriterion("FIELDTYPE <>", value, "fieldtype");
            return this;
        }

        public Criteria andFieldtypeGreaterThan(String value) {
            addCriterion("FIELDTYPE >", value, "fieldtype");
            return this;
        }

        public Criteria andFieldtypeGreaterThanOrEqualTo(String value) {
            addCriterion("FIELDTYPE >=", value, "fieldtype");
            return this;
        }

        public Criteria andFieldtypeLessThan(String value) {
            addCriterion("FIELDTYPE <", value, "fieldtype");
            return this;
        }

        public Criteria andFieldtypeLessThanOrEqualTo(String value) {
            addCriterion("FIELDTYPE <=", value, "fieldtype");
            return this;
        }

        public Criteria andFieldtypeLike(String value) {
            addCriterion("FIELDTYPE like", value, "fieldtype");
            return this;
        }

        public Criteria andFieldtypeNotLike(String value) {
            addCriterion("FIELDTYPE not like", value, "fieldtype");
            return this;
        }

        public Criteria andFieldtypeIn(List values) {
            addCriterion("FIELDTYPE in", values, "fieldtype");
            return this;
        }

        public Criteria andFieldtypeNotIn(List values) {
            addCriterion("FIELDTYPE not in", values, "fieldtype");
            return this;
        }

        public Criteria andFieldtypeBetween(String value1, String value2) {
            addCriterion("FIELDTYPE between", value1, value2, "fieldtype");
            return this;
        }

        public Criteria andFieldtypeNotBetween(String value1, String value2) {
            addCriterion("FIELDTYPE not between", value1, value2, "fieldtype");
            return this;
        }

        public Criteria andIspkIsNull() {
            addCriterion("ISPK is null");
            return this;
        }

        public Criteria andIspkIsNotNull() {
            addCriterion("ISPK is not null");
            return this;
        }

        public Criteria andIspkEqualTo(Integer value) {
            addCriterion("ISPK =", value, "ispk");
            return this;
        }

        public Criteria andIspkNotEqualTo(Integer value) {
            addCriterion("ISPK <>", value, "ispk");
            return this;
        }

        public Criteria andIspkGreaterThan(Integer value) {
            addCriterion("ISPK >", value, "ispk");
            return this;
        }

        public Criteria andIspkGreaterThanOrEqualTo(Integer value) {
            addCriterion("ISPK >=", value, "ispk");
            return this;
        }

        public Criteria andIspkLessThan(Integer value) {
            addCriterion("ISPK <", value, "ispk");
            return this;
        }

        public Criteria andIspkLessThanOrEqualTo(Integer value) {
            addCriterion("ISPK <=", value, "ispk");
            return this;
        }

        public Criteria andIspkIn(List values) {
            addCriterion("ISPK in", values, "ispk");
            return this;
        }

        public Criteria andIspkNotIn(List values) {
            addCriterion("ISPK not in", values, "ispk");
            return this;
        }

        public Criteria andIspkBetween(Integer value1, Integer value2) {
            addCriterion("ISPK between", value1, value2, "ispk");
            return this;
        }

        public Criteria andIspkNotBetween(Integer value1, Integer value2) {
            addCriterion("ISPK not between", value1, value2, "ispk");
            return this;
        }

        public Criteria andDefaultvalueIsNull() {
            addCriterion("DEFAULTVALUE is null");
            return this;
        }

        public Criteria andDefaultvalueIsNotNull() {
            addCriterion("DEFAULTVALUE is not null");
            return this;
        }

        public Criteria andDefaultvalueEqualTo(String value) {
            addCriterion("DEFAULTVALUE =", value, "defaultvalue");
            return this;
        }

        public Criteria andDefaultvalueNotEqualTo(String value) {
            addCriterion("DEFAULTVALUE <>", value, "defaultvalue");
            return this;
        }

        public Criteria andDefaultvalueGreaterThan(String value) {
            addCriterion("DEFAULTVALUE >", value, "defaultvalue");
            return this;
        }

        public Criteria andDefaultvalueGreaterThanOrEqualTo(String value) {
            addCriterion("DEFAULTVALUE >=", value, "defaultvalue");
            return this;
        }

        public Criteria andDefaultvalueLessThan(String value) {
            addCriterion("DEFAULTVALUE <", value, "defaultvalue");
            return this;
        }

        public Criteria andDefaultvalueLessThanOrEqualTo(String value) {
            addCriterion("DEFAULTVALUE <=", value, "defaultvalue");
            return this;
        }

        public Criteria andDefaultvalueLike(String value) {
            addCriterion("DEFAULTVALUE like", value, "defaultvalue");
            return this;
        }

        public Criteria andDefaultvalueNotLike(String value) {
            addCriterion("DEFAULTVALUE not like", value, "defaultvalue");
            return this;
        }

        public Criteria andDefaultvalueIn(List values) {
            addCriterion("DEFAULTVALUE in", values, "defaultvalue");
            return this;
        }

        public Criteria andDefaultvalueNotIn(List values) {
            addCriterion("DEFAULTVALUE not in", values, "defaultvalue");
            return this;
        }

        public Criteria andDefaultvalueBetween(String value1, String value2) {
            addCriterion("DEFAULTVALUE between", value1, value2, "defaultvalue");
            return this;
        }

        public Criteria andDefaultvalueNotBetween(String value1, String value2) {
            addCriterion("DEFAULTVALUE not between", value1, value2, "defaultvalue");
            return this;
        }

        public Criteria andIsrequireIsNull() {
            addCriterion("ISREQUIRE is null");
            return this;
        }

        public Criteria andIsrequireIsNotNull() {
            addCriterion("ISREQUIRE is not null");
            return this;
        }

        public Criteria andIsrequireEqualTo(Integer value) {
            addCriterion("ISREQUIRE =", value, "isrequire");
            return this;
        }

        public Criteria andIsrequireNotEqualTo(Integer value) {
            addCriterion("ISREQUIRE <>", value, "isrequire");
            return this;
        }

        public Criteria andIsrequireGreaterThan(Integer value) {
            addCriterion("ISREQUIRE >", value, "isrequire");
            return this;
        }

        public Criteria andIsrequireGreaterThanOrEqualTo(Integer value) {
            addCriterion("ISREQUIRE >=", value, "isrequire");
            return this;
        }

        public Criteria andIsrequireLessThan(Integer value) {
            addCriterion("ISREQUIRE <", value, "isrequire");
            return this;
        }

        public Criteria andIsrequireLessThanOrEqualTo(Integer value) {
            addCriterion("ISREQUIRE <=", value, "isrequire");
            return this;
        }

        public Criteria andIsrequireIn(List values) {
            addCriterion("ISREQUIRE in", values, "isrequire");
            return this;
        }

        public Criteria andIsrequireNotIn(List values) {
            addCriterion("ISREQUIRE not in", values, "isrequire");
            return this;
        }

        public Criteria andIsrequireBetween(Integer value1, Integer value2) {
            addCriterion("ISREQUIRE between", value1, value2, "isrequire");
            return this;
        }

        public Criteria andIsrequireNotBetween(Integer value1, Integer value2) {
            addCriterion("ISREQUIRE not between", value1, value2, "isrequire");
            return this;
        }

        public Criteria andIsuniqueIsNull() {
            addCriterion("ISUNIQUE is null");
            return this;
        }

        public Criteria andIsuniqueIsNotNull() {
            addCriterion("ISUNIQUE is not null");
            return this;
        }

        public Criteria andIsuniqueEqualTo(Integer value) {
            addCriterion("ISUNIQUE =", value, "isunique");
            return this;
        }

        public Criteria andIsuniqueNotEqualTo(Integer value) {
            addCriterion("ISUNIQUE <>", value, "isunique");
            return this;
        }

        public Criteria andIsuniqueGreaterThan(Integer value) {
            addCriterion("ISUNIQUE >", value, "isunique");
            return this;
        }

        public Criteria andIsuniqueGreaterThanOrEqualTo(Integer value) {
            addCriterion("ISUNIQUE >=", value, "isunique");
            return this;
        }

        public Criteria andIsuniqueLessThan(Integer value) {
            addCriterion("ISUNIQUE <", value, "isunique");
            return this;
        }

        public Criteria andIsuniqueLessThanOrEqualTo(Integer value) {
            addCriterion("ISUNIQUE <=", value, "isunique");
            return this;
        }

        public Criteria andIsuniqueIn(List values) {
            addCriterion("ISUNIQUE in", values, "isunique");
            return this;
        }

        public Criteria andIsuniqueNotIn(List values) {
            addCriterion("ISUNIQUE not in", values, "isunique");
            return this;
        }

        public Criteria andIsuniqueBetween(Integer value1, Integer value2) {
            addCriterion("ISUNIQUE between", value1, value2, "isunique");
            return this;
        }

        public Criteria andIsuniqueNotBetween(Integer value1, Integer value2) {
            addCriterion("ISUNIQUE not between", value1, value2, "isunique");
            return this;
        }

        public Criteria andIssearchIsNull() {
            addCriterion("ISSEARCH is null");
            return this;
        }

        public Criteria andIssearchIsNotNull() {
            addCriterion("ISSEARCH is not null");
            return this;
        }

        public Criteria andIssearchEqualTo(Integer value) {
            addCriterion("ISSEARCH =", value, "issearch");
            return this;
        }

        public Criteria andIssearchNotEqualTo(Integer value) {
            addCriterion("ISSEARCH <>", value, "issearch");
            return this;
        }

        public Criteria andIssearchGreaterThan(Integer value) {
            addCriterion("ISSEARCH >", value, "issearch");
            return this;
        }

        public Criteria andIssearchGreaterThanOrEqualTo(Integer value) {
            addCriterion("ISSEARCH >=", value, "issearch");
            return this;
        }

        public Criteria andIssearchLessThan(Integer value) {
            addCriterion("ISSEARCH <", value, "issearch");
            return this;
        }

        public Criteria andIssearchLessThanOrEqualTo(Integer value) {
            addCriterion("ISSEARCH <=", value, "issearch");
            return this;
        }

        public Criteria andIssearchIn(List values) {
            addCriterion("ISSEARCH in", values, "issearch");
            return this;
        }

        public Criteria andIssearchNotIn(List values) {
            addCriterion("ISSEARCH not in", values, "issearch");
            return this;
        }

        public Criteria andIssearchBetween(Integer value1, Integer value2) {
            addCriterion("ISSEARCH between", value1, value2, "issearch");
            return this;
        }

        public Criteria andIssearchNotBetween(Integer value1, Integer value2) {
            addCriterion("ISSEARCH not between", value1, value2, "issearch");
            return this;
        }

        public Criteria andIsgridshowIsNull() {
            addCriterion("ISGRIDSHOW is null");
            return this;
        }

        public Criteria andIsgridshowIsNotNull() {
            addCriterion("ISGRIDSHOW is not null");
            return this;
        }

        public Criteria andIsgridshowEqualTo(Integer value) {
            addCriterion("ISGRIDSHOW =", value, "isgridshow");
            return this;
        }

        public Criteria andIsgridshowNotEqualTo(Integer value) {
            addCriterion("ISGRIDSHOW <>", value, "isgridshow");
            return this;
        }

        public Criteria andIsgridshowGreaterThan(Integer value) {
            addCriterion("ISGRIDSHOW >", value, "isgridshow");
            return this;
        }

        public Criteria andIsgridshowGreaterThanOrEqualTo(Integer value) {
            addCriterion("ISGRIDSHOW >=", value, "isgridshow");
            return this;
        }

        public Criteria andIsgridshowLessThan(Integer value) {
            addCriterion("ISGRIDSHOW <", value, "isgridshow");
            return this;
        }

        public Criteria andIsgridshowLessThanOrEqualTo(Integer value) {
            addCriterion("ISGRIDSHOW <=", value, "isgridshow");
            return this;
        }

        public Criteria andIsgridshowIn(List values) {
            addCriterion("ISGRIDSHOW in", values, "isgridshow");
            return this;
        }

        public Criteria andIsgridshowNotIn(List values) {
            addCriterion("ISGRIDSHOW not in", values, "isgridshow");
            return this;
        }

        public Criteria andIsgridshowBetween(Integer value1, Integer value2) {
            addCriterion("ISGRIDSHOW between", value1, value2, "isgridshow");
            return this;
        }

        public Criteria andIsgridshowNotBetween(Integer value1, Integer value2) {
            addCriterion("ISGRIDSHOW not between", value1, value2, "isgridshow");
            return this;
        }

        public Criteria andSortIsNull() {
            addCriterion("SORT is null");
            return this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("SORT is not null");
            return this;
        }

        public Criteria andSortEqualTo(Integer value) {
            addCriterion("SORT =", value, "sort");
            return this;
        }

        public Criteria andSortNotEqualTo(Integer value) {
            addCriterion("SORT <>", value, "sort");
            return this;
        }

        public Criteria andSortGreaterThan(Integer value) {
            addCriterion("SORT >", value, "sort");
            return this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("SORT >=", value, "sort");
            return this;
        }

        public Criteria andSortLessThan(Integer value) {
            addCriterion("SORT <", value, "sort");
            return this;
        }

        public Criteria andSortLessThanOrEqualTo(Integer value) {
            addCriterion("SORT <=", value, "sort");
            return this;
        }

        public Criteria andSortIn(List values) {
            addCriterion("SORT in", values, "sort");
            return this;
        }

        public Criteria andSortNotIn(List values) {
            addCriterion("SORT not in", values, "sort");
            return this;
        }

        public Criteria andSortBetween(Integer value1, Integer value2) {
            addCriterion("SORT between", value1, value2, "sort");
            return this;
        }

        public Criteria andSortNotBetween(Integer value1, Integer value2) {
            addCriterion("SORT not between", value1, value2, "sort");
            return this;
        }

        public Criteria andIseditIsNull() {
            addCriterion("ISEDIT is null");
            return this;
        }

        public Criteria andIseditIsNotNull() {
            addCriterion("ISEDIT is not null");
            return this;
        }

        public Criteria andIseditEqualTo(Integer value) {
            addCriterion("ISEDIT =", value, "isedit");
            return this;
        }

        public Criteria andIseditNotEqualTo(Integer value) {
            addCriterion("ISEDIT <>", value, "isedit");
            return this;
        }

        public Criteria andIseditGreaterThan(Integer value) {
            addCriterion("ISEDIT >", value, "isedit");
            return this;
        }

        public Criteria andIseditGreaterThanOrEqualTo(Integer value) {
            addCriterion("ISEDIT >=", value, "isedit");
            return this;
        }

        public Criteria andIseditLessThan(Integer value) {
            addCriterion("ISEDIT <", value, "isedit");
            return this;
        }

        public Criteria andIseditLessThanOrEqualTo(Integer value) {
            addCriterion("ISEDIT <=", value, "isedit");
            return this;
        }

        public Criteria andIseditIn(List values) {
            addCriterion("ISEDIT in", values, "isedit");
            return this;
        }

        public Criteria andIseditNotIn(List values) {
            addCriterion("ISEDIT not in", values, "isedit");
            return this;
        }

        public Criteria andIseditBetween(Integer value1, Integer value2) {
            addCriterion("ISEDIT between", value1, value2, "isedit");
            return this;
        }

        public Criteria andIseditNotBetween(Integer value1, Integer value2) {
            addCriterion("ISEDIT not between", value1, value2, "isedit");
            return this;
        }

        public Criteria andIscodeIsNull() {
            addCriterion("ISCODE is null");
            return this;
        }

        public Criteria andIscodeIsNotNull() {
            addCriterion("ISCODE is not null");
            return this;
        }

        public Criteria andIscodeEqualTo(Integer value) {
            addCriterion("ISCODE =", value, "iscode");
            return this;
        }

        public Criteria andIscodeNotEqualTo(Integer value) {
            addCriterion("ISCODE <>", value, "iscode");
            return this;
        }

        public Criteria andIscodeGreaterThan(Integer value) {
            addCriterion("ISCODE >", value, "iscode");
            return this;
        }

        public Criteria andIscodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("ISCODE >=", value, "iscode");
            return this;
        }

        public Criteria andIscodeLessThan(Integer value) {
            addCriterion("ISCODE <", value, "iscode");
            return this;
        }

        public Criteria andIscodeLessThanOrEqualTo(Integer value) {
            addCriterion("ISCODE <=", value, "iscode");
            return this;
        }

        public Criteria andIscodeIn(List values) {
            addCriterion("ISCODE in", values, "iscode");
            return this;
        }

        public Criteria andIscodeNotIn(List values) {
            addCriterion("ISCODE not in", values, "iscode");
            return this;
        }

        public Criteria andIscodeBetween(Integer value1, Integer value2) {
            addCriterion("ISCODE between", value1, value2, "iscode");
            return this;
        }

        public Criteria andIscodeNotBetween(Integer value1, Integer value2) {
            addCriterion("ISCODE not between", value1, value2, "iscode");
            return this;
        }

        public Criteria andIssystemIsNull() {
            addCriterion("ISSYSTEM is null");
            return this;
        }

        public Criteria andIssystemIsNotNull() {
            addCriterion("ISSYSTEM is not null");
            return this;
        }

        public Criteria andIssystemEqualTo(Integer value) {
            addCriterion("ISSYSTEM =", value, "issystem");
            return this;
        }

        public Criteria andIssystemNotEqualTo(Integer value) {
            addCriterion("ISSYSTEM <>", value, "issystem");
            return this;
        }

        public Criteria andIssystemGreaterThan(Integer value) {
            addCriterion("ISSYSTEM >", value, "issystem");
            return this;
        }

        public Criteria andIssystemGreaterThanOrEqualTo(Integer value) {
            addCriterion("ISSYSTEM >=", value, "issystem");
            return this;
        }

        public Criteria andIssystemLessThan(Integer value) {
            addCriterion("ISSYSTEM <", value, "issystem");
            return this;
        }

        public Criteria andIssystemLessThanOrEqualTo(Integer value) {
            addCriterion("ISSYSTEM <=", value, "issystem");
            return this;
        }

        public Criteria andIssystemIn(List values) {
            addCriterion("ISSYSTEM in", values, "issystem");
            return this;
        }

        public Criteria andIssystemNotIn(List values) {
            addCriterion("ISSYSTEM not in", values, "issystem");
            return this;
        }

        public Criteria andIssystemBetween(Integer value1, Integer value2) {
            addCriterion("ISSYSTEM between", value1, value2, "issystem");
            return this;
        }

        public Criteria andIssystemNotBetween(Integer value1, Integer value2) {
            addCriterion("ISSYSTEM not between", value1, value2, "issystem");
            return this;
        }

        public Criteria andFieldcssIsNull() {
            addCriterion("FIELDCSS is null");
            return this;
        }

        public Criteria andFieldcssIsNotNull() {
            addCriterion("FIELDCSS is not null");
            return this;
        }

        public Criteria andFieldcssEqualTo(String value) {
            addCriterion("FIELDCSS =", value, "fieldcss");
            return this;
        }

        public Criteria andFieldcssNotEqualTo(String value) {
            addCriterion("FIELDCSS <>", value, "fieldcss");
            return this;
        }

        public Criteria andFieldcssGreaterThan(String value) {
            addCriterion("FIELDCSS >", value, "fieldcss");
            return this;
        }

        public Criteria andFieldcssGreaterThanOrEqualTo(String value) {
            addCriterion("FIELDCSS >=", value, "fieldcss");
            return this;
        }

        public Criteria andFieldcssLessThan(String value) {
            addCriterion("FIELDCSS <", value, "fieldcss");
            return this;
        }

        public Criteria andFieldcssLessThanOrEqualTo(String value) {
            addCriterion("FIELDCSS <=", value, "fieldcss");
            return this;
        }

        public Criteria andFieldcssLike(String value) {
            addCriterion("FIELDCSS like", value, "fieldcss");
            return this;
        }

        public Criteria andFieldcssNotLike(String value) {
            addCriterion("FIELDCSS not like", value, "fieldcss");
            return this;
        }

        public Criteria andFieldcssIn(List values) {
            addCriterion("FIELDCSS in", values, "fieldcss");
            return this;
        }

        public Criteria andFieldcssNotIn(List values) {
            addCriterion("FIELDCSS not in", values, "fieldcss");
            return this;
        }

        public Criteria andFieldcssBetween(String value1, String value2) {
            addCriterion("FIELDCSS between", value1, value2, "fieldcss");
            return this;
        }

        public Criteria andFieldcssNotBetween(String value1, String value2) {
            addCriterion("FIELDCSS not between", value1, value2, "fieldcss");
            return this;
        }

        public Criteria andTableidIsNull() {
            addCriterion("TABLEID is null");
            return this;
        }

        public Criteria andTableidIsNotNull() {
            addCriterion("TABLEID is not null");
            return this;
        }

        public Criteria andTableidEqualTo(String value) {
            addCriterion("TABLEID =", value, "tableid");
            return this;
        }

        public Criteria andTableidNotEqualTo(String value) {
            addCriterion("TABLEID <>", value, "tableid");
            return this;
        }

        public Criteria andTableidGreaterThan(String value) {
            addCriterion("TABLEID >", value, "tableid");
            return this;
        }

        public Criteria andTableidGreaterThanOrEqualTo(String value) {
            addCriterion("TABLEID >=", value, "tableid");
            return this;
        }

        public Criteria andTableidLessThan(String value) {
            addCriterion("TABLEID <", value, "tableid");
            return this;
        }

        public Criteria andTableidLessThanOrEqualTo(String value) {
            addCriterion("TABLEID <=", value, "tableid");
            return this;
        }

        public Criteria andTableidLike(String value) {
            addCriterion("TABLEID like", value, "tableid");
            return this;
        }

        public Criteria andTableidNotLike(String value) {
            addCriterion("TABLEID not like", value, "tableid");
            return this;
        }

        public Criteria andTableidIn(List values) {
            addCriterion("TABLEID in", values, "tableid");
            return this;
        }

        public Criteria andTableidNotIn(List values) {
            addCriterion("TABLEID not in", values, "tableid");
            return this;
        }

        public Criteria andTableidBetween(String value1, String value2) {
            addCriterion("TABLEID between", value1, value2, "tableid");
            return this;
        }

        public Criteria andTableidNotBetween(String value1, String value2) {
            addCriterion("TABLEID not between", value1, value2, "tableid");
            return this;
        }
    }
}