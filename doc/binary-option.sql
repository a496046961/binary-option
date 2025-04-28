/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80100
 Source Host           : localhost:3306
 Source Schema         : binary-option

 Target Server Type    : MySQL
 Target Server Version : 80100
 File Encoding         : 65001

 Date: 28/04/2025 08:27:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_symbol
-- ----------------------------
DROP TABLE IF EXISTS `t_symbol`;
CREATE TABLE `t_symbol` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `full_name` varchar(255) DEFAULT NULL COMMENT '全名称',
  `symbol` varchar(255) DEFAULT NULL COMMENT '交易对',
  `base_coin` varchar(50) DEFAULT NULL COMMENT '基础币名称',
  `quote_coin` varchar(30) DEFAULT NULL COMMENT '计价货币',
  `scale` int DEFAULT NULL COMMENT '精度',
  `follow_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '跟随名称',
  `follow_input` text COMMENT '跟随的数据',
  `data_source` varchar(30) DEFAULT NULL COMMENT '数据源',
  `type` int DEFAULT NULL COMMENT '类型，1:数字货币，2:期货，3:指数',
  `return_rate_conf` text COMMENT '回报率配置，json',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='交易对';

-- ----------------------------
-- Records of t_symbol
-- ----------------------------
BEGIN;
INSERT INTO `t_symbol` (`id`, `full_name`, `symbol`, `base_coin`, `quote_coin`, `scale`, `follow_name`, `follow_input`, `data_source`, `type`, `return_rate_conf`) VALUES (1, 'bitcoin', 'BTCUSDT', 'BTC', 'USDT', 5, 'btcusdt', NULL, 'binance', 1, '{\"60\":0.02, \"120\":0.05, \"300\":0.1}');
COMMIT;

-- ----------------------------
-- Table structure for t_wallet
-- ----------------------------
DROP TABLE IF EXISTS `t_wallet`;
CREATE TABLE `t_wallet` (
  `id` bigint NOT NULL COMMENT '主键',
  `coin_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '币种名称',
  `balance` decimal(26,6) NOT NULL DEFAULT '0.000000' COMMENT '可用余额',
  `freeze_balance` decimal(26,6) NOT NULL DEFAULT '0.000000' COMMENT '冻结余额',
  `lock_balance` decimal(26,6) NOT NULL DEFAULT '0.000000' COMMENT '锁仓余额',
  `version` int NOT NULL COMMENT '版本',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='钱包';

-- ----------------------------
-- Records of t_wallet
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_wallet_log
-- ----------------------------
DROP TABLE IF EXISTS `t_wallet_log`;
CREATE TABLE `t_wallet_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `wallet_id` bigint NOT NULL COMMENT '关联的钱包ID(对应t_wallet.id)',
  `coin_name` varchar(30) NOT NULL COMMENT '币种名称',
  `change_type` varchar(20) NOT NULL COMMENT '变动类型(充值/提现/转账/交易/系统调整等)',
  `change_amount` decimal(26,6) NOT NULL COMMENT '变动金额(正数表示增加，负数表示减少)',
  `before_balance` decimal(26,6) NOT NULL COMMENT '变动前可用余额',
  `after_balance` decimal(26,6) NOT NULL COMMENT '变动后可用余额',
  `before_freeze` decimal(26,6) NOT NULL COMMENT '变动前冻结余额',
  `after_freeze` decimal(26,6) NOT NULL COMMENT '变动后冻结余额',
  `before_lock` decimal(26,6) NOT NULL COMMENT '变动前锁仓余额',
  `after_lock` decimal(26,6) NOT NULL COMMENT '变动后锁仓余额',
  `business_id` varchar(64) DEFAULT NULL COMMENT '业务ID(订单号/交易号等)',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注说明',
  `operator` varchar(64) DEFAULT NULL COMMENT '操作人(用户ID或系统)',
  `ip_address` varchar(50) DEFAULT NULL COMMENT '操作IP地址',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_wallet_id` (`wallet_id`),
  KEY `idx_coin_name` (`coin_name`),
  KEY `idx_business_id` (`business_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='钱包资产变动日志';

-- ----------------------------
-- Records of t_wallet_log
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
