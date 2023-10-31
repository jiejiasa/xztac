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

 Date: 31/10/2023 18:36:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for in_inventory
-- ----------------------------
DROP TABLE IF EXISTS `in_inventory`;
CREATE TABLE `in_inventory`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `inventory_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '仓库编号',
  `people_id` int NULL DEFAULT NULL COMMENT '用户id',
  `car_information` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '车辆信息',
  `car_num` int NULL DEFAULT NULL COMMENT '数量',
  `time` datetime NULL DEFAULT NULL COMMENT '入库时间',
  `in_ma_id` int NULL DEFAULT NULL COMMENT '库存id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1982652419 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '入库记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of in_inventory
-- ----------------------------
INSERT INTO `in_inventory` VALUES (-2006175743, NULL, 1, NULL, NULL, '2023-10-31 14:43:31', NULL);
INSERT INTO `in_inventory` VALUES (-1980960767, '撒旦', 1, '打撒', 2, '2023-10-23 12:05:18', NULL);
INSERT INTO `in_inventory` VALUES (-1855115262, NULL, 1, NULL, NULL, '2023-10-31 14:24:58', NULL);
INSERT INTO `in_inventory` VALUES (-1800609790, NULL, 1, NULL, NULL, '2023-10-31 10:33:26', NULL);
INSERT INTO `in_inventory` VALUES (-1620242431, NULL, 1, NULL, NULL, '2023-10-31 10:41:50', NULL);
INSERT INTO `in_inventory` VALUES (-1477705727, NULL, 1, NULL, NULL, '2023-10-31 14:23:46', NULL);
INSERT INTO `in_inventory` VALUES (-1389555710, '仓库一', 1, 'xx汽车', 1, '2023-09-21 16:30:04', NULL);
INSERT INTO `in_inventory` VALUES (-1293094911, '仓库一', 1, 'xx汽车', 2, '2023-09-21 16:18:56', NULL);
INSERT INTO `in_inventory` VALUES (-1242771455, 'test', 1, '121', 1, NULL, NULL);
INSERT INTO `in_inventory` VALUES (-1230180350, NULL, 1, NULL, NULL, '2023-10-31 10:45:24', NULL);
INSERT INTO `in_inventory` VALUES (-915599358, '仓库一', 1, 'xx汽车', 1, '2023-09-21 16:24:01', NULL);
INSERT INTO `in_inventory` VALUES (-911400959, '仓库一', 1, 'xx汽车', 1, '2023-10-21 01:26:31', NULL);
INSERT INTO `in_inventory` VALUES (-529735679, NULL, 1, NULL, NULL, '2023-10-31 10:39:47', NULL);
INSERT INTO `in_inventory` VALUES (-475197439, NULL, 1, NULL, NULL, '2023-10-31 10:42:43', NULL);
INSERT INTO `in_inventory` VALUES (-370348030, NULL, 1, NULL, NULL, '2023-10-31 10:49:52', NULL);
INSERT INTO `in_inventory` VALUES (-328404990, NULL, 1, NULL, NULL, '2023-10-31 10:44:13', NULL);
INSERT INTO `in_inventory` VALUES (-210956286, '仓库一', 1, 'xx汽车', 1, '2023-09-21 16:36:34', NULL);
INSERT INTO `in_inventory` VALUES (-43184126, '仓库一', 1, 'xx汽车', 1, '2023-09-21 16:27:11', NULL);
INSERT INTO `in_inventory` VALUES (25, NULL, 1, NULL, NULL, '2023-10-31 15:16:25', 25);
INSERT INTO `in_inventory` VALUES (26, NULL, 1, NULL, NULL, '2023-10-31 15:34:36', 26);
INSERT INTO `in_inventory` VALUES (27, NULL, 1, NULL, NULL, '2023-10-31 16:17:34', 27);
INSERT INTO `in_inventory` VALUES (204271617, NULL, 1, NULL, NULL, '2023-10-31 10:45:23', NULL);
INSERT INTO `in_inventory` VALUES (208416769, NULL, 1, NULL, NULL, '2023-10-31 14:46:18', NULL);
INSERT INTO `in_inventory` VALUES (543948801, NULL, 1, NULL, NULL, '2023-10-30 11:17:44', NULL);
INSERT INTO `in_inventory` VALUES (653119490, NULL, 1, NULL, NULL, '2023-10-31 10:50:33', NULL);
INSERT INTO `in_inventory` VALUES (824967170, NULL, 1, NULL, NULL, '2023-10-30 11:18:12', NULL);
INSERT INTO `in_inventory` VALUES (837611521, '打', 1, '阿斯蒂芬', NULL, '2023-10-23 12:03:02', NULL);
INSERT INTO `in_inventory` VALUES (850194433, '啊的房间', 1, '安抚数据库', 1, '2023-10-23 11:55:49', NULL);
INSERT INTO `in_inventory` VALUES (908865538, NULL, 1, NULL, NULL, '2023-10-31 14:40:45', NULL);
INSERT INTO `in_inventory` VALUES (992751618, NULL, 1, NULL, NULL, '2023-10-31 14:41:27', NULL);
INSERT INTO `in_inventory` VALUES (1038938114, '十大打法', 1, '阿斯顿', NULL, '2023-10-23 11:59:11', NULL);
INSERT INTO `in_inventory` VALUES (1315770369, NULL, 1, NULL, NULL, '2023-10-31 10:42:57', NULL);
INSERT INTO `in_inventory` VALUES (1487728642, '大苏打', 1, '阿斯蒂芬', NULL, '2023-10-23 12:00:54', NULL);
INSERT INTO `in_inventory` VALUES (1596731393, NULL, 1, NULL, NULL, '2023-10-31 14:46:47', NULL);
INSERT INTO `in_inventory` VALUES (1672273922, '2', 1, '12', 12, '2023-10-24 10:15:29', NULL);
INSERT INTO `in_inventory` VALUES (1840025601, NULL, 1, NULL, -5, '2023-10-26 17:34:36', NULL);
INSERT INTO `in_inventory` VALUES (1982652418, NULL, 1, NULL, NULL, '2023-10-31 10:37:09', NULL);

SET FOREIGN_KEY_CHECKS = 1;
