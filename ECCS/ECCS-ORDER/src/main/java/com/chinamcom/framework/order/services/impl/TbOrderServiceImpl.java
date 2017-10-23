package com.chinamcom.framework.order.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.chinamcom.framework.order.mapper.TbOrderLogisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.common.constant.Constant;
import com.chinamcom.framework.common.model.PageModel;
import com.chinamcom.framework.common.service.BaseService;
import com.chinamcom.framework.common.util.StringUtil;
import com.chinamcom.framework.order.mapper.TbOrderInstanceMapper;
import com.chinamcom.framework.order.mapper.TbOrderMapper;
import com.chinamcom.framework.order.model.TbOrder;
import com.chinamcom.framework.order.model.TbOrderExample;
import com.chinamcom.framework.order.model.TbOrderExample.Criteria;
import com.chinamcom.framework.order.model.TbOrderInstance;
import com.chinamcom.framework.order.model.TbOrderInstanceExample;
import com.chinamcom.framework.order.services.ITbOrderService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

@Service("tbOrderService")
public class TbOrderServiceImpl extends BaseService implements ITbOrderService {

    @Autowired
    private TbOrderMapper tbOrderMapper;
    @Autowired
    private TbOrderInstanceMapper tbOrderInstanceMapper;
    @Autowired
    private TbOrderLogisticsMapper tbOrderLogisticsMapper;

    @Override
    public String selectTbOrderList(String sn, JsonObject param) {
        String message = respWriter.toError(sn);
        List<JsonObject> result = new ArrayList<JsonObject>();
        try {
            Optional<Integer> uid = Optional.ofNullable(param.getInteger("uid"));// 用户ID
            Integer pageNum = Optional.ofNullable(param.getInteger("pageNum")).orElse(1);// 页码
            Integer pageSize = Optional.ofNullable(param.getInteger("pageSize")).orElse(Integer.MAX_VALUE);// 每页显示条数

            TbOrderExample example = new TbOrderExample();
            Criteria criteria = example.createCriteria();
            if (uid.isPresent()) {
                criteria.andUserIdEqualTo(uid.get());
            }
            example.setOrderByClause("CREATE_TIME DESC");
            Page<Object> page = PageHelper.startPage(pageNum, pageSize);
            List<TbOrder> orders = tbOrderMapper.selectByExample(example);
            if (!orders.isEmpty()) {
                orders.forEach(item -> {
                    JsonObject reply_order = new JsonObject();
                    reply_order.put("oid", item.getId());// 订单ID
                    reply_order.put("code", item.getSerialNumber());// 订单编号
                    reply_order.put("ctime", item.getCreateTime().getTime());// 下单时间
                    reply_order.put("status", item.getStatus());// 订单状态
                    reply_order.put("logistics_name", StringUtil.getlogisticsName(item.getLogisticsCdoe()));// 物流公司
                    reply_order.put("logistics_code", item.getLogisticsNumber());// 物流单号
                    reply_order.put("express_pay", item.getExpressPay().floatValue());// 运费
                    reply_order.put("product_pay", item.getFee().floatValue());// 产品总额
                    reply_order.put("pay", item.getPayFee().floatValue());// 总额（含运费）
                    reply_order.put("apply", item.getAftersaleCode());// 售后编号

                    JsonArray replyInstance = new JsonArray();
                    TbOrderInstanceExample instance = new TbOrderInstanceExample();
                    instance.createCriteria().andOrderIdEqualTo(item.getId());
                    List<TbOrderInstance> instances = tbOrderInstanceMapper.selectByExample(instance);
                    if (!instances.isEmpty()) {
                        instances.forEach(inst -> {
                            JsonObject i = new JsonObject();
                            i.put("name", inst.getProductName());
                            i.put("code", inst.getProductCode());
                            i.put("model", StringUtil.subModelFromCode(inst.getProductCode()));
                            i.put("color", StringUtil.getColorName(StringUtil.subColorFromCode(inst.getProductCode())));
                            i.put("total", inst.getProductTotal());
                            i.put("price", inst.getProductPrice().floatValue());
                            i.put("fee", inst.getPayFee().floatValue());
                            replyInstance.add(i);
                        });
                        reply_order.put("prods", replyInstance);
                    }
                    result.add(reply_order);
                });
            }
            message = respWriter.toSuccess(new PageModel(page, result), sn);
        } catch (Exception e) {
            throw e;
        }
        return message;
    }

    /**
     * 从购物车下订单
     *
     * @param sn
     * @param param
     * @return
     */
    @Override
    public String submitTbOrderInfoByCart(String sn, JsonObject param) {
        String message = respWriter.toError(sn);
        try {
            Date date = new Date();
            Integer uid = param.getInteger("uid");// 用户ID
            Integer expressid = param.getInteger("expressid");//收货地址
            String channel = Optional.ofNullable(param.getString("channel")).orElse("pc");//渠道
            String delivers = Optional.ofNullable(param.getString("delivers")).orElse("1");//送货时间
            String logistics = Optional.ofNullable(param.getString("logistics")).orElse("EMS");//物流公司代码
            // 查询购物车中待结算的产品
            TbOrderInstanceExample tbOrderInstanceExample = new TbOrderInstanceExample();
            tbOrderInstanceExample.createCriteria().andUserIdEqualTo(uid).andStatusEqualTo(2).andOrderIdIsNull();
            List<TbOrderInstance> listTbOrderInstance = tbOrderInstanceMapper.selectByExample(tbOrderInstanceExample);
            Integer order_type = 99;
            BigDecimal fee = new BigDecimal("0.00");// 订单原价
            BigDecimal pay_fee = new BigDecimal("0.00");// 实付金额
            BigDecimal reduce_fee = new BigDecimal("0.00");// 减免金额
            if (!listTbOrderInstance.isEmpty()) {
                for (int i = 0; i < listTbOrderInstance.size(); i++) {
                    TbOrderInstance instance = listTbOrderInstance.get(i);
                    BigDecimal price = instance.getProductPrice();
                    BigDecimal reducefee = instance.getReduceFee();
                    BigDecimal total = new BigDecimal(instance.getProductTotal());
                    BigDecimal i_all_payfee = price.multiply(total);// 商品总额
                    BigDecimal i_reducefee = reducefee.multiply(total);// 商品减免总额
                    BigDecimal i_payfee = i_all_payfee.subtract(i_reducefee);// 商品实付金额
                    fee = fee.add(i_payfee);
                    reduce_fee = reduce_fee.add(i_reducefee);
                }
                // 快递费用
                BigDecimal logisticsPay = StringUtil.getExpressPay(logistics);
                pay_fee = fee.subtract(reduce_fee).add(logisticsPay);
                String serial_number = StringUtil.getOrderCode();// 序列号
                // 生成订单
                TbOrder order = new TbOrder();
                LOG.info("******处理订单******");
                order.setSerialNumber(serial_number);
                order.setFee(fee);
                order.setPayFee(pay_fee);
                order.setReduceFee(reduce_fee);
                order.setOrderType(order_type);
                order.setChannel(channel);
                order.setStatus(Constant.ORDER_STATUS_NOPAY);
                order.setUserId(uid);
                order.setCreateTime(date);
                order.setExpressId(expressid);
                order.setLogisticsName(logistics);
                order.setLogisticsCdoe(logistics);
                order.setExpressPay(logisticsPay);
                order.setDeliversTime(delivers);
                if (param.containsKey("invoce")) {
                    order.setInvoceHead(param.getString("invoce"));
                }
                tbOrderMapper.insertSelective(order);
                LOG.info("order builder succeed.");
                Integer orderid = order.getId();
                LOG.info("orderid:" + orderid);
                // 更新购物车中待结算的产品
                TbOrderInstance record = new TbOrderInstance();
                record.setUpdateTime(new Date());
                record.setStatus(3);
                record.setOrderId(orderid);
                tbOrderInstanceMapper.updateByExampleSelective(record, tbOrderInstanceExample);
                LOG.info("order_instance update succeed. ");
                JsonObject result = new JsonObject();
                result.put("ordercode", order.getSerialNumber()).put("payfee", order.getPayFee().floatValue());
                message = respWriter.toSuccess(result, sn);
            } else {
                LOG.info("请先选中购物车的产品再提交订单.");
                message = respWriter.toError(sn, "订单生成失败.");
            }
        } catch (Exception e) {
            throw e;
        }
        return message;
    }

    /**
     * 从产品展览处直接下订单
     *
     * @param sn
     * @param param
     * @return
     */
    @Override
    public String submitTbOrderInfo(String sn, JsonObject param) {
        String message = respWriter.toError(sn);
        try {
            Date date = new Date();
            Integer uid = param.getInteger("uid");// 用户ID
            Integer expressid = param.getInteger("expressid");//收货地址
            String channel = Optional.ofNullable(param.getString("channel")).orElse("pc");//渠道
            String delivers = Optional.ofNullable(param.getString("delivers")).orElse("1");//送货时间
            String logistics = Optional.ofNullable(param.getString("logistics")).orElse("EMS");//物流公司代码
            JsonObject product = param.getJsonObject("product");// 产品信息

            Integer order_type = 99;
            BigDecimal price = new BigDecimal(product.getFloat("price"));
            BigDecimal reducefee = new BigDecimal("0.00");
            BigDecimal total = new BigDecimal(product.containsKey("total") ? product.getInteger("total") : 1);

            BigDecimal fee = price.multiply(total);// 商品总额
            BigDecimal payfee = fee.subtract(reducefee);// 商品实付金额
            BigDecimal logisticsPay = StringUtil.getExpressPay(logistics);
            String serial_number = StringUtil.getOrderCode();// 序列号
            // 生成订单
            TbOrder order = new TbOrder();
            LOG.info("******处理订单******");
            order.setSerialNumber(serial_number);
            order.setFee(fee);
            order.setPayFee(fee.add(logisticsPay));
            order.setReduceFee(reducefee);
            order.setOrderType(order_type);
            order.setChannel(channel);
            order.setStatus(Constant.ORDER_STATUS_NOPAY);
            order.setUserId(uid);
            order.setCreateTime(date);
            order.setExpressId(expressid);
            order.setLogisticsName(logistics);
            order.setLogisticsCdoe(logistics);
            order.setExpressPay(logisticsPay);
            order.setDeliversTime(delivers);
            if (param.containsKey("invoce")) {
                order.setInvoceHead(param.getString("invoce"));
            }
            tbOrderMapper.insertSelective(order);
            LOG.info("order builder succeed.");
            Integer orderid = order.getId();
            LOG.info("orderid:" + orderid);
            // 订单明细
            String product_code = product.getString("code");
            String product_name = product.getString("name");
            TbOrderInstance instance = new TbOrderInstance();
            instance.setOrderId(orderid);
            instance.setProductCode(product_code);
            instance.setProductName(product_name);
            instance.setProductPrice(price);
            instance.setProductTotal(total.intValue());
            instance.setPayFee(payfee);
            instance.setReduceFee(reducefee);
            instance.setCreateTime(date);
            instance.setStatus(Constant.INSTANCE_STATUS_YESORDER);
            instance.setUserId(uid);
            tbOrderInstanceMapper.insertSelective(instance);
            LOG.info("build order_instance succeed.");
            JsonObject result = new JsonObject();
            result.put("ordercode", order.getSerialNumber()).put("payfee", order.getPayFee().floatValue());
            message = respWriter.toSuccess(result, sn);
        } catch (Exception e) {
            throw e;
        }
        return message;
    }

    @Override
    public void selectTbOrderInfo(JsonObject param, JsonObject result) {
        try {
            Optional<Integer> orderid = Optional.ofNullable(param.getInteger("orderid"));// 订单ID
            Optional<String> ordercode = Optional.ofNullable(param.getString("ordercode"));
            Optional<String> prodcode = Optional.ofNullable(param.getString("prodcode"));
            TbOrderExample example = new TbOrderExample();
            Criteria criteria = example.createCriteria();
            if (orderid.isPresent()) {
                criteria.andIdEqualTo(orderid.get());
            }
            if (ordercode.isPresent()) {
                criteria.andSerialNumberEqualTo(ordercode.get());
            }
            List<TbOrder> lTbOrder = tbOrderMapper.selectByExample(example);
            if (!lTbOrder.isEmpty()) {
                TbOrder order = lTbOrder.get(0);
                result.put("oid", order.getId());// 订单ID
                result.put("code", order.getSerialNumber());// 订单编号
                result.put("ctime", order.getCreateTime().getTime());// 下单时间
                result.put("status", order.getStatus());// 订单状态
                result.put("ptime", order.getPayTime() != null ? order.getPayTime().getTime() : "");// 支付时间
                result.put("stime", order.getSendTime() != null ? order.getSendTime().getTime() : "");// 发货时间
                result.put("otime", order.getCompateTime() != null ? order.getCompateTime().getTime() : "");// 完成时间
                result.put("utime", order.getUpdateTime() != null ? order.getUpdateTime().getTime() : "");// 更新时间
                result.put("invoce", order.getInvoceHead());// 发票抬头
                result.put("devivers", order.getDeliversTime());// 送货时间
                result.put("logistics_name", StringUtil.getlogisticsName(order.getLogisticsName()));// 物流公司
                result.put("logistics_code", order.getLogisticsNumber());// 物流单号
                result.put("express", order.getExpressId());
                result.put("express_pay", order.getExpressPay().floatValue());// 运费
                result.put("product_pay", order.getFee().floatValue());// 产品总额
                result.put("pay", order.getPayFee().floatValue());// 总额（含运费）
                result.put("pay_type", order.getDescription());// 支付类型
                result.put("desc", order.getDescription());// 备注

                //支付信息
                JsonArray replyInstance = new JsonArray();
                TbOrderInstanceExample instance = new TbOrderInstanceExample();
                TbOrderInstanceExample.Criteria cri = instance.createCriteria();
                cri.andOrderIdEqualTo(order.getId());
                if (prodcode.isPresent()) {
                    cri.andProductCodeEqualTo(prodcode.get());
                }
                List<TbOrderInstance> instances = tbOrderInstanceMapper.selectByExample(instance);
                if (!instances.isEmpty()) {
                    instances.forEach(inst -> {
                        JsonObject i = new JsonObject();
                        i.put("name", inst.getProductName());
                        i.put("code", inst.getProductCode());
                        i.put("model", StringUtil.subModelFromCode(inst.getProductCode()));
                        i.put("color", StringUtil.getColorName(StringUtil.subColorFromCode(inst.getProductCode())));
                        i.put("total", inst.getProductTotal());
                        i.put("price", inst.getProductPrice().floatValue());
                        i.put("fee", inst.getPayFee().floatValue());
                        replyInstance.add(i);
                    });
                    result.put("prods", replyInstance);
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public String updateTbOrderInfo(String sn, JsonObject param) {
        String message = respWriter.toError(sn);
        List<TbOrder> result = new ArrayList<TbOrder>();
        try {
            Date date = new Date();
            Optional<Integer> orderid = Optional.ofNullable(param.getInteger("orderid"));// 订单ID
            Optional<String> ordercode = Optional.ofNullable(param.getString("ordercode"));// 订单编号
            Integer status = param.containsKey("status") ? param.getInteger("status") : 0;
            TbOrder record = new TbOrder();
            record.setUpdateTime(date);
            if (param.containsKey("desc")) {
                record.setDescription(param.getString("desc"));
            }
            switch (status) {
                case 1:// 待支付
                    record.setStatus(Constant.ORDER_STATUS_NOPAY);
                    break;
                case 2:// 支付成功，待发货
                    record.setStatus(Constant.ORDER_STATUS_NOSEND);
                    if (param.containsKey("pay_time")) {
                        record.setPayTime(new Date(param.getLong("pay_time")));
                    } else {
                        record.setPayTime(date);
                    }
                    break;
                case 3:// 已发货
                    record.setStatus(Constant.ORDER_STATUS_SENDED);
                    if (param.containsKey("send_time")) {
                        record.setSendTime(new Date(param.getLong("send_time")));
                    } else {
                        record.setSendTime(date);
                    }
                    break;
                case 4:// 交易完成
                    record.setStatus(Constant.ORDER_STATUS_COMPLATE);
                    if (param.containsKey("complete_time")) {
                        record.setCompateTime(new Date(param.getLong("complete_time")));
                    } else {
                        record.setCompateTime(date);
                    }
                    break;
                case 5:// 订单取消
                    record.setStatus(Constant.ORDER_STATUS_CANCEL);
                    if (param.containsKey("reason")) {
                        record.setDescription("客户主动取消，原因：" + param.getString("reason"));
                    }
                    break;
                case 6:// 已拒收
                    record.setStatus(Constant.ORDER_STATUS_REFUSE);
                    record.setDescription("订单被客户拒收");
                    break;
                case 7:// 交易关闭
                    record.setStatus(Constant.ORDER_STATUS_CLOSE);
                    if (param.containsKey("reason")) {
                        record.setDescription("客户主动取消，原因：" + param.getString("reason"));
                    }
                    break;
                default:
                    break;
            }
            TbOrderExample example = new TbOrderExample();
            TbOrderExample.Criteria criteria = example.createCriteria();
            if (ordercode.isPresent()) {
                criteria.andSerialNumberEqualTo(ordercode.get());
            }
            if (orderid.isPresent()) {
                criteria.andIdEqualTo(orderid.get());
            }

            List<TbOrder> exist = tbOrderMapper.selectByExample(example);
            if (exist != null && exist.size() > 0) {
                TbOrder o = exist.get(0);
                if (status == o.getStatus()) {
                    LOG.info("订单【" + o.getSerialNumber() + "】状态已经为" + status + "，不能重复提交.");
                    return message;
                }
            }
            tbOrderMapper.updateByExampleSelective(record, example);
            message = respWriter.toSuccess(result, sn);
        } catch (Exception e) {
            throw e;
        }
        return message;
    }

}
