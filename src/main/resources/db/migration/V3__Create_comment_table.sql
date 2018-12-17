CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `question_id` int(11) NOT NULL,
  `reply_user_id` int(11) DEFAULT NULL,
  `reply_id` int(11) DEFAULT NULL,
  `content` VARCHAR(512) DEFAULT NULL,
  `like_count` int(11) DEFAULT 0,
  `gmt_create` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  `form_id` VARCHAR(255) DEFAULT NULL,
  `status` tinyint(4) DEFAULT 1,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;