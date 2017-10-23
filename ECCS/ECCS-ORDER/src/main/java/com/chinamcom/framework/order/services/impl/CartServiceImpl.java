package com.chinamcom.framework.order.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.common.model.PageModel;
import com.chinamcom.framework.common.service.BaseService;
import com.chinamcom.framework.common.util.StringUtil;
import com.chinamcom.framework.order.mapper.TbOrderInstanceMapper;
import com.chinamcom.framework.order.model.TbOrderInstance;
import com.chinamcom.framework.order.model.TbOrderInstanceExample;
import com.chinamcom.framework.order.services.ICartService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

@Service("cartServiceImpl")
public class CartServiceImpl extends BaseService implements ICartService {

	@Autowired
	private TbOrderInstanceMapper tbOrderInstanceMapper;

	@Override
	public String addCart(String sn, JsonObject param) {
		// TODO addCart
		String message = null;
		try {
			Integer uid = param.getInteger("uid");
			Integer prodtotal = Optional.ofNullable(param.getInteger("total")).orElse(1);// 数量
			String prodcode = param.getString("code");
			String prodname = param.getString("name");
			Integer id = param.getInteger("id");
			BigDecimal prodprice = new BigDecimal(param.getFloat("price").floatValue()).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal payfee = prodprice.multiply(new BigDecimal(prodtotal));
			TbOrderInstance instance = new TbOrderInstance();
			// 先查询购物车是否存在
			TbOrderInstanceExample tbOrderInstanceExample = new TbOrderInstanceExample();
			TbOrderInstanceExample.Criteria criteria = tbOrderInstanceExample.createCriteria();
			criteria.andUserIdEqualTo(uid).andProductCodeEqualTo(prodcode);
			List<Integer> status = new ArrayList<Integer>();
			status.add(1);
			status.add(2);
			criteria.andStatusIn(status);
			List<TbOrderInstance> tbOrderInstanceList = tbOrderInstanceMapper.selectByExample(tbOrderInstanceExample);
			if (!tbOrderInstanceList.isEmpty()) {//更新
				Integer _prodtotal = tbOrderInstanceList.get(0).getProductTotal();
				BigDecimal _payfee = tbOrderInstanceList.get(0).getPayFee();
				instance.setProductTotal(prodtotal.intValue() + _prodtotal);
				instance.setPayFee(payfee.add(_payfee));
				instance.setUpdateTime(new Date());
				tbOrderInstanceMapper.updateByExampleSelective(instance, tbOrderInstanceExample);
			}else{//新增
				instance.setUserId(uid);
				instance.setProductId(id);
				instance.setProductCode(prodcode);
				instance.setProductName(prodname);
				instance.setProductPrice(prodprice);
				instance.setProductTotal(prodtotal);
				instance.setPayFee(payfee);
				instance.setCreateTime(new Date());
				instance.setStatus(1);
				tbOrderInstanceMapper.insertSelective(instance);
			}
			message = respWriter.toSuccess(sn);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	@Override
	public String incrCart(String sn, JsonObject param) {
		// TODO incrCart
		String message = null;
		try {
			Integer instanceid = param.getInteger("instanceid");// 购物车中产品的ID
			TbOrderInstance instance = tbOrderInstanceMapper.selectByPrimaryKey(instanceid);
			Integer total = instance.getProductTotal();
			BigDecimal price = instance.getProductPrice();
			BigDecimal payfee = instance.getPayFee();
			TbOrderInstance record = new TbOrderInstance();
			record.setId(instanceid);
			record.setUpdateTime(new Date());
			record.setProductTotal(total + 1);
			record.setPayFee(payfee.add(price));
			tbOrderInstanceMapper.updateByPrimaryKeySelective(record);

			message = respWriter.toSuccess(new JsonObject().put("total", total + 1).put("instanceid", instanceid), sn);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	@Override
	public String decrCart(String sn, JsonObject param) {
		// TODO decrCart
		String message = null;
		try {
			Integer instanceid = param.getInteger("instanceid");// 购物车中产品的ID
			TbOrderInstance instance = tbOrderInstanceMapper.selectByPrimaryKey(instanceid);
			Integer total = instance.getProductTotal();
			BigDecimal price = instance.getProductPrice();
			BigDecimal payfee = instance.getPayFee();
			TbOrderInstance record = new TbOrderInstance();
			record.setId(instanceid);
			record.setUpdateTime(new Date());
			record.setProductTotal(total - 1);
			record.setPayFee(payfee.subtract(price));
			tbOrderInstanceMapper.updateByPrimaryKeySelective(record);

			message = respWriter.toSuccess(new JsonObject().put("total", total - 1).put("instanceid", instanceid), sn);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}
	

	@Override
	public void account(JsonObject buy, JsonObject param) {
		// TODO account
		try {
			JsonArray instances = new JsonArray();
			Integer uid = param.getInteger("uid");// 用户ID
			Integer status = Optional.ofNullable(param.getInteger("status")).orElse(2);

			TbOrderInstanceExample example = new TbOrderInstanceExample();
			example.createCriteria().andUserIdEqualTo(uid).andStatusEqualTo(status);
			
			List<TbOrderInstance> result = tbOrderInstanceMapper.selectByExample(example);
			if (result != null && result.size() > 0) {
				result.forEach(item -> {
					JsonObject i = new JsonObject();
					if (item.getId() != null) {
						i.put("instanceid", item.getId());
					}
					if (item.getProductCode() != null) {
						i.put("prodcode", item.getProductCode());
					}
					if (item.getProductName() != null) {
						i.put("prodname", item.getProductName());
					}
					if (item.getProductPrice() != null) {
						i.put("prodprice", item.getProductPrice().toString());
					}
					if (item.getProductTotal() != null) {
						i.put("prodtotal", item.getProductTotal());
					}
					if (item.getPayFee() != null) {
						i.put("payfee", item.getPayFee().toString());
					}
					instances.add(i);
				});
				buy.put("product", instances);
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public String checked(String sn, JsonObject param) {
		// TODO buyCart
		String message = null;
		try {
			Integer instanceid = param.getInteger("instanceid");// 购物车中产品的ID
			Integer uid = param.getInteger("uid");// 用户ID
			Boolean checked = Optional.ofNullable(param.getBoolean("checked")).orElse(false);
			
			TbOrderInstance record = new TbOrderInstance();
			record.setUpdateTime(new Date());
			record.setStatus(checked ? 2 : 1);
			TbOrderInstanceExample example = new TbOrderInstanceExample();
			TbOrderInstanceExample.Criteria criteria = example.createCriteria();
			criteria.andUserIdEqualTo(uid);
			if(instanceid!=0){
				criteria.andIdEqualTo(instanceid);
			}
			List<Integer> status = new ArrayList<Integer>();
			status.add(1);
			status.add(2);
			criteria.andStatusIn(status);
			tbOrderInstanceMapper.updateByExampleSelective(record,example);
			/**
			JsonArray instances = new JsonArray();

			TbOrderInstanceExample query = new TbOrderInstanceExample();
			List<Integer> ss = new ArrayList<Integer>();
			ss.add(1);ss.add(2);
			query.createCriteria().andUserIdEqualTo(uid).andStatusIn(ss);

			List<TbOrderInstance> result = tbOrderInstanceMapper.selectByExample(query);
			if (result != null && result.size() > 0) {
				result.forEach(item -> {
					JsonObject i = new JsonObject();
					if (item.getId() != null) {
						i.put("instanceid", item.getId());
					}
					if (item.getProductCode() != null) {
						i.put("prodcode", item.getProductCode());
					}
					if (item.getProductName() != null) {
						i.put("prodname", item.getProductName());
					}
					if (item.getProductPrice() != null) {
						i.put("prodprice", item.getProductPrice().toString());
					}
					if (item.getProductTotal() != null) {
						i.put("prodtotal", item.getProductTotal());
					}
					if (item.getPayFee() != null) {
						i.put("payfee", item.getPayFee().toString());
					}
					if (item.getStatus() != null) {
						i.put("checked", item.getStatus() == 1 ? "false" : "true");
					}
					instances.add(i);
				});
			}
			*/
			message = respWriter.toSuccess(sn);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	@Override
	public String delCart(String sn, JsonObject param) {
		// TODO delCart
		String message = null;
		try {
			Integer instanceid = param.getInteger("instanceid");// 购物车中产品的ID
			TbOrderInstance record = new TbOrderInstance();
			record.setId(instanceid);
			record.setUpdateTime(new Date());
			record.setStatus(4);
			tbOrderInstanceMapper.updateByPrimaryKeySelective(record);
			message = respWriter.toSuccess(sn);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	@Override
	public String listCart(String sn, JsonObject param) {
		// TODO listCart
		String message = null;
		try {
			JsonArray instances = new JsonArray();

			Integer uid = param.getInteger("uid");// 用户ID
			Optional<Integer> status = Optional.ofNullable(param.getInteger("status"));
			Integer pageNum = Optional.ofNullable(param.getInteger("pageNum")).orElse(1);// 页码
			Integer pageSize = Optional.ofNullable(param.getInteger("pageSize")).orElse(Integer.MAX_VALUE);// 每页显示条数

			TbOrderInstanceExample example = new TbOrderInstanceExample();
			TbOrderInstanceExample.Criteria criteria = example.createCriteria();
			criteria.andUserIdEqualTo(uid);
			if(status.isPresent()){
				criteria.andStatusEqualTo(status.get());
			} else {
				List<Integer> s = new ArrayList<Integer>();
				s.add(1);
				s.add(2);
				criteria.andStatusIn(s);
			}
			Page<Object> page = PageHelper.startPage(pageNum, pageSize);
			List<TbOrderInstance> result = tbOrderInstanceMapper.selectByExample(example);
			if (result != null && result.size() > 0) {
				result.forEach(item -> {
					JsonObject i = new JsonObject();
					if (item.getId() != null) {
						i.put("instanceid", item.getId());
					}
					if (item.getProductCode() != null) {
						i.put("prodcode", item.getProductCode());
						i.put("model", StringUtil.subModelFromCode(item.getProductCode()));
						i.put("color", StringUtil.getColorName(StringUtil.subColorFromCode(item.getProductCode())));
					}
					if (item.getProductName() != null) {
						i.put("prodname", item.getProductName());
					}
					if (item.getProductPrice() != null) {
						i.put("prodprice", item.getProductPrice().floatValue());
					}
					if (item.getProductTotal() != null) {
						i.put("prodtotal", item.getProductTotal());
					}
					if (item.getPayFee() != null) {
						i.put("payfee", item.getPayFee().toString());
					}
					if (item.getStatus() != null) {
						i.put("checked", item.getStatus() == 1 ? Boolean.FALSE : Boolean.TRUE);
					}
					instances.add(i);
				});
			}
			message = respWriter.toSuccess(new PageModel(page, instances), sn);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

}
