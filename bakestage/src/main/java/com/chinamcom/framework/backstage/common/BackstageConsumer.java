package com.chinamcom.framework.backstage.common;

public abstract class BackstageConsumer {
	// ==================================售后相关=============================================================
	/**
	 * 售后列表服务地址
	 */
	public static final String LIST_AFTERSALE_BACKSTAGE = "list.aftersale.backstage";

	/**
	 * 售后详情服务地址
	 */
	public static final String INFO_AFTERSALE_BACKSTAGE = "info.aftersale.backstage";

	/**
	 * 客服受理服务地址
	 */
	public static final String HANDLE_AFTERSALE_BACKSTAGE = "handle.aftersale.backstage";

	// ==================================产品相关=============================================================
	/**
	 * 产品设备列表服务地址
	 */
	public static final String LIST_PRODUCTDEVICE_BACKSTAGE = "list.productdevice.backstage";
	/**
	 * 产品设备新增服务地址
	 */
	public static final String ADD_PRODUCTDEVICE_BACKSTAGE = "add.productdevice.backstage";
	/**
	 * 产品设备删除服务地址
	 */
	public static final String DEL_PRODUCTDEVICE_BACKSTAGE = "del.productdevice.backstage";

	// ===================================监控相关============================================================
	/**
	 * 基于EventBus进行实时日志监控
	 */
	public static final String LOG_EB_MONITOR_BACKSTAGE = "log.eb.monitor.backstage";
	/**
	 * 服务监控
	 */
	public static final String SERVICE_MONITOR_BACKSTAGE = "services.monitor.backstage";
	// ===================================用户管理============================================================
	/**
	 * 用户登陆
	 */
	public static final String LOGIN_BACKSTAGE = "login.backstage";
	/**
	 * 新增用户
	 */
	public static final String ADD_USER_BACKSTAGE = "add.user.backstage";
	/**
	 * 删除用户
	 */
	public static final String DEL_USER_BACKSTAGE = "del.user.backstage";
	/**
	 * 更新用户
	 */
	public static final String UPDATE_USER_BACKSTAGE = "update.user.backstage";
	/**
	 * 查询用户
	 */
	public static final String LIST_USER_BACKSTAGE = "list.user.backstage";
}
