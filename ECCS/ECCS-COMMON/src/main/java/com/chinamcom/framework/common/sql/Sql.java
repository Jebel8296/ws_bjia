package com.chinamcom.framework.common.sql;

public class Sql {
	
	/**根据商品编码查*/
	public static final String PRODUCT_SELECT_BYCODE = "SELECT "+
													   		"a.ID procid, "+
													   		"a.CATEGORY_ID category, "+
													   		"a.CODE code, "+
													   		"a.NAME name, "+
													   		"a.MODEL model, "+
													   		"a.COLOUR color, "+
													   		"a.PRICE price, "+
													   		"a.ACTIVITY_PRICE actprice, "+
													   		"a.STOCK procstock, "+
													   		"a.SALES procsales, "+
													   		"a.CREATE_TIME createtime, "+
													   		"a.DISPLAY_NAME disname, "+
													   		"a.DESCRIPTION des "+ 
													   	"FROM "+
													   		"tb_product a "+ 
													   	"WHERE a.STATUS=1 AND a.CODE=?";

	/**根据商品类别查*/
	public static final String PRODUCT_SELECT_BYCATEGORY = "SELECT "+
	   															"a.ID procid, "+
	   															"a.CATEGORY_ID category, "+
	   															"a.CODE code, "+
	   															"a.NAME name, "+
	   															"a.MODEL model, "+
	   															"a.COLOUR color, "+
	   															"a.PRICE price, "+
	   															"a.ACTIVITY_PRICE actprice, "+
	   															"a.STOCK procstock, "+
	   															"a.SALES procsales, "+
	   															"a.CREATE_TIME createtime, "+
	   															"a.DISPLAY_NAME disname, "+
	   															"a.DESCRIPTION des "+ 
													   	"FROM "+
													   		"tb_product a "+ 
													   	"WHERE a.STATUS=1 AND a.CATEGORY_ID=?";

	/**查All*/
	public static final String PRODUCT_SELECT_ALL = "SELECT "+
	   														"a.ID procid, "+
	   														"a.CATEGORY_ID category, "+
	   														"a.CODE code, "+
	   														"a.NAME name, "+
	   														"a.MODEL model, "+
	   														"a.COLOUR color, "+
	   														"a.PRICE price, "+
	   														"a.ACTIVITY_PRICE actprice, "+
	   														"a.STOCK procstock, "+
	   														"a.SALES procsales, "+
	   														"a.CREATE_TIME createtime, "+
	   														"a.DISPLAY_NAME disname, "+
	   														"a.DESCRIPTION des "+ 
													   	"FROM "+
													   		"tb_product a "+ 
													   	"WHERE a.STATUS=1";
	
	/** 生成订单 */
	public static final String ORDER_INSERT_BUILD="INSERT INTO tb_order(SERIAL_NUMBER,ORDER_TYPE,FEE,PAY_FEE,REDUCE_FEE,CHANNEL,STATUS,USER_ID,CREATE_TIME) VALUES(?,?,?,?,?,?,?,?,NOW())";
	/** 后成订单明细 */
	public static final String ORDER_INSTANCE_INSERT_BUILD="INSERT INTO tb_order_instance(PRODUCT_CODE,PRODUCT_NAME,PRODUCT_PRICE,PRODUCT_TOTAL,PAY_FEE,REDUCE_FEE,USER_ID,STATUS,CREATE_TIME,ORDER_ID) VALUES(?,?,?,?,?,?,?,?,NOW(),?)";
	/**生成物流信息*/
	public static final String ORDER_LOGISTICS_INSERT_BUILD = "INSERT INTO tb_order_logistics(ORDER_ID,NAME,PHONE,ZIPCODE,PROVINCE,CITY,AREA,ADDRESS,DISPATCH_TIME,INVOCE,PRICE,LOGISTICS_COMPANY_NAME,CREATE_TIME) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,NOW())";
	/** 用户订单列表 */
	public static final String ORDER_SELECT_BYUID = "SELECT id oid,serial_number ocode,fee ofee,pay_fee payfee,reduce_fee reducefee,channel,STATUS,user_id uid,create_time ctime,pay_time ptime,send_time stime,compate_time ctime FROM tb_order WHERE user_id = ? ";
	/** 订单详情 */
	public static final String ORDER_INSTANCE_SELECT_BYORDERID = "SELECT PRODUCT_CODE code,PRODUCT_NAME name,PRODUCT_PRICE price,PRODUCT_TOTAL total,PAY_FEE payfee,REDUCE_FEE reducefee,CREATE_TIME ctime,USER_ID uid,STATUS status  FROM tb_order_instance WHERE ORDER_ID=?";
	/** 物流信息 */
	public static final String ORDER_LOGISTICS_SELECT_BYORDERID = "SELECT NAME name,PHONE phone,ZIPCODE zipcode,PROVINCE province,CITY city,AREA area,ADDRESS address,DISPATCH_TIME sendtime,INVOCE invoce,LOGISTICS_SERIAL logisticscode,PRICE price,LOGISTICS_COMPANY_NAME company FROM tb_order_logistics WHERE ORDER_ID=?";
	/** 用户单个订单 */
	public static final String ORDER_SELECT_BYORDEID = "SELECT id oid,serial_number ocode,fee ofee,pay_fee payfee,reduce_fee reducefee,channel,STATUS,user_id uid,create_time ctime,pay_time ptime,send_time stime,compate_time ctime FROM tb_order WHERE id = ? ";
	
}
