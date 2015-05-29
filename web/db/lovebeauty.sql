/*
Navicat MySQL Data Transfer

Source Server         : localconn
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : lovebeauty

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2015-05-28 01:16:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `advertisement`
-- ----------------------------
DROP TABLE IF EXISTS `advertisement`;
CREATE TABLE `advertisement` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` bit(1) NOT NULL COMMENT '广告类型 0外部链接 1内部链接',
  `picname` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '广告图片',
  `url` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '广告链接',
  `backup` mediumtext COLLATE utf8_bin COMMENT '广告备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of advertisement
-- ----------------------------

-- ----------------------------
-- Table structure for `book_info`
-- ----------------------------
DROP TABLE IF EXISTS `book_info`;
CREATE TABLE `book_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userid` int(11) NOT NULL COMMENT '预约人id',
  `description` mediumtext COLLATE utf8_bin COMMENT '预约简介',
  `picname` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '效果图',
  `booktime` timestamp NULL DEFAULT NULL COMMENT '预约时间',
  PRIMARY KEY (`id`),
  KEY `fk_bi_userid` (`userid`),
  CONSTRAINT `fk_bi_userid` FOREIGN KEY (`userid`) REFERENCES `customer` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of book_info
-- ----------------------------

-- ----------------------------
-- Table structure for `city`
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '名字',
  `opentime` varchar(20) COLLATE utf8_bin DEFAULT 'CURRENT_TIMESTAMP' COMMENT '开通日期',
  `stoptime` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '停用时间',
  `state` bit(1) DEFAULT b'1' COMMENT '城市状态 0停用 1开通',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of city
-- ----------------------------
INSERT INTO `city` VALUES ('1', '北京', '', '2015-05-26 22:18:15', '');
INSERT INTO `city` VALUES ('2', '天津', '2015-05-24 16:41:20', '', '');
INSERT INTO `city` VALUES ('3', '重庆', '2015-05-24 16:41:20', '', '');
INSERT INTO `city` VALUES ('26', '青岛', '2015-05-24 16:41:19', '', '');
INSERT INTO `city` VALUES ('42', '北京', '2015-05-24 16:41:19', '', '');
INSERT INTO `city` VALUES ('43', '北京', '2015-05-24 16:41:18', '', '');
INSERT INTO `city` VALUES ('44', '北京', '2015-05-26 22:22:00', '', '');
INSERT INTO `city` VALUES ('45', '北京', '2015-05-24 16:41:33', null, '');
INSERT INTO `city` VALUES ('46', '北京', '2015-05-24 16:41:37', null, '');
INSERT INTO `city` VALUES ('47', '北京', '2015-05-24 16:41:41', null, '');
INSERT INTO `city` VALUES ('48', '重庆', '2015-05-24 16:41:46', null, '');
INSERT INTO `city` VALUES ('49', '青岛', '', '2015-05-24 16:42:05', '');

-- ----------------------------
-- Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `demoid` int(11) NOT NULL,
  `score` int(11) NOT NULL,
  `comment` mediumtext COLLATE utf8_bin,
  `customerid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_com_demoid` (`demoid`),
  KEY `fk_com_customid` (`customerid`),
  CONSTRAINT `fk_com_customid` FOREIGN KEY (`customerid`) REFERENCES `customer` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_com_demoid` FOREIGN KEY (`demoid`) REFERENCES `demo` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('1', '39', '5', 0x6464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464, '26');

-- ----------------------------
-- Table structure for `common_address`
-- ----------------------------
DROP TABLE IF EXISTS `common_address`;
CREATE TABLE `common_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL COMMENT '用户id',
  `address` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '常用地址',
  PRIMARY KEY (`id`),
  KEY `fk_ca_userid` (`userid`),
  CONSTRAINT `fk_ca_userid` FOREIGN KEY (`userid`) REFERENCES `customer` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of common_address
-- ----------------------------

-- ----------------------------
-- Table structure for `customer`
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account` varchar(11) COLLATE utf8_bin NOT NULL COMMENT '（手机号）账户（需校验11位）',
  `password` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `regip` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT '注册ip',
  `loginip` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT '登录ip',
  `forbidden` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否被禁用 0没被禁用 1禁用',
  `city` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '所在城市',
  `jubao` int(11) DEFAULT '0' COMMENT '记录举报次数',
  `regtime` timestamp NULL DEFAULT NULL COMMENT '注册时间',
  `logintime` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_c_account` (`account`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('26', '13333333333', 'adminroot', '106.39.2.226', null, '', null, '3', '2015-05-15 12:59:54', null);

-- ----------------------------
-- Table structure for `demo`
-- ----------------------------
DROP TABLE IF EXISTS `demo`;
CREATE TABLE `demo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sellerid` int(11) NOT NULL COMMENT '所属卖家id',
  `name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '作品名称',
  `description` mediumtext COLLATE utf8_bin NOT NULL COMMENT '作品描述',
  `picname` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '作品图片',
  `price` decimal(11,2) NOT NULL COMMENT '作品价格',
  `PreferentialPrice` decimal(11,2) DEFAULT NULL COMMENT '首次优惠价格',
  `booktime` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '可预约时间',
  `fileEName` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `demoType` int(2) NOT NULL COMMENT '作品类型 0 美甲 1 美睫 2美容 3 化妆造型 4美足',
  PRIMARY KEY (`id`),
  KEY `fk_d_sellerid` (`sellerid`),
  CONSTRAINT `fk_d_sellerid` FOREIGN KEY (`sellerid`) REFERENCES `seller` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of demo
-- ----------------------------
INSERT INTO `demo` VALUES ('39', '13', '2', 0x646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464, '30-618723.jpg', '2222.00', '22.00', '22', 'pic_13_xoVM15CRGLm4wYsIMowOtA.jpg', '0');
INSERT INTO `demo` VALUES ('43', '13', '6666', 0x646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464646464, '暴风截图20155135167252.jpg', '666.00', '666.00', '66', 'pic_13_50jMyPYbL2GAHXCPCT62w.jpg', '2');

-- ----------------------------
-- Table structure for `manager`
-- ----------------------------
DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) COLLATE utf8_bin NOT NULL,
  `password` varchar(30) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_m_account` (`account`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of manager
-- ----------------------------
INSERT INTO `manager` VALUES ('1', 'nanmeiying', 'nanmeiying');
INSERT INTO `manager` VALUES ('2', 'admin', 'admin');

-- ----------------------------
-- Table structure for `manicure_division`
-- ----------------------------
DROP TABLE IF EXISTS `manicure_division`;
CREATE TABLE `manicure_division` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `sex` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '性别',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `star` int(11) DEFAULT NULL COMMENT '星级',
  `headimg` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
  `serverScope` mediumtext COLLATE utf8_bin COMMENT '服务商圈',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of manicure_division
-- ----------------------------

-- ----------------------------
-- Table structure for `order`
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '订单id',
  `userid` int(11) NOT NULL COMMENT '买家（用户）id',
  `sellerid` int(11) NOT NULL COMMENT '卖家id',
  `state` int(11) NOT NULL COMMENT '订单状态 1未付款 2交易成功 3取消订单',
  `demoid` int(11) NOT NULL COMMENT '作品id',
  `ordertime` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '预约时间',
  `paytime` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '付款时间',
  PRIMARY KEY (`id`),
  KEY `fk_o_sellerid` (`sellerid`),
  KEY `fk_o_userid` (`userid`),
  KEY `fk_o_demoid` (`demoid`),
  CONSTRAINT `fk_o_demoid` FOREIGN KEY (`demoid`) REFERENCES `demo` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_o_sellerid` FOREIGN KEY (`sellerid`) REFERENCES `seller` (`id`),
  CONSTRAINT `fk_o_userid` FOREIGN KEY (`userid`) REFERENCES `customer` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES ('fdsfds', '26', '13', '2', '39', '2015-05-27 23:06:30', '2015-05-27 23:34:32');
INSERT INTO `order` VALUES ('rrrrr', '26', '13', '2', '39', '2015-05-27 23:06:48', '2015-05-27 23:06:48');
INSERT INTO `order` VALUES ('www', '26', '13', '3', '39', '2015-05-27 23:07:33', '');

-- ----------------------------
-- Table structure for `privateorder`
-- ----------------------------
DROP TABLE IF EXISTS `privateorder`;
CREATE TABLE `privateorder` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userid` int(11) NOT NULL COMMENT '客户id',
  `sellerid` int(11) DEFAULT NULL COMMENT '商家id',
  `filename` varchar(100) COLLATE utf8_bin DEFAULT '' COMMENT '模型图片',
  `state` int(1) NOT NULL COMMENT '私人订制状态 0未指定 1已指定 2已成功',
  `description` mediumtext COLLATE utf8_bin COMMENT '需求描述',
  `price` decimal(13,2) DEFAULT NULL COMMENT '心理价格',
  `successtime` varchar(20) COLLATE utf8_bin DEFAULT '' COMMENT '交易成功实时间',
  `ordertime` varchar(20) COLLATE utf8_bin DEFAULT '' COMMENT '下单时间',
  PRIMARY KEY (`id`),
  KEY `fk_pri_userid` (`userid`),
  KEY `fk_pri_sellerid` (`sellerid`),
  CONSTRAINT `fk_pri_sellerid` FOREIGN KEY (`sellerid`) REFERENCES `seller` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_pri_userid` FOREIGN KEY (`userid`) REFERENCES `customer` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of privateorder
-- ----------------------------
INSERT INTO `privateorder` VALUES ('1', '1', '13', '', '2', 0xE68891E8A681E79A84E69588E69E9CE4BDA0E5AE9EE78EB0E4B88DE4BA86, '12.00', '2015-05-26 12:25:19', '2015-05-26 12:25:19');
INSERT INTO `privateorder` VALUES ('2', '1', '13', 'hbvf', '2', null, '1.00', '2', '2');
INSERT INTO `privateorder` VALUES ('3', '1', '13', 'gfd', '2', null, '1.00', '2', '2');
INSERT INTO `privateorder` VALUES ('4', '1', '13', 'f', '2', null, '1.00', '2', '2');
INSERT INTO `privateorder` VALUES ('5', '1', '13', 'f', '2', null, '1.00', '2', '2');
INSERT INTO `privateorder` VALUES ('6', '1', '13', 'f', '2', null, '1.00', '2', '2');
INSERT INTO `privateorder` VALUES ('7', '1', '13', 'f', '2', null, '1.00', '2', '2');
INSERT INTO `privateorder` VALUES ('8', '1', '13', 'f', '1', null, '1.00', '22', '2');
INSERT INTO `privateorder` VALUES ('9', '1', '13', 'f', '1', null, '11.00', '2', '2');
INSERT INTO `privateorder` VALUES ('10', '1', '13', 'f', '2', null, '1.00', '2015-05-26 13:47:32', '2');
INSERT INTO `privateorder` VALUES ('11', '1', '13', 'tets.jpg', '1', null, '15.00', '', 'fds');

-- ----------------------------
-- Table structure for `report_info`
-- ----------------------------
DROP TABLE IF EXISTS `report_info`;
CREATE TABLE `report_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `reportid` int(11) NOT NULL COMMENT '举报者id',
  `reportedid` int(11) NOT NULL COMMENT '被举报者id',
  `reporttype` bit(1) NOT NULL COMMENT '举报类型 0买家举报卖家  1卖家举报买家',
  `reason` mediumtext COLLATE utf8_bin NOT NULL COMMENT '举报理由',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of report_info
-- ----------------------------
INSERT INTO `report_info` VALUES ('1', '13', '26', '', 0x66647361666473);
INSERT INTO `report_info` VALUES ('2', '13', '26', '', 0x666473666473);
INSERT INTO `report_info` VALUES ('3', '13', '26', '', 0x666473666473);
INSERT INTO `report_info` VALUES ('4', '13', '26', '', 0x66736461666473);
INSERT INTO `report_info` VALUES ('5', '13', '26', '', 0x6666647361666473);

-- ----------------------------
-- Table structure for `seller`
-- ----------------------------
DROP TABLE IF EXISTS `seller`;
CREATE TABLE `seller` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `regip` varchar(15) COLLATE utf8_bin DEFAULT '' COMMENT '注册ip',
  `loginip` varchar(15) COLLATE utf8_bin DEFAULT '' COMMENT '登录ip',
  `regtime` varchar(20) COLLATE utf8_bin DEFAULT '',
  `logintime` varchar(20) COLLATE utf8_bin DEFAULT '',
  `account` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '账号',
  `password` varchar(30) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '密码',
  `checked` int(2) NOT NULL DEFAULT '0' COMMENT '是否通过审核  0否 1是',
  `jubao` int(11) NOT NULL DEFAULT '0' COMMENT '记录举报次数',
  `checkedtime` varchar(20) COLLATE utf8_bin DEFAULT '' COMMENT '审核时间',
  `authed` int(2) NOT NULL DEFAULT '0' COMMENT '是否认证',
  `auth_time` varchar(20) COLLATE utf8_bin DEFAULT '' COMMENT '认证时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_s_account` (`account`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of seller
-- ----------------------------
INSERT INTO `seller` VALUES ('13', '127.0.0.1', '127.0.0.1', '2015-05-13 13:32:27', '2015-05-28 00:50:17', 'aaaaaa', 'aaaaaa', '1', '0', null, '1', null);
INSERT INTO `seller` VALUES ('14', '127.0.0.1', '106.39.2.226', '2015-05-13 00:33:49', '2015-05-15 08:25:08', 'nanmeiying2', 'nanmeiying2', '1', '0', null, '0', null);
INSERT INTO `seller` VALUES ('15', '58.56.176.46', '58.56.176.46', '2015-05-13 15:18:48', '2015-05-13 15:18:57', 'wuzq', '123456', '1', '0', '2015-05-28 00:57:05', '0', null);
INSERT INTO `seller` VALUES ('16', '45.33.55.240', '45.33.55.240', '2015-05-14 22:14:44', '2015-05-14 22:14:50', 'nanmeiying88', 'nSGD6MkIyUenVRDVMf5FQ', '0', '0', null, '0', null);
INSERT INTO `seller` VALUES ('17', '114.248.204.47', '114.248.204.47', '2015-05-14 22:52:40', '2015-05-14 22:52:47', '1234', '123456', '1', '0', '2015-05-28 00:57:09', '0', null);
INSERT INTO `seller` VALUES ('18', '106.39.2.226', '106.39.2.226', '2015-05-15 08:25:34', '2015-05-15 08:25:43', 'nanmeiying3', 'nanmeiying3', '0', '0', null, '0', null);
INSERT INTO `seller` VALUES ('21', '58.56.176.14', '58.56.176.14', '2015-05-26 18:20:09', '2015-05-26 22:21:06', 'wuzhiqian', 'wuzhiqian', '0', '0', null, '0', null);
INSERT INTO `seller` VALUES ('22', '127.0.0.1', '127.0.0.1', '2015-05-27 23:55:15', '2015-05-28 00:15:59', 'bbbbbb', 'bbbbbb', '1', '0', '2015-05-28 00:07:06', '1', '');
INSERT INTO `seller` VALUES ('23', '127.0.0.1', '127.0.0.1', '2015-05-28 00:16:21', '2015-05-28 00:30:26', 'cccccc', 'cccccc', '1', '0', '2015-05-28 00:18:04', '1', '');
INSERT INTO `seller` VALUES ('24', '127.0.0.1', '127.0.0.1', '2015-05-28 00:30:57', '2015-05-28 00:31:02', 'dddddd', 'dddddd', '1', '0', '2015-05-28 00:35:13', '1', '');
INSERT INTO `seller` VALUES ('25', '127.0.0.1', '127.0.0.1', '2015-05-28 00:34:11', '2015-05-28 00:34:16', 'eeeeee', 'eeeeee', '0', '0', '', '1', '');
INSERT INTO `seller` VALUES ('26', '127.0.0.1', '127.0.0.1', '2015-05-28 00:50:37', '2015-05-28 01:05:49', 'gggggg', 'gggggg', '1', '0', '2015-05-28 01:06:57', '1', '');
INSERT INTO `seller` VALUES ('27', '127.0.0.1', '127.0.0.1', '2015-05-28 00:52:45', '2015-05-28 00:52:48', 'ssssss', 'ssssss', '0', '0', '', '1', '');
INSERT INTO `seller` VALUES ('28', '127.0.0.1', '127.0.0.1', '2015-05-28 01:07:21', '2015-05-28 01:12:04', 'tttttt', 'tttttt', '0', '0', '', '1', '');
INSERT INTO `seller` VALUES ('29', '127.0.0.1', '127.0.0.1', '2015-05-28 01:12:17', '2015-05-28 01:12:21', 'kkkkkk', 'kkkkkk', '0', '0', '', '1', '');

-- ----------------------------
-- Table structure for `seller_validate_info`
-- ----------------------------
DROP TABLE IF EXISTS `seller_validate_info`;
CREATE TABLE `seller_validate_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sellerid` int(11) NOT NULL COMMENT '卖家id',
  `name` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '用户名称',
  `identify` varchar(18) COLLATE utf8_bin NOT NULL COMMENT '身份证号',
  `shopname` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '店名',
  `address` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '地址',
  `payaccount` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '支付宝账户',
  `servicescope` mediumtext COLLATE utf8_bin NOT NULL COMMENT '服务范围',
  `telephone` varchar(11) COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `sex` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `birthday` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `email` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `head_img` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `identify_img` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `alipay_key` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `alipay_pid` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_sv_sellerid` (`sellerid`),
  CONSTRAINT `fk_sv_sellerid` FOREIGN KEY (`sellerid`) REFERENCES `seller` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of seller_validate_info
-- ----------------------------
INSERT INTO `seller_validate_info` VALUES ('6', '13', '王小二', '111111111111111111', '小二', '小二北京', '111111@qq.com', 0xE88C83E5BEB7E890A8E58F91E7949FE5A4A720646420, '12222222222', null, null, null, null, null, null, null);
INSERT INTO `seller_validate_info` VALUES ('7', '22', '张三', '122222222222222222', '张三店铺', '北京店铺', 'ssss@qq.com', 0xE5BE88E5A5BD, '12345678903', '0', '1990-09-09', 'fdsfdsa@qq.com', 'pic_head_1432742740589.jpg', 'pic_identify_1432742740589.jpg', '3333333333333', 'uuuuuuuuuuu');
INSERT INTO `seller_validate_info` VALUES ('8', '23', 'zhaoliu', '122222222222222222', 'fjdlsk', 'fjdsla', 'sss@qq.com', 0x666473666473, '12222222222', '0', '1990-09-09', 'fdsa@qq.com', 'pic_head_1432744234203.jpg', 'pic_identify_1432744234233.jpg', 'fjdslfjdlks', 'ffdsafds');
INSERT INTO `seller_validate_info` VALUES ('9', '24', 'fdsafds', '122222222222222222', 'fdsa', 'fdsa', 'fdsa@qq.com', 0x666473, '12222222222', '0', '12222222222', '222@qq.com', 'pic_head_1432744305234.jpg', 'pic_identify_1432744305234.jpg', 'fdsa', 'fdsa');
INSERT INTO `seller_validate_info` VALUES ('10', '25', 'fdsa', '122222222222222222', 'fdsa', 'fdsafdsa', 'fdsa@qq.com', 0x66647361, '12222222222', '0', '33333', 'fdsfds@qq.com', 'pic_head_1432744495921.jpg', 'pic_identify_1432744495951.jpg', 'fdsa', 'fdsa');
INSERT INTO `seller_validate_info` VALUES ('11', '26', 'fdsa', '122222222222222222', 'fdsa', 'fdsa', 'fdsa@qq.com', 0x647366647361, '12222222222', '0', 'fdsa', 'fsdafsd@qq.com', 'pic_head_1432746398882.jpg', 'pic_identify_1432746398912.jpg', 'fsafdsa', 'fsdaf');
INSERT INTO `seller_validate_info` VALUES ('12', '27', 'fdsfsd', '122222222222222222', 'gfds', 'gfds', 'gfd@qq.com', 0x667364666473, '12222222222', '0', 'fdsfds', 'fdsfsd@qq.com', 'pic_head_1432745606978.jpg', 'pic_identify_1432745607008.jpg', 'gfdg', 'fdsg');
INSERT INTO `seller_validate_info` VALUES ('13', '28', 'fdsa', '122222222221222222', 'fdsf', 'fdsa', 'fdsafds@qq.com', 0x666473, '12222222222', '0', '12222', 'fsdafsd@qq.com', 'pic_head_1432746597390.jpg', 'pic_identify_1432746597420.jpg', 'fdsa', 'fds');
INSERT INTO `seller_validate_info` VALUES ('14', '29', 'cxzczxc', '122222222221222222', 'fdsa', 'fdsa', 'fdsa@qq.com', 0x61667364, '12222222222', '0', 'fdsf', 'vcxzcx@qe.com', 'pic_head_1432746775801.jpg', 'pic_identify_1432746775831.jpg', 'fdsfsd', 'fds');
