package com.chinamcom.framework.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务常量
 *
 * @author xxug
 * @since 20160831
 */
public class ConsumerConstant {

    public static final String ZM3C = "zm3c.";
    public static final String SEND_SMS = ZM3C + "send.sms";// 发送短信验证码

    // 用户服务
    public static final String USER = "user.";
    public static final String ZM3C_USER_REGISTER = ZM3C + USER + "register";// 注册服务
    public static final String ZM3C_USER_LOGIN_CN = ZM3C + USER + "login.cn";// 登陆
    public static final String ZM3C_USER_LOGIN_WX = ZM3C + USER + "login.wx";// 微信登陆
    public static final String ZM3C_USER_LOGIN_WB = ZM3C + USER + "login.wb";// 微博登陆
    public static final String ZM3C_USER_LOGIN_QQ = ZM3C + USER + "login.qq";// QQ登陆
    public static final String ZM3C_USER_UNLOGIN = ZM3C + USER + "unlogin";// 退出登陆
    // 密码服务
    public static final String PASSWORD = "password.";
    public static final String PASSWORD_FORGET = ZM3C + PASSWORD + "forget";// 忘记密码
    public static final String PASSWORD_MODIFY = ZM3C + PASSWORD + "modify";// 修改密码

    public static final String ZM3C_PRODUCT_QUERY = ZM3C + "product.query";// 产品查询
    public static final String ZM3C_PRODUCT_INIT = ZM3C + "product.init";// 产品初始化
    public static final String ZM3C_PRODUCT_ITEM = ZM3C + "product.item";// 检索产品对应的管易平台上的编号

    public static final String ZM3C_DEVICES_QUERY = ZM3C + "devices.query";// 设备产品查询

    public static final String ZM3C_STOCK_UPDATE = ZM3C + "stock.update";// 更新库存
    public static final String ZM3C_STOCK_QUERY = ZM3C + "stock.query";// 查询库存

    /**
     * 支付宝
     */
    public static final String ZM3C_PAYMENT_ALIPAY = "alipay";
    /**
     * 银联
     */
    public static final String ZM3C_PAYMENT_UNIONPAY = "unionpay";

    // 订单
    public static final String ORDER = "order.";
    public static final String ZM3C_ORDER_ADD = ZM3C + ORDER + "add";
    public static final String ZM3C_ORDER_LIST = ZM3C + ORDER + "list";
    public static final String ZM3C_ORDER_INFO = ZM3C + ORDER + "info";
    public static final String ZM3C_ORDER_UPDATE = ZM3C + ORDER + "update";
    public static final String ZM3C_ORDER_CANCEL = ZM3C + ORDER + "cancel";
    public static final String ZM3C_ORDER_CLOSE = ZM3C + ORDER + "close";
    public static final String ZM3C_ORDER_RECEIPT = ZM3C + ORDER + "receipt";
    public static final String ZM3C_ORDER_REFUSE = ZM3C + ORDER + "refuse";

    // 监测服务
    public static final String MONITOR = "monitor.";
    public static final String MONITOR_LOG = ZM3C + MONITOR + "log";// 监测业务日志
    public static final String MONITOR_SERVICE = ZM3C + MONITOR + "service";// 监测服务状态
    public static final String MONITOR_METRICS = ZM3C + MONITOR + "metrics";// 监测系统指标

    // 购物车服务
    public static final String CART = "cart.";
    public static final String CART_ADD = ZM3C + CART + "add";// 加入购物车
    public static final String CART_LIST = ZM3C + CART + "list";// 购物车列表
    public static final String CART_DEL = ZM3C + CART + "del";// 删除购物车
    public static final String CART_INCR = ZM3C + CART + "incr";// 增加数量
    public static final String CART_DECR = ZM3C + CART + "decr";// 减少数量
    public static final String CART_ACCOUNT = ZM3C + CART + "account";// 去结算
    public static final String CART_CHECK = ZM3C + CART + "check";// 选中或取消购物车中的产品

    // 收货地址维护服务
    public static final String EXPRESS = "express.";
    public static final String EXPRESS_ADD = ZM3C + EXPRESS + "add";// 新增
    public static final String EXPRESS_MOD = ZM3C + EXPRESS + "mod";// 修改
    public static final String EXPRESS_DEL = ZM3C + EXPRESS + "del";// 删除
    public static final String EXPRESS_LIST = ZM3C + EXPRESS + "list";// 列表
    public static final String EXPRESS_DEF = ZM3C + EXPRESS + "def";// 默认

    // 售后客服
    public static final String AFTERSALE = "aftersale.";
    public static final String AFTERSALE_APPLEY = ZM3C + AFTERSALE + "apply";// 提交售后服务
    public static final String AFTERSALE_ACCEPT = ZM3C + AFTERSALE + "accept";// 受理售后服务
    public static final String AFTERSALE_LIST = ZM3C + AFTERSALE + "list";// 售后服务列表
    public static final String AFTERSALE_INFO = ZM3C + AFTERSALE + "info";// 售后服务详情
    public static final String AFTERSALE_DEVICE = ZM3C + AFTERSALE + "device";// 通过设备编号提交售后服务
    public static final String AFTERSALE_DEVICECHECK = ZM3C + AFTERSALE + "devicecheck";// 通过设备编号提交售后服务--确认提交
    public static final String AFTERSALE_UPLOAD = ZM3C + AFTERSALE + "upload";//用户上传售后物流单号
    public static final String AFTERSALE_CANCEL = ZM3C + AFTERSALE + "cancel";//取消售后
    public static final String AFTERSALE_UPDATE = ZM3C + AFTERSALE + "second";//修改售后

    // 支付相关
    public static final String PAYMENT = "payment.";
    public static final String PAYMENT_JUMPPAY = ZM3C + PAYMENT + "jumppay";// 跳转到统一支付平台
    public static final String PAYMENT_ACCEPTPAY = ZM3C + PAYMENT + "acceptpay";// 接收统一支付平台的请求

    // 生成图片验证码
    public static final String GENERATE_CODE = ZM3C + "generate.code";
    // 验证图片验证码 一般用于服务间调用
    public static final String VERIFY_CODE = ZM3C + "verify.code";

    // 生成统一登陆平台的签名
    public static final String BUILD_SIGN_CODE = ZM3C + "build.sign";

    public static Map<String, String> transform = new HashMap<String, String>();

    static {
        transform.put("PRO11", ZM3C_PRODUCT_QUERY);
        transform.put("PRO12", ZM3C_DEVICES_QUERY);

        transform.put("STO11", ZM3C_STOCK_UPDATE);
        transform.put("STO12", ZM3C_STOCK_QUERY);

        transform.put("ORD11", ZM3C_ORDER_ADD);
        transform.put("ORD12", ZM3C_ORDER_LIST);
        transform.put("ORD13", ZM3C_ORDER_INFO);

        transform.put("CAR11", CART_ADD);
        transform.put("CAR12", CART_LIST);
        transform.put("CAR13", CART_DEL);
        transform.put("CAR14", CART_INCR);
        transform.put("CAR15", CART_DECR);
        transform.put("CAR16", CART_ACCOUNT);
        transform.put("CAR17", EXPRESS_DEF);

        transform.put("AFT11", AFTERSALE_APPLEY);
        transform.put("AFT12", AFTERSALE_ACCEPT);
        transform.put("AFT13", AFTERSALE_LIST);
        transform.put("AFT14", AFTERSALE_INFO);
        transform.put("AFT15", AFTERSALE_DEVICE);

        transform.put("USE11", ZM3C_USER_REGISTER);
        transform.put("USE12", ZM3C_USER_LOGIN_CN);
        transform.put("USE13", SEND_SMS);
        transform.put("USE14", PASSWORD_FORGET);
        transform.put("USE15", PASSWORD_MODIFY);

        transform.put("EXP11", EXPRESS_ADD);
        transform.put("EXP12", EXPRESS_MOD);
        transform.put("EXP13", EXPRESS_DEL);
        transform.put("EXP14", EXPRESS_LIST);
        transform.put("EXP15", EXPRESS_DEF);

        transform.put("PAY11", PAYMENT_JUMPPAY);
        transform.put("PAY12", PAYMENT_ACCEPTPAY);

        transform.put("CODE11", GENERATE_CODE);
        transform.put("CODE12", VERIFY_CODE);
    }
}
