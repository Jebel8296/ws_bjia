package com.chinamcom.framework.user.verticle;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.user.dao.AppUserInfo;
import com.chinamcom.framework.user.dao.TargetBaseInfo;
import com.chinamcom.framework.user.service.PersonalInfoService;
import com.chinamcom.framework.user.service.TargetBaseInfoService;

@Component
public class TargetBaseInfoVerticle extends BaseVerticle{

	private static Logger logger = Logger.getLogger(TargetBaseInfoVerticle.class);
	/*@Autowired
	private TransactionTemplate sjc;
	
	@Autowired
	private DataSource dataSource;
	
	@Qualifier(value="jdbcTemplate11")
	private JdbcTemplate jdbcTemplate;*/
	 
	@Autowired
	private  TargetBaseInfoService targetBaseInfoService;
	
	/*@Autowired
	private DeviceWearInfoService deviceWearInfoService;*/
	
	@Autowired
	private PersonalInfoService personalInfoService;
	
	public TargetBaseInfoVerticle() {
	}

	@Override
	public void start() throws Exception {
		vertx.eventBus().consumer("user.targetbaseinfo.targetbaseinfolist", message -> {
			ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
			logger.info("查询基础信息请求：" + message.body());
			try{
			Integer uid = request.getJSONObject("targetbaseinfo").getInteger("uid");//用户uid
			String deviceImei = request.getJSONObject("targetbaseinfo").getString("deviceImei");//imei号
//			Integer target = request.getJSONObject("targetbaseinfo").getInteger("target");//用户目标
			Map<String , Object> map = new HashMap<String , Object>();
			map.put("uid", uid);
			map.put("deviceImei", deviceImei);
//			DeviceWearInfo deviceActiveInfo = deviceWearInfoService.getDeviceWearInfoByImei(map);
			AppUserInfo appUserInfo =  personalInfoService.getAppUserInfoByUid(uid);
			List<TargetBaseInfo> list = targetBaseInfoService.getTargetBaseInfoList(map);
			Integer height = null;
			Integer weight = null;
			if(appUserInfo!=null){
				if(appUserInfo.getHeight()!=null && !appUserInfo.getHeight().equals("")){
					height = appUserInfo.getHeight();
				}else{
					height = 135;
				}
				double stepd = dealStepd(height);
				if(appUserInfo.getWeight()!=null && !appUserInfo.getWeight().equals("")){
					weight = appUserInfo.getWeight();
				}else{
					weight = 29;
				}
				for(int i=0;i<list.size();i++){
					Integer target = Integer.parseInt(list.get(i).getStepNo());
					list.get(i).setDuration(dealDuration(stepd,target)+"");
					list.get(i).setDistance(dealDistance(stepd,target));
					list.get(i).setEnergy(dealEnergy(weight,stepd,target));
				}
			}else{
				height = 135;
				double stepd = dealStepd(height);
				weight = 29;
				for(int i=0;i<list.size();i++){
					Integer target = Integer.parseInt(list.get(i).getStepNo());
					list.get(i).setDuration(dealDuration(stepd,target)+"");
					list.get(i).setDistance(dealDistance(stepd,target));
					list.get(i).setEnergy(dealEnergy(weight,stepd,target));
				}
			}
			
			//佩戴者信息算消耗的时长、热量、距离
			/*if(deviceActiveInfo!=null){
				if(deviceActiveInfo.getDeviceHeight()!=null && !deviceActiveInfo.getDeviceHeight().equals("")){
					height = deviceActiveInfo.getDeviceHeight();
				}else{
					height = 170;
				}
				double stepd = dealStepd(height);
				if(deviceActiveInfo.getDeviceWeight()!=null && !deviceActiveInfo.getDeviceWeight().equals("")){
					weight = deviceActiveInfo.getDeviceWeight();
				}else{
					weight = 65;
				}
				for(int i=0;i<list.size();i++){
					Integer target = Integer.parseInt(list.get(i).getStepNo());
					list.get(i).setDuration(dealDuration(stepd,target)+"");
					list.get(i).setDistance(dealDistance(stepd,target));
					list.get(i).setEnergy(dealEnergy(weight,stepd,target));
				}
			}else{
				height = 170;
				double stepd = dealStepd(height);
				weight = 65;
				for(int i=0;i<list.size();i++){
					Integer target = Integer.parseInt(list.get(i).getStepNo());
					list.get(i).setDuration(dealDuration(stepd,target)+"");
					list.get(i).setDistance(dealDistance(stepd,target));
					list.get(i).setEnergy(dealEnergy(weight,stepd,target));
				}
			}*/
			message.reply(respWriter.toSuccess(getSerialNumber(request), list));
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				message.reply(respWriter.toError(getSerialNumber(request), RespCode.CODE_500));
			}
			});
		}
	
	//时长(分钟)
	public Integer dealDuration(double stepd,Integer target){
		double iRound = 60*stepd*target/5000.0/60.0;
		BigDecimal deSource = new BigDecimal(iRound);
		int duration = deSource.setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
		return duration;
	}
	
	//距离(步距。。。cm)
	public double dealStepd(Integer deviceHeight){
		double stepd = deviceHeight*0.45;
		return stepd;
	}	
	
	//距离()
	public String dealDistance(double stepd,Integer target){
		double distance = new Double(Math.round(stepd*target/100/100.0)/10.0);
		return String.valueOf(distance);
	}
	
	//热量
	public String dealEnergy(Integer deviceWeight,double stepd,Integer target){
		double engrykc = (deviceWeight*stepd*target)/100.0/1000.0*1.036;
		BigDecimal deSource = new BigDecimal(engrykc);
		int energy = deSource.setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
		return String.valueOf(energy);
	}
}
