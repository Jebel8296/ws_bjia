package com.chinamcom.framework.common.constants;

/**
 * 
 * @author fattiger.xiaoyang
 * @date 2016/07/28
 */
public class Constants {

	public static final String UP_GRADE_KEY_PREFIX  = "upgrade."; 		 // redis upgrade key prefix
	public static final String BLACK_WHITE_LIST_KEY = "black.white.list";// redis black white list key

	public static final String CHANNEL_IOS 	   = "ios";
	public static final String CHANNEL_ANDROID = "android";
	public static final String CHANNEL_WATCH   = "watch";

	public static final String AMAP_WEBAPI_KEY 	  = "87ebe254ff0eac24b61a3797452b9850"; //绑定服务 Web服务API
	public static final String AMAP_DEVICEAPI_KEY = "d6e90bf4bd83db3ba4355bb7afe8dd80"; //绑定服务 智能硬件定位
	
	public static final String POSITION_URL 		  = "http://apilocate.amap.com/position";
	public static final String COORDINATE_CONVERT_URL = "http://restapi.amap.com/v3/assistant/coordinate/convert";
	public static final String REGEO_URL			  = "http://restapi.amap.com/v3/geocode/regeo";
	public static final String WEATHER_URL 			  = "http://restapi.amap.com/v3/weather/weatherInfo";
	
	public static final Integer GROUP_ID = 90000000;
	
	// 群组状态
	/**1:活动*/
	public static final Integer GROUPCHAT_GROUP_STATUS_ACTIVE = 1;
	/**2:解散*/
	public static final Integer GROUPCHAT_GROUP_STATUS_INACTIVE = 2;
	// 群组中成员状态
	/**1:在线*/
	public static final Integer GROUPCHAT_MEMBER_STATUS_ACTIVE = 1;
	/**2:退群*/
	public static final Integer GROUPCHAT_MEMBER_STATUS_QUIT = 2;
	/**手表端接受群消息*/
	public static final Integer GROUPCHAT_MEMBER_STATUS_WATCHSTAT_YES = 1;
	public static final Integer GROUPCHAT_MEMBER_STATUS_WATCHSTAT_NO = 0;
	/**屏蔽接受群消息*/
	public static final Integer GROUPCHAT_MEMBER_STATUS_SCREENSTAT_YES = 1;
	public static final Integer GROUPCHAT_MEMBER_STATUS_SCREENSTAT_NO = 0;
	
	/**
	 * 0 申请好友
	 */
	public static final int applyToBeFriend = 0;
	/**
	 * 1 同意好友申请 成为好友
	 */
	public static final int agreeWithToBeFriend = 1;
	/**
	 * 2 删除好友
	 */
	public static final int deleteFriend = 2;
	/**
	 *  -1 拒绝好友申请 不为好友
	 */
	public static final int refuseToBeFriend = -1;
	/**
	 * -2 删除好友申请 不为好友
	 */
	public static final int deleteApplyToBeFriend = -2;

}
