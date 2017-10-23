package com.chinamcom.framework.products.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.service.BaseService;
import com.chinamcom.framework.common.util.StringUtil;
import com.chinamcom.framework.products.mapper.TbActivityMapper;
import com.chinamcom.framework.products.mapper.TbProductColorMapper;
import com.chinamcom.framework.products.mapper.TbProductDevicesMapper;
import com.chinamcom.framework.products.mapper.TbProductImageboxMapper;
import com.chinamcom.framework.products.mapper.TbProductInfoMapper;
import com.chinamcom.framework.products.mapper.TbProductModelMapper;
import com.chinamcom.framework.products.mapper.TbProductStockMapper;
import com.chinamcom.framework.products.mapper.TbProductTypeMapper;
import com.chinamcom.framework.products.model.TbActivity;
import com.chinamcom.framework.products.model.TbActivityExample;
import com.chinamcom.framework.products.model.TbProductColor;
import com.chinamcom.framework.products.model.TbProductColorExample;
import com.chinamcom.framework.products.model.TbProductDevices;
import com.chinamcom.framework.products.model.TbProductDevicesExample;
import com.chinamcom.framework.products.model.TbProductImagebox;
import com.chinamcom.framework.products.model.TbProductImageboxExample;
import com.chinamcom.framework.products.model.TbProductInfo;
import com.chinamcom.framework.products.model.TbProductInfoExample;
import com.chinamcom.framework.products.model.TbProductModel;
import com.chinamcom.framework.products.model.TbProductModelExample;
import com.chinamcom.framework.products.model.TbProductStock;
import com.chinamcom.framework.products.model.TbProductStockExample;
import com.chinamcom.framework.products.model.TbProductType;
import com.chinamcom.framework.products.model.TbProductTypeExample;
import com.chinamcom.framework.products.services.ITbProductInfoService;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

@Service("tbProductInfoService")
public class TbProductInfoServiceImpl extends BaseService implements ITbProductInfoService {

	@Autowired
	private TbActivityMapper activityMapper;
	@Autowired
	private TbProductInfoMapper productInfoMapper;
	@Autowired
	private TbProductDevicesMapper productDevicesMapper;	
	@Autowired
	private TbProductImageboxMapper productImageboxMapper;
	@Autowired
	private TbProductStockMapper productStockMapper;
	@Autowired
	private TbProductTypeMapper productTypeMapper;
	@Autowired
	private TbProductModelMapper productModelMapper;
	@Autowired
	private TbProductColorMapper productColorMapper;
	

	
	
	@Override
	public String selectTbProductTypeList(String sn, JsonObject param) throws Exception {
		String message = respWriter.toError(sn);
		JsonArray result = new JsonArray();
		try {
			Optional<Integer> prodtype = Optional.ofNullable(param.getInteger("prodtype"));// 产品类别40耳机41手表
			TbProductTypeExample typeExample = new TbProductTypeExample();
			TbProductTypeExample.Criteria typeCriteria = typeExample.createCriteria();
			if(prodtype.isPresent()){
				typeCriteria.andTypeCodeEqualTo(prodtype.get().toString());	
			}
			List<TbProductType> lTbProductType = productTypeMapper.selectByExample(typeExample);
			if (!lTbProductType.isEmpty()) {
				lTbProductType.forEach(tpt->{
					JsonObject type = new JsonObject();
					type.put("code", tpt.getTypeCode());
					type.put("name", tpt.getTypeName());
					TbProductModelExample modelExample = new TbProductModelExample();
					modelExample.createCriteria().andTypeCodeEqualTo(tpt.getTypeCode());
					List<TbProductModel> lTbProductModel = productModelMapper.selectByExample(modelExample);
					JsonArray model = new JsonArray();
					if (!lTbProductModel.isEmpty()) {
						lTbProductModel.forEach(tpm -> {
							JsonObject _model = new JsonObject();
							_model.put("code", tpm.getModelCode());
							_model.put("name", tpm.getModelName());
							TbProductColorExample colorExample = new TbProductColorExample();
							colorExample.createCriteria().andModelCodeEqualTo(tpm.getModelCode()).andTypeCodeEqualTo(tpt.getTypeCode());
							List<TbProductColor> lTbProductColor = productColorMapper.selectByExample(colorExample);
							JsonArray color = new JsonArray();
							if(!lTbProductColor.isEmpty()){
								lTbProductColor.forEach(tpc->{
									JsonObject _color = new JsonObject();
									_color.put("code", tpc.getColorCode());
									_color.put("name", tpc.getColorName());
									color.add(_color);
								});
							}
							_model.put("color", color);
							model.add(_model);
						});
					}
					type.put("model", model);
					result.add(type);
				});
			}
			message = respWriter.toSuccess(result, sn);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	@Override
	public String selectTbProductInfoList(String sn, JsonObject param) {
		String message = respWriter.toError(sn);
		try {
			Optional<String> prodcode = Optional.ofNullable(param.getString("prodcode"));// 产品编号
			Optional<Integer> prodid = Optional.ofNullable(param.getInteger("prodid"));// 产品ID
			Optional<Integer> prodtype = Optional.ofNullable(param.getInteger("prodtype"));// 产品类别40耳机41手表
			TbProductInfoExample piExample = new TbProductInfoExample();
			TbProductInfoExample.Criteria criteria = piExample.createCriteria();
			criteria.andStatusEqualTo(1);
			if (prodcode.isPresent() || prodid.isPresent()) {// 查询某个产品信息
				if (prodcode.isPresent()) {
					criteria.andProdcodeEqualTo(prodcode.get());
				}
				if (prodid.isPresent()) {
					criteria.andIdEqualTo(prodid.get());
				}
			}
			if(prodtype.isPresent()){
				criteria.andProdtypeEqualTo(prodtype.get());
			}
			List<TbProductInfo> pinfos = productInfoMapper.selectByExample(piExample);
			if (!pinfos.isEmpty()) {
				pinfos.forEach(prod -> {
					// 判断产品是否有活动信息
					if (prod.getActivity() == 1) {
						TbActivityExample activity = new TbActivityExample();
						activity.createCriteria().andProdcodeEqualTo(prod.getProdcode()).andProdidEqualTo(prod.getId());
						List<TbActivity> activityinfo = activityMapper.selectByExample(activity);
						if (!activityinfo.isEmpty()) {
							prod.setActivityPrice(activityinfo.get(0).getPrice());
							prod.setBeginTime(activityinfo.get(0).getBeginTime());
							prod.setEndTime(activityinfo.get(0).getEndTime());
							prod.setActivityDesc(activityinfo.get(0).getDesc());
						}
					}
					prod.setModel(StringUtil.subModelFromCode(prod.getProdcode()));
					prod.setColor(StringUtil.subColorFromCode(prod.getProdcode()));
					prod.setColorname(StringUtil.getColorName(prod.getColor()));
					// 检索产品的库存信息
					TbProductStockExample stockExample = new TbProductStockExample();
					stockExample.createCriteria().andProdidEqualTo(prod.getId()).andProdcodeEqualTo(prod.getProdcode());
					List<TbProductStock> stockinfo = productStockMapper.selectByExample(stockExample);
					if (!stockinfo.isEmpty()) {
						prod.setStock(stockinfo.get(0).getStock());
						prod.setWarning(stockinfo.get(0).getWarning());
					}
					// 检索产品展示图片信息
					TbProductImageboxExample imageExample = new TbProductImageboxExample();
					imageExample.createCriteria().andProdidEqualTo(prod.getId()).andProdcodeEqualTo(prod.getProdcode()).andTypeEqualTo(2);
					List<TbProductImagebox> imageinfo = productImageboxMapper.selectByExample(imageExample);
					if (!imageinfo.isEmpty()) {
						JsonObject showImages = new JsonObject();
						imageinfo.forEach(image -> {
							showImages.put(image.getTypenum().toString(), image.getLocation());
						});
						prod.setShowImages(showImages);
					}
					// 检索产品购买时图片信息
					TbProductImageboxExample imagBExample = new TbProductImageboxExample();
					imagBExample.createCriteria().andProdidEqualTo(prod.getId()).andProdcodeEqualTo(prod.getProdcode()).andTypeEqualTo(3);
					List<TbProductImagebox> imagebinfo = productImageboxMapper.selectByExample(imagBExample);
					if (!imagebinfo.isEmpty()) {
						JsonObject buyImages = new JsonObject();
						imagebinfo.forEach(image -> {
							buyImages.put(image.getTypenum().toString(), image.getLocation());
						});
						prod.setBuyImages(buyImages);
						;
					}
				});
				message = respWriter.toSuccess(pinfos, sn);
			}else{
				message = respWriter.toError(sn, RespCode.CODE_504);
			}
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	@Override
	public String selectTbProductDevicesList(String sn, JsonObject param) throws Exception {
		String message = respWriter.toError(sn);
		try {
			JsonObject result = new JsonObject();
			Optional<Integer> prodid = Optional.ofNullable(param.getInteger("prodid"));// 产品ID
			Optional<Integer> prodtype = Optional.ofNullable(param.getInteger("prodtype"));// 产品类别40：耳机41：手表
			Optional<String> prodcode = Optional.ofNullable(param.getString("prodcode"));// 产品编码
			Optional<String> devicescode = Optional.ofNullable(param.getString("devicescode"));// 设备编号
			TbProductDevicesExample example = new TbProductDevicesExample();
			TbProductDevicesExample.Criteria criteria = example.createCriteria();
			if (prodid.isPresent()) {
				criteria.andProdidEqualTo(prodid.get());
			}
			if (prodtype.isPresent()) {
				criteria.andProdtypeEqualTo(prodtype.get());
			}
			if (prodcode.isPresent()) {
				criteria.andProdcodeEqualTo(prodcode.get());
			}
			if (devicescode.isPresent()) {
				criteria.andDevicescodeEqualTo(devicescode.get());
			}
			List<TbProductDevices> devices = productDevicesMapper.selectByExample(example);
			if (!devices.isEmpty()) {
				//result.put("devices", devices);
				TbProductDevices d = devices.get(0);
				TbProductInfo tbProductInfo = productInfoMapper.selectByPrimaryKey(d.getProdid());
				if (tbProductInfo != null) {
					result.put("decives", d.getDevicescode());
					result.put("bdate", param.getLong("date"));
					result.put("name", tbProductInfo.getProdname());
					result.put("code",tbProductInfo.getProdcode());
					result.put("desc", tbProductInfo.getProddesc());
					result.put("price", tbProductInfo.getProdprice().floatValue());
					result.put("total", 1);
					//检索产品购买时图片信息
					TbProductImageboxExample imagBExample = new TbProductImageboxExample();
					imagBExample.createCriteria().andProdidEqualTo(d.getProdid()).andTypeEqualTo(3);
					Optional<List<TbProductImagebox>> imagebinfo = Optional.ofNullable(productImageboxMapper.selectByExample(imagBExample));
					if(imagebinfo.isPresent()){
						JsonObject buyImages = new JsonObject();
						imagebinfo.get().forEach(image->{
							buyImages.put(image.getTypenum().toString(), image.getLocation());
						});
						result.put("image", buyImages);
					}
				}
				message = respWriter.toSuccess(result, sn);
			} else {
				message = respWriter.toError(sn, RespCode.CODE_504);
			}
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

}
