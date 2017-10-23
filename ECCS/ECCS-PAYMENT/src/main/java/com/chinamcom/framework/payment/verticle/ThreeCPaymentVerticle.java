package com.chinamcom.framework.payment.verticle;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinamcom.framework.common.constant.ConsumerConstant;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.payment.common.UnionPayConstant;
import com.chinamcom.framework.payment.sdk.union.AcpService;
import com.chinamcom.framework.payment.service.IThreeCPayService;

import io.vertx.core.json.JsonObject;

/**
 * 支付服务
 * 
 * @author xuxg
 * @since 20160901
 *
 */
@Component
public class ThreeCPaymentVerticle extends BaseVerticle {

	@Autowired
	private IThreeCPayService threeCPayService;

	@Override
	public void start() throws Exception {
		
		vertx.eventBus().consumer(ConsumerConstant.ZM3C_PAYMENT_UNIONPAY, message -> {
			LOG.info("received:" + message.body());
			JsonObject reqData = (JsonObject) message.body();
			String sn = reqData.getString("sn");
			String reply = respWriter.toError(sn);
			try {
				Optional<String> txnAmt = Optional.ofNullable(reqData.getString("txnAmt"));// 交易金额
				Optional<String> orderId = Optional.ofNullable(reqData.getString("orderId"));// 订单号
				if (orderId.isPresent() && txnAmt.isPresent()) {
					Map<String, String> requestData = new HashMap<String, String>();
					// 争取相关参数
					requestData.put("version", UnionPayConstant.version);
					requestData.put("encoding", UnionPayConstant.encoding_UTF8);
					requestData.put("signMethod", "01");// 签名方法，只支持01：RSA方式证书加密
					requestData.put("txnType", "01");// 交易类型 01：消费
					requestData.put("txnSubType", "01");// 交易子类型 01：自助消费
					requestData.put("bizType", "000201");// 业务类型 B2C网关支付
					requestData.put("channelType", "07");// 渠道类型 07：PC 08：手机

					// 商户相关参数
					requestData.put("merId", UnionPayConstant.merId);
					requestData.put("accessType", "0");
					requestData.put("orderId", orderId.get());
					requestData.put("txnTime", UnionPayConstant.getCurrentTime());// 订单发送时间
					requestData.put("currencyCode", "156");// 交易币种 人民币
					requestData.put("txnAmt", txnAmt.get());// 交易金额，单位：分

					requestData.put("frontUrl", UnionPayConstant.frontUrl);// 前台通知地址
					requestData.put("backUrl", UnionPayConstant.backUrl);// 后台通知地址

					// 对请求参数进行签名并生成HTML表单,将表单写入浏览器跳转打开银联页面
					Map<String, String> submitFormData = AcpService.sign(requestData, UnionPayConstant.encoding_UTF8);
					String html = AcpService.createAutoFormHtml("https://101.231.204.80:5000/gateway/api/frontTransReq.do", submitFormData,UnionPayConstant.encoding_UTF8);
					LOG.info(html);
					reply = html;
				} else {
					reply = respWriter.toError(sn, "请求参数不符合规范.");
				}
			} catch (Exception e) {
				LOG.error(e);
				reply = respWriter.toError(sn, "unionpay faild.");
			}
			message.reply(reply);
		});
	}
}
