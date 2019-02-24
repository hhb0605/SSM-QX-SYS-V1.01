/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50524
Source Host           : localhost:3306
Source Database       : qxsys

Target Server Type    : MYSQL
Target Server Version : 50524
File Encoding         : 65001

Date: 2019-02-24 17:35:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_article`
-- ----------------------------
DROP TABLE IF EXISTS `sys_article`;
CREATE TABLE `sys_article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `title` varchar(200) DEFAULT NULL COMMENT '标题',
  `title_pic` varchar(200) DEFAULT NULL COMMENT '封面图片',
  `author` varchar(100) NOT NULL COMMENT '作者',
  `content` mediumtext COMMENT '内容',
  `content_md` mediumtext COMMENT '内容-Markdown',
  `origin` varchar(100) DEFAULT NULL COMMENT '来源',
  `state` varchar(100) NOT NULL COMMENT '状态',
  `eye_count` bigint(20) DEFAULT '0' COMMENT '浏览量',
  `publish_time` timestamp NOT NULL DEFAULT '1970-02-01 00:00:01' COMMENT '发布时间',
  `edit_time` timestamp NOT NULL DEFAULT '1970-02-01 00:00:01' COMMENT '上次修改时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='文章表';

-- ----------------------------
-- Records of sys_article
-- ----------------------------
INSERT INTO `sys_article` VALUES ('1', null, null, 'sda', null, null, null, '1', '0', '1970-02-01 00:00:01', '1970-02-01 00:00:01', '2019-02-22 16:46:55');

-- ----------------------------
-- Table structure for `sys_article_category`
-- ----------------------------
DROP TABLE IF EXISTS `sys_article_category`;
CREATE TABLE `sys_article_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `article_id` bigint(20) NOT NULL COMMENT '文章ID',
  `category_id` bigint(20) NOT NULL COMMENT '分类ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='文章&&分类关联表';

-- ----------------------------
-- Records of sys_article_category
-- ----------------------------
INSERT INTO `sys_article_category` VALUES ('1', '1', '1');

-- ----------------------------
-- Table structure for `sys_article_tags`
-- ----------------------------
DROP TABLE IF EXISTS `sys_article_tags`;
CREATE TABLE `sys_article_tags` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `article_id` bigint(20) NOT NULL COMMENT '文章ID',
  `tags_id` bigint(20) NOT NULL COMMENT '标签ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='文章&&标签关联表';

-- ----------------------------
-- Records of sys_article_tags
-- ----------------------------
INSERT INTO `sys_article_tags` VALUES ('1', '1', '1');

-- ----------------------------
-- Table structure for `sys_category`
-- ----------------------------
DROP TABLE IF EXISTS `sys_category`;
CREATE TABLE `sys_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) DEFAULT NULL COMMENT '分类名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='分类表';

-- ----------------------------
-- Records of sys_category
-- ----------------------------
INSERT INTO `sys_category` VALUES ('1', null);

-- ----------------------------
-- Table structure for `sys_comments`
-- ----------------------------
DROP TABLE IF EXISTS `sys_comments`;
CREATE TABLE `sys_comments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `p_id` bigint(20) DEFAULT '0' COMMENT '父级ID，给哪个留言进行回复',
  `c_id` bigint(20) DEFAULT '0' COMMENT '子级ID，给哪个留言下的回复进行评论',
  `article_title` varchar(200) DEFAULT NULL COMMENT '文章标题',
  `article_id` bigint(20) DEFAULT NULL COMMENT '文章ID',
  `author` varchar(200) DEFAULT NULL COMMENT '留言人',
  `author_id` varchar(200) DEFAULT NULL COMMENT '给谁留言',
  `email` varchar(100) DEFAULT NULL COMMENT '留言邮箱',
  `content` text COMMENT '留言内容',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '留言时间',
  `url` varchar(200) DEFAULT NULL COMMENT '链接',
  `state` varchar(100) DEFAULT '正常' COMMENT '状态',
  `sort` bigint(20) DEFAULT '0' COMMENT '分类：0默认文章详情页，1友链页，2关于我页',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论表';

-- ----------------------------
-- Records of sys_comments
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_links`
-- ----------------------------
DROP TABLE IF EXISTS `sys_links`;
CREATE TABLE `sys_links` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) DEFAULT NULL COMMENT '连接名称',
  `url` varchar(200) DEFAULT NULL COMMENT '连接URL',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='友链表';

-- ----------------------------
-- Records of sys_links
-- ----------------------------
INSERT INTO `sys_links` VALUES ('1', null, null);

-- ----------------------------
-- Table structure for `sys_logs`
-- ----------------------------
DROP TABLE IF EXISTS `sys_logs`;
CREATE TABLE `sys_logs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `createdTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
-- Records of sys_logs
-- ----------------------------
INSERT INTO `sys_logs` VALUES ('89', 'admin', '删除操作', 'cn.qx.sys.service.impl.LogServiceImpl.deleteObjects', '[[87]]', '58', '0:0:0:0:0:0:0:1', '2019-02-22 17:25:33');
INSERT INTO `sys_logs` VALUES ('90', 'admin', '删除操作', 'cn.qx.sys.service.impl.LogServiceImpl.deleteObjects', '[[88]]', '157', '0:0:0:0:0:0:0:1', '2019-02-22 17:25:39');

-- ----------------------------
-- Table structure for `sys_menus`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menus`;
CREATE TABLE `sys_menus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '资源名称',
  `url` varchar(200) DEFAULT NULL COMMENT '资源URL',
  `type` int(11) DEFAULT NULL COMMENT '类型     1：菜单   2：按钮',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `note` varchar(100) DEFAULT NULL COMMENT '备注',
  `parent_id` int(11) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `permission` varchar(500) DEFAULT NULL COMMENT '授权(如：user:create)',
  `created_time` datetime DEFAULT NULL,
  `modifiedTime` datetime DEFAULT NULL,
  `created_user` varchar(20) DEFAULT NULL COMMENT '创建用户',
  `modified_user` varchar(20) DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=139 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='资源管理';

-- ----------------------------
-- Records of sys_menus
-- ----------------------------
INSERT INTO `sys_menus` VALUES ('8', '系统管理', '请求路径', '1', '8', null, null, 'sys:list', '2017-07-12 15:15:59', '2017-07-21 11:16:00', 'admin', 'admin');
INSERT INTO `sys_menus` VALUES ('25', '日志管理', 'log/doFindPageObject.do', '1', '25', null, '8', 'sys:log:view', '2017-07-12 15:15:59', '2018-10-29 12:15:48', 'admin', null);
INSERT INTO `sys_menus` VALUES ('45', '用户管理', 'user/doUserListUI.do', '1', '45', null, '8', 'sys:user:view', '2017-07-12 15:15:59', '2018-10-29 12:16:41', 'admin', null);
INSERT INTO `sys_menus` VALUES ('46', '菜单管理', 'menu/doMenuListUI.do', '1', '46', null, '8', 'sys:menu:view', '2017-07-12 15:15:59', '2018-10-29 12:17:01', 'admin', null);
INSERT INTO `sys_menus` VALUES ('47', '角色管理', 'role/doRoleListUI.do', '1', '47', null, '8', 'sys:role:view', '2017-07-12 15:15:59', '2018-10-29 12:17:46', 'admin', null);
INSERT INTO `sys_menus` VALUES ('131', '禁用启用', 'user/doValidById.do', '1', '111111', null, '45', 'sys:user:valid', '2018-07-18 19:24:48', '2018-07-18 19:24:48', null, null);
INSERT INTO `sys_menus` VALUES ('132', '日志删除', 'log/doDeleteObject.do', '2', '10', null, '25', 'sys:log:delete', '2018-10-29 12:18:42', '2018-10-29 12:18:42', null, null);

-- ----------------------------
-- Table structure for `sys_roles`
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles`;
CREATE TABLE `sys_roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '角色名称',
  `note` varchar(500) DEFAULT NULL COMMENT '备注',
  `created_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `created_user` varchar(20) DEFAULT NULL COMMENT '创建用户',
  `modified_user` varchar(20) DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色';

-- ----------------------------
-- Records of sys_roles
-- ----------------------------
INSERT INTO `sys_roles` VALUES ('46', '普通会员', '普通会员', '2018-07-06 19:25:36', '2019-02-13 10:21:07', null, null);
INSERT INTO `sys_roles` VALUES ('47', '系统所有者', '系系统所有者', '2018-07-18 19:23:11', '2019-02-13 10:20:45', null, null);
INSERT INTO `sys_roles` VALUES ('48', '权限管理员', '权限管理', '2019-01-30 17:24:42', '2019-02-13 10:20:55', '系统所有者', null);
INSERT INTO `sys_roles` VALUES ('49', '第二个权限管理者', '黄浩', '2019-02-23 15:48:51', '2019-02-23 15:48:51', null, null);
INSERT INTO `sys_roles` VALUES ('51', '第二个普通用户', '黄浩', '2019-02-23 17:00:56', '2019-02-23 17:00:56', null, null);

-- ----------------------------
-- Table structure for `sys_role_menus`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menus`;
CREATE TABLE `sys_role_menus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `menu_id` int(11) DEFAULT NULL COMMENT 'ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1488 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Records of sys_role_menus
-- ----------------------------
INSERT INTO `sys_role_menus` VALUES ('1476', '49', '8');
INSERT INTO `sys_role_menus` VALUES ('1477', '49', '25');
INSERT INTO `sys_role_menus` VALUES ('1478', '49', '132');
INSERT INTO `sys_role_menus` VALUES ('1479', '49', '46');
INSERT INTO `sys_role_menus` VALUES ('1480', '49', '47');
INSERT INTO `sys_role_menus` VALUES ('1481', '50', '8');
INSERT INTO `sys_role_menus` VALUES ('1482', '50', '25');
INSERT INTO `sys_role_menus` VALUES ('1483', '50', '132');
INSERT INTO `sys_role_menus` VALUES ('1484', '50', '46');
INSERT INTO `sys_role_menus` VALUES ('1485', '50', '47');
INSERT INTO `sys_role_menus` VALUES ('1486', '51', '8');
INSERT INTO `sys_role_menus` VALUES ('1487', '51', '46');

-- ----------------------------
-- Table structure for `sys_tags`
-- ----------------------------
DROP TABLE IF EXISTS `sys_tags`;
CREATE TABLE `sys_tags` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) DEFAULT NULL COMMENT '标签名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='标签表';

-- ----------------------------
-- Records of sys_tags
-- ----------------------------
INSERT INTO `sys_tags` VALUES ('1', null);

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `nickname` varchar(100) DEFAULT NULL COMMENT '昵称',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `salt` varchar(200) NOT NULL COMMENT '盐值',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(100) DEFAULT NULL COMMENT '头像',
  `valid` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常  默认值 ：1',
  `modified_user` varchar(20) DEFAULT NULL COMMENT '修改用户',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  `mobile` int(15) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='标签表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '菜鸡一枚', 'b9fd429a10b73d2c0971db1d4ad975a9', '9a924d8ae9800c229c892eeba7d413a6', '233333@qq.com', 'http://img.api.tycoding.cn/avatar.jpg', '1', 'admin', '2019-02-23 17:27:46', null, null);
INSERT INTO `sys_user` VALUES ('15', 'zxzx', null, 'a0ec95965b80ed1c2a01af749de615df', 'e5b5f928-ec6f-45d1-bb11-6186f84f23c3', 'sdasd', null, '1', 'admin', '2019-02-24 15:43:47', '21213222', '2019-02-24 15:43:38');

-- ----------------------------
-- Table structure for `sys_user_roles`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_roles`;
CREATE TABLE `sys_user_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of sys_user_roles
-- ----------------------------
INSERT INTO `sys_user_roles` VALUES ('1', '19', '46');
INSERT INTO `sys_user_roles` VALUES ('13', '0', '47');
INSERT INTO `sys_user_roles` VALUES ('14', '0', '46');
INSERT INTO `sys_user_roles` VALUES ('15', '0', '47');
INSERT INTO `sys_user_roles` VALUES ('16', '0', '46');
INSERT INTO `sys_user_roles` VALUES ('17', '0', '47');
INSERT INTO `sys_user_roles` VALUES ('18', '0', '46');
INSERT INTO `sys_user_roles` VALUES ('19', '0', '47');
INSERT INTO `sys_user_roles` VALUES ('20', '6', '46');
INSERT INTO `sys_user_roles` VALUES ('21', '6', '47');
INSERT INTO `sys_user_roles` VALUES ('22', '7', '46');
INSERT INTO `sys_user_roles` VALUES ('23', '7', '49');
INSERT INTO `sys_user_roles` VALUES ('26', '9', '46');
INSERT INTO `sys_user_roles` VALUES ('27', '9', '48');
INSERT INTO `sys_user_roles` VALUES ('28', '8', '46');
INSERT INTO `sys_user_roles` VALUES ('29', '8', '48');
INSERT INTO `sys_user_roles` VALUES ('30', '8', '49');
INSERT INTO `sys_user_roles` VALUES ('31', '10', '46');
INSERT INTO `sys_user_roles` VALUES ('32', '10', '47');
INSERT INTO `sys_user_roles` VALUES ('33', '11', '46');
INSERT INTO `sys_user_roles` VALUES ('34', '11', '47');
INSERT INTO `sys_user_roles` VALUES ('35', '12', '46');
INSERT INTO `sys_user_roles` VALUES ('36', '13', '47');
INSERT INTO `sys_user_roles` VALUES ('37', '14', '46');
INSERT INTO `sys_user_roles` VALUES ('38', '3', '51');
INSERT INTO `sys_user_roles` VALUES ('41', '15', '46');
INSERT INTO `sys_user_roles` VALUES ('42', '15', '47');
