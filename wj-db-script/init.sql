-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: workout-journal-db
-- ------------------------------------------------------
-- Server version	8.1.0

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
-- Table structure for table `equipment_jointable`
--

DROP TABLE IF EXISTS `equipment_jointable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `equipment_jointable` (
  `exercise_id` bigint NOT NULL,
  `category_id` bigint NOT NULL,
  KEY `excercise_id_idx` (`exercise_id`),
  KEY `category_id_FK_idx` (`category_id`),
  CONSTRAINT `FK_category_id-equipment` FOREIGN KEY (`category_id`) REFERENCES `exercise_equipment_categories` (`id`),
  CONSTRAINT `FK_exercise_id-equipment` FOREIGN KEY (`exercise_id`) REFERENCES `exercises` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipment_jointable`
--

LOCK TABLES `equipment_jointable` WRITE;
/*!40000 ALTER TABLE `equipment_jointable` DISABLE KEYS */;
INSERT INTO `equipment_jointable` VALUES (13,12),(13,9),(14,12),(14,9),(15,10),(16,12),(16,9),(17,12),(18,12),(19,10),(20,12),(20,9),(21,12),(21,9),(22,12),(23,18),(24,18),(25,10),(26,10);
/*!40000 ALTER TABLE `equipment_jointable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercise_equipment_categories`
--

DROP TABLE IF EXISTS `exercise_equipment_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exercise_equipment_categories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise_equipment_categories`
--

LOCK TABLES `exercise_equipment_categories` WRITE;
/*!40000 ALTER TABLE `exercise_equipment_categories` DISABLE KEYS */;
INSERT INTO `exercise_equipment_categories` VALUES (7,'Bands'),(8,'Barbell'),(9,'Bench'),(10,'Body Weight'),(11,'Cables'),(12,'Dumbbells'),(13,'EZ Bar'),(14,'Kettlebell'),(18,'Machine'),(15,'Machines'),(16,'Smith Machine'),(17,'Trap Bar');
/*!40000 ALTER TABLE `exercise_equipment_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercise_musclegroup_categories`
--

DROP TABLE IF EXISTS `exercise_musclegroup_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exercise_musclegroup_categories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise_musclegroup_categories`
--

LOCK TABLES `exercise_musclegroup_categories` WRITE;
/*!40000 ALTER TABLE `exercise_musclegroup_categories` DISABLE KEYS */;
INSERT INTO `exercise_musclegroup_categories` VALUES (7,'Abs'),(8,'Biceps'),(9,'Calves'),(10,'Chest'),(11,'Forearms'),(12,'Glutes'),(13,'Hamstrings'),(14,'Lats'),(15,'Lower Back'),(16,'Obliques'),(17,'Quads'),(18,'Shoulders'),(19,'Traps'),(20,'Triceps');
/*!40000 ALTER TABLE `exercise_musclegroup_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercises`
--

DROP TABLE IF EXISTS `exercises`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exercises` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercises`
--

LOCK TABLES `exercises` WRITE;
/*!40000 ALTER TABLE `exercises` DISABLE KEYS */;
INSERT INTO `exercises` VALUES (18,'Biceps Curl'),(25,'Bicycle Crunch'),(13,'Dumbbell Bench Press'),(14,'Dumbbell Incline Bench Press'),(16,'Dumbbell Incline Fly'),(17,'Dumbbell Row'),(20,'Dumbbell Shoulder Press'),(21,'Dumbbell Skull Crusher'),(22,'Lat Raise'),(24,'Leg Curl Seated'),(23,'Leg Extension'),(26,'Plank'),(19,'Pull Up'),(15,'Push Up');
/*!40000 ALTER TABLE `exercises` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `musclegroups`
--

DROP TABLE IF EXISTS `musclegroups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `musclegroups` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `exercise_id` bigint NOT NULL,
  `category_id` bigint NOT NULL,
  `is_primary` tinyint DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `excercise_id_idx` (`exercise_id`),
  KEY `category_id_idx` (`category_id`),
  CONSTRAINT `category_id` FOREIGN KEY (`category_id`) REFERENCES `exercise_musclegroup_categories` (`id`),
  CONSTRAINT `excercise_id` FOREIGN KEY (`exercise_id`) REFERENCES `exercises` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `musclegroups`
--

LOCK TABLES `musclegroups` WRITE;
/*!40000 ALTER TABLE `musclegroups` DISABLE KEYS */;
INSERT INTO `musclegroups` VALUES (23,13,10,1),(24,13,20,0),(25,14,10,1),(26,14,20,0),(27,15,10,1),(28,15,20,0),(29,16,10,1),(30,17,14,1),(31,17,8,0),(32,18,8,1),(33,19,14,1),(34,19,8,0),(35,20,18,1),(36,21,20,1),(37,22,14,1),(38,22,18,1),(39,23,17,1),(40,23,13,1),(41,23,12,0),(42,23,9,0),(43,24,13,1),(44,25,7,1),(45,25,16,1),(46,26,7,1),(47,26,16,0),(48,26,18,0);
/*!40000 ALTER TABLE `musclegroups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (3,'Misiac2000@gmail.com'),(2,'testuser@email.com');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workout_exercises`
--

DROP TABLE IF EXISTS `workout_exercises`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workout_exercises` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `workout_id` bigint NOT NULL,
  `exercise_id` bigint NOT NULL,
  `load` float NOT NULL,
  `reps` int NOT NULL,
  `set_number` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK__idx` (`exercise_id`),
  KEY `FK_workout_id_idx` (`id`),
  KEY `FK_workout_id_idx1` (`workout_id`),
  CONSTRAINT `FK_exercise_id` FOREIGN KEY (`exercise_id`) REFERENCES `exercises` (`id`),
  CONSTRAINT `FK_workout_id` FOREIGN KEY (`workout_id`) REFERENCES `workouts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workout_exercises`
--

LOCK TABLES `workout_exercises` WRITE;
/*!40000 ALTER TABLE `workout_exercises` DISABLE KEYS */;
INSERT INTO `workout_exercises` VALUES (50,21,13,20,12,1),(51,21,13,30,10,2),(52,21,13,40,10,3),(53,21,13,50,9,4),(54,21,13,50,7,5),(55,21,20,25,11,1),(56,21,20,25,9,2),(57,21,20,25,7,3),(58,21,20,20,5,4),(59,21,14,20,12,1),(60,21,14,20,8,0),(61,21,14,20,7,3),(62,21,22,10,15,1),(63,21,22,10,12,2);
/*!40000 ALTER TABLE `workout_exercises` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workouts`
--

DROP TABLE IF EXISTS `workouts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workouts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_idx` (`user_id`),
  CONSTRAINT `user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workouts`
--

LOCK TABLES `workouts` WRITE;
/*!40000 ALTER TABLE `workouts` DISABLE KEYS */;
INSERT INTO `workouts` VALUES (21,2,'2023-12-20 15:42:00');
/*!40000 ALTER TABLE `workouts` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-05  0:37:44
