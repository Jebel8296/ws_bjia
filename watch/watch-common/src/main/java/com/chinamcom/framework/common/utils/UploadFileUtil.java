package com.chinamcom.framework.common.utils;

import java.util.HashMap;
import java.util.Map;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpClientResponse;

public class UploadFileUtil {
	/**
	 * 
	 * @param vertx
	 * @param host 文件上传服务器
	 * @param port 文件上传服务器对应的端口
	 * @param param 相关参数
	 * @param bytes 文件对应的流
	 * @param responseHandler 文件上传完后的处理函数
	 */
	public static void uploadFile(Vertx vertx, String host, int port, Map<String,String> param, byte[] bytes, Handler<HttpClientResponse> responseHandler){
		if(param == null){
			param = new HashMap<>();
		}
		StringBuilder sb = new StringBuilder();
		String fileName = param.get("fileName");
		String website = param.get("website");
		String rightType = param.get("rightType");
		String type = param.get("type");
		String uri = param.get("uri");
		String token = param.get("token");
		
		if(website==null || "".equals(website.trim())){
			website="watch";
		}
		sb.append("website=").append(website);

		if(rightType==null || "".equals(rightType.trim())){
			rightType="PUBLIC";
		}
		sb.append("&rightType=").append(rightType);
		
		if(type!=null){
			sb.append("&type=").append(type);
		}
		
		if(uri!=null){
			sb.append("&uri=").append(uri);
		}
		
		if(token!=null){
			sb.append("&token=").append(token);
		}
		
		if(fileName==null || "".equals(rightType.trim())){
			fileName = "tmpFile";
		}
		
		HttpClientRequest req = vertx.createHttpClient(new HttpClientOptions()).post(port, host,
				"/upload?"+sb.toString(), responseHandler);
		req.setChunked(true);
		req.putHeader("Content-Type", "multipart/form-data; boundary=MyBoundary");
		req.putHeader("accept", "application/json");
		Buffer buffer = Buffer.buffer();
		buffer.appendString("--MyBoundary\r\n");
		buffer.appendString("Content-Disposition: form-data; name=\"file\"; filename=\""+fileName+"\"\r\n");
		buffer.appendString("Content-Type: application/octet-stream\r\n");
		buffer.appendString("Content-Transfer-Encoding: binary\r\n");
		buffer.appendString("\r\n");
		buffer.appendBytes(bytes);
		buffer.appendString("\r\n");
		buffer.appendString("--MyBoundary--\r\n");
		req.end(buffer);
	}
	
	/**
	 * 
	 * @param vertx
	 * @param host 文件上传服务器
	 * @param port 文件上传服务器对应的端口
	 * @param param 相关参数
	 * @param responseHandler 文件上传完后的处理函数
	 */
	public static void combineAvatar(Vertx vertx, String host, int port, Map<String,String> param, Handler<HttpClientResponse> responseHandler){
		StringBuilder sb = new StringBuilder();
		String uris = param.get("uris");
		String website = param.get("website");
		String rightType = param.get("rightType");
		String uri = param.get("uri");
		String token = param.get("token");
		
		if(website==null || "".equals(website.trim())){
			website="watch";
		}
		sb.append("website=").append(website);

		if(rightType==null || "".equals(rightType.trim())){
			rightType="PUBLIC";
		}
		sb.append("&rightType=").append(rightType);
		
		if(uri!=null){
			sb.append("&uri=").append(uri);
		}
		
		if(token!=null){
			sb.append("&token=").append(token);
		}
		
		sb.append("&uris=").append(uris);
		
		HttpClientRequest req = vertx.createHttpClient(new HttpClientOptions()).post(port, host,
				"/combineAvatar?"+sb.toString(), responseHandler);
		req.setChunked(true);
		req.end();
	}
}
