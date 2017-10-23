package com.chinamcom.framework.common.constant;

public class Constant {

	/** 待支付 */
	public static final Integer ORDER_STATUS_NOPAY = 1;
	/** 已支付，待发货 */
	public static final Integer ORDER_STATUS_NOSEND = 2;
	/** 已发货 */
	public static final Integer ORDER_STATUS_SENDED = 3;
	/** 交易完成 */
	public static final Integer ORDER_STATUS_COMPLATE = 4;
	/** 取消 包括超时自动取消和用户取消 */
	public static final Integer ORDER_STATUS_CANCEL = 5;
	/** 已拒收 */
	public static final Integer ORDER_STATUS_REFUSE = 6;
	public static final Integer ORDER_STATUS_CLOSE = 7;

	/** 在购物车里，未生成订单 */
	public static final Integer INSTANCE_STATUS_NOORDER = 1;
	/** 在购物车里,被删除 */
	public static final Integer INSTANCE_STATUS_DEL = 2;
	/** 已生成订单 */
	public static final Integer INSTANCE_STATUS_YESORDER = 3;
	/** 生成订单,被取消 */
	public static final Integer INSTANCE_STATUS_CANCELORDER = 4;

	/** 待支付 */
	public static final Integer PAYMENT_STATUS_NOPAY = 1;
	/** 已支付 */
	public static final Integer PAYMENT_STATUS_PAYED = 2;
	/** 支付失败 */
	public static final Integer PAYMENT_STATUS_PAYFAIL = 3;
	/** 订单取消，支付状态也取消 */
	public static final Integer PAYMENT_STATUS_CANCEL = 4;

	public static final Integer PRODUCTTYPE_SMART = 34;// 智能设备
	public static final Integer PRODUCTTYPE_WATCH = 41;// 手表
	public static final Integer PRODUCTTYPE_HEADSET = 40;// 耳机

}
