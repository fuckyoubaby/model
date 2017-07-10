SET FOREIGN_KEY_CHECKS=0;

-- ************************************************************************************************************

-- 用户部分
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user` (
  `uuid` varchar (22) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_xingming` varchar(48) DEFAULT NULL,
  `user_contact` varchar(255) DEFAULT '',
  `user_username` varchar(48) DEFAULT '',
  `user_password` varchar(48) DEFAULT '',
  `user_enabled` tinyint(1) DEFAULT '0' COMMENT '1 for YES or 0 for NO',
  `user_code` varchar(4) default '',
  `user_code_date` datetime default NULL,
  PRIMARY KEY (`uuid`),
  KEY `system_user_index_name` (`user_xingming`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `system_user` VALUES ('2016052513021024601', '2017-01-01 12:12:12', '系统管理员', 'admin@changhong.com', 'chadadmin', 'chad123456', 1, '', '2016-01-01 12:12:12');

-- 系统日志
DROP TABLE IF EXISTS `system_action_log`;
CREATE TABLE `system_action_log` (
  `uuid` varchar (22) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_uuid` varchar(22) DEFAULT NULL,
  `user_xingming` varchar(48) DEFAULT '',
  `action_type` varchar(20) DEFAULT '',
  `action_module` varchar(20) DEFAULT '',
  `action_desc` text,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ************************************************************************************************************

-- 系统元数据部分
DROP TABLE IF EXISTS `system_meta_data`;
CREATE TABLE `system_meta_data` (
  `uuid` varchar (22) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_level` varchar(20) DEFAULT NULL,
  `start_time` varchar(10) DEFAULT NULL,
  `end_time` varchar(10) DEFAULT NULL,
  `heart_internal` varchar (10) DEFAULT NULL,
  `note` varchar (255) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `system_meta_data` VALUES ('2016052513021024701', '2016-01-01 12:12:12', 'SYSTEM', '06:00', '23:00', '10', '默认系统配置');

-- ************************************************************************************************************

-- BOX相关信息
DROP TABLE IF EXISTS `box_area`;
CREATE TABLE `box_area` (
  `uuid` varchar(22) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `name` varchar(120) DEFAULT '',
  `parent_uuid` varchar(22) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `box_area_index_name` (`name`),
  KEY `box_area_index_parent_uuid` (`parent_uuid`),
  CONSTRAINT FOREIGN KEY (`parent_uuid`) REFERENCES `box_area` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `box_area`(uuid, name, parent_uuid) VALUES ('201611211423024777045', '全国', NULL);

DROP TABLE IF EXISTS `box_community`;
CREATE TABLE `box_community` (
  `uuid` varchar(22) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `name` varchar(255) DEFAULT '',
  `abbreviation` varchar(255) DEFAULT '',
  `comment` text,
  `area_uuid` varchar(22) DEFAULT NULL,
  `meta_data_uuid` varchar(22) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `box_community_index_name` (`name`),
  KEY `box_community_index_area_uuid` (`area_uuid`),
  CONSTRAINT FOREIGN KEY (`area_uuid`) REFERENCES `box_area` (`uuid`),
  CONSTRAINT FOREIGN KEY (`meta_data_uuid`) REFERENCES `system_meta_data` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `box_info`;
CREATE TABLE `box_info` (
  `uuid` varchar(22) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `mac_number` varchar(17) DEFAULT NULL,
  `ssid_name` varchar(36) DEFAULT NULL,
  `ssid_password` varchar(36) DEFAULT NULL,
  `mac_note` varchar(255) DEFAULT NULL,
  `mac_version` varchar(10) DEFAULT NULL,
  `last_report_time` datetime DEFAULT NULL,
  `cpu_rate` int(2) DEFAULT 0,
  `mem_rate` int(2) DEFAULT 0,
  `disk_rate` int(2) DEFAULT 0,
  `last_code_time` datetime DEFAULT NULL,
  `code_num` varchar(4) DEFAULT NULL,
  `community_uuid` varchar(22) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `box_info_mac_number` (`mac_number`),
  CONSTRAINT FOREIGN KEY (`community_uuid`) REFERENCES `box_community` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ************************************************
-- 设备MAC
DROP TABLE IF EXISTS `box_mac`;
CREATE TABLE `box_mac` (
  `uuid` varchar(22) COLLATE utf8_bin NOT NULL,
  `mac` varchar(17) COLLATE utf8_bin NOT NULL DEFAULT '',
  `status` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT 'B_INITE',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- *******************************************************************
-- 设备和MAC相关联的触发器
-- 新增设备后，修改MAC状态为使用
DROP TRIGGER IF EXISTS `AF_box_info_add`;
DELIMITER ;;
CREATE TRIGGER `AF_box_info_add` AFTER INSERT ON `box_info` FOR EACH ROW begin
  if(!ISNULL(new.mac_number)) then
    update box_mac set status = 'B_USED' where mac = new.mac_number;
  end if;
end
;;
DELIMITER ;

-- 更新设备信息时，旧MAC状态为初始状态，新MAC为使用状态
DROP TRIGGER IF EXISTS `AF_box_info_update`;
DELIMITER ;;
CREATE TRIGGER `AF_box_info_update` AFTER UPDATE ON `box_info` FOR EACH ROW begin
 if(new.mac_number != old.mac_number) then
    update box_mac set status='B_INITE' where mac = old.mac_number;
    update box_mac set status='B_USED' where mac = new.mac_number;
end if;
end
;;
DELIMITER ;

-- 删除设备时，恢复MAC状态为初始状态
DROP TRIGGER IF EXISTS `AF_box_info_delete`;
DELIMITER ;;
CREATE TRIGGER `AF_box_info_delete` AFTER DELETE ON `box_info` FOR EACH ROW begin
  if(!ISNULL(old.mac_number)) then
     update box_mac set status='B_INITE' where mac=old.mac_number;
end if;
end
;;
DELIMITER ;

-- ************************************************************************************************************
-- 设备控制
DROP TABLE IF EXISTS `box_command`;
CREATE TABLE `box_command` (
  `uuid` varchar(22) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `mac_number` varchar(17) DEFAULT NULL,
  `command_type` varchar(20) DEFAULT 'C_RESTART',
  `comment` varchar(255) DEFAULT '',
  `finished` tinyint(1) DEFAULT '0' COMMENT '1 for YES or 0 for NO',
  `command_plan` varchar(19) default '',
  PRIMARY KEY (`uuid`),
  KEY `box_command_mac_number` (`mac_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ************************************************************************************************************
-- 终端管理
DROP TABLE IF EXISTS `app_manage`;
CREATE TABLE `app_manage` (
  `uuid` varchar(22) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `app_name` varchar(20)  default NULL,
  `app_type` varchar(20)  default NULL,
  `app_version` varchar(20) default NULL,
  `app_versioninfo` varchar(255) default NULL,
  `app_fileurl` varchar(200) default NULL,
  `app_enabled` tinyint(1) default NULL,
  PRIMARY KEY  (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


-- ************************************************************************************************************
-- 手机应用管理
DROP TABLE IF EXISTS `app_phone_manage`;
CREATE TABLE `app_phone_manage` (
  `uuid` varchar(22) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `app_name` varchar(20)  default NULL,
  `app_type` varchar(20)  default NULL,
  `app_version` varchar(20) default NULL,
  `app_versioninfo` varchar(255) default NULL,
  `app_fileurl` varchar(200) default NULL,
  `app_enabled` tinyint(1) default NULL,
  PRIMARY KEY  (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 终端升级策略
DROP TABLE IF EXISTS `app_strategy`;
CREATE TABLE `app_strategy` (
 `uuid` varchar(22)  NOT NULL,
 `name` varchar(20)  default NULL,
 `timestamp_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
 `timestamp_distrbute` datetime default NULL,
 `app_version` varchar(20) default NULL,
 `app_strategy_enabled` tinyint(1) default NULL,
 `app_strategy_distribute_enabled` tinyint(1) default NULL,
 PRIMARY KEY  (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 策略设备配置
DROP TABLE IF EXISTS `app_strategy_box`;
CREATE TABLE `app_strategy_box` (
  `uuid` varchar(22) NOT NULL,
  `strategy_uuid` varchar(22) default NULL,
  `mac_number` varchar(17) default NULL,
  PRIMARY KEY  (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 策略社区配置
DROP TABLE IF EXISTS `app_strategy_community`;
CREATE TABLE `app_strategy_community` (
  `uuid` varchar(22) NOT NULL,
  `strategy_uuid` varchar(22) default NULL,
  `community_uuid` varchar(22) default NULL,
  PRIMARY KEY  (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 策略区域配置
DROP TABLE IF EXISTS `app_strategy_area`;
CREATE TABLE `app_strategy_area` (
  `uuid` varchar(22) NOT NULL,
  `strategy_uuid` varchar(22) default NULL,
  `area_uuid` varchar(22) character set utf8 collate utf8_bin default NULL,
  PRIMARY KEY  (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ************************************************************************************************************
-- 广告相关表
-- ----------------------------
-- Table structure for advertisment
-- ----------------------------
DROP TABLE IF EXISTS `advertisment`;
-- 废弃无用表

-- ***********************

-- ----------------------------
-- Table structure for ad_resource
-- ----------------------------
DROP TABLE IF EXISTS `ad_resource`;
CREATE TABLE `ad_resource` (
  `uuid` varchar(22) NOT NULL,
  `name` varchar(150) NOT NULL,
  `type` varchar(100) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '0',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `advertiser` varchar(255) DEFAULT NULL COMMENT '广告商',
  `agents` varchar(255) DEFAULT NULL COMMENT '代理商',
   `size` double(40,2) DEFAULT '0.00' COMMENT '资源容量单位KB',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- *********************************
-- 播放升级相关表
-- ****************************

-- ----------------------------
-- Table structure for content_upgrade
-- ----------------------------
DROP TABLE IF EXISTS `content_upgrade`;
CREATE TABLE `content_upgrade` (
  `uuid` varchar(22) COLLATE utf8_bin NOT NULL,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `description` varchar(600) COLLATE utf8_bin DEFAULT NULL,
  `enable` tinyint(4) DEFAULT '1',
  `publishtime` timestamp NULL DEFAULT NULL,
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for content_upgrade_area
-- ----------------------------
DROP TABLE IF EXISTS `content_upgrade_area`;
CREATE TABLE `content_upgrade_area` (
  `uuid` varchar(22) COLLATE utf8_bin NOT NULL,
  `area_id` varchar(22) COLLATE utf8_bin NOT NULL,
  `upgrade_id` varchar(22) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for content_upgrade_box
-- ----------------------------
DROP TABLE IF EXISTS `content_upgrade_box`;
CREATE TABLE `content_upgrade_box` (
  `uuid` varchar(22) COLLATE utf8_bin NOT NULL,
  `box_id` varchar(22) COLLATE utf8_bin NOT NULL,
  `upgrade_id` varchar(22) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for content_upgrade_community
-- ----------------------------
DROP TABLE IF EXISTS `content_upgrade_community`;
CREATE TABLE `content_upgrade_community` (
  `uuid` varchar(22) COLLATE utf8_bin NOT NULL,
  `community_id` varchar(22) COLLATE utf8_bin NOT NULL,
  `upgrade_id` varchar(22) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for play_content
-- ----------------------------
DROP TABLE IF EXISTS `play_content`;
CREATE TABLE `play_content` (
  `uuid` varchar(22) COLLATE utf8_bin NOT NULL,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `version` varchar(20) COLLATE utf8_bin NOT NULL,
  `default_duration` double DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT NULL,
  `amount` int(11) DEFAULT '0',
  `upgrade_id` varchar(22) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `pu_Foreign_1` (`upgrade_id`),
  CONSTRAINT `pu_Foreign_1` FOREIGN KEY (`upgrade_id`) REFERENCES `content_upgrade` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 播放列表内容表
-- ----------------------------
-- Table structure for play_item
-- ----------------------------
DROP TABLE IF EXISTS `play_item`;
CREATE TABLE `play_item` (
  `uuid` varchar(22) COLLATE utf8_bin NOT NULL,
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  `description` varchar(600) COLLATE utf8_bin DEFAULT NULL,
  `item_index` tinyint(4) DEFAULT NULL,
  `start_date` timestamp NULL DEFAULT NULL,
  `end_date` timestamp NULL DEFAULT NULL,
  `item_repeat` int(11) DEFAULT NULL,
  `amount` int(11) NOT NULL DEFAULT '0',
  `content_uuid` varchar(22) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FK9012893EA76E02E5` (`content_uuid`),
  CONSTRAINT `FK9012893EA76E02E5` FOREIGN KEY (`content_uuid`) REFERENCES `play_content` (`uuid`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for play_item_content
-- ----------------------------
DROP TABLE IF EXISTS `play_item_content`;
CREATE TABLE `play_item_content` (
  `uuid` varchar(22) COLLATE utf8_bin NOT NULL,
  `resource_uuid` varchar(22) COLLATE utf8_bin NOT NULL,
  `item_index` tinyint(4) DEFAULT NULL,
  `item_repeat` int(11) DEFAULT NULL,
  `item_duration` float DEFAULT NULL,
  `play_item_uuid` varchar(22) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FKAB534678CA773A64` (`play_item_uuid`),
  CONSTRAINT `FKAB534678CA773A64` FOREIGN KEY (`play_item_uuid`) REFERENCES `play_item` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPRESSED;

-- 播放列表相关触发器

DROP TRIGGER IF EXISTS `AF_insert_update_content_amount`;
DELIMITER ;;
CREATE TRIGGER `AF_insert_update_content_amount` AFTER UPDATE ON `play_item` FOR EACH ROW begin
 if(ISNULL(old.content_uuid)&& !ISNULL(new.content_uuid)) then
    update play_content set amount=amount+1 where uuid = new.content_uuid;
elseif(ISNULL(new.content_uuid) && !ISNULL(old.content_uuid)) then
    update play_content set amount=if(amount>1, amount-1,0) where uuid = old.content_uuid;
end if;
end
;;
DELIMITER ;

DROP TRIGGER IF EXISTS `AF_add_resource_disable`;
DELIMITER ;;
CREATE TRIGGER `AF_add_resource_disable` AFTER INSERT ON `play_item_content` FOR EACH ROW begin
if(!ISNULL(new.resource_uuid) && !ISNULL(new.play_item_uuid)) then
 update ad_resource set status = 0 where uuid = new.resource_uuid;
 update play_item set amount=amount+1 where uuid = new.play_item_uuid;
end if;
end
;;
DELIMITER ;

DROP TRIGGER IF EXISTS `AF_delete_resource_enable`;
DELIMITER ;;
CREATE TRIGGER `AF_delete_resource_enable` AFTER DELETE ON `play_item_content` FOR EACH ROW begin
if(!ISNULL(old.resource_uuid)) then
  update ad_resource set status = 1 where uuid = old.resource_uuid;
end if;
if(!ISNULL(old.play_item_uuid)) then
  update play_item set amount= if(amount>1,amount-1,0) where uuid = old.play_item_uuid;
end if;
end
;;
DELIMITER ;


-- ************************************************************************************************************

-- ----------------------------
-- Table structure for ad_data_upload
-- ----------------------------
DROP TABLE IF EXISTS `ad_data_upload`;
CREATE TABLE `ad_data_upload` (
  `uuid` varchar(22) NOT NULL,
  `timestamp` timestamp NULL default NULL,
  `upload_date` date default NULL,
  `times` int(11) default NULL,
  `duration` decimal(15,2) default NULL,
  `total_time` decimal(15,2) default NULL,
  `mac_number` varchar(17) default NULL,
  `adr_uuid` varchar(22) default NULL,
  PRIMARY KEY  (`uuid`),
  KEY `FK4F16561AB1237AD8` (`adr_uuid`),
  CONSTRAINT `FK4F16561AB1237AD8` FOREIGN KEY (`adr_uuid`) REFERENCES `ad_resource` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- **************************************
-- 17.3.23 加入设备异常存储表
DROP TABLE IF EXISTS `box_report`;
CREATE TABLE `box_report` (
  `uuid` varchar(22) COLLATE utf8_bin NOT NULL,
  `mac` varchar(17) COLLATE utf8_bin NOT NULL DEFAULT '',
  `status` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT 'B_R_REPORT',
  `timestamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `code` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


SET FOREIGN_KEY_CHECKS=1;