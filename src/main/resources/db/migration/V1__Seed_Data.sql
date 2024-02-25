-- MariaDB dump 10.19  Distrib 10.6.9-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: db_cards_v1
-- ------------------------------------------------------
-- Server version	10.6.9-MariaDB-1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `auth_session`
--

DROP TABLE IF EXISTS `auth_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auth_session` (
  `sn` bigint(20) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `activity` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `browser` varchar(255) DEFAULT NULL,
  `is_active` char(1) DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  `logout_time` datetime DEFAULT NULL,
  `os` varchar(255) DEFAULT NULL,
  `refresh_token` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sn`),
  UNIQUE KEY `UK_l4yxbe42hsno2l2i21mc5ead8` (`refresh_token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_session`
--

LOCK TABLES `auth_session` WRITE;
/*!40000 ALTER TABLE `auth_session` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `card`
--

DROP TABLE IF EXISTS `card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `card` (
  `id` bigint(20) NOT NULL,
  `deleted_by` varchar(255) DEFAULT NULL,
  `deleted_flag` char(1) DEFAULT NULL,
  `deleted_time` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_flag` char(1) DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `posted_by` varchar(255) DEFAULT NULL,
  `posted_flag` char(1) DEFAULT NULL,
  `posted_time` datetime DEFAULT NULL,
  `verified_by` varchar(255) DEFAULT NULL,
  `verified_flag` char(1) DEFAULT NULL,
  `verified_time` datetime DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq5apcc4ddrab8t48q2uqvyquq` (`user_id`),
  CONSTRAINT `FKq5apcc4ddrab8t48q2uqvyquq` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `card`
--

LOCK TABLES `card` WRITE;
/*!40000 ALTER TABLE `card` DISABLE KEYS */;
INSERT INTO `card` VALUES (6,NULL,'N',NULL,NULL,'N',NULL,'Member1','Y','2024-02-26 00:04:57',NULL,'N',NULL,'#0000FF','Visa card new','Visa Card','To Do',4),(7,NULL,'N',NULL,NULL,'N',NULL,'Member1','Y','2024-02-26 00:05:18',NULL,'N',NULL,'#0000FF','Master card new','Master Card','To Do',4),(8,NULL,'N',NULL,NULL,'N',NULL,'Member2','Y','2024-02-26 00:05:54',NULL,'N',NULL,'#0000FF','Visa Bank card new','Visa Bank Card','To Do',5),(9,NULL,'N',NULL,NULL,'N',NULL,'Member2','Y','2024-02-26 00:05:55',NULL,'N',NULL,'#0000FF','Visa Bank card new','Visa Bank Card','To Do',5),(10,NULL,'N',NULL,NULL,'N',NULL,'Member2','Y','2024-02-26 00:05:56',NULL,'N',NULL,'#0000FF','Visa Bank card new','Visa Bank Card','To Do',5),(11,NULL,'N',NULL,NULL,'N',NULL,'Member2','Y','2024-02-26 00:05:56',NULL,'N',NULL,'#0000FF','Visa Bank card new','Visa Bank Card','To Do',5),(12,NULL,'N',NULL,NULL,'N',NULL,'Member2','Y','2024-02-26 00:05:57',NULL,'N',NULL,'#0000FF','Visa Bank card new','Visa Bank Card','To Do',5),(13,NULL,'N',NULL,NULL,'N',NULL,'Member2','Y','2024-02-26 00:05:57',NULL,'N',NULL,'#0000FF','Visa Bank card new','Visa Bank Card','To Do',5),(14,NULL,'N',NULL,NULL,'N',NULL,'Member2','Y','2024-02-26 00:05:57',NULL,'N',NULL,'#0000FF','Visa Bank card new','Visa Bank Card','To Do',5),(15,NULL,'N',NULL,NULL,'N',NULL,'Member2','Y','2024-02-26 00:05:57',NULL,'N',NULL,'#0000FF','Visa Bank card new','Visa Bank Card','To Do',5);
/*!40000 ALTER TABLE `card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (16);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refresh_token`
--

DROP TABLE IF EXISTS `refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `refresh_token` (
  `id` bigint(20) NOT NULL,
  `expiry_date` datetime NOT NULL,
  `token` varchar(255) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r4k4edos30bx9neoq81mdvwph` (`token`),
  KEY `FKjtx87i0jvq2svedphegvdwcuy` (`user_id`),
  CONSTRAINT `FKjtx87i0jvq2svedphegvdwcuy` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refresh_token`
--

LOCK TABLES `refresh_token` WRITE;
/*!40000 ALTER TABLE `refresh_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `refresh_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL,
  `deleted_by` varchar(255) DEFAULT NULL,
  `deleted_flag` char(1) DEFAULT NULL,
  `deleted_time` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_flag` char(1) DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `posted_by` varchar(255) DEFAULT NULL,
  `posted_flag` char(1) DEFAULT NULL,
  `posted_time` datetime DEFAULT NULL,
  `verified_by` varchar(255) DEFAULT NULL,
  `verified_flag` char(1) DEFAULT NULL,
  `verified_time` datetime DEFAULT NULL,
  `name` varchar(250) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ofx66keruapi6vyqpv6f2or37` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,NULL,'N',NULL,NULL,'N',NULL,NULL,'Y','2024-02-25 17:35:58',NULL,'N',NULL,'ROLE_MEMBER'),(2,NULL,'N',NULL,NULL,'N',NULL,NULL,'Y','2024-02-25 17:35:58',NULL,'N',NULL,'ROLE_ADMIN');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`),
  CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (3,2),(4,1),(5,1);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `deleted_by` varchar(255) DEFAULT NULL,
  `deleted_flag` char(1) DEFAULT NULL,
  `deleted_time` datetime DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_flag` char(1) DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `posted_by` varchar(255) DEFAULT NULL,
  `posted_flag` char(1) DEFAULT NULL,
  `posted_time` datetime DEFAULT NULL,
  `verified_by` varchar(255) DEFAULT NULL,
  `verified_flag` char(1) DEFAULT NULL,
  `verified_time` datetime DEFAULT NULL,
  `email` varchar(150) NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (3,NULL,'N',NULL,NULL,'N',NULL,NULL,'Y','2024-02-25 17:35:58',NULL,'N',NULL,'admin@admin.com','Admin','User','$2a$10$gv4Gg9I/TmD3SenLOu3XC.lINW9Fx1JAOXklVkGZdaLulpU0JFn4q','PENDING','Admin'),(4,NULL,'N',NULL,'','N',NULL,NULL,'Y','2024-02-25 17:35:58',NULL,'N',NULL,'member1@member.com','Member1','Member1','$2a$10$fzEk48mb11caykteOC5YV./jFIw5xhR1URu/JCESKXGoSe31lDWWS','PENDING','Member1'),(5,NULL,'N',NULL,'','N',NULL,NULL,'Y','2024-02-25 17:35:59',NULL,'N',NULL,'member2@member.com','Member2','Member2','$2a$10$miCYIZkHoi3nxOIsVL0xlOnNruJPO/HRCoINbH8uC2efzXCepmWsu','PENDING','Member2');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-26  0:06:36
