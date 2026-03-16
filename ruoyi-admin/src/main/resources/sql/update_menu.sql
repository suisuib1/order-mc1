-- 删除旧的木材厂管理菜单
DELETE FROM sys_menu WHERE menu_id >= 5000 AND menu_id <= 5010;

-- 添加客户管理菜单 (一级)
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) 
VALUES(5000, '客户管理', 0, 1, '/timber/customer', 'timber/customer/index', 1, 0, 'C', '0', '0', 'timber:customer:list', 'user', 'admin', sysdate(), '', NULL, '客户管理菜单');

-- 添加产品管理菜单 (一级)
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) 
VALUES(5001, '产品管理', 0, 2, '/timber/product', 'timber/product/index', 1, 0, 'C', '0', '0', 'timber:product:list', 'shopping', 'admin', sysdate(), '', NULL, '产品管理菜单');

-- 添加订单管理菜单 (一级)
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) 
VALUES(5002, '订单管理', 0, 3, '/timber/order', 'timber/order/index', 1, 0, 'C', '0', '0', 'timber:order:list', 'list', 'admin', sysdate(), '', NULL, '订单管理菜单');

-- 添加库存管理菜单 (一级)
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) 
VALUES(5003, '库存管理', 0, 4, '/timber/stock', 'timber/stock/index', 1, 0, 'C', '0', '0', 'timber:stock:list', 'redis', 'admin', sysdate(), '', NULL, '库存管理菜单');

-- 添加统计报表菜单 (一级)
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) 
VALUES(5004, '统计报表', 0, 5, '/timber/report', 'timber/report/index', 1, 0, 'C', '0', '0', 'timber:report:list', 'chart', 'admin', sysdate(), '', NULL, '统计报表菜单');

-- 添加生产管理菜单 (一级)
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) 
VALUES(5005, '生产管理', 0, 6, '/timber/production', 'timber/production/index', 1, 0, 'C', '0', '0', 'timber:production:list', 'build', 'admin', sysdate(), '', NULL, '生产管理菜单');
