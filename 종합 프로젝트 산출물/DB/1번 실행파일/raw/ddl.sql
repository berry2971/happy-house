DROP DATABASE IF EXISTS `happyhouse`;
CREATE DATABASE `happyhouse`;
USE `happyhouse`;

DROP TABLE IF EXISTS `deal`;
DROP TABLE IF EXISTS `complex`;
DROP TABLE IF EXISTS `district`;
DROP TABLE IF EXISTS `blacklist`;
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `article`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `deal` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addr_lv1` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `addr_lv2` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `addr_lv3` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `addr_lv4` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bunji` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bunji_main` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bunji_sub` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `apt_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `square` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `deal_year_month` char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `deal_day` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `price` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `floor` int DEFAULT NULL,
  `built_year` char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `addr_road` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `rel_data` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `deal_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `dealer_loc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_DEAL_ADDR` (`bunji`,`addr_lv3`,`addr_lv2`,`addr_lv1`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `complex` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addr_lv1` varchar(100) DEFAULT NULL,
  `addr_lv2` varchar(100) DEFAULT NULL,
  `addr_lv3` varchar(100) DEFAULT NULL,
  `addr_lv4` varchar(100) DEFAULT NULL,
  `danji_code` varchar(100) DEFAULT NULL,
  `danji_name` varchar(500) DEFAULT NULL,
  `danji_type` varchar(100) DEFAULT NULL,
  `addr_legacy` varchar(1000) DEFAULT NULL,
  `addr_road` varchar(1000) DEFAULT NULL,
  `freesale_type` varchar(100) DEFAULT NULL,
  `accepted_date` varchar(100) DEFAULT NULL,
  `num_dong` varchar(100) DEFAULT NULL,
  `num_house` varchar(100) DEFAULT NULL,
  `management_type` varchar(100) DEFAULT NULL,
  `heat_type` varchar(100) DEFAULT NULL,
  `hall_type` varchar(100) DEFAULT NULL,
  `builder` varchar(500) DEFAULT NULL,
  `developer` varchar(500) DEFAULT NULL,
  `house_manager` varchar(500) DEFAULT NULL,
  `common_management_type` varchar(100) DEFAULT NULL,
  `num_common_management_worker` varchar(50) DEFAULT NULL,
  `security_management_type` varchar(100) DEFAULT NULL,
  `num_security_management_worker` varchar(50) DEFAULT NULL,
  `security_manager` varchar(500) DEFAULT NULL,
  `cleaning_management_type` varchar(100) DEFAULT NULL,
  `num_cleaning_management_worker` varchar(50) DEFAULT NULL,
  `food_waste_disposal_type` varchar(100) DEFAULT NULL,
  `fumigation_management_type` varchar(100) DEFAULT NULL,
  `num_fumigation_per_year` varchar(50) DEFAULT NULL,
  `fumigation_type` varchar(100) DEFAULT NULL,
  `building_structure` varchar(100) DEFAULT NULL,
  `elec_capacity` varchar(50) DEFAULT NULL,
  `elec_contract_type` varchar(100) DEFAULT NULL,
  `elec_manager_exists` varchar(100) DEFAULT NULL,
  `fire_receiver_type` varchar(100) DEFAULT NULL,
  `water_supply_type` varchar(100) DEFAULT NULL,
  `elevator_management_type` varchar(100) DEFAULT NULL,
  `num_elevator_guest` varchar(50) DEFAULT NULL,
  `num_elevator_load` varchar(50) DEFAULT NULL,
  `num_elevator_guest_load` varchar(50) DEFAULT NULL,
  `num_elevator_disabled` varchar(50) DEFAULT NULL,
  `num_elevator_emergency` varchar(50) DEFAULT NULL,
  `num_elevator_etc` varchar(50) DEFAULT NULL,
  `num_parking_total` varchar(50) DEFAULT NULL,
  `num_parking_floor` varchar(50) DEFAULT NULL,
  `num_parking_basement` varchar(50) DEFAULT NULL,
  `num_cctv` varchar(50) DEFAULT NULL,
  `num_tech` varchar(50) DEFAULT NULL,
  `center_addr` varchar(1000) DEFAULT NULL,
  `center_tel` varchar(100) DEFAULT NULL,
  `center_fax` varchar(100) DEFAULT NULL,
  `amenities` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `complex_addr_idx` (`addr_lv4`,`addr_lv2`,`addr_lv1`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `district` (
  `id` bigint NOT NULL,
  `addr_lv1` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `addr_lv2` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `addr_lv3` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `addr_lv4` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_district_adrr_lv1` (`addr_lv1`),
  KEY `idx_district_adrr_lv2` (`addr_lv2`),
  KEY `idx_district_adrr_lv3` (`addr_lv3`),
  KEY `idx_district_adrr_lv4` (`addr_lv4`),
  KEY `idx_district_complex1` (`addr_lv4`,`addr_lv3`,`addr_lv2`,`addr_lv1`),
  KEY `idx_district_complex2` (`addr_lv3`,`addr_lv2`,`addr_lv1`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `blacklist` (
  `id` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `article` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `board_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `subject` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `write_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  PRIMARY KEY (`id`),
  KEY `IDX_ARTICLE_BOARD_NAME` (`board_name`),
  KEY `IDX_ARTICLE_SUBJECT` (`subject`),
  KEY `IDX_ARTICLE_AUTHOR` (`author`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `article_id` bigint DEFAULT NULL,
  `write_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `content` text,
  PRIMARY KEY (`id`),
  KEY `IDX_COMMENT_ARTICLE` (`article_id`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user` (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `pw` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tel` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `bmk_addr_lv1` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `bmk_addr_lv2` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `bmk_addr_lv3` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `bmk_addr_lv4` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_user_name` (`name`),
  KEY `idx_user_tel` (`tel`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;