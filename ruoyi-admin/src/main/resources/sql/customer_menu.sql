-- 1. Insert the top-level menu '木材厂管理'
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES('木材厂管理', 0, '5', 'timber', NULL, 1, 0, 'M', '0', '0', '', 'tree', 'admin', sysdate(), '木材厂管理主菜单');

-- 2. Get the id of the menu we just inserted
SET @timber_menu_id = (SELECT menu_id FROM sys_menu WHERE menu_name = '木材厂管理' AND path='timber');

-- 3. Insert the '客户管理' menu under '木材厂管理'
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES('客户管理', @timber_menu_id, '1', 'customer', 'system/customer/index', 1, 0, 'C', '0', '0', 'system:customer:list', 'user', 'admin', sysdate(), '客户管理菜单');

-- 4. Get the id of the '客户管理' menu
SET @customer_menu_id = (SELECT menu_id FROM sys_menu WHERE menu_name = '客户管理' AND path='customer');

-- 5. Insert button permissions for '客户管理'
INSERT INTO sys_menu (menu_name, parent_id, order_num, perms, create_by, create_time) VALUES ('客户查询', @customer_menu_id, 1, 'system:customer:query', 'admin', sysdate());
INSERT INTO sys_menu (menu_name, parent_id, order_num, perms, create_by, create_time) VALUES ('客户新增', @customer_menu_id, 2, 'system:customer:add', 'admin', sysdate());
INSERT INTO sys_menu (menu_name, parent_id, order_num, perms, create_by, create_time) VALUES ('客户修改', @customer_menu_id, 3, 'system:customer:edit', 'admin', sysdate());
INSERT INTO sys_menu (menu_name, parent_id, order_num, perms, create_by, create_time) VALUES ('客户删除', @customer_menu_id, 4, 'system:customer:remove', 'admin', sysdate());
INSERT INTO sys_menu (menu_name, parent_id, order_num, perms, create_by, create_time) VALUES ('客户导出', @customer_menu_id, 5, 'system:customer:export', 'admin', sysdate());
