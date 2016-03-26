CREATE DATABASE  IF NOT EXISTS `db_joke` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `db_joke`;
-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: db_joke
-- ------------------------------------------------------
-- Server version	5.6.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_comment`
--

DROP TABLE IF EXISTS `tb_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_comment` (
  `com_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `joke_id` int(10) unsigned NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `user_name` varchar(45) DEFAULT NULL,
  `com_content` varchar(500) NOT NULL,
  `com_lou` int(11) NOT NULL,
  `com_time` datetime NOT NULL,
  PRIMARY KEY (`com_id`),
  UNIQUE KEY `idnew_table_UNIQUE` (`com_id`),
  KEY `com_joke_id_idx` (`joke_id`),
  KEY `com_user_id_idx` (`user_id`),
  CONSTRAINT `com_joke_id` FOREIGN KEY (`joke_id`) REFERENCES `tb_joke` (`joke_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `com_user_id` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_joke`
--

DROP TABLE IF EXISTS `tb_joke`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_joke` (
  `joke_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned DEFAULT '0',
  `type` int(11) NOT NULL DEFAULT '0',
  `timecreate` datetime NOT NULL,
  `content` varchar(800) NOT NULL,
  `imgurl` varchar(45) DEFAULT NULL,
  `ishasimg` int(11) DEFAULT '0',
  `share_count` int(11) DEFAULT '0',
  `collect_count` int(11) DEFAULT '0',
  `look_count` int(11) DEFAULT '0',
  PRIMARY KEY (`joke_id`),
  UNIQUE KEY `joke_id_UNIQUE` (`joke_id`)
) ENGINE=InnoDB AUTO_INCREMENT=244 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_jubao`
--

DROP TABLE IF EXISTS `tb_jubao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_jubao` (
  `jubao_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `joke_id` int(10) unsigned NOT NULL,
  `user_id` int(11) DEFAULT '0',
  `jubao_time` datetime DEFAULT NULL,
  PRIMARY KEY (`jubao_id`),
  KEY `jubao_joke_id_idx` (`joke_id`),
  CONSTRAINT `jubao_joke_id` FOREIGN KEY (`joke_id`) REFERENCES `tb_joke` (`joke_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_ques`
--

DROP TABLE IF EXISTS `tb_ques`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_ques` (
  `ques_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ques_content` varchar(500) COLLATE utf8_bin NOT NULL,
  `ques_email` varchar(45) COLLATE utf8_bin NOT NULL,
  `ques_time` datetime NOT NULL,
  PRIMARY KEY (`ques_id`),
  UNIQUE KEY `ques_id_UNIQUE` (`ques_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_user`
--

DROP TABLE IF EXISTS `tb_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_user` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) NOT NULL,
  `user_pwd` varchar(45) NOT NULL,
  `user_qqtoken` varchar(45) DEFAULT NULL,
  `user_xlweibo` varchar(45) DEFAULT NULL,
  `user_weixin` varchar(45) DEFAULT NULL,
  `headurl` varchar(145) DEFAULT NULL,
  `user_sex` varchar(15) DEFAULT NULL,
  `user_motto` varchar(145) DEFAULT NULL,
  PRIMARY KEY (`user_name`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`),
  UNIQUE KEY `user_qqtoken_UNIQUE` (`user_qqtoken`),
  UNIQUE KEY `user_xlweibo_UNIQUE` (`user_xlweibo`),
  UNIQUE KEY `user_weixin_UNIQUE` (`user_weixin`),
  UNIQUE KEY `headurl_UNIQUE` (`headurl`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_usercollect`
--

DROP TABLE IF EXISTS `tb_usercollect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_usercollect` (
  `usercollect_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `joke_id` int(10) unsigned NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`usercollect_id`),
  UNIQUE KEY `usercollect_id_UNIQUE` (`usercollect_id`),
  KEY `collect_joke_id_idx` (`joke_id`),
  KEY `collect_user_id_idx` (`user_id`),
  CONSTRAINT `collect_joke_id` FOREIGN KEY (`joke_id`) REFERENCES `tb_joke` (`joke_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `collect_user_id` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_usercomment`
--

DROP TABLE IF EXISTS `tb_usercomment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_usercomment` (
  `usercomment_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `joke_id` int(10) unsigned NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `com_time` datetime NOT NULL,
  PRIMARY KEY (`usercomment_id`),
  UNIQUE KEY `usercomment_id_UNIQUE` (`usercomment_id`),
  KEY `usrcom_joke_id_idx` (`joke_id`),
  KEY `usercom_user_id_idx` (`user_id`),
  CONSTRAINT `usercom_user_id` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `usrcom_joke_id` FOREIGN KEY (`joke_id`) REFERENCES `tb_joke` (`joke_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tb_userlook`
--

DROP TABLE IF EXISTS `tb_userlook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_userlook` (
  `userlook_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `joke_id` int(10) unsigned NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`userlook_id`),
  UNIQUE KEY `userlook_id_UNIQUE` (`userlook_id`),
  KEY `joke_id_idx` (`joke_id`),
  KEY `user_id_idx` (`user_id`),
  CONSTRAINT `joke_id` FOREIGN KEY (`joke_id`) REFERENCES `tb_joke` (`joke_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-03-26 14:07:33
