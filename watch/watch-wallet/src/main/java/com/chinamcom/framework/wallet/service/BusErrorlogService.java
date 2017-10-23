package com.chinamcom.framework.wallet.service;

import com.chinamcom.framework.common.exception.ServiceException;
import com.chinamcom.framework.wallet.model.BusErrorlog;

public interface BusErrorlogService {
	
	public Integer insert(BusErrorlog br) throws ServiceException;
	
}
