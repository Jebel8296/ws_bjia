package com.chinamcom.framework.products.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbProductAttributeExample {
    /**
     * Table: tb_product_attribute
    @mbggenerated 2016-10-21 13:43:06
     */
    protected String orderByClause;

    /**
     * Table: tb_product_attribute
    @mbggenerated 2016-10-21 13:43:06
     */
    protected boolean distinct;

    /**
     * Table: tb_product_attribute
    @mbggenerated 2016-10-21 13:43:06
     */
    protected List<Criteria> oredCriteria;

    public TbProductAttributeExample() {
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
     * Table: tb_product_attribute
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

        public Criteria andAttributeIsNull() {
            addCriterion("ATTRIBUTE is null");
            return (Criteria) this;
        }

        public Criteria andAttributeIsNotNull() {
            addCriterion("ATTRIBUTE is not null");
            return (Criteria) this;
        }

        public Criteria andAttributeEqualTo(String value) {
            addCriterion("ATTRIBUTE =", value, "attribute");
            return (Criteria) this;
        }

        public Criteria andAttributeNotEqualTo(String value) {
            addCriterion("ATTRIBUTE <>", value, "attribute");
            return (Criteria) this;
        }

        public Criteria andAttributeGreaterThan(String value) {
            addCriterion("ATTRIBUTE >", value, "attribute");
            return (Criteria) this;
        }

        public Criteria andAttributeGreaterThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE >=", value, "attribute");
            return (Criteria) this;
        }

        public Criteria andAttributeLessThan(String value) {
            addCriterion("ATTRIBUTE <", value, "attribute");
            return (Criteria) this;
        }

        public Criteria andAttributeLessThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE <=", value, "attribute");
            return (Criteria) this;
        }

        public Criteria andAttributeLike(String value) {
            addCriterion("ATTRIBUTE like", value, "attribute");
            return (Criteria) this;
        }

        public Criteria andAttributeNotLike(String value) {
            addCriterion("ATTRIBUTE not like", value, "attribute");
            return (Criteria) this;
        }

        public Criteria andAttributeIn(List<String> values) {
            addCriterion("ATTRIBUTE in", values, "attribute");
            return (Criteria) this;
        }

        public Criteria andAttributeNotIn(List<String> values) {
            addCriterion("ATTRIBUTE not in", values, "attribute");
            return (Criteria) this;
        }

        public Criteria andAttributeBetween(String value1, String value2) {
            addCriterion("ATTRIBUTE between", value1, value2, "attribute");
            return (Criteria) this;
        }

        public Criteria andAttributeNotBetween(String value1, String value2) {
            addCriterion("ATTRIBUTE not between", value1, value2, "attribute");
            return (Criteria) this;
        }

        public Criteria andValueIsNull() {
            addCriterion("VALUE is null");
            return (Criteria) this;
        }

        public Criteria andValueIsNotNull() {
            addCriterion("VALUE is not null");
            return (Criteria) this;
        }

        public Criteria andValueEqualTo(String value) {
            addCriterion("VALUE =", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueNotEqualTo(String value) {
            addCriterion("VALUE <>", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueGreaterThan(String value) {
            addCriterion("VALUE >", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueGreaterThanOrEqualTo(String value) {
            addCriterion("VALUE >=", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueLessThan(String value) {
            addCriterion("VALUE <", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueLessThanOrEqualTo(String value) {
            addCriterion("VALUE <=", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueLike(String value) {
            addCriterion("VALUE like", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueNotLike(String value) {
            addCriterion("VALUE not like", value, "value");
            return (Criteria) this;
        }

        public Criteria andValueIn(List<String> values) {
            addCriterion("VALUE in", values, "value");
            return (Criteria) this;
        }

        public Criteria andValueNotIn(List<String> values) {
            addCriterion("VALUE not in", values, "value");
            return (Criteria) this;
        }

        public Criteria andValueBetween(String value1, String value2) {
            addCriterion("VALUE between", value1, value2, "value");
            return (Criteria) this;
        }

        public Criteria andValueNotBetween(String value1, String value2) {
            addCriterion("VALUE not between", value1, value2, "value");
            return (Criteria) this;
        }

        public Criteria andSvalueIsNull() {
            addCriterion("SVALUE is null");
            return (Criteria) this;
        }

        public Criteria andSvalueIsNotNull() {
            addCriterion("SVALUE is not null");
            return (Criteria) this;
        }

        public Criteria andSvalueEqualTo(String value) {
            addCriterion("SVALUE =", value, "svalue");
            return (Criteria) this;
        }

        public Criteria andSvalueNotEqualTo(String value) {
            addCriterion("SVALUE <>", value, "svalue");
            return (Criteria) this;
        }

        public Criteria andSvalueGreaterThan(String value) {
            addCriterion("SVALUE >", value, "svalue");
            return (Criteria) this;
        }

        public Criteria andSvalueGreaterThanOrEqualTo(String value) {
            addCriterion("SVALUE >=", value, "svalue");
            return (Criteria) this;
        }

        public Criteria andSvalueLessThan(String value) {
            addCriterion("SVALUE <", value, "svalue");
            return (Criteria) this;
        }

        public Criteria andSvalueLessThanOrEqualTo(String value) {
            addCriterion("SVALUE <=", value, "svalue");
            return (Criteria) this;
        }

        public Criteria andSvalueLike(String value) {
            addCriterion("SVALUE like", value, "svalue");
            return (Criteria) this;
        }

        public Criteria andSvalueNotLike(String value) {
            addCriterion("SVALUE not like", value, "svalue");
            return (Criteria) this;
        }

        public Criteria andSvalueIn(List<String> values) {
            addCriterion("SVALUE in", values, "svalue");
            return (Criteria) this;
        }

        public Criteria andSvalueNotIn(List<String> values) {
            addCriterion("SVALUE not in", values, "svalue");
            return (Criteria) this;
        }

        public Criteria andSvalueBetween(String value1, String value2) {
            addCriterion("SVALUE between", value1, value2, "svalue");
            return (Criteria) this;
        }

        public Criteria andSvalueNotBetween(String value1, String value2) {
            addCriterion("SVALUE not between", value1, value2, "svalue");
            return (Criteria) this;
        }

        public Criteria andDatatypeIsNull() {
            addCriterion("DATATYPE is null");
            return (Criteria) this;
        }

        public Criteria andDatatypeIsNotNull() {
            addCriterion("DATATYPE is not null");
            return (Criteria) this;
        }

        public Criteria andDatatypeEqualTo(Integer value) {
            addCriterion("DATATYPE =", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeNotEqualTo(Integer value) {
            addCriterion("DATATYPE <>", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeGreaterThan(Integer value) {
            addCriterion("DATATYPE >", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("DATATYPE >=", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeLessThan(Integer value) {
            addCriterion("DATATYPE <", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeLessThanOrEqualTo(Integer value) {
            addCriterion("DATATYPE <=", value, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeIn(List<Integer> values) {
            addCriterion("DATATYPE in", values, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeNotIn(List<Integer> values) {
            addCriterion("DATATYPE not in", values, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeBetween(Integer value1, Integer value2) {
            addCriterion("DATATYPE between", value1, value2, "datatype");
            return (Criteria) this;
        }

        public Criteria andDatatypeNotBetween(Integer value1, Integer value2) {
            addCriterion("DATATYPE not between", value1, value2, "datatype");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("CREATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("CREATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("CREATE_TIME =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("CREATE_TIME <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("CREATE_TIME >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("CREATE_TIME <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("CREATE_TIME in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("CREATE_TIME not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME not between", value1, value2, "createTime");
            return (Criteria) this;
        }
    }

    /**
     * This class corresponds to the database table tb_product_attribute
    @mbggenerated do_not_delete_during_merge 2016-10-21 13:43:06
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * [STRATO MyBatis Generator]
     * Table: tb_product_attribute
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