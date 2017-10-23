package com.chinamcom.framework.products.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbProductImageboxExample {
    /**
     * Table: tb_product_imagebox
    @mbggenerated 2016-10-21 13:43:06
     */
    protected String orderByClause;

    /**
     * Table: tb_product_imagebox
    @mbggenerated 2016-10-21 13:43:06
     */
    protected boolean distinct;

    /**
     * Table: tb_product_imagebox
    @mbggenerated 2016-10-21 13:43:06
     */
    protected List<Criteria> oredCriteria;

    public TbProductImageboxExample() {
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
     * Table: tb_product_imagebox
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

        public Criteria andUriIsNull() {
            addCriterion("URI is null");
            return (Criteria) this;
        }

        public Criteria andUriIsNotNull() {
            addCriterion("URI is not null");
            return (Criteria) this;
        }

        public Criteria andUriEqualTo(String value) {
            addCriterion("URI =", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriNotEqualTo(String value) {
            addCriterion("URI <>", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriGreaterThan(String value) {
            addCriterion("URI >", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriGreaterThanOrEqualTo(String value) {
            addCriterion("URI >=", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriLessThan(String value) {
            addCriterion("URI <", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriLessThanOrEqualTo(String value) {
            addCriterion("URI <=", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriLike(String value) {
            addCriterion("URI like", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriNotLike(String value) {
            addCriterion("URI not like", value, "uri");
            return (Criteria) this;
        }

        public Criteria andUriIn(List<String> values) {
            addCriterion("URI in", values, "uri");
            return (Criteria) this;
        }

        public Criteria andUriNotIn(List<String> values) {
            addCriterion("URI not in", values, "uri");
            return (Criteria) this;
        }

        public Criteria andUriBetween(String value1, String value2) {
            addCriterion("URI between", value1, value2, "uri");
            return (Criteria) this;
        }

        public Criteria andUriNotBetween(String value1, String value2) {
            addCriterion("URI not between", value1, value2, "uri");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("TYPE is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("TYPE =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("TYPE <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("TYPE >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("TYPE >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("TYPE <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("TYPE <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("TYPE in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("TYPE not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("TYPE between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("TYPE not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypenumIsNull() {
            addCriterion("TYPENUM is null");
            return (Criteria) this;
        }

        public Criteria andTypenumIsNotNull() {
            addCriterion("TYPENUM is not null");
            return (Criteria) this;
        }

        public Criteria andTypenumEqualTo(Integer value) {
            addCriterion("TYPENUM =", value, "typenum");
            return (Criteria) this;
        }

        public Criteria andTypenumNotEqualTo(Integer value) {
            addCriterion("TYPENUM <>", value, "typenum");
            return (Criteria) this;
        }

        public Criteria andTypenumGreaterThan(Integer value) {
            addCriterion("TYPENUM >", value, "typenum");
            return (Criteria) this;
        }

        public Criteria andTypenumGreaterThanOrEqualTo(Integer value) {
            addCriterion("TYPENUM >=", value, "typenum");
            return (Criteria) this;
        }

        public Criteria andTypenumLessThan(Integer value) {
            addCriterion("TYPENUM <", value, "typenum");
            return (Criteria) this;
        }

        public Criteria andTypenumLessThanOrEqualTo(Integer value) {
            addCriterion("TYPENUM <=", value, "typenum");
            return (Criteria) this;
        }

        public Criteria andTypenumIn(List<Integer> values) {
            addCriterion("TYPENUM in", values, "typenum");
            return (Criteria) this;
        }

        public Criteria andTypenumNotIn(List<Integer> values) {
            addCriterion("TYPENUM not in", values, "typenum");
            return (Criteria) this;
        }

        public Criteria andTypenumBetween(Integer value1, Integer value2) {
            addCriterion("TYPENUM between", value1, value2, "typenum");
            return (Criteria) this;
        }

        public Criteria andTypenumNotBetween(Integer value1, Integer value2) {
            addCriterion("TYPENUM not between", value1, value2, "typenum");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("STATUS is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andLocationIsNull() {
            addCriterion("LOCATION is null");
            return (Criteria) this;
        }

        public Criteria andLocationIsNotNull() {
            addCriterion("LOCATION is not null");
            return (Criteria) this;
        }

        public Criteria andLocationEqualTo(String value) {
            addCriterion("LOCATION =", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationNotEqualTo(String value) {
            addCriterion("LOCATION <>", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationGreaterThan(String value) {
            addCriterion("LOCATION >", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationGreaterThanOrEqualTo(String value) {
            addCriterion("LOCATION >=", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationLessThan(String value) {
            addCriterion("LOCATION <", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationLessThanOrEqualTo(String value) {
            addCriterion("LOCATION <=", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationLike(String value) {
            addCriterion("LOCATION like", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationNotLike(String value) {
            addCriterion("LOCATION not like", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationIn(List<String> values) {
            addCriterion("LOCATION in", values, "location");
            return (Criteria) this;
        }

        public Criteria andLocationNotIn(List<String> values) {
            addCriterion("LOCATION not in", values, "location");
            return (Criteria) this;
        }

        public Criteria andLocationBetween(String value1, String value2) {
            addCriterion("LOCATION between", value1, value2, "location");
            return (Criteria) this;
        }

        public Criteria andLocationNotBetween(String value1, String value2) {
            addCriterion("LOCATION not between", value1, value2, "location");
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
     * This class corresponds to the database table tb_product_imagebox
    @mbggenerated do_not_delete_during_merge 2016-10-21 13:43:06
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * [STRATO MyBatis Generator]
     * Table: tb_product_imagebox
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