package com.chinamcom.framework.backstage.service.impl;

import com.chinamcom.framework.backstage.common.PageModel;
import com.chinamcom.framework.backstage.mapper.*;
import com.chinamcom.framework.backstage.model.*;
import com.chinamcom.framework.backstage.reply.Reply;
import com.chinamcom.framework.backstage.service.AbstractBackstageService;
import com.chinamcom.framework.backstage.service.IAfterSaleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("afterSaleService")
public class AfterSaleServiceImpl extends AbstractBackstageService implements IAfterSaleService {

    @Autowired
    private TbAftersaleMapper tbAfterSaleMapper;

    @Autowired
    private TbAftersaleProductMapper tbAfterSaleProductMapper;

    @Autowired
    private TbAftersaleLogisticsMapper tbAfterSaleLogisticsMapper;

    @Autowired
    private TbAftersaleImageboxMapper tbAfterSaleImageboxMapper;

    @Autowired
    private TbAftersaleReplyMapper tbAftersaleReplyMapper;

    @Override
    public JsonObject selectTbAfterSale(JsonObject param) {
        JsonObject message = new JsonObject();
        try {
            JsonArray result = new JsonArray();
            Integer uid = param.getInteger("uid");
            Integer pageNum = Optional.ofNullable(param.getInteger("pageNum")).orElse(1);// 页码
            Integer pageSize = Optional.ofNullable(param.getInteger("pageSize")).orElse(10);// 每页显示条数

            String status = Optional.ofNullable(param.getString("status")).orElse("9");// 状态9：所有
            String type = Optional.ofNullable(param.getString("type")).orElse("9");// 状态9：所有
            String code = param.getString("code");


            TbAftersaleExample example = new TbAftersaleExample();
            TbAftersaleExample.Criteria criteria = example.createCriteria();
            if (uid != 0) {
                criteria.andUidEqualTo(uid);
            }
            if (!status.equals("9")) {
                criteria.andStatusEqualTo(Integer.valueOf(status));
            }
            if (!type.equals("9")) {
                criteria.andAftertypeEqualTo(Integer.valueOf(type));
            }
            if (code != null && code != "") {
                criteria.andAftercodeEqualTo(code);
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String beginDate = simpleDateFormat.format(simpleDateFormat.parse(param.getString("beginDate"))) + " 00:00:00";
            String endDate = simpleDateFormat.format(simpleDateFormat.parse(param.getString("endDate"))) + " 23:59:59";
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            criteria.andCreateTimeBetween(simpleDateFormat.parse(beginDate), simpleDateFormat.parse(endDate));
            example.setOrderByClause("create_time desc");
            Page<Object> page = PageHelper.startPage(pageNum, pageSize);
            List<TbAftersale> listTbAftersale = tbAfterSaleMapper.selectByExample(example);
            if (!listTbAftersale.isEmpty()) {
                listTbAftersale.forEach(item -> {
                    JsonObject sale = new JsonObject();
                    sale.put("aftercode", item.getAftercode());
                    sale.put("applytime", item.getCreateTime().getTime());
                    sale.put("aftertype", item.getAftertype() == 1 ? "退货" : item.getAftertype() == 2 ? "换货" : "维修");
                    sale.put("status", item.getStatus());
                    TbAftersaleProductExample pexample = new TbAftersaleProductExample();
                    pexample.createCriteria().andAfterCodeEqualTo(item.getAftercode());
                    List<TbAftersaleProduct> listTbAftersaleProduct = tbAfterSaleProductMapper.selectByExample(pexample);
                    if (!listTbAftersaleProduct.isEmpty()) {
                        TbAftersaleProduct product = listTbAftersaleProduct.get(0);
                        sale.put("prodname", product.getProductName());
                        sale.put("prodcode", product.getProductCode());
                        sale.put("total", product.getQuantity().intValue());
                    }
                    TbAftersaleLogisticsExample lexample = new TbAftersaleLogisticsExample();
                    lexample.createCriteria().andAfterCodeEqualTo(item.getAftercode());
                    List<TbAftersaleLogistics> listTbAftersaleLogistics = tbAfterSaleLogisticsMapper.selectByExampleWithExpress(lexample);
                    if (!listTbAftersaleLogistics.isEmpty()) {
                        TbAftersaleLogistics l = listTbAftersaleLogistics.get(0);
                        sale.put("customer", new JsonObject().put("name", l.getName()).put("phone", l.getPhone())
                                .put("address", l.getProvince() + l.getCity() + l.getArea() + l.getAddress()).put("zipcode", l.getZipcode()));
                    }
                    result.add(sale);
                });
                message = new Reply(Response.Status.OK.getStatusCode(), "successed", new PageModel(page, result))
                        .toJson();
            } else {
                message = new Reply(Response.Status.NOT_FOUND.getStatusCode(), "No Data.", null).toJson();
            }
        } catch (Exception e) {
            log.error("检索售后列表异常：" + e);
            message = new Reply(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "error", new JsonObject())
                    .toJson();
        }
        log.info("response:" + message);
        return message;
    }

    @Override
    public JsonObject selectTbAfterSaleInfo(JsonObject param) {
        JsonObject message = new JsonObject();
        try {
            JsonObject sale = new JsonObject();
            TbAftersaleExample example = new TbAftersaleExample();
            TbAftersaleExample.Criteria criteria = example.createCriteria();
            criteria.andAftercodeEqualTo(param.getString("aftercode"));
            List<TbAftersale> listTbAftersale = tbAfterSaleMapper.selectByExample(example);
            if (!listTbAftersale.isEmpty()) {
                TbAftersale item = listTbAftersale.get(0);
                sale.put("ordercode",item.getOrderCode());
                sale.put("aftercode", item.getAftercode());
                sale.put("applytime", item.getCreateTime().getTime());
                sale.put("handletime", item.getHandleTime() != null ? item.getHandleTime().getTime() : null);
                sale.put("finishtime", item.getFinishTime() != null ? item.getFinishTime().getTime() : null);
                sale.put("updatetime", item.getUpdateTime() != null ? item.getUpdateTime().getTime() : null);
                sale.put("replenishtime", item.getReplenishTime() != null ? item.getReplenishTime().getTime() : null);
                sale.put("logisticstime", item.getLogisticsTime() != null ? item.getLogisticsTime().getTime() : null);
                sale.put("aftertype", item.getAftertype() == 1 ? "退货" : item.getAftertype() == 2 ? "换货" : "维修");
                sale.put("producttype", item.getProductType());
                sale.put("status", item.getStatus());
                sale.put("reply", item.getReply());
                sale.put("reason", item.getReason());
                sale.put("remark", item.getRemark());
                TbAftersaleProductExample pexample = new TbAftersaleProductExample();
                pexample.createCriteria().andAfterIdEqualTo(item.getId());
                List<TbAftersaleProduct> listTbAftersaleProduct = tbAfterSaleProductMapper.selectByExample(pexample);
                if (!listTbAftersaleProduct.isEmpty()) {
                    TbAftersaleProduct product = listTbAftersaleProduct.get(0);
                    sale.put("prodname", product.getProductName());
                    sale.put("prodcode", product.getProductCode());
                    sale.put("total", product.getQuantity().intValue());
                    if (product.getSignTime() != null) {
                        sale.put("signtime", product.getSignTime().getTime());
                    }
                    sale.put("prodprice", product.getRemark() != null ? product.getRemark() : "0.00");
                    sale.put("devicecode", product.getDeviceCode());
                }
                TbAftersaleImageboxExample iexample = new TbAftersaleImageboxExample();
                iexample.createCriteria().andAfterCodeEqualTo(item.getAftercode());
                List<TbAftersaleImagebox> listTbAftersaleImagebox = tbAfterSaleImageboxMapper.selectByExample(iexample);
                if (!listTbAftersaleImagebox.isEmpty()) {
                    JsonArray images = new JsonArray();
                    listTbAftersaleImagebox.forEach(image -> {
                        images.add(image.getImageUri());
                    });
                    sale.put("images", images);
                }
                TbAftersaleLogisticsExample lexample = new TbAftersaleLogisticsExample();
                lexample.createCriteria().andAfterCodeEqualTo(item.getAftercode());
                List<TbAftersaleLogistics> listTbAftersaleLogistics = tbAfterSaleLogisticsMapper.selectByExampleWithExpress(lexample);
                if (!listTbAftersaleLogistics.isEmpty()) {
                    TbAftersaleLogistics l = listTbAftersaleLogistics.get(0);
                    sale.put("customer", new JsonObject().put("name", l.getName()).put("phone", l.getPhone())
                            .put("address", l.getProvince() + l.getCity() + l.getArea() + l.getAddress()).put("zipcode", l.getZipcode()));
                }
                TbAftersaleLogisticsExample tbAftersaleLogisticsExample = new TbAftersaleLogisticsExample();
                tbAftersaleLogisticsExample.createCriteria().andAfterCodeEqualTo(item.getAftercode());
                List<TbAftersaleLogistics> tbAftersaleLogistics = tbAfterSaleLogisticsMapper.selectByExample(tbAftersaleLogisticsExample);
                if (tbAftersaleLogistics != null && tbAftersaleLogistics.size() > 0) {
                    TbAftersaleLogistics tbAftersaleLogistics1 = tbAftersaleLogistics.get(0);
                    sale.put("logisticScode", tbAftersaleLogistics1.getLogisticsCode());
                    sale.put("logisticName", tbAftersaleLogistics1.getLogisticsStatus());
                }

                TbAftersaleReplyExample tbAftersaleReplyExample = new TbAftersaleReplyExample();
                tbAftersaleReplyExample.createCriteria().andAftercodeEqualTo(item.getAftercode());
                List<TbAftersaleReply> tbAftersaleReplyList = tbAftersaleReplyMapper.selectByExample(tbAftersaleReplyExample);
                if (tbAftersaleReplyList != null && tbAftersaleReplyList.size() > 0) {
                    JsonArray replys = new JsonArray();
                    tbAftersaleReplyList.forEach(tbAftersaleReply -> {
                        JsonObject reply = new JsonObject();
                        reply.put("from", tbAftersaleReply.getStatusFrom());
                        reply.put("to", tbAftersaleReply.getStatusTo());
                        reply.put("createTime", tbAftersaleReply.getCreateTime().getTime());
                        reply.put("reply", tbAftersaleReply.getReply());
                        replys.add(reply);
                    });
                    sale.put("replyResult", replys);
                }
                message = new Reply(Response.Status.OK.getStatusCode(), "successed", sale).toJson();
            } else {
                message = new Reply(Response.Status.NOT_FOUND.getStatusCode(), "No Data.", sale).toJson();
            }
        } catch (Exception e) {
            log.error("检索售后详情异常：" + e);
            message = new Reply(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "error", null).toJson();
        }
        log.info("response:" + message);
        return message;
    }

    @Override
    public JsonObject handleTbAfterSale(JsonObject param) {
        JsonObject message = new JsonObject();
        try {
            String reply = param.getString("reply");
            String aftercode = param.getString("aftercode");
            Integer result = Integer.valueOf(param.getString("result"));

            TbAftersaleExample tbAftersaleExample = new TbAftersaleExample();
            tbAftersaleExample.createCriteria().andAftercodeEqualTo(aftercode);
            List<TbAftersale> tbAftersaleList = tbAfterSaleMapper.selectByExample(tbAftersaleExample);
            tbAftersaleList.forEach(item -> {
                //更新售后的反馈信息
                TbAftersaleReply tbAftersaleReply = new TbAftersaleReply();
                tbAftersaleReply.setAftercode(item.getAftercode());
                tbAftersaleReply.setAfterid(item.getId());
                tbAftersaleReply.setStatusFrom(item.getStatus());
                tbAftersaleReply.setStatusTo(result);
                tbAftersaleReply.setReply(reply);
                tbAftersaleReply.setCreateTime(new Date());
                tbAftersaleReplyMapper.insertSelective(tbAftersaleReply);
                //更新售后信息
                TbAftersale tbAftersale = new TbAftersale();
                tbAftersale.setStatus(result);
                tbAftersale.setUpdateTime(new Date());

                TbAftersaleExample tbAftersaleExample1 = new TbAftersaleExample();
                tbAftersaleExample1.createCriteria().andAftercodeEqualTo(aftercode);
                tbAfterSaleMapper.updateByExampleSelective(tbAftersale, tbAftersaleExample1);
            });
            message = new Reply(Response.Status.OK.getStatusCode(), "successed", null).toJson();
        } catch (Exception e) {
            log.error("处理售后列表异常：" + e);
            message = new Reply(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "error", null).toJson();
        }
        log.info("response:" + message);
        return message;
    }

}
