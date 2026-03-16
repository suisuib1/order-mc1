-- ----------------------------
-- 订单表
-- ----------------------------
CREATE TABLE t_order (
  order_id      bigint(20)    NOT NULL AUTO_INCREMENT    COMMENT '订单ID',
  order_number  varchar(50)   NOT NULL UNIQUE            COMMENT '订单号',
  customer_id   bigint(20)    NOT NULL                   COMMENT '客户ID',
  total_amount  decimal(10,2) NOT NULL                   COMMENT '总金额',
  order_status  varchar(20)   NOT NULL                   COMMENT '订单状态',
  create_by     varchar(64)   DEFAULT ''                 COMMENT '创建者',
  create_time   datetime                                 COMMENT '创建时间',
  update_by     varchar(64)   DEFAULT ''                 COMMENT '更新者',
  update_time   datetime                                 COMMENT '更新时间',
  remark        varchar(500)  DEFAULT ''                 COMMENT '备注',
  PRIMARY KEY (order_id)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT = '订单表';

-- ----------------------------
-- 订单项表
-- ----------------------------
CREATE TABLE t_order_item (
  item_id       bigint(20)    NOT NULL AUTO_INCREMENT    COMMENT '订单项ID',
  order_id      bigint(20)    NOT NULL                   COMMENT '订单ID',
  product_id    bigint(20)    NOT NULL                   COMMENT '产品ID',
  quantity      int(11)       NOT NULL                   COMMENT '数量',
  unit_price    decimal(10,2) NOT NULL                   COMMENT '单价',
  PRIMARY KEY (item_id)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT = '订单项表';
