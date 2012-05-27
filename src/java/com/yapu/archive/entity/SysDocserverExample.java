package com.yapu.archive.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysDocserverExample {
    
    protected String orderByClause;
    protected List oredCriteria;
    public SysDocserverExample() {
        oredCriteria = new ArrayList();
    }
    protected SysDocserverExample(SysDocserverExample example) {
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

        public Criteria andDocserveridIsNull() {
            addCriterion("DOCSERVERID is null");
            return this;
        }

        public Criteria andDocserveridIsNotNull() {
            addCriterion("DOCSERVERID is not null");
            return this;
        }

        public Criteria andDocserveridEqualTo(String value) {
            addCriterion("DOCSERVERID =", value, "docserverid");
            return this;
        }

        public Criteria andDocserveridNotEqualTo(String value) {
            addCriterion("DOCSERVERID <>", value, "docserverid");
            return this;
        }

        public Criteria andDocserveridGreaterThan(String value) {
            addCriterion("DOCSERVERID >", value, "docserverid");
            return this;
        }

        public Criteria andDocserveridGreaterThanOrEqualTo(String value) {
            addCriterion("DOCSERVERID >=", value, "docserverid");
            return this;
        }

        public Criteria andDocserveridLessThan(String value) {
            addCriterion("DOCSERVERID <", value, "docserverid");
            return this;
        }

        public Criteria andDocserveridLessThanOrEqualTo(String value) {
            addCriterion("DOCSERVERID <=", value, "docserverid");
            return this;
        }

        public Criteria andDocserveridLike(String value) {
            addCriterion("DOCSERVERID like", value, "docserverid");
            return this;
        }

        public Criteria andDocserveridNotLike(String value) {
            addCriterion("DOCSERVERID not like", value, "docserverid");
            return this;
        }

        public Criteria andDocserveridIn(List values) {
            addCriterion("DOCSERVERID in", values, "docserverid");
            return this;
        }

        public Criteria andDocserveridNotIn(List values) {
            addCriterion("DOCSERVERID not in", values, "docserverid");
            return this;
        }

        public Criteria andDocserveridBetween(String value1, String value2) {
            addCriterion("DOCSERVERID between", value1, value2, "docserverid");
            return this;
        }

        public Criteria andDocserveridNotBetween(String value1, String value2) {
            addCriterion("DOCSERVERID not between", value1, value2, "docserverid");
            return this;
        }

        public Criteria andServeripIsNull() {
            addCriterion("SERVERIP is null");
            return this;
        }

        public Criteria andServeripIsNotNull() {
            addCriterion("SERVERIP is not null");
            return this;
        }

        public Criteria andServeripEqualTo(String value) {
            addCriterion("SERVERIP =", value, "serverip");
            return this;
        }

        public Criteria andServeripNotEqualTo(String value) {
            addCriterion("SERVERIP <>", value, "serverip");
            return this;
        }

        public Criteria andServeripGreaterThan(String value) {
            addCriterion("SERVERIP >", value, "serverip");
            return this;
        }

        public Criteria andServeripGreaterThanOrEqualTo(String value) {
            addCriterion("SERVERIP >=", value, "serverip");
            return this;
        }

        public Criteria andServeripLessThan(String value) {
            addCriterion("SERVERIP <", value, "serverip");
            return this;
        }

        public Criteria andServeripLessThanOrEqualTo(String value) {
            addCriterion("SERVERIP <=", value, "serverip");
            return this;
        }

        public Criteria andServeripLike(String value) {
            addCriterion("SERVERIP like", value, "serverip");
            return this;
        }

        public Criteria andServeripNotLike(String value) {
            addCriterion("SERVERIP not like", value, "serverip");
            return this;
        }

        public Criteria andServeripIn(List values) {
            addCriterion("SERVERIP in", values, "serverip");
            return this;
        }

        public Criteria andServeripNotIn(List values) {
            addCriterion("SERVERIP not in", values, "serverip");
            return this;
        }

        public Criteria andServeripBetween(String value1, String value2) {
            addCriterion("SERVERIP between", value1, value2, "serverip");
            return this;
        }

        public Criteria andServeripNotBetween(String value1, String value2) {
            addCriterion("SERVERIP not between", value1, value2, "serverip");
            return this;
        }

        public Criteria andServerpathIsNull() {
            addCriterion("SERVERPATH is null");
            return this;
        }

        public Criteria andServerpathIsNotNull() {
            addCriterion("SERVERPATH is not null");
            return this;
        }

        public Criteria andServerpathEqualTo(String value) {
            addCriterion("SERVERPATH =", value, "serverpath");
            return this;
        }

        public Criteria andServerpathNotEqualTo(String value) {
            addCriterion("SERVERPATH <>", value, "serverpath");
            return this;
        }

        public Criteria andServerpathGreaterThan(String value) {
            addCriterion("SERVERPATH >", value, "serverpath");
            return this;
        }

        public Criteria andServerpathGreaterThanOrEqualTo(String value) {
            addCriterion("SERVERPATH >=", value, "serverpath");
            return this;
        }

        public Criteria andServerpathLessThan(String value) {
            addCriterion("SERVERPATH <", value, "serverpath");
            return this;
        }

        public Criteria andServerpathLessThanOrEqualTo(String value) {
            addCriterion("SERVERPATH <=", value, "serverpath");
            return this;
        }

        public Criteria andServerpathLike(String value) {
            addCriterion("SERVERPATH like", value, "serverpath");
            return this;
        }

        public Criteria andServerpathNotLike(String value) {
            addCriterion("SERVERPATH not like", value, "serverpath");
            return this;
        }

        public Criteria andServerpathIn(List values) {
            addCriterion("SERVERPATH in", values, "serverpath");
            return this;
        }

        public Criteria andServerpathNotIn(List values) {
            addCriterion("SERVERPATH not in", values, "serverpath");
            return this;
        }

        public Criteria andServerpathBetween(String value1, String value2) {
            addCriterion("SERVERPATH between", value1, value2, "serverpath");
            return this;
        }

        public Criteria andServerpathNotBetween(String value1, String value2) {
            addCriterion("SERVERPATH not between", value1, value2, "serverpath");
            return this;
        }

        public Criteria andFtpuserIsNull() {
            addCriterion("FTPUSER is null");
            return this;
        }

        public Criteria andFtpuserIsNotNull() {
            addCriterion("FTPUSER is not null");
            return this;
        }

        public Criteria andFtpuserEqualTo(String value) {
            addCriterion("FTPUSER =", value, "ftpuser");
            return this;
        }

        public Criteria andFtpuserNotEqualTo(String value) {
            addCriterion("FTPUSER <>", value, "ftpuser");
            return this;
        }

        public Criteria andFtpuserGreaterThan(String value) {
            addCriterion("FTPUSER >", value, "ftpuser");
            return this;
        }

        public Criteria andFtpuserGreaterThanOrEqualTo(String value) {
            addCriterion("FTPUSER >=", value, "ftpuser");
            return this;
        }

        public Criteria andFtpuserLessThan(String value) {
            addCriterion("FTPUSER <", value, "ftpuser");
            return this;
        }

        public Criteria andFtpuserLessThanOrEqualTo(String value) {
            addCriterion("FTPUSER <=", value, "ftpuser");
            return this;
        }

        public Criteria andFtpuserLike(String value) {
            addCriterion("FTPUSER like", value, "ftpuser");
            return this;
        }

        public Criteria andFtpuserNotLike(String value) {
            addCriterion("FTPUSER not like", value, "ftpuser");
            return this;
        }

        public Criteria andFtpuserIn(List values) {
            addCriterion("FTPUSER in", values, "ftpuser");
            return this;
        }

        public Criteria andFtpuserNotIn(List values) {
            addCriterion("FTPUSER not in", values, "ftpuser");
            return this;
        }

        public Criteria andFtpuserBetween(String value1, String value2) {
            addCriterion("FTPUSER between", value1, value2, "ftpuser");
            return this;
        }

        public Criteria andFtpuserNotBetween(String value1, String value2) {
            addCriterion("FTPUSER not between", value1, value2, "ftpuser");
            return this;
        }

        public Criteria andFtppasswordIsNull() {
            addCriterion("FTPPASSWORD is null");
            return this;
        }

        public Criteria andFtppasswordIsNotNull() {
            addCriterion("FTPPASSWORD is not null");
            return this;
        }

        public Criteria andFtppasswordEqualTo(String value) {
            addCriterion("FTPPASSWORD =", value, "ftppassword");
            return this;
        }

        public Criteria andFtppasswordNotEqualTo(String value) {
            addCriterion("FTPPASSWORD <>", value, "ftppassword");
            return this;
        }

        public Criteria andFtppasswordGreaterThan(String value) {
            addCriterion("FTPPASSWORD >", value, "ftppassword");
            return this;
        }

        public Criteria andFtppasswordGreaterThanOrEqualTo(String value) {
            addCriterion("FTPPASSWORD >=", value, "ftppassword");
            return this;
        }

        public Criteria andFtppasswordLessThan(String value) {
            addCriterion("FTPPASSWORD <", value, "ftppassword");
            return this;
        }

        public Criteria andFtppasswordLessThanOrEqualTo(String value) {
            addCriterion("FTPPASSWORD <=", value, "ftppassword");
            return this;
        }

        public Criteria andFtppasswordLike(String value) {
            addCriterion("FTPPASSWORD like", value, "ftppassword");
            return this;
        }

        public Criteria andFtppasswordNotLike(String value) {
            addCriterion("FTPPASSWORD not like", value, "ftppassword");
            return this;
        }

        public Criteria andFtppasswordIn(List values) {
            addCriterion("FTPPASSWORD in", values, "ftppassword");
            return this;
        }

        public Criteria andFtppasswordNotIn(List values) {
            addCriterion("FTPPASSWORD not in", values, "ftppassword");
            return this;
        }

        public Criteria andFtppasswordBetween(String value1, String value2) {
            addCriterion("FTPPASSWORD between", value1, value2, "ftppassword");
            return this;
        }

        public Criteria andFtppasswordNotBetween(String value1, String value2) {
            addCriterion("FTPPASSWORD not between", value1, value2, "ftppassword");
            return this;
        }

        public Criteria andServernameIsNull() {
            addCriterion("SERVERNAME is null");
            return this;
        }

        public Criteria andServernameIsNotNull() {
            addCriterion("SERVERNAME is not null");
            return this;
        }

        public Criteria andServernameEqualTo(String value) {
            addCriterion("SERVERNAME =", value, "servername");
            return this;
        }

        public Criteria andServernameNotEqualTo(String value) {
            addCriterion("SERVERNAME <>", value, "servername");
            return this;
        }

        public Criteria andServernameGreaterThan(String value) {
            addCriterion("SERVERNAME >", value, "servername");
            return this;
        }

        public Criteria andServernameGreaterThanOrEqualTo(String value) {
            addCriterion("SERVERNAME >=", value, "servername");
            return this;
        }

        public Criteria andServernameLessThan(String value) {
            addCriterion("SERVERNAME <", value, "servername");
            return this;
        }

        public Criteria andServernameLessThanOrEqualTo(String value) {
            addCriterion("SERVERNAME <=", value, "servername");
            return this;
        }

        public Criteria andServernameLike(String value) {
            addCriterion("SERVERNAME like", value, "servername");
            return this;
        }

        public Criteria andServernameNotLike(String value) {
            addCriterion("SERVERNAME not like", value, "servername");
            return this;
        }

        public Criteria andServernameIn(List values) {
            addCriterion("SERVERNAME in", values, "servername");
            return this;
        }

        public Criteria andServernameNotIn(List values) {
            addCriterion("SERVERNAME not in", values, "servername");
            return this;
        }

        public Criteria andServernameBetween(String value1, String value2) {
            addCriterion("SERVERNAME between", value1, value2, "servername");
            return this;
        }

        public Criteria andServernameNotBetween(String value1, String value2) {
            addCriterion("SERVERNAME not between", value1, value2, "servername");
            return this;
        }

        public Criteria andServertypeIsNull() {
            addCriterion("SERVERTYPE is null");
            return this;
        }

        public Criteria andServertypeIsNotNull() {
            addCriterion("SERVERTYPE is not null");
            return this;
        }

        public Criteria andServertypeEqualTo(String value) {
            addCriterion("SERVERTYPE =", value, "servertype");
            return this;
        }

        public Criteria andServertypeNotEqualTo(String value) {
            addCriterion("SERVERTYPE <>", value, "servertype");
            return this;
        }

        public Criteria andServertypeGreaterThan(String value) {
            addCriterion("SERVERTYPE >", value, "servertype");
            return this;
        }

        public Criteria andServertypeGreaterThanOrEqualTo(String value) {
            addCriterion("SERVERTYPE >=", value, "servertype");
            return this;
        }

        public Criteria andServertypeLessThan(String value) {
            addCriterion("SERVERTYPE <", value, "servertype");
            return this;
        }

        public Criteria andServertypeLessThanOrEqualTo(String value) {
            addCriterion("SERVERTYPE <=", value, "servertype");
            return this;
        }

        public Criteria andServertypeLike(String value) {
            addCriterion("SERVERTYPE like", value, "servertype");
            return this;
        }

        public Criteria andServertypeNotLike(String value) {
            addCriterion("SERVERTYPE not like", value, "servertype");
            return this;
        }

        public Criteria andServertypeIn(List values) {
            addCriterion("SERVERTYPE in", values, "servertype");
            return this;
        }

        public Criteria andServertypeNotIn(List values) {
            addCriterion("SERVERTYPE not in", values, "servertype");
            return this;
        }

        public Criteria andServertypeBetween(String value1, String value2) {
            addCriterion("SERVERTYPE between", value1, value2, "servertype");
            return this;
        }

        public Criteria andServertypeNotBetween(String value1, String value2) {
            addCriterion("SERVERTYPE not between", value1, value2, "servertype");
            return this;
        }

        public Criteria andServerstateIsNull() {
            addCriterion("SERVERSTATE is null");
            return this;
        }

        public Criteria andServerstateIsNotNull() {
            addCriterion("SERVERSTATE is not null");
            return this;
        }

        public Criteria andServerstateEqualTo(Integer value) {
            addCriterion("SERVERSTATE =", value, "serverstate");
            return this;
        }

        public Criteria andServerstateNotEqualTo(Integer value) {
            addCriterion("SERVERSTATE <>", value, "serverstate");
            return this;
        }

        public Criteria andServerstateGreaterThan(Integer value) {
            addCriterion("SERVERSTATE >", value, "serverstate");
            return this;
        }

        public Criteria andServerstateGreaterThanOrEqualTo(Integer value) {
            addCriterion("SERVERSTATE >=", value, "serverstate");
            return this;
        }

        public Criteria andServerstateLessThan(Integer value) {
            addCriterion("SERVERSTATE <", value, "serverstate");
            return this;
        }

        public Criteria andServerstateLessThanOrEqualTo(Integer value) {
            addCriterion("SERVERSTATE <=", value, "serverstate");
            return this;
        }

        public Criteria andServerstateIn(List values) {
            addCriterion("SERVERSTATE in", values, "serverstate");
            return this;
        }

        public Criteria andServerstateNotIn(List values) {
            addCriterion("SERVERSTATE not in", values, "serverstate");
            return this;
        }

        public Criteria andServerstateBetween(Integer value1, Integer value2) {
            addCriterion("SERVERSTATE between", value1, value2, "serverstate");
            return this;
        }

        public Criteria andServerstateNotBetween(Integer value1, Integer value2) {
            addCriterion("SERVERSTATE not between", value1, value2, "serverstate");
            return this;
        }

        public Criteria andServermemoIsNull() {
            addCriterion("SERVERMEMO is null");
            return this;
        }

        public Criteria andServermemoIsNotNull() {
            addCriterion("SERVERMEMO is not null");
            return this;
        }

        public Criteria andServermemoEqualTo(String value) {
            addCriterion("SERVERMEMO =", value, "servermemo");
            return this;
        }

        public Criteria andServermemoNotEqualTo(String value) {
            addCriterion("SERVERMEMO <>", value, "servermemo");
            return this;
        }

        public Criteria andServermemoGreaterThan(String value) {
            addCriterion("SERVERMEMO >", value, "servermemo");
            return this;
        }

        public Criteria andServermemoGreaterThanOrEqualTo(String value) {
            addCriterion("SERVERMEMO >=", value, "servermemo");
            return this;
        }

        public Criteria andServermemoLessThan(String value) {
            addCriterion("SERVERMEMO <", value, "servermemo");
            return this;
        }

        public Criteria andServermemoLessThanOrEqualTo(String value) {
            addCriterion("SERVERMEMO <=", value, "servermemo");
            return this;
        }

        public Criteria andServermemoLike(String value) {
            addCriterion("SERVERMEMO like", value, "servermemo");
            return this;
        }

        public Criteria andServermemoNotLike(String value) {
            addCriterion("SERVERMEMO not like", value, "servermemo");
            return this;
        }

        public Criteria andServermemoIn(List values) {
            addCriterion("SERVERMEMO in", values, "servermemo");
            return this;
        }

        public Criteria andServermemoNotIn(List values) {
            addCriterion("SERVERMEMO not in", values, "servermemo");
            return this;
        }

        public Criteria andServermemoBetween(String value1, String value2) {
            addCriterion("SERVERMEMO between", value1, value2, "servermemo");
            return this;
        }

        public Criteria andServermemoNotBetween(String value1, String value2) {
            addCriterion("SERVERMEMO not between", value1, value2, "servermemo");
            return this;
        }
    }
}