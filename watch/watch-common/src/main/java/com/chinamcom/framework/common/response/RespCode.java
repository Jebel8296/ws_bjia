package com.chinamcom.framework.common.response;

public enum RespCode {
	CODE_200(200,"success"),
	
	CODE_404(404,"页面未找到"),
	CODE_450(450,"参数错误"),
	
	CODE_500(500,"服务器内部错误"),
	CODE_501(501,"您的账号已在其他设备上登录，会话已过期。"),
	CODE_502(502,"无效请求"),
	CODE_503(503,"不支持的请求"),
	
	CODE_600(600,"验签失败"),
	CODE_601(601,"系统升级"),
	CODE_602(602,"添加失败"),
	CODE_603(603,"修改失败"),
	CODE_604(604,"删除失败"),
	CODE_605(605,"token校验失败"),
	
	//user
	CODE_1000(1000,"密码错误"),
	CODE_1001(1001,"该手表IMEI号已被使用，请检查输入是否有误。"),
	CODE_1002(1002,"该手表已绑定此用户！"),
	CODE_1003(1003,"囧rz，手表IMEI号不对呀，你是不是趁我不注意乱输入了一些鬼东西进来！"),
	CODE_1004(1004,"用户自动注册失败"),
	CODE_1005(1005,"绑定失败"),
	CODE_1006(1006,"解绑失败"),
	CODE_1007(1007,"查询出错"),
	CODE_1008(1008,"头像上传失败"),
	CODE_1009(1009,"修改出错"),
	CODE_1010(1010,"设置目标出错"),
	CODE_1011(1011,"插入sos设置重复"),
	CODE_1012(1012,"最多设置5个闹钟!"),
	CODE_1013(1013,"删除失败"),
	CODE_1014(1014,"推送参数出错"),
	CODE_1015(1015,"新增失败"),
	CODE_1016(1016,"必传参数出错"),
	//sport
	CODE_2000(2000,"绑定手表不在线"),
	CODE_2001(2001,"未绑定手表，请先绑定手表"),
	
	//wallet
	CODE_3000(3000,"获取通信账户余额失败"),
	
	CODE_5000(5000,"获取充值历史记录失败"),
	CODE_5001(5001,"获取升级版本失败"),
	//sociality
	CODE_4000(4000,"密码错误"),
	CODE_4001(4001,"该用户已是你的好友,请勿重复添加"),
	CODE_4002(4002,"之前已处理好友请求,请勿重复提交"),
	CODE_4003(4003,"不是好友"),
	CODE_4004(4004,"没有可删除的记录"),
	CODE_4005(4005,"自己不能加自己为好友"),
	CODE_4006(4006,"你已不在群组中"),
	CODE_4007(4007,"手表上最多显示5个好友"),
	CODE_4008(4008,"修改好友是否显示失败"),
	
	CODE_8888(8888,"系统正在升级中");
	
	
	private Integer code;
	private String desp;
	
	RespCode(Integer code,String desp){
		this.code=code;
		this.desp=desp;
	}
	
	public String getDesp() {
		return desp;
	}
	
	public int getCode(){
		return code;
	}
	
	@Override
	public String toString() {
		return String.valueOf(code);
	}
}
