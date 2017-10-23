package com.chinamcom.framework.wallet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.wallet.dao.BusErrorlogMapper;
import com.chinamcom.framework.wallet.model.BusErrorlog;
import com.chinamcom.framework.wallet.service.BusErrorlogService;

@Service
public class BusErrorlogServiceImpl implements BusErrorlogService{

	@Autowired
	private BusErrorlogMapper busErrorlogMapper;
	
	@Override
	public Integer insert(BusErrorlog br) {
		// TODO Auto-generated method stub
		
		return busErrorlogMapper.insert(br);
	}

}
