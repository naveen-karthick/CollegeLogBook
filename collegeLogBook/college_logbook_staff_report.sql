-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: college_logbook
-- ------------------------------------------------------
-- Server version	5.7.20-log

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
-- Table structure for table `staff_report`
--

DROP TABLE IF EXISTS `staff_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `staff_report` (
  `date_on` date NOT NULL,
  `hour_on` int(5) NOT NULL,
  `staff_id` int(5) DEFAULT NULL,
  `work_done` varchar(60) DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `year_1` int(5) NOT NULL,
  `dept_id` int(5) NOT NULL,
  PRIMARY KEY (`date_on`,`hour_on`,`year_1`,`dept_id`),
  KEY `dept_id` (`dept_id`),
  KEY `staff_id` (`staff_id`),
  CONSTRAINT `staff_report_ibfk_1` FOREIGN KEY (`staff_id`) REFERENCES `login` (`staff_id`),
  CONSTRAINT `staff_report_ibfk_2` FOREIGN KEY (`dept_id`) REFERENCES `departments` (`dept_id`),
  CONSTRAINT `staff_report_ibfk_3` FOREIGN KEY (`staff_id`) REFERENCES `login` (`staff_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff_report`
--

LOCK TABLES `staff_report` WRITE;
/*!40000 ALTER TABLE `staff_report` DISABLE KEYS */;
INSERT INTO `staff_report` VALUES ('2016-02-09',3,1,'Ee','2018-01-27 08:59:04',2,1),('2017-01-17',1,1,' something','2018-01-25 15:56:56',1,1),('2017-06-14',1,1,'hey an','2018-01-26 10:33:12',1,1),('2017-07-12',1,1,'Hey bro wassup','2018-01-26 10:09:31',1,1),('2017-10-18',1,1,' hey man','2018-01-25 11:04:38',1,1),('2017-11-01',1,3,'something about something','2018-01-24 15:46:52',1,1),('2017-11-02',1,3,'something about something','2018-01-24 15:47:09',1,1),('2017-11-02',2,3,'oaishdo as das asd asdas das dasd asdasd asda sdas das','2018-01-24 15:50:13',1,1),('2017-12-01',1,1,' qpwoeiqffopdjasdb','2018-01-24 16:29:45',1,1),('2017-12-05',1,3,'something','2018-01-24 13:21:05',1,1),('2017-12-05',2,3,'something','2018-01-24 13:21:10',1,1),('2017-12-05',3,3,'asdas','2018-01-24 13:22:37',1,1),('2017-12-06',3,3,'asdas','2018-01-24 13:25:21',2,1),('2017-12-08',3,3,'ada dai','2018-01-24 15:36:27',1,1),('2017-12-09',1,1,'ada dai','2018-01-24 15:39:42',3,1),('2017-12-15',1,3,'something','2018-01-24 15:29:16',1,1),('2017-12-22',1,1,'asd','2018-01-24 15:42:14',1,1),('2017-12-22',3,1,'joker man','2018-01-24 15:41:22',3,1),('2017-12-24',1,1,'asd qwerty','2018-01-24 15:42:33',1,1),('2018-01-01',1,1,'integerated  circuits','2018-01-27 13:22:37',1,1),('2018-01-02',1,1,'suma something','2018-01-23 15:25:27',1,1),('2018-01-05',1,1,'Semi-Conductors','2018-01-23 18:23:39',1,1),('2018-01-05',3,1,'computer networks','2018-01-26 10:13:13',3,1),('2018-01-07',1,3,'circuit theory','2018-01-07 15:31:46',1,1),('2018-01-07',2,3,'electronic devices-semi conductors','2018-01-07 12:04:54',1,1),('2018-01-07',3,3,'Linear integerated circuits-op amp','2018-01-07 11:50:09',3,1),('2018-01-07',4,3,'electronic circuits- advanced biasing','2018-01-07 21:53:03',2,1),('2018-01-07',6,4,'Software design','2018-01-22 17:48:39',2,2),('2018-01-07',6,1,'professional ethics - ethics of engineering','2018-01-30 10:49:49',4,1),('2018-01-08',2,2,'Professional ethics-quality management','2018-01-07 10:33:08',4,1),('2018-01-08',4,3,'Quality Management - TQM','2018-01-08 09:28:55',3,3),('2018-01-08',6,3,'ethics','2018-01-08 23:00:09',4,1),('2018-01-08',7,3,'engineering ethics','2018-01-08 21:05:23',4,1),('2018-01-09',1,3,'something about something','2018-01-09 15:35:34',1,3),('2018-01-09',2,3,'something man','2018-01-09 15:37:38',1,6),('2018-01-09',4,3,'something','2018-01-09 15:27:25',1,1),('2018-01-11',3,1,'Hello something','2018-01-28 19:05:10',3,1),('2018-01-13',1,1,'hey man','2018-01-29 12:02:40',1,1),('2018-01-13',4,1,'Biasing','2018-01-28 11:29:41',1,1),('2018-01-15',2,3,'lashdas','2018-01-24 12:52:17',1,1),('2018-01-15',2,3,'lashdas','2018-01-24 12:53:05',2,1),('2018-01-17',4,3,'communication networks','2018-01-23 18:12:11',2,1),('2018-01-17',4,3,'communication','2018-01-23 18:15:01',4,1),('2018-01-18',7,1,'Joker and Batman','2018-01-22 17:35:47',4,1),('2018-01-19',0,1,'lasdgasld','2018-01-22 17:33:45',0,1),('2018-01-19',1,1,'something','2018-01-29 11:59:50',1,1),('2018-01-21',5,1,'electronics','2018-01-21 22:11:59',1,1),('2018-01-21',5,1,'advanced digital circuits','2018-01-23 09:34:36',3,5),('2018-01-22',4,1,'Linear Integerated circuits','2018-01-22 17:00:49',1,1),('2018-01-24',1,3,'some','2018-01-24 13:03:56',1,1),('2018-01-24',1,3,'lkasds','2018-01-24 13:10:22',4,1),('2018-01-24',2,3,'lkasds','2018-01-24 13:10:28',4,1),('2018-01-25',5,1,'Kolangal','2018-01-22 17:43:23',1,1),('2018-01-26',1,1,'something aboutsomething','2018-01-29 12:04:34',1,1),('2018-01-26',5,1,'Kolangal','2018-01-22 17:43:31',1,1),('2018-01-27',6,1,'something','2018-01-23 09:42:18',1,1),('2018-01-28',1,4,'INT','2018-01-29 23:18:38',3,2),('2018-01-28',2,3,'something','2018-01-29 13:51:52',1,1),('2018-01-28',2,4,'INT','2018-01-29 23:18:51',2,3),('2018-01-28',4,4,'INT','2018-01-29 23:19:10',4,2),('2018-01-29',1,1,'something','2018-01-29 12:17:15',1,1),('2018-01-29',3,3,'joking','2018-01-29 14:24:12',1,3),('2018-01-31',6,1,'hello','2018-01-23 09:54:30',1,1),('2018-02-15',1,1,'asdas','2018-01-23 10:29:53',1,1),('2018-02-15',6,1,'oiagsdas','2018-01-23 10:24:14',1,1),('2018-02-18',1,1,'about something','2018-01-24 12:35:42',1,1),('2018-02-25',1,1,'asjdasd','2018-01-24 12:40:01',1,1);
/*!40000 ALTER TABLE `staff_report` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-30 11:16:34
