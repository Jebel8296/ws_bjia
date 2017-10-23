package com.chinamcom.framework.backstage.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbAftersaleImageboxExample {
    /**
     * Table: tb_aftersale_imagebox
    @mbggenerated 2016-11-09 13:35:22
     */
    protected String orderByClause;

    /**
     * Table: tb_aftersale_imagebox
    @mbggenerated 2016-11-09 13:35:22
     */
    protected boolean distinct;

    /**
     * Table: tb_aftersale_imagebox
    @mbggenerated 2016-11-09 13:35:22
     */
    protected List<Criteria> oredCriteria;

    public TbAftersaleImageboxExample() {
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
     * Table: tb_aftersale_imagebox
    @mbggenerated 2016-11-09 13:35:22
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

        public Criteria andAfterIdIsNull() {
            addCriterion("after_id is null");
            return (Criteria) this;
        }

        public Criteria andAfterIdIsNotNull() {
            addCriterion("after_id is not null");
            return (Criteria) this;
        }

        public Criteria andAfterIdEqualTo(Integer value) {
            addCriterion("after_id =", value, "afterId");
            return (Criteria) this;
        }

        public Criteria andAfterIdNotEqualTo(Integer value) {
            addCriterion("after_id <>", value, "afterId");
            return (Criteria) this;
        }

        public Criteria andAfterIdGreaterThan(Integer value) {
            addCriterion("after_id >", value, "afterId");
            return (Criteria) this;
        }

        public Criteria andAfterIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("after_id >=", value, "afterId");
            return (Criteria) this;
        }

        public Criteria andAfterIdLessThan(Integer value) {
            addCriterion("after_id <", value, "afterId");
            return (Criteria) this;
        }

        public Criteria andAfterIdLessThanOrEqualTo(Integer value) {
            addCriterion("after_id <=", value, "afterId");
            return (Criteria) this;
        }

        public Criteria andAfterIdIn(List<Integer> values) {
            addCriterion("after_id in", values, "afterId");
            return (Criteria) this;
        }

        public Criteria andAfterIdNotIn(List<Integer> values) {
            addCriterion("after_id not in", values, "afterId");
            return (Criteria) this;
        }

        public Criteria andAfterIdBetween(Integer value1, Integer value2) {
            addCriterion("after_id between", value1, value2, "afterId");
            return (Criteria) this;
        }

        public Criteria andAfterIdNotBetween(Integer value1, Integer value2) {
            addCriterion("after_id not between", value1, value2, "afterId");
            return (Criteria) this;
        }

        public Criteria andAfterCodeIsNull() {
            addCriterion("after_code is null");
            return (Criteria) this;
        }

        public Criteria andAfterCodeIsNotNull() {
            addCriterion("after_code is not null");
            return (Criteria) this;
        }

        public Criteria andAfterCodeEqualTo(String value) {
            addCriterion("after_code =", value, "afterCode");
            return (Criteria) this;
        }

        public Criteria andAfterCodeNotEqualTo(String value) {
            addCriterion("after_code <>", value, "afterCode");
            return (Criteria) this;
        }

        public Criteria andAfterCodeGreaterThan(String value) {
            addCriterion("after_code >", value, "afterCode");
            return (Criteria) this;
        }

        public Criteria andAfterCodeGreaterThanOrEqualTo(String value) {
            addCriterion("after_code >=", value, "afterCode");
            return (Criteria) this;
        }

        public Criteria andAfterCodeLessThan(String value) {
            addCriterion("after_code <", value, "afterCode");
            return (Criteria) this;
        }

        public Criteria andAfterCodeLessThanOrEqualTo(String value) {
            addCriterion("after_code <=", value, "afterCode");
            return (Criteria) this;
        }

        public Criteria andAfterCodeLike(String value) {
            addCriterion("after_code like", value, "afterCode");
            return (Criteria) this;
        }

        public Criteria andAfterCodeNotLike(String value) {
            addCriterion("after_code not like", value, "afterCode");
            return (Criteria) this;
        }

        public Criteria andAfterCodeIn(List<String> values) {
            addCriterion("after_code in", values, "afterCode");
            return (Criteria) this;
        }

        public Criteria andAfterCodeNotIn(List<String> values) {
            addCriterion("after_code not in", values, "afterCode");
            return (Criteria) this;
        }

        public Criteria andAfterCodeBetween(String value1, String value2) {
            addCriterion("after_code between", value1, value2, "afterCode");
            return (Criteria) this;
        }

        public Criteria andAfterCodeNotBetween(String value1, String value2) {
            addCriterion("after_code not between", value1, value2, "afterCode");
            return (Criteria) this;
        }

        public Criteria andImageUriIsNull() {
            addCriterion("image_uri is null");
            return (Criteria) this;
        }

        public Criteria andImageUriIsNotNull() {
            addCriterion("image_uri is not null");
            return (Criteria) this;
        }

        public Criteria andImageUriEqualTo(String value) {
            addCriterion("image_uri =", value, "imageUri");
            return (Criteria) this;
        }

        public Criteria andImageUriNotEqualTo(String value) {
            addCriterion("image_uri <>", value, "imageUri");
            return (Criteria) this;
        }

        public Criteria andImageUriGreaterThan(String value) {
            addCriterion("image_uri >", value, "imageUri");
            return (Criteria) this;
        }

        public Criteria andImageUriGreaterThanOrEqualTo(String value) {
            addCriterion("image_uri >=", value, "imageUri");
            return (Criteria) this;
        }

        public Criteria andImageUriLessThan(String value) {
            addCriterion("image_uri <", value, "imageUri");
            return (Criteria) this;
        }

        public Criteria andImageUriLessThanOrEqualTo(String value) {
            addCriterion("image_uri <=", value, "imageUri");
            return (Criteria) this;
        }

        public Criteria andImageUriLike(String value) {
            addCriterion("image_uri like", value, "imageUri");
            return (Criteria) this;
        }

        public Criteria andImageUriNotLike(String value) {
            addCriterion("image_uri not like", value, "imageUri");
            return (Criteria) this;
        }

        public Criteria andImageUriIn(List<String> values) {
            addCriterion("image_uri in", values, "imageUri");
            return (Criteria) this;
        }

        public Criteria andImageUriNotIn(List<String> values) {
            addCriterion("image_uri not in", values, "imageUri");
            return (Criteria) this;
        }

        public Criteria andImageUriBetween(String value1, String value2) {
            addCriterion("image_uri between", value1, value2, "imageUri");
            return (Criteria) this;
        }

        public Criteria andImageUriNotBetween(String value1, String value2) {
            addCriterion("image_uri not between", value1, value2, "imageUri");
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
    }

    /**
     * This class corresponds to the database table tb_aftersale_imagebox
    @mbggenerated do_not_delete_during_merge 2016-11-09 13:35:22
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * [STRATO MyBatis Generator]
     * Table: tb_aftersale_imagebox
    @mbggenerated 2016-11-09 13:35:22
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