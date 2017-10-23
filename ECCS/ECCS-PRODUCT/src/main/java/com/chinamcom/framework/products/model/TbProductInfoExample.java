package com.chinamcom.framework.products.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbProductInfoExample {
    /**
     * Table: tb_product_info
    @mbggenerated 2016-10-31 15:59:58
     */
    protected String orderByClause;

    /**
     * Table: tb_product_info
    @mbggenerated 2016-10-31 15:59:58
     */
    protected boolean distinct;

    /**
     * Table: tb_product_info
    @mbggenerated 2016-10-31 15:59:58
     */
    protected List<Criteria> oredCriteria;

    public TbProductInfoExample() {
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
     * Table: tb_product_info
    @mbggenerated 2016-10-31 15:59:58
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

        public Criteria andProdnameIsNull() {
            addCriterion("PRODNAME is null");
            return (Criteria) this;
        }

        public Criteria andProdnameIsNotNull() {
            addCriterion("PRODNAME is not null");
            return (Criteria) this;
        }

        public Criteria andProdnameEqualTo(String value) {
            addCriterion("PRODNAME =", value, "prodname");
            return (Criteria) this;
        }

        public Criteria andProdnameNotEqualTo(String value) {
            addCriterion("PRODNAME <>", value, "prodname");
            return (Criteria) this;
        }

        public Criteria andProdnameGreaterThan(String value) {
            addCriterion("PRODNAME >", value, "prodname");
            return (Criteria) this;
        }

        public Criteria andProdnameGreaterThanOrEqualTo(String value) {
            addCriterion("PRODNAME >=", value, "prodname");
            return (Criteria) this;
        }

        public Criteria andProdnameLessThan(String value) {
            addCriterion("PRODNAME <", value, "prodname");
            return (Criteria) this;
        }

        public Criteria andProdnameLessThanOrEqualTo(String value) {
            addCriterion("PRODNAME <=", value, "prodname");
            return (Criteria) this;
        }

        public Criteria andProdnameLike(String value) {
            addCriterion("PRODNAME like", value, "prodname");
            return (Criteria) this;
        }

        public Criteria andProdnameNotLike(String value) {
            addCriterion("PRODNAME not like", value, "prodname");
            return (Criteria) this;
        }

        public Criteria andProdnameIn(List<String> values) {
            addCriterion("PRODNAME in", values, "prodname");
            return (Criteria) this;
        }

        public Criteria andProdnameNotIn(List<String> values) {
            addCriterion("PRODNAME not in", values, "prodname");
            return (Criteria) this;
        }

        public Criteria andProdnameBetween(String value1, String value2) {
            addCriterion("PRODNAME between", value1, value2, "prodname");
            return (Criteria) this;
        }

        public Criteria andProdnameNotBetween(String value1, String value2) {
            addCriterion("PRODNAME not between", value1, value2, "prodname");
            return (Criteria) this;
        }

        public Criteria andSecondnameIsNull() {
            addCriterion("SECONDNAME is null");
            return (Criteria) this;
        }

        public Criteria andSecondnameIsNotNull() {
            addCriterion("SECONDNAME is not null");
            return (Criteria) this;
        }

        public Criteria andSecondnameEqualTo(String value) {
            addCriterion("SECONDNAME =", value, "secondname");
            return (Criteria) this;
        }

        public Criteria andSecondnameNotEqualTo(String value) {
            addCriterion("SECONDNAME <>", value, "secondname");
            return (Criteria) this;
        }

        public Criteria andSecondnameGreaterThan(String value) {
            addCriterion("SECONDNAME >", value, "secondname");
            return (Criteria) this;
        }

        public Criteria andSecondnameGreaterThanOrEqualTo(String value) {
            addCriterion("SECONDNAME >=", value, "secondname");
            return (Criteria) this;
        }

        public Criteria andSecondnameLessThan(String value) {
            addCriterion("SECONDNAME <", value, "secondname");
            return (Criteria) this;
        }

        public Criteria andSecondnameLessThanOrEqualTo(String value) {
            addCriterion("SECONDNAME <=", value, "secondname");
            return (Criteria) this;
        }

        public Criteria andSecondnameLike(String value) {
            addCriterion("SECONDNAME like", value, "secondname");
            return (Criteria) this;
        }

        public Criteria andSecondnameNotLike(String value) {
            addCriterion("SECONDNAME not like", value, "secondname");
            return (Criteria) this;
        }

        public Criteria andSecondnameIn(List<String> values) {
            addCriterion("SECONDNAME in", values, "secondname");
            return (Criteria) this;
        }

        public Criteria andSecondnameNotIn(List<String> values) {
            addCriterion("SECONDNAME not in", values, "secondname");
            return (Criteria) this;
        }

        public Criteria andSecondnameBetween(String value1, String value2) {
            addCriterion("SECONDNAME between", value1, value2, "secondname");
            return (Criteria) this;
        }

        public Criteria andSecondnameNotBetween(String value1, String value2) {
            addCriterion("SECONDNAME not between", value1, value2, "secondname");
            return (Criteria) this;
        }

        public Criteria andProddescIsNull() {
            addCriterion("PRODDESC is null");
            return (Criteria) this;
        }

        public Criteria andProddescIsNotNull() {
            addCriterion("PRODDESC is not null");
            return (Criteria) this;
        }

        public Criteria andProddescEqualTo(String value) {
            addCriterion("PRODDESC =", value, "proddesc");
            return (Criteria) this;
        }

        public Criteria andProddescNotEqualTo(String value) {
            addCriterion("PRODDESC <>", value, "proddesc");
            return (Criteria) this;
        }

        public Criteria andProddescGreaterThan(String value) {
            addCriterion("PRODDESC >", value, "proddesc");
            return (Criteria) this;
        }

        public Criteria andProddescGreaterThanOrEqualTo(String value) {
            addCriterion("PRODDESC >=", value, "proddesc");
            return (Criteria) this;
        }

        public Criteria andProddescLessThan(String value) {
            addCriterion("PRODDESC <", value, "proddesc");
            return (Criteria) this;
        }

        public Criteria andProddescLessThanOrEqualTo(String value) {
            addCriterion("PRODDESC <=", value, "proddesc");
            return (Criteria) this;
        }

        public Criteria andProddescLike(String value) {
            addCriterion("PRODDESC like", value, "proddesc");
            return (Criteria) this;
        }

        public Criteria andProddescNotLike(String value) {
            addCriterion("PRODDESC not like", value, "proddesc");
            return (Criteria) this;
        }

        public Criteria andProddescIn(List<String> values) {
            addCriterion("PRODDESC in", values, "proddesc");
            return (Criteria) this;
        }

        public Criteria andProddescNotIn(List<String> values) {
            addCriterion("PRODDESC not in", values, "proddesc");
            return (Criteria) this;
        }

        public Criteria andProddescBetween(String value1, String value2) {
            addCriterion("PRODDESC between", value1, value2, "proddesc");
            return (Criteria) this;
        }

        public Criteria andProddescNotBetween(String value1, String value2) {
            addCriterion("PRODDESC not between", value1, value2, "proddesc");
            return (Criteria) this;
        }

        public Criteria andProdpriceIsNull() {
            addCriterion("PRODPRICE is null");
            return (Criteria) this;
        }

        public Criteria andProdpriceIsNotNull() {
            addCriterion("PRODPRICE is not null");
            return (Criteria) this;
        }

        public Criteria andProdpriceEqualTo(BigDecimal value) {
            addCriterion("PRODPRICE =", value, "prodprice");
            return (Criteria) this;
        }

        public Criteria andProdpriceNotEqualTo(BigDecimal value) {
            addCriterion("PRODPRICE <>", value, "prodprice");
            return (Criteria) this;
        }

        public Criteria andProdpriceGreaterThan(BigDecimal value) {
            addCriterion("PRODPRICE >", value, "prodprice");
            return (Criteria) this;
        }

        public Criteria andProdpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PRODPRICE >=", value, "prodprice");
            return (Criteria) this;
        }

        public Criteria andProdpriceLessThan(BigDecimal value) {
            addCriterion("PRODPRICE <", value, "prodprice");
            return (Criteria) this;
        }

        public Criteria andProdpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PRODPRICE <=", value, "prodprice");
            return (Criteria) this;
        }

        public Criteria andProdpriceIn(List<BigDecimal> values) {
            addCriterion("PRODPRICE in", values, "prodprice");
            return (Criteria) this;
        }

        public Criteria andProdpriceNotIn(List<BigDecimal> values) {
            addCriterion("PRODPRICE not in", values, "prodprice");
            return (Criteria) this;
        }

        public Criteria andProdpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRODPRICE between", value1, value2, "prodprice");
            return (Criteria) this;
        }

        public Criteria andProdpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PRODPRICE not between", value1, value2, "prodprice");
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

        public Criteria andParenttypeIsNull() {
            addCriterion("PARENTTYPE is null");
            return (Criteria) this;
        }

        public Criteria andParenttypeIsNotNull() {
            addCriterion("PARENTTYPE is not null");
            return (Criteria) this;
        }

        public Criteria andParenttypeEqualTo(Integer value) {
            addCriterion("PARENTTYPE =", value, "parenttype");
            return (Criteria) this;
        }

        public Criteria andParenttypeNotEqualTo(Integer value) {
            addCriterion("PARENTTYPE <>", value, "parenttype");
            return (Criteria) this;
        }

        public Criteria andParenttypeGreaterThan(Integer value) {
            addCriterion("PARENTTYPE >", value, "parenttype");
            return (Criteria) this;
        }

        public Criteria andParenttypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("PARENTTYPE >=", value, "parenttype");
            return (Criteria) this;
        }

        public Criteria andParenttypeLessThan(Integer value) {
            addCriterion("PARENTTYPE <", value, "parenttype");
            return (Criteria) this;
        }

        public Criteria andParenttypeLessThanOrEqualTo(Integer value) {
            addCriterion("PARENTTYPE <=", value, "parenttype");
            return (Criteria) this;
        }

        public Criteria andParenttypeIn(List<Integer> values) {
            addCriterion("PARENTTYPE in", values, "parenttype");
            return (Criteria) this;
        }

        public Criteria andParenttypeNotIn(List<Integer> values) {
            addCriterion("PARENTTYPE not in", values, "parenttype");
            return (Criteria) this;
        }

        public Criteria andParenttypeBetween(Integer value1, Integer value2) {
            addCriterion("PARENTTYPE between", value1, value2, "parenttype");
            return (Criteria) this;
        }

        public Criteria andParenttypeNotBetween(Integer value1, Integer value2) {
            addCriterion("PARENTTYPE not between", value1, value2, "parenttype");
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

        public Criteria andActivityIsNull() {
            addCriterion("ACTIVITY is null");
            return (Criteria) this;
        }

        public Criteria andActivityIsNotNull() {
            addCriterion("ACTIVITY is not null");
            return (Criteria) this;
        }

        public Criteria andActivityEqualTo(Integer value) {
            addCriterion("ACTIVITY =", value, "activity");
            return (Criteria) this;
        }

        public Criteria andActivityNotEqualTo(Integer value) {
            addCriterion("ACTIVITY <>", value, "activity");
            return (Criteria) this;
        }

        public Criteria andActivityGreaterThan(Integer value) {
            addCriterion("ACTIVITY >", value, "activity");
            return (Criteria) this;
        }

        public Criteria andActivityGreaterThanOrEqualTo(Integer value) {
            addCriterion("ACTIVITY >=", value, "activity");
            return (Criteria) this;
        }

        public Criteria andActivityLessThan(Integer value) {
            addCriterion("ACTIVITY <", value, "activity");
            return (Criteria) this;
        }

        public Criteria andActivityLessThanOrEqualTo(Integer value) {
            addCriterion("ACTIVITY <=", value, "activity");
            return (Criteria) this;
        }

        public Criteria andActivityIn(List<Integer> values) {
            addCriterion("ACTIVITY in", values, "activity");
            return (Criteria) this;
        }

        public Criteria andActivityNotIn(List<Integer> values) {
            addCriterion("ACTIVITY not in", values, "activity");
            return (Criteria) this;
        }

        public Criteria andActivityBetween(Integer value1, Integer value2) {
            addCriterion("ACTIVITY between", value1, value2, "activity");
            return (Criteria) this;
        }

        public Criteria andActivityNotBetween(Integer value1, Integer value2) {
            addCriterion("ACTIVITY not between", value1, value2, "activity");
            return (Criteria) this;
        }
    }

    /**
     * This class corresponds to the database table tb_product_info
    @mbggenerated do_not_delete_during_merge 2016-10-31 15:59:58
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * [STRATO MyBatis Generator]
     * Table: tb_product_info
    @mbggenerated 2016-10-31 15:59:58
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