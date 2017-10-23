package com.chinamcom.framework.wallet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chinamcom.framework.wallet.dao.BusRechargeMapper;
import com.chinamcom.framework.wallet.model.BusRecharge;
import com.chinamcom.framework.wallet.service.BusRechargeService;

@Service
public class BusRechargeServiceImpl implements BusRechargeService{
	
	@Autowired
	private BusRechargeMapper busRechargeMapper;
	
	@Override
	public Integer insert(BusRecharge br) {

		return busRechargeMapper.insert(br);
	}

}
