package com.chinamcom.framework.upload.model;

import java.io.Serializable;

import com.chinamcom.framework.upload.model.ErrorConstants.UPLOADFILEERROR;

public class RestDTO implements Serializable{
	private static final long serialVersionUID = 4851000202138344380L;
	
	String code;
	String msg;
	Object respData;
	
	public RestDTO(){
		
	}
	
	public RestDTO(String code,String msg,Object respData){
		this.code = code;
		this.msg = msg;
		this.respData = respData;
	}
	public static RestDTO error(String code,String msg,Object respData){
		return new RestDTO(code,msg,respData);
	}
	public static RestDTO error(UPLOADFILEERROR uploadfileerror){
		return error(uploadfileerror.getCode(),uploadfileerror.getMsg(),null);
	}
	public static RestDTO success(Object obj){
		return new RestDTO("0","操作成功",obj);
	}
	public static RestDTO success(){
		return new RestDTO("0","操作成功","");
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getRespData() {
		return respData;
	}
	public void setRespData(Object respData) {
		this.respData = respData;
	}

	@Override
	public String toString() {
		return "RestDTO [code=" + code + ", msg=" + msg + ", respData=" + respData + "]";
	}
}
