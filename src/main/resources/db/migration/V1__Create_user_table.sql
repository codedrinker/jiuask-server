CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openid` char(55) NOT NULL,
  `token` char(36) NOT NULL,
  `nick_name` varchar(100) DEFAULT NULL,
  `avatar_url` varchar(255) DEFAULT NULL,
  `gender` tinyint(4) DEFAULT NULL,
  `gmt_create` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  `status` tinyint(4) DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_token` (`token`),
  UNIQUE KEY `uk_user_openid` (`openid`)
) ENGINE=InnoDB;