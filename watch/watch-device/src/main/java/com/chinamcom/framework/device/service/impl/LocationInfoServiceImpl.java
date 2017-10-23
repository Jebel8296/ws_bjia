package com.chinamcom.framework.device.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.device.model.LocationInfo;
import com.chinamcom.framework.device.service.LocationInfoService;

@Service
public class LocationInfoServiceImpl implements LocationInfoService {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Override
	public String getadCodeByImei(String imei) {
		String sql = "select adcode from location_info where imei = ? order by id desc limit 1";
		List<String> adCodeList = jdbcTemplate.query(sql, new Object[]{imei}, (rs, i) -> {
            return rs.getString("adcode");
        });
		if(adCodeList != null && adCodeList.size() > 0){
			return adCodeList.get(0);
		}
		return null;
	}

	@Override
	public LocationInfo getLocationByImei(String imei) {
		String sql = "select lon_fix lon, lat_fix lat, address from location_info where imei = ? order by id desc limit 1";
		List<LocationInfo> locationInfoList = jdbcTemplate.query(sql, new Object[]{imei}, (rs, i) -> {
			LocationInfo locationInfo = new LocationInfo();
			locationInfo.setLat(rs.getString("lat"));
			locationInfo.setLon(rs.getString("lon"));
			locationInfo.setAddress(rs.getString("address"));
            return locationInfo;
        });
		if(locationInfoList != null && locationInfoList.size() > 0){
			return locationInfoList.get(0);
		}
		return null;
	}

}
