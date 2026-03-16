-- ----------------------------
-- 1. Get the id of the '木材厂管理' menu
-- ----------------------------
SET @timber_menu_id = (SELECT menu_id FROM sys_menu WHERE menu_name = '木材厂管理' AND path = 'timber');

-- ----------------------------
-- 2. Insert the '订单管理' menu under '木材厂管理'
-- ----------------------------
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES('订单管理', @timber_menu_id, '3', 'order', 'system/order/index', 1, 0, 'C', '0', '0', 'system:order:list', 'list', 'admin', sysdate(), '订单管理菜单');

-- ----------------------------
-- 3. Get the id of the '订单管理' menu
-- ----------------------------
SET @order_menu_id = (SELECT menu_id FROM sys_menu WHERE menu_name = '订单管理' AND path = 'order');

-- ----------------------------
-- 4. Insert button permissions for '订单管理'
-- ----------------------------
INSERT INTO sys_menu (menu_name, parent_id, order_num, perms, create_by, create_time) VALUES ('订单查询', @order_menu_id, 1, 'system:order:query', 'admin', sysdate());
INSERT INTO sys_menu (menu_name, parent_id, order_num, perms, create_by, create_time) VALUES ('订单新增', @order_menu_id, 2, 'system:order:add', 'admin', sysdate());
INSERT INTO sys_menu (menu_name, parent_id, order_num, perms, create_by, create_time) VALUES ('订单修改', @order_menu_id, 3, 'system:order:edit', 'admin', sysdate());
INSERT INTO sys_menu (menu_name, parent_id, order_num, perms, create_by, create_time) VALUES ('订单删除', @order_menu_id, 4, 'system:order:remove', 'admin', sysdate());
INSERT INTO sys_menu (menu_name, parent_id, order_num, perms, create_by, create_time) VALUES ('订单导出', @order_menu_id, 5, 'system:order:export', 'admin', sysdate());
