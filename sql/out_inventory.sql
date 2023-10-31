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

 Date: 31/10/2023 22:52:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for out_inventory
-- ----------------------------
DROP TABLE IF EXISTS `out_inventory`;
CREATE TABLE `out_inventory`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `outbound_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '出庫原因',
  `creator_id` int(64) NULL DEFAULT NULL COMMENT '出庫人id',
  `parking_fees` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '停车费用',
  `paid` int(11) NULL DEFAULT NULL COMMENT '是否已付',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间',
  `in_man_id` int(64) NULL DEFAULT NULL COMMENT '库存管理表id',
  `vehicle_recipient` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '车辆接收人',
  `del_flag` binary(1) NOT NULL DEFAULT 0x30 COMMENT '是否刪除 0：未刪除 1：已刪除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '出库记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of out_inventory
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
