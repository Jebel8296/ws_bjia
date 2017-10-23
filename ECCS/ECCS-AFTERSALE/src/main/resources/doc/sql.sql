--*******************售后服务主表************************

CREATE TABLE `tb_aftersale` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `aftercode` varchar(45) NOT NULL COMMENT '售后服务编号:yyyyMMddHHmmssXXXX',
  `aftertype` int(11) DEFAULT NULL COMMENT '售后类型 1退货 2换货 3维修',
  `status` int(11) DEFAULT NULL COMMENT '售后状态  0待处理1已受理2售后完成3取消',
  `order_id` int(11) DEFAULT NULL COMMENT '订单ID',
  `product_code` varchar(45) DEFAULT NULL COMMENT '商品编码',
  `uid` int(11) DEFAULT NULL COMMENT '用户ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT NULL,
  `reason` varchar(300) DEFAULT NULL COMMENT '售后原因',
  `product_type` int(11) DEFAULT NULL COMMENT '售后产品类型  1手表 2耳机 3手机卡 4手环 5其他',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `reply` varchar(100) DEFAULT NULL COMMENT '客服反馈',
  PRIMARY KEY (`id`),
  UNIQUE KEY `aftercode_UNIQUE` (`aftercode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='售后服务表';

--*******************售后提交的图片信息**********************

CREATE TABLE `tb_aftersale_imagebox` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `after_id` int(11) NOT NULL COMMENT '售后服务ID',
  `uri` varchar(200) DEFAULT NULL,
  `mime` varchar(200) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `size` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='售后产品图片';

--******************售后的物流信息**************************

CREATE TABLE `tb_aftersale_logistics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `afterid` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `phone` varchar(45) NOT NULL,
  `zipcode` varchar(45) NOT NULL,
  `address` varchar(200) DEFAULT NULL,
  `province` int(11) NOT NULL,
  `city` int(11) NOT NULL,
  `area` int(11) NOT NULL,
  `road` varchar(200) NOT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='售后物流信息';

--******************售后的产品信息**************************

CREATE TABLE `tb_aftersale_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `afterid` int(11) NOT NULL COMMENT '售后ID',
  `product_code` varchar(45) DEFAULT NULL COMMENT '产品编码',
  `product_name` varchar(45) DEFAULT NULL COMMENT '产品名称',
  `device_code` varchar(45) DEFAULT NULL COMMENT '设备编码\n',
  `sign_time` datetime DEFAULT NULL COMMENT '签收日期',
  `phone` varchar(45) DEFAULT NULL COMMENT '电话',
  `quantity` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='售后产品信息';




