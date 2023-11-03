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

 Date: 02/11/2023 17:51:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for in_inventory
-- ----------------------------
DROP TABLE IF EXISTS `in_inventory`;
CREATE TABLE `in_inventory`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `in_ma_id` int NULL DEFAULT NULL COMMENT '库存id',
  `people_id` int NULL DEFAULT NULL COMMENT '用户id',
  `time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '入库登记时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1982652419 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '入库记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of in_inventory
-- ----------------------------
INSERT INTO `in_inventory` VALUES (29, 29, 1, '2023-11-01 15:52:39');
INSERT INTO `in_inventory` VALUES (30, 30, 1, '2023-11-01 16:36:00');
INSERT INTO `in_inventory` VALUES (31, 31, 101, '2023-11-01 16:39:09');
INSERT INTO `in_inventory` VALUES (32, 32, 101, '2023-11-01 16:54:44');
INSERT INTO `in_inventory` VALUES (33, 33, 101, '2023-11-01 17:02:21');
INSERT INTO `in_inventory` VALUES (34, 34, 1, '2023-11-02 10:17:14');

-- ----------------------------
-- Table structure for inventory_management
-- ----------------------------
DROP TABLE IF EXISTS `inventory_management`;
CREATE TABLE `inventory_management`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `customer_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '客户名称',
  `region` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地区',
  `parking_garage` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '停放车库',
  `make_and_model` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '入库型号',
  `business_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '业务类型',
  `licens_plate_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '车牌号',
  `inbound_date` date NULL DEFAULT NULL COMMENT '入库日期',
  `clearance_team` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '清收团队',
  `pick_up_fee` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收车费用',
  `is_normal` int NULL DEFAULT 0 COMMENT '0：正常 1：异常 2：未知',
  `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `status` int NULL DEFAULT 0 COMMENT '状态 ：0：在库 1：审核中 2：未通过 3：审核通过 ',
  `settle_status` binary(1) NULL DEFAULT NULL COMMENT '0:未支付1：已支付',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `time` date NULL DEFAULT NULL COMMENT '时间',
  `del_flag` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除0：正常 1：删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '车辆库存管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of inventory_management
-- ----------------------------
INSERT INTO `inventory_management` VALUES (29, '暗室逢灯', '撒地方', '撒地方', '阿斯蒂芬', '阿斯蒂芬', '阿斯蒂芬', '2023-10-31', '撒地方', '23.5', 0, '撒地方', 3, 0x31, 1, NULL, 0);
INSERT INTO `inventory_management` VALUES (30, '的方式', '撒地方2', '阿斯蒂芬', '安抚', '阿斯蒂芬', '撒地方', '2023-10-31', '撒地方', '123.2', 0, '暗室逢灯', 3, 0x30, 1, NULL, 0);
INSERT INTO `inventory_management` VALUES (31, '发', '阿斯蒂芬', '爱的色放', '撒地方', '撒反对', '阿斯弗', '2023-11-01', '暗室逢灯', NULL, 0, '阿斯蒂芬', 1, 0x30, 101, NULL, 0);
INSERT INTO `inventory_management` VALUES (32, '撒旦', '阿斯顿', '撒地方', '阿斯蒂芬', '阿斯蒂芬', '撒地方', '2023-10-31', '撒地方', '123.5', 0, '撒地方', 1, 0x30, 101, NULL, 0);
INSERT INTO `inventory_management` VALUES (33, '阿道夫', '撒地方', '阿斯蒂芬', '撒地方', '阿斯蒂芬·', '撒地方', '2023-10-31', '撒地方', NULL, 0, '撒地方', 1, 0x30, 101, NULL, 0);
INSERT INTO `inventory_management` VALUES (34, '1', '1', '1', '1', '1', '1', '2023-11-02', '1', '1', 2, '大苏打·', 0, 0x31, 1, NULL, 0);

-- ----------------------------
-- Table structure for out_inventory
-- ----------------------------
DROP TABLE IF EXISTS `out_inventory`;
CREATE TABLE `out_inventory`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `outbound_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '出庫原因',
  `creator_id` int NULL DEFAULT NULL COMMENT '出庫人id',
  `parking_fees` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '停车费用',
  `paid` int NULL DEFAULT NULL COMMENT '是否已付',
  `in_man_id` int NULL DEFAULT NULL COMMENT '库存管理表id',
  `out_remark` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '出库备注',
  `vehicle_recipient` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '车辆接收人',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间',
  `del_flag` binary(1) NOT NULL DEFAULT 0x30 COMMENT '是否刪除 0：未刪除 1：已刪除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1953312770 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '出库记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of out_inventory
-- ----------------------------
INSERT INTO `out_inventory` VALUES (-1469239294, '撒地方', NULL, NULL, NULL, 33, '1', '阿斯蒂芬', '2023-11-02 16:43:58', 0x30);
INSERT INTO `out_inventory` VALUES (-1397936126, '阿斯蒂芬', 1, '1', 1, 29, '1', '阿斯顿', '2023-11-02 16:57:28', 0x30);
INSERT INTO `out_inventory` VALUES (-617795582, '撒地方', NULL, NULL, NULL, 32, '1', '撒地方', '2023-11-02 16:43:59', 0x30);
INSERT INTO `out_inventory` VALUES (1919758338, '阿斯蒂芬', NULL, NULL, NULL, 30, '1', '撒反对', '2023-11-02 16:44:00', 0x30);
INSERT INTO `out_inventory` VALUES (1953312769, '阿斯蒂芬', NULL, NULL, NULL, 31, '1', '阿凡达', '2023-11-02 16:44:04', 0x30);

SET FOREIGN_KEY_CHECKS = 1;
