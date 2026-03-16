-- ----------------------------
-- 1. Get the id of the '木材厂管理' menu
-- ----------------------------
SET @timber_menu_id = (SELECT menu_id FROM sys_menu WHERE menu_name = '木材厂管理' AND path = 'timber');

-- ----------------------------
-- 2. Insert the '产品管理' menu under '木材厂管理'
-- ----------------------------
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES('产品管理', @timber_menu_id, '2', 'product', 'system/product/index', 1, 0, 'C', '0', '0', 'system:product:list', 'shopping', 'admin', sysdate(), '产品管理菜单');

-- ----------------------------
-- 3. Get the id of the '产品管理' menu
-- ----------------------------
SET @product_menu_id = (SELECT menu_id FROM sys_menu WHERE menu_name = '产品管理' AND path = 'product');

-- ----------------------------
-- 4. Insert button permissions for '产品管理'
-- ----------------------------
INSERT INTO sys_menu (menu_name, parent_id, order_num, perms, create_by, create_time) VALUES ('产品查询', @product_menu_id, 1, 'system:product:query', 'admin', sysdate());
INSERT INTO sys_menu (menu_name, parent_id, order_num, perms, create_by, create_time) VALUES ('产品新增', @product_menu_id, 2, 'system:product:add', 'admin', sysdate());
INSERT INTO sys_menu (menu_name, parent_id, order_num, perms, create_by, create_time) VALUES ('产品修改', @product_menu_id, 3, 'system:product:edit', 'admin', sysdate());
INSERT INTO sys_menu (menu_name, parent_id, order_num, perms, create_by, create_time) VALUES ('产品删除', @product_menu_id, 4, 'system:product:remove', 'admin', sysdate());
INSERT INTO sys_menu (menu_name, parent_id, order_num, perms, create_by, create_time) VALUES ('产品导出', @product_menu_id, 5, 'system:product:export', 'admin', sysdate());
