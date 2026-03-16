-- ----------------------------
-- 产品信息表
-- ----------------------------
CREATE TABLE t_product (
  product_id    bigint(20)    NOT NULL AUTO_INCREMENT    COMMENT '产品ID',
  product_name  varchar(100)  NOT NULL                   COMMENT '产品名称',
  category      varchar(50)   DEFAULT ''                 COMMENT '产品种类',
  specification varchar(100)  DEFAULT ''                 COMMENT '产品规格',
  unit_price    decimal(10,2) DEFAULT 0.00               COMMENT '单价',
  stock_quantity int(11)      DEFAULT 0                  COMMENT '库存数量',
  create_by     varchar(64)   DEFAULT ''                 COMMENT '创建者',
  create_time   datetime                                 COMMENT '创建时间',
  update_by     varchar(64)   DEFAULT ''                 COMMENT '更新者',
  update_time   datetime                                 COMMENT '更新时间',
  remark        varchar(500)  DEFAULT ''                 COMMENT '备注',
  PRIMARY KEY (product_id)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT = '产品信息表';
