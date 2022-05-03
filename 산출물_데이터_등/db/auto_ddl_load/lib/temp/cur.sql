SET GLOBAL LOCAL_INFILE = TRUE;

LOAD DATA LOCAL INFILE "C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/deal_202203.csv "
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