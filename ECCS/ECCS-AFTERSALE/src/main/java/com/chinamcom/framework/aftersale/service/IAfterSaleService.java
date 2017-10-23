package com.chinamcom.framework.aftersale.service;

import com.chinamcom.framework.common.service.IService;

import io.vertx.core.json.JsonObject;

public interface IAfterSaleService extends IService {

    /**
     * 查询售后服务
     *
     * @param sn
     * @param param
     * @return
     */
    String selectTbAfterSale(String sn, JsonObject param);

    /**
     * 售后服务详情
     *
     * @param sn
     * @param param
     * @return
     */
    void selectTbAfterSaleInfo(String sn, JsonObject param, JsonObject result);

    /**
     * 更新售后信息
     *
     * @param sn
     * @param param
     * @return
     */
    String updateTbAfterSale(String sn, JsonObject param);
    /**
     * 更新售后信息
     *
     * @param sn
     * @param param
     * @return
     */
    String updateSecond(String sn, JsonObject param);

    /**
     * 申请售后服务
     *
     * @param sn
     * @param param
     * @return
     */
    String submitTbAfterSale(String sn, JsonObject param);

    /**
     * 根据设备号-申请售后服务
     *
     * @param sn
     * @param param
     * @return
     */
    String submitTbAfterSaleByDevice(String sn, JsonObject param);

    /**
     * 客服处理售后服务
     *
     * @param sn
     * @param param
     * @return
     */
    String handleTbAfterSale(String sn, JsonObject param);

    /**
     * 上传申请售后的物流信息
     *
     * @param sn
     * @param param
     * @return
     */
    String uploadLogistics(String sn, JsonObject param);

    /**
     * 更新售后状态
     *
     * @param sn
     * @param param
     * @return
     */
    String cancelAfterSale(String sn, JsonObject param);
}
