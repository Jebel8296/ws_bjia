package com.chinamcom.framework.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.util.PropertyPlaceholderHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Administrator on 2016/7/26.
 */
public class PropertyConfigLoader extends PropertyPlaceholderConfigurer {

    private static Map<String, String> properties = new HashMap<>();

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper(
                DEFAULT_PLACEHOLDER_PREFIX, DEFAULT_PLACEHOLDER_SUFFIX, DEFAULT_VALUE_SEPARATOR, false);

        for (Map.Entry<Object, Object> entry : props.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            value = helper.replacePlaceholders(value, props);
            properties.put(key, value);
        }

        super.processProperties(beanFactoryToProcess, props);
    }

    public  String get(String key) {
        return properties.get(key);
    }

    public  String get(String key, String def) {
        return properties.containsKey(key) ? properties.get(key) : def;
    }

    public  Map<String, String> getProperties() {
        return properties;
    }


}
