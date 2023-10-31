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

 Date: 31/10/2023 18:36:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for inventory_management
-- ----------------------------
DROP TABLE IF EXISTS `inventory_management`;
CREATE TABLE `inventory_management`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `inventory_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '仓库编号',
  `car_information` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '车辆信息',
  `car_num` int NULL DEFAULT NULL COMMENT '车辆数量',
  `creator_id` int NULL DEFAULT NULL COMMENT '创建人id',
  `time` date NULL DEFAULT NULL COMMENT '时间',
  `del_flag` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除0：正常 1：删除',
  `customer_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '客户名称',
  `region` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地区',
  `parking_garage` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '停放车库',
  `make_and_model` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '入库型号',
  `business_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '业务类型',
  `licens_plate_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '车牌号',
  `status` int NULL DEFAULT 0 COMMENT '状态 ：0：在库 1：审核中 2：出库',
  `inbound_date` date NULL DEFAULT NULL COMMENT '入库日期',
  `clearance_team` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '清收团队',
  `pick_up_fee` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收车费用',
  `settle_status` binary(1) NULL DEFAULT NULL COMMENT '0:未支付1：已支付',
  `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '车辆库存管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of inventory_management
-- ----------------------------
INSERT INTO `inventory_management` VALUES (1, '仓库一', 'xx汽车', 8, 1, '2023-09-21', 0, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `inventory_management` VALUES (5, '啊的房间', '安抚数据库', 1, 1, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `inventory_management` VALUES (9, '撒旦', '打撒', 2, 1, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `inventory_management` VALUES (10, '2', '12', 12, 1, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `inventory_management` VALUES (11, NULL, NULL, -5, 1, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `inventory_management` VALUES (12, NULL, NULL, NULL, 1, NULL, 1, '发', '阿斯蒂芬', NULL, NULL, '阿斯顿', NULL, 0, NULL, '暗室逢灯', '123', 0x30, NULL);
INSERT INTO `inventory_management` VALUES (13, NULL, NULL, NULL, 1, NULL, 0, '阿斯蒂芬', '暗室逢灯', NULL, NULL, '爱的色放', NULL, 0, '2023-10-31', '阿斯蒂芬', '123', 0x30, NULL);
INSERT INTO `inventory_management` VALUES (14, NULL, NULL, NULL, 1, NULL, 0, 'asdf1', 'asdf1', NULL, NULL, NULL, NULL, 0, NULL, 'asfd1', NULL, NULL, NULL);
INSERT INTO `inventory_management` VALUES (15, NULL, NULL, NULL, 1, NULL, 0, 'sad1', 'ad1', NULL, NULL, NULL, NULL, 0, NULL, 'sad1', NULL, NULL, NULL);
INSERT INTO `inventory_management` VALUES (16, NULL, NULL, NULL, 1, NULL, 0, 'qw', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `inventory_management` VALUES (17, NULL, NULL, NULL, 1, NULL, 0, 'qw1', NULL, NULL, NULL, NULL, 'da1阿斯蒂芬坷拉激发了', 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `inventory_management` VALUES (18, NULL, NULL, NULL, 1, NULL, 0, '暗室逢灯', NULL, NULL, NULL, NULL, NULL, 0, '2023-10-31', NULL, NULL, NULL, NULL);
INSERT INTO `inventory_management` VALUES (19, NULL, NULL, NULL, 1, NULL, 0, '挨个看了', '萨芬哦', '撒放假哦', '啊是当年覅哦', '爱上佛i', '埃是法国农家', 0, '2023-10-30', '爱上防护', '456.1456', 0x30, 'vnoqnhlskadjf`1');
INSERT INTO `inventory_management` VALUES (20, NULL, NULL, NULL, 1, NULL, 0, NULL, NULL, NULL, NULL, '阿斯蒂芬', NULL, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `inventory_management` VALUES (21, NULL, NULL, NULL, 1, NULL, 0, NULL, NULL, NULL, NULL, '阿斯蒂芬', NULL, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `inventory_management` VALUES (22, NULL, NULL, NULL, 1, NULL, 0, NULL, NULL, NULL, NULL, '阿斯蒂芬', NULL, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `inventory_management` VALUES (23, NULL, NULL, NULL, 1, NULL, 0, NULL, NULL, NULL, NULL, '阿斯蒂芬', NULL, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `inventory_management` VALUES (24, NULL, NULL, NULL, 1, NULL, 0, '请问', NULL, NULL, NULL, '阿斯蒂芬答复', NULL, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `inventory_management` VALUES (25, NULL, NULL, NULL, 1, NULL, 0, '请问', NULL, NULL, NULL, '阿斯蒂芬答复', NULL, 0, '2023-10-18', NULL, NULL, NULL, NULL);
INSERT INTO `inventory_management` VALUES (26, NULL, NULL, NULL, 1, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, 0x30, NULL);
INSERT INTO `inventory_management` VALUES (27, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, '艾弗森', NULL, NULL, 0, NULL, NULL, NULL, 0x30, NULL);

SET FOREIGN_KEY_CHECKS = 1;
