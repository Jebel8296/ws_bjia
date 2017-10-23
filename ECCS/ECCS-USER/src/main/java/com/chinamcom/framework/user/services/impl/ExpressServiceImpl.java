package com.chinamcom.framework.user.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.service.BaseService;
import com.chinamcom.framework.user.services.IExpressService;

import io.vertx.core.json.JsonObject;

@Transactional
public class ExpressServiceImpl extends BaseService implements IExpressService {

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String addExpress(String sn, JsonObject param) throws Exception {
        StringBuilder sql = new StringBuilder("INSERT INTO tb_user_express(");
        sql.append("USERID,").append("NAME,").append("PHONE,").append("province,").append("city,").append("area,")
                .append("ZIPCODE,").append("ADDRESS,").append("STATUS,")
                .append("CREATETIME) VALUES(?,?,?,?,?,?,?,?,?,NOW())");

        Integer def = Optional.ofNullable(param.getInteger("def")).orElse(0);// 是否默认
        if (def == 1) {
            // 先将此用户下的默认地址重置为正常,保证一个用户只有一个默认地址
            StringBuilder sql1 = new StringBuilder("UPDATE tb_user_express SET STATUS=0 WHERE USERID=? AND STATUS=1 ");
            jdbcTemplate.update(sql1.toString(), new Object[]{param.getInteger("uid")});
        }
        jdbcTemplate.update(sql.toString(),
                new Object[]{param.getInteger("uid"), param.getString("name"), param.getString("phone"),
                        param.getString("province"), param.getString("city"), param.getString("area"),
                        param.getString("zipcode"), param.getString("address"), def});

        StringBuilder sql2 = new StringBuilder("SELECT a.ID eid,a.NAME name,a.PHONE phone,");
        sql2.append("a.PROVINCE provname,a.CITY cityname,a.AREA areaname,");
        sql2.append("a.ZIPCODE zipcode,a.ADDRESS address,a.STATUS status ");
        sql2.append("FROM tb_user_express a WHERE a.STATUS != 2 AND USERID=? ORDER BY ID DESC");
        List<Map<String, Object>> result = null;
        result = Optional.ofNullable(jdbcTemplate.queryForList(sql2.toString(), new Object[]{param.getInteger("uid")})).orElse(new ArrayList<Map<String, Object>>());
        if (result != null && result.size() > 0) {
            return respWriter.toSuccess(result.get(0), sn);
        } else {
            return respWriter.toSuccess(sn);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String modExpress(String sn, JsonObject param) throws Exception {
        StringBuilder sql = new StringBuilder("UPDATE tb_user_express SET NAME=?,PHONE=?,province=?,city=?,area=?,ZIPCODE=?,ADDRESS=?,STATUS=?,UPDATETIME=NOW() WHERE ID=?");
        Integer def = Optional.ofNullable(param.getInteger("def")).orElse(0);// 是否默认
        if (def == 1) {
            StringBuilder sql1 = new StringBuilder("UPDATE tb_user_express SET STATUS=0 WHERE USERID=? AND STATUS=1 ");
            jdbcTemplate.update(sql1.toString(), new Object[]{param.getInteger("uid")});
        }
        jdbcTemplate.update(sql.toString(),
                new Object[]{param.getString("name"), param.getString("phone"), param.getString("province"),
                        param.getString("city"), param.getString("area"), param.getString("zipcode"),
                        param.getString("address"), def, param.getInteger("eid")});

        return respWriter.toSuccess(sn);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String delExpress(String sn, JsonObject param) throws Exception {
        StringBuilder sql = new StringBuilder("UPDATE tb_user_express SET STATUS = ? WHERE ID = ?");
        jdbcTemplate.update(sql.toString(), new Object[]{2, param.getInteger("eid")});
        return respWriter.toSuccess(sn);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String defExpress(String sn, JsonObject param) throws Exception {
        // 先将此用户下的默认地址重置为正常,保证一个用户只有一个默认地址
        StringBuilder sql1 = new StringBuilder("UPDATE tb_user_express SET STATUS=0 WHERE USERID=? AND STATUS=1 ");
        jdbcTemplate.update(sql1.toString(), new Object[]{param.getInteger("uid")});
        // 设置默认地址
        StringBuilder sql2 = new StringBuilder("UPDATE tb_user_express SET STATUS=1 WHERE USERID=? AND ID=?");
        jdbcTemplate.update(sql2.toString(), new Object[]{param.getInteger("uid"), param.getInteger("eid")});
        return respWriter.toSuccess(sn);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String listExpress(String sn, JsonObject param) throws Exception {
        StringBuilder sql1 = new StringBuilder("SELECT a.ID eid,a.NAME name,a.PHONE phone,");
        sql1.append("a.PROVINCE provname,a.CITY cityname,a.AREA areaname,");
        sql1.append("a.ZIPCODE zipcode,a.ADDRESS address,a.STATUS status ");
        sql1.append("FROM tb_user_express a WHERE a.STATUS != 2 ");
        //sql1.append("LEFT JOIN addr_province b ON a.PROVENCEID=b.PRO_ID LEFT JOIN addr_city c ON a.CITYID=c.CITY_ID LEFT JOIN addr_area d on a.AREAID=d.AREA_ID ");
        List<Map<String, Object>> result = null;
        if (param.containsKey("express")) {
            sql1.append("AND ID=? ORDER BY ID DESC");
            result = Optional.ofNullable(jdbcTemplate.queryForList(sql1.toString(), new Object[]{param.getInteger("express")})).orElse(new ArrayList<Map<String, Object>>());
        } else {
            sql1.append("AND USERID=? ORDER BY ID DESC");
            result = Optional.ofNullable(jdbcTemplate.queryForList(sql1.toString(), new Object[]{param.getInteger("uid")})).orElse(new ArrayList<Map<String, Object>>());
        }
        String reply = respWriter.toError(sn);
        if (result.isEmpty()) {
            reply = respWriter.toError(sn, RespCode.CODE_504);
        } else {
            reply = respWriter.toSuccess(result, sn);
        }
        return reply;
    }

}
