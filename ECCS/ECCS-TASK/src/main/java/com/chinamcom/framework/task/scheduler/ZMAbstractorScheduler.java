package com.chinamcom.framework.task.scheduler;

import java.util.Properties;

import com.chinamcom.framework.common.util.ConfigUtil;

import io.vertx.core.AbstractVerticle;

public class ZMAbstractorScheduler extends AbstractVerticle {

	protected Properties properties = ConfigUtil.getDefaultConfig();

	@Override
	public void start() throws Exception {
		super.start();
	}

}
