package com.chinamcom.framework.aftersale.service.impl;

import com.chinamcom.framework.aftersale.mapper.*;
import com.chinamcom.framework.aftersale.model.*;
import com.chinamcom.framework.aftersale.service.IAfterSaleService;
import com.chinamcom.framework.common.model.PageModel;
import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.service.BaseService;
import com.chinamcom.framework.common.util.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("afterSaleService")
public class AfterSaleServiceImpl extends BaseService implements IAfterSaleService {

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
    public String selectTbAfterSale(String sn, JsonObject param) {
        String message = respWriter.toError(sn, RespCode.CODE_504);
        try {
            JsonArray result = new JsonArray();
            Integer uid = param.getInteger("uid");
            Integer pageNum = Optional.ofNullable(param.getInteger("pageNum")).orElse(1);// 页码
            Integer pageSize = Optional.ofNullable(param.getInteger("pageSize")).orElse(Integer.MAX_VALUE);// 每页显示条数

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
                    List<TbAftersaleProduct> listTbAftersaleProduct = tbAfterSaleProductMapper
                            .selectByExample(pexample);
                    if (!listTbAftersaleProduct.isEmpty()) {
                        TbAftersaleProduct product = listTbAftersaleProduct.get(0);
                        sale.put("prodname", product.getProductName());
                        sale.put("prodcode", product.getProductCode());
                        sale.put("total", product.getQuantity().intValue());
                    }
                    TbAftersaleLogisticsExample lexample = new TbAftersaleLogisticsExample();
                    lexample.createCriteria().andAfterCodeEqualTo(item.getAftercode());
                    List<TbAftersaleLogistics> listTbAftersaleLogistics = tbAfterSaleLogisticsMapper
                            .selectByExampleWithExpress(lexample);
                    if (!listTbAftersaleLogistics.isEmpty()) {
                        TbAftersaleLogistics l = listTbAftersaleLogistics.get(0);
                        sale.put("customer", new JsonObject().put("name", l.getName()).put("phone", l.getPhone())
                                .put("address", l.getProvince() + l.getCity() + l.getArea() + l.getAddress()));
                    }
                    result.add(sale);
                });
                message = respWriter.toSuccess(new PageModel(page, result), sn);
            }
        } catch (Exception e) {
            throw e;
        }
        return message;
    }

    @Override
    public void selectTbAfterSaleInfo(String sn, JsonObject param, JsonObject sale) {
        try {
            Integer uid = param.getInteger("uid");
            Optional<Integer> afterid = Optional.ofNullable(param.getInteger("afterid"));
            Optional<String> aftercode = Optional.ofNullable(param.getString("aftercode"));
            TbAftersaleExample example = new TbAftersaleExample();
            TbAftersaleExample.Criteria criteria = example.createCriteria();
            criteria.andUidEqualTo(uid);
            if (afterid.isPresent()) {
                criteria.andIdEqualTo(afterid.get());
            }
            if (aftercode.isPresent()) {
                criteria.andAftercodeEqualTo(aftercode.get());
            }
            List<TbAftersale> listTbAftersale = tbAfterSaleMapper.selectByExample(example);
            if (!listTbAftersale.isEmpty()) {
                TbAftersale item = listTbAftersale.get(0);
                sale.put("ordercode", item.getOrderCode());
                sale.put("aftercode", item.getAftercode());
                sale.put("applytime", item.getCreateTime().getTime());
                sale.put("handletime", item.getHandleTime() != null ? item.getHandleTime().getTime() : null);
                sale.put("finishtime", item.getFinishTime() != null ? item.getFinishTime().getTime() : null);
                sale.put("updatetime", item.getUpdateTime() != null ? item.getUpdateTime().getTime() : null);
                sale.put("logisticstime", item.getLogisticsTime() != null ? item.getUpdateTime().getTime() : null);
                sale.put("replenishtime", item.getReplenishTime() != null ? item.getUpdateTime().getTime() : null);
                sale.put("aftertype", item.getAftertype());
                sale.put("status", item.getStatus());
                sale.put("reply", item.getReply());
                sale.put("remark", item.getRemark());
                sale.put("reason", item.getReason());
                sale.put("producttype", item.getProductType());
                TbAftersaleProductExample pexample = new TbAftersaleProductExample();
                pexample.createCriteria().andAfterCodeEqualTo(item.getAftercode());
                List<TbAftersaleProduct> listTbAftersaleProduct = tbAfterSaleProductMapper.selectByExample(pexample);
                if (!listTbAftersaleProduct.isEmpty()) {
                    TbAftersaleProduct product = listTbAftersaleProduct.get(0);
                    JsonObject detail = new JsonObject();
                    sale.put("prodname", product.getProductName());
                    sale.put("prodcode", product.getProductCode());
                    sale.put("total", product.getQuantity().intValue());
                    if (product.getSignTime() != null) {
                        sale.put("signtime", product.getSignTime().getTime());
                    }
                    if (product.getDeviceCode() != null) {
                        sale.put("devicecode", product.getDeviceCode());
                    }
                    if (product.getRemark() != null) {
                        sale.put("prodprice", product.getRemark());
                    }
                    //sale.put("detail", detail);
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
                TbAftersaleLogisticsExample logistics = new TbAftersaleLogisticsExample();
                TbAftersaleLogisticsExample.Criteria logCriteria = logistics.createCriteria();
                if (afterid.isPresent()) {
                    logCriteria.andIdEqualTo(afterid.get());
                }
                if (aftercode.isPresent()) {
                    logCriteria.andAfterCodeEqualTo(aftercode.get());
                }
                List<TbAftersaleLogistics> logisticsData = tbAfterSaleLogisticsMapper.selectByExample(logistics);
                if (logisticsData != null && logisticsData.size() > 0) {
                    TbAftersaleLogistics logist = logisticsData.get(0);
                    JsonObject l = new JsonObject();
                    l.put("express", logist.getExpressId());
                    l.put("logisticsCode", logist.getLogisticsCode());
                    l.put("logisticsName", logist.getLogisticsStatus());
                    sale.put("logistics", l);
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
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public String updateSecond(String sn, JsonObject param) {
        String message = respWriter.toError(sn);
        try {
            Integer uid = param.getInteger("uid");
            String aftercode = param.getString("aftercode");// 售后编号
            // 先检索售后编号是否存在
            TbAftersaleExample tbAftersaleExample = new TbAftersaleExample();
            tbAftersaleExample.createCriteria().andAftercodeEqualTo(aftercode).andUidEqualTo(uid);
            List<TbAftersale> exists = tbAfterSaleMapper.selectByExample(tbAftersaleExample);
            if (exists != null && exists.size() > 0) {
                TbAftersale tbAftersale = exists.get(0);
                TbAftersale sale = new TbAftersale();
                if (param.containsKey("producttype")) {
                    sale.setProductType(param.getJsonObject("producttype").encode());
                }
                if (param.containsKey("reason")) {
                    sale.setReason(param.getString("reason"));
                }
                if (param.containsKey("remark")) {
                    sale.setRemark(param.getString("remark"));
                }
                sale.setUpdateTime(new Date());
                sale.setStatus(0);
                tbAfterSaleMapper.updateByExampleSelective(sale, tbAftersaleExample);
                LOG.info("update aftersale succeed.");
                //处理售后产品图片
                if (param.containsKey("imageboxs")) {
                    JsonArray imageboxs = param.getJsonArray("imageboxs");// 图片信息
                    TbAftersaleImageboxExample deleteExample = new TbAftersaleImageboxExample();
                    deleteExample.createCriteria().andAfterCodeEqualTo(aftercode);
                    tbAfterSaleImageboxMapper.deleteByExample(deleteExample);
                    imageboxs.forEach(image -> {
                        TbAftersaleImagebox imagebox = new TbAftersaleImagebox();
                        imagebox.setImageUri(image.toString());
                        imagebox.setAfterCode(aftercode);
                        tbAfterSaleImageboxMapper.insertSelective(imagebox);
                    });
                    LOG.info("build aftersale_imagebox succeed.");
                }
                // 处理物流信息
                if (param.containsKey("express")) {
                    Integer expressId = param.getInteger("express");// 收货ID
                    TbAftersaleLogistics logistics = new TbAftersaleLogistics();
                    logistics.setExpressId(expressId);
                    if (param.containsKey("logisticsCode")) {
                        logistics.setLogisticsCode(param.getString("logisticsCode"));
                    }
                    if (param.containsKey("logisticsName")) {
                        logistics.setLogisticsStatus(param.getString("logisticsName"));
                    }
                    TbAftersaleLogisticsExample updateExample = new TbAftersaleLogisticsExample();
                    updateExample.createCriteria().andAfterCodeEqualTo(aftercode);
                    tbAfterSaleLogisticsMapper.updateByExampleSelective(logistics, updateExample);
                    LOG.info("build aftersale_logistics succeed.");
                }
                //更新售后的反馈信息
                TbAftersaleReply tbAftersaleReply = new TbAftersaleReply();
                tbAftersaleReply.setAftercode(tbAftersale.getAftercode());
                tbAftersaleReply.setAfterid(tbAftersale.getId());
                tbAftersaleReply.setStatusFrom(tbAftersale.getStatus());
                tbAftersaleReply.setStatusTo(0);
                tbAftersaleReply.setReply("用户更新完资料，重新申请售后");
                tbAftersaleReply.setCreateTime(new Date());
                tbAftersaleReplyMapper.insertSelective(tbAftersaleReply);
                message = respWriter.toSuccess(new JsonObject().put("code", aftercode), sn);
            } else {
                LOG.info("售后编号【" + aftercode + "】不存在");
                message = respWriter.toError(sn, "系统错误.");
            }
        } catch (Exception e) {
            throw e;
        }
        return message;
    }

    @Override
    public String updateTbAfterSale(String sn, JsonObject param) {
        String message = respWriter.toError(sn);
        try {
            Integer uid = param.getInteger("uid");
            String aftercode = param.getString("aftercode");// 售后编号
            // 先检索售后编号是否存在
            TbAftersaleExample tbAftersaleExample = new TbAftersaleExample();
            tbAftersaleExample.createCriteria().andAftercodeEqualTo(aftercode).andUidEqualTo(uid);
            List<TbAftersale> exists = tbAfterSaleMapper.selectByExample(tbAftersaleExample);
            if (exists != null && exists.size() > 0) {
                TbAftersale sale = new TbAftersale();
                if (param.containsKey("producttype")) {
                    sale.setProductType(param.getJsonObject("producttype").encode());
                }
                if (param.containsKey("reason")) {
                    sale.setReason(param.getString("reason"));
                }
                if (param.containsKey("remark")) {
                    sale.setRemark(param.getString("remark"));
                }
                sale.setUpdateTime(new Date());
                tbAfterSaleMapper.updateByExampleSelective(sale, tbAftersaleExample);
                LOG.info("update aftersale succeed.");
                //处理售后产品图片
                if (param.containsKey("imageboxs")) {
                    JsonArray imageboxs = param.getJsonArray("imageboxs");// 图片信息
                    TbAftersaleImageboxExample deleteExample = new TbAftersaleImageboxExample();
                    deleteExample.createCriteria().andAfterCodeEqualTo(aftercode);
                    tbAfterSaleImageboxMapper.deleteByExample(deleteExample);
                    imageboxs.forEach(image -> {
                        TbAftersaleImagebox imagebox = new TbAftersaleImagebox();
                        imagebox.setImageUri(image.toString());
                        imagebox.setAfterCode(aftercode);
                        tbAfterSaleImageboxMapper.insertSelective(imagebox);
                    });
                    LOG.info("build aftersale_imagebox succeed.");
                }
                // 处理物流信息
                if (param.containsKey("express")) {
                    Integer expressId = param.getInteger("express");// 收货ID
                    TbAftersaleLogistics logistics = new TbAftersaleLogistics();
                    logistics.setExpressId(expressId);
                    if (param.containsKey("logisticsCode")) {
                        logistics.setLogisticsCode(param.getString("logisticsCode"));
                    }
                    if (param.containsKey("logisticsName")) {
                        logistics.setLogisticsStatus(param.getString("logisticsName"));
                    }
                    TbAftersaleLogisticsExample updateExample = new TbAftersaleLogisticsExample();
                    updateExample.createCriteria().andAfterCodeEqualTo(aftercode);
                    tbAfterSaleLogisticsMapper.updateByExampleSelective(logistics, updateExample);
                    LOG.info("build aftersale_logistics succeed.");
                }

                TbAftersale status = new TbAftersale();
                Date date = new Date();
                status.setUpdateTime(date);
                status.setLogisticsTime(date);
                status.setStatus(2);
                tbAfterSaleMapper.updateByExampleSelective(status, tbAftersaleExample);
                message = respWriter.toSuccess(new JsonObject().put("code", aftercode), sn);
            } else {
                LOG.info("售后编号【" + aftercode + "】不存在");
                message = respWriter.toError(sn, "系统错误.");
            }
        } catch (Exception e) {
            throw e;
        }
        return message;
    }

    @Override
    public String submitTbAfterSale(String sn, JsonObject param) {
        String message = respWriter.toError(sn);
        try {
            Integer uid = param.getInteger("uid");
            Integer type = param.getInteger("type");// 1退货2换货3维修
            Integer orderid = param.getInteger("orderid");// 订单ID
            String ordercode = param.getString("ordercode");// 订单编号
            String channel = Optional.ofNullable(param.getString("channel")).orElse("pc");
            JsonArray products = param.getJsonArray("prods");// 商品信息
            Optional<JsonArray> imageboxs = Optional.ofNullable(param.getJsonArray("imageboxs"));// 图片信息
            String prodcode = products.getJsonObject(0).getString("code");

            // 先检索此订单是否已经申请过售后
            TbAftersaleExample tbAftersaleExample = new TbAftersaleExample();
            TbAftersaleExample.Criteria criteria = tbAftersaleExample.createCriteria();
            if (orderid != null) {
                criteria.andOrderIdEqualTo(orderid);
            }
            if (ordercode != null) {
                criteria.andOrderCodeEqualTo(ordercode);
            }
            criteria.andProductCodeEqualTo(prodcode);
            tbAftersaleExample.setOrderByClause("id desc");
            List<TbAftersale> exists = tbAfterSaleMapper.selectByExample(tbAftersaleExample);
            boolean comeon = true;
            if (exists != null && exists.size() > 0) {
                if (!(exists.get(0).getStatus() == 3 || exists.get(0).getStatus() == 4)) {
                    comeon = false;
                }
            }
            if (comeon) {
                TbAftersale sale = new TbAftersale();
                sale.setAftercode(StringUtil.getAfterSaleCode());
                sale.setAftertype(type);
                sale.setUid(uid);
                sale.setOrderId(orderid);
                sale.setOrderCode(ordercode);
                sale.setProductCode(prodcode);
                if (param.containsKey("producttype")) {
                    sale.setProductType(param.getJsonObject("producttype").encode());
                }
                if (param.containsKey("reason")) {
                    sale.setReason(param.getString("reason"));
                }
                if (param.containsKey("remark")) {
                    sale.setRemark(param.getString("remark"));
                }
                sale.setChannel(channel);
                tbAfterSaleMapper.insertSelective(sale);
                LOG.info("build aftersale succeed.");
                LOG.info("afterid:" + sale.getId());
                //处理售后商品信息
                products.forEach(item -> {
                    JsonObject prod = (JsonObject) item;
                    TbAftersaleProduct product = new TbAftersaleProduct();
                    product.setAfterId(sale.getId());
                    product.setAfterCode(sale.getAftercode());
                    if (prod.containsKey("code")) {
                        product.setProductCode(prod.getString("code"));
                    }
                    if (prod.containsKey("name")) {
                        product.setProductName(prod.getString("name"));
                    }
                    if (prod.containsKey("price")) {
                        product.setRemark(prod.getDouble("price").toString());
                    }
                    product.setCreateTime(new Date());
                    product.setQuantity(param.containsKey("total") ? param.getInteger("total") : 1);
                    tbAfterSaleProductMapper.insertSelective(product);
                    LOG.info("build aftersale_product succeed.");
                });
                //处理售后产品图片
                if (imageboxs.isPresent()) {
                    imageboxs.get().forEach(image -> {
                        TbAftersaleImagebox imagebox = new TbAftersaleImagebox();
                        imagebox.setImageUri(image.toString());
                        imagebox.setAfterId(sale.getId());
                        imagebox.setAfterCode(sale.getAftercode());
                        tbAfterSaleImageboxMapper.insertSelective(imagebox);
                        LOG.info("build aftersale_imagebox succeed.");
                    });
                }
                // 处理物流信息
                Integer expressId = param.getInteger("express");// 收货ID
                TbAftersaleLogistics logistics = new TbAftersaleLogistics();
                logistics.setAfterId(sale.getId());
                logistics.setAfterCode(sale.getAftercode());
                if (param.containsKey("express")) {
                    logistics.setExpressId(expressId);
                }
                tbAfterSaleLogisticsMapper.insertSelective(logistics);
                LOG.info("build aftersale_logistics succeed.");
                message = respWriter.toSuccess(new JsonObject().put("code", sale.getAftercode()), sn);
            } else {
                LOG.info("订单【" + ordercode + "】，已申请过售后，不能重复申请");
                message = respWriter.toError(sn, RespCode.CODE_509);
            }
        } catch (Exception e) {
            throw e;
        }
        return message;
    }

    @Override
    public String submitTbAfterSaleByDevice(String sn, JsonObject param) {
        String message = respWriter.toError(sn);
        try {
            Integer uid = param.getInteger("uid");
            Integer type = param.getInteger("type");// 1退货2换货3维修
            String devicecode = param.getString("devicescode");// 设备编号
            Long signtime = param.getLong("signtime");// 签收日期
            String channel = Optional.ofNullable(param.getString("channel")).orElse("pc");
            JsonObject products = param.getJsonObject("prods");// 商品信息
            Optional<JsonArray> imageboxs = Optional.ofNullable(param.getJsonArray("imageboxs"));// 图片信息
            TbAftersale sale = new TbAftersale();
            sale.setAftercode(StringUtil.getAfterSaleCode());
            sale.setAftertype(type);
            sale.setUid(uid);
            sale.setProductCode(devicecode);
            if (param.containsKey("producttype")) {
                sale.setProductType(param.getJsonObject("producttype").encode());
            }
            if (param.containsKey("reason")) {
                sale.setReason(param.getString("reason"));
            }
            if (param.containsKey("remark")) {
                sale.setRemark(param.getString("remark"));
            }
            sale.setChannel(channel);
            tbAfterSaleMapper.insertSelective(sale);
            LOG.info("build aftersale succeed.");
            LOG.info("afterid:" + sale.getId());
            //处理售后产品信息
            TbAftersaleProduct product = new TbAftersaleProduct();
            product.setAfterId(sale.getId());
            product.setAfterCode(sale.getAftercode());
            if (products.containsKey("code")) {
                product.setProductCode(products.getString("code"));
            }
            if (products.containsKey("name")) {
                product.setProductName(products.getString("name"));
            }
            if (products.containsKey("price")) {
                product.setRemark(products.getFloat("price").toString());
            }
            product.setDeviceCode(devicecode);
            product.setSignTime(new Date(signtime));
            product.setCreateTime(new Date());
            product.setQuantity(products.containsKey("total") ? products.getInteger("total") : 1);
            tbAfterSaleProductMapper.insertSelective(product);
            LOG.info("build aftersale_product succeed.");
            //处理售后图片信息
            if (imageboxs.isPresent()) {
                imageboxs.get().forEach(image -> {
                    TbAftersaleImagebox imagebox = new TbAftersaleImagebox();
                    imagebox.setImageUri(image.toString());
                    imagebox.setAfterId(sale.getId());
                    imagebox.setAfterCode(sale.getAftercode());
                    tbAfterSaleImageboxMapper.insertSelective(imagebox);
                    LOG.info("build aftersale_imagebox succeed.");
                });
            }
            // 处理物流信息
            TbAftersaleLogistics logistics = new TbAftersaleLogistics();
            logistics.setAfterId(sale.getId());
            logistics.setAfterCode(sale.getAftercode());
            if (param.containsKey("express")) {
                logistics.setExpressId(param.getInteger("express"));
            }
            tbAfterSaleLogisticsMapper.insertSelective(logistics);
            LOG.info("build aftersale_logistics succeed.");
            message = respWriter.toSuccess(new JsonObject().put("code", sale.getAftercode()), sn);
        } catch (Exception e) {
            throw e;
        }
        return message;
    }

    @Override
    public String handleTbAfterSale(String sn, JsonObject param) {
        String message = respWriter.toError(sn);
        try {
            Optional<Integer> afterid = Optional.ofNullable(param.getInteger("afterid"));
            Optional<String> reply = Optional.ofNullable(param.getString("reply"));// 售后完成时的反馈信息
            Optional<String> aftercode = Optional.ofNullable(param.getString("aftercode"));
            Integer type = param.getInteger("type");
            TbAftersale updatesale = new TbAftersale();
            updatesale.setStatus(type);
            if (type == 1) {
                updatesale.setHandleTime(new Date());
            }
            if (type == 2) {
                updatesale.setFinishTime(new Date());
            }
            updatesale.setUpdateTime(new Date());
            if (reply.isPresent()) {
                updatesale.setReply(reply.get());
            }
            TbAftersaleExample example = new TbAftersaleExample();
            TbAftersaleExample.Criteria criteria = example.createCriteria();
            if (afterid.isPresent()) {
                criteria.andIdEqualTo(afterid.get());
            }
            if (aftercode.isPresent()) {
                criteria.andAftercodeEqualTo(aftercode.get());
            }
            tbAfterSaleMapper.updateByExampleSelective(updatesale, example);
            message = respWriter.toSuccess(sn);
        } catch (Exception e) {
            throw e;
        }
        return message;
    }

    @Override
    public String uploadLogistics(String sn, JsonObject param) {
        String message = respWriter.toError(sn);
        try {
            Integer uid = param.getInteger("uid");
            Optional<String> logisticsCode = Optional.ofNullable(param.getString("logisticsCode"));
            Optional<String> logisticsName = Optional.ofNullable(param.getString("logisticsName"));
            TbAftersaleExample example = new TbAftersaleExample();
            TbAftersaleExample.Criteria criteria = example.createCriteria();
            criteria.andUidEqualTo(uid);
            if (param.containsKey("afterid")) {
                criteria.andIdEqualTo(param.getInteger("afterid"));
            }
            if (param.containsKey("aftercode")) {
                criteria.andAftercodeEqualTo(param.getString("aftercode"));
            }
            List<TbAftersale> exist = tbAfterSaleMapper.selectByExample(example);
            if (exist != null && exist.size() > 0) {
                TbAftersale aftersale = exist.get(0);
                TbAftersaleLogisticsExample updateExample = new TbAftersaleLogisticsExample();
                updateExample.createCriteria().andAfterIdEqualTo(aftersale.getId());
                updateExample.createCriteria().andAfterCodeEqualTo(aftersale.getAftercode());
                TbAftersaleLogistics logistics = new TbAftersaleLogistics();
                if (param.containsKey("logisticsCode")) {
                    logistics.setLogisticsCode(param.getString("logisticsCode"));
                }
                if (param.containsKey("logisticsName")) {
                    logistics.setLogisticsStatus(param.getString("logisticsName"));
                }
                tbAfterSaleLogisticsMapper.updateByExampleSelective(logistics, updateExample);
                Date date = new Date();
                //更新售后的反馈信息
                TbAftersaleReply tbAftersaleReply = new TbAftersaleReply();
                tbAftersaleReply.setAftercode(aftersale.getAftercode());
                tbAftersaleReply.setAfterid(aftersale.getId());
                tbAftersaleReply.setStatusFrom(aftersale.getStatus());
                tbAftersaleReply.setStatusTo(2);
                tbAftersaleReply.setReply("用户提交物流信息，包括物流公司名称，物流单号");
                tbAftersaleReply.setCreateTime(date);
                tbAftersaleReplyMapper.insertSelective(tbAftersaleReply);
                //更新售后信息
                TbAftersale updatasale = new TbAftersale();
                updatasale.setStatus(2);
                updatasale.setUpdateTime(date);
                updatasale.setLogisticsTime(date);
                tbAfterSaleMapper.updateByExampleSelective(updatasale, example);
                message = respWriter.toSuccess(sn);
            } else {
                message = respWriter.toError(sn, "售后信息不存在.");
            }
        } catch (Exception e) {
            throw e;
        }
        return message;
    }

    @Override
    public String cancelAfterSale(String sn, JsonObject param) {
        String message = respWriter.toError(sn);
        try {
            Integer uid = param.getInteger("uid");
            TbAftersaleExample example = new TbAftersaleExample();
            TbAftersaleExample.Criteria criteria = example.createCriteria();
            criteria.andUidEqualTo(uid);
            if (param.containsKey("afterid")) {
                criteria.andIdEqualTo(param.getInteger("afterid"));
            }
            if (param.containsKey("aftercode")) {
                criteria.andAftercodeEqualTo(param.getString("aftercode"));
            }
            List<TbAftersale> exist = tbAfterSaleMapper.selectByExample(example);
            if (exist != null && exist.size() > 0) {
                TbAftersale tbAftersale = exist.get(0);
                TbAftersale sale = new TbAftersale();
                sale.setStatus(4);
                Date date = new Date();
                sale.setUpdateTime(date);
                sale.setFinishTime(date);
                tbAfterSaleMapper.updateByExampleSelective(sale, example);

                //更新售后的反馈信息
                TbAftersaleReply tbAftersaleReply = new TbAftersaleReply();
                tbAftersaleReply.setAftercode(tbAftersale.getAftercode());
                tbAftersaleReply.setAfterid(tbAftersale.getId());
                tbAftersaleReply.setStatusFrom(tbAftersale.getStatus());
                tbAftersaleReply.setStatusTo(4);
                tbAftersaleReply.setReply("用户主动取消申请售后");
                tbAftersaleReply.setCreateTime(date);
                tbAftersaleReplyMapper.insertSelective(tbAftersaleReply);

                message = respWriter.toSuccess(sn);
            } else {
                message = respWriter.toError(sn, "售后信息不存在.");
            }
        } catch (Exception e) {
            throw e;
        }
        return message;
    }
}
