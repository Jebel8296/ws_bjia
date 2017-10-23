package com.chinamcom.framework.common.response;



/**
 * 
 * @author fattiger.xiaoyang
 * @date 2016/07/15
 */
public class RespBodyBuilder {
	
	public String toSuccess(String serial_number) {
		return new RespBody(serial_number).toJsonString();
	}
	
	public String toCmdSuccess(String serial_number, String cmd) {
		return new RespBody(serial_number, cmd).toJsonString();
	}
	
	/**
	 * To success. 默认成功的输出
	 * 
	 * @return the request result
	 */
	public String toSuccess(String serial_number, Object result) {
		RespBody resp = new RespBody(serial_number, RespCode.CODE_200, result);
		return resp.toJsonString();
	}
	
	public String toCmdSuccess(String serial_number, String cmd, Object result) {
		RespBody resp = new RespBody(serial_number, cmd, RespCode.CODE_200, result);
		return resp.toJsonString();
	}
	
	/**
	 * 
	 * @param result the result 成功的结果对象
	 * @return the response 用于直接json输出的对象
	 */
	public String toSuccess(String serial_number, RespCode code, Object result) {
		RespBody resp = new RespBody(serial_number, code, result);
		return resp.toJsonString();
	}

	/**
	 * To error. 默认的失败结果输出
	 * 
	 * @param message 错误消息
	 * @param result the result
	 * @return the response
	 */
	public String toError(String serial_number) {
		RespBody resp = new RespBody(serial_number, RespCode.CODE_500);
		return resp.toJsonString();
	}
	
	
	
	/**
	 * To error. 默认的失败结果输出
	 * 
	 * @param message 错误消息
	 * @param result the result
	 * @return the response
	 */
	public String toError(String serial_number, RespCode code) {
		RespBody resp = new RespBody(serial_number, code);
		return resp.toJsonString();
	}
	
	/**
	 * To error.可复写错误信息 
	 * @param message 错误消息
	 * @param code the code
	 * @return the response
	 */
	public String toError(String serial_number, String message) {
		RespBody resp = new RespBody(serial_number, RespCode.CODE_500, message, null);
		return resp.toJsonString();
	}
	
	/**
	 * To error.可复写错误信息 
	 * @param message 错误消息
	 * @param code the code
	 * @return the response
	 */
	public String toCmdError(String serial_number, String cmd, String message) {
		RespBody resp = new RespBody(serial_number, cmd, RespCode.CODE_500, message, null);
		return resp.toJsonString();
	}
	
	/**
	 * To error. 默认的失败结果输出
	 * 
	 * @param message 错误消息
	 * @param result the result
	 * @return the response
	 */
	public String toCmdError(String serial_number, String cmd) {
		RespBody resp = new RespBody(serial_number, cmd, RespCode.CODE_500);
		return resp.toJsonString();
	}
	
	public String toCmdError(String serial_number, String cmd, RespCode code) {
		RespBody resp = new RespBody(serial_number, cmd, code);
		return resp.toJsonString();
	}
	
	public String toCmdError(String serial_number, String cmd, RespCode code, String message) {
		RespBody resp = new RespBody(serial_number, cmd, code, message, null);
		return resp.toJsonString();
	}
	
	/**
	 * To error.可复写错误信息 
	 * @param message 错误消息
	 * @param code the code
	 * @return the response
	 */
	public String toError(String serial_number, RespCode code, String message) {
		RespBody resp = new RespBody(serial_number, code, message, null);
		return resp.toJsonString();
	}
	
	public String toError(String serial_number, RespCode code, Throwable e){
		RespBody resp = new RespBody(serial_number, code, e);
		return resp.toJsonString();
	}
	
	/**
	 * To error.可复写错误信息 
	 * @param message 错误消息
	 * @param code the code
	 * @return the response
	 */
	public String toDAOError(String serial_number, String message) {
		RespBody resp = new RespBody(serial_number, RespCode.CODE_500, changeDaoMsg(message), null);
		return resp.toJsonString();
	}
	
	public static String changeDaoMsg(String msg){
		return msg.substring(msg.indexOf(":")+1);
	}
}
