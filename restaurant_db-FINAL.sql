-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: restaurant_db
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `benutzer`
--

DROP TABLE IF EXISTS `benutzer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `benutzer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nutzername` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `passwort` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `vorname` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `nachname` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `geburtsdatum` date DEFAULT NULL,
  `salt` varchar(16) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `benutzer`
--

LOCK TABLES `benutzer` WRITE;
/*!40000 ALTER TABLE `benutzer` DISABLE KEYS */;
INSERT INTO `benutzer` VALUES (1,'hansi','pass','hans@mail.de','','',NULL,NULL),(2,'peter','12345','p@p.p','','',NULL,NULL),(3,'a','b','test@gmail.com',NULL,NULL,NULL,NULL),(4,'hans','a@a.de','123',NULL,NULL,NULL,NULL),(5,'jona','a@a.de','0621b8d12472652179800db4e04e0cfbb920e5a0351a6273e65654a1351c6efe',NULL,NULL,NULL,'16b17659cb74950d'),(6,'Schmidt','schmidt@gmail.com','passs',NULL,NULL,NULL,NULL),(7,'gh','gh','ac80081b215b78970710ea5203b4781bbd2b2ae5ac56b3646ac13302867a0485',NULL,NULL,NULL,'1d51b152981874a8'),(8,'null','null','9a150c82b9c75aad12868dc2a5bc004cccc1e9cd0b51da6fe979e6fdff22d603','Hans','Günther',NULL,'2488bb1cb6227817'),(9,'günni','günni@gmail.com','f0d2a96282910a801b71563c861754b1b9c16349bf45a4b8cfe3847ac70f2049','Günther','Heinz',NULL,'ea41299e41c12d3c'),(10,'günni','günni@gmail.com','4c66af4944124e08e1c15f44044bcc7743c0582a5528800b2876efa1cb92c8cd','Günther','Heinz',NULL,'b4352171f7169ec2'),(11,'chef','rest@auran.te','044e35dd342ffc1608052cb790cf057c3d37e1ecb3b7c35e80dc3c04e534987c','Heinz','Boss',NULL,'f2cb551b791fd837');
/*!40000 ALTER TABLE `benutzer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gast`
--

DROP TABLE IF EXISTS `gast`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gast` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nutzerId` int NOT NULL,
  `telefonnummer` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_nutzer_id` (`nutzerId`),
  CONSTRAINT `fk_nutzer_id` FOREIGN KEY (`nutzerId`) REFERENCES `benutzer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gast`
--

LOCK TABLES `gast` WRITE;
/*!40000 ALTER TABLE `gast` DISABLE KEYS */;
INSERT INTO `gast` VALUES (1,1,'0123456789');
/*!40000 ALTER TABLE `gast` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mitarbeiter`
--

DROP TABLE IF EXISTS `mitarbeiter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mitarbeiter` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nutzerId` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_nutzer_id2` (`nutzerId`),
  CONSTRAINT `fk_nutzer_id2` FOREIGN KEY (`nutzerId`) REFERENCES `benutzer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mitarbeiter`
--

LOCK TABLES `mitarbeiter` WRITE;
/*!40000 ALTER TABLE `mitarbeiter` DISABLE KEYS */;
INSERT INTO `mitarbeiter` VALUES (1,11);
/*!40000 ALTER TABLE `mitarbeiter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservierung`
--

DROP TABLE IF EXISTS `reservierung`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservierung` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tischId` int NOT NULL,
  `personenzahl` int NOT NULL,
  `gastId` int DEFAULT NULL,
  `zeitpunkt` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tisch_id` (`tischId`),
  KEY `fk_gast_id` (`gastId`),
  CONSTRAINT `fk_tisch_id` FOREIGN KEY (`tischId`) REFERENCES `tisch` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservierung`
--

LOCK TABLES `reservierung` WRITE;
/*!40000 ALTER TABLE `reservierung` DISABLE KEYS */;
INSERT INTO `reservierung` VALUES (5,0,11,1,'2025-10-01 23:43:26'),(6,0,11,1,'2026-01-01 00:00:00'),(7,0,15,1,'2026-01-01 05:00:00'),(13,0,4,9,'2025-10-29 17:00:00'),(14,1,1,11,'2025-10-28 18:30:00'),(15,0,5,11,'2026-01-01 20:30:00');
/*!40000 ALTER TABLE `reservierung` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tisch`
--

DROP TABLE IF EXISTS `tisch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tisch` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nummer` int NOT NULL,
  `kapazitaet` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tisch`
--

LOCK TABLES `tisch` WRITE;
/*!40000 ALTER TABLE `tisch` DISABLE KEYS */;
INSERT INTO `tisch` VALUES (0,12,5),(1,13,2);
/*!40000 ALTER TABLE `tisch` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-28  5:40:58
