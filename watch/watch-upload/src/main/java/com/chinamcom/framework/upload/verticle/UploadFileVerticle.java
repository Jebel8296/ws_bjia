package com.chinamcom.framework.upload.verticle;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;

import com.chinamcom.framework.common.container.Application;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.utils.ConfigUtil;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.upload.model.ErrorConstants;
import com.chinamcom.framework.upload.model.RestDTO;
import com.chinamcom.framework.upload.model.UploadFile;
import com.chinamcom.framework.upload.service.UploadFileService;
import com.chinamcom.framework.upload.util.ImageUtil;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;

@Component
public class UploadFileVerticle extends BaseVerticle {
	private static Logger log = Logger.getLogger(UploadFileVerticle.class);

	@Autowired
	private UploadFileService uploadFileService;
	
	private DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	
	private DateFormat dateFormat2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    
    private static Properties config;

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:applicationContext.xml");
		UploadFileVerticle uploadFileVerticle = applicationContext.getBean(UploadFileVerticle.class);
		ClusterManager mgr = new ZookeeperClusterManager();
    	VertxOptions options = new VertxOptions().setClusterManager(mgr).setClustered(true);
    	DeploymentOptions deploymentOptions = new DeploymentOptions().setWorker(true);
		config = ConfigUtil.getDefaultConfig();
		options.setClusterHost(config.getProperty("cluster.host", "localhost"));
		List<Verticle> verticles = new ArrayList<Verticle>();
    	verticles.add(uploadFileVerticle);
		Application.run(verticles, options, deploymentOptions);
	}

	@Override
	public void start() throws Exception {
		Router router = Router.router(vertx);
		String uploadsDirectory = config.getProperty("uploadFile.location","/ecommerce/app/watch/upload-file/files/");
		router.route().handler(BodyHandler.create().setUploadsDirectory(uploadsDirectory));
		router.route("/").handler(routingContext -> {
			routingContext.response().putHeader("content-type", "text/html; charset=utf-8")
					.end("<form action=\"/upload\" method=\"post\" enctype=\"multipart/form-data\">\n" 
							+ "    <div>\n"
							+ "        <label for=\"file\">Select a file:</label>\n"
							+ "        <input type=\"file\" name=\"file\" />\n"
							+ "        <label for=\"mime\">mime:</label>\n<input type=\"text\" name=\"mime\" />\n" 
							+ "        <label for=\"name\">name:</label>\n<input type=\"text\" name=\"name\" />\n" 
							+ "        <label for=\"type\">type:</label>\n<input type=\"text\" name=\"type\" />\n" 
							+ "        <label for=\"website\">website:</label>\n<input type=\"text\" name=\"website\" />\n" 
							+ "        <label for=\"rightType\">rightType:</label>\n<input type=\"text\" name=\"rightType\" />\n" 
							+ "        <label for=\"uri\">uri:</label>\n<input type=\"text\" name=\"uri\" />\n"
							+ "    </div>\n"
							+ "    <div class=\"button\">\n" + "        <button type=\"submit\">Send</button>\n"
							+ "    </div>" + "</form>"
							+"<form action=\"/combineAvatar\" method=\"post\" >\n" 
							+ "    <div>\n"
							+ "        <label for=\"uris\">uris:</label>\n<input type=\"text\" name=\"uris\" />\n" 
							+ "        <label for=\"website\">website:</label>\n<input type=\"text\" name=\"website\" />\n" 
							+ "        <label for=\"uri\">uri:</label>\n<input type=\"text\" name=\"uri\" />\n" 
							+ "    </div>\n"
							+ "    <div class=\"button\">\n" + "        <button type=\"submit\">Send</button>\n"
							+ "    </div>" + "</form>"
						);
		});
		router.post("/upload").handler(ctx -> {
			try {
				ctx.response().putHeader("Content-Type", "text/plain; charset=utf-8");
				ctx.response().setChunked(true);
				String type = ctx.request().getParam("type");
				String website = ctx.request().getParam("website");
				String rightType = ctx.request().getParam("rightType");
				//String token = ctx.request().getParam("token");
				Set<FileUpload> fileUploads = ctx.fileUploads();
				if(fileUploads.size() != 1){
					ctx.response().end(Json.encode(RestDTO.error(ErrorConstants.UPLOADFILEERROR.E80001)));
					return;
				}
				FileUpload fileUpload = fileUploads.iterator().next();
				long size = fileUpload.size();
				String uri = ctx.request().getParam("uri");
				String fileName = fileUpload.fileName();
				String mime = fileUpload.contentType();
				Integer uid = 0;
				UploadFile uploadFile = null;
				if(uri!=null && !uri.trim().equals("")){
					Map<String, Object> map = new HashMap<String, Object>();
		            map.put("uri", uri);
		            List<UploadFile> uploadFiles = uploadFileService.getUploadFile(map);
		            if(uploadFiles == null || uploadFiles.size() == 0){
		            	ctx.response().end(Json.encode(RestDTO.error(ErrorConstants.UPLOADFILEERROR.E80002)));
						return;
		            }
		            uploadFile = uploadFiles.get(0);
		            try {
						Files.delete(Paths.get(uploadsDirectory+uri)); //删除原先的文件
					} catch (IOException e) {
						log.error("对应文件找不到，删除忽略：", e);
					}
		            
		            String uploadedFileName = fileUpload.uploadedFileName();
					String directory = dateFormat.format(new Date());
					Path directoryPath = Paths.get(uploadsDirectory + directory);
					if(!Files.exists(directoryPath)){
						Files.createDirectory(directoryPath);
					}
					uri = directory + "/" + Paths.get(uploadedFileName).getFileName().toString();
					Files.move(Paths.get(uploadedFileName), Paths.get(uploadsDirectory+uri));
					uploadFile.setMime(mime);
					uploadFile.setName(fileName);
					uploadFile.setSize(size);
					uploadFile.setType(type);
					uploadFile.setUri(uri);
					uploadFile.setCreatedAt(new Date());
					uploadFile.setUid(uid);
					uploadFile.setWebsite(website);
					uploadFile.setRightType(UploadFile.RightType.valueOf(rightType));
					uploadFileService.update(uploadFile);
				}else{
					String uploadedFileName = fileUpload.uploadedFileName();
					String directory = dateFormat.format(new Date());
					Path directoryPath = Paths.get(uploadsDirectory + directory);
					if(!Files.exists(directoryPath)){
						Files.createDirectory(directoryPath);
					}
					uri = directory + "/" + Paths.get(uploadedFileName).getFileName().toString();
					Files.move(Paths.get(uploadedFileName), Paths.get(uploadsDirectory+uri));
					uploadFile = new UploadFile();
					uploadFile.setMime(mime);
					uploadFile.setName(fileName);
					uploadFile.setSize(size);
					uploadFile.setType(type);
					uploadFile.setUri(uri);
					uploadFile.setCreatedAt(new Date());
					uploadFile.setUid(uid);
					uploadFile.setWebsite(website);
					uploadFile.setRightType(UploadFile.RightType.valueOf(rightType));
					uploadFileService.insert(uploadFile);
				}
				ctx.response().end(Json.encode(RestDTO.success(uploadFile)));
			} catch (Exception e) {
				log.error("文件上传接收失败", e);
				ctx.response().end(Json.encode(RestDTO.error(ErrorConstants.UPLOADFILEERROR.E30000)));
			}
		});
		router.get("/download.do").handler(ctx -> {
			try {
				ctx.response().putHeader("Content-Type", "text/plain; charset=utf-8");
				String uri = ctx.request().getParam("uri");
				//String token = ctx.request().getParam("token");
				HttpServerResponse response = ctx.response();
	            Map<String, Object> map = new HashMap<String, Object>();
	            map.put("uri", uri);
	            List<UploadFile> uploadFiles = uploadFileService.getUploadFile(map);
	            if(uploadFiles == null || uploadFiles.size() == 0){
	            	ctx.response().end(Json.encode(RestDTO.error(ErrorConstants.UPLOADFILEERROR.E80002)));
					return;
	            }
	            UploadFile uploadFile = uploadFiles.get(0);
	            response.putHeader("Content-Type", uploadFile.getMime());
	            response.putHeader("Content-Disposition", "attachment;filename="+new String(uploadFile.getName().getBytes("utf-8"), "ISO_8859_1"));
	            response.putHeader("X-Accel-Redirect", "/files/"+uri);
				ctx.response().setChunked(true);
				ctx.response().end();
			} catch (Exception e) {
				log.error("文件下载失败", e);
				ctx.response().end(Json.encode(RestDTO.error(ErrorConstants.UPLOADFILEERROR.E30000)));
			}
		});
		router.post("/combineAvatar").handler(ctx -> {
			try {
				log.info("合并头像开始");
				long start = System.currentTimeMillis();
				ctx.response().putHeader("Content-Type", "text/plain; charset=utf-8");
				String uri = ctx.request().getParam("uri");
				String uris = ctx.request().getParam("uris");
				String website = ctx.request().getParam("website");
				String urisArray[] = uris.split(",");
				List<BufferedImage> originalBufferedImage = new ArrayList<>();
				for(String uriTmp : urisArray){
					if("default".equals(uriTmp)){//默认头像
						uriTmp = "defaultHead.png";
					}
					byte[] uriAllBytes;
					try {
						if(uriTmp.startsWith("http://")){//第三方登陆时，头像是第三方的地址
							URI picUri = new URI(uriTmp);
							SimpleClientHttpRequestFactory schr = new SimpleClientHttpRequestFactory();
							ClientHttpRequest chr = schr.createRequest(picUri, HttpMethod.GET);
				            //chr.getBody().write(param.getBytes());//body中设置请求参数
							ClientHttpResponse res = chr.execute();
							InputStream is = res.getBody(); //获得返回数据,注意这里是个流
							ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
					        byte[] buff = new byte[100];  
					        int rc = 0;  
					        while ((rc = is.read(buff, 0, 100)) > 0) {  
					            swapStream.write(buff, 0, rc);  
					        }  
					        uriAllBytes = swapStream.toByteArray();
						}else{
							uriAllBytes = Files.readAllBytes(Paths.get(uploadsDirectory+uriTmp));
						}
					} catch (IOException e) {
						log.error("对应头像找不到，将采用默认头像：", e);
						uriAllBytes = Files.readAllBytes(Paths.get(uploadsDirectory+"defaultHead.png"));
					}
					ByteArrayInputStream in = new ByteArrayInputStream(uriAllBytes);
					try {
						originalBufferedImage.add(ImageIO.read(in));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				byte[] avatar = null;
				try {
					avatar = ImageUtil.getCombinationOfhead(originalBufferedImage);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Integer uid = 0;
				UploadFile uploadFile = null;
				if(uri!=null && !"".equals(uri.trim())){//修改群组头像
					Map<String, Object> map = new HashMap<String, Object>();
		            map.put("uri", uri);
		            List<UploadFile> uploadFiles = uploadFileService.getUploadFile(map);
		            if(uploadFiles == null || uploadFiles.size() == 0){
		            	ctx.response().end(Json.encode(RestDTO.error(ErrorConstants.UPLOADFILEERROR.E80002)));
						return;
		            }
		            Files.delete(Paths.get(uploadsDirectory+uri)); //删除原先的文件
		            uploadFile = uploadFiles.get(0);
		            Date now = new Date();
					String directory = dateFormat.format(now);
					String fileName = dateFormat2.format(now)+RandomStringUtils.randomNumeric(3)+".jpg";
					Path directoryPath = Paths.get(uploadsDirectory + directory);
					if(!Files.exists(directoryPath)){
						Files.createDirectory(directoryPath);
					}
					Path newFile = Files.createFile(Paths.get(uploadsDirectory+directory+"/"+fileName));
					Files.write(newFile, avatar);
					uri = directory+"/"+fileName ;
					uploadFile.setSize(new Long(avatar.length));
					uploadFile.setUid(uid);
					uploadFile.setUri(uri);
					uploadFileService.update(uploadFile);
				}else{//创建群组头像
					Date now = new Date();
					String directory = dateFormat.format(now);
					String fileName = dateFormat2.format(now)+RandomStringUtils.randomNumeric(3)+".jpg";
					Path directoryPath = Paths.get(uploadsDirectory + directory);
					if(!Files.exists(directoryPath)){
						Files.createDirectory(directoryPath);
					}
					Path newFile = Files.createFile(Paths.get(uploadsDirectory+directory+"/"+fileName));
					Files.write(newFile, avatar);
					uri = directory+"/"+fileName ;
					uploadFile = new UploadFile();
					uploadFile.setMime("image/jpg");
					uploadFile.setName("combineAvatar.jpg");
					uploadFile.setSize(new Long(avatar.length));
					uploadFile.setUri(uri);
					uploadFile.setCreatedAt(new Date());
					uploadFile.setWebsite(website);
					uploadFile.setUid(uid);
					uploadFile.setRightType(UploadFile.RightType.PUBLIC);
					uploadFileService.insert(uploadFile);
				}
				ctx.response().end(Json.encode(RestDTO.success(uploadFile)));
				long end = System.currentTimeMillis();
				long sec = (end - start)/1000;
				log.info("合并头像结束,共耗时秒数："+sec);
			} catch (Exception e) {
				log.error("生成群组头像失败", e);
				ctx.response().end(Json.encode(RestDTO.error(ErrorConstants.UPLOADFILEERROR.E30000)));
			}
		});
		vertx.createHttpServer().requestHandler(router::accept).listen(Integer.parseInt(config.getProperty("server.port.http","10020")));
	}

}
