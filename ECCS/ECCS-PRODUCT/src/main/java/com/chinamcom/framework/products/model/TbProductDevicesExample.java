package com.chinamcom.framework.products.model;

import java.util.ArrayList;
import java.util.List;

public class TbProductDevicesExample {
    /**
     * Table: tb_product_devices
    @mbggenerated 2016-10-21 13:43:06
     */
    protected String orderByClause;

    /**
     * Table: tb_product_devices
    @mbggenerated 2016-10-21 13:43:06
     */
    protected boolean distinct;

    /**
     * Table: tb_product_devices
    @mbggenerated 2016-10-21 13:43:06
     */
    protected List<Criteria> oredCriteria;

    public TbProductDevicesExample() {
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
     * Table: tb_product_devices
    @mbggenerated 2016-10-21 13:43:06
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
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andProdidIsNull() {
            addCriterion("PRODID is null");
            return (Criteria) this;
        }

        public Criteria andProdidIsNotNull() {
            addCriterion("PRODID is not null");
            return (Criteria) this;
        }

        public Criteria andProdidEqualTo(Integer value) {
            addCriterion("PRODID =", value, "prodid");
            return (Criteria) this;
        }

        public Criteria andProdidNotEqualTo(Integer value) {
            addCriterion("PRODID <>", value, "prodid");
            return (Criteria) this;
        }

        public Criteria andProdidGreaterThan(Integer value) {
            addCriterion("PRODID >", value, "prodid");
            return (Criteria) this;
        }

        public Criteria andProdidGreaterThanOrEqualTo(Integer value) {
            addCriterion("PRODID >=", value, "prodid");
            return (Criteria) this;
        }

        public Criteria andProdidLessThan(Integer value) {
            addCriterion("PRODID <", value, "prodid");
            return (Criteria) this;
        }

        public Criteria andProdidLessThanOrEqualTo(Integer value) {
            addCriterion("PRODID <=", value, "prodid");
            return (Criteria) this;
        }

        public Criteria andProdidIn(List<Integer> values) {
            addCriterion("PRODID in", values, "prodid");
            return (Criteria) this;
        }

        public Criteria andProdidNotIn(List<Integer> values) {
            addCriterion("PRODID not in", values, "prodid");
            return (Criteria) this;
        }

        public Criteria andProdidBetween(Integer value1, Integer value2) {
            addCriterion("PRODID between", value1, value2, "prodid");
            return (Criteria) this;
        }

        public Criteria andProdidNotBetween(Integer value1, Integer value2) {
            addCriterion("PRODID not between", value1, value2, "prodid");
            return (Criteria) this;
        }

        public Criteria andProdcodeIsNull() {
            addCriterion("PRODCODE is null");
            return (Criteria) this;
        }

        public Criteria andProdcodeIsNotNull() {
            addCriterion("PRODCODE is not null");
            return (Criteria) this;
        }

        public Criteria andProdcodeEqualTo(String value) {
            addCriterion("PRODCODE =", value, "prodcode");
            return (Criteria) this;
        }

        public Criteria andProdcodeNotEqualTo(String value) {
            addCriterion("PRODCODE <>", value, "prodcode");
            return (Criteria) this;
        }

        public Criteria andProdcodeGreaterThan(String value) {
            addCriterion("PRODCODE >", value, "prodcode");
            return (Criteria) this;
        }

        public Criteria andProdcodeGreaterThanOrEqualTo(String value) {
            addCriterion("PRODCODE >=", value, "prodcode");
            return (Criteria) this;
        }

        public Criteria andProdcodeLessThan(String value) {
            addCriterion("PRODCODE <", value, "prodcode");
            return (Criteria) this;
        }

        public Criteria andProdcodeLessThanOrEqualTo(String value) {
            addCriterion("PRODCODE <=", value, "prodcode");
            return (Criteria) this;
        }

        public Criteria andProdcodeLike(String value) {
            addCriterion("PRODCODE like", value, "prodcode");
            return (Criteria) this;
        }

        public Criteria andProdcodeNotLike(String value) {
            addCriterion("PRODCODE not like", value, "prodcode");
            return (Criteria) this;
        }

        public Criteria andProdcodeIn(List<String> values) {
            addCriterion("PRODCODE in", values, "prodcode");
            return (Criteria) this;
        }

        public Criteria andProdcodeNotIn(List<String> values) {
            addCriterion("PRODCODE not in", values, "prodcode");
            return (Criteria) this;
        }

        public Criteria andProdcodeBetween(String value1, String value2) {
            addCriterion("PRODCODE between", value1, value2, "prodcode");
            return (Criteria) this;
        }

        public Criteria andProdcodeNotBetween(String value1, String value2) {
            addCriterion("PRODCODE not between", value1, value2, "prodcode");
            return (Criteria) this;
        }

        public Criteria andDevicescodeIsNull() {
            addCriterion("DEVICESCODE is null");
            return (Criteria) this;
        }

        public Criteria andDevicescodeIsNotNull() {
            addCriterion("DEVICESCODE is not null");
            return (Criteria) this;
        }

        public Criteria andDevicescodeEqualTo(String value) {
            addCriterion("DEVICESCODE =", value, "devicescode");
            return (Criteria) this;
        }

        public Criteria andDevicescodeNotEqualTo(String value) {
            addCriterion("DEVICESCODE <>", value, "devicescode");
            return (Criteria) this;
        }

        public Criteria andDevicescodeGreaterThan(String value) {
            addCriterion("DEVICESCODE >", value, "devicescode");
            return (Criteria) this;
        }

        public Criteria andDevicescodeGreaterThanOrEqualTo(String value) {
            addCriterion("DEVICESCODE >=", value, "devicescode");
            return (Criteria) this;
        }

        public Criteria andDevicescodeLessThan(String value) {
            addCriterion("DEVICESCODE <", value, "devicescode");
            return (Criteria) this;
        }

        public Criteria andDevicescodeLessThanOrEqualTo(String value) {
            addCriterion("DEVICESCODE <=", value, "devicescode");
            return (Criteria) this;
        }

        public Criteria andDevicescodeLike(String value) {
            addCriterion("DEVICESCODE like", value, "devicescode");
            return (Criteria) this;
        }

        public Criteria andDevicescodeNotLike(String value) {
            addCriterion("DEVICESCODE not like", value, "devicescode");
            return (Criteria) this;
        }

        public Criteria andDevicescodeIn(List<String> values) {
            addCriterion("DEVICESCODE in", values, "devicescode");
            return (Criteria) this;
        }

        public Criteria andDevicescodeNotIn(List<String> values) {
            addCriterion("DEVICESCODE not in", values, "devicescode");
            return (Criteria) this;
        }

        public Criteria andDevicescodeBetween(String value1, String value2) {
            addCriterion("DEVICESCODE between", value1, value2, "devicescode");
            return (Criteria) this;
        }

        public Criteria andDevicescodeNotBetween(String value1, String value2) {
            addCriterion("DEVICESCODE not between", value1, value2, "devicescode");
            return (Criteria) this;
        }

        public Criteria andProdtypeIsNull() {
            addCriterion("PRODTYPE is null");
            return (Criteria) this;
        }

        public Criteria andProdtypeIsNotNull() {
            addCriterion("PRODTYPE is not null");
            return (Criteria) this;
        }

        public Criteria andProdtypeEqualTo(Integer value) {
            addCriterion("PRODTYPE =", value, "prodtype");
            return (Criteria) this;
        }

        public Criteria andProdtypeNotEqualTo(Integer value) {
            addCriterion("PRODTYPE <>", value, "prodtype");
            return (Criteria) this;
        }

        public Criteria andProdtypeGreaterThan(Integer value) {
            addCriterion("PRODTYPE >", value, "prodtype");
            return (Criteria) this;
        }

        public Criteria andProdtypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("PRODTYPE >=", value, "prodtype");
            return (Criteria) this;
        }

        public Criteria andProdtypeLessThan(Integer value) {
            addCriterion("PRODTYPE <", value, "prodtype");
            return (Criteria) this;
        }

        public Criteria andProdtypeLessThanOrEqualTo(Integer value) {
            addCriterion("PRODTYPE <=", value, "prodtype");
            return (Criteria) this;
        }

        public Criteria andProdtypeIn(List<Integer> values) {
            addCriterion("PRODTYPE in", values, "prodtype");
            return (Criteria) this;
        }

        public Criteria andProdtypeNotIn(List<Integer> values) {
            addCriterion("PRODTYPE not in", values, "prodtype");
            return (Criteria) this;
        }

        public Criteria andProdtypeBetween(Integer value1, Integer value2) {
            addCriterion("PRODTYPE between", value1, value2, "prodtype");
            return (Criteria) this;
        }

        public Criteria andProdtypeNotBetween(Integer value1, Integer value2) {
            addCriterion("PRODTYPE not between", value1, value2, "prodtype");
            return (Criteria) this;
        }
    }

    /**
     * This class corresponds to the database table tb_product_devices
    @mbggenerated do_not_delete_during_merge 2016-10-21 13:43:06
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * [STRATO MyBatis Generator]
     * Table: tb_product_devices
    @mbggenerated 2016-10-21 13:43:06
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