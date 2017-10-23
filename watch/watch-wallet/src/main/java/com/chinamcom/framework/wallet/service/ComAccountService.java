package com.chinamcom.framework.wallet.service;

import java.text.ParseException;
import java.util.List;

import com.chinamcom.framework.bss.exception.DAOBaseException;
import com.chinamcom.framework.bss.model.RechargeInfo;
import com.chinamcom.framework.bss.model.mhall.ResponseObject;
import com.chinamcom.framework.common.exception.ServiceException;


public interface ComAccountService {
	
	ResponseObject recharge(String phoneNo, Integer amount, String payChannel, String channel);
	
	ResponseObject getComBalance(String phoneNo, String channel);
	
	ResponseObject rechargeList(String phoneNo, String yearMonth, String channel);

	ResponseObject getVersionInfo(String appKey, Integer fileType, Integer versionCode,String channel);
	
	List<Object> getSelectMonthList();
	
	int getBalance(String phoneNo) throws DAOBaseException, ParseException;
	
	boolean checkPwd(String phoneNo, String pwd) throws ServiceException, DAOBaseException ;
	
	List<RechargeInfo> rechargeHis(String phoneNo, String month) throws ServiceException, DAOBaseException ;
	
}

