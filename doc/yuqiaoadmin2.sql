/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : localhost:3306
 Source Schema         : yuqiaoadmin2

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 23/08/2020 20:00:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_app_user
-- ----------------------------
DROP TABLE IF EXISTS `t_app_user`;
CREATE TABLE `t_app_user`  (
  `f_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `f_about` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_account_non_expired` bit(1) NOT NULL,
  `f_account_non_locked` bit(1) NOT NULL,
  `f_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_country` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_credentials_non_expired` bit(1) NOT NULL,
  `f_email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_enabled` bit(1) NOT NULL,
  `f_first_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_last_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_nation_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_postal_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_province` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_real_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_tel` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `f_version` int(11) NULL DEFAULT NULL,
  `f_f_organisation_id` bigint(20) NULL DEFAULT NULL,
  `f_client_machine` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_client_os` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_f_created` datetime(0) NULL DEFAULT NULL,
  `f_f_created_by` bigint(20) NULL DEFAULT NULL,
  `f_f_created_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_f_updated` datetime(0) NULL DEFAULT NULL,
  `f_f_updated_by` bigint(20) NULL DEFAULT NULL,
  `f_f_updated_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`f_id`) USING BTREE,
  UNIQUE INDEX `UK_js8umblie8bkavrgql30ojac0`(`f_username`) USING BTREE,
  INDEX `FKa5h4emj3109dpiwd55e8gvnqa`(`f_f_organisation_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 100 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_app_user
-- ----------------------------
INSERT INTO `t_app_user` VALUES (2, 'about', b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd2', NULL, NULL, NULL, '', 'hxx2', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (3, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd1', NULL, NULL, NULL, NULL, 'hxx1', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (4, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd10', NULL, NULL, NULL, NULL, 'hxx10', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (5, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd11', NULL, NULL, NULL, NULL, 'hxx11', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (6, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd12', NULL, NULL, NULL, NULL, 'hxx12', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (7, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd13', NULL, NULL, NULL, NULL, 'hxx13', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (8, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd14', NULL, NULL, NULL, NULL, 'hxx14', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (9, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd15', NULL, NULL, NULL, NULL, 'hxx15', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (10, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd16', NULL, NULL, NULL, NULL, 'hxx16', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (11, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd17', NULL, NULL, NULL, NULL, 'hxx17', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (12, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd18', NULL, NULL, NULL, NULL, 'hxx18', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (13, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd19', NULL, NULL, NULL, NULL, 'hxx19', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (14, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd20', NULL, NULL, NULL, NULL, 'hxx20', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (15, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd21', NULL, NULL, NULL, NULL, 'hxx21', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (16, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd22', NULL, NULL, NULL, NULL, 'hxx22', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (17, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd23', NULL, NULL, NULL, NULL, 'hxx23', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (18, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd24', NULL, NULL, NULL, NULL, 'hxx24', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (19, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd25', NULL, NULL, NULL, NULL, 'hxx25', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (20, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd26', NULL, NULL, NULL, NULL, 'hxx26', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (21, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd27', NULL, NULL, NULL, NULL, 'hxx27', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (22, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd28', NULL, NULL, NULL, NULL, 'hxx28', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (23, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd29', NULL, NULL, NULL, NULL, 'hxx29', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (24, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd30', NULL, NULL, NULL, NULL, 'hxx30', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (25, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd31', NULL, NULL, NULL, NULL, 'hxx31', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (26, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd32', NULL, NULL, NULL, NULL, 'hxx32', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (27, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd33', NULL, NULL, NULL, NULL, 'hxx33', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (28, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd34', NULL, NULL, NULL, NULL, 'hxx34', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (29, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd35', NULL, NULL, NULL, NULL, 'hxx35', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (30, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd36', NULL, NULL, NULL, NULL, 'hxx36', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (31, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd37', NULL, NULL, NULL, NULL, 'hxx37', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (32, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd38', NULL, NULL, NULL, NULL, 'hxx38', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (33, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd39', NULL, NULL, NULL, NULL, 'hxx39', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (34, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd40', NULL, NULL, NULL, NULL, 'hxx40', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (35, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd41', NULL, NULL, NULL, NULL, 'hxx41', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (36, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd42', NULL, NULL, NULL, NULL, 'hxx42', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (37, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd43', NULL, NULL, NULL, NULL, 'hxx43', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (38, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd44', NULL, NULL, NULL, NULL, 'hxx44', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (39, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd45', NULL, NULL, NULL, NULL, 'hxx45', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (40, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd46', NULL, NULL, NULL, NULL, 'hxx46', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (41, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd47', NULL, NULL, NULL, NULL, 'hxx47', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (42, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd48', NULL, NULL, NULL, NULL, 'hxx48', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (43, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd49', NULL, NULL, NULL, NULL, 'hxx49', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (44, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd50', NULL, NULL, NULL, NULL, 'hxx50', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (45, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd51', NULL, NULL, NULL, NULL, 'hxx51', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (46, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd52', NULL, NULL, NULL, NULL, 'hxx52', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (47, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd53', NULL, NULL, NULL, NULL, 'hxx53', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (48, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd54', NULL, NULL, NULL, NULL, 'hxx54', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (49, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd55', NULL, NULL, NULL, NULL, 'hxx55', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (50, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd56', NULL, NULL, NULL, NULL, 'hxx56', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (51, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd57', NULL, NULL, NULL, NULL, 'hxx57', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (52, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd58', NULL, NULL, NULL, NULL, 'hxx58', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (53, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd59', NULL, NULL, NULL, NULL, 'hxx59', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (54, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd60', NULL, NULL, NULL, NULL, 'hxx60', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (55, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd61', NULL, NULL, NULL, NULL, 'hxx61', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (56, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd62', NULL, NULL, NULL, NULL, 'hxx62', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (57, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd63', NULL, NULL, NULL, NULL, 'hxx63', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (58, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd64', NULL, NULL, NULL, NULL, 'hxx64', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (59, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd65', NULL, NULL, NULL, NULL, 'hxx65', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (60, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd66', NULL, NULL, NULL, NULL, 'hxx66', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (61, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd67', NULL, NULL, NULL, NULL, 'hxx67', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (62, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd68', NULL, NULL, NULL, NULL, 'hxx68', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (63, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd69', NULL, NULL, NULL, NULL, 'hxx69', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (64, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd70', NULL, NULL, NULL, NULL, 'hxx70', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (65, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd71', NULL, NULL, NULL, NULL, 'hxx71', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (66, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd72', NULL, NULL, NULL, NULL, 'hxx72', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (67, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd73', NULL, NULL, NULL, NULL, 'hxx73', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (68, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd74', NULL, NULL, NULL, NULL, 'hxx74', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (69, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd75', NULL, NULL, NULL, NULL, 'hxx75', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (70, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd76', NULL, NULL, NULL, NULL, 'hxx76', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (71, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd77', NULL, NULL, NULL, NULL, 'hxx77', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (72, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd78', NULL, NULL, NULL, NULL, 'hxx78', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (73, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd79', NULL, NULL, NULL, NULL, 'hxx79', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (74, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd80', NULL, NULL, NULL, NULL, 'hxx80', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (75, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd81', NULL, NULL, NULL, NULL, 'hxx81', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (76, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd82', NULL, NULL, NULL, NULL, 'hxx82', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (77, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd83', NULL, NULL, NULL, NULL, 'hxx83', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (78, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd84', NULL, NULL, NULL, NULL, 'hxx84', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (79, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd85', NULL, NULL, NULL, NULL, 'hxx85', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (80, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd86', NULL, NULL, NULL, NULL, 'hxx86', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (81, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd87', NULL, NULL, NULL, NULL, 'hxx87', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (82, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd88', NULL, NULL, NULL, NULL, 'hxx88', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (83, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd89', NULL, NULL, NULL, NULL, 'hxx89', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (84, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd90', NULL, NULL, NULL, NULL, 'hxx90', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (85, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd91', NULL, NULL, NULL, NULL, 'hxx91', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (86, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd92', NULL, NULL, NULL, NULL, 'hxx92', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (87, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd93', NULL, NULL, NULL, NULL, 'hxx93', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (88, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd94', NULL, NULL, NULL, NULL, 'hxx94', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (89, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd95', NULL, NULL, NULL, NULL, 'hxx95', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (90, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd96', NULL, NULL, NULL, NULL, 'hxx96', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (91, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd97', NULL, NULL, NULL, NULL, 'hxx97', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (92, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd98', NULL, NULL, NULL, NULL, 'hxx98', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (93, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd99', NULL, NULL, NULL, NULL, 'hxx99', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (94, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, 'pwd100', NULL, NULL, NULL, NULL, 'hxx100', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_app_user` VALUES (95, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, '$2a$10$cVqQ357kwblnle6BInV09ef6GVT2Ap8QvHh2aBLKiJR4nLgiYIf0a', NULL, NULL, NULL, NULL, 'test1', 0, NULL, NULL, NULL, '2020-08-21 11:18:43', -1, NULL, '2020-08-21 11:18:43', -1, NULL);
INSERT INTO `t_app_user` VALUES (96, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, '$2a$10$rWaCfmDWa/hb2RkrnBNgU.JxJ5YDKh7zyVXJvhX1tKpMJ9s9wpbtu', NULL, NULL, NULL, NULL, 'test2', 0, NULL, NULL, NULL, '2020-08-21 11:18:43', -1, NULL, '2020-08-21 11:18:43', -1, NULL);
INSERT INTO `t_app_user` VALUES (97, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, '$2a$10$mXeYnm4FI1t9au7QCHdyDeFBqH3it4xCdw9YhI47BQhZPTQ7oilbC', NULL, NULL, NULL, NULL, 'test3', 0, NULL, NULL, NULL, '2020-08-21 11:18:43', -1, NULL, '2020-08-21 11:18:43', -1, NULL);
INSERT INTO `t_app_user` VALUES (98, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, '$2a$10$/8zqkKiaGPylAjaxtMnItuQHd8vWrKiXLlGP/SfimzzngcxFL.bxG', NULL, NULL, NULL, NULL, 'test4', 0, NULL, NULL, NULL, '2020-08-21 11:18:43', -1, NULL, '2020-08-21 11:18:43', -1, NULL);
INSERT INTO `t_app_user` VALUES (99, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, b'0', NULL, NULL, NULL, NULL, '$2a$10$Fgg7Srub569j0PF9BBoZV./DyCimyIZrREgzIWs1i0jb9Ixgez8gK', NULL, NULL, NULL, NULL, 'test5', 0, NULL, NULL, NULL, '2020-08-21 11:18:43', -1, NULL, '2020-08-21 11:18:43', -1, NULL);

-- ----------------------------
-- Table structure for t_authority
-- ----------------------------
DROP TABLE IF EXISTS `t_authority`;
CREATE TABLE `t_authority`  (
  `f_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `f_authority` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_category1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_category2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_category3` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`f_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_authority
-- ----------------------------
INSERT INTO `t_authority` VALUES (1, '管理员', NULL, NULL, NULL, '管理员角色');
INSERT INTO `t_authority` VALUES (2, '内容运营', NULL, NULL, NULL, '内容运营者角色');
INSERT INTO `t_authority` VALUES (14, '2', NULL, NULL, NULL, '2');
INSERT INTO `t_authority` VALUES (15, '3', NULL, NULL, NULL, '3');
INSERT INTO `t_authority` VALUES (13, '1', NULL, NULL, NULL, '1');
INSERT INTO `t_authority` VALUES (16, '4', NULL, NULL, NULL, '4');
INSERT INTO `t_authority` VALUES (17, '5', NULL, NULL, NULL, '5');
INSERT INTO `t_authority` VALUES (18, '6', NULL, NULL, NULL, '6');
INSERT INTO `t_authority` VALUES (19, '7', NULL, NULL, NULL, '7');
INSERT INTO `t_authority` VALUES (20, '8', NULL, NULL, NULL, '8');
INSERT INTO `t_authority` VALUES (21, '9', NULL, NULL, NULL, '9');
INSERT INTO `t_authority` VALUES (22, '10', NULL, NULL, NULL, '10');

-- ----------------------------
-- Table structure for t_demo
-- ----------------------------
DROP TABLE IF EXISTS `t_demo`;
CREATE TABLE `t_demo`  (
  `f_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `f_client_machine` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_client_os` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_f_created` datetime(0) NULL DEFAULT NULL,
  `f_f_created_by` bigint(20) NULL DEFAULT NULL,
  `f_f_created_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_f_updated` datetime(0) NULL DEFAULT NULL,
  `f_f_updated_by` bigint(20) NULL DEFAULT NULL,
  `f_f_updated_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_attr1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_attr2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`f_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_demo
-- ----------------------------

-- ----------------------------
-- Table structure for t_logging
-- ----------------------------
DROP TABLE IF EXISTS `t_logging`;
CREATE TABLE `t_logging`  (
  `f_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `f_browser` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_business_type` int(11) NULL DEFAULT NULL,
  `f_create_time` datetime(0) NULL DEFAULT NULL,
  `f_description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_error_msg` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `f_method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_operate_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_operate_id` bigint(20) NULL DEFAULT NULL,
  `f_operate_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_operate_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_request_body` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_request_method` int(11) NULL DEFAULT NULL,
  `f_request_param` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_response_body` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_success` bit(1) NOT NULL,
  `f_system_os` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`f_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 97 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_logging
-- ----------------------------
INSERT INTO `t_logging` VALUES (1, '谷歌浏览器', 0, '2020-08-21 11:59:41', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (2, '谷歌浏览器', 0, '2020-08-21 12:28:57', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (3, '谷歌浏览器', 0, '2020-08-21 13:12:49', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (4, '谷歌浏览器', 0, '2020-08-21 13:13:01', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (5, '谷歌浏览器', 0, '2020-08-21 13:13:32', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (6, '谷歌浏览器', 0, '2020-08-21 13:13:38', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (7, '谷歌浏览器', 0, '2020-08-21 13:16:45', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (8, '谷歌浏览器', 0, '2020-08-21 13:16:52', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (9, '谷歌浏览器', 0, '2020-08-21 13:18:39', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (10, '谷歌浏览器', 0, '2020-08-21 13:18:51', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (11, '谷歌浏览器', 0, '2020-08-21 13:19:04', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (12, '谷歌浏览器', 0, '2020-08-21 23:20:18', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (13, '谷歌浏览器', 0, '2020-08-21 23:37:05', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (14, '谷歌浏览器', 0, '2020-08-22 01:03:52', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (15, '谷歌浏览器', 0, '2020-08-22 01:10:29', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (16, '谷歌浏览器', 0, '2020-08-22 10:48:48', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (17, '谷歌浏览器', 0, '2020-08-22 10:52:40', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (18, '谷歌浏览器', 0, '2020-08-22 10:55:03', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (19, '谷歌浏览器', 0, '2020-08-22 10:56:58', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (20, '谷歌浏览器', 0, '2020-08-22 10:58:17', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (21, '谷歌浏览器', 0, '2020-08-22 10:58:45', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (22, '谷歌浏览器', 0, '2020-08-22 11:02:14', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (23, '谷歌浏览器', 0, '2020-08-22 11:02:21', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (24, '谷歌浏览器', 0, '2020-08-22 11:06:05', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (25, '谷歌浏览器', 0, '2020-08-22 11:06:43', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (26, '谷歌浏览器', 0, '2020-08-22 11:13:08', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (27, '谷歌浏览器', 0, '2020-08-22 11:16:04', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (28, '谷歌浏览器', 0, '2020-08-22 11:16:18', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (29, '谷歌浏览器', 0, '2020-08-22 11:22:08', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (30, '谷歌浏览器', 0, '2020-08-22 11:24:47', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (31, '谷歌浏览器', 0, '2020-08-22 11:26:05', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (32, '谷歌浏览器', 0, '2020-08-22 11:26:27', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (33, '谷歌浏览器', 0, '2020-08-22 11:27:03', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (34, '谷歌浏览器', 0, '2020-08-22 11:27:11', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (35, '谷歌浏览器', 0, '2020-08-22 11:27:50', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (36, '谷歌浏览器', 0, '2020-08-22 11:28:14', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (37, '谷歌浏览器', 0, '2020-08-22 11:28:29', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (38, '谷歌浏览器', 0, '2020-08-22 11:28:48', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (39, '谷歌浏览器', 0, '2020-08-22 11:29:01', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (40, '谷歌浏览器', 0, '2020-08-22 11:29:15', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (41, '谷歌浏览器', 0, '2020-08-22 11:29:47', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (42, '谷歌浏览器', 0, '2020-08-22 11:29:59', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (43, '谷歌浏览器', 0, '2020-08-22 11:31:08', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (44, '谷歌浏览器', 0, '2020-08-22 11:31:33', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (45, '谷歌浏览器', 0, '2020-08-22 11:32:43', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (46, '谷歌浏览器', 0, '2020-08-22 11:32:59', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (47, '谷歌浏览器', 0, '2020-08-22 11:38:38', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (48, '谷歌浏览器', 0, '2020-08-22 11:59:18', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (49, '谷歌浏览器', 3, '2020-08-22 12:03:56', '暂无介绍', NULL, '/system/role/remove/4', '127.0.0.1', 95, 'test1', '/system/role/remove/4', '', 3, NULL, NULL, b'1', 'Windows', '删除角色');
INSERT INTO `t_logging` VALUES (50, '谷歌浏览器', 3, '2020-08-22 12:07:58', '暂无介绍', 'Parameter value [undefined] did not match expected type [java.lang.Long (n/a)]; nested exception is java.lang.IllegalArgumentException: Parameter value [undefined] did not match expected type [java.lang.Long (n/a)]', '/system/role/batchRemove/undefined', '127.0.0.1', 95, 'test1', '/system/role/batchRemove/undefined', '', 3, NULL, NULL, b'0', 'Windows', '批量删除角色');
INSERT INTO `t_logging` VALUES (51, '谷歌浏览器', 3, '2020-08-22 12:08:27', '暂无介绍', 'Parameter value [undefined] did not match expected type [java.lang.Long (n/a)]; nested exception is java.lang.IllegalArgumentException: Parameter value [undefined] did not match expected type [java.lang.Long (n/a)]', '/system/role/batchRemove/undefined', '127.0.0.1', 95, 'test1', '/system/role/batchRemove/undefined', '', 3, NULL, NULL, b'0', 'Windows', '批量删除角色');
INSERT INTO `t_logging` VALUES (52, '谷歌浏览器', 3, '2020-08-22 12:09:00', '暂无介绍', 'Parameter value [3] did not match expected type [java.lang.Long (n/a)]; nested exception is java.lang.IllegalArgumentException: Parameter value [3] did not match expected type [java.lang.Long (n/a)]', '/system/role/batchRemove/3', '127.0.0.1', 95, 'test1', '/system/role/batchRemove/3', '', 3, NULL, NULL, b'0', 'Windows', '批量删除角色');
INSERT INTO `t_logging` VALUES (53, '谷歌浏览器', 0, '2020-08-22 12:10:09', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (54, '谷歌浏览器', 3, '2020-08-22 12:10:19', '暂无介绍', 'Parameter value [3] did not match expected type [java.lang.Long (n/a)]; nested exception is java.lang.IllegalArgumentException: Parameter value [3] did not match expected type [java.lang.Long (n/a)]', '/system/role/batchRemove/3', '127.0.0.1', 95, 'test1', '/system/role/batchRemove/3', '', 3, NULL, NULL, b'0', 'Windows', '批量删除角色');
INSERT INTO `t_logging` VALUES (55, '谷歌浏览器', 3, '2020-08-22 12:11:32', '暂无介绍', 'Parameter value [3] did not match expected type [java.lang.Long (n/a)]; nested exception is java.lang.IllegalArgumentException: Parameter value [3] did not match expected type [java.lang.Long (n/a)]', '/system/role/batchRemove/3', '127.0.0.1', 95, 'test1', '/system/role/batchRemove/3', '', 3, NULL, NULL, b'0', 'Windows', '批量删除角色');
INSERT INTO `t_logging` VALUES (56, '谷歌浏览器', 0, '2020-08-22 12:21:59', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (57, '谷歌浏览器', 3, '2020-08-22 12:22:20', '暂无介绍', 'Parameter value [3,5] did not match expected type [java.lang.Long (n/a)]; nested exception is java.lang.IllegalArgumentException: Parameter value [3,5] did not match expected type [java.lang.Long (n/a)]', '/system/role/batchRemove/3,5', '127.0.0.1', 95, 'test1', '/system/role/batchRemove/3,5', '', 3, NULL, NULL, b'0', 'Windows', '批量删除角色');
INSERT INTO `t_logging` VALUES (58, '谷歌浏览器', 0, '2020-08-22 12:29:33', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (59, '谷歌浏览器', 3, '2020-08-22 12:30:55', '暂无介绍', 'org.hibernate.QueryException: Legacy-style query parameters (`?`) are no longer supported; use JPA-style ordinal parameters (e.g., `?1`) instead : delete from com.yuqiaotech.sysadmin.model.Authority where id in (?) [delete from com.yuqiaotech.sysadmin.model.Authority where id in (?)]; nested exception is java.lang.IllegalArgumentException: org.hibernate.QueryException: Legacy-style query parameters (`?`) are no longer supported; use JPA-style ordinal parameters (e.g., `?1`) instead : delete from com.yuqiaotech.sysadmin.model.Authority where id in (?) [delete from com.yuqiaotech.sysadmin.model.Authority where id in (?)]', '/system/role/batchRemove/3,5', '127.0.0.1', 95, 'test1', '/system/role/batchRemove/3,5', '', 3, NULL, NULL, b'0', 'Windows', '批量删除角色');
INSERT INTO `t_logging` VALUES (60, '谷歌浏览器', 0, '2020-08-22 12:31:33', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (61, '谷歌浏览器', 3, '2020-08-22 12:31:48', '暂无介绍', 'Parameter value [3,5] did not match expected type [java.lang.Long (n/a)]; nested exception is java.lang.IllegalArgumentException: Parameter value [3,5] did not match expected type [java.lang.Long (n/a)]', '/system/role/batchRemove/3,5', '127.0.0.1', 95, 'test1', '/system/role/batchRemove/3,5', '', 3, NULL, NULL, b'0', 'Windows', '批量删除角色');
INSERT INTO `t_logging` VALUES (62, '谷歌浏览器', 0, '2020-08-22 13:28:52', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (63, '谷歌浏览器', 3, '2020-08-22 13:29:00', '暂无介绍', 'Parameter value element [3] did not match expected type [java.lang.Long (n/a)]; nested exception is java.lang.IllegalArgumentException: Parameter value element [3] did not match expected type [java.lang.Long (n/a)]', '/system/role/batchRemove/3,5', '127.0.0.1', 95, 'test1', '/system/role/batchRemove/3,5', '', 3, NULL, NULL, b'0', 'Windows', '批量删除角色');
INSERT INTO `t_logging` VALUES (64, '谷歌浏览器', 0, '2020-08-22 13:34:12', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (65, '谷歌浏览器', 3, '2020-08-22 13:34:21', '暂无介绍', 'java.lang.String cannot be cast to java.lang.Long', '/system/role/batchRemove/3,5', '127.0.0.1', 95, 'test1', '/system/role/batchRemove/3,5', '', 3, NULL, NULL, b'0', 'Windows', '批量删除角色');
INSERT INTO `t_logging` VALUES (66, '谷歌浏览器', 0, '2020-08-22 13:38:03', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (67, '谷歌浏览器', 3, '2020-08-22 13:38:10', '暂无介绍', NULL, '/system/role/batchRemove/3,5', '127.0.0.1', 95, 'test1', '/system/role/batchRemove/3,5', '', 3, NULL, NULL, b'1', 'Windows', '批量删除角色');
INSERT INTO `t_logging` VALUES (68, '谷歌浏览器', 3, '2020-08-22 13:39:48', '暂无介绍', NULL, '/system/role/batchRemove/7,6', '127.0.0.1', 95, 'test1', '/system/role/batchRemove/7,6', '', 3, NULL, NULL, b'1', 'Windows', '批量删除角色');
INSERT INTO `t_logging` VALUES (69, '谷歌浏览器', 3, '2020-08-22 13:40:53', '暂无介绍', NULL, '/system/role/batchRemove/9,8,10', '127.0.0.1', 95, 'test1', '/system/role/batchRemove/9,8,10', '', 3, NULL, NULL, b'1', 'Windows', '批量删除角色');
INSERT INTO `t_logging` VALUES (70, '谷歌浏览器', 3, '2020-08-22 13:42:21', '暂无介绍', NULL, '/system/role/batchRemove/11', '127.0.0.1', 95, 'test1', '/system/role/batchRemove/11', '', 3, NULL, NULL, b'1', 'Windows', '批量删除角色');
INSERT INTO `t_logging` VALUES (71, '谷歌浏览器', 0, '2020-08-22 13:42:43', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (72, '谷歌浏览器', 3, '2020-08-22 13:42:54', '暂无介绍', NULL, '/system/role/batchRemove/12', '127.0.0.1', 95, 'test1', '/system/role/batchRemove/12', '', 3, NULL, NULL, b'1', 'Windows', '批量删除角色');
INSERT INTO `t_logging` VALUES (73, '谷歌浏览器', 0, '2020-08-22 17:37:01', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (74, '谷歌浏览器', 0, '2020-08-22 17:49:54', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (75, '谷歌浏览器', 0, '2020-08-22 17:52:24', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (76, '谷歌浏览器', 0, '2020-08-22 22:21:54', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (77, '谷歌浏览器', 0, '2020-08-23 01:44:51', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (78, '谷歌浏览器', 0, '2020-08-23 11:15:29', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (79, '谷歌浏览器', 0, '2020-08-23 11:19:29', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (80, '谷歌浏览器', 0, '2020-08-23 19:12:15', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (81, '谷歌浏览器', 0, '2020-08-23 19:17:17', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (82, '谷歌浏览器', 0, '2020-08-23 19:18:28', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (83, '谷歌浏览器', 0, '2020-08-23 19:18:41', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (84, '谷歌浏览器', 0, '2020-08-23 19:27:24', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (85, '谷歌浏览器', 0, '2020-08-23 19:30:38', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (86, '谷歌浏览器', 0, '2020-08-23 19:30:46', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (87, '谷歌浏览器', 0, '2020-08-23 19:32:40', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (88, '谷歌浏览器', 0, '2020-08-23 19:36:29', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (89, '谷歌浏览器', 3, '2020-08-23 19:36:50', '暂无介绍', NULL, '/demo/demo/remove/1', '127.0.0.1', 95, 'test1', '/demo/demo/remove/1', '', 3, NULL, NULL, b'1', 'Windows', '删除角色');
INSERT INTO `t_logging` VALUES (90, '谷歌浏览器', 3, '2020-08-23 19:36:58', '暂无介绍', NULL, '/demo/demo/batchRemove/2', '127.0.0.1', 95, 'test1', '/demo/demo/batchRemove/2', '', 3, NULL, NULL, b'1', 'Windows', '批量删除角色');
INSERT INTO `t_logging` VALUES (91, '谷歌浏览器', 0, '2020-08-23 19:39:09', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (92, '谷歌浏览器', 0, '2020-08-23 19:41:30', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (93, '谷歌浏览器', 0, '2020-08-23 19:45:21', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (94, '谷歌浏览器', 0, '2020-08-23 19:52:25', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (95, '谷歌浏览器', 0, '2020-08-23 19:52:54', '返回 Index 主页视图', NULL, '/index', '127.0.0.1', 95, 'test1', '/index', '', 0, NULL, NULL, b'1', 'Windows', '主页');
INSERT INTO `t_logging` VALUES (96, '谷歌浏览器', 3, '2020-08-23 19:53:14', '暂无介绍', NULL, '/demo/demo/batchRemove/3,4', '127.0.0.1', 95, 'test1', '/demo/demo/batchRemove/3,4', '', 3, NULL, NULL, b'1', 'Windows', '批量删除角色');

-- ----------------------------
-- Table structure for t_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `t_operation_log`;
CREATE TABLE `t_operation_log`  (
  `f_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `f_action_method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_action_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_entity_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_entity_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_operation_log` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_operation_time` datetime(0) NULL DEFAULT NULL,
  `f_operation_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_region` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_request_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`f_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_operation_log
-- ----------------------------

-- ----------------------------
-- Table structure for t_option_group
-- ----------------------------
DROP TABLE IF EXISTS `t_option_group`;
CREATE TABLE `t_option_group`  (
  `f_f_group_id` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `f_column_name1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_column_name2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_column_name3` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_column_name4` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_column_name5` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_editor` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_group_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_key_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`f_f_group_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_option_group
-- ----------------------------

-- ----------------------------
-- Table structure for t_organisation
-- ----------------------------
DROP TABLE IF EXISTS `t_organisation`;
CREATE TABLE `t_organisation`  (
  `f_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `f_org_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_org_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_org_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_f_parent_org_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`f_id`) USING BTREE,
  INDEX `FK3s49ry2diltgfb54sktjy00lr`(`f_f_parent_org_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_organisation
-- ----------------------------

-- ----------------------------
-- Table structure for t_protec_resour_author
-- ----------------------------
DROP TABLE IF EXISTS `t_protec_resour_author`;
CREATE TABLE `t_protec_resour_author`  (
  `f_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `f_authority` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_f_protected_resource_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`f_id`) USING BTREE,
  INDEX `FKcfdgjt0o0jdhkgjg3t32o5mly`(`f_f_protected_resource_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_protec_resour_author
-- ----------------------------

-- ----------------------------
-- Table structure for t_protected_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_protected_resource`;
CREATE TABLE `t_protected_resource`  (
  `f_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `f_compare_order` int(11) NULL DEFAULT NULL,
  `f_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_pattern_str` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`f_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_protected_resource
-- ----------------------------

-- ----------------------------
-- Table structure for t_select_option
-- ----------------------------
DROP TABLE IF EXISTS `t_select_option`;
CREATE TABLE `t_select_option`  (
  `f_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `f_display_order` int(11) NULL DEFAULT NULL,
  `f_option_label1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_option_label2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_option_label3` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_option_label4` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_option_label5` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_option_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_f_group_id` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`f_id`) USING BTREE,
  INDEX `FKjwh3ldat3521ow6p1994hbvst`(`f_f_group_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_select_option
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_config
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_config`;
CREATE TABLE `t_sys_config`  (
  `f_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `f_description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_item_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_item_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_item_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`f_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_sys_config
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dict_data`;
CREATE TABLE `t_sys_dict_data`  (
  `f_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `f_data_label` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_data_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_enable` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_is_default` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_type_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_client_machine` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_client_os` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_created` datetime(0) NULL DEFAULT NULL,
  `f_created_by` bigint(20) NULL DEFAULT NULL,
  `f_created_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_updated` datetime(0) NULL DEFAULT NULL,
  `f_updated_by` bigint(20) NULL DEFAULT NULL,
  `f_updated_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`f_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_sys_dict_data
-- ----------------------------

-- ----------------------------
-- Table structure for t_user_authority
-- ----------------------------
DROP TABLE IF EXISTS `t_user_authority`;
CREATE TABLE `t_user_authority`  (
  `f_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `f_authority` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`f_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_authority
-- ----------------------------

-- ----------------------------
-- Table structure for t_user_config
-- ----------------------------
DROP TABLE IF EXISTS `t_user_config`;
CREATE TABLE `t_user_config`  (
  `f_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `f_config_category` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_config_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_config_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `f_username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`f_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_config
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
