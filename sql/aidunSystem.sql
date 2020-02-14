CREATE TABLE `sys_user` (
   `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
   `account` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '账号',
   `user_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '姓名',
   `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '密码',
   `salt` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '盐',
   `state` char(1) COLLATE utf8_unicode_ci NOT NULL DEFAULT '1' COMMENT '0  删除   1正常',
   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
   `update_time` datetime DEFAULT NULL COMMENT '修改时间',
   `open_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



CREATE TABLE `sys_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `role` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '角色标识程序中判断使用,如"admin",这个是唯一的:',
  `description` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '角色描述,UI界面显示使用',
  `available` bit(1) DEFAULT b'1' COMMENT '是否可用,如果不可用将不会添加给用户  0不可  1可用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;


CREATE TABLE `sys_permission` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '权限名 菜单名',
  `resource_type` enum('button','menu') COLLATE utf8_unicode_ci NOT NULL COMMENT '资源类型，[menu|button]',
  `url` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '菜单路径，按钮可以空',
  `permission` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view',
  `parent_id` int(11) DEFAULT NULL COMMENT '父id',
  `available` bit(1) DEFAULT b'1' COMMENT '是否有用  0无用  1有用',
  `button_type` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `icon` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '一级菜单图标',
  `sort` int(11) DEFAULT '0' COMMENT '菜单排序  0默认最后一个',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `sys_role_permission` (
   `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
   `role_id` int(11) NOT NULL,
   `permission_id` int(11) NOT NULL,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `sys_user_role` (
   `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
   `role_id` int(11) NOT NULL COMMENT '角色表id',
   `user_id` int(11) NOT NULL,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;