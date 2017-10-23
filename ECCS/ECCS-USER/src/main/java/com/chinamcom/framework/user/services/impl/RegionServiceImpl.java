package com.chinamcom.framework.user.services.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinamcom.framework.common.service.BaseService;
import com.chinamcom.framework.user.services.IRegionService;

import io.vertx.core.json.JsonObject;

@Transactional
public class RegionServiceImpl extends BaseService implements IRegionService {

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public String select(String sn, JsonObject param) {
		String message = respWriter.toError(sn);
		try {
			StringBuilder provinceSql = new StringBuilder("SELECT pro_id id ,pro_name name FROM addr_province");
			// 省
			Optional<List<Map<String, Object>>> province = Optional.ofNullable(jdbcTemplate.queryForList(provinceSql.toString()));
			if (province.isPresent()) {
				
			}
			message = respWriter.toSuccess(sn);
		} catch (Exception e) {
			throw e;
		}
		return message;

	}

}
