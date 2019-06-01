/*
Navicat MySQL Data Transfer

Source Server         : MySql80
Source Server Version : 80013
Source Host           : 127.0.0.1:3306
Source Database       : xhh

Target Server Type    : MYSQL
Target Server Version : 80013
File Encoding         : 65001

Date: 2019-06-01 12:11:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for coupon_type
-- ----------------------------
DROP TABLE IF EXISTS `coupon_type`;
CREATE TABLE `coupon_type` (
  `coupon_name` varchar(45) NOT NULL COMMENT '?????',
  `create_time` datetime DEFAULT NULL COMMENT '????',
  `user_id` varchar(32) DEFAULT NULL COMMENT '????',
  PRIMARY KEY (`coupon_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of coupon_type
-- ----------------------------
INSERT INTO `coupon_type` VALUES ('50元优惠卷', '2019-05-10 10:04:00', '1');

-- ----------------------------
-- Table structure for discount_coupon
-- ----------------------------
DROP TABLE IF EXISTS `discount_coupon`;
CREATE TABLE `discount_coupon` (
  `coupon_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `coupon_name` varchar(45) NOT NULL,
  `face_price` bigint(20) NOT NULL DEFAULT '0',
  `pay_price` bigint(20) NOT NULL DEFAULT '0' COMMENT '????',
  `source` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0 ????\n1 ????',
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `addtime` datetime NOT NULL,
  `coupon_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0 ???\n1 ???',
  `coupon_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0 ???',
  `swit` varchar(3) NOT NULL DEFAULT 'off' COMMENT 'on???off??',
  PRIMARY KEY (`coupon_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of discount_coupon
-- ----------------------------
INSERT INTO `discount_coupon` VALUES ('1', '5元优惠券', '5', '0', '0', '2019-03-01 08:45:03', '2019-05-28 08:45:05', '2019-01-31 20:45:38', '0', '0', 'on');
INSERT INTO `discount_coupon` VALUES ('2', '8元优惠券', '8', '0', '0', '2019-03-01 08:45:21', '2019-05-28 08:45:23', '2019-01-31 20:45:55', '0', '0', 'on');

-- ----------------------------
-- Table structure for store_admin
-- ----------------------------
DROP TABLE IF EXISTS `store_admin`;
CREATE TABLE `store_admin` (
  `admin_id` varchar(32) NOT NULL COMMENT '主键',
  `admin_username` varchar(32) NOT NULL COMMENT '管理员账号',
  `admin_password` varchar(128) NOT NULL COMMENT '管理员密码',
  `admin_type` varchar(1) NOT NULL COMMENT '0：系统管理员',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员表';

-- ----------------------------
-- Records of store_admin
-- ----------------------------
INSERT INTO `store_admin` VALUES ('1', 'super', 'c3VwZXI=', '1', '2019-04-03 09:37:06');
INSERT INTO `store_admin` VALUES ('30b97adde559484aba1c30bcf51f8943', 'test', 'MTIzNDU2', ' ', '2019-04-15 10:44:30');

-- ----------------------------
-- Table structure for store_cart
-- ----------------------------
DROP TABLE IF EXISTS `store_cart`;
CREATE TABLE `store_cart` (
  `cart_id` varchar(32) NOT NULL COMMENT '主键',
  `cart_uid` varchar(32) NOT NULL COMMENT '用户主键',
  `cart_gid` varchar(32) NOT NULL COMMENT '商品主键',
  `cart_num` int(10) NOT NULL COMMENT '购物车商品数量',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`cart_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='购物车表';

-- ----------------------------
-- Records of store_cart
-- ----------------------------
INSERT INTO `store_cart` VALUES ('8c77c1a26b931348', '9311e0fcffe1b4ff', '09', '1', '2019-05-17 15:51:02');
INSERT INTO `store_cart` VALUES ('943d8f55a2514df4', '9311e0fcffe1b4ff', '12', '1', '2019-05-15 14:15:31');
INSERT INTO `store_cart` VALUES ('9b272370d3cb6577', '9311e0fcffe1b4ff', '01', '8', '2019-05-17 16:30:09');
INSERT INTO `store_cart` VALUES ('ba0a18566cc475a5', '9311e0fcffe1b4ff', '1', '1', '2019-05-07 08:56:21');

-- ----------------------------
-- Table structure for store_consume
-- ----------------------------
DROP TABLE IF EXISTS `store_consume`;
CREATE TABLE `store_consume` (
  `consume_id` varchar(32) NOT NULL COMMENT '主键',
  `consume_no` varchar(32) NOT NULL COMMENT '订单编号',
  `consume_coin` varchar(10) NOT NULL COMMENT '消费金额',
  `consume_uid` varchar(32) NOT NULL COMMENT '用户主键',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`consume_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消费记录表';

-- ----------------------------
-- Records of store_consume
-- ----------------------------
INSERT INTO `store_consume` VALUES ('1', '', '100', '9311e0fcffe1b4ff', '2019-03-14 17:03:55');
INSERT INTO `store_consume` VALUES ('2', '', '200', '9311e0fcffe1b4ff', '2019-03-14 17:04:05');
INSERT INTO `store_consume` VALUES ('82282601ccb0fa2e', 'ON201904301551317', '80', '9311e0fcffe1b4ff', '2019-04-30 15:51:35');
INSERT INTO `store_consume` VALUES ('82700f19890a8c84', 'ON201904301742475', '244', '9311e0fcffe1b4ff', '2019-04-30 17:43:06');
INSERT INTO `store_consume` VALUES ('8b21aa101b43975e', 'ON201904301111072', '40', '9311e0fcffe1b4ff', '2019-04-30 11:11:12');
INSERT INTO `store_consume` VALUES ('90d4a3cafb2fd802', 'ON201905081544458', '205', '9311e0fcffe1b4ff', '2019-05-08 15:44:51');
INSERT INTO `store_consume` VALUES ('95ed0eaee27c48c6', 'ON201904301709486', '449', '9311e0fcffe1b4ff', '2019-04-30 17:10:18');
INSERT INTO `store_consume` VALUES ('9b1d4128f68b6a29', 'ON201904301720071', '244', '9311e0fcffe1b4ff', '2019-04-30 17:20:50');
INSERT INTO `store_consume` VALUES ('a0a80fc9a95e827a', 'ON201904301503081', '41', '9311e0fcffe1b4ff', '2019-04-30 15:09:58');
INSERT INTO `store_consume` VALUES ('a4df5f7d6a949649', 'ON201905091703559', '820', '9311e0fcffe1b4ff', '2019-05-09 17:04:03');
INSERT INTO `store_consume` VALUES ('a6ae7e55af337336', 'ON201905061633428', '41', '9311e0fcffe1b4ff', '2019-05-06 16:33:46');
INSERT INTO `store_consume` VALUES ('a7406cfdb1f60e85', 'ON201905081517031', '205', '9311e0fcffe1b4ff', '2019-05-08 15:17:09');
INSERT INTO `store_consume` VALUES ('b02297c4eeeaf341', 'ON201905061626042', '41', '9311e0fcffe1b4ff', '2019-05-06 16:26:09');
INSERT INTO `store_consume` VALUES ('b1e12089cd193cbd', 'ON201904301739391', '238', '9311e0fcffe1b4ff', '2019-04-30 17:40:29');
INSERT INTO `store_consume` VALUES ('b4c6ac6d58e515ba', 'ON201905061636351', '168', '9311e0fcffe1b4ff', '2019-05-06 16:38:12');
INSERT INTO `store_consume` VALUES ('b8450563de501d80', 'ON201904301703571', '238', '9311e0fcffe1b4ff', '2019-04-30 17:05:25');
INSERT INTO `store_consume` VALUES ('b84dc66cec0af1e9', 'ON201905061635254', '120', '9311e0fcffe1b4ff', '2019-05-06 16:35:30');
INSERT INTO `store_consume` VALUES ('b8ca33d7b7e54ae8', 'ON201905061634453', '80', '9311e0fcffe1b4ff', '2019-05-06 16:34:49');
INSERT INTO `store_consume` VALUES ('b9062b9a1c741cc9', 'ON201905061648021', '84', '9311e0fcffe1b4ff', '2019-05-06 16:48:19');
INSERT INTO `store_consume` VALUES ('b9dd09908ba9fc9b', 'ON201904301505191', '40', '9311e0fcffe1b4ff', '2019-04-30 15:08:42');
INSERT INTO `store_consume` VALUES ('bc7401f9195c8706', 'ON201905061729267', '126', '9311e0fcffe1b4ff', '2019-05-06 17:29:55');

-- ----------------------------
-- Table structure for store_coupon
-- ----------------------------
DROP TABLE IF EXISTS `store_coupon`;
CREATE TABLE `store_coupon` (
  `coupon_id` varchar(32) NOT NULL COMMENT '主键',
  `coupon_uid` varchar(32) NOT NULL COMMENT '用户主键',
  `coupon_coin` varchar(10) NOT NULL COMMENT '优惠金额',
  `coupon_status` varchar(1) NOT NULL COMMENT '0：未使用，1：已使用',
  `coupon_start` datetime NOT NULL COMMENT '起始时间',
  `coupon_end` datetime NOT NULL COMMENT '结束时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`coupon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券表';

-- ----------------------------
-- Records of store_coupon
-- ----------------------------
INSERT INTO `store_coupon` VALUES ('1', '9311e0fcffe1b4ff', '2', '0', '2019-04-09 15:43:21', '2019-05-31 15:43:24', '2019-04-10 15:52:48', '2019-04-09 15:43:57');

-- ----------------------------
-- Table structure for store_express
-- ----------------------------
DROP TABLE IF EXISTS `store_express`;
CREATE TABLE `store_express` (
  `express_id` varchar(32) NOT NULL COMMENT '主键',
  `express_no` varchar(32) NOT NULL COMMENT '运单编号',
  `express_ono` varchar(32) NOT NULL COMMENT '订单编号',
  `express_type` varchar(32) NOT NULL COMMENT '快递公司',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`express_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运单表';

-- ----------------------------
-- Records of store_express
-- ----------------------------
INSERT INTO `store_express` VALUES ('1', '1', '1', '圆通', '2019-03-15 00:00:00');
INSERT INTO `store_express` VALUES ('2', '2', '1', '圆通', '2019-03-15 00:00:00');

-- ----------------------------
-- Table structure for store_freight
-- ----------------------------
DROP TABLE IF EXISTS `store_freight`;
CREATE TABLE `store_freight` (
  `freight_id` varchar(32) NOT NULL COMMENT '主键',
  `freight_type` varchar(6) NOT NULL COMMENT '快递公司',
  `freight_city` varchar(32) NOT NULL COMMENT '始发城市',
  `freight_same` varchar(10) NOT NULL COMMENT '同城价格',
  `freight_general` varchar(10) NOT NULL COMMENT '一般地区价格',
  `freight_remote` varchar(10) NOT NULL COMMENT '偏远地区价格',
  PRIMARY KEY (`freight_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运费表';

-- ----------------------------
-- Records of store_freight
-- ----------------------------
INSERT INTO `store_freight` VALUES ('1', '圆通快递', '长治市', '4', '6', '10');
INSERT INTO `store_freight` VALUES ('2', '申通快递', '长治市', '5', '8', '12');
INSERT INTO `store_freight` VALUES ('3', '中通快递', '长治市', '6', '10', '14');
INSERT INTO `store_freight` VALUES ('4', '韵达快递', '长治市', '8', '12', '16');

-- ----------------------------
-- Table structure for store_gimg
-- ----------------------------
DROP TABLE IF EXISTS `store_gimg`;
CREATE TABLE `store_gimg` (
  `gimg_id` varchar(32) NOT NULL COMMENT '主键',
  `gimg_url` varchar(256) NOT NULL COMMENT '商品主图',
  `gimg_gid` varchar(32) NOT NULL COMMENT '商品主键',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`gimg_id`),
  KEY `商品ID` (`gimg_gid`) USING BTREE,
  CONSTRAINT `store_gimg_ibfk_1` FOREIGN KEY (`gimg_gid`) REFERENCES `store_goods` (`goods_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品主图表';

-- ----------------------------
-- Records of store_gimg
-- ----------------------------
INSERT INTO `store_gimg` VALUES ('01', 'https://img.alicdn.com/imgextra/i3/4215684831/O1CN011lYdrvHGv85SrSH_!!4215684831.jpg_430x430q90.jpg', '01', '2019-04-24 16:51:42');
INSERT INTO `store_gimg` VALUES ('02', 'https://img.alicdn.com/imgextra/i3/4215684831/O1CN01rogFTo1lYdrwFdx6V_!!4215684831.jpg_430x430q90.jpg', '01', '2019-04-24 16:53:51');
INSERT INTO `store_gimg` VALUES ('03', 'https://img.alicdn.com/imgextra/i4/4215684831/O1CN01zxOnZH1lYduZAaCt0_!!0-item_pic.jpg_430x430q90.jpg', '01', '2019-04-24 16:54:21');
INSERT INTO `store_gimg` VALUES ('04', 'https://img.alicdn.com/imgextra/i3/3015214310/O1CN016r5cyL1hi1YCrAzdH_!!3015214310.jpg_430x430q90.jpg', '02', '2019-04-24 16:54:38');
INSERT INTO `store_gimg` VALUES ('05', 'https://img.alicdn.com/imgextra/i1/3015214310/O1CN01kClqUp1hi1YF2nKUk_!!0-item_pic.jpg_430x430q90.jpg', '02', '2019-04-24 16:55:06');
INSERT INTO `store_gimg` VALUES ('06', 'https://img.alicdn.com/imgextra/i2/3015214310/O1CN01sMhlzp1hi1YDmfAIu_!!3015214310.jpg_430x430q90.jpg', '02', '2019-04-24 16:55:25');
INSERT INTO `store_gimg` VALUES ('07', 'https://img.alicdn.com/imgextra/i4/2939922051/O1CN01DvPaOj1R1Oqc58Was_!!2939922051.jpg_430x430q90.jpg', '03', '2019-04-24 16:55:49');
INSERT INTO `store_gimg` VALUES ('08', 'https://img.alicdn.com/imgextra/i4/2939922051/O1CN01lBXPDY1R1OqNaDxPK_!!2939922051.jpg_430x430q90.jpg', '03', '2019-04-24 16:56:06');
INSERT INTO `store_gimg` VALUES ('09', 'https://img.alicdn.com/imgextra/i3/2939922051/O1CN01Detbcj1R1OqOx6R53_!!2939922051.jpg_430x430q90.jpg', '03', '2019-04-24 16:56:33');
INSERT INTO `store_gimg` VALUES ('10', 'https://img.alicdn.com/imgextra/i3/4215684831/O1CN011lYdrvHGv85SrSH_!!4215684831.jpg_430x430q90.jpg', '04', '2019-05-14 17:54:35');
INSERT INTO `store_gimg` VALUES ('11', 'https://img.alicdn.com/imgextra/i3/4215684831/O1CN01rogFTo1lYdrwFdx6V_!!4215684831.jpg_430x430q90.jpg', '04', '2019-05-14 17:56:12');
INSERT INTO `store_gimg` VALUES ('12', 'https://img.alicdn.com/imgextra/i4/4215684831/O1CN01zxOnZH1lYduZAaCt0_!!0-item_pic.jpg_430x430q90.jpg', '04', '2019-05-14 17:56:36');
INSERT INTO `store_gimg` VALUES ('13', 'https://img.alicdn.com/imgextra/i3/3015214310/O1CN016r5cyL1hi1YCrAzdH_!!3015214310.jpg_430x430q90.jpg', '05', '2019-05-14 17:57:27');
INSERT INTO `store_gimg` VALUES ('14', 'https://img.alicdn.com/imgextra/i1/3015214310/O1CN01kClqUp1hi1YF2nKUk_!!0-item_pic.jpg_430x430q90.jpg', '05', '2019-05-14 17:57:43');
INSERT INTO `store_gimg` VALUES ('15', 'https://img.alicdn.com/imgextra/i2/3015214310/O1CN01sMhlzp1hi1YDmfAIu_!!3015214310.jpg_430x430q90.jpg', '05', '2019-05-14 17:58:13');
INSERT INTO `store_gimg` VALUES ('16', 'https://img.alicdn.com/imgextra/i4/2939922051/O1CN01DvPaOj1R1Oqc58Was_!!2939922051.jpg_430x430q90.jpg', '06', '2019-05-14 18:03:26');
INSERT INTO `store_gimg` VALUES ('17', 'https://img.alicdn.com/imgextra/i4/2939922051/O1CN01lBXPDY1R1OqNaDxPK_!!2939922051.jpg_430x430q90.jpg', '06', '2019-05-14 18:03:48');
INSERT INTO `store_gimg` VALUES ('18', 'https://img.alicdn.com/imgextra/i3/2939922051/O1CN01Detbcj1R1OqOx6R53_!!2939922051.jpg_430x430q90.jpg', '06', '2019-05-14 18:04:05');
INSERT INTO `store_gimg` VALUES ('19', 'https://img.alicdn.com/imgextra/i3/4215684831/O1CN011lYdrvHGv85SrSH_!!4215684831.jpg_430x430q90.jpg', '07', '2019-05-14 18:05:36');
INSERT INTO `store_gimg` VALUES ('20', 'https://img.alicdn.com/imgextra/i3/4215684831/O1CN01rogFTo1lYdrwFdx6V_!!4215684831.jpg_430x430q90.jpg', '07', '2019-05-14 18:05:57');
INSERT INTO `store_gimg` VALUES ('21', 'https://img.alicdn.com/imgextra/i4/4215684831/O1CN01zxOnZH1lYduZAaCt0_!!0-item_pic.jpg_430x430q90.jpg', '07', '2019-05-14 18:06:13');
INSERT INTO `store_gimg` VALUES ('22', 'https://img.alicdn.com/imgextra/i3/3015214310/O1CN016r5cyL1hi1YCrAzdH_!!3015214310.jpg_430x430q90.jpg', '08', '2019-05-14 18:06:41');
INSERT INTO `store_gimg` VALUES ('23', 'https://img.alicdn.com/imgextra/i1/3015214310/O1CN01kClqUp1hi1YF2nKUk_!!0-item_pic.jpg_430x430q90.jpg', '08', '2019-05-14 18:06:59');
INSERT INTO `store_gimg` VALUES ('24', 'https://img.alicdn.com/imgextra/i2/3015214310/O1CN01sMhlzp1hi1YDmfAIu_!!3015214310.jpg_430x430q90.jpg', '08', '2019-05-14 18:07:16');
INSERT INTO `store_gimg` VALUES ('25', 'https://img.alicdn.com/imgextra/i4/2939922051/O1CN01DvPaOj1R1Oqc58Was_!!2939922051.jpg_430x430q90.jpg', '09', '2019-05-14 18:08:08');
INSERT INTO `store_gimg` VALUES ('26', 'https://img.alicdn.com/imgextra/i4/2939922051/O1CN01lBXPDY1R1OqNaDxPK_!!2939922051.jpg_430x430q90.jpg', '09', '2019-05-14 18:08:22');
INSERT INTO `store_gimg` VALUES ('27', 'https://img.alicdn.com/imgextra/i3/2939922051/O1CN01Detbcj1R1OqOx6R53_!!2939922051.jpg_430x430q90.jpg', '09', '2019-05-14 18:08:37');
INSERT INTO `store_gimg` VALUES ('28', 'https://img.alicdn.com/imgextra/i3/4215684831/O1CN011lYdrvHGv85SrSH_!!4215684831.jpg_430x430q90.jpg', '10', '2019-05-15 08:53:46');
INSERT INTO `store_gimg` VALUES ('29', 'https://img.alicdn.com/imgextra/i3/4215684831/O1CN01rogFTo1lYdrwFdx6V_!!4215684831.jpg_430x430q90.jpg', '10', '2019-05-15 08:54:05');
INSERT INTO `store_gimg` VALUES ('30', 'https://img.alicdn.com/imgextra/i4/4215684831/O1CN01zxOnZH1lYduZAaCt0_!!0-item_pic.jpg_430x430q90.jpg', '10', '2019-05-15 08:54:21');
INSERT INTO `store_gimg` VALUES ('31', 'https://img.alicdn.com/imgextra/i3/3015214310/O1CN016r5cyL1hi1YCrAzdH_!!3015214310.jpg_430x430q90.jpg', '11', '2019-05-15 08:55:27');
INSERT INTO `store_gimg` VALUES ('32', 'https://img.alicdn.com/imgextra/i1/3015214310/O1CN01kClqUp1hi1YF2nKUk_!!0-item_pic.jpg_430x430q90.jpg', '11', '2019-05-15 08:55:45');
INSERT INTO `store_gimg` VALUES ('33', 'https://img.alicdn.com/imgextra/i2/3015214310/O1CN01sMhlzp1hi1YDmfAIu_!!3015214310.jpg_430x430q90.jpg', '11', '2019-05-15 08:56:05');
INSERT INTO `store_gimg` VALUES ('34', 'https://img.alicdn.com/imgextra/i4/2939922051/O1CN01DvPaOj1R1Oqc58Was_!!2939922051.jpg_430x430q90.jpg', '12', '2019-05-15 08:56:31');
INSERT INTO `store_gimg` VALUES ('35', 'https://img.alicdn.com/imgextra/i4/2939922051/O1CN01lBXPDY1R1OqNaDxPK_!!2939922051.jpg_430x430q90.jpg', '12', '2019-05-15 08:56:45');
INSERT INTO `store_gimg` VALUES ('36', 'https://img.alicdn.com/imgextra/i3/2939922051/O1CN01Detbcj1R1OqOx6R53_!!2939922051.jpg_430x430q90.jpg', '12', '2019-05-15 08:57:00');

-- ----------------------------
-- Table structure for store_goods
-- ----------------------------
DROP TABLE IF EXISTS `store_goods`;
CREATE TABLE `store_goods` (
  `goods_id` varchar(32) NOT NULL COMMENT '主键',
  `goods_title` varchar(32) NOT NULL COMMENT '商品标题',
  `goods_label` varchar(32) DEFAULT '' COMMENT '商品标签',
  `goods_describe` varchar(128) DEFAULT '' COMMENT '商品描述',
  `goods_image` varchar(256) DEFAULT NULL COMMENT '商品图片',
  `goods_library` varchar(10) NOT NULL COMMENT '入库价格',
  `goods_original` varchar(10) NOT NULL COMMENT '商品原价',
  `goods_price` varchar(10) NOT NULL COMMENT '商品现价',
  `goods_second` varchar(10) DEFAULT '' COMMENT '秒杀价',
  `goods_group` varchar(10) DEFAULT '' COMMENT '拼团价格',
  `goods_type` varchar(32) NOT NULL DEFAULT '' COMMENT '商品类型',
  `goods_spec` varchar(32) NOT NULL COMMENT '商品规格',
  `goods_inventory` int(10) NOT NULL COMMENT '商品库存',
  `goods_sales` int(10) NOT NULL DEFAULT '0' COMMENT '商品销量',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';

-- ----------------------------
-- Records of store_goods
-- ----------------------------
INSERT INTO `store_goods` VALUES ('01', '苹果（普通）', '6', '', 'https://img.alicdn.com/imgextra/i1/4215684831/O1CN011lYdrosZCsFptmV_!!4215684831.jpg', '100', '43', '40', '1', '38', '89e9464781f4c0', '500g', '200', '0', '2019-05-14 17:34:13');
INSERT INTO `store_goods` VALUES ('02', '番茄（普通）', '6', '', 'https://img.alicdn.com/imgextra/i3/3015214310/O1CN018kgpdu1hi1YCFKMrR_!!3015214310.jpg', '100', '43', '40', '1', '38', '89e9464781f4c1', '500g', '200', '0', '2019-05-14 17:34:13');
INSERT INTO `store_goods` VALUES ('03', '西瓜（普通）', '6', '', 'https://img.alicdn.com/imgextra/i4/2939922051/O1CN01Txzxs21R1OqK2fLwc_!!2939922051.jpg', '100', '43', '40', '1', '38', '89e9464781f4c2', '500g', '200', '0', '2019-05-14 17:34:13');
INSERT INTO `store_goods` VALUES ('04', '苹果（拼团）', '2', '', 'https://img.alicdn.com/imgextra/i3/4215684831/O1CN01rogFTo1lYdrwFdx6V_!!4215684831.jpg_430x430q90.jpg', '100', '43', '40', '20', '38', '89e9464781f4c3', '500g', '200', '0', '2019-05-14 17:34:13');
INSERT INTO `store_goods` VALUES ('05', '番茄（拼团）', '2', '', 'https://img.alicdn.com/imgextra/i3/3015214310/O1CN016r5cyL1hi1YCrAzdH_!!3015214310.jpg_430x430q90.jpg', '200', '43', '40', '100', '38', '89e9464781f4c4', '500g', '200', '0', '2019-05-14 17:34:13');
INSERT INTO `store_goods` VALUES ('06', '西瓜（拼团）', '2', '', 'https://img.alicdn.com/imgextra/i4/2939922051/O1CN01DvPaOj1R1Oqc58Was_!!2939922051.jpg_430x430q90.jpg', '100', '43', '40', '1', '38', '89e9464781f4c5', '500g', '200', '0', '2019-05-14 17:34:13');
INSERT INTO `store_goods` VALUES ('07', '苹果（秒杀）', '1', '', 'https://img.alicdn.com/imgextra/i3/4215684831/O1CN011lYdrvHGv85SrSH_!!4215684831.jpg_430x430q90.jpg', '100', '43', '40', '100', '38', '5', '500g', '200', '0', '2019-05-14 17:36:05');
INSERT INTO `store_goods` VALUES ('08', '番茄（秒杀）', '3', '', 'https://img.alicdn.com/imgextra/i3/3015214310/O1CN018kgpdu1hi1YCFKMrR_!!3015214310.jpg', '100', '43', '40', '1', '38', '89e9464781f4c7', '500g', '200', '0', '2019-05-14 17:37:04');
INSERT INTO `store_goods` VALUES ('09', '西瓜（秒杀）', '3', '', 'https://img.alicdn.com/imgextra/i4/2939922051/O1CN01lBXPDY1R1OqNaDxPK_!!2939922051.jpg_430x430q90.jpg', '100', '43', '40', '1', '38', '89e9464781f4c8', '500g', '199', '1', '2019-05-14 17:39:21');
INSERT INTO `store_goods` VALUES ('10', '苹果（推荐）', '4', '', 'https://img.alicdn.com/imgextra/i4/4215684831/O1CN01zxOnZH1lYduZAaCt0_!!0-item_pic.jpg_430x430q90.jpg', '100', '43', '40', '1', '38', '89e9464781f4c9', '500g', '200', '0', '2019-05-14 17:40:47');
INSERT INTO `store_goods` VALUES ('11', '番茄（推荐）', '4', '', 'https://img.alicdn.com/imgextra/i2/3015214310/O1CN01sMhlzp1hi1YDmfAIu_!!3015214310.jpg_430x430q90.jpg', '100', '43', '40', '1', '38', '89e9464781f410', '500g', '199', '1', '2019-05-14 17:41:40');
INSERT INTO `store_goods` VALUES ('12', '西瓜（推荐）', '4', '', 'https://img.alicdn.com/imgextra/i4/2939922051/O1CN01Txzxs21R1OqK2fLwc_!!2939922051.jpg', '100', '43', '40', '1', '38', '89e9464781f411', '500g', '2', '2', '2019-05-14 17:42:53');

-- ----------------------------
-- Table structure for store_goods_lower_limit
-- ----------------------------
DROP TABLE IF EXISTS `store_goods_lower_limit`;
CREATE TABLE `store_goods_lower_limit` (
  `id` varchar(32) NOT NULL DEFAULT '',
  `admin_id` varchar(32) NOT NULL,
  `goods_id` varchar(32) NOT NULL,
  `lower_limit` int(12) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `admin_username` varchar(32) DEFAULT NULL COMMENT '操作人姓名',
  `goods_title` varchar(32) DEFAULT NULL COMMENT '商品标题',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品库存下限表\r\n';

-- ----------------------------
-- Records of store_goods_lower_limit
-- ----------------------------
INSERT INTO `store_goods_lower_limit` VALUES ('80ba01c278f66271', '1', '12', '10', '2019-05-15 11:43:46', 'super', '西瓜（推荐）');
INSERT INTO `store_goods_lower_limit` VALUES ('86139c1f8f953c69', '1', '11', '1000', '2019-05-15 11:48:35', 'super', '番茄（推荐）');
INSERT INTO `store_goods_lower_limit` VALUES ('86cc84772cd0e174', '1', '10', '200', '2019-05-15 11:45:41', 'super', '苹果（推荐）');
INSERT INTO `store_goods_lower_limit` VALUES ('973e3fee042098fa', '1', '07', '1000', '2019-05-15 11:49:03', 'super', '苹果（秒杀）');
INSERT INTO `store_goods_lower_limit` VALUES ('99f76e284178fe75', '1', '12', '10', '2019-05-15 11:42:19', 'super', '西瓜（推荐）');
INSERT INTO `store_goods_lower_limit` VALUES ('9a07c1227904e8d2', '1', '10', '10', '2019-05-15 11:42:19', 'super', '苹果（推荐）');
INSERT INTO `store_goods_lower_limit` VALUES ('afd5f9683cfd0fa2', '1', '10', '10', '2019-05-15 11:43:46', 'super', '苹果（推荐）');
INSERT INTO `store_goods_lower_limit` VALUES ('b469eb229f12116a', '1', '11', '10', '2019-05-15 11:50:41', 'super', '番茄（推荐）');
INSERT INTO `store_goods_lower_limit` VALUES ('b74b3de0c3c2370f', '1', '12', '200', '2019-05-15 11:45:41', 'super', '西瓜（推荐）');
INSERT INTO `store_goods_lower_limit` VALUES ('b80551e77243b080', '1', '11', '10', '2019-05-15 11:43:46', 'super', '番茄（推荐）');
INSERT INTO `store_goods_lower_limit` VALUES ('bdb12cf42d71a2e3', '1', '11', '200', '2019-05-15 11:45:41', 'super', '番茄（推荐）');
INSERT INTO `store_goods_lower_limit` VALUES ('bfd3e7bbe7f6e2e7', '1', '11', '10', '2019-05-15 11:42:19', 'super', '番茄（推荐）');

-- ----------------------------
-- Table structure for store_image
-- ----------------------------
DROP TABLE IF EXISTS `store_image`;
CREATE TABLE `store_image` (
  `image_id` varchar(32) NOT NULL COMMENT '主键',
  `image_link` varchar(256) NOT NULL COMMENT '图片链接',
  PRIMARY KEY (`image_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='轮播图表';

-- ----------------------------
-- Records of store_image
-- ----------------------------
INSERT INTO `store_image` VALUES ('1', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1556109752687&di=265917165fa8120a41427443aafcc2ae&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0134975a9617a5a80121923188341a.jpg%401280w_1l_2o_100sh.jpg');
INSERT INTO `store_image` VALUES ('2', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1556109836023&di=f218b6b2e8797f282686cfb1da6c1b0b&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F018d585ac9a88da801212573ec31fc.jpg%401280w_1l_2o_100sh.jpg');
INSERT INTO `store_image` VALUES ('3', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1556109938182&di=78161ca63b8f5025540c35e1ed1798e9&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01792a5723055132f875a399c417fb.jpg');

-- ----------------------------
-- Table structure for store_info
-- ----------------------------
DROP TABLE IF EXISTS `store_info`;
CREATE TABLE `store_info` (
  `info_id` varchar(32) NOT NULL COMMENT '主键',
  `info_city` varchar(32) NOT NULL COMMENT '始发城市',
  `info_name` varchar(32) NOT NULL COMMENT '商户名称',
  `info_address` varchar(128) NOT NULL COMMENT '商户地址',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户信息表';

-- ----------------------------
-- Records of store_info
-- ----------------------------
INSERT INTO `store_info` VALUES ('1', '长治市', '东山国际', '山西省长治市', '2019-03-04 14:57:45');

-- ----------------------------
-- Table structure for store_notice
-- ----------------------------
DROP TABLE IF EXISTS `store_notice`;
CREATE TABLE `store_notice` (
  `notice_id` varchar(32) NOT NULL COMMENT '主键',
  `notice_title` varchar(32) NOT NULL COMMENT '公告标题',
  `notice_content` text NOT NULL COMMENT '公告内容',
  `notice_ending` varchar(32) NOT NULL COMMENT '公告结尾',
  `notice_image` varchar(128) DEFAULT NULL COMMENT '公告图片',
  `notice_status` varchar(1) NOT NULL COMMENT '0：关，1：开',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公告表';

-- ----------------------------
-- Records of store_notice
-- ----------------------------
INSERT INTO `store_notice` VALUES ('1', '欢迎来到菜多多商城', '重大优惠等着您', '祝您购物愉快', 'http://bmob-cdn-24994.b0.upaiyun.com/2019/04/17/e956939b4087b8858062eb341067e655.jpg', '1', '2019-04-17 09:59:59');

-- ----------------------------
-- Table structure for store_ogd
-- ----------------------------
DROP TABLE IF EXISTS `store_ogd`;
CREATE TABLE `store_ogd` (
  `ogd_id` varchar(32) NOT NULL COMMENT '主键',
  `ogd_gid` varchar(32) NOT NULL COMMENT '订单商品ID',
  `ogd_gnum` int(10) NOT NULL COMMENT '订单商品数量',
  `ogd_no` varchar(32) NOT NULL COMMENT '订单编号',
  PRIMARY KEY (`ogd_id`),
  KEY `订单编号` (`ogd_no`) USING BTREE,
  CONSTRAINT `store_ogd_ibfk_1` FOREIGN KEY (`ogd_no`) REFERENCES `store_order` (`order_no`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单商品表';

-- ----------------------------
-- Records of store_ogd
-- ----------------------------
INSERT INTO `store_ogd` VALUES ('818889ae50dd81e4', '12', '1', 'ON201905151513471');
INSERT INTO `store_ogd` VALUES ('859ecfcd8981c8c1', '12', '1', 'ON201905151513207');
INSERT INTO `store_ogd` VALUES ('87f357a0eed4da5a', '12', '1', 'ON201905151416191');
INSERT INTO `store_ogd` VALUES ('89aa9062212a3d0c', '12', '1', 'ON201905151422169');
INSERT INTO `store_ogd` VALUES ('8a969d5c0fde6c2f', '12', '1', 'ON201905151510511');
INSERT INTO `store_ogd` VALUES ('8ae3f57ee62828ec', '12', '1', 'ON201905151423337');
INSERT INTO `store_ogd` VALUES ('8bc295b8d0aeba46', '12', '1', 'ON201905151450372');
INSERT INTO `store_ogd` VALUES ('8ebb2863eab20cbd', '12', '1', 'ON201905151417501');
INSERT INTO `store_ogd` VALUES ('8f1d0a2331054b23', '1', '6', 'ON201904301739391');
INSERT INTO `store_ogd` VALUES ('90251492ed496dbc', '12', '1', 'ON201905151426452');
INSERT INTO `store_ogd` VALUES ('90fb5b87574614f2', '2', '6', 'ON201904301742475');
INSERT INTO `store_ogd` VALUES ('912df619d9381bcb', '12', '1', 'ON201905151451401');
INSERT INTO `store_ogd` VALUES ('91b0d151cf180513', '1', '8', 'ON201904301732421');
INSERT INTO `store_ogd` VALUES ('930a02edfd30ad73', '3', '4', 'ON201905061636351');
INSERT INTO `store_ogd` VALUES ('94408aefd9d41109', '12', '1', 'ON201905151425331');
INSERT INTO `store_ogd` VALUES ('94d325b5c34eb146', '12', '1', 'ON201905151514241');
INSERT INTO `store_ogd` VALUES ('9538bae2e7179381', '2', '12', 'ON201904301731014');
INSERT INTO `store_ogd` VALUES ('96c24b9fe0a5760f', '2', '1', 'ON201905081517031');
INSERT INTO `store_ogd` VALUES ('97b7adbbffc9ac55', '12', '1', 'ON201905151457077');
INSERT INTO `store_ogd` VALUES ('97b7df51cf8b07f6', '1', '2', 'ON201905061634453');
INSERT INTO `store_ogd` VALUES ('980d97e993499d75', '1', '5', 'ON201904301736031');
INSERT INTO `store_ogd` VALUES ('982ed67979a60140', '1', '3', 'ON201905061635254');
INSERT INTO `store_ogd` VALUES ('99d36e1cc9240b5c', '2', '1', 'ON201905081544458');
INSERT INTO `store_ogd` VALUES ('99f8f5eee6a8b75b', '12', '1', 'ON201905151415582');
INSERT INTO `store_ogd` VALUES ('9b72ed696ce2b2df', '2', '1', 'ON201904301503081');
INSERT INTO `store_ogd` VALUES ('9caf466376ce07a3', '12', '1', 'ON201905151501351');
INSERT INTO `store_ogd` VALUES ('9cd64458ab1d1f91', '1', '2', 'ON201904301014172');
INSERT INTO `store_ogd` VALUES ('9d4500de1f6daaa4', '12', '1', 'ON201905151424071');
INSERT INTO `store_ogd` VALUES ('9db1800dd5bda36a', '1', '1', 'ON201904301023566');
INSERT INTO `store_ogd` VALUES ('a00354495e997c09', '1', '1', 'ON201904291616579');
INSERT INTO `store_ogd` VALUES ('a22d58300bc9937a', '2', '4', 'ON201905091703559');
INSERT INTO `store_ogd` VALUES ('a25b76491a68d908', '1', '1', 'ON201904301111072');
INSERT INTO `store_ogd` VALUES ('a31c5f8b09ebf0dc', '2', '6', 'ON201904301720071');
INSERT INTO `store_ogd` VALUES ('a3a58c92efbec4b7', '1', '2', 'ON201904301001101');
INSERT INTO `store_ogd` VALUES ('a4e07a776a2e4688', '2', '3', 'ON201904291618118');
INSERT INTO `store_ogd` VALUES ('a5a44f6a1aa81d7c', '12', '1', 'ON201905151449385');
INSERT INTO `store_ogd` VALUES ('a67ba72fe3aff821', '1', '4', 'ON201904291701512');
INSERT INTO `store_ogd` VALUES ('a8c143d6e32766ee', '2', '1', 'ON201905061633428');
INSERT INTO `store_ogd` VALUES ('a90845ca65e21557', '3', '2', 'ON201905061648021');
INSERT INTO `store_ogd` VALUES ('a9d7bb25ecb8fd84', '1', '2', 'ON201904301551317');
INSERT INTO `store_ogd` VALUES ('adbb41ec1f11be9e', '3', '3', 'ON201905061729267');
INSERT INTO `store_ogd` VALUES ('ae36bde151810b97', '1', '1', 'ON201904301505191');
INSERT INTO `store_ogd` VALUES ('aec2cc91c3368807', '1', '6', 'ON201904301703571');
INSERT INTO `store_ogd` VALUES ('af99f1ea1585b0c9', '1', '10', 'ON201904301729151');
INSERT INTO `store_ogd` VALUES ('b95fcfab688554c2', '1', '1', 'ON201904291656041');
INSERT INTO `store_ogd` VALUES ('b9f8a6e30731cbdc', '2', '1', 'ON201905061626042');
INSERT INTO `store_ogd` VALUES ('bb05f13b5403c5a3', '12', '1', 'ON201905151512031');
INSERT INTO `store_ogd` VALUES ('bb2c75a83c9852b4', '1', '2', 'ON201904291618118');
INSERT INTO `store_ogd` VALUES ('bc34c82a9a6399c9', '1', '8', 'ON201904301647441');
INSERT INTO `store_ogd` VALUES ('bc9a71238a254eff', '12', '1', 'ON201905151417291');
INSERT INTO `store_ogd` VALUES ('be573a0172cd778c', '3', '1', 'ON201904291618118');
INSERT INTO `store_ogd` VALUES ('bfafeff9ea143db1', '2', '11', 'ON201904301709486');

-- ----------------------------
-- Table structure for store_operation_record
-- ----------------------------
DROP TABLE IF EXISTS `store_operation_record`;
CREATE TABLE `store_operation_record` (
  `operation_record_id` varchar(32) NOT NULL COMMENT '主键',
  `operation_record_aid` varchar(32) NOT NULL COMMENT '管理员主键',
  `operation_record_gid` varchar(32) NOT NULL COMMENT '商品主键',
  `operation_record_type` varchar(1) NOT NULL COMMENT '1：出库，2：入库',
  `operation_record_goodsTitle` varchar(32) NOT NULL COMMENT '商品名称',
  `operation_record_goodsLabel` varchar(32) NOT NULL COMMENT '商品标签',
  `operation_record_goodsType` varchar(32) NOT NULL COMMENT '商品类型',
  `operation_record_goodsNum` int(10) NOT NULL COMMENT '操作商品数量',
  `operation_record_name` varchar(32) NOT NULL COMMENT '操作人姓名',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`operation_record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of store_operation_record
-- ----------------------------
INSERT INTO `store_operation_record` VALUES ('8cbe383007dfe6f5', '1', 'a24ebc9a43d1f383', '2', 'www', '拼团商品', '水果', '1', 'super', '2019-05-16 15:16:32');
INSERT INTO `store_operation_record` VALUES ('936da4b64e2291a1', '1', 'a24ebc9a43d1f383', '2', 'www', '拼团商品', '水果', '2', 'super', '2019-05-16 16:15:52');
INSERT INTO `store_operation_record` VALUES ('955da4d9e0f4ffa6', '1', '09', '1', '西瓜（秒杀）', '秒杀商品', '瓜类', '1', 'super', '2019-05-16 16:34:25');

-- ----------------------------
-- Table structure for store_order
-- ----------------------------
DROP TABLE IF EXISTS `store_order`;
CREATE TABLE `store_order` (
  `order_no` varchar(32) NOT NULL COMMENT '主键',
  `order_price` varchar(10) NOT NULL COMMENT '订单金额',
  `order_status` varchar(1) NOT NULL COMMENT '0：已退款，1：未支付，2：已支付，3：已发货，4：已完成',
  `order_uid` varchar(32) NOT NULL COMMENT '订单用户ID',
  `order_name` varchar(32) NOT NULL COMMENT '收货人姓名',
  `order_phone` varchar(11) NOT NULL COMMENT '收货人电话',
  `order_address` varchar(128) NOT NULL COMMENT '收货地址',
  `order_fare` varchar(32) NOT NULL COMMENT '运费',
  `order_group` varchar(1) NOT NULL DEFAULT '0' COMMENT '0：非团购，1：团购',
  `group_status` varchar(1) NOT NULL DEFAULT '' COMMENT '0：拼团进行中，1：拼团成功',
  `order_delivery` varchar(32) NOT NULL DEFAULT '自己配送' COMMENT '配送方式',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
-- Records of store_order
-- ----------------------------
INSERT INTO `store_order` VALUES ('ON201904291616579', '40', '0', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', '1', '自己配送', '2019-04-29 16:16:58', '2019-04-29 16:16:58');
INSERT INTO `store_order` VALUES ('ON201904291618118', '245', '0', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-04-29 16:18:12', '2019-04-29 16:18:12');
INSERT INTO `store_order` VALUES ('ON201904291656041', '40', '0', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-04-29 16:56:04', '2019-04-29 16:56:04');
INSERT INTO `store_order` VALUES ('ON201904291701512', '160', '0', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-04-29 17:01:51', '2019-04-29 17:01:51');
INSERT INTO `store_order` VALUES ('ON201904301001101', '80', '0', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-04-30 10:01:10', '2019-04-30 10:01:10');
INSERT INTO `store_order` VALUES ('ON201904301014172', '80', '0', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-04-30 10:14:17', '2019-04-30 10:14:17');
INSERT INTO `store_order` VALUES ('ON201904301014452', '40', '0', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-04-30 10:14:45', '2019-04-30 10:14:45');
INSERT INTO `store_order` VALUES ('ON201904301023566', '40', '0', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-04-30 10:23:57', '2019-04-30 10:23:57');
INSERT INTO `store_order` VALUES ('ON201904301111072', '40', '0', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-04-30 11:11:08', '2019-04-30 11:11:08');
INSERT INTO `store_order` VALUES ('ON201904301503081', '41', '0', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-04-30 15:03:09', '2019-04-30 15:03:09');
INSERT INTO `store_order` VALUES ('ON201904301505191', '40', '2', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-04-30 15:05:20', '2019-04-30 15:05:20');
INSERT INTO `store_order` VALUES ('ON201904301551317', '80', '2', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-04-30 15:51:32', '2019-04-30 15:51:32');
INSERT INTO `store_order` VALUES ('ON201904301647441', '320', '1', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-04-30 16:47:44', '2019-04-30 16:47:44');
INSERT INTO `store_order` VALUES ('ON201904301703571', '240', '0', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-04-30 17:03:58', '2019-04-30 17:03:58');
INSERT INTO `store_order` VALUES ('ON201904301709486', '451', '2', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-04-30 17:09:49', '2019-04-30 17:09:49');
INSERT INTO `store_order` VALUES ('ON201904301720071', '246', '0', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-04-30 17:20:07', '2019-04-30 17:20:07');
INSERT INTO `store_order` VALUES ('ON201904301729151', '400', '0', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-04-30 17:29:16', '2019-04-30 17:29:16');
INSERT INTO `store_order` VALUES ('ON201904301731014', '492', '0', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-04-30 17:31:02', '2019-04-30 17:31:02');
INSERT INTO `store_order` VALUES ('ON201904301732421', '320', '0', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-04-30 17:32:43', '2019-04-30 17:32:43');
INSERT INTO `store_order` VALUES ('ON201904301736031', '200', '0', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-04-30 17:36:04', '2019-04-30 17:36:04');
INSERT INTO `store_order` VALUES ('ON201904301739391', '240', '0', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-04-30 17:39:39', '2019-04-30 17:39:39');
INSERT INTO `store_order` VALUES ('ON201904301742475', '244.0', '0', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-04-30 17:42:48', '2019-04-30 17:42:48');
INSERT INTO `store_order` VALUES ('ON201905061626042', '41.0', '0', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-06 16:26:04', '2019-05-06 16:26:04');
INSERT INTO `store_order` VALUES ('ON201905061633428', '41.0', '2', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-06 16:33:42', '2019-05-06 16:33:42');
INSERT INTO `store_order` VALUES ('ON201905061634453', '80.0', '2', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-06 16:34:45', '2019-05-06 16:34:45');
INSERT INTO `store_order` VALUES ('ON201905061635254', '120.0', '2', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-06 16:35:26', '2019-05-06 16:35:26');
INSERT INTO `store_order` VALUES ('ON201905061636351', '168.0', '2', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-06 16:36:35', '2019-05-06 16:36:35');
INSERT INTO `store_order` VALUES ('ON201905061648021', '84.0', '2', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-06 16:48:02', '2019-05-06 16:48:02');
INSERT INTO `store_order` VALUES ('ON201905061729267', '126.0', '0', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-06 17:29:26', '2019-05-06 17:29:26');
INSERT INTO `store_order` VALUES ('ON201905081516231', '50', '1', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-08 15:16:23', '2019-05-08 15:16:23');
INSERT INTO `store_order` VALUES ('ON201905081517031', '205.0', '2', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-08 15:17:03', '2019-05-08 15:17:03');
INSERT INTO `store_order` VALUES ('ON201905081544458', '205.0', '2', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-08 15:44:46', '2019-05-08 15:44:46');
INSERT INTO `store_order` VALUES ('ON201905091703559', '820.0', '2', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-09 17:03:56', '2019-05-09 17:03:56');
INSERT INTO `store_order` VALUES ('ON201905101526481', '50', '1', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-10 15:26:49', '2019-05-10 15:26:49');
INSERT INTO `store_order` VALUES ('ON201905101725061', '50', '1', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-10 17:25:07', '2019-05-10 17:25:07');
INSERT INTO `store_order` VALUES ('ON201905101725281', '50', '1', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-10 17:25:28', '2019-05-10 17:25:28');
INSERT INTO `store_order` VALUES ('ON201905101726009', '205', '1', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-10 17:26:00', '2019-05-10 17:26:00');
INSERT INTO `store_order` VALUES ('ON201905151415582', '200', '1', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-15 14:15:59', '2019-05-15 14:15:59');
INSERT INTO `store_order` VALUES ('ON201905151416191', '200', '1', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-15 14:16:19', '2019-05-15 14:16:19');
INSERT INTO `store_order` VALUES ('ON201905151417291', '200', '1', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-15 14:17:30', '2019-05-15 14:17:30');
INSERT INTO `store_order` VALUES ('ON201905151417501', '200', '1', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-15 14:17:51', '2019-05-15 14:17:51');
INSERT INTO `store_order` VALUES ('ON201905151422169', '200', '1', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-15 14:22:17', '2019-05-15 14:22:17');
INSERT INTO `store_order` VALUES ('ON201905151423337', '200', '1', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-15 14:23:34', '2019-05-15 14:23:34');
INSERT INTO `store_order` VALUES ('ON201905151424071', '200', '1', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-15 14:24:07', '2019-05-15 14:24:07');
INSERT INTO `store_order` VALUES ('ON201905151425331', '200', '1', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-15 14:25:33', '2019-05-15 14:25:33');
INSERT INTO `store_order` VALUES ('ON201905151426452', '200', '1', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-15 14:26:46', '2019-05-15 14:26:46');
INSERT INTO `store_order` VALUES ('ON201905151449385', '200', '1', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-15 14:49:39', '2019-05-15 14:49:39');
INSERT INTO `store_order` VALUES ('ON201905151450372', '200', '1', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-15 14:50:38', '2019-05-15 14:50:38');
INSERT INTO `store_order` VALUES ('ON201905151451401', '200', '1', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-15 14:51:41', '2019-05-15 14:51:41');
INSERT INTO `store_order` VALUES ('ON201905151457077', '200', '1', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-15 14:57:07', '2019-05-15 14:57:07');
INSERT INTO `store_order` VALUES ('ON201905151501351', '200', '1', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-15 15:01:35', '2019-05-15 15:01:35');
INSERT INTO `store_order` VALUES ('ON201905151510511', '200', '1', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-15 15:10:52', '2019-05-15 15:10:52');
INSERT INTO `store_order` VALUES ('ON201905151512031', '200', '1', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-15 15:12:03', '2019-05-15 15:12:03');
INSERT INTO `store_order` VALUES ('ON201905151513207', '200', '1', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-15 15:13:21', '2019-05-15 15:13:21');
INSERT INTO `store_order` VALUES ('ON201905151513471', '200', '1', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-15 15:13:48', '2019-05-15 15:13:48');
INSERT INTO `store_order` VALUES ('ON201905151514241', '200', '1', '9311e0fcffe1b4ff', '李康', '15503553372', '山西省长治市上党区新市西街20号正东方向99米', '0', '0', ' ', '自己配送', '2019-05-15 15:14:24', '2019-05-15 15:14:24');

-- ----------------------------
-- Table structure for store_recharge
-- ----------------------------
DROP TABLE IF EXISTS `store_recharge`;
CREATE TABLE `store_recharge` (
  `recharge_id` varchar(32) NOT NULL COMMENT '主键',
  `recharge_coin` varchar(32) NOT NULL COMMENT '充值金额',
  `recharge_type` varchar(1) NOT NULL COMMENT '0：微信',
  `recharge_uid` varchar(32) NOT NULL COMMENT '用户主键',
  `recharge_status` varchar(1) NOT NULL COMMENT '0：未支付，1：已支付',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`recharge_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='充值记录表';

-- ----------------------------
-- Records of store_recharge
-- ----------------------------
INSERT INTO `store_recharge` VALUES ('0f9f3527c9a5402faa07aa58ad895cb1', '100', '0', '1', '1', '2019-04-19 09:31:58');
INSERT INTO `store_recharge` VALUES ('1', '100', '1', '1', '1', '2019-03-14 17:31:07');
INSERT INTO `store_recharge` VALUES ('2', '200', '1', '1', '1', '2019-03-14 17:31:22');
INSERT INTO `store_recharge` VALUES ('233d49e0d59445f4942d43ff7cca7d8d', '100', '0', '1', '1', '2019-04-15 15:38:03');
INSERT INTO `store_recharge` VALUES ('36685764a0bf41e6bf435d974d866aa8', '200', '0', 'b576038eea65479888fcb1afe58b43d6', '1', '2019-04-19 09:32:10');
INSERT INTO `store_recharge` VALUES ('5f9b2a484d7b4658a7387d989ff301e9', '-100', '0', '', '1', '2019-04-11 17:34:49');
INSERT INTO `store_recharge` VALUES ('612df401e2a64bdc987e5ea764ec96aa', '300', '0', '1', '1', '2019-04-11 18:09:38');
INSERT INTO `store_recharge` VALUES ('6984db4a07174fffbdacc14dfe703c83', '-100', '0', '1', '1', '2019-04-11 17:34:11');
INSERT INTO `store_recharge` VALUES ('74bc0e8633dc433283e934f261a7db34', '300.96', '0', '1', '1', '2019-04-11 17:32:29');
INSERT INTO `store_recharge` VALUES ('80922b011e6c0d45', '5000', '0', '9311e0fcffe1b4ff', '1', '2019-05-17 14:54:34');
INSERT INTO `store_recharge` VALUES ('8a4b517d7f102c35', '10', '0', '9311e0fcffe1b4ff', '1', '2019-05-17 09:30:19');
INSERT INTO `store_recharge` VALUES ('8b4835db56512714', '500', '0', '9311e0fcffe1b4ff', '1', '2019-05-07 14:13:31');
INSERT INTO `store_recharge` VALUES ('90077d35c583d149', '1', '0', '9311e0fcffe1b4ff', '1', '2019-05-18 10:05:44');
INSERT INTO `store_recharge` VALUES ('928b8c87844ce6f4', '1000', '0', '9311e0fcffe1b4ff', '1', '2019-05-17 14:54:26');
INSERT INTO `store_recharge` VALUES ('adaed3b5e0aa0598', '10', '0', '9311e0fcffe1b4ff', '1', '2019-05-17 09:31:13');
INSERT INTO `store_recharge` VALUES ('b0177174a72993d0', '20000', '0', '9311e0fcffe1b4ff', '1', '2019-05-17 14:54:56');
INSERT INTO `store_recharge` VALUES ('b07b56230ac74f00968e1dd90f29625e', '100', '0', 'f997bd08db7e491d91382ffaebbc81e7', '1', '2019-04-15 16:59:37');
INSERT INTO `store_recharge` VALUES ('b42d05f289c71153', '10', '0', '9311e0fcffe1b4ff', '1', '2019-05-17 09:31:21');
INSERT INTO `store_recharge` VALUES ('b434a5914c8d4829bd4b96cfc275db1b', '100', '0', '1', '1', '2019-04-16 18:08:46');
INSERT INTO `store_recharge` VALUES ('b474eed7497cefcf', '200', '0', '9311e0fcffe1b4ff', '1', '2019-05-10 10:17:42');
INSERT INTO `store_recharge` VALUES ('b4c83f33e36dab8c', '1000', '0', '9311e0fcffe1b4ff', '1', '2019-05-10 10:17:36');
INSERT INTO `store_recharge` VALUES ('ba75ca023b3fb961', '100', '0', '9311e0fcffe1b4ff', '1', '2019-05-10 10:17:46');
INSERT INTO `store_recharge` VALUES ('bfeb2c287c261393', '10', '0', '9311e0fcffe1b4ff', '1', '2019-05-17 11:11:41');
INSERT INTO `store_recharge` VALUES ('de188abbdc2e4f9d8f65af4b12b570f5', '200', '0', '', '1', '2019-04-11 17:32:49');
INSERT INTO `store_recharge` VALUES ('e8565569c545458c896583efe8ddc698', '300', '0', '1', '1', '2019-04-11 18:08:51');
INSERT INTO `store_recharge` VALUES ('e94837b9b3d24797b3d337262eea6d3f', '100', '0', '1', '1', '2019-04-11 14:26:35');
INSERT INTO `store_recharge` VALUES ('ec3b1532d97e4c458c78efb4cbface83', '1033', '0', '', '1', '2019-04-11 18:00:31');

-- ----------------------------
-- Table structure for store_refund
-- ----------------------------
DROP TABLE IF EXISTS `store_refund`;
CREATE TABLE `store_refund` (
  `refund_id` varchar(32) NOT NULL COMMENT '主键',
  `refund_coin` varchar(10) NOT NULL COMMENT '退款金额',
  `refund_uid` varchar(32) NOT NULL COMMENT '用户主键',
  `refund_type` varchar(1) NOT NULL COMMENT '0：微信，1：余额',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`refund_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='退款记录表';

-- ----------------------------
-- Records of store_refund
-- ----------------------------
INSERT INTO `store_refund` VALUES ('88b8d3c8c910c2c8', '40', '9311e0fcffe1b4ff', '1', '2019-04-30 11:46:19');
INSERT INTO `store_refund` VALUES ('8c2b95960ecf6119', '126.0', '9311e0fcffe1b4ff', '1', '2019-05-06 17:31:50');
INSERT INTO `store_refund` VALUES ('8c9509e097ed8fab', '40', '9311e0fcffe1b4ff', '1', '2019-04-30 18:00:50');
INSERT INTO `store_refund` VALUES ('8cbc6bcec95de5c0', '240', '9311e0fcffe1b4ff', '1', '2019-04-30 18:01:15');
INSERT INTO `store_refund` VALUES ('969e290d50d769a5', '400', '9311e0fcffe1b4ff', '1', '2019-04-30 18:00:46');
INSERT INTO `store_refund` VALUES ('99648af20a5f77f4', '40', '9311e0fcffe1b4ff', '1', '2019-04-30 11:45:49');
INSERT INTO `store_refund` VALUES ('9a83441cdd74b947', '40', '9311e0fcffe1b4ff', '1', '2019-04-30 11:31:36');
INSERT INTO `store_refund` VALUES ('a03bccb4435c2680', '41.0', '9311e0fcffe1b4ff', '1', '2019-05-06 16:27:26');
INSERT INTO `store_refund` VALUES ('a42af01a1a04e555', '41', '9311e0fcffe1b4ff', '1', '2019-04-30 18:01:00');
INSERT INTO `store_refund` VALUES ('a4cf0e3b1c330848', '200', '9311e0fcffe1b4ff', '1', '2019-04-30 18:00:43');
INSERT INTO `store_refund` VALUES ('a640a4c8c756346d', '80', '9311e0fcffe1b4ff', '1', '2019-04-30 11:44:58');
INSERT INTO `store_refund` VALUES ('a92762ad55c7fd15', '246', '9311e0fcffe1b4ff', '1', '2019-04-30 17:59:47');
INSERT INTO `store_refund` VALUES ('ab3f33dd922b0f4c', '40', '9311e0fcffe1b4ff', '1', '2019-04-30 18:00:57');
INSERT INTO `store_refund` VALUES ('ad830506b3efebdd', '320', '9311e0fcffe1b4ff', '1', '2019-04-30 18:00:40');
INSERT INTO `store_refund` VALUES ('aef0a8dd14c2459c', '492', '9311e0fcffe1b4ff', '1', '2019-04-30 17:59:51');
INSERT INTO `store_refund` VALUES ('b283033dbbab170b', '240', '9311e0fcffe1b4ff', '1', '2019-04-30 17:59:54');
INSERT INTO `store_refund` VALUES ('b429b2ee4c45e978', '160', '9311e0fcffe1b4ff', '1', '2019-04-30 15:09:13');
INSERT INTO `store_refund` VALUES ('b4e297406b58921f', '244.0', '9311e0fcffe1b4ff', '1', '2019-04-30 17:59:57');
INSERT INTO `store_refund` VALUES ('b51f4a06c1df8c4b', '245', '9311e0fcffe1b4ff', '1', '2019-04-30 11:35:28');
INSERT INTO `store_refund` VALUES ('b55e5de11e07bb54', '40', '9311e0fcffe1b4ff', '1', '2019-04-30 18:00:37');
INSERT INTO `store_refund` VALUES ('b60c2f5c009f624a', '80', '9311e0fcffe1b4ff', '1', '2019-04-30 18:00:53');

-- ----------------------------
-- Table structure for store_share
-- ----------------------------
DROP TABLE IF EXISTS `store_share`;
CREATE TABLE `store_share` (
  `share_id` varchar(32) NOT NULL COMMENT '主键',
  `share_coin` varchar(10) NOT NULL COMMENT '奖励金额',
  `share_status` varchar(1) NOT NULL DEFAULT '0' COMMENT '0：分享失败，1：分享成功',
  `share_type` varchar(1) NOT NULL COMMENT '分享类型',
  `share_xuid` varchar(32) DEFAULT NULL COMMENT '被分享者主键',
  `share_uid` varchar(32) NOT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`share_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分享记录表';

-- ----------------------------
-- Records of store_share
-- ----------------------------
INSERT INTO `store_share` VALUES ('8533f7385a23bb9a', '0.05', '1', '0', null, '9311e0fcffe1b4ff', '2019-05-17 10:14:04');
INSERT INTO `store_share` VALUES ('895763283711a0e6', '0.01', '1', '0', null, '9311e0fcffe1b4ff', '2019-05-17 10:13:52');
INSERT INTO `store_share` VALUES ('8c037c2b0b6bb637', '0.05', '1', '0', null, '9311e0fcffe1b4ff', '2019-05-17 10:13:57');
INSERT INTO `store_share` VALUES ('99d0643b1bc47b6b', '0.01', '1', '0', null, '9311e0fcffe1b4ff', '2019-05-17 10:14:08');
INSERT INTO `store_share` VALUES ('9d678fde64f22e30', '0.03', '1', '0', null, '9311e0fcffe1b4ff', '2019-05-17 10:13:33');
INSERT INTO `store_share` VALUES ('a6459c4a574d407d', '0.05', '1', '0', null, '9311e0fcffe1b4ff', '2019-05-17 10:13:29');
INSERT INTO `store_share` VALUES ('aaebde0d76ea11f3', '0.04', '1', '0', null, '9311e0fcffe1b4ff', '2019-05-17 10:14:12');
INSERT INTO `store_share` VALUES ('afa5b3d56dece3aa', '0.02', '1', '0', null, '9311e0fcffe1b4ff', '2019-05-17 10:13:25');
INSERT INTO `store_share` VALUES ('afd79d4deec218ec', '0.01', '1', '0', null, '9311e0fcffe1b4ff', '2019-04-30 11:34:22');
INSERT INTO `store_share` VALUES ('b0269f59348a6c15', '0.01', '1', '0', null, '9311e0fcffe1b4ff', '2019-05-17 10:13:49');
INSERT INTO `store_share` VALUES ('b11ba4f828aa3d5e', '0.05', '1', '0', null, '9311e0fcffe1b4ff', '2019-05-07 14:14:26');
INSERT INTO `store_share` VALUES ('b358b22419551d0f', '0.01', '1', '0', null, '9311e0fcffe1b4ff', '2019-05-17 10:14:00');
INSERT INTO `store_share` VALUES ('b38669cdf535a68f', '0.02', '1', '0', null, '9311e0fcffe1b4ff', '2019-05-17 10:14:15');

-- ----------------------------
-- Table structure for store_sort
-- ----------------------------
DROP TABLE IF EXISTS `store_sort`;
CREATE TABLE `store_sort` (
  `sort_id` varchar(32) NOT NULL COMMENT '主键',
  `sort_name` varchar(32) NOT NULL COMMENT '分类名称',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '上级ID',
  `grade` varchar(32) NOT NULL COMMENT '级别：1 大分类（商品标签）, 2 小分类',
  `number` int(12) DEFAULT NULL COMMENT '排序 ',
  PRIMARY KEY (`sort_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分类表';

-- ----------------------------
-- Records of store_sort
-- ----------------------------
INSERT INTO `store_sort` VALUES ('1', '下架商品', '0', '1', '1');
INSERT INTO `store_sort` VALUES ('2', '拼团商品', '0', '1', '2');
INSERT INTO `store_sort` VALUES ('3', '秒杀商品', '0', '1', '3');
INSERT INTO `store_sort` VALUES ('4', '推荐商品', '0', '1', '4');
INSERT INTO `store_sort` VALUES ('5', '下架商品', '1', '2', '4');
INSERT INTO `store_sort` VALUES ('6', '普通商品', '0', '1', '6');
INSERT INTO `store_sort` VALUES ('89e9464781f410', '蔬菜', '4', '2', '2');
INSERT INTO `store_sort` VALUES ('89e9464781f411', '瓜类', '4', '2', '3');
INSERT INTO `store_sort` VALUES ('89e9464781f4c0', '水果', '6', '2', '1');
INSERT INTO `store_sort` VALUES ('89e9464781f4c1', '蔬菜', '6', '2', '2');
INSERT INTO `store_sort` VALUES ('89e9464781f4c2', '瓜类', '6', '2', '3');
INSERT INTO `store_sort` VALUES ('89e9464781f4c3', '水果1', '2', '2', '1');
INSERT INTO `store_sort` VALUES ('89e9464781f4c4', '蔬菜', '2', '2', '2');
INSERT INTO `store_sort` VALUES ('89e9464781f4c5', '瓜类', '2', '2', '3');
INSERT INTO `store_sort` VALUES ('89e9464781f4c6', '水果', '3', '2', '1');
INSERT INTO `store_sort` VALUES ('89e9464781f4c7', '蔬菜', '3', '2', '2');
INSERT INTO `store_sort` VALUES ('89e9464781f4c8', '瓜类', '3', '2', '3');
INSERT INTO `store_sort` VALUES ('89e9464781f4c9', '水果', '4', '2', '1');

-- ----------------------------
-- Table structure for store_user
-- ----------------------------
DROP TABLE IF EXISTS `store_user`;
CREATE TABLE `store_user` (
  `user_id` varchar(32) NOT NULL COMMENT '主键',
  `user_openid` varchar(128) NOT NULL COMMENT 'open_id',
  `user_sessionkey` varchar(128) NOT NULL COMMENT 'session_key',
  `user_phone` varchar(11) NOT NULL COMMENT '用户电话',
  `user_wallet` varchar(10) NOT NULL DEFAULT '0' COMMENT '用户钱包',
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户姓名',
  `user_address` varchar(128) DEFAULT NULL COMMENT '收货地址',
  `user_password` varchar(128) DEFAULT '' COMMENT '支付密码',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of store_user
-- ----------------------------
INSERT INTO `store_user` VALUES ('9311e0fcffe1b4ff', 'o4JFK5E5IPkL4yxIypzBt8J4UoeY', 'QHy0D2MZoUVzDgko59enQQ==', '15503553372', '31107.36', '李康', '山西省长治市上党区新市西街20号正东方向99米', '123456', '2019-04-29 09:16:14');

-- ----------------------------
-- Table structure for store_vip
-- ----------------------------
DROP TABLE IF EXISTS `store_vip`;
CREATE TABLE `store_vip` (
  `vip_id` varchar(32) NOT NULL COMMENT '主键',
  `vip_uid` varchar(32) NOT NULL COMMENT '用户主键',
  `vip_price` varchar(10) NOT NULL COMMENT '会员价格',
  `vip_discount` double(10,2) NOT NULL COMMENT '会员折扣',
  `vip_type` varchar(1) NOT NULL COMMENT '0：月会员，1：季会员，2：年会员',
  `vip_status` varchar(1) NOT NULL DEFAULT '0' COMMENT '0：未支付，1：已支付',
  `vip_start` datetime NOT NULL COMMENT '起始时间',
  `vip_end` datetime NOT NULL COMMENT '结束时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`vip_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员表';

-- ----------------------------
-- Records of store_vip
-- ----------------------------
INSERT INTO `store_vip` VALUES ('1', '9311e0fcffe1b4ff', '100', '5.00', '2', '1', '2019-05-02 11:10:21', '2019-06-01 11:10:25', '2019-05-07 11:10:29');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '系统管理', '', 'sys', '0', 'fa fa-desktop', '6', null, null);
INSERT INTO `sys_menu` VALUES ('2', '1', '权限管理', '/back/sysMenu/list', 'back:sysMenu:list', '1', '', '3', null, null);
INSERT INTO `sys_menu` VALUES ('3', '2', '权限管理_删除_按钮', '/back/api/sysMenu/delete', 'back:api:sysMenu:delete', '2', null, null, null, null);
INSERT INTO `sys_menu` VALUES ('4', '2', '权限管理_新增_按钮', '/back/api/sysMenu/save', 'back:api:sysMenu:save', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('5', '2', '权限管理_添加_页面', '/back/sysMenu/add', 'back:sysMenu:add', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('6', '2', '权限管理_编辑_页面', '/back/sysMenu/edit', 'back:menu:edit', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('7', '2', '权限管理_修改_按钮', '/back/api/sysMenu/update', 'back:api:sysMenu:update', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('8', '1', '角色管理', '/back/sysRole/list', 'back:sysRole:list', '1', '', '2', null, null);
INSERT INTO `sys_menu` VALUES ('9', '8', '角色管理_添加_页面', '/back/sysRole/add', 'back:sysRole:add', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('10', '8', '角色管理_新增_按钮', 'back/api/sysRole/save', 'back:api:sysRole:save', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('11', '8', '角色管理_删除_按钮', '/back/api/sysRole/delete', 'back:api:sysRole:delete', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('12', '8', '角色管理_编辑_页面', '/back/sysRole/edit', 'back:sysRole:edit', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('13', '8', '角色管理_修改_按钮', '/back/api/sysRole/update', 'back:api:sysRole:update', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('14', '1', '系统用户管理', '/back/admin/list', 'back:admin:list', '1', '', '1', null, null);
INSERT INTO `sys_menu` VALUES ('15', '14', '系统用户管理_添加_页面', '/back/admin/add', 'back:admin:add', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('16', '14', '系统用户管理_新增_按钮', '/back/api/admin/save', 'back:api:admin:save', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('17', '14', '系统用户管理_编辑_页面', '/back/admin/edit', 'back:admin:edit', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('18', '14', '系统用户管理_修改_按钮', '/back/api/admin/update', 'back:api:admin:update', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('19', '14', '系统用户管理_删除_按钮', '/back/api/admin/delete', 'back:api:admin:delete', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('21', '14', '系统用户管理_密码编辑_页面', '/back/admin/resetPwd', 'back:admin:resetPwd', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('22', '14', '系统用户管理_密码修改_按钮', '/back/api/admin/resetPwd', 'back:api:admin:resetPwd', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('23', '0', '商品管理', '', 'good', '0', 'fa fa-shopping-cart', '2', null, null);
INSERT INTO `sys_menu` VALUES ('24', '23', '推荐商品管理', '/back/recommend/list', 'back:recommend:list', '1', '', '3', null, null);
INSERT INTO `sys_menu` VALUES ('27', '24', '推荐商品管理_删除_按钮', '/back/api/recommend/delete', 'back:api:recommend:delete', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('28', '24', '推荐商品管理_批量删除_按钮', '/back/api/recommend/batchRemove', 'back:api:recommend:batchRemove', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('29', '24', '推荐商品管理_编辑_页面', '/back/recommend/edit', 'back:recommend:edit', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('30', '24', '推荐商品管理_修改_按钮', '/back/api/recommend/update', 'back:api:recommend:update ', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('31', '23', '热销商品管理', '/back/sellWell/list', 'back:sellWell:list', '1', '', '4', null, null);
INSERT INTO `sys_menu` VALUES ('32', '31', '热销商品管理_编辑_页面', '/back/sellWell/edit', 'back:sellWell:edit', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('33', '31', '热销商品管理_修改_按钮', '/back/api/sellWell/update', 'back:api:sellWell:update', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('34', '23', '拼团商品管理', '/back/assemble/list', 'back:assemble:list', '1', '', '5', null, null);
INSERT INTO `sys_menu` VALUES ('35', '34', '拼团商品管理_删除_按钮', '/back/api/assemble/delete', 'back:api:assemble:delete', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('36', '34', '拼团商品管理_编辑_页面', '/back/assemble/edit', 'back:assemble:edit', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('37', '34', '拼团商品管理_修改_按钮', '/back/api/assemble/update', 'back:api:assemble:update', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('38', '34', '拼团商品管理_批量删除_按钮', '/back/api/assemble/batchRemove', 'back:api:assemble:batchRemove', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('39', '0', '用户管理', '', 'WeChat', '0', 'fa fa-user', '1', null, null);
INSERT INTO `sys_menu` VALUES ('40', '39', '用户管理', '/back/user/list', 'back:user:list', '1', '', '1', null, null);
INSERT INTO `sys_menu` VALUES ('41', '40', '用户管理_删除_按钮', '/back/api/user/delete', 'back:api:user:delete', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('42', '40', '用户管理_批量删除_按钮', '/back/api/user/batchRemove', 'back:api:user:batchRemove', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('45', '39', 'vip管理', '/back/vip/list', 'back:vip:list', '1', '', '2', null, null);
INSERT INTO `sys_menu` VALUES ('55', '39', '账户管理', '/back/account/list', 'back:account:list', '1', '', '3', null, null);
INSERT INTO `sys_menu` VALUES ('56', '116', '充值管理', '/back/recharge/list', 'back:recharge:list', '1', '', '4', null, null);
INSERT INTO `sys_menu` VALUES ('57', '55', '账户管理_操作_充值', '/back/account/toRecharge', 'back:account:toRecharge', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('66', '23', '商品列表', '/back/goodsManagement/list', 'back:goodsManagement:list', '1', '', '1', null, null);
INSERT INTO `sys_menu` VALUES ('67', '66', '商品列表_新增_页面', '/back/goodsManagement/add', 'back:goodsManagement:add', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('68', '66', '商品列表_编辑_页面', '/back/goodsManagement/edit', 'back:goodsManagement:edit', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('69', '66', '商品列表_商品入库_按钮', '/back/api/goodsManagement/save', 'back:api:goodsManagement:save', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('70', '66', '商品列表_编辑_按钮', '/back/goods/goodsManagement/update', 'back:api:goodsManagement:update', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('71', '66', '商品列表_删除_按钮', '/back/goods/goodsManagement/delete', 'back:api:goodsManagement:delete', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('72', '0', '公告管理', '', 'notice', '0', 'fa fa-volume-up', '4', null, null);
INSERT INTO `sys_menu` VALUES ('73', '72', '公告列表', '/back/notice/list', 'back:notice:list', '1', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('74', '73', '公告管理_添加_页面', '/back/notice/add', 'back:notice:add', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('75', '73', '公告管理_添加_按钮', '/back/api/notice/add', 'back:api:notice:add', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('76', '73', '公告管理_编辑_页面', '/back/notice/edit', 'back:notice:edit', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('77', '73', '公告管理_编辑_按钮', '/back/api/notice/edit', 'back:api:notice:edit', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('78', '73', '公告管理_删除_按钮', '/back/api/notice/del', 'back:api:notice:del', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('79', '23', '轮播图管理', '/back/image/list', 'back:image:list', '1', '', '8', null, null);
INSERT INTO `sys_menu` VALUES ('80', '66', '商品列表_批量删除_按钮', '/back/goods/goodsManagement/batchRemove', 'back:api:goodsMangement:batchRemove', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('81', '116', '消费管理', '/back/consume/list', 'back:consume:list', '1', '', '3', null, null);
INSERT INTO `sys_menu` VALUES ('82', '39', '分享管理', '/back/share/list', 'back:share:list', '1', '', '4', null, null);
INSERT INTO `sys_menu` VALUES ('87', '23', '商品分类管理', '/back/sort/list', 'back:sort:list', '1', '', '2', null, null);
INSERT INTO `sys_menu` VALUES ('88', '87', '商品分类管理_添加_页面', '/back/sort/add', 'back:sort:add', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('89', '87', '商品分类管理_新增_按钮', '/back/api/sort/save', 'back:api:sort:save', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('90', '87', '商品分类管理_编辑_页面', 'back:sort:edit', 'back:sort:edit', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('91', '87', '商品分类管理_修改_按钮', '/back/api/sort/update', 'back:api:sort:update', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('92', '87', '商品分类管理_删除_按钮', '/back/api/sort/delete', 'back:api:sort:delete', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('93', '39', '优惠券管理', '/back/discountManage/list', 'back:discountManage:list', '1', '', '5', null, null);
INSERT INTO `sys_menu` VALUES ('94', '23', '下架商品管理', '/back/undercarriage/list', 'back:undercarriage:list', '1', '', '7', null, null);
INSERT INTO `sys_menu` VALUES ('95', '94', '下架商品管理_删除_按钮', '/back/api/undercarriage/delete', 'back:api:undercarriage:delete', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('96', '94', '下架商品管理_批量删除_按钮', '/back/api/undercarriage/batchRemove', 'back:api:undercarriage:batchRemove', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('97', '94', '下架商品管理_修改_按钮', '/back/api/undercarriage/update', 'back:api:undercarriage:update', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('98', '94', '下架商品管理_编辑_页面', '/back/undercarriage/edit', 'back:undercarriage:edit', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('99', '23', '秒杀商品管理', '/back/spike/list', 'back:spike:list', '1', '', '6', null, null);
INSERT INTO `sys_menu` VALUES ('100', '116', '出入库记录管理', '/back/operationRecord/list', 'back:operationRecord:list', '1', '', '1', null, null);
INSERT INTO `sys_menu` VALUES ('101', '99', '秒杀商品管理_编辑_页面', '/back/spike/edit', 'back:spike:edit', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('102', '99', '秒杀商品管理_修改_按钮', '/back/api/spike/update', 'back:api:spike:update', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('103', '100', '记录列表_删除_按钮', '/back/api/operationRecord/delete', 'back:api:operationRecord:delete', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('104', '99', '秒杀商品管理_删除_按钮', '/back/api/spike/delete', 'back:api:spike:delete', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('105', '99', '秒杀商品管理_批量删除_按钮', '/back/api/spike/batchRemove', 'back:api:spike:batchRemove', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('106', '0', '订单管理', '', 'order', '0', 'fa fa-tags', '3', null, null);
INSERT INTO `sys_menu` VALUES ('107', '106', '订单列表', '/back/order/list', 'back:order:list', '1', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('108', '93', '优惠券_按钮_新增', 'back/discountManage/toAdd', 'back:discountManage:toAdd', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('109', '93', '优惠券_按钮_配置', '/back/discountManage/toConfig', 'back:discountManage:toConfig', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('110', '93', '优惠券-新增-保存', '/back/api/discountManage/save', 'back:api:discountManage:save', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('111', '66', '商品列表_出库_页面', '/back/goodsManagement/del', 'back:goodsManagement:del', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('112', '93', '优惠券-配置-保存', '/back/api/discountManage/couponConfigSave', 'back:api:discountManage:couponConfigSave', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('113', '93', '优惠券-按钮-修改', '/back/discountManage/toEdit', 'back:discountManage:toEdit', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('114', '93', '优惠券-修改-保存', '/back/api/discountManage/edit', 'back:api:discountManage:edit', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('115', '93', '优惠券-按钮-删除', '/back/api/discountManage/remove', 'back:api:discountManage:remove', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('116', '0', '操作记录', '', 'history', '0', 'fa fa-file-text-o', '5', null, null);
INSERT INTO `sys_menu` VALUES ('117', '116', '库存下限记录', '/back/operationRecord/lowerLimit', 'back:operationRecord:lowerLimit', '1', '', '2', null, null);
INSERT INTO `sys_menu` VALUES ('118', '66', '商品列表_商品出库_按钮', '/back/api/goodsManagement/del', 'back:api:goodsManagement:del', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('119', '66', '商品列表_库存下限配置_页面', '/back/goodsManagement/positioning', 'back:goodsManagement:positioning', '2', '', null, null, null);
INSERT INTO `sys_menu` VALUES ('120', '66', '商品列表_库存下限配置_按钮', '/back/api/lowerLimit/save', 'back:api:lowerLimit:save', '2', '', '10', null, null);
INSERT INTO `sys_menu` VALUES ('121', '116', '退款记录', '/back/refund/list', 'back:refund:list', '1', '', '5', null, null);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `role_sign` varchar(100) DEFAULT NULL COMMENT '角色标识',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `user_id_create` bigint(255) DEFAULT NULL COMMENT '创建用户id',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'super', null, '超级用户', null, null, null);
INSERT INTO `sys_role` VALUES ('14', 'test', null, '测试', null, null, null);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5717 DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('5447', '14', '3');
INSERT INTO `sys_role_menu` VALUES ('5448', '14', '5');
INSERT INTO `sys_role_menu` VALUES ('5449', '14', '6');
INSERT INTO `sys_role_menu` VALUES ('5450', '14', '2');
INSERT INTO `sys_role_menu` VALUES ('5451', '14', '9');
INSERT INTO `sys_role_menu` VALUES ('5452', '14', '11');
INSERT INTO `sys_role_menu` VALUES ('5453', '14', '12');
INSERT INTO `sys_role_menu` VALUES ('5454', '14', '8');
INSERT INTO `sys_role_menu` VALUES ('5455', '14', '15');
INSERT INTO `sys_role_menu` VALUES ('5456', '14', '17');
INSERT INTO `sys_role_menu` VALUES ('5457', '14', '19');
INSERT INTO `sys_role_menu` VALUES ('5458', '14', '21');
INSERT INTO `sys_role_menu` VALUES ('5459', '14', '14');
INSERT INTO `sys_role_menu` VALUES ('5460', '14', '1');
INSERT INTO `sys_role_menu` VALUES ('5461', '14', '27');
INSERT INTO `sys_role_menu` VALUES ('5462', '14', '28');
INSERT INTO `sys_role_menu` VALUES ('5463', '14', '29');
INSERT INTO `sys_role_menu` VALUES ('5464', '14', '24');
INSERT INTO `sys_role_menu` VALUES ('5465', '14', '32');
INSERT INTO `sys_role_menu` VALUES ('5466', '14', '31');
INSERT INTO `sys_role_menu` VALUES ('5467', '14', '35');
INSERT INTO `sys_role_menu` VALUES ('5468', '14', '36');
INSERT INTO `sys_role_menu` VALUES ('5469', '14', '38');
INSERT INTO `sys_role_menu` VALUES ('5470', '14', '34');
INSERT INTO `sys_role_menu` VALUES ('5471', '14', '67');
INSERT INTO `sys_role_menu` VALUES ('5472', '14', '68');
INSERT INTO `sys_role_menu` VALUES ('5473', '14', '69');
INSERT INTO `sys_role_menu` VALUES ('5474', '14', '70');
INSERT INTO `sys_role_menu` VALUES ('5475', '14', '71');
INSERT INTO `sys_role_menu` VALUES ('5476', '14', '80');
INSERT INTO `sys_role_menu` VALUES ('5477', '14', '111');
INSERT INTO `sys_role_menu` VALUES ('5478', '14', '118');
INSERT INTO `sys_role_menu` VALUES ('5479', '14', '119');
INSERT INTO `sys_role_menu` VALUES ('5480', '14', '120');
INSERT INTO `sys_role_menu` VALUES ('5481', '14', '66');
INSERT INTO `sys_role_menu` VALUES ('5482', '14', '79');
INSERT INTO `sys_role_menu` VALUES ('5483', '14', '88');
INSERT INTO `sys_role_menu` VALUES ('5484', '14', '90');
INSERT INTO `sys_role_menu` VALUES ('5485', '14', '92');
INSERT INTO `sys_role_menu` VALUES ('5486', '14', '87');
INSERT INTO `sys_role_menu` VALUES ('5487', '14', '95');
INSERT INTO `sys_role_menu` VALUES ('5488', '14', '96');
INSERT INTO `sys_role_menu` VALUES ('5489', '14', '98');
INSERT INTO `sys_role_menu` VALUES ('5490', '14', '94');
INSERT INTO `sys_role_menu` VALUES ('5491', '14', '101');
INSERT INTO `sys_role_menu` VALUES ('5492', '14', '105');
INSERT INTO `sys_role_menu` VALUES ('5493', '14', '99');
INSERT INTO `sys_role_menu` VALUES ('5494', '14', '23');
INSERT INTO `sys_role_menu` VALUES ('5495', '14', '41');
INSERT INTO `sys_role_menu` VALUES ('5496', '14', '42');
INSERT INTO `sys_role_menu` VALUES ('5497', '14', '40');
INSERT INTO `sys_role_menu` VALUES ('5498', '14', '45');
INSERT INTO `sys_role_menu` VALUES ('5499', '14', '57');
INSERT INTO `sys_role_menu` VALUES ('5500', '14', '55');
INSERT INTO `sys_role_menu` VALUES ('5501', '14', '82');
INSERT INTO `sys_role_menu` VALUES ('5502', '14', '108');
INSERT INTO `sys_role_menu` VALUES ('5503', '14', '109');
INSERT INTO `sys_role_menu` VALUES ('5504', '14', '110');
INSERT INTO `sys_role_menu` VALUES ('5505', '14', '112');
INSERT INTO `sys_role_menu` VALUES ('5506', '14', '113');
INSERT INTO `sys_role_menu` VALUES ('5507', '14', '114');
INSERT INTO `sys_role_menu` VALUES ('5508', '14', '115');
INSERT INTO `sys_role_menu` VALUES ('5509', '14', '93');
INSERT INTO `sys_role_menu` VALUES ('5510', '14', '39');
INSERT INTO `sys_role_menu` VALUES ('5511', '14', '74');
INSERT INTO `sys_role_menu` VALUES ('5512', '14', '75');
INSERT INTO `sys_role_menu` VALUES ('5513', '14', '76');
INSERT INTO `sys_role_menu` VALUES ('5514', '14', '77');
INSERT INTO `sys_role_menu` VALUES ('5515', '14', '78');
INSERT INTO `sys_role_menu` VALUES ('5516', '14', '73');
INSERT INTO `sys_role_menu` VALUES ('5517', '14', '72');
INSERT INTO `sys_role_menu` VALUES ('5518', '14', '107');
INSERT INTO `sys_role_menu` VALUES ('5519', '14', '106');
INSERT INTO `sys_role_menu` VALUES ('5520', '14', '56');
INSERT INTO `sys_role_menu` VALUES ('5521', '14', '81');
INSERT INTO `sys_role_menu` VALUES ('5522', '14', '103');
INSERT INTO `sys_role_menu` VALUES ('5523', '14', '100');
INSERT INTO `sys_role_menu` VALUES ('5524', '14', '117');
INSERT INTO `sys_role_menu` VALUES ('5525', '14', '116');
INSERT INTO `sys_role_menu` VALUES ('5526', '14', '104');
INSERT INTO `sys_role_menu` VALUES ('5622', '1', '3');
INSERT INTO `sys_role_menu` VALUES ('5623', '1', '4');
INSERT INTO `sys_role_menu` VALUES ('5624', '1', '5');
INSERT INTO `sys_role_menu` VALUES ('5625', '1', '6');
INSERT INTO `sys_role_menu` VALUES ('5626', '1', '7');
INSERT INTO `sys_role_menu` VALUES ('5627', '1', '2');
INSERT INTO `sys_role_menu` VALUES ('5628', '1', '9');
INSERT INTO `sys_role_menu` VALUES ('5629', '1', '10');
INSERT INTO `sys_role_menu` VALUES ('5630', '1', '11');
INSERT INTO `sys_role_menu` VALUES ('5631', '1', '12');
INSERT INTO `sys_role_menu` VALUES ('5632', '1', '13');
INSERT INTO `sys_role_menu` VALUES ('5633', '1', '8');
INSERT INTO `sys_role_menu` VALUES ('5634', '1', '15');
INSERT INTO `sys_role_menu` VALUES ('5635', '1', '16');
INSERT INTO `sys_role_menu` VALUES ('5636', '1', '17');
INSERT INTO `sys_role_menu` VALUES ('5637', '1', '18');
INSERT INTO `sys_role_menu` VALUES ('5638', '1', '19');
INSERT INTO `sys_role_menu` VALUES ('5639', '1', '21');
INSERT INTO `sys_role_menu` VALUES ('5640', '1', '22');
INSERT INTO `sys_role_menu` VALUES ('5641', '1', '14');
INSERT INTO `sys_role_menu` VALUES ('5642', '1', '1');
INSERT INTO `sys_role_menu` VALUES ('5643', '1', '27');
INSERT INTO `sys_role_menu` VALUES ('5644', '1', '28');
INSERT INTO `sys_role_menu` VALUES ('5645', '1', '29');
INSERT INTO `sys_role_menu` VALUES ('5646', '1', '30');
INSERT INTO `sys_role_menu` VALUES ('5647', '1', '24');
INSERT INTO `sys_role_menu` VALUES ('5648', '1', '32');
INSERT INTO `sys_role_menu` VALUES ('5649', '1', '33');
INSERT INTO `sys_role_menu` VALUES ('5650', '1', '31');
INSERT INTO `sys_role_menu` VALUES ('5651', '1', '35');
INSERT INTO `sys_role_menu` VALUES ('5652', '1', '36');
INSERT INTO `sys_role_menu` VALUES ('5653', '1', '37');
INSERT INTO `sys_role_menu` VALUES ('5654', '1', '38');
INSERT INTO `sys_role_menu` VALUES ('5655', '1', '34');
INSERT INTO `sys_role_menu` VALUES ('5656', '1', '67');
INSERT INTO `sys_role_menu` VALUES ('5657', '1', '68');
INSERT INTO `sys_role_menu` VALUES ('5658', '1', '69');
INSERT INTO `sys_role_menu` VALUES ('5659', '1', '70');
INSERT INTO `sys_role_menu` VALUES ('5660', '1', '71');
INSERT INTO `sys_role_menu` VALUES ('5661', '1', '80');
INSERT INTO `sys_role_menu` VALUES ('5662', '1', '111');
INSERT INTO `sys_role_menu` VALUES ('5663', '1', '118');
INSERT INTO `sys_role_menu` VALUES ('5664', '1', '119');
INSERT INTO `sys_role_menu` VALUES ('5665', '1', '120');
INSERT INTO `sys_role_menu` VALUES ('5666', '1', '66');
INSERT INTO `sys_role_menu` VALUES ('5667', '1', '79');
INSERT INTO `sys_role_menu` VALUES ('5668', '1', '88');
INSERT INTO `sys_role_menu` VALUES ('5669', '1', '89');
INSERT INTO `sys_role_menu` VALUES ('5670', '1', '90');
INSERT INTO `sys_role_menu` VALUES ('5671', '1', '91');
INSERT INTO `sys_role_menu` VALUES ('5672', '1', '92');
INSERT INTO `sys_role_menu` VALUES ('5673', '1', '87');
INSERT INTO `sys_role_menu` VALUES ('5674', '1', '95');
INSERT INTO `sys_role_menu` VALUES ('5675', '1', '96');
INSERT INTO `sys_role_menu` VALUES ('5676', '1', '97');
INSERT INTO `sys_role_menu` VALUES ('5677', '1', '98');
INSERT INTO `sys_role_menu` VALUES ('5678', '1', '94');
INSERT INTO `sys_role_menu` VALUES ('5679', '1', '101');
INSERT INTO `sys_role_menu` VALUES ('5680', '1', '102');
INSERT INTO `sys_role_menu` VALUES ('5681', '1', '104');
INSERT INTO `sys_role_menu` VALUES ('5682', '1', '105');
INSERT INTO `sys_role_menu` VALUES ('5683', '1', '99');
INSERT INTO `sys_role_menu` VALUES ('5684', '1', '23');
INSERT INTO `sys_role_menu` VALUES ('5685', '1', '41');
INSERT INTO `sys_role_menu` VALUES ('5686', '1', '42');
INSERT INTO `sys_role_menu` VALUES ('5687', '1', '40');
INSERT INTO `sys_role_menu` VALUES ('5688', '1', '45');
INSERT INTO `sys_role_menu` VALUES ('5689', '1', '57');
INSERT INTO `sys_role_menu` VALUES ('5690', '1', '55');
INSERT INTO `sys_role_menu` VALUES ('5691', '1', '82');
INSERT INTO `sys_role_menu` VALUES ('5692', '1', '108');
INSERT INTO `sys_role_menu` VALUES ('5693', '1', '109');
INSERT INTO `sys_role_menu` VALUES ('5694', '1', '110');
INSERT INTO `sys_role_menu` VALUES ('5695', '1', '112');
INSERT INTO `sys_role_menu` VALUES ('5696', '1', '113');
INSERT INTO `sys_role_menu` VALUES ('5697', '1', '114');
INSERT INTO `sys_role_menu` VALUES ('5698', '1', '115');
INSERT INTO `sys_role_menu` VALUES ('5699', '1', '93');
INSERT INTO `sys_role_menu` VALUES ('5700', '1', '39');
INSERT INTO `sys_role_menu` VALUES ('5701', '1', '74');
INSERT INTO `sys_role_menu` VALUES ('5702', '1', '75');
INSERT INTO `sys_role_menu` VALUES ('5703', '1', '76');
INSERT INTO `sys_role_menu` VALUES ('5704', '1', '77');
INSERT INTO `sys_role_menu` VALUES ('5705', '1', '78');
INSERT INTO `sys_role_menu` VALUES ('5706', '1', '73');
INSERT INTO `sys_role_menu` VALUES ('5707', '1', '72');
INSERT INTO `sys_role_menu` VALUES ('5708', '1', '107');
INSERT INTO `sys_role_menu` VALUES ('5709', '1', '106');
INSERT INTO `sys_role_menu` VALUES ('5710', '1', '56');
INSERT INTO `sys_role_menu` VALUES ('5711', '1', '81');
INSERT INTO `sys_role_menu` VALUES ('5712', '1', '103');
INSERT INTO `sys_role_menu` VALUES ('5713', '1', '100');
INSERT INTO `sys_role_menu` VALUES ('5714', '1', '117');
INSERT INTO `sys_role_menu` VALUES ('5715', '1', '121');
INSERT INTO `sys_role_menu` VALUES ('5716', '1', '116');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('43', 'c0a7b5cdb76d4eba9e02d29766e607a5', '14');
INSERT INTO `sys_user_role` VALUES ('45', '300e18574502446bb642cf81436f59da', '1');
INSERT INTO `sys_user_role` VALUES ('46', 'f23609e7e74d4e4e9b60b194138660e0', '1');
INSERT INTO `sys_user_role` VALUES ('48', '738b32b53a1a48999eb429eabbc50d70', '1');
INSERT INTO `sys_user_role` VALUES ('49', '30bf60eb3e6a43edbb2705ead42669cd', '1');
INSERT INTO `sys_user_role` VALUES ('50', '85b58625c8dd4b0e88e82dd45d3f38a9', '1');
INSERT INTO `sys_user_role` VALUES ('54', '28189a9574f94b0eaed5212155d9bf41', '1');
INSERT INTO `sys_user_role` VALUES ('57', '288fe0fe783249ccbb4788c4f19860bd', '14');
INSERT INTO `sys_user_role` VALUES ('62', '1', '1');
INSERT INTO `sys_user_role` VALUES ('68', '30b97adde559484aba1c30bcf51f8943', '14');

-- ----------------------------
-- Table structure for s_static_param
-- ----------------------------
DROP TABLE IF EXISTS `s_static_param`;
CREATE TABLE `s_static_param` (
  `static_param_name` varchar(100) NOT NULL COMMENT '参数名',
  `static_param_value` varchar(40) NOT NULL COMMENT '参数值',
  `static_param_value_desc` varchar(200) NOT NULL COMMENT '参数描述',
  `static_param_value_order` int(11) NOT NULL COMMENT '参数排序',
  `user_id` varchar(40) NOT NULL COMMENT '创建人',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`static_param_name`,`static_param_value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_static_param
-- ----------------------------
INSERT INTO `s_static_param` VALUES ('SOURCE', '0', '系统发放', '0', '1', '2019-04-25 11:20:20');
INSERT INTO `s_static_param` VALUES ('SOURCE', '1', '用户购买', '1', '1', '2019-04-25 11:20:20');
INSERT INTO `s_static_param` VALUES ('SWIT', '0', 'off', '1', '1', '2019-04-25 11:20:20');
INSERT INTO `s_static_param` VALUES ('SWIT', '1', 'on', '0', '1', '2019-04-25 11:20:20');
