package com.chinamcom.framework.backstage.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfigUtil {
	private static Logger log = Logger.getLogger(ConfigUtil.class);
	private static final String DEFAULT_CONFIG_FILE = "application.properties";
	private static final String SYS_CONFIG_KEY = "application.conf";
	private static final String CONFIG_FILE = "application.properties";

	public static Properties getConfig(String configFile) {
		Properties conf = new Properties();
		try {
			String resourceLocation = System.getProperty(SYS_CONFIG_KEY, configFile);
			conf.load(getConfigStream(resourceLocation));
			log.info(conf);
		} catch (FileNotFoundException e) {
			log.error("Could not find config file", e);
		} catch (IOException e) {
			log.error("Failed to load config ", e);
		}
		return conf;
	}

	public static Properties getDefaultConfig() {
		Properties conf = new Properties();
		try {
			String resourceLocation = DEFAULT_CONFIG_FILE;
			conf.load(getConfigStream(resourceLocation));
			log.info("Loaded application.properties file from resourceLocation=" + resourceLocation);
			log.info(conf);
		} catch (FileNotFoundException e) {
			log.error("Could not find config file", e);
		} catch (IOException e) {
			log.error("Failed to load config ", e);
		}
		return conf;
	}

	private static InputStream getConfigStream(String resourceLocation) throws FileNotFoundException {
		ClassLoader ctxClsLoader = Thread.currentThread().getContextClassLoader();
		InputStream is = null;
		if (ctxClsLoader != null) {
			is = ctxClsLoader.getResourceAsStream(resourceLocation);
		}
		if (is == null && !resourceLocation.equals(CONFIG_FILE)) {
			is = new FileInputStream(resourceLocation);
		} else if (is == null && resourceLocation.equals(CONFIG_FILE)) {
			is = ConfigUtil.class.getClassLoader().getResourceAsStream(resourceLocation);
			if (is == null) {
				is = ConfigUtil.class.getClassLoader().getResourceAsStream(DEFAULT_CONFIG_FILE);
			}
		}
		return is;
	}
}
