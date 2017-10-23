package com.chinamcom.framework.common.constants;

/**
 * mainmodel.childmodel.function
 * 
 * @author fattiger.xiaoyang
 * @date 2016/08/02
 */
public class ServerConstants {

	// private static final String SERVER_PREFIX = "chinamcom.watch."; //server
	// prefix

	// =================sport====================
	private static final String MOLDEL_PREFIX_SPORT = "sport.";
	public static final String SERVER_SPORT_INFO_ADD = MOLDEL_PREFIX_SPORT + "sportinfo.add"; // 添加运动数据
	public static final String SERVER_SPORT_INFO_QUERY = MOLDEL_PREFIX_SPORT + "sportinfo.query"; // 添加运动数据

	// =================sociality================
	private static final String MOLDEL_PREFIX_SOCIALITY = "sociality.";
	public static final String MOLDEL_SYNCHRONIZE_COMMUNICATION = MOLDEL_PREFIX_SOCIALITY
			+ "synchronize.communitcation"; // 同步通讯录//手表通话列表
	public static final String SERVER_CALL_INFO_LIST = MOLDEL_PREFIX_SOCIALITY + "callinfo.list";
	public static final String SERVER_CALL_INFO_DEL = MOLDEL_PREFIX_SOCIALITY + "callinfo.del"; // 删除手表通话
	public static final String SERVER_FRIEND_LIST = MOLDEL_PREFIX_SOCIALITY + "friend.list"; // 查询好友列表
	public static final String SERVER_FRIEND_SEARCH = MOLDEL_PREFIX_SOCIALITY + "friend.search"; // 搜索好友
	public static final String SERVER_APPLYTOBEFRIEND_LIST = MOLDEL_PREFIX_SOCIALITY + "applyToBefriend.list"; // 查询好友申请（新的朋友）列表
	public static final String SERVER_APPLYTOBEFRIEND_ADD = MOLDEL_PREFIX_SOCIALITY + "applyToBeFriend.add"; // 添加好友申请（添加到通讯录）
	public static final String SERVER_APPLYTOBEFRIEND_DEL = MOLDEL_PREFIX_SOCIALITY + "applyToBeFriend.del"; // 删除好友申请（新的朋友）
	public static final String SERVER_FRIEND_DEL = MOLDEL_PREFIX_SOCIALITY + "friend.del"; // 删除好友
	public static final String SERVER_AGREEWITH2FRIEND_OPT = MOLDEL_PREFIX_SOCIALITY + "agreeWithToBeFriend.opt"; // 同意成为好友
	public static final String SERVER_AGREEWITH2FRIEND_BLUETOOTH_OPT = MOLDEL_PREFIX_SOCIALITY
			+ "agreeWithToBeFriend.bluetooth.opt"; // 同意成为好友
	public static final String SERVER_REFUSE2FRIEND_OPT = MOLDEL_PREFIX_SOCIALITY + "refuseToBeFriend.opt"; // 拒绝成为好友
	public static final String SERVER_MODIFYALIAS_OPT = MOLDEL_PREFIX_SOCIALITY + "modify.alias"; // 修改好友备注

	/** 创建群聊 */
	public static final String SERVER_GROUPCHAT_GROUP_CREATE = MOLDEL_PREFIX_SOCIALITY + "groupchat.group.create";
	/** 退出群聊 */
	public static final String SERVER_GROUPCHAT_GROUP_QUIT = MOLDEL_PREFIX_SOCIALITY + "groupchat.group.quit";
	/** 屏蔽群聊 */
	public static final String SERVER_GROUPCHAT_GROUP_SCREEN = MOLDEL_PREFIX_SOCIALITY + "groupchat.group.screen";
	/** 群聊名称编辑 */
	public static final String SERVER_GROUPCHAT_GROUP_MODIFY = MOLDEL_PREFIX_SOCIALITY + "groupchat.group.modify";
	/** 添加群聊成员 */
	public static final String SERVER_GROUPCHAT_MEMBER_ADD = MOLDEL_PREFIX_SOCIALITY + "groupchat.member.add";
	/** 删除群聊成员 */
	public static final String SERVER_GROUPCHAT_MEMBER_DEL = MOLDEL_PREFIX_SOCIALITY + "groupchat.member.del";
	/** 修改群聊昵称 */
	public static final String SERVER_GROUPCHAT_MEMBER_MODIFY = MOLDEL_PREFIX_SOCIALITY + "groupchat.member.modify";
	/** 群聊成员列表 */
	public static final String SERVER_GROUPCHAT_MEMBER_SEARCH = MOLDEL_PREFIX_SOCIALITY + "groupchat.member.search";
	/** 设置手表接收群消息 */
	public static final String SERVER_GROUPCHAT_MEMBER_SETWATCH = MOLDEL_PREFIX_SOCIALITY + "groupchat.member.setwatch";
	/** 根据UID推送群信息 */
	public static final String SERVER_GROUPCHAT_SEND_BYUID = MOLDEL_PREFIX_SOCIALITY + "groupchat.send.byuid";

	// =================wallet================
	private static final String MOLDEL_PREFIX_WALLET = "wallet.";
	public static final String SERVER_COM_ACCOUNT_QUERY = MOLDEL_PREFIX_WALLET + "comaccount.query"; // 通信账户余额
	public static final String SERVER_COM_ACCOUNT_RECHARGE = MOLDEL_PREFIX_WALLET + "comaccount.recharge"; // 通信账户充值
	public static final String SERVER_COM_ACCOUNT_RECHARGE_HISTORY = MOLDEL_PREFIX_WALLET
			+ "comaccount.recharge.history"; // 通信账户充值历史记录
	public static final String SERVER_COM_ACCOUNT_RECHARGE_MONTHLIST = MOLDEL_PREFIX_WALLET
			+ "comaccount.recharge.monthlist"; // 通信账户充值历史记录

	// ===================version==============
	public static final String VERSON_UPGRADE = "version.upgrade";
	public static final String WATCH_VERSON_UPGRADE = "watch.version.upgrade";

	// ===================user=================
	public static final String FIRMWARE_UPGRADE = "firmware.upgrade";
	public static final String USER_FRIEND_LIKES_LIST = "user.friendlikes.list";
	public static final String USER_FRIEND_LIKES_OPEATER = "user.friendlikes.operate";

	// ================contacks================
	public static final String MOLDEL_PREFIX_CONTACK = "contact.";
	public static final String SERVER_CONTACK_MEMBER_LIST = MOLDEL_PREFIX_CONTACK + "member.list";// 通讯录列表
	public static final String SERVER_CONTACK_MEMBER_SEARCH = MOLDEL_PREFIX_CONTACK + "member.search";// 通讯录查询
	public static final String SERVER_CONTACK_MEMBER_INFO = MOLDEL_PREFIX_CONTACK + "member.info";// 通讯录信息
	public static final String SERVER_CONTACK_MEMBER_ADD = MOLDEL_PREFIX_CONTACK + "member.add";// 增加通讯录
	public static final String SERVER_CONTACK_MEMBER_EDIT = MOLDEL_PREFIX_CONTACK + "member.edit";// 编辑通讯录
	public static final String SERVER_CONTACK_MEMBER_DEL = MOLDEL_PREFIX_CONTACK + "member.del";// 删除通讯录
	public static final String SERVER_CONTACK_MEMBER_HEAD = MOLDEL_PREFIX_CONTACK + "member.head";// 编辑通讯录头像
	public static final String SERVER_CONTACK_MEMBER_SYNC = MOLDEL_PREFIX_CONTACK + "member.sync";// 同步通讯录到手表
	public static final String SERVER_CONTACK_MEMBER_SETWATCH = MOLDEL_PREFIX_CONTACK + "member.setwatch";// 通讯录在手表显示
	public static final String SERVER_CONTACK_MEMBER_SYNC_BYUID = MOLDEL_PREFIX_CONTACK + "member.sync.byuid";// 根据用户ID推通讯录
}
