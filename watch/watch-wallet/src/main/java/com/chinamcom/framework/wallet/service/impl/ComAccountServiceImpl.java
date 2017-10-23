package com.chinamcom.framework.wallet.service.impl;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.bss.exception.DAOBaseException;
import com.chinamcom.framework.bss.model.CXZHResponseObject;
import com.chinamcom.framework.bss.model.CXZHResponseObject.BalanceInfo;
import com.chinamcom.framework.bss.model.InterFaceCode;
import com.chinamcom.framework.bss.model.RechargeInfo;
import com.chinamcom.framework.bss.model.mhall.ResponseObject;
import com.chinamcom.framework.bss.service.SimpleQueryBusiness;
import com.chinamcom.framework.bss.service.SimpleQueryMhall;
import com.chinamcom.framework.common.exception.ServiceException;
import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.utils.DateUtil;
import com.chinamcom.framework.common.utils.StringUtil;
import com.chinamcom.framework.wallet.service.ComAccountService;


@Service
public class ComAccountServiceImpl implements ComAccountService {

	@Autowired
	private SimpleQueryBusiness simpleQueryBusiness;
	
	@Autowired
	private SimpleQueryMhall simpleQueryMhall;
	
	@Override
	public int getBalance(String phoneNo) throws DAOBaseException, ParseException {
		HashMap<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("phoneno", phoneNo);
		CXZHResponseObject obj = (CXZHResponseObject) simpleQueryBusiness.callInterface(reqMap, InterFaceCode.CXZHXX);
		int balance = 0;
		if (obj.getResponse_data() != null && obj.getResponse_data().getBalanceinfo() != null) {
			List<BalanceInfo> balances = obj.getResponse_data().getBalanceinfo();
			for (BalanceInfo ba : balances) {
				if (ba.getBoxid() == 80) {
					continue;
				}
				long effTime = DateUtil.getDateTime(ba.getEfftime()).getTime();
				long expireTime = DateUtil.getDateTime(ba.getExpiretime()).getTime();
				long dateNow = new Date().getTime();
				if (dateNow >= effTime && dateNow < expireTime) {
					balance += ba.getAmt();
				}
			}
		}
		return balance;
	}

	@Override
	public boolean checkPwd(String phoneNo, String pwd) throws ServiceException, DAOBaseException {
		if(StringUtil.isEmpty(pwd)){//如果密码为空
			throw new ServiceException("密码不能为空", RespCode.CODE_1000);
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("phoneno", phoneNo);
		map.put("password", pwd.toUpperCase());
		map.put("password_type","1");
		try {
			simpleQueryBusiness.callInterface(map, InterFaceCode.KFMMJY);
		} catch (DAOBaseException e) {
			if(e.getMessage().indexOf("06E000012") >= 0 ||e.getMessage().indexOf("鉴权失败") >= 0){
				throw new ServiceException("错误的服务密码", RespCode.CODE_1000);
			}else{
				throw e;
			}
		}
		return false;
	}
	
	@Override
	public ResponseObject getComBalance(String phoneNo, String channel) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("phoneNo", phoneNo);
		return simpleQueryMhall.callInterface(map, "account/getBalance", channel);
	}

	@Override
	public ResponseObject recharge(String phoneNo, Integer amount, String payChannel, String channel) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("targetNo", phoneNo);
		map.put("amount", amount);
		map.put("payChannel", payChannel);
		return simpleQueryMhall.callInterface(map, "account/recharge", channel);
	}
	
	@Override
	public ResponseObject rechargeList(String phoneNo, String yearMonth, String channel) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("phoneNo", phoneNo);
		map.put("yearMonth", yearMonth);
		return simpleQueryMhall.callInterface(map, "account/getRechargeHisList", channel);
	}
	
	@Override
	public ResponseObject getVersionInfo(String appKey, Integer fileType, Integer versionCode,String channel) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("appOnlyKey",appKey);
		map.put("fileType", fileType);
		map.put("versionCode",versionCode);
		return simpleQueryMhall.callInterface(map, "version/getVersionInfo", channel);
	}

	@Override
	public List<Object> getSelectMonthList() {
		List<Object> monthList = new ArrayList<Object>();
		Date current = new Date();
		for (int i = 0; i < 6; i++) {
			Date d = DateUtils.addMonths(current, -i);
			Map<String, String> month = new HashMap<String, String>();
			month.put("monthStr", DateUtil.getMonthString(d));
			month.put("monthCN", DateUtil.getMonthStringCN(d));
			monthList.add(month);
		}
		return monthList;
	}

	@Override
	public List<RechargeInfo> rechargeHis(String phoneNo, String month) throws ServiceException, DAOBaseException {
		String sDate = "";
		String eDate = "";
		if (month != null && month.length() == 6) {
			sDate = month+"01";
			eDate = DateUtil.getMonthEndDate(month);
		} else {
			//查询全部，最多6个月的
			Date start = DateUtils.addMonths(new Date(), -6);
			sDate = DateUtil.getDateString(start);
			eDate = DateUtil.getDateString(DateUtils.addDays(new Date(), 2));
		}
		HashMap<String, Object> reqMsg = new HashMap<String, Object>();
		reqMsg.put("phoneno", phoneNo);
		reqMsg.put("start_date", sDate);
		reqMsg.put("end_date", eDate);
		// 查询crm充值记录
		CXZHResponseObject history = (CXZHResponseObject) simpleQueryBusiness.callInterface(reqMsg, InterFaceCode.RECHARGE_HISTORY);
		// 返回结果
		List<RechargeInfo> returnList = new ArrayList<RechargeInfo>();
		
		List<com.chinamcom.framework.bss.model.CXZHResponseObject.HistoryData> chargeInfo = history.getResponse_data().getChargeinfo();
		for (com.chinamcom.framework.bss.model.CXZHResponseObject.HistoryData hd : chargeInfo) {
			if(hd.getCharge_money()==0){
				continue;
			}
			RechargeInfo ri = new RechargeInfo();
			ri.setChannel(channelCode2String(hd));
			ri.setSum(hd.getCharge_money());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				ri.setTime(sdf.parse(hd.getCharge_time()).getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//	ri.setPhoneNo(hd.getCharge_phoneno());
			
			returnList.add(ri);
		}
		
		// 由于crm返回的是正序，这里改成反序
		Collections.reverse(returnList);
		return returnList;
	}
	
	private String channelCode2String(com.chinamcom.framework.bss.model.CXZHResponseObject.HistoryData hd){
		int charge_book_id = hd.getCharge_book_id();
		int pay_type = hd.getPay_type();
		String channelCode = "";
		if (pay_type == 12) {
			channelCode = "资金账户转入" ;  //余额转移
		}else{
			switch (charge_book_id) {
				case 1:;
				case 51:channelCode =getChannelName(hd.getChannel_code())+"充值" ;break;
				/*case 21:
				//	String prod_name = productMapper.selectProWithAttrByInterCode(historyData.getPay_prod()).get(0).getProName();
					String prod_name = null;
					channelCode = prod_name ;break;*/
				case 10:channelCode = "话费卡充值" ;break;
				case 22:channelCode = "优惠赠送" ;break;
				case 31:channelCode = "充值赠送" ;break;
				case 50:channelCode = "调账" ;break;
				case 81:channelCode = "账单赠送" ;break;
				case 80:channelCode = "营销活动赠送" ;break;
				default:
					break;
			}
		}
		return channelCode;
	}
	private String getChannelName(String channelCode){
		String channelName = ""; 
		/*try {
		//	UmChannelInfo umChannelInfo = null;
					//umChannelInfoMapper.selectByPrimaryKey(channelCode);
			channelName =  null;  //umChannelInfo.getChannelName();
		} catch (Exception e) {
			//logger.error(e.getMessage(), e);
		}*/
		if(channelCode.equals("app")){
			channelName="手机客户端";
		}else if(channelCode.equals("cplat")){
			channelName="充值平台";
		}else if(channelCode.equals("crm")){
			channelName="营业厅";
		}else if(channelCode.equals("cust")){
			channelName="客服";
		}else if(channelCode.equals("ivr")){
			channelName="IVR";
		}else if(channelCode.equals("mail")){
			channelName="邮箱";
		}else if(channelCode.equals("sms")){
			channelName="短厅";
		}else if(channelCode.equals("tmall")){
			channelName="天猫";
		}else if(channelCode.equals("vop")){
			channelName="VOP";
		}else if(channelCode.equals("web")){
			channelName="网厅";
		}else if(channelCode.equals("wechat")){
			channelName="微厅";
		}else{
			channelName="系统";
		}
		return channelName;
	}
}
