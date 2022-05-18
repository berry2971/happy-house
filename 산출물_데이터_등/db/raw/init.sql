use happyhouse;

DROP TABLE IF EXISTS `district`;
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
  KEY `idx_district_adrr_lv4` (`addr_lv4`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `pw` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tel` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `bookmarkedAddressState` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `bookmarkedAddressCity` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `bookmarkedAddressTown` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `bookmarkedAddressVillage` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `bookmarkedAddressHamlet` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_user_name` (`name`),
  KEY `idx_user_tel` (`tel`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `deal`;
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
  KEY `IDX_DEAL_ADDR` (`apt_name`,`addr_lv3`,`bunji`,`addr_lv4`,`addr_lv2`,`addr_lv1`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `board`;
CREATE TABLE `board` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `board_name` varchar(100) DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL,
  `author` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `write_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `content` longtext,
  PRIMARY KEY (`id`),
  KEY `idx_board_title` (`title`),
  KEY `idx_board_board_name` (`board_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SHOW VARIABLES LIKE '%infile%';
SET GLOBAL LOCAL_INFILE = TRUE;
SHOW VARIABLES LIKE '%infile%';

LOAD DATA LOCAL INFILE "C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/deal_202202.csv"
INTO TABLE `deal` FIELDS TERMINATED BY ","
IGNORE 1 ROWS
(@addr_lv1, @addr_lv2, @addr_lv3, @addr_lv4, @bunji, @bunji_main, @bunji_sub, @apt_name, @square, @deal_year_month, @deal_day, @price, @floor, @built_year, @addr_road, @rel_data, @deal_type, @dealer_loc)
SET
`addr_lv1` = @addr_lv1,
`addr_lv2` = @addr_lv2,
`addr_lv3` = @addr_lv3,
`addr_lv4` = @addr_lv4,
`bunji` = @bunji,
`bunji_main` = @bunji_main,
`bunji_sub` = @bunji_sub,
`apt_name` = @apt_name,
`square` = @square,
`deal_year_month` = @deal_year_month,
`deal_day` = @deal_day,
`price` = @price,
`floor` = @floor,
`built_year` = @built_year,
`addr_road` = @addr_road,
`rel_data` = @rel_data,
`deal_type` = @deal_type;

LOAD DATA LOCAL INFILE "C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/districts.csv "
INTO TABLE `district` FIELDS TERMINATED BY ","
IGNORE 1 ROWS
(@id, @addr_lv1, @addr_lv2, @addr_lv3, @addr_lv4)
SET
`id` = @id,
`addr_lv1` = @addr_lv1,
`addr_lv2` = @addr_lv2,
`addr_lv3` = @addr_lv3,
`addr_lv4` = @addr_lv4;