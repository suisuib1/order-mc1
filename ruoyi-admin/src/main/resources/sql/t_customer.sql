-- ----------------------------
-- 客户信息表
-- ----------------------------
CREATE TABLE t_customer (
  customer_id   bigint(20)    NOT NULL AUTO_INCREMENT    COMMENT '客户ID',
  customer_name varchar(100)  NOT NULL                   COMMENT '客户名称',
  contact_name  varchar(50)   DEFAULT ''                 COMMENT '联系人',
  phone_number  varchar(20)   DEFAULT ''                 COMMENT '联系电话',
  address       varchar(255)  DEFAULT ''                 COMMENT '地址',
  create_by     varchar(64)   DEFAULT ''                 COMMENT '创建者',
  create_time   datetime                                 COMMENT '创建时间',
  update_by     varchar(64)   DEFAULT ''                 COMMENT '更新者',
  update_time   datetime                                 COMMENT '更新时间',
  remark        varchar(500)  DEFAULT ''                 COMMENT '备注',
  PRIMARY KEY (customer_id)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT = '客户信息表';
