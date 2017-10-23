package com.chinamcom.framework.backstage.model;

import java.util.ArrayList;
import java.util.List;

public class TbAftersaleLogisticsExample {
    /**
     * Table: tb_aftersale_logistics
    @mbggenerated 2016-11-09 13:35:22
     */
    protected String orderByClause;

    /**
     * Table: tb_aftersale_logistics
    @mbggenerated 2016-11-09 13:35:22
     */
    protected boolean distinct;

    /**
     * Table: tb_aftersale_logistics
    @mbggenerated 2016-11-09 13:35:22
     */
    protected List<Criteria> oredCriteria;

    public TbAftersaleLogisticsExample() {
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
     * Table: tb_aftersale_logistics
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

        public Criteria andExpressIdIsNull() {
            addCriterion("express_id is null");
            return (Criteria) this;
        }

        public Criteria andExpressIdIsNotNull() {
            addCriterion("express_id is not null");
            return (Criteria) this;
        }

        public Criteria andExpressIdEqualTo(Integer value) {
            addCriterion("express_id =", value, "expressId");
            return (Criteria) this;
        }

        public Criteria andExpressIdNotEqualTo(Integer value) {
            addCriterion("express_id <>", value, "expressId");
            return (Criteria) this;
        }

        public Criteria andExpressIdGreaterThan(Integer value) {
            addCriterion("express_id >", value, "expressId");
            return (Criteria) this;
        }

        public Criteria andExpressIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("express_id >=", value, "expressId");
            return (Criteria) this;
        }

        public Criteria andExpressIdLessThan(Integer value) {
            addCriterion("express_id <", value, "expressId");
            return (Criteria) this;
        }

        public Criteria andExpressIdLessThanOrEqualTo(Integer value) {
            addCriterion("express_id <=", value, "expressId");
            return (Criteria) this;
        }

        public Criteria andExpressIdIn(List<Integer> values) {
            addCriterion("express_id in", values, "expressId");
            return (Criteria) this;
        }

        public Criteria andExpressIdNotIn(List<Integer> values) {
            addCriterion("express_id not in", values, "expressId");
            return (Criteria) this;
        }

        public Criteria andExpressIdBetween(Integer value1, Integer value2) {
            addCriterion("express_id between", value1, value2, "expressId");
            return (Criteria) this;
        }

        public Criteria andExpressIdNotBetween(Integer value1, Integer value2) {
            addCriterion("express_id not between", value1, value2, "expressId");
            return (Criteria) this;
        }

        public Criteria andLogisticsCodeIsNull() {
            addCriterion("logistics_code is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsCodeIsNotNull() {
            addCriterion("logistics_code is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsCodeEqualTo(String value) {
            addCriterion("logistics_code =", value, "logisticsCode");
            return (Criteria) this;
        }

        public Criteria andLogisticsCodeNotEqualTo(String value) {
            addCriterion("logistics_code <>", value, "logisticsCode");
            return (Criteria) this;
        }

        public Criteria andLogisticsCodeGreaterThan(String value) {
            addCriterion("logistics_code >", value, "logisticsCode");
            return (Criteria) this;
        }

        public Criteria andLogisticsCodeGreaterThanOrEqualTo(String value) {
            addCriterion("logistics_code >=", value, "logisticsCode");
            return (Criteria) this;
        }

        public Criteria andLogisticsCodeLessThan(String value) {
            addCriterion("logistics_code <", value, "logisticsCode");
            return (Criteria) this;
        }

        public Criteria andLogisticsCodeLessThanOrEqualTo(String value) {
            addCriterion("logistics_code <=", value, "logisticsCode");
            return (Criteria) this;
        }

        public Criteria andLogisticsCodeLike(String value) {
            addCriterion("logistics_code like", value, "logisticsCode");
            return (Criteria) this;
        }

        public Criteria andLogisticsCodeNotLike(String value) {
            addCriterion("logistics_code not like", value, "logisticsCode");
            return (Criteria) this;
        }

        public Criteria andLogisticsCodeIn(List<String> values) {
            addCriterion("logistics_code in", values, "logisticsCode");
            return (Criteria) this;
        }

        public Criteria andLogisticsCodeNotIn(List<String> values) {
            addCriterion("logistics_code not in", values, "logisticsCode");
            return (Criteria) this;
        }

        public Criteria andLogisticsCodeBetween(String value1, String value2) {
            addCriterion("logistics_code between", value1, value2, "logisticsCode");
            return (Criteria) this;
        }

        public Criteria andLogisticsCodeNotBetween(String value1, String value2) {
            addCriterion("logistics_code not between", value1, value2, "logisticsCode");
            return (Criteria) this;
        }

        public Criteria andLogisticsStatusIsNull() {
            addCriterion("logistics_status is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsStatusIsNotNull() {
            addCriterion("logistics_status is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsStatusEqualTo(String value) {
            addCriterion("logistics_status =", value, "logisticsStatus");
            return (Criteria) this;
        }

        public Criteria andLogisticsStatusNotEqualTo(String value) {
            addCriterion("logistics_status <>", value, "logisticsStatus");
            return (Criteria) this;
        }

        public Criteria andLogisticsStatusGreaterThan(String value) {
            addCriterion("logistics_status >", value, "logisticsStatus");
            return (Criteria) this;
        }

        public Criteria andLogisticsStatusGreaterThanOrEqualTo(String value) {
            addCriterion("logistics_status >=", value, "logisticsStatus");
            return (Criteria) this;
        }

        public Criteria andLogisticsStatusLessThan(String value) {
            addCriterion("logistics_status <", value, "logisticsStatus");
            return (Criteria) this;
        }

        public Criteria andLogisticsStatusLessThanOrEqualTo(String value) {
            addCriterion("logistics_status <=", value, "logisticsStatus");
            return (Criteria) this;
        }

        public Criteria andLogisticsStatusLike(String value) {
            addCriterion("logistics_status like", value, "logisticsStatus");
            return (Criteria) this;
        }

        public Criteria andLogisticsStatusNotLike(String value) {
            addCriterion("logistics_status not like", value, "logisticsStatus");
            return (Criteria) this;
        }

        public Criteria andLogisticsStatusIn(List<String> values) {
            addCriterion("logistics_status in", values, "logisticsStatus");
            return (Criteria) this;
        }

        public Criteria andLogisticsStatusNotIn(List<String> values) {
            addCriterion("logistics_status not in", values, "logisticsStatus");
            return (Criteria) this;
        }

        public Criteria andLogisticsStatusBetween(String value1, String value2) {
            addCriterion("logistics_status between", value1, value2, "logisticsStatus");
            return (Criteria) this;
        }

        public Criteria andLogisticsStatusNotBetween(String value1, String value2) {
            addCriterion("logistics_status not between", value1, value2, "logisticsStatus");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andZipcodeIsNull() {
            addCriterion("zipcode is null");
            return (Criteria) this;
        }

        public Criteria andZipcodeIsNotNull() {
            addCriterion("zipcode is not null");
            return (Criteria) this;
        }

        public Criteria andZipcodeEqualTo(String value) {
            addCriterion("zipcode =", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeNotEqualTo(String value) {
            addCriterion("zipcode <>", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeGreaterThan(String value) {
            addCriterion("zipcode >", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeGreaterThanOrEqualTo(String value) {
            addCriterion("zipcode >=", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeLessThan(String value) {
            addCriterion("zipcode <", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeLessThanOrEqualTo(String value) {
            addCriterion("zipcode <=", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeLike(String value) {
            addCriterion("zipcode like", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeNotLike(String value) {
            addCriterion("zipcode not like", value, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeIn(List<String> values) {
            addCriterion("zipcode in", values, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeNotIn(List<String> values) {
            addCriterion("zipcode not in", values, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeBetween(String value1, String value2) {
            addCriterion("zipcode between", value1, value2, "zipcode");
            return (Criteria) this;
        }

        public Criteria andZipcodeNotBetween(String value1, String value2) {
            addCriterion("zipcode not between", value1, value2, "zipcode");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNull() {
            addCriterion("province is null");
            return (Criteria) this;
        }

        public Criteria andProvinceIsNotNull() {
            addCriterion("province is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceEqualTo(String value) {
            addCriterion("province =", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotEqualTo(String value) {
            addCriterion("province <>", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThan(String value) {
            addCriterion("province >", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceGreaterThanOrEqualTo(String value) {
            addCriterion("province >=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThan(String value) {
            addCriterion("province <", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLessThanOrEqualTo(String value) {
            addCriterion("province <=", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceLike(String value) {
            addCriterion("province like", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotLike(String value) {
            addCriterion("province not like", value, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceIn(List<String> values) {
            addCriterion("province in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotIn(List<String> values) {
            addCriterion("province not in", values, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceBetween(String value1, String value2) {
            addCriterion("province between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andProvinceNotBetween(String value1, String value2) {
            addCriterion("province not between", value1, value2, "province");
            return (Criteria) this;
        }

        public Criteria andCityIsNull() {
            addCriterion("city is null");
            return (Criteria) this;
        }

        public Criteria andCityIsNotNull() {
            addCriterion("city is not null");
            return (Criteria) this;
        }

        public Criteria andCityEqualTo(String value) {
            addCriterion("city =", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotEqualTo(String value) {
            addCriterion("city <>", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThan(String value) {
            addCriterion("city >", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThanOrEqualTo(String value) {
            addCriterion("city >=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThan(String value) {
            addCriterion("city <", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThanOrEqualTo(String value) {
            addCriterion("city <=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLike(String value) {
            addCriterion("city like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotLike(String value) {
            addCriterion("city not like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityIn(List<String> values) {
            addCriterion("city in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotIn(List<String> values) {
            addCriterion("city not in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityBetween(String value1, String value2) {
            addCriterion("city between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotBetween(String value1, String value2) {
            addCriterion("city not between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andAreaIsNull() {
            addCriterion("area is null");
            return (Criteria) this;
        }

        public Criteria andAreaIsNotNull() {
            addCriterion("area is not null");
            return (Criteria) this;
        }

        public Criteria andAreaEqualTo(String value) {
            addCriterion("area =", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotEqualTo(String value) {
            addCriterion("area <>", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThan(String value) {
            addCriterion("area >", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThanOrEqualTo(String value) {
            addCriterion("area >=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThan(String value) {
            addCriterion("area <", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThanOrEqualTo(String value) {
            addCriterion("area <=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLike(String value) {
            addCriterion("area like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotLike(String value) {
            addCriterion("area not like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaIn(List<String> values) {
            addCriterion("area in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotIn(List<String> values) {
            addCriterion("area not in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaBetween(String value1, String value2) {
            addCriterion("area between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotBetween(String value1, String value2) {
            addCriterion("area not between", value1, value2, "area");
            return (Criteria) this;
        }
    }

    /**
     * This class corresponds to the database table tb_aftersale_logistics
    @mbggenerated do_not_delete_during_merge 2016-11-09 13:35:22
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * [STRATO MyBatis Generator]
     * Table: tb_aftersale_logistics
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