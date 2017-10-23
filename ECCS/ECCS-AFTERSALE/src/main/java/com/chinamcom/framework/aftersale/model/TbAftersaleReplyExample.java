package com.chinamcom.framework.aftersale.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbAftersaleReplyExample {
    /**
     * Table: tb_aftersale_reply
    @mbggenerated 2017-07-04 15:56:44
     */
    protected String orderByClause;

    /**
     * Table: tb_aftersale_reply
    @mbggenerated 2017-07-04 15:56:44
     */
    protected boolean distinct;

    /**
     * Table: tb_aftersale_reply
    @mbggenerated 2017-07-04 15:56:44
     */
    protected List<Criteria> oredCriteria;

    public TbAftersaleReplyExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
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
        orderByClause = null;
        distinct = false;
    }

    /**
     * [STRATO MyBatis Generator]
     * Table: tb_aftersale_reply
    @mbggenerated 2017-07-04 15:56:44
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andAfteridIsNull() {
            addCriterion("afterid is null");
            return (Criteria) this;
        }

        public Criteria andAfteridIsNotNull() {
            addCriterion("afterid is not null");
            return (Criteria) this;
        }

        public Criteria andAfteridEqualTo(Integer value) {
            addCriterion("afterid =", value, "afterid");
            return (Criteria) this;
        }

        public Criteria andAfteridNotEqualTo(Integer value) {
            addCriterion("afterid <>", value, "afterid");
            return (Criteria) this;
        }

        public Criteria andAfteridGreaterThan(Integer value) {
            addCriterion("afterid >", value, "afterid");
            return (Criteria) this;
        }

        public Criteria andAfteridGreaterThanOrEqualTo(Integer value) {
            addCriterion("afterid >=", value, "afterid");
            return (Criteria) this;
        }

        public Criteria andAfteridLessThan(Integer value) {
            addCriterion("afterid <", value, "afterid");
            return (Criteria) this;
        }

        public Criteria andAfteridLessThanOrEqualTo(Integer value) {
            addCriterion("afterid <=", value, "afterid");
            return (Criteria) this;
        }

        public Criteria andAfteridIn(List<Integer> values) {
            addCriterion("afterid in", values, "afterid");
            return (Criteria) this;
        }

        public Criteria andAfteridNotIn(List<Integer> values) {
            addCriterion("afterid not in", values, "afterid");
            return (Criteria) this;
        }

        public Criteria andAfteridBetween(Integer value1, Integer value2) {
            addCriterion("afterid between", value1, value2, "afterid");
            return (Criteria) this;
        }

        public Criteria andAfteridNotBetween(Integer value1, Integer value2) {
            addCriterion("afterid not between", value1, value2, "afterid");
            return (Criteria) this;
        }

        public Criteria andAftercodeIsNull() {
            addCriterion("aftercode is null");
            return (Criteria) this;
        }

        public Criteria andAftercodeIsNotNull() {
            addCriterion("aftercode is not null");
            return (Criteria) this;
        }

        public Criteria andAftercodeEqualTo(String value) {
            addCriterion("aftercode =", value, "aftercode");
            return (Criteria) this;
        }

        public Criteria andAftercodeNotEqualTo(String value) {
            addCriterion("aftercode <>", value, "aftercode");
            return (Criteria) this;
        }

        public Criteria andAftercodeGreaterThan(String value) {
            addCriterion("aftercode >", value, "aftercode");
            return (Criteria) this;
        }

        public Criteria andAftercodeGreaterThanOrEqualTo(String value) {
            addCriterion("aftercode >=", value, "aftercode");
            return (Criteria) this;
        }

        public Criteria andAftercodeLessThan(String value) {
            addCriterion("aftercode <", value, "aftercode");
            return (Criteria) this;
        }

        public Criteria andAftercodeLessThanOrEqualTo(String value) {
            addCriterion("aftercode <=", value, "aftercode");
            return (Criteria) this;
        }

        public Criteria andAftercodeLike(String value) {
            addCriterion("aftercode like", value, "aftercode");
            return (Criteria) this;
        }

        public Criteria andAftercodeNotLike(String value) {
            addCriterion("aftercode not like", value, "aftercode");
            return (Criteria) this;
        }

        public Criteria andAftercodeIn(List<String> values) {
            addCriterion("aftercode in", values, "aftercode");
            return (Criteria) this;
        }

        public Criteria andAftercodeNotIn(List<String> values) {
            addCriterion("aftercode not in", values, "aftercode");
            return (Criteria) this;
        }

        public Criteria andAftercodeBetween(String value1, String value2) {
            addCriterion("aftercode between", value1, value2, "aftercode");
            return (Criteria) this;
        }

        public Criteria andAftercodeNotBetween(String value1, String value2) {
            addCriterion("aftercode not between", value1, value2, "aftercode");
            return (Criteria) this;
        }

        public Criteria andStatusFromIsNull() {
            addCriterion("status_from is null");
            return (Criteria) this;
        }

        public Criteria andStatusFromIsNotNull() {
            addCriterion("status_from is not null");
            return (Criteria) this;
        }

        public Criteria andStatusFromEqualTo(Integer value) {
            addCriterion("status_from =", value, "statusFrom");
            return (Criteria) this;
        }

        public Criteria andStatusFromNotEqualTo(Integer value) {
            addCriterion("status_from <>", value, "statusFrom");
            return (Criteria) this;
        }

        public Criteria andStatusFromGreaterThan(Integer value) {
            addCriterion("status_from >", value, "statusFrom");
            return (Criteria) this;
        }

        public Criteria andStatusFromGreaterThanOrEqualTo(Integer value) {
            addCriterion("status_from >=", value, "statusFrom");
            return (Criteria) this;
        }

        public Criteria andStatusFromLessThan(Integer value) {
            addCriterion("status_from <", value, "statusFrom");
            return (Criteria) this;
        }

        public Criteria andStatusFromLessThanOrEqualTo(Integer value) {
            addCriterion("status_from <=", value, "statusFrom");
            return (Criteria) this;
        }

        public Criteria andStatusFromIn(List<Integer> values) {
            addCriterion("status_from in", values, "statusFrom");
            return (Criteria) this;
        }

        public Criteria andStatusFromNotIn(List<Integer> values) {
            addCriterion("status_from not in", values, "statusFrom");
            return (Criteria) this;
        }

        public Criteria andStatusFromBetween(Integer value1, Integer value2) {
            addCriterion("status_from between", value1, value2, "statusFrom");
            return (Criteria) this;
        }

        public Criteria andStatusFromNotBetween(Integer value1, Integer value2) {
            addCriterion("status_from not between", value1, value2, "statusFrom");
            return (Criteria) this;
        }

        public Criteria andStatusToIsNull() {
            addCriterion("status_to is null");
            return (Criteria) this;
        }

        public Criteria andStatusToIsNotNull() {
            addCriterion("status_to is not null");
            return (Criteria) this;
        }

        public Criteria andStatusToEqualTo(Integer value) {
            addCriterion("status_to =", value, "statusTo");
            return (Criteria) this;
        }

        public Criteria andStatusToNotEqualTo(Integer value) {
            addCriterion("status_to <>", value, "statusTo");
            return (Criteria) this;
        }

        public Criteria andStatusToGreaterThan(Integer value) {
            addCriterion("status_to >", value, "statusTo");
            return (Criteria) this;
        }

        public Criteria andStatusToGreaterThanOrEqualTo(Integer value) {
            addCriterion("status_to >=", value, "statusTo");
            return (Criteria) this;
        }

        public Criteria andStatusToLessThan(Integer value) {
            addCriterion("status_to <", value, "statusTo");
            return (Criteria) this;
        }

        public Criteria andStatusToLessThanOrEqualTo(Integer value) {
            addCriterion("status_to <=", value, "statusTo");
            return (Criteria) this;
        }

        public Criteria andStatusToIn(List<Integer> values) {
            addCriterion("status_to in", values, "statusTo");
            return (Criteria) this;
        }

        public Criteria andStatusToNotIn(List<Integer> values) {
            addCriterion("status_to not in", values, "statusTo");
            return (Criteria) this;
        }

        public Criteria andStatusToBetween(Integer value1, Integer value2) {
            addCriterion("status_to between", value1, value2, "statusTo");
            return (Criteria) this;
        }

        public Criteria andStatusToNotBetween(Integer value1, Integer value2) {
            addCriterion("status_to not between", value1, value2, "statusTo");
            return (Criteria) this;
        }

        public Criteria andReplyIsNull() {
            addCriterion("reply is null");
            return (Criteria) this;
        }

        public Criteria andReplyIsNotNull() {
            addCriterion("reply is not null");
            return (Criteria) this;
        }

        public Criteria andReplyEqualTo(String value) {
            addCriterion("reply =", value, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyNotEqualTo(String value) {
            addCriterion("reply <>", value, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyGreaterThan(String value) {
            addCriterion("reply >", value, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyGreaterThanOrEqualTo(String value) {
            addCriterion("reply >=", value, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyLessThan(String value) {
            addCriterion("reply <", value, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyLessThanOrEqualTo(String value) {
            addCriterion("reply <=", value, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyLike(String value) {
            addCriterion("reply like", value, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyNotLike(String value) {
            addCriterion("reply not like", value, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyIn(List<String> values) {
            addCriterion("reply in", values, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyNotIn(List<String> values) {
            addCriterion("reply not in", values, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyBetween(String value1, String value2) {
            addCriterion("reply between", value1, value2, "reply");
            return (Criteria) this;
        }

        public Criteria andReplyNotBetween(String value1, String value2) {
            addCriterion("reply not between", value1, value2, "reply");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }
    }

    /**
     * This class corresponds to the database table tb_aftersale_reply
    @mbggenerated do_not_delete_during_merge 2017-07-04 15:56:44
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * [STRATO MyBatis Generator]
     * Table: tb_aftersale_reply
    @mbggenerated 2017-07-04 15:56:44
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}