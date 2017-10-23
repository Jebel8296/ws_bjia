package com.chinamcom.framework.order.scheduler;

import com.chinamcom.framework.order.mapper.TbOrderMapper;
import com.chinamcom.framework.order.model.TbOrder;
import com.chinamcom.framework.order.model.TbOrderExample;
import io.vertx.core.AbstractVerticle;
import io.vertx.rx.java.RxHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rx.Scheduler;
import rx.functions.Action0;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/7/19.
 */
@Component
public class OrderScheduler extends AbstractVerticle {

    private static Logger LOG = Logger.getLogger(OrderScheduler.class);

    @Autowired
    private TbOrderMapper tbOrderMapper;

    @Override
    public void start() throws Exception {
        super.start();
        int initialDelay = 15;
        int period = 600;
        Scheduler scheduler = RxHelper.scheduler(vertx);
        scheduler.createWorker().schedulePeriodically(new Action0() {
            @Override
            public void call() {
                LOG.info("发货次日起10日后自动变更为交易完成,任务开始......");
                TbOrderExample tbOrderExample = new TbOrderExample();
                //取订单状态为已发货的订单
                tbOrderExample.createCriteria().andStatusEqualTo(3);
                List<TbOrder> tbOrderList = tbOrderMapper.selectByExample(tbOrderExample);
                if (tbOrderList != null && tbOrderList.size() > 0) {
                    for (int i = 0; i < tbOrderList.size(); i++) {
                        TbOrder tbOrder = tbOrderList.get(i);
                        Date sendTime = tbOrder.getSendTime();
                        if (sendTime != null) {
                            Date nowTime = new Date();
                            Long seconds = nowTime.getTime() - sendTime.getTime();
                            if (seconds / (1000 * 60 * 60 * 24) > 11) {
                                TbOrder update = new TbOrder();
                                update.setStatus(4);//交易完成
                                update.setCompateTime(nowTime);//完成时间
                                update.setUpdateTime(nowTime);
                                TbOrderExample tbOrderExample1 = new TbOrderExample();
                                tbOrderExample1.createCriteria().andIdEqualTo(tbOrder.getId());
                                tbOrderMapper.updateByExampleSelective(update, tbOrderExample1);
                                LOG.info("订单【" + tbOrder.getSerialNumber() + "】处理完成.");
                            }
                        }
                    }
                }
                LOG.info("任务结束.");
            }
        }, initialDelay, period, TimeUnit.SECONDS);
        LOG.info(this.getClass().getName() + "is deployed successfully.");
    }
}
