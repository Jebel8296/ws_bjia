package com.chinamcom.framework.payment.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbOrderPaymentExample {
    /**
     * Table: tb_order_payment
    @mbggenerated 2016-12-01 14:03:40
     */
    protected String orderByClause;

    /**
     * Table: tb_order_payment
    @mbggenerated 2016-12-01 14:03:40
     */
    protected boolean distinct;

    /**
     * Table: tb_order_payment
    @mbggenerated 2016-12-01 14:03:40
     */
    protected List<Criteria> oredCriteria;

    public TbOrderPaymentExample() {
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
     * Table: tb_order_payment
    @mbggenerated 2016-12-01 14:03:40
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

        public Criteria andOrderIdIsNull() {
            addCriterion("ORDER_ID is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("ORDER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(Integer value) {
            addCriterion("ORDER_ID =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(Integer value) {
            addCriterion("ORDER_ID <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(Integer value) {
            addCriterion("ORDER_ID >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ORDER_ID >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(Integer value) {
            addCriterion("ORDER_ID <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(Integer value) {
            addCriterion("ORDER_ID <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<Integer> values) {
            addCriterion("ORDER_ID in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<Integer> values) {
            addCriterion("ORDER_ID not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(Integer value1, Integer value2) {
            addCriterion("ORDER_ID between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ORDER_ID not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderCodeIsNull() {
            addCriterion("ORDER_CODE is null");
            return (Criteria) this;
        }

        public Criteria andOrderCodeIsNotNull() {
            addCriterion("ORDER_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andOrderCodeEqualTo(String value) {
            addCriterion("ORDER_CODE =", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotEqualTo(String value) {
            addCriterion("ORDER_CODE <>", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeGreaterThan(String value) {
            addCriterion("ORDER_CODE >", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeGreaterThanOrEqualTo(String value) {
            addCriterion("ORDER_CODE >=", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeLessThan(String value) {
            addCriterion("ORDER_CODE <", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeLessThanOrEqualTo(String value) {
            addCriterion("ORDER_CODE <=", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeLike(String value) {
            addCriterion("ORDER_CODE like", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotLike(String value) {
            addCriterion("ORDER_CODE not like", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeIn(List<String> values) {
            addCriterion("ORDER_CODE in", values, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotIn(List<String> values) {
            addCriterion("ORDER_CODE not in", values, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeBetween(String value1, String value2) {
            addCriterion("ORDER_CODE between", value1, value2, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotBetween(String value1, String value2) {
            addCriterion("ORDER_CODE not between", value1, value2, "orderCode");
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

        public Criteria andPayTypeIsNull() {
            addCriterion("PAY_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andPayTypeIsNotNull() {
            addCriterion("PAY_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andPayTypeEqualTo(Integer value) {
            addCriterion("PAY_TYPE =", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotEqualTo(Integer value) {
            addCriterion("PAY_TYPE <>", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThan(Integer value) {
            addCriterion("PAY_TYPE >", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("PAY_TYPE >=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThan(Integer value) {
            addCriterion("PAY_TYPE <", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThanOrEqualTo(Integer value) {
            addCriterion("PAY_TYPE <=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeIn(List<Integer> values) {
            addCriterion("PAY_TYPE in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotIn(List<Integer> values) {
            addCriterion("PAY_TYPE not in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeBetween(Integer value1, Integer value2) {
            addCriterion("PAY_TYPE between", value1, value2, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("PAY_TYPE not between", value1, value2, "payType");
            return (Criteria) this;
        }

        public Criteria andPayChannelIsNull() {
            addCriterion("PAY_CHANNEL is null");
            return (Criteria) this;
        }

        public Criteria andPayChannelIsNotNull() {
            addCriterion("PAY_CHANNEL is not null");
            return (Criteria) this;
        }

        public Criteria andPayChannelEqualTo(String value) {
            addCriterion("PAY_CHANNEL =", value, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayChannelNotEqualTo(String value) {
            addCriterion("PAY_CHANNEL <>", value, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayChannelGreaterThan(String value) {
            addCriterion("PAY_CHANNEL >", value, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayChannelGreaterThanOrEqualTo(String value) {
            addCriterion("PAY_CHANNEL >=", value, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayChannelLessThan(String value) {
            addCriterion("PAY_CHANNEL <", value, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayChannelLessThanOrEqualTo(String value) {
            addCriterion("PAY_CHANNEL <=", value, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayChannelLike(String value) {
            addCriterion("PAY_CHANNEL like", value, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayChannelNotLike(String value) {
            addCriterion("PAY_CHANNEL not like", value, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayChannelIn(List<String> values) {
            addCriterion("PAY_CHANNEL in", values, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayChannelNotIn(List<String> values) {
            addCriterion("PAY_CHANNEL not in", values, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayChannelBetween(String value1, String value2) {
            addCriterion("PAY_CHANNEL between", value1, value2, "payChannel");
            return (Criteria) this;
        }

        public Criteria andPayChannelNotBetween(String value1, String value2) {
            addCriterion("PAY_CHANNEL not between", value1, value2, "payChannel");
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

        public Criteria andPayReqIsNull() {
            addCriterion("PAY_REQ is null");
            return (Criteria) this;
        }

        public Criteria andPayReqIsNotNull() {
            addCriterion("PAY_REQ is not null");
            return (Criteria) this;
        }

        public Criteria andPayReqEqualTo(String value) {
            addCriterion("PAY_REQ =", value, "payReq");
            return (Criteria) this;
        }

        public Criteria andPayReqNotEqualTo(String value) {
            addCriterion("PAY_REQ <>", value, "payReq");
            return (Criteria) this;
        }

        public Criteria andPayReqGreaterThan(String value) {
            addCriterion("PAY_REQ >", value, "payReq");
            return (Criteria) this;
        }

        public Criteria andPayReqGreaterThanOrEqualTo(String value) {
            addCriterion("PAY_REQ >=", value, "payReq");
            return (Criteria) this;
        }

        public Criteria andPayReqLessThan(String value) {
            addCriterion("PAY_REQ <", value, "payReq");
            return (Criteria) this;
        }

        public Criteria andPayReqLessThanOrEqualTo(String value) {
            addCriterion("PAY_REQ <=", value, "payReq");
            return (Criteria) this;
        }

        public Criteria andPayReqLike(String value) {
            addCriterion("PAY_REQ like", value, "payReq");
            return (Criteria) this;
        }

        public Criteria andPayReqNotLike(String value) {
            addCriterion("PAY_REQ not like", value, "payReq");
            return (Criteria) this;
        }

        public Criteria andPayReqIn(List<String> values) {
            addCriterion("PAY_REQ in", values, "payReq");
            return (Criteria) this;
        }

        public Criteria andPayReqNotIn(List<String> values) {
            addCriterion("PAY_REQ not in", values, "payReq");
            return (Criteria) this;
        }

        public Criteria andPayReqBetween(String value1, String value2) {
            addCriterion("PAY_REQ between", value1, value2, "payReq");
            return (Criteria) this;
        }

        public Criteria andPayReqNotBetween(String value1, String value2) {
            addCriterion("PAY_REQ not between", value1, value2, "payReq");
            return (Criteria) this;
        }

        public Criteria andPayReqTimeIsNull() {
            addCriterion("PAY_REQ_TIME is null");
            return (Criteria) this;
        }

        public Criteria andPayReqTimeIsNotNull() {
            addCriterion("PAY_REQ_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andPayReqTimeEqualTo(Date value) {
            addCriterion("PAY_REQ_TIME =", value, "payReqTime");
            return (Criteria) this;
        }

        public Criteria andPayReqTimeNotEqualTo(Date value) {
            addCriterion("PAY_REQ_TIME <>", value, "payReqTime");
            return (Criteria) this;
        }

        public Criteria andPayReqTimeGreaterThan(Date value) {
            addCriterion("PAY_REQ_TIME >", value, "payReqTime");
            return (Criteria) this;
        }

        public Criteria andPayReqTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("PAY_REQ_TIME >=", value, "payReqTime");
            return (Criteria) this;
        }

        public Criteria andPayReqTimeLessThan(Date value) {
            addCriterion("PAY_REQ_TIME <", value, "payReqTime");
            return (Criteria) this;
        }

        public Criteria andPayReqTimeLessThanOrEqualTo(Date value) {
            addCriterion("PAY_REQ_TIME <=", value, "payReqTime");
            return (Criteria) this;
        }

        public Criteria andPayReqTimeIn(List<Date> values) {
            addCriterion("PAY_REQ_TIME in", values, "payReqTime");
            return (Criteria) this;
        }

        public Criteria andPayReqTimeNotIn(List<Date> values) {
            addCriterion("PAY_REQ_TIME not in", values, "payReqTime");
            return (Criteria) this;
        }

        public Criteria andPayReqTimeBetween(Date value1, Date value2) {
            addCriterion("PAY_REQ_TIME between", value1, value2, "payReqTime");
            return (Criteria) this;
        }

        public Criteria andPayReqTimeNotBetween(Date value1, Date value2) {
            addCriterion("PAY_REQ_TIME not between", value1, value2, "payReqTime");
            return (Criteria) this;
        }

        public Criteria andPayRspIsNull() {
            addCriterion("PAY_RSP is null");
            return (Criteria) this;
        }

        public Criteria andPayRspIsNotNull() {
            addCriterion("PAY_RSP is not null");
            return (Criteria) this;
        }

        public Criteria andPayRspEqualTo(String value) {
            addCriterion("PAY_RSP =", value, "payRsp");
            return (Criteria) this;
        }

        public Criteria andPayRspNotEqualTo(String value) {
            addCriterion("PAY_RSP <>", value, "payRsp");
            return (Criteria) this;
        }

        public Criteria andPayRspGreaterThan(String value) {
            addCriterion("PAY_RSP >", value, "payRsp");
            return (Criteria) this;
        }

        public Criteria andPayRspGreaterThanOrEqualTo(String value) {
            addCriterion("PAY_RSP >=", value, "payRsp");
            return (Criteria) this;
        }

        public Criteria andPayRspLessThan(String value) {
            addCriterion("PAY_RSP <", value, "payRsp");
            return (Criteria) this;
        }

        public Criteria andPayRspLessThanOrEqualTo(String value) {
            addCriterion("PAY_RSP <=", value, "payRsp");
            return (Criteria) this;
        }

        public Criteria andPayRspLike(String value) {
            addCriterion("PAY_RSP like", value, "payRsp");
            return (Criteria) this;
        }

        public Criteria andPayRspNotLike(String value) {
            addCriterion("PAY_RSP not like", value, "payRsp");
            return (Criteria) this;
        }

        public Criteria andPayRspIn(List<String> values) {
            addCriterion("PAY_RSP in", values, "payRsp");
            return (Criteria) this;
        }

        public Criteria andPayRspNotIn(List<String> values) {
            addCriterion("PAY_RSP not in", values, "payRsp");
            return (Criteria) this;
        }

        public Criteria andPayRspBetween(String value1, String value2) {
            addCriterion("PAY_RSP between", value1, value2, "payRsp");
            return (Criteria) this;
        }

        public Criteria andPayRspNotBetween(String value1, String value2) {
            addCriterion("PAY_RSP not between", value1, value2, "payRsp");
            return (Criteria) this;
        }

        public Criteria andPayRspTimeIsNull() {
            addCriterion("PAY_RSP_TIME is null");
            return (Criteria) this;
        }

        public Criteria andPayRspTimeIsNotNull() {
            addCriterion("PAY_RSP_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andPayRspTimeEqualTo(Date value) {
            addCriterion("PAY_RSP_TIME =", value, "payRspTime");
            return (Criteria) this;
        }

        public Criteria andPayRspTimeNotEqualTo(Date value) {
            addCriterion("PAY_RSP_TIME <>", value, "payRspTime");
            return (Criteria) this;
        }

        public Criteria andPayRspTimeGreaterThan(Date value) {
            addCriterion("PAY_RSP_TIME >", value, "payRspTime");
            return (Criteria) this;
        }

        public Criteria andPayRspTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("PAY_RSP_TIME >=", value, "payRspTime");
            return (Criteria) this;
        }

        public Criteria andPayRspTimeLessThan(Date value) {
            addCriterion("PAY_RSP_TIME <", value, "payRspTime");
            return (Criteria) this;
        }

        public Criteria andPayRspTimeLessThanOrEqualTo(Date value) {
            addCriterion("PAY_RSP_TIME <=", value, "payRspTime");
            return (Criteria) this;
        }

        public Criteria andPayRspTimeIn(List<Date> values) {
            addCriterion("PAY_RSP_TIME in", values, "payRspTime");
            return (Criteria) this;
        }

        public Criteria andPayRspTimeNotIn(List<Date> values) {
            addCriterion("PAY_RSP_TIME not in", values, "payRspTime");
            return (Criteria) this;
        }

        public Criteria andPayRspTimeBetween(Date value1, Date value2) {
            addCriterion("PAY_RSP_TIME between", value1, value2, "payRspTime");
            return (Criteria) this;
        }

        public Criteria andPayRspTimeNotBetween(Date value1, Date value2) {
            addCriterion("PAY_RSP_TIME not between", value1, value2, "payRspTime");
            return (Criteria) this;
        }

        public Criteria andPayRspCodeIsNull() {
            addCriterion("PAY_RSP_CODE is null");
            return (Criteria) this;
        }

        public Criteria andPayRspCodeIsNotNull() {
            addCriterion("PAY_RSP_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andPayRspCodeEqualTo(String value) {
            addCriterion("PAY_RSP_CODE =", value, "payRspCode");
            return (Criteria) this;
        }

        public Criteria andPayRspCodeNotEqualTo(String value) {
            addCriterion("PAY_RSP_CODE <>", value, "payRspCode");
            return (Criteria) this;
        }

        public Criteria andPayRspCodeGreaterThan(String value) {
            addCriterion("PAY_RSP_CODE >", value, "payRspCode");
            return (Criteria) this;
        }

        public Criteria andPayRspCodeGreaterThanOrEqualTo(String value) {
            addCriterion("PAY_RSP_CODE >=", value, "payRspCode");
            return (Criteria) this;
        }

        public Criteria andPayRspCodeLessThan(String value) {
            addCriterion("PAY_RSP_CODE <", value, "payRspCode");
            return (Criteria) this;
        }

        public Criteria andPayRspCodeLessThanOrEqualTo(String value) {
            addCriterion("PAY_RSP_CODE <=", value, "payRspCode");
            return (Criteria) this;
        }

        public Criteria andPayRspCodeLike(String value) {
            addCriterion("PAY_RSP_CODE like", value, "payRspCode");
            return (Criteria) this;
        }

        public Criteria andPayRspCodeNotLike(String value) {
            addCriterion("PAY_RSP_CODE not like", value, "payRspCode");
            return (Criteria) this;
        }

        public Criteria andPayRspCodeIn(List<String> values) {
            addCriterion("PAY_RSP_CODE in", values, "payRspCode");
            return (Criteria) this;
        }

        public Criteria andPayRspCodeNotIn(List<String> values) {
            addCriterion("PAY_RSP_CODE not in", values, "payRspCode");
            return (Criteria) this;
        }

        public Criteria andPayRspCodeBetween(String value1, String value2) {
            addCriterion("PAY_RSP_CODE between", value1, value2, "payRspCode");
            return (Criteria) this;
        }

        public Criteria andPayRspCodeNotBetween(String value1, String value2) {
            addCriterion("PAY_RSP_CODE not between", value1, value2, "payRspCode");
            return (Criteria) this;
        }

        public Criteria andPayRspMsgIsNull() {
            addCriterion("PAY_RSP_MSG is null");
            return (Criteria) this;
        }

        public Criteria andPayRspMsgIsNotNull() {
            addCriterion("PAY_RSP_MSG is not null");
            return (Criteria) this;
        }

        public Criteria andPayRspMsgEqualTo(String value) {
            addCriterion("PAY_RSP_MSG =", value, "payRspMsg");
            return (Criteria) this;
        }

        public Criteria andPayRspMsgNotEqualTo(String value) {
            addCriterion("PAY_RSP_MSG <>", value, "payRspMsg");
            return (Criteria) this;
        }

        public Criteria andPayRspMsgGreaterThan(String value) {
            addCriterion("PAY_RSP_MSG >", value, "payRspMsg");
            return (Criteria) this;
        }

        public Criteria andPayRspMsgGreaterThanOrEqualTo(String value) {
            addCriterion("PAY_RSP_MSG >=", value, "payRspMsg");
            return (Criteria) this;
        }

        public Criteria andPayRspMsgLessThan(String value) {
            addCriterion("PAY_RSP_MSG <", value, "payRspMsg");
            return (Criteria) this;
        }

        public Criteria andPayRspMsgLessThanOrEqualTo(String value) {
            addCriterion("PAY_RSP_MSG <=", value, "payRspMsg");
            return (Criteria) this;
        }

        public Criteria andPayRspMsgLike(String value) {
            addCriterion("PAY_RSP_MSG like", value, "payRspMsg");
            return (Criteria) this;
        }

        public Criteria andPayRspMsgNotLike(String value) {
            addCriterion("PAY_RSP_MSG not like", value, "payRspMsg");
            return (Criteria) this;
        }

        public Criteria andPayRspMsgIn(List<String> values) {
            addCriterion("PAY_RSP_MSG in", values, "payRspMsg");
            return (Criteria) this;
        }

        public Criteria andPayRspMsgNotIn(List<String> values) {
            addCriterion("PAY_RSP_MSG not in", values, "payRspMsg");
            return (Criteria) this;
        }

        public Criteria andPayRspMsgBetween(String value1, String value2) {
            addCriterion("PAY_RSP_MSG between", value1, value2, "payRspMsg");
            return (Criteria) this;
        }

        public Criteria andPayRspMsgNotBetween(String value1, String value2) {
            addCriterion("PAY_RSP_MSG not between", value1, value2, "payRspMsg");
            return (Criteria) this;
        }

        public Criteria andExt01IsNull() {
            addCriterion("EXT01 is null");
            return (Criteria) this;
        }

        public Criteria andExt01IsNotNull() {
            addCriterion("EXT01 is not null");
            return (Criteria) this;
        }

        public Criteria andExt01EqualTo(String value) {
            addCriterion("EXT01 =", value, "ext01");
            return (Criteria) this;
        }

        public Criteria andExt01NotEqualTo(String value) {
            addCriterion("EXT01 <>", value, "ext01");
            return (Criteria) this;
        }

        public Criteria andExt01GreaterThan(String value) {
            addCriterion("EXT01 >", value, "ext01");
            return (Criteria) this;
        }

        public Criteria andExt01GreaterThanOrEqualTo(String value) {
            addCriterion("EXT01 >=", value, "ext01");
            return (Criteria) this;
        }

        public Criteria andExt01LessThan(String value) {
            addCriterion("EXT01 <", value, "ext01");
            return (Criteria) this;
        }

        public Criteria andExt01LessThanOrEqualTo(String value) {
            addCriterion("EXT01 <=", value, "ext01");
            return (Criteria) this;
        }

        public Criteria andExt01Like(String value) {
            addCriterion("EXT01 like", value, "ext01");
            return (Criteria) this;
        }

        public Criteria andExt01NotLike(String value) {
            addCriterion("EXT01 not like", value, "ext01");
            return (Criteria) this;
        }

        public Criteria andExt01In(List<String> values) {
            addCriterion("EXT01 in", values, "ext01");
            return (Criteria) this;
        }

        public Criteria andExt01NotIn(List<String> values) {
            addCriterion("EXT01 not in", values, "ext01");
            return (Criteria) this;
        }

        public Criteria andExt01Between(String value1, String value2) {
            addCriterion("EXT01 between", value1, value2, "ext01");
            return (Criteria) this;
        }

        public Criteria andExt01NotBetween(String value1, String value2) {
            addCriterion("EXT01 not between", value1, value2, "ext01");
            return (Criteria) this;
        }

        public Criteria andExt02IsNull() {
            addCriterion("EXT02 is null");
            return (Criteria) this;
        }

        public Criteria andExt02IsNotNull() {
            addCriterion("EXT02 is not null");
            return (Criteria) this;
        }

        public Criteria andExt02EqualTo(String value) {
            addCriterion("EXT02 =", value, "ext02");
            return (Criteria) this;
        }

        public Criteria andExt02NotEqualTo(String value) {
            addCriterion("EXT02 <>", value, "ext02");
            return (Criteria) this;
        }

        public Criteria andExt02GreaterThan(String value) {
            addCriterion("EXT02 >", value, "ext02");
            return (Criteria) this;
        }

        public Criteria andExt02GreaterThanOrEqualTo(String value) {
            addCriterion("EXT02 >=", value, "ext02");
            return (Criteria) this;
        }

        public Criteria andExt02LessThan(String value) {
            addCriterion("EXT02 <", value, "ext02");
            return (Criteria) this;
        }

        public Criteria andExt02LessThanOrEqualTo(String value) {
            addCriterion("EXT02 <=", value, "ext02");
            return (Criteria) this;
        }

        public Criteria andExt02Like(String value) {
            addCriterion("EXT02 like", value, "ext02");
            return (Criteria) this;
        }

        public Criteria andExt02NotLike(String value) {
            addCriterion("EXT02 not like", value, "ext02");
            return (Criteria) this;
        }

        public Criteria andExt02In(List<String> values) {
            addCriterion("EXT02 in", values, "ext02");
            return (Criteria) this;
        }

        public Criteria andExt02NotIn(List<String> values) {
            addCriterion("EXT02 not in", values, "ext02");
            return (Criteria) this;
        }

        public Criteria andExt02Between(String value1, String value2) {
            addCriterion("EXT02 between", value1, value2, "ext02");
            return (Criteria) this;
        }

        public Criteria andExt02NotBetween(String value1, String value2) {
            addCriterion("EXT02 not between", value1, value2, "ext02");
            return (Criteria) this;
        }

        public Criteria andExt03IsNull() {
            addCriterion("EXT03 is null");
            return (Criteria) this;
        }

        public Criteria andExt03IsNotNull() {
            addCriterion("EXT03 is not null");
            return (Criteria) this;
        }

        public Criteria andExt03EqualTo(String value) {
            addCriterion("EXT03 =", value, "ext03");
            return (Criteria) this;
        }

        public Criteria andExt03NotEqualTo(String value) {
            addCriterion("EXT03 <>", value, "ext03");
            return (Criteria) this;
        }

        public Criteria andExt03GreaterThan(String value) {
            addCriterion("EXT03 >", value, "ext03");
            return (Criteria) this;
        }

        public Criteria andExt03GreaterThanOrEqualTo(String value) {
            addCriterion("EXT03 >=", value, "ext03");
            return (Criteria) this;
        }

        public Criteria andExt03LessThan(String value) {
            addCriterion("EXT03 <", value, "ext03");
            return (Criteria) this;
        }

        public Criteria andExt03LessThanOrEqualTo(String value) {
            addCriterion("EXT03 <=", value, "ext03");
            return (Criteria) this;
        }

        public Criteria andExt03Like(String value) {
            addCriterion("EXT03 like", value, "ext03");
            return (Criteria) this;
        }

        public Criteria andExt03NotLike(String value) {
            addCriterion("EXT03 not like", value, "ext03");
            return (Criteria) this;
        }

        public Criteria andExt03In(List<String> values) {
            addCriterion("EXT03 in", values, "ext03");
            return (Criteria) this;
        }

        public Criteria andExt03NotIn(List<String> values) {
            addCriterion("EXT03 not in", values, "ext03");
            return (Criteria) this;
        }

        public Criteria andExt03Between(String value1, String value2) {
            addCriterion("EXT03 between", value1, value2, "ext03");
            return (Criteria) this;
        }

        public Criteria andExt03NotBetween(String value1, String value2) {
            addCriterion("EXT03 not between", value1, value2, "ext03");
            return (Criteria) this;
        }
    }

    /**
     * This class corresponds to the database table tb_order_payment
    @mbggenerated do_not_delete_during_merge 2016-12-01 14:03:40
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * [STRATO MyBatis Generator]
     * Table: tb_order_payment
    @mbggenerated 2016-12-01 14:03:40
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