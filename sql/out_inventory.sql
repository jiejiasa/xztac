/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : localhost:3306
 Source Schema         : xzt

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 31/10/2023 18:36:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for out_inventory
-- ----------------------------
DROP TABLE IF EXISTS `out_inventory`;
CREATE TABLE `out_inventory`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `inventory_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '仓库编号',
  `people_id` int NULL DEFAULT NULL COMMENT '用户id',
  `car_information` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '车辆信息',
  `car_num` int NULL DEFAULT NULL COMMENT '数量',
  `time` datetime NULL DEFAULT NULL COMMENT '时间',
  `in_man_id` int NULL DEFAULT NULL COMMENT '库存管理表id',
  `settle_status` int NULL DEFAULT NULL COMMENT '0：未结清 1： 已结清',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '出库记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of out_inventory
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
