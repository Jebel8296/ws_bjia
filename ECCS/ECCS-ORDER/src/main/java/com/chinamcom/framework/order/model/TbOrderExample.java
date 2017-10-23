package com.chinamcom.framework.order.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbOrderExample {
    /**
     * Table: tb_order
    @mbggenerated 2017-05-22 14:16:05
     */
    protected String orderByClause;

    /**
     * Table: tb_order
    @mbggenerated 2017-05-22 14:16:05
     */
    protected boolean distinct;

    /**
     * Table: tb_order
    @mbggenerated 2017-05-22 14:16:05
     */
    protected List<Criteria> oredCriteria;

    public TbOrderExample() {
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
     * Table: tb_order
    @mbggenerated 2017-05-22 14:16:05
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

        public Criteria andSerialNumberIsNull() {
            addCriterion("SERIAL_NUMBER is null");
            return (Criteria) this;
        }

        public Criteria andSerialNumberIsNotNull() {
            addCriterion("SERIAL_NUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andSerialNumberEqualTo(String value) {
            addCriterion("SERIAL_NUMBER =", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberNotEqualTo(String value) {
            addCriterion("SERIAL_NUMBER <>", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberGreaterThan(String value) {
            addCriterion("SERIAL_NUMBER >", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberGreaterThanOrEqualTo(String value) {
            addCriterion("SERIAL_NUMBER >=", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberLessThan(String value) {
            addCriterion("SERIAL_NUMBER <", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberLessThanOrEqualTo(String value) {
            addCriterion("SERIAL_NUMBER <=", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberLike(String value) {
            addCriterion("SERIAL_NUMBER like", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberNotLike(String value) {
            addCriterion("SERIAL_NUMBER not like", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberIn(List<String> values) {
            addCriterion("SERIAL_NUMBER in", values, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberNotIn(List<String> values) {
            addCriterion("SERIAL_NUMBER not in", values, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberBetween(String value1, String value2) {
            addCriterion("SERIAL_NUMBER between", value1, value2, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberNotBetween(String value1, String value2) {
            addCriterion("SERIAL_NUMBER not between", value1, value2, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNull() {
            addCriterion("ORDER_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNotNull() {
            addCriterion("ORDER_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeEqualTo(Integer value) {
            addCriterion("ORDER_TYPE =", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotEqualTo(Integer value) {
            addCriterion("ORDER_TYPE <>", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThan(Integer value) {
            addCriterion("ORDER_TYPE >", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("ORDER_TYPE >=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThan(Integer value) {
            addCriterion("ORDER_TYPE <", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThanOrEqualTo(Integer value) {
            addCriterion("ORDER_TYPE <=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIn(List<Integer> values) {
            addCriterion("ORDER_TYPE in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotIn(List<Integer> values) {
            addCriterion("ORDER_TYPE not in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeBetween(Integer value1, Integer value2) {
            addCriterion("ORDER_TYPE between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("ORDER_TYPE not between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andFeeIsNull() {
            addCriterion("FEE is null");
            return (Criteria) this;
        }

        public Criteria andFeeIsNotNull() {
            addCriterion("FEE is not null");
            return (Criteria) this;
        }

        public Criteria andFeeEqualTo(BigDecimal value) {
            addCriterion("FEE =", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeNotEqualTo(BigDecimal value) {
            addCriterion("FEE <>", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeGreaterThan(BigDecimal value) {
            addCriterion("FEE >", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("FEE >=", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeLessThan(BigDecimal value) {
            addCriterion("FEE <", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("FEE <=", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeIn(List<BigDecimal> values) {
            addCriterion("FEE in", values, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeNotIn(List<BigDecimal> values) {
            addCriterion("FEE not in", values, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FEE between", value1, value2, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FEE not between", value1, value2, "fee");
            return (Criteria) this;
        }

        public Criteria andPayFeeIsNull() {
            addCriterion("PAY_FEE is null");
            return (Criteria) this;
        }

        public Criteria andPayFeeIsNotNull() {
            addCriterion("PAY_FEE is not null");
            return (Criteria) this;
        }

        public Criteria andPayFeeEqualTo(BigDecimal value) {
            addCriterion("PAY_FEE =", value, "payFee");
            return (Criteria) this;
        }

        public Criteria andPayFeeNotEqualTo(BigDecimal value) {
            addCriterion("PAY_FEE <>", value, "payFee");
            return (Criteria) this;
        }

        public Criteria andPayFeeGreaterThan(BigDecimal value) {
            addCriterion("PAY_FEE >", value, "payFee");
            return (Criteria) this;
        }

        public Criteria andPayFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PAY_FEE >=", value, "payFee");
            return (Criteria) this;
        }

        public Criteria andPayFeeLessThan(BigDecimal value) {
            addCriterion("PAY_FEE <", value, "payFee");
            return (Criteria) this;
        }

        public Criteria andPayFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PAY_FEE <=", value, "payFee");
            return (Criteria) this;
        }

        public Criteria andPayFeeIn(List<BigDecimal> values) {
            addCriterion("PAY_FEE in", values, "payFee");
            return (Criteria) this;
        }

        public Criteria andPayFeeNotIn(List<BigDecimal> values) {
            addCriterion("PAY_FEE not in", values, "payFee");
            return (Criteria) this;
        }

        public Criteria andPayFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PAY_FEE between", value1, value2, "payFee");
            return (Criteria) this;
        }

        public Criteria andPayFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PAY_FEE not between", value1, value2, "payFee");
            return (Criteria) this;
        }

        public Criteria andReduceFeeIsNull() {
            addCriterion("REDUCE_FEE is null");
            return (Criteria) this;
        }

        public Criteria andReduceFeeIsNotNull() {
            addCriterion("REDUCE_FEE is not null");
            return (Criteria) this;
        }

        public Criteria andReduceFeeEqualTo(BigDecimal value) {
            addCriterion("REDUCE_FEE =", value, "reduceFee");
            return (Criteria) this;
        }

        public Criteria andReduceFeeNotEqualTo(BigDecimal value) {
            addCriterion("REDUCE_FEE <>", value, "reduceFee");
            return (Criteria) this;
        }

        public Criteria andReduceFeeGreaterThan(BigDecimal value) {
            addCriterion("REDUCE_FEE >", value, "reduceFee");
            return (Criteria) this;
        }

        public Criteria andReduceFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REDUCE_FEE >=", value, "reduceFee");
            return (Criteria) this;
        }

        public Criteria andReduceFeeLessThan(BigDecimal value) {
            addCriterion("REDUCE_FEE <", value, "reduceFee");
            return (Criteria) this;
        }

        public Criteria andReduceFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REDUCE_FEE <=", value, "reduceFee");
            return (Criteria) this;
        }

        public Criteria andReduceFeeIn(List<BigDecimal> values) {
            addCriterion("REDUCE_FEE in", values, "reduceFee");
            return (Criteria) this;
        }

        public Criteria andReduceFeeNotIn(List<BigDecimal> values) {
            addCriterion("REDUCE_FEE not in", values, "reduceFee");
            return (Criteria) this;
        }

        public Criteria andReduceFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REDUCE_FEE between", value1, value2, "reduceFee");
            return (Criteria) this;
        }

        public Criteria andReduceFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REDUCE_FEE not between", value1, value2, "reduceFee");
            return (Criteria) this;
        }

        public Criteria andExpressPayIsNull() {
            addCriterion("EXPRESS_PAY is null");
            return (Criteria) this;
        }

        public Criteria andExpressPayIsNotNull() {
            addCriterion("EXPRESS_PAY is not null");
            return (Criteria) this;
        }

        public Criteria andExpressPayEqualTo(BigDecimal value) {
            addCriterion("EXPRESS_PAY =", value, "expressPay");
            return (Criteria) this;
        }

        public Criteria andExpressPayNotEqualTo(BigDecimal value) {
            addCriterion("EXPRESS_PAY <>", value, "expressPay");
            return (Criteria) this;
        }

        public Criteria andExpressPayGreaterThan(BigDecimal value) {
            addCriterion("EXPRESS_PAY >", value, "expressPay");
            return (Criteria) this;
        }

        public Criteria andExpressPayGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("EXPRESS_PAY >=", value, "expressPay");
            return (Criteria) this;
        }

        public Criteria andExpressPayLessThan(BigDecimal value) {
            addCriterion("EXPRESS_PAY <", value, "expressPay");
            return (Criteria) this;
        }

        public Criteria andExpressPayLessThanOrEqualTo(BigDecimal value) {
            addCriterion("EXPRESS_PAY <=", value, "expressPay");
            return (Criteria) this;
        }

        public Criteria andExpressPayIn(List<BigDecimal> values) {
            addCriterion("EXPRESS_PAY in", values, "expressPay");
            return (Criteria) this;
        }

        public Criteria andExpressPayNotIn(List<BigDecimal> values) {
            addCriterion("EXPRESS_PAY not in", values, "expressPay");
            return (Criteria) this;
        }

        public Criteria andExpressPayBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("EXPRESS_PAY between", value1, value2, "expressPay");
            return (Criteria) this;
        }

        public Criteria andExpressPayNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("EXPRESS_PAY not between", value1, value2, "expressPay");
            return (Criteria) this;
        }

        public Criteria andChannelIsNull() {
            addCriterion("CHANNEL is null");
            return (Criteria) this;
        }

        public Criteria andChannelIsNotNull() {
            addCriterion("CHANNEL is not null");
            return (Criteria) this;
        }

        public Criteria andChannelEqualTo(String value) {
            addCriterion("CHANNEL =", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotEqualTo(String value) {
            addCriterion("CHANNEL <>", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelGreaterThan(String value) {
            addCriterion("CHANNEL >", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelGreaterThanOrEqualTo(String value) {
            addCriterion("CHANNEL >=", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLessThan(String value) {
            addCriterion("CHANNEL <", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLessThanOrEqualTo(String value) {
            addCriterion("CHANNEL <=", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLike(String value) {
            addCriterion("CHANNEL like", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotLike(String value) {
            addCriterion("CHANNEL not like", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelIn(List<String> values) {
            addCriterion("CHANNEL in", values, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotIn(List<String> values) {
            addCriterion("CHANNEL not in", values, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelBetween(String value1, String value2) {
            addCriterion("CHANNEL between", value1, value2, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotBetween(String value1, String value2) {
            addCriterion("CHANNEL not between", value1, value2, "channel");
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

        public Criteria andUserIdIsNull() {
            addCriterion("USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("USER_ID =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("USER_ID <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("USER_ID >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("USER_ID >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("USER_ID <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("USER_ID <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("USER_ID in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("USER_ID not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("USER_ID between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("USER_ID not between", value1, value2, "userId");
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

        public Criteria andPayTimeIsNull() {
            addCriterion("PAY_TIME is null");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNotNull() {
            addCriterion("PAY_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andPayTimeEqualTo(Date value) {
            addCriterion("PAY_TIME =", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotEqualTo(Date value) {
            addCriterion("PAY_TIME <>", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThan(Date value) {
            addCriterion("PAY_TIME >", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("PAY_TIME >=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThan(Date value) {
            addCriterion("PAY_TIME <", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThanOrEqualTo(Date value) {
            addCriterion("PAY_TIME <=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeIn(List<Date> values) {
            addCriterion("PAY_TIME in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotIn(List<Date> values) {
            addCriterion("PAY_TIME not in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeBetween(Date value1, Date value2) {
            addCriterion("PAY_TIME between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotBetween(Date value1, Date value2) {
            addCriterion("PAY_TIME not between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeIsNull() {
            addCriterion("SEND_TIME is null");
            return (Criteria) this;
        }

        public Criteria andSendTimeIsNotNull() {
            addCriterion("SEND_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andSendTimeEqualTo(Date value) {
            addCriterion("SEND_TIME =", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotEqualTo(Date value) {
            addCriterion("SEND_TIME <>", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeGreaterThan(Date value) {
            addCriterion("SEND_TIME >", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("SEND_TIME >=", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeLessThan(Date value) {
            addCriterion("SEND_TIME <", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeLessThanOrEqualTo(Date value) {
            addCriterion("SEND_TIME <=", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeIn(List<Date> values) {
            addCriterion("SEND_TIME in", values, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotIn(List<Date> values) {
            addCriterion("SEND_TIME not in", values, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeBetween(Date value1, Date value2) {
            addCriterion("SEND_TIME between", value1, value2, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotBetween(Date value1, Date value2) {
            addCriterion("SEND_TIME not between", value1, value2, "sendTime");
            return (Criteria) this;
        }

        public Criteria andCompateTimeIsNull() {
            addCriterion("COMPATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCompateTimeIsNotNull() {
            addCriterion("COMPATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCompateTimeEqualTo(Date value) {
            addCriterion("COMPATE_TIME =", value, "compateTime");
            return (Criteria) this;
        }

        public Criteria andCompateTimeNotEqualTo(Date value) {
            addCriterion("COMPATE_TIME <>", value, "compateTime");
            return (Criteria) this;
        }

        public Criteria andCompateTimeGreaterThan(Date value) {
            addCriterion("COMPATE_TIME >", value, "compateTime");
            return (Criteria) this;
        }

        public Criteria andCompateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("COMPATE_TIME >=", value, "compateTime");
            return (Criteria) this;
        }

        public Criteria andCompateTimeLessThan(Date value) {
            addCriterion("COMPATE_TIME <", value, "compateTime");
            return (Criteria) this;
        }

        public Criteria andCompateTimeLessThanOrEqualTo(Date value) {
            addCriterion("COMPATE_TIME <=", value, "compateTime");
            return (Criteria) this;
        }

        public Criteria andCompateTimeIn(List<Date> values) {
            addCriterion("COMPATE_TIME in", values, "compateTime");
            return (Criteria) this;
        }

        public Criteria andCompateTimeNotIn(List<Date> values) {
            addCriterion("COMPATE_TIME not in", values, "compateTime");
            return (Criteria) this;
        }

        public Criteria andCompateTimeBetween(Date value1, Date value2) {
            addCriterion("COMPATE_TIME between", value1, value2, "compateTime");
            return (Criteria) this;
        }

        public Criteria andCompateTimeNotBetween(Date value1, Date value2) {
            addCriterion("COMPATE_TIME not between", value1, value2, "compateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("UPDATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("UPDATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("UPDATE_TIME =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("UPDATE_TIME <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("UPDATE_TIME >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("UPDATE_TIME >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("UPDATE_TIME <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("UPDATE_TIME <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("UPDATE_TIME in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("UPDATE_TIME not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("UPDATE_TIME between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("UPDATE_TIME not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("DESCRIPTION is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("DESCRIPTION is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("DESCRIPTION =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("DESCRIPTION <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("DESCRIPTION >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("DESCRIPTION >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("DESCRIPTION <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("DESCRIPTION <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("DESCRIPTION like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("DESCRIPTION not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("DESCRIPTION in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("DESCRIPTION not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("DESCRIPTION between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("DESCRIPTION not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andExpressIdIsNull() {
            addCriterion("EXPRESS_ID is null");
            return (Criteria) this;
        }

        public Criteria andExpressIdIsNotNull() {
            addCriterion("EXPRESS_ID is not null");
            return (Criteria) this;
        }

        public Criteria andExpressIdEqualTo(Integer value) {
            addCriterion("EXPRESS_ID =", value, "expressId");
            return (Criteria) this;
        }

        public Criteria andExpressIdNotEqualTo(Integer value) {
            addCriterion("EXPRESS_ID <>", value, "expressId");
            return (Criteria) this;
        }

        public Criteria andExpressIdGreaterThan(Integer value) {
            addCriterion("EXPRESS_ID >", value, "expressId");
            return (Criteria) this;
        }

        public Criteria andExpressIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("EXPRESS_ID >=", value, "expressId");
            return (Criteria) this;
        }

        public Criteria andExpressIdLessThan(Integer value) {
            addCriterion("EXPRESS_ID <", value, "expressId");
            return (Criteria) this;
        }

        public Criteria andExpressIdLessThanOrEqualTo(Integer value) {
            addCriterion("EXPRESS_ID <=", value, "expressId");
            return (Criteria) this;
        }

        public Criteria andExpressIdIn(List<Integer> values) {
            addCriterion("EXPRESS_ID in", values, "expressId");
            return (Criteria) this;
        }

        public Criteria andExpressIdNotIn(List<Integer> values) {
            addCriterion("EXPRESS_ID not in", values, "expressId");
            return (Criteria) this;
        }

        public Criteria andExpressIdBetween(Integer value1, Integer value2) {
            addCriterion("EXPRESS_ID between", value1, value2, "expressId");
            return (Criteria) this;
        }

        public Criteria andExpressIdNotBetween(Integer value1, Integer value2) {
            addCriterion("EXPRESS_ID not between", value1, value2, "expressId");
            return (Criteria) this;
        }

        public Criteria andLogisticsNameIsNull() {
            addCriterion("LOGISTICS_NAME is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsNameIsNotNull() {
            addCriterion("LOGISTICS_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsNameEqualTo(String value) {
            addCriterion("LOGISTICS_NAME =", value, "logisticsName");
            return (Criteria) this;
        }

        public Criteria andLogisticsNameNotEqualTo(String value) {
            addCriterion("LOGISTICS_NAME <>", value, "logisticsName");
            return (Criteria) this;
        }

        public Criteria andLogisticsNameGreaterThan(String value) {
            addCriterion("LOGISTICS_NAME >", value, "logisticsName");
            return (Criteria) this;
        }

        public Criteria andLogisticsNameGreaterThanOrEqualTo(String value) {
            addCriterion("LOGISTICS_NAME >=", value, "logisticsName");
            return (Criteria) this;
        }

        public Criteria andLogisticsNameLessThan(String value) {
            addCriterion("LOGISTICS_NAME <", value, "logisticsName");
            return (Criteria) this;
        }

        public Criteria andLogisticsNameLessThanOrEqualTo(String value) {
            addCriterion("LOGISTICS_NAME <=", value, "logisticsName");
            return (Criteria) this;
        }

        public Criteria andLogisticsNameLike(String value) {
            addCriterion("LOGISTICS_NAME like", value, "logisticsName");
            return (Criteria) this;
        }

        public Criteria andLogisticsNameNotLike(String value) {
            addCriterion("LOGISTICS_NAME not like", value, "logisticsName");
            return (Criteria) this;
        }

        public Criteria andLogisticsNameIn(List<String> values) {
            addCriterion("LOGISTICS_NAME in", values, "logisticsName");
            return (Criteria) this;
        }

        public Criteria andLogisticsNameNotIn(List<String> values) {
            addCriterion("LOGISTICS_NAME not in", values, "logisticsName");
            return (Criteria) this;
        }

        public Criteria andLogisticsNameBetween(String value1, String value2) {
            addCriterion("LOGISTICS_NAME between", value1, value2, "logisticsName");
            return (Criteria) this;
        }

        public Criteria andLogisticsNameNotBetween(String value1, String value2) {
            addCriterion("LOGISTICS_NAME not between", value1, value2, "logisticsName");
            return (Criteria) this;
        }

        public Criteria andLogisticsCdoeIsNull() {
            addCriterion("LOGISTICS_CDOE is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsCdoeIsNotNull() {
            addCriterion("LOGISTICS_CDOE is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsCdoeEqualTo(String value) {
            addCriterion("LOGISTICS_CDOE =", value, "logisticsCdoe");
            return (Criteria) this;
        }

        public Criteria andLogisticsCdoeNotEqualTo(String value) {
            addCriterion("LOGISTICS_CDOE <>", value, "logisticsCdoe");
            return (Criteria) this;
        }

        public Criteria andLogisticsCdoeGreaterThan(String value) {
            addCriterion("LOGISTICS_CDOE >", value, "logisticsCdoe");
            return (Criteria) this;
        }

        public Criteria andLogisticsCdoeGreaterThanOrEqualTo(String value) {
            addCriterion("LOGISTICS_CDOE >=", value, "logisticsCdoe");
            return (Criteria) this;
        }

        public Criteria andLogisticsCdoeLessThan(String value) {
            addCriterion("LOGISTICS_CDOE <", value, "logisticsCdoe");
            return (Criteria) this;
        }

        public Criteria andLogisticsCdoeLessThanOrEqualTo(String value) {
            addCriterion("LOGISTICS_CDOE <=", value, "logisticsCdoe");
            return (Criteria) this;
        }

        public Criteria andLogisticsCdoeLike(String value) {
            addCriterion("LOGISTICS_CDOE like", value, "logisticsCdoe");
            return (Criteria) this;
        }

        public Criteria andLogisticsCdoeNotLike(String value) {
            addCriterion("LOGISTICS_CDOE not like", value, "logisticsCdoe");
            return (Criteria) this;
        }

        public Criteria andLogisticsCdoeIn(List<String> values) {
            addCriterion("LOGISTICS_CDOE in", values, "logisticsCdoe");
            return (Criteria) this;
        }

        public Criteria andLogisticsCdoeNotIn(List<String> values) {
            addCriterion("LOGISTICS_CDOE not in", values, "logisticsCdoe");
            return (Criteria) this;
        }

        public Criteria andLogisticsCdoeBetween(String value1, String value2) {
            addCriterion("LOGISTICS_CDOE between", value1, value2, "logisticsCdoe");
            return (Criteria) this;
        }

        public Criteria andLogisticsCdoeNotBetween(String value1, String value2) {
            addCriterion("LOGISTICS_CDOE not between", value1, value2, "logisticsCdoe");
            return (Criteria) this;
        }

        public Criteria andLogisticsNumberIsNull() {
            addCriterion("LOGISTICS_NUMBER is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsNumberIsNotNull() {
            addCriterion("LOGISTICS_NUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsNumberEqualTo(String value) {
            addCriterion("LOGISTICS_NUMBER =", value, "logisticsNumber");
            return (Criteria) this;
        }

        public Criteria andLogisticsNumberNotEqualTo(String value) {
            addCriterion("LOGISTICS_NUMBER <>", value, "logisticsNumber");
            return (Criteria) this;
        }

        public Criteria andLogisticsNumberGreaterThan(String value) {
            addCriterion("LOGISTICS_NUMBER >", value, "logisticsNumber");
            return (Criteria) this;
        }

        public Criteria andLogisticsNumberGreaterThanOrEqualTo(String value) {
            addCriterion("LOGISTICS_NUMBER >=", value, "logisticsNumber");
            return (Criteria) this;
        }

        public Criteria andLogisticsNumberLessThan(String value) {
            addCriterion("LOGISTICS_NUMBER <", value, "logisticsNumber");
            return (Criteria) this;
        }

        public Criteria andLogisticsNumberLessThanOrEqualTo(String value) {
            addCriterion("LOGISTICS_NUMBER <=", value, "logisticsNumber");
            return (Criteria) this;
        }

        public Criteria andLogisticsNumberLike(String value) {
            addCriterion("LOGISTICS_NUMBER like", value, "logisticsNumber");
            return (Criteria) this;
        }

        public Criteria andLogisticsNumberNotLike(String value) {
            addCriterion("LOGISTICS_NUMBER not like", value, "logisticsNumber");
            return (Criteria) this;
        }

        public Criteria andLogisticsNumberIn(List<String> values) {
            addCriterion("LOGISTICS_NUMBER in", values, "logisticsNumber");
            return (Criteria) this;
        }

        public Criteria andLogisticsNumberNotIn(List<String> values) {
            addCriterion("LOGISTICS_NUMBER not in", values, "logisticsNumber");
            return (Criteria) this;
        }

        public Criteria andLogisticsNumberBetween(String value1, String value2) {
            addCriterion("LOGISTICS_NUMBER between", value1, value2, "logisticsNumber");
            return (Criteria) this;
        }

        public Criteria andLogisticsNumberNotBetween(String value1, String value2) {
            addCriterion("LOGISTICS_NUMBER not between", value1, value2, "logisticsNumber");
            return (Criteria) this;
        }

        public Criteria andDeliversTimeIsNull() {
            addCriterion("DELIVERS_TIME is null");
            return (Criteria) this;
        }

        public Criteria andDeliversTimeIsNotNull() {
            addCriterion("DELIVERS_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andDeliversTimeEqualTo(String value) {
            addCriterion("DELIVERS_TIME =", value, "deliversTime");
            return (Criteria) this;
        }

        public Criteria andDeliversTimeNotEqualTo(String value) {
            addCriterion("DELIVERS_TIME <>", value, "deliversTime");
            return (Criteria) this;
        }

        public Criteria andDeliversTimeGreaterThan(String value) {
            addCriterion("DELIVERS_TIME >", value, "deliversTime");
            return (Criteria) this;
        }

        public Criteria andDeliversTimeGreaterThanOrEqualTo(String value) {
            addCriterion("DELIVERS_TIME >=", value, "deliversTime");
            return (Criteria) this;
        }

        public Criteria andDeliversTimeLessThan(String value) {
            addCriterion("DELIVERS_TIME <", value, "deliversTime");
            return (Criteria) this;
        }

        public Criteria andDeliversTimeLessThanOrEqualTo(String value) {
            addCriterion("DELIVERS_TIME <=", value, "deliversTime");
            return (Criteria) this;
        }

        public Criteria andDeliversTimeLike(String value) {
            addCriterion("DELIVERS_TIME like", value, "deliversTime");
            return (Criteria) this;
        }

        public Criteria andDeliversTimeNotLike(String value) {
            addCriterion("DELIVERS_TIME not like", value, "deliversTime");
            return (Criteria) this;
        }

        public Criteria andDeliversTimeIn(List<String> values) {
            addCriterion("DELIVERS_TIME in", values, "deliversTime");
            return (Criteria) this;
        }

        public Criteria andDeliversTimeNotIn(List<String> values) {
            addCriterion("DELIVERS_TIME not in", values, "deliversTime");
            return (Criteria) this;
        }

        public Criteria andDeliversTimeBetween(String value1, String value2) {
            addCriterion("DELIVERS_TIME between", value1, value2, "deliversTime");
            return (Criteria) this;
        }

        public Criteria andDeliversTimeNotBetween(String value1, String value2) {
            addCriterion("DELIVERS_TIME not between", value1, value2, "deliversTime");
            return (Criteria) this;
        }

        public Criteria andInvoceHeadIsNull() {
            addCriterion("INVOCE_HEAD is null");
            return (Criteria) this;
        }

        public Criteria andInvoceHeadIsNotNull() {
            addCriterion("INVOCE_HEAD is not null");
            return (Criteria) this;
        }

        public Criteria andInvoceHeadEqualTo(String value) {
            addCriterion("INVOCE_HEAD =", value, "invoceHead");
            return (Criteria) this;
        }

        public Criteria andInvoceHeadNotEqualTo(String value) {
            addCriterion("INVOCE_HEAD <>", value, "invoceHead");
            return (Criteria) this;
        }

        public Criteria andInvoceHeadGreaterThan(String value) {
            addCriterion("INVOCE_HEAD >", value, "invoceHead");
            return (Criteria) this;
        }

        public Criteria andInvoceHeadGreaterThanOrEqualTo(String value) {
            addCriterion("INVOCE_HEAD >=", value, "invoceHead");
            return (Criteria) this;
        }

        public Criteria andInvoceHeadLessThan(String value) {
            addCriterion("INVOCE_HEAD <", value, "invoceHead");
            return (Criteria) this;
        }

        public Criteria andInvoceHeadLessThanOrEqualTo(String value) {
            addCriterion("INVOCE_HEAD <=", value, "invoceHead");
            return (Criteria) this;
        }

        public Criteria andInvoceHeadLike(String value) {
            addCriterion("INVOCE_HEAD like", value, "invoceHead");
            return (Criteria) this;
        }

        public Criteria andInvoceHeadNotLike(String value) {
            addCriterion("INVOCE_HEAD not like", value, "invoceHead");
            return (Criteria) this;
        }

        public Criteria andInvoceHeadIn(List<String> values) {
            addCriterion("INVOCE_HEAD in", values, "invoceHead");
            return (Criteria) this;
        }

        public Criteria andInvoceHeadNotIn(List<String> values) {
            addCriterion("INVOCE_HEAD not in", values, "invoceHead");
            return (Criteria) this;
        }

        public Criteria andInvoceHeadBetween(String value1, String value2) {
            addCriterion("INVOCE_HEAD between", value1, value2, "invoceHead");
            return (Criteria) this;
        }

        public Criteria andInvoceHeadNotBetween(String value1, String value2) {
            addCriterion("INVOCE_HEAD not between", value1, value2, "invoceHead");
            return (Criteria) this;
        }

        public Criteria andAftersaleCodeIsNull() {
            addCriterion("AFTERSALE_CODE is null");
            return (Criteria) this;
        }

        public Criteria andAftersaleCodeIsNotNull() {
            addCriterion("AFTERSALE_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andAftersaleCodeEqualTo(String value) {
            addCriterion("AFTERSALE_CODE =", value, "aftersaleCode");
            return (Criteria) this;
        }

        public Criteria andAftersaleCodeNotEqualTo(String value) {
            addCriterion("AFTERSALE_CODE <>", value, "aftersaleCode");
            return (Criteria) this;
        }

        public Criteria andAftersaleCodeGreaterThan(String value) {
            addCriterion("AFTERSALE_CODE >", value, "aftersaleCode");
            return (Criteria) this;
        }

        public Criteria andAftersaleCodeGreaterThanOrEqualTo(String value) {
            addCriterion("AFTERSALE_CODE >=", value, "aftersaleCode");
            return (Criteria) this;
        }

        public Criteria andAftersaleCodeLessThan(String value) {
            addCriterion("AFTERSALE_CODE <", value, "aftersaleCode");
            return (Criteria) this;
        }

        public Criteria andAftersaleCodeLessThanOrEqualTo(String value) {
            addCriterion("AFTERSALE_CODE <=", value, "aftersaleCode");
            return (Criteria) this;
        }

        public Criteria andAftersaleCodeLike(String value) {
            addCriterion("AFTERSALE_CODE like", value, "aftersaleCode");
            return (Criteria) this;
        }

        public Criteria andAftersaleCodeNotLike(String value) {
            addCriterion("AFTERSALE_CODE not like", value, "aftersaleCode");
            return (Criteria) this;
        }

        public Criteria andAftersaleCodeIn(List<String> values) {
            addCriterion("AFTERSALE_CODE in", values, "aftersaleCode");
            return (Criteria) this;
        }

        public Criteria andAftersaleCodeNotIn(List<String> values) {
            addCriterion("AFTERSALE_CODE not in", values, "aftersaleCode");
            return (Criteria) this;
        }

        public Criteria andAftersaleCodeBetween(String value1, String value2) {
            addCriterion("AFTERSALE_CODE between", value1, value2, "aftersaleCode");
            return (Criteria) this;
        }

        public Criteria andAftersaleCodeNotBetween(String value1, String value2) {
            addCriterion("AFTERSALE_CODE not between", value1, value2, "aftersaleCode");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("REMARK is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("REMARK =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("REMARK <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("REMARK >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("REMARK >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("REMARK <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("REMARK <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("REMARK like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("REMARK not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("REMARK in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("REMARK not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("REMARK between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("REMARK not between", value1, value2, "remark");
            return (Criteria) this;
        }
    }

    /**
     * This class corresponds to the database table tb_order
    @mbggenerated do_not_delete_during_merge 2017-05-22 14:16:05
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * [STRATO MyBatis Generator]
     * Table: tb_order
    @mbggenerated 2017-05-22 14:16:05
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