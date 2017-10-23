package com.chinamcom.framework.backstage.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.util.StringUtils;

public class StringUtil extends StringUtils {

	private static final String DEFAULT_PATTERN = "yyyyMMddHHmmssSSS";
	private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/** 商品编码截取商品型号 */
	public static String subModelFromCode(String code) {
		String model = "A100";
		if (isNotEmpty(code)) {// 3C40A100010001
			model = code.substring(4, 8);
		}
		return model;
	}

	/** 商品编码截取商品类别 */
	public static String subCategoryFromCode(String code) {
		String category = "40";
		if (isNotEmpty(code)) {// 3C40A100010001
			category = code.substring(2, 4);
		}
		return category;
	}

	/** 商品编码截取颜色码 */
	public static String subColorFromCode(String code) {
		String category = "40";
		if (isNotEmpty(code)) {
			category = code.substring(8, 10);
		}
		return category;
	}

	/**
	 * 根据color取名称
	 * 
	 * @param color
	 * @return
	 */
	public static String getColorName(String color) {
		String name = "白色";
		switch (color) {
		case "01":
			name = "全黑";
			break;
		case "02":
			name = "白蓝";
			break;
		default:
			break;
		}
		return name;
	}

	/** 由商品编码、商品型号、颜色码组装商品编码 */
	public static String buildProductCode(String category, String model, String color) {
		return "3C" + category + model + color;
	}

	public static String getAfterSaleCode() {
		String random = generateRandomCode(6);
		return random;
	}

	public static String getOrderCode() {
		String random = generateRandomCode(8);
		return random;
	}

	public static String getSn() {
		String date = new SimpleDateFormat(DEFAULT_PATTERN).format(new Date());
		String random = generateRandomCode(6);// 生成6位随机数
		String sn = date + random;
		return sn;
	}

	public static Date parseString2Date(String date) {
		Date d = new Date();
		try {
			d = new SimpleDateFormat(DATE_PATTERN).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	public static String parseDate2String(Date date) {
		return new SimpleDateFormat(DATE_PATTERN).format(date);
	}

	/**
	 * 生成6位随机数
	 */
	private static String generateRandomCode(int digit) {
		String code = String.valueOf(new Random().nextInt(10));
		switch (digit) {
		case 2:
			code = String.valueOf(new Random().nextInt(89) + 10);
			break;
		case 4:
			code = String.valueOf(new Random().nextInt(8999) + 1000);
			break;
		case 6:
			code = String.valueOf(new Random().nextInt(899999) + 100000);
			break;
		case 8:
			code = String.valueOf(new Random().nextInt(89999999) + 10000000);
			break;
		default:
			break;
		}
		return code;
	}

	/**
	 * 4位短信验证码
	 */
	public static String getSmsAuthCode() {
		String random = generateRandomCode(4);
		return random;
	}

	/**
	 * 短信内容
	 */
	public static String getSmsContent(String authcode) {
		StringBuilder content = new StringBuilder("您正在使用中麦必加官网，随机短信验证码为");
		content.append(authcode);
		content.append("。中麦必加不会以任何方式向您索取该密码，请勿告知他人！");
		return content.toString();
	}

	/**
	 * 短信序列码
	 */
	public static String getSmsSn(String phone) {
		return String.valueOf(new Random().nextInt(8999) + 1000) + phone + String.valueOf(new Random().nextInt(10));
	}

	public static void main(String[] args) throws Exception {
	}

	// 不同的物流公司取不同的费用
	public static BigDecimal getExpressPay(String express) {
		BigDecimal pay = null;
		switch (express) {
		case "宅急送(免费)":
			pay = new BigDecimal("0.00");
			break;
		case "天天快递":
			pay = new BigDecimal("12.00");
			break;
		case "顺丰快递":
			pay = new BigDecimal("12.00");
			break;
		case "申通快递":
			pay = new BigDecimal("12.00");
			break;
		case "圆通快递":
			pay = new BigDecimal("12.00");
			break;
		default:
			pay = new BigDecimal("0.00");
			break;
		}
		return pay;
	}

}
