package com.chinamcom.framework.aftersale.scheduler;

import com.chinamcom.framework.aftersale.model.TbAftersale;
import com.chinamcom.framework.aftersale.model.TbAftersaleExample;
import com.chinamcom.framework.aftersale.service.IAfterSaleService;
import io.vertx.core.AbstractVerticle;
import io.vertx.rx.java.RxHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rx.Scheduler;
import rx.functions.Action0;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/7/19.
 */
@Component
public class AfterSaleScheduler extends AbstractVerticle {

    private static Logger LOG = Logger.getLogger(AfterSaleScheduler.class);

    @Autowired
    private IAfterSaleService afterSaleService;

    @Override
    public void start() throws Exception {
        super.start();
        int initialDelay = 15;
        int period = 600;
        Scheduler scheduler = RxHelper.scheduler(vertx);
        scheduler.createWorker().schedulePeriodically(new Action0() {
            @Override
            public void call() {
                LOG.info("默认发货次日起10日后自动变更为交易完成......");
                TbAftersaleExample tbAftersaleExample = new TbAftersaleExample();
                tbAftersaleExample.createCriteria();
            }
        }, initialDelay, period, TimeUnit.SECONDS);
        LOG.info(this.getClass().getName() + "is deployed successfully.");
    }
}
