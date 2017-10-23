package com.chinamcom.framework.common.response;

public enum RespCode {
	CODE_200(200,"success"),
	
	CODE_404(404,"页面未找到"),
	CODE_450(450,"参数错误"),
	
	CODE_500(500,"服务器内部错误"),
	CODE_501(501,"无效会话"),
	CODE_502(502,"无效请求"),
	CODE_503(503,"不支持的请求"),
	CODE_504(504,"未查到数据"),
	CODE_505(505,"参数不符合规范"),
	CODE_506(506,"登陆失效，请重新登陆"),
	CODE_507(507,"验证码输入错误，请重新输入"),
	CODE_508(508,"密码输入错误，请重新输入"),
	CODE_509(509,"已提交过售后，不能重复提交"),
	
	CODE_600(600,"验签失败"),
	CODE_601(601,"系统升级"),
	CODE_602(602,"添加失败"),
	CODE_603(603,"修改失败"),
	CODE_604(604,"删除失败"),
	//user
	CODE_1000(1000,"密码错误"),
	//wallet
	CODE_3000(3000,"获取通信账户余额失败"),
	
	CODE_5000(5000,"获取充值历史记录失败"),
	//sociality
	CODE_4000(4000,"密码错误"),
	CODE_4001(4001,"该用户已是你的好友,请勿重复添加"),
	CODE_4002(4002,"之前已处理好友请求,请勿重复提交"),
	CODE_4003(4003,"不是好友"),
	CODE_4004(4004,"没有可删除的记录"),
	
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
