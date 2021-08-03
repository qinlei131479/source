/*
 Navicat Premium Data Transfer

 Source Server         : localhost-company(iflash)
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : course_auth

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 03/08/2021 22:42:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `tb_oauth_code`;
CREATE TABLE `tb_oauth_code` (
  `code` varchar(256) DEFAULT NULL COMMENT '授权码(未加密)',
  `authentication` blob COMMENT 'AuthorizationRequestHolder.java对象序列化后的二进制数据'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='授权码表';

-- ----------------------------
-- Records of tb_oauth_code
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tb_oauthclient
-- ----------------------------
DROP TABLE IF EXISTS `tb_oauthclient`;
CREATE TABLE `tb_oauthclient` (
  `client_id` varchar(32) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='终端信息表';

-- ----------------------------
-- Records of tb_oauthclient
-- ----------------------------
BEGIN;
INSERT INTO `tb_oauthclient` VALUES ('app', NULL, 'app', 'server', 'password,refresh_token', NULL, NULL, NULL, NULL, NULL, 'true');
INSERT INTO `tb_oauthclient` VALUES ('center', NULL, 'center', 'server', 'password,refresh_token,authorization_code,client_credentials', '', NULL, 86400, NULL, '{\"remoteAccountServiceClassName\":\"CenterAdminService\",\"tokenMaxNum\":2}', 'true');
INSERT INTO `tb_oauthclient` VALUES ('daemon', NULL, 'daemon', 'server', 'password,refresh_token', NULL, NULL, NULL, NULL, NULL, 'true');
INSERT INTO `tb_oauthclient` VALUES ('gen', NULL, 'gen', 'server', 'password,refresh_token', NULL, NULL, NULL, NULL, NULL, 'true');
INSERT INTO `tb_oauthclient` VALUES ('styn', NULL, 'styn', 'server', 'password,refresh_token,authorization_code,client_credentials', 'http://localhost:4040/sso1/login,http://localhost:4041/sso1/login', NULL, NULL, NULL, '{\"remoteAccountServiceClassName\":\"BizAdminService\"}', 'true');
INSERT INTO `tb_oauthclient` VALUES ('test', NULL, 'test', 'server', 'password,refresh_token,authorization_code,client_credentials,implicit', NULL, NULL, 7200, 86400, '{\"remoteAccountServiceClassName\":\"MisAdminService\"}', 'true');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
