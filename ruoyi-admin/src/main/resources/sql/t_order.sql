CREATE TABLE IF NOT EXISTS t_customer (
  customer_id    bigint(20)     NOT NULL AUTO_INCREMENT COMMENT '客户ID',
  customer_name  varchar(100)   NOT NULL COMMENT '客户名称',
  contact_name   varchar(50)    DEFAULT '' COMMENT '联系人',
  phone_number   varchar(30)    DEFAULT '' COMMENT '联系电话',
  address        varchar(255)   DEFAULT '' COMMENT '地址',
  create_by      varchar(64)    DEFAULT '' COMMENT '创建者',
  create_time    datetime COMMENT '创建时间',
  update_by      varchar(64)    DEFAULT '' COMMENT '更新者',
  update_time    datetime COMMENT '更新时间',
  remark         varchar(500)   DEFAULT '' COMMENT '备注',
  PRIMARY KEY (customer_id)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT='客户信息表';

CREATE TABLE IF NOT EXISTS t_product (
  product_id      bigint(20)      NOT NULL AUTO_INCREMENT COMMENT '产品ID',
  product_name    varchar(100)    NOT NULL COMMENT '产品名称',
  category        varchar(50)     DEFAULT '' COMMENT '产品种类',
  specification   varchar(255)    DEFAULT '' COMMENT '产品规格',
  unit_price      decimal(10, 2)  NOT NULL DEFAULT 0 COMMENT '单价',
  stock_quantity  bigint(20)      NOT NULL DEFAULT 0 COMMENT '库存数量',
  create_by       varchar(64)     DEFAULT '' COMMENT '创建者',
  create_time     datetime COMMENT '创建时间',
  update_by       varchar(64)     DEFAULT '' COMMENT '更新者',
  update_time     datetime COMMENT '更新时间',
  remark          varchar(500)    DEFAULT '' COMMENT '备注',
  PRIMARY KEY (product_id)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT='产品信息表';

CREATE TABLE IF NOT EXISTS t_order (
  order_id                 bigint(20)      NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  order_number             varchar(50)     NOT NULL COMMENT '订单号',
  customer_id              bigint(20)      NOT NULL COMMENT '客户ID',
  total_amount             decimal(10, 2)  NOT NULL DEFAULT 0 COMMENT '总金额',
  contract_no              varchar(50)     DEFAULT '' COMMENT '合同编号',
  order_status             varchar(30)     NOT NULL COMMENT '订单状态',
  cancel_reason            varchar(255)    DEFAULT '' COMMENT '取消原因',
  stock_deducted           char(1)         DEFAULT '0' COMMENT '是否已扣减库存',
  production_task_id       bigint(20)      DEFAULT NULL COMMENT '生产任务ID',
  submit_review_time       datetime COMMENT '提交审核时间',
  review_time              datetime COMMENT '审核通过时间',
  contract_time            datetime COMMENT '合同签订时间',
  production_start_time    datetime COMMENT '开始生产时间',
  production_finish_time   datetime COMMENT '完成生产时间',
  outbound_time            datetime COMMENT '出库时间',
  complete_time            datetime COMMENT '完成时间',
  create_by                varchar(64)     DEFAULT '' COMMENT '创建者',
  create_time              datetime COMMENT '创建时间',
  update_by                varchar(64)     DEFAULT '' COMMENT '更新者',
  update_time              datetime COMMENT '更新时间',
  remark                   varchar(500)    DEFAULT '' COMMENT '备注',
  PRIMARY KEY (order_id),
  UNIQUE KEY uk_order_number (order_number)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT='订单表';

CREATE TABLE IF NOT EXISTS t_order_item (
  item_id       bigint(20)      NOT NULL AUTO_INCREMENT COMMENT '订单项ID',
  order_id      bigint(20)      NOT NULL COMMENT '订单ID',
  product_id    bigint(20)      NOT NULL COMMENT '产品ID',
  quantity      bigint(20)      NOT NULL COMMENT '数量',
  unit_price    decimal(10, 2)  NOT NULL DEFAULT 0 COMMENT '单价',
  PRIMARY KEY (item_id)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT='订单项表';

CREATE TABLE IF NOT EXISTS t_production_task (
  task_id           bigint(20)     NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  task_no           varchar(50)    NOT NULL COMMENT '任务单号',
  order_id          bigint(20)     NOT NULL COMMENT '订单ID',
  order_number      varchar(50)    NOT NULL COMMENT '订单号',
  task_status       varchar(30)    NOT NULL COMMENT '任务状态',
  planned_quantity  bigint(20)     NOT NULL DEFAULT 0 COMMENT '计划生产数量',
  create_mode       varchar(20)    DEFAULT '' COMMENT '创建方式',
  assignee_id       bigint(20)     DEFAULT NULL COMMENT '负责人ID',
  create_by         varchar(64)    DEFAULT '' COMMENT '创建者',
  create_time       datetime COMMENT '创建时间',
  update_by         varchar(64)    DEFAULT '' COMMENT '更新者',
  update_time       datetime COMMENT '更新时间',
  remark            varchar(500)   DEFAULT '' COMMENT '备注',
  PRIMARY KEY (task_id),
  UNIQUE KEY uk_task_no (task_no)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT='生产任务表';

CREATE TABLE IF NOT EXISTS t_order_status_log (
  log_id             bigint(20)    NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  order_id           bigint(20)    NOT NULL COMMENT '订单ID',
  from_status        varchar(30)   DEFAULT '' COMMENT '原状态',
  to_status          varchar(30)   NOT NULL COMMENT '目标状态',
  operation_remark   varchar(255)  DEFAULT '' COMMENT '操作备注',
  operation_by       varchar(64)   DEFAULT '' COMMENT '操作人',
  operation_time     datetime COMMENT '操作时间',
  PRIMARY KEY (log_id)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT='订单状态流转日志表';

CREATE TABLE IF NOT EXISTS t_stock_flow (
  flow_id            bigint(20)    NOT NULL AUTO_INCREMENT COMMENT '流水ID',
  product_id         bigint(20)    NOT NULL COMMENT '产品ID',
  flow_type          varchar(20)   NOT NULL COMMENT '流水类型',
  change_quantity    bigint(20)    NOT NULL COMMENT '变动数量',
  before_quantity    bigint(20)    NOT NULL COMMENT '变动前库存',
  after_quantity     bigint(20)    NOT NULL COMMENT '变动后库存',
  order_number       varchar(50)   DEFAULT '' COMMENT '订单单号',
  task_no            varchar(50)   DEFAULT '' COMMENT '生产任务单号',
  business_time      datetime COMMENT '业务时间',
  create_by          varchar(64)   DEFAULT '' COMMENT '创建者',
  create_time        datetime COMMENT '创建时间',
  remark             varchar(500)  DEFAULT '' COMMENT '备注',
  PRIMARY KEY (flow_id)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT='库存流水表';
