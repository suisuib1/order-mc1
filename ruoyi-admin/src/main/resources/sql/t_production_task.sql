CREATE TABLE t_production_task (
  task_id         bigint(20)    NOT NULL AUTO_INCREMENT    COMMENT '任务ID',
  order_id        bigint(20)    NOT NULL                   COMMENT '订单ID',
  task_status     varchar(20)   NOT NULL                   COMMENT '任务状态',
  assignee_id     bigint(20)                               COMMENT '负责人ID',
  create_by       varchar(64)   DEFAULT ''                 COMMENT '创建者',
  create_time     datetime                                 COMMENT '创建时间',
  update_by       varchar(64)   DEFAULT ''                 COMMENT '更新者',
  update_time     datetime                                 COMMENT '更新时间',
  remark          varchar(500)  DEFAULT ''                 COMMENT '备注',
  PRIMARY KEY (task_id)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT = '生产任务表';
