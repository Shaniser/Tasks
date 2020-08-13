-- MySQL dump 10.13  Distrib 8.0.16, for macos10.14 (x86_64)
--
-- Host: localhost    Database: organizer
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `attachments`
--

DROP TABLE IF EXISTS `attachments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `attachments` (
  `attachment_id` mediumint(9) NOT NULL AUTO_INCREMENT COMMENT 'id вложения',
  `attachment_content` text NOT NULL COMMENT 'содержание вложения',
  `public` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'публично ли вложение',
  `hidden` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'видно ли вложение всем',
  `event_id` mediumint(9) NOT NULL COMMENT 'id события, к которому относится это вложение',
  PRIMARY KEY (`attachment_id`),
  KEY `event_id` (`event_id`),
  CONSTRAINT `attachments_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `events` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attachments`
--

LOCK TABLES `attachments` WRITE;
/*!40000 ALTER TABLE `attachments` DISABLE KEYS */;
/*!40000 ALTER TABLE `attachments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `events`
--

DROP TABLE IF EXISTS `events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `events` (
  `event_id` mediumint(9) NOT NULL AUTO_INCREMENT COMMENT 'id события',
  `event_header` varchar(100) NOT NULL COMMENT 'название события',
  `event_content` text NOT NULL COMMENT 'описание события',
  `event_date` date NOT NULL COMMENT 'дата события',
  `event_begin_time` time NOT NULL COMMENT 'время начала события',
  `event_end_time` time DEFAULT NULL COMMENT 'время конца события',
  `lesson` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'true = событие-пара, false = просто событие',
  `group_id` mediumint(9) NOT NULL COMMENT 'id группы, которое создало данное событие',
  PRIMARY KEY (`event_id`),
  KEY `group_id` (`group_id`),
  CONSTRAINT `events_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `groups` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `events`
--

LOCK TABLES `events` WRITE;
/*!40000 ALTER TABLE `events` DISABLE KEYS */;
/*!40000 ALTER TABLE `events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groups`
--

DROP TABLE IF EXISTS `groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `groups` (
  `group_id` mediumint(9) NOT NULL AUTO_INCREMENT COMMENT 'id группы',
  `group_name` varchar(20) NOT NULL COMMENT 'имя группы',
  `admin_password` varchar(36) NOT NULL COMMENT 'пароль для админа',
  `users_password` varchar(36) NOT NULL COMMENT 'пароль для остальных пользователей группы',
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groups`
--

LOCK TABLES `groups` WRITE;
/*!40000 ALTER TABLE `groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teachers`
--

DROP TABLE IF EXISTS `teachers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `teachers` (
  `teacher_id` mediumint(9) NOT NULL AUTO_INCREMENT COMMENT 'id преподавателя',
  `teacher_name` varchar(50) NOT NULL COMMENT 'ФИО преподавателя',
  `teacher_email` varchar(50) DEFAULT NULL COMMENT 'почта преподавателя',
  `teacher_phone` varchar(12) DEFAULT NULL COMMENT 'номер преподавателя',
  `teacher_department` varchar(50) DEFAULT NULL COMMENT 'факультет преподавателя',
  `group_id` mediumint(9) NOT NULL COMMENT 'id группы, которая добавила данного преподавателя',
  PRIMARY KEY (`teacher_id`),
  KEY `group_id` (`group_id`),
  CONSTRAINT `teachers_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `groups` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teachers`
--

LOCK TABLES `teachers` WRITE;
/*!40000 ALTER TABLE `teachers` DISABLE KEYS */;
/*!40000 ALTER TABLE `teachers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-08-13 16:52:17
