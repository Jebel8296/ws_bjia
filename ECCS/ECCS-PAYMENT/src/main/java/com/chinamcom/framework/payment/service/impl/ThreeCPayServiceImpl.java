package com.chinamcom.framework.payment.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.common.service.BaseService;
import com.chinamcom.framework.payment.mapper.TbOrderPaymentMapper;
import com.chinamcom.framework.payment.model.TbOrderPayment;
import com.chinamcom.framework.payment.model.TbOrderPaymentExample;
import com.chinamcom.framework.payment.model.TbOrderPaymentQueryModel;
import com.chinamcom.framework.payment.service.IThreeCPayService;

import io.vertx.core.json.JsonObject;

@Service("threeCPayService")
public class ThreeCPayServiceImpl extends BaseService implements IThreeCPayService {

	@Autowired
	protected TbOrderPaymentMapper mapper;

	@Override
	public String insertTbOrderPayment(String sn, JsonObject param) {
		String message = respWriter.toError(sn);
		try {
			Date date = new Date();
			Optional<String> ordercode = Optional.ofNullable(param.getString("ordercode"));// 订单号
			Optional<Integer> orderid = Optional.ofNullable(param.getInteger("orderid"));// 订单号
			//判断是否已存在订单的支付信息
			TbOrderPaymentExample example = new TbOrderPaymentExample();
			TbOrderPaymentExample.Criteria criteria = example.createCriteria();
			if(ordercode.isPresent()){
				criteria.andOrderCodeEqualTo(ordercode.get());
			}
			if(orderid.isPresent()){
				criteria.andOrderIdEqualTo(orderid.get());
			}
			List<TbOrderPayment> tops = mapper.selectByExample(example);
			if (tops != null && tops.size() > 0) {
				TbOrderPayment top = tops.get(0);
				if (top.getStatus() == 1) {
					TbOrderPayment update = new TbOrderPayment();
					update.setId(top.getId());
					update.setFee(new BigDecimal(param.getString("payfee")));
					update.setPayType(1);
					update.setStatus(1);
					update.setCreateTime(date);
					update.setPayReq(param.getString("PAY_REQ"));
					update.setPayReqTime(date);
					mapper.updateByPrimaryKeySelective(update);
				}
			}else{
				TbOrderPayment payment = new TbOrderPayment();
				if(ordercode.isPresent()){
					payment.setOrderCode(ordercode.get());
				}
				if(orderid.isPresent()){
					criteria.andOrderIdEqualTo(orderid.get());
					payment.setOrderId(orderid.get());
				}
				payment.setFee(new BigDecimal(param.getString("payfee")));
				payment.setPayType(1);
				payment.setStatus(1);
				payment.setCreateTime(date);
				payment.setPayReq(param.getString("PAY_REQ"));
				payment.setPayReqTime(date);
				mapper.insert(payment);
			}
			message = respWriter.toSuccess(sn);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	@Override
	public String updateTbOrderPayment(String sn, JsonObject param) {
		String message = respWriter.toError(sn);
		try {
			Date date = new Date();
			Optional<String> ordercode = Optional.ofNullable(param.getString("ordercode"));// 订单号
			Optional<Integer> orderid = Optional.ofNullable(param.getInteger("orderid"));// 订单号
			TbOrderPayment payment = new TbOrderPayment();
			if(param.containsKey("paychannel")){
				payment.setPayChannel(param.getString("paychannel"));
			}
			if(param.containsKey("ext01")){
				payment.setExt01(param.getString("ext01"));
			}
			if(param.containsKey("ext02")){
				payment.setExt02(param.getString("ext02"));
			}
			if(param.containsKey("ext03")){
				payment.setExt03(param.getString("ext03"));
			}
			if(param.containsKey("pay_rsp")){
				payment.setPayRsp(param.getString("pay_rsp"));
			}
			if(param.containsKey("pay_msg")){
				payment.setPayRspMsg(param.getString("pay_msg"));
			}
			payment.setStatus(param.getInteger("status"));
			payment.setUpdateTime(date);
			payment.setPayRspTime(date);
			TbOrderPaymentExample example = new TbOrderPaymentExample();
			TbOrderPaymentExample.Criteria criteria = example.createCriteria();
			if(ordercode.isPresent()){
				criteria.andOrderCodeEqualTo(ordercode.get());
			}
			if(orderid.isPresent()){
				criteria.andOrderIdEqualTo(orderid.get());
			}
			mapper.updateByExampleSelective(payment, example);
			message = respWriter.toSuccess(sn);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	@Override
	public List<TbOrderPayment> selectByTbOrderPayment(TbOrderPaymentQueryModel payment) {
		return null;
	}

}
