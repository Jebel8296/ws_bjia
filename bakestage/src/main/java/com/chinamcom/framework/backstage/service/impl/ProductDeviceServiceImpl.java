package com.chinamcom.framework.backstage.service.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.backstage.common.PageModel;
import com.chinamcom.framework.backstage.mapper.TbProductDevicesMapper;
import com.chinamcom.framework.backstage.model.TbProductDevices;
import com.chinamcom.framework.backstage.model.TbProductDevicesExample;
import com.chinamcom.framework.backstage.reply.Reply;
import com.chinamcom.framework.backstage.service.AbstractBackstageService;
import com.chinamcom.framework.backstage.service.IProductDeviceService;
import com.chinamcom.framework.backstage.util.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import io.vertx.core.json.JsonObject;

@Service("productDeviceService")
public class ProductDeviceServiceImpl extends AbstractBackstageService implements IProductDeviceService {

	@Autowired
	private TbProductDevicesMapper productDevicesMapper;

	@Override
	public JsonObject deleteTbProductDevices(JsonObject param) {
		JsonObject result = new JsonObject();
		TbProductDevicesExample example = new TbProductDevicesExample();
		TbProductDevicesExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(param.getInteger("id"));
		try {
			List<TbProductDevices> exist = productDevicesMapper.selectByExample(example);
			if (exist != null && exist.size() > 0) {
				productDevicesMapper.deleteByExample(example);
				result = new Reply(Response.Status.OK.getStatusCode(), "successed", null).toJson();
			} else {
				result = new Reply(Response.Status.NOT_FOUND.getStatusCode(), "No Data.", null).toJson();
			}
		} catch (Exception e) {
			log.error("删除产品设备关系异常：" + e);
			result = new Reply(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "error", null).toJson();
		}
		log.info("response:" + result);
		return result;
	}

	@Override
	public JsonObject addTbProductDevices(JsonObject param) {
		JsonObject result = new JsonObject();
		TbProductDevicesExample example = new TbProductDevicesExample();
		TbProductDevicesExample.Criteria criteria1 = example.createCriteria();
		criteria1.andDevicescodeEqualTo(param.getString("devicode"));
		TbProductDevicesExample.Criteria criteria2 = example.createCriteria();
		criteria2.andProdcodeEqualTo(param.getString("prodcode"));
		example.or(criteria2);
		try {
			List<TbProductDevices> exist = productDevicesMapper.selectByExample(example);
			if (exist != null && exist.size() > 0) {
				result = new Reply(Response.Status.BAD_GATEWAY.getStatusCode(), "数据已存在.", null).toJson();
			} else {
				TbProductDevices record = new TbProductDevices();
				record.setDevicescode(param.getString("devicode"));
				record.setProdcode(param.getString("prodcode"));
				record.setProdtype(Integer.valueOf(StringUtil.subCategoryFromCode(param.getString("prodcode"))));
				productDevicesMapper.insertSelective(record);
				result = new Reply(Response.Status.OK.getStatusCode(), "successed", null).toJson();
			}
		} catch (Exception e) {
			log.error("产品设备关系新增异常：" + e);
			result = new Reply(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "error", null).toJson();
		}
		log.info("response:" + result);
		return result;
	}

	@Override
	public JsonObject queryTbProductDevices(JsonObject param) {
		JsonObject result = new JsonObject();
		TbProductDevicesExample example = new TbProductDevicesExample();
		TbProductDevicesExample.Criteria criteria = example.createCriteria();
		if (param.containsKey("prodcode")) {
			criteria.andProdcodeEqualTo(param.getString("prodcode"));
		}
		if (param.containsKey("devicode")) {
			criteria.andDevicescodeEqualTo(param.getString("devicode"));
		}
		try {
			Page<Object> page = PageHelper.startPage(param.getInteger("pageNum"), param.getInteger("pageSize"));
			List<TbProductDevices> lists = productDevicesMapper.selectByExample(example);
			if (!lists.isEmpty()) {
				result = new Reply(Response.Status.OK.getStatusCode(), "successed", new PageModel(page, lists))
						.toJson();
			} else {
				result = new Reply(Response.Status.NOT_FOUND.getStatusCode(), "No Data.", null).toJson();
			}
		} catch (Exception e) {
			log.error("检索产品设备关系异常：" + e);
			result = new Reply(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "error", null).toJson();
		}
		log.info("response:" + result);
		return result;
	}

}
