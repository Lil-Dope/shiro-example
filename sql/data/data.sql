INSERT INTO `sys_user` VALUES ('1', 'aidun', '超级管理员', '3037ce8743e7a88c584326c2578b35d6', '572914', '1', '2019-05-29 10:33:24', null, null);

INSERT INTO `sys_role` VALUES ('1', 'admin', '超管', '', '2019-06-03 11:09:26');

INSERT INTO `sys_user_role` VALUES ('1', '1', '1');

INSERT INTO `sys_permission` VALUES (1, '账号管理', 'menu', '', 'system:all', 0, true, '', 'el-icon-menu', 2);
INSERT INTO `sys_permission` VALUES (2, '权限管理', 'menu', '/permission/list', 'role:all', 1, true, NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (3, '新增角色', 'button', NULL, 'role:addupdate', 2, true, 'primary', NULL, 0);
INSERT INTO `sys_permission` VALUES (4, '编辑', 'button', NULL, 'role:addupdate', 2, true, 'success', NULL, 0);
INSERT INTO `sys_permission` VALUES (5, '查看用户', 'button', NULL, 'role:userList', 2, true, 'primary', NULL, 0);
INSERT INTO `sys_permission` VALUES (6, '绑定用户', 'button', NULL, 'role:bindUser', 2, true, 'primary', NULL, 0);
INSERT INTO `sys_permission` VALUES (7, '用户管理', 'menu', '/permission/user', 'user:all', 1, true, NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (8, '删除', 'button', NULL, 'user:delete', 7, true, 'danger', NULL, 0);
INSERT INTO `sys_permission` VALUES (9, '个人中心', 'menu', NULL, 'personal:all', 0, true, NULL, 'el-icon-user-solid', 3);
INSERT INTO `sys_permission` VALUES (10, '修改密码', 'button', '/setting/modifyPwd', 'personal:modifyPwd', 9, true, NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (11, '日志管理', 'menu', NULL, 'log:all', 0, true, NULL, 'el-icon-s-platform', 7);
INSERT INTO `sys_permission` VALUES (12, '用户操作日志', 'menu', NULL, 'log:list', 11, true, NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (13, '首页', 'button', '/index', 'index', 0, true, NULL, 'el-icon-s-data', 8);
INSERT INTO `sys_permission` VALUES (14, '系统设置', 'menu', NULL, 'system:setting', 0, true, NULL, 'el-icon-s-tools', 1);
INSERT INTO `sys_permission` VALUES (15, '菜单管理', 'menu', '/system/menu', 'menu:list', 14, true, NULL, NULL, 0);
INSERT INTO `sys_permission` VALUES (16, '新增菜单', 'button', '', 'menu:add', 15, true, 'primary', '', 0);
INSERT INTO `sys_permission` VALUES (17, '删除', 'button', '', 'menu:delete', 15, true, 'danger', '', 0);
INSERT INTO `sys_permission` VALUES (18, '编辑', 'button', NULL, 'menu:edit', 15, true, 'success', NULL, 0);
INSERT INTO `sys_permission` VALUES (19, '新增用户', 'button', NULL, 'user:add',7, true, 'primary', NULL, 0);



INSERT INTO `sys_role_permission` VALUES ('1', '1', '1');
INSERT INTO `sys_role_permission` VALUES ('2', '1', '2');
INSERT INTO `sys_role_permission` VALUES ('3', '1', '3');
INSERT INTO `sys_role_permission` VALUES ('4', '1', '4');
INSERT INTO `sys_role_permission` VALUES ('5', '1', '5');
INSERT INTO `sys_role_permission` VALUES ('6', '1', '6');
INSERT INTO `sys_role_permission` VALUES ('7', '1', '7');
INSERT INTO `sys_role_permission` VALUES ('8', '1', '8');
INSERT INTO `sys_role_permission` VALUES ('9', '1', '9');
INSERT INTO `sys_role_permission` VALUES ('10', '1', '10');
INSERT INTO `sys_role_permission` VALUES ('11', '1', '11');
INSERT INTO `sys_role_permission` VALUES ('12', '1', '12');
INSERT INTO `sys_role_permission` VALUES ('13', '1', '13');
INSERT INTO `sys_role_permission` VALUES ('14', '1', '14');
INSERT INTO `sys_role_permission` VALUES ('15', '1', '15');
INSERT INTO `sys_role_permission` VALUES ('16', '1', '16');
INSERT INTO `sys_role_permission` VALUES ('17', '1', '17');
INSERT INTO `sys_role_permission` VALUES ('18', '1', '18');
INSERT INTO `sys_role_permission` VALUES ('19', '1', '19');