CREATE TABLE `stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8_bin NOT NULL,
  `code` varchar(20) COLLATE utf8_bin NOT NULL,
  `price` int(11) NOT NULL,
  `ctime` bigint(20) NOT NULL,
  `unit` tinyint(4) NOT NULL COMMENT '1:人民币，2:美元，3:欧元，4:英镑，5:澳元',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;