/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : sampledb

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2020-08-25 16:31:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(400) DEFAULT NULL,
  `url` varchar(1000) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '数据导入', 'import.html', '0', 'icon-home', '1');
INSERT INTO `sys_menu` VALUES ('2', '报表查询', 'report.html', '0', 'icon-th', '2');
INSERT INTO `sys_menu` VALUES ('3', '数据维护', null, '0', 'icon-wrench', '3');
INSERT INTO `sys_menu` VALUES ('4', '客户等级维护', 'customers.html', '3', 'icon-wrench', '3');
INSERT INTO `sys_menu` VALUES ('5', '专业明细维护', 'fix_detail_pro.html', '3', 'icon-wrench', '3');
INSERT INTO `sys_menu` VALUES ('6', '发票专业维护', 'fix_content_pro.html', '3', 'icon-wrench', '3');
INSERT INTO `sys_menu` VALUES ('7', '分类编号维护', 'fix_info_pro.html', '3', 'icon-wrench', '3');
INSERT INTO `sys_menu` VALUES ('8', '菜单分配', 'menu_setting.html', '0', 'icon-th-list', '5');
INSERT INTO `sys_menu` VALUES ('9', '流程管理', 'flow_view.html', '0', 'icon-random', '4');

-- ----------------------------
-- Table structure for `sys_userlevel`
-- ----------------------------
DROP TABLE IF EXISTS `sys_userlevel`;
CREATE TABLE `sys_userlevel` (
  `userlevel` int(11) NOT NULL,
  `level_name` varchar(400) DEFAULT NULL,
  `menu_ids` varchar(400) DEFAULT NULL,
  `user_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userlevel`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_userlevel
-- ----------------------------
INSERT INTO `sys_userlevel` VALUES ('0', '普通用户', '2|9|', 'normal');
INSERT INTO `sys_userlevel` VALUES ('10', '专业部门管理员', '2|9|', 'normal');
INSERT INTO `sys_userlevel` VALUES ('20', '专业部门领导', '2|9|', 'approval');
INSERT INTO `sys_userlevel` VALUES ('30', '市场部对接人', '2|9|', 'normal');
INSERT INTO `sys_userlevel` VALUES ('40', '市场部管理员', '2|9|', 'approval');
INSERT INTO `sys_userlevel` VALUES ('50', '二级管理员', '2|9|', 'admin');
INSERT INTO `sys_userlevel` VALUES ('100', '一级管理员', '1|2|3|4|5|6|7|8|9|', 'admin');

-- ----------------------------
-- Table structure for `sys_users`
-- ----------------------------
DROP TABLE IF EXISTS `sys_users`;
CREATE TABLE `sys_users` (
  `username` varchar(200) NOT NULL,
  `password` varchar(300) DEFAULT NULL,
  `userlevel` int(11) DEFAULT '0',
  `login_time` varchar(20) DEFAULT NULL,
  `try_counts` int(11) DEFAULT '0',
  `brch_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_users
-- ----------------------------
INSERT INTO `sys_users` VALUES ('test', '96e79218965eb72c92a549dd5a330112', '100', '20190312 08:49:59', '0', null);
INSERT INTO `sys_users` VALUES ('test2', '96e79218965eb72c92a549dd5a330112', '50', '20180308 17:06:42', '0', null);

-- ----------------------------
-- Table structure for `wx_access`
-- ----------------------------
DROP TABLE IF EXISTS `wx_access`;
CREATE TABLE `wx_access` (
  `serial` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `wx_token` varchar(200) DEFAULT NULL,
  `wx_expire` varchar(200) DEFAULT '0',
  PRIMARY KEY (`serial`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of wx_access
-- ----------------------------
INSERT INTO `wx_access` VALUES ('1', '32_oN2r8fojv-AHX6sCIzbVnAW5fasF-mjRwtFf_by85tHEtF_rh3ocuJdUylN4prMxaoGy3LEPZW7_eb1WqCpWSstsBlp4CcUTWHdyXdNQxutq8Ro_0lKeEleByAHLO5O24zuDtAefM6iEpaEZJFMfAAACYF', '1587116370110');

-- ----------------------------
-- Table structure for `wx_appinfo`
-- ----------------------------
DROP TABLE IF EXISTS `wx_appinfo`;
CREATE TABLE `wx_appinfo` (
  `appid` varchar(255) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `expire` varchar(255) DEFAULT NULL,
  `app_name` varchar(255) DEFAULT NULL,
  `app_img` varchar(255) DEFAULT NULL,
  `secret` varchar(255) DEFAULT NULL,
  `cloud_env` varchar(255) DEFAULT NULL,
  `list_keys` text,
  `list_query` text,
  PRIMARY KEY (`appid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of wx_appinfo
-- ----------------------------
