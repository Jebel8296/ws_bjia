package com.chinamcom.framework.upload.model;

public class ErrorConstants {
	public static enum UPLOADFILEERROR{
    	E30000("30000", "接口调用失败"),
    	E80001("80001", "上传文件必须有且只能上传一个文件！"),
    	E80002("80002", "文件不存在！");
    	
		private String code;
		private String msg;
		
		private UPLOADFILEERROR(String code,String msg) {
			this.code = code;
			this.msg = msg;
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
    }
}
