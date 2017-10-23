package com.chinamcom.framework.task.common;

/**
 * 调度常量
 *
 * @author xuxg
 * @since 20160805
 */
public abstract class SchedulerContant {
    /**
     * 24小时未支付的订单
     */
    public static final String ORDER_GREATER_THAN_SQL_ = "SELECT ID,SERIAL_NUMBER,USER_ID,CREATE_TIME FROM tb_order WHERE STATUS=1 AND HOUR(TIMEDIFF(NOW(),CREATE_TIME))>=24 LIMIT 0,10";
    /**
     * 未支付订单改为取消状态
     */
    public static final String ORDER_BATHUPDATE_SQL = "UPDATE tb_order SET STATUS=7, UPDATE_TIME=NOW(),COMPATE_TIME=NOW(),DESCRIPTION='24小时未支付，系统自动关闭' WHERE STATUS=1 AND HOUR(TIMEDIFF(NOW(),CREATE_TIME))>=24 LIMIT 100";
    public static final String ORDER_ONEUPDATE_SQL = "UPDATE tb_order SET STATUS=7, UPDATE_TIME=NOW(),COMPATE_TIME=NOW(),DESCRIPTION='24小时未支付，系统自动关闭' WHERE ID=?";
}
