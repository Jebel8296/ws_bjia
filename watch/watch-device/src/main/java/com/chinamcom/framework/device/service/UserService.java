package com.chinamcom.framework.device.service;

import com.chinamcom.framework.device.model.ClockInfo;

public interface UserService {
   Integer getUserIdByImei(String imei);
   
   String getSosMessageByUid(Integer uid);
   
   ClockInfo getClockInfoById(Integer id);
   
   Integer getUserTarget(Integer uid);
}
