/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : xzt

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 01/11/2023 23:17:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xzt_garage_price
-- ----------------------------
DROP TABLE IF EXISTS `xzt_garage_price`;
CREATE TABLE `xzt_garage_price`  (
  `id` int(64) NOT NULL COMMENT '主键',
  `p_id` int(64) NULL DEFAULT NULL COMMENT '父id',
  `garage_type_or_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '车库类型或名称',
  `day_or_month_price` int(5) NULL DEFAULT NULL COMMENT '每天/月价格',
  `fixed_price` int(10) NULL DEFAULT NULL COMMENT '7天内的固定价格',
  `creater_id` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `del_flag` binary(1) NOT NULL DEFAULT 0x30 COMMENT '是否删除: 0:正常 1：删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xzt_garage_price
-- ----------------------------
INSERT INTO `xzt_garage_price` VALUES (1, 0, '月', 0, 0, 1, '2023-11-01 23:07:04', 0x30);
INSERT INTO `xzt_garage_price` VALUES (2, 1, '苏州', 1, 1, 2, '2023-11-01 23:07:22', 0x30);

SET FOREIGN_KEY_CHECKS = 1;
