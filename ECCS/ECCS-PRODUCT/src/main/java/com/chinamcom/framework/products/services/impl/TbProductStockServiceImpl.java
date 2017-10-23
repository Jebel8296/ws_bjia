package com.chinamcom.framework.products.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.common.service.BaseService;
import com.chinamcom.framework.products.mapper.TbProductStockMapper;
import com.chinamcom.framework.products.model.TbProductStock;
import com.chinamcom.framework.products.model.TbProductStockExample;
import com.chinamcom.framework.products.services.ITbProductStockService;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

@Service("tbProductStockService")
public class TbProductStockServiceImpl extends BaseService implements ITbProductStockService {

    @Autowired
    private TbProductStockMapper productStockMapper;

    @Override
    public String selectTbProductStockList(String sn, JsonObject param) throws Exception {
        String message = respWriter.toError(sn);
        JsonObject result = new JsonObject();
        List<TbProductStock> product = new ArrayList<TbProductStock>();
        JsonArray products = param.getJsonArray("product");
        products.forEach(prod -> {
            JsonObject p = (JsonObject) prod;
            Optional<Integer> prodid = Optional.ofNullable(p.getInteger("prodid"));
            Optional<String> prodcode = Optional.ofNullable(p.getString("prodcode"));
            Optional<String> itemcode = Optional.ofNullable(p.getString("itemcode"));
            TbProductStockExample example = new TbProductStockExample();
            TbProductStockExample.Criteria criteria = example.createCriteria();
            if (prodid.isPresent()) {
                criteria.andProdidEqualTo(prodid.get());
            }
            if (prodcode.isPresent()) {
                criteria.andProdcodeEqualTo(prodcode.get());
            }
            if (itemcode.isPresent()) {
                criteria.andItemcodeEqualTo(itemcode.get());
            }
            List<TbProductStock> rdata = productStockMapper.selectByExample(example);
            if (!rdata.isEmpty()) {
                product.add(rdata.get(0));
            }
        });
        result.put("stock", product);
        message = respWriter.toSuccess(result, sn);
        return message;
    }

    @Override
    public String updateTbProductStockList(String sn, JsonObject param) throws Exception {
        String message = respWriter.toError(sn);
        Integer type = param.getInteger("type");// 1增加2减少
        JsonArray products = param.getJsonArray("product");
        products.forEach(product -> {
            JsonObject p = (JsonObject) product;
            Optional<Integer> prodid = Optional.ofNullable(p.getInteger("prodid"));
            Optional<String> prodcode = Optional.ofNullable(p.getString("prodcode"));
            Optional<Integer> number = Optional.ofNullable(p.getInteger("number"));// 数量
            if (number.isPresent()) {
                TbProductStockExample example = new TbProductStockExample();
                TbProductStockExample.Criteria criteria = example.createCriteria();
                if (prodid.isPresent()) {
                    criteria.andProdidEqualTo(prodid.get());
                }
                if (prodcode.isPresent()) {
                    criteria.andProdcodeEqualTo(prodcode.get());
                }
                List<TbProductStock> rdata = productStockMapper.selectByExample(example);
                if (!rdata.isEmpty()) {
                    TbProductStock prod = rdata.get(0);
                    Integer stock = prod.getStock();// 库存
                    Integer sales = prod.getSales();// 销量
                    // 库存预警green:库存充足（STOCK>100)red:库存紧张(STOCK<100)gray:无库存(STOCK=0)
                    String warning = prod.getWarning();
                    if (type == 1) {
                        stock = stock + number.get();
                        sales = sales - number.get();
                        warning = stock > 100 ? "green" : stock == 0 ? "gray" : "red";
                    }
                    if (type == 2) {
                        stock = stock - number.get();
                        sales = sales + number.get();
                        warning = stock > 100 ? "green" : stock == 0 ? "gray" : "red";
                    }
                    TbProductStock record = new TbProductStock();
                    record.setStock(stock);
                    record.setSales(sales);
                    record.setWarning(warning);
                    record.setUpdateTime(new Date());
                    TbProductStockExample tpse = new TbProductStockExample();
                    TbProductStockExample.Criteria c = tpse.createCriteria();
                    if (prodid.isPresent()) {
                        c.andProdidEqualTo(prodid.get());
                    }
                    if (prodcode.isPresent()) {
                        c.andProdcodeEqualTo(prodcode.get());
                    }
                    productStockMapper.updateByExampleSelective(record, tpse);
                }
            }
        });
        message = respWriter.toSuccess(sn);
        return message;
    }

}
