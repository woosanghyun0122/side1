-- MySQL dump 10.13  Distrib 9.1.0, for macos15.2 (arm64)
--
-- Host: localhost    Database: side
-- ------------------------------------------------------
-- Server version	9.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cart`
--

CREATE DATABASE IF NOT EXISTS side;
USE side;

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `userid` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `amount` int NOT NULL,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cart_product_FK` (`product_id`),
  KEY `cart_users_FK` (`userid`),
  CONSTRAINT `cart_product_FK` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  CONSTRAINT `cart_users_FK` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `category_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `category_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `depth` int NOT NULL,
  `parent_id` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES ('CAT001','전자제품',1,NULL,'2025-01-13 12:19:02','2025-01-13 12:19:02'),('CAT00101','핸드폰',2,'CAT001','2025-01-13 12:19:09','2025-01-13 12:19:09'),('CAT00102','노트북',2,'CAT001','2025-01-13 12:19:09','2025-01-13 12:19:09'),('CAT002','패션',1,NULL,'2025-01-13 12:19:02','2025-01-13 12:19:02'),('CAT00201','남성',2,'CAT002','2025-01-13 12:19:09','2025-01-13 12:19:09'),('CAT00202','여성',2,'CAT002','2025-01-13 12:19:09','2025-01-13 12:19:09'),('CAT003','홈&키친',1,NULL,'2025-01-13 12:19:02','2025-01-13 12:19:02'),('CAT00301','가구',2,'CAT003','2025-01-13 12:19:09','2025-01-13 12:19:09'),('CAT00302','가전',2,'CAT003','2025-01-13 12:19:09','2025-01-13 12:19:09'),('CAT004','스포츠 & 아웃도어',1,NULL,'2025-01-13 12:19:02','2025-01-13 12:19:02'),('CAT00401','스포츠용품',2,'CAT004','2025-01-13 12:19:09','2025-01-13 12:19:09'),('CAT00402','아웃도어 제품',2,'CAT004','2025-01-13 12:19:09','2025-01-13 12:19:09'),('CAT005','도서',1,NULL,'2025-01-13 12:19:02','2025-01-13 12:19:02'),('CAT00501','소설',2,'CAT005','2025-01-13 12:19:09','2025-01-13 12:19:09'),('CAT00502','기타',2,'CAT005','2025-01-13 12:19:09','2025-01-13 12:19:09');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_num` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '주문번호',
  `product_id` int NOT NULL COMMENT '상품 키',
  `amount` int DEFAULT '0' COMMENT '수량',
  `order_status` varchar(20) DEFAULT NULL COMMENT '주문상태',
  `cancel_reason` blob COMMENT '환불 사유',
  `reject_reason` blob COMMENT '반려 사유',
  `created_at` timestamp NOT NULL COMMENT '생성일시',
  `updated_at` timestamp NOT NULL COMMENT '변경일시',
  PRIMARY KEY (`id`),
  KEY `order_item_order_fk` (`order_num`),
  KEY `order_item_product_FK` (`product_id`),
  CONSTRAINT `order_item_orders_FK` FOREIGN KEY (`order_num`) REFERENCES `orders` (`order_num`),
  CONSTRAINT `order_item_product_FK` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_num` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '주문번호',
  `userid` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '주문 시 로그인 아이디',
  `order_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '주문 명',
  `payment_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '결제키',
  `customer_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '주문자 이름',
  `customer_email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '주문자 이메일',
  `customer_phone` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '주문자 핸드폰 번호',
  `zip_code` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '우편번호',
  `address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '주소',
  `address_detail` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '상세주소',
  `created_at` timestamp NOT NULL COMMENT '생성일시',
  `updated_at` timestamp NOT NULL COMMENT '변경일시',
  PRIMARY KEY (`order_num`),
  KEY `order_users_FK` (`userid`),
  KEY `orders_payment_FK` (`payment_key`),
  CONSTRAINT `orders_users_FK` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(60) DEFAULT NULL,
  `userid` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '판매자 ID',
  `price` int DEFAULT NULL,
  `content` text,
  `category_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `quantity` int DEFAULT NULL,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  `sale_count` int DEFAULT '0',
  `view_count` int DEFAULT '0',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=215 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (115,'iPhone 13 Pro','seller1',1200000,'Apple iPhone 13 Pro with 128GB storage','CAT00101',50,'2025-01-13 12:19:33','2025-01-13 12:19:33',0,0),(116,'Samsung Galaxy S21','seller1',1100000,'Samsung Galaxy S21 with 256GB storage','CAT00101',30,'2025-01-13 12:19:33','2025-01-13 12:19:33',0,0),(117,'Google Pixel 6','seller1',1000000,'Google Pixel 6 with Android 12','CAT00101',40,'2025-01-13 12:19:33','2025-01-13 12:19:33',0,0),(118,'OnePlus 9','seller2',900000,'OnePlus 9 with 8GB RAM and 128GB storage','CAT00101',25,'2025-01-13 12:19:33','2025-01-13 12:19:33',0,0),(119,'Xiaomi Mi 11','seller2',700000,'Xiaomi Mi 11 with 108MP camera','CAT00101',20,'2025-01-13 12:19:33','2025-01-13 12:19:33',0,0),(120,'Sony Xperia 5 III','seller2',800000,'Sony Xperia 5 III with OLED display','CAT00101',15,'2025-01-13 12:19:33','2025-01-13 12:19:33',0,0),(121,'Oppo Find X3','seller3',1000000,'Oppo Find X3 with Snapdragon processor','CAT00101',10,'2025-01-13 12:19:33','2025-01-13 12:19:33',0,0),(122,'Huawei P50 Pro','seller3',700000,'Huawei P50 Pro with Leica camera','CAT00101',8,'2025-01-13 12:19:33','2025-01-13 12:19:33',0,0),(123,'Asus ROG Phone 5','seller3',1200000,'Gaming phone with 144Hz display','CAT00101',12,'2025-01-13 12:19:33','2025-01-13 12:19:33',0,0),(124,'Nokia G50','seller3',500000,'Affordable smartphone with 5G support','CAT00101',60,'2025-01-13 12:19:33','2025-01-13 12:19:33',0,0),(125,'MacBook Pro 16\"','seller3',2000000,'Apple MacBook Pro with M1 Max chip','CAT00102',10,'2025-01-13 12:19:34','2025-01-13 12:19:34',0,0),(126,'Dell XPS 13','seller3',13000000,'Dell XPS 13 with 11th Gen Intel processor','CAT00102',20,'2025-01-13 12:19:34','2025-01-13 12:19:34',0,0),(127,'HP Spectre x360','seller3',1000000,'HP Spectre x360 convertible laptop','CAT00102',15,'2025-01-13 12:19:34','2025-01-13 12:19:34',0,0),(128,'Lenovo ThinkPad X1 Carbon','seller1',1100000,'Lenovo business laptop with Intel vPro','CAT00102',18,'2025-01-13 12:19:34','2025-01-13 12:19:34',0,0),(129,'Asus ZenBook 14','seller1',1200000,'Ultra-thin laptop with OLED display','CAT00102',12,'2025-01-13 12:19:34','2025-01-13 12:19:34',0,0),(130,'Microsoft Surface Laptop 4','seller1',1000000,'Microsoft Surface with AMD Ryzen','CAT00102',17,'2025-01-13 12:19:34','2025-01-13 12:19:34',0,0),(131,'Acer Swift 3','seller2',8000000,'Affordable laptop with long battery life','CAT00102',22,'2025-01-13 12:19:34','2025-01-13 12:19:34',0,0),(132,'Razer Blade 15','seller2',1600000,'Gaming laptop with GeForce RTX 3070','CAT00102',8,'2025-01-13 12:19:34','2025-01-13 12:19:34',0,0),(133,'LG Gram 17','seller2',1900000,'Ultra-lightweight 17-inch laptop','CAT00102',11,'2025-01-13 12:19:34','2025-01-13 12:19:34',0,0),(134,'Samsung Galaxy Book Pro','seller2',1800000,'Samsung laptop with AMOLED screen','CAT00102',14,'2025-01-13 12:19:34','2025-01-13 12:19:34',0,0),(135,'Levi\'s Men\'s Jeans','seller2',139000,'Classic fit denim jeans','CAT00201',100,'2025-01-13 12:19:36','2025-01-13 12:19:36',0,0),(136,'Nike Men\'s Running Shoes','seller2',120000,'Lightweight and comfortable','CAT00201',50,'2025-01-13 12:19:36','2025-01-13 12:19:36',0,0),(137,'Adidas Men\'s Hoodie','seller2',70000,'Comfortable and stylish hoodie','CAT00201',60,'2025-01-13 12:19:36','2025-01-13 12:19:36',0,0),(138,'Polo Ralph Lauren T-Shirt','seller3',179000,'Soft cotton T-shirt','CAT00201',80,'2025-01-13 12:19:36','2025-01-13 12:19:36',0,0),(139,'Under Armour Shorts','seller3',35000,'Breathable and durable','CAT00201',40,'2025-01-13 12:19:36','2025-01-13 12:19:36',0,0),(140,'Calvin Klein Jacket','seller3',129000,'Stylish bomber jacket','CAT00201',20,'2025-01-13 12:19:36','2025-01-13 12:19:36',0,0),(141,'Vans Slip-On Sneakers','seller1',59000,'Classic slip-on sneakers','CAT00201',90,'2025-01-13 12:19:36','2025-01-13 12:19:36',0,0),(142,'Reebok Sweatpants','seller1',55000,'Comfortable for casual wear','CAT00201',70,'2025-01-13 12:19:36','2025-01-13 12:19:36',0,0),(143,'Hanes Men\'s Boxer Briefs','seller1',25000,'Pack of 5 boxer briefs','CAT00201',110,'2025-01-13 12:19:36','2025-01-13 12:19:36',0,0),(144,'The North Face Men\'s Parka','seller1',219000,'Insulated parka for cold weather','CAT00201',15,'2025-01-13 12:19:36','2025-01-13 12:19:36',0,0),(145,'Zara Women\'s Dress','seller2',80000,'Elegant floral dress','CAT00202',50,'2025-01-13 12:19:38','2025-01-13 12:19:38',0,0),(146,'H&M Women\'s Blazer','seller2',39000,'Professional blazer for women','CAT00202',40,'2025-01-13 12:19:38','2025-01-13 12:19:38',0,0),(147,'Forever 21 Skirt','seller3',30000,'A-line mini skirt','CAT00202',70,'2025-01-13 12:19:38','2025-01-13 12:19:38',0,0),(148,'Uniqlo Women\'s Sweater','seller3',39000,'Soft and warm sweater','CAT00202',90,'2025-01-13 12:19:38','2025-01-13 12:19:38',0,0),(149,'Coach Handbag','seller3',250000,'Leather handbag with spacious interior','CAT00202',20,'2025-01-13 12:19:38','2025-01-13 12:19:38',0,0),(150,'Michael Kors Watch','seller3',180000,'Elegant wrist watch','CAT00202',30,'2025-01-13 12:19:38','2025-01-13 12:19:38',0,0),(151,'Aldo Women\'s Heels','seller1',89000,'Stylish high heels','CAT00202',25,'2025-01-13 12:19:38','2025-01-13 12:19:38',0,0),(152,'Gucci Scarf','seller1',350000,'Silk scarf with designer print','CAT00202',10,'2025-01-13 12:19:38','2025-01-13 12:19:38',0,0),(153,'Sephora Lipstick','seller3',25000,'Long-lasting lipstick','CAT00202',100,'2025-01-13 12:19:38','2025-01-13 12:19:38',0,0),(154,'Ray-Ban Sunglasses','seller2',150000,'UV-protection sunglasses','CAT00202',35,'2025-01-13 12:19:38','2025-01-13 12:19:38',0,0),(155,'KitchenAid Mixer','seller1',39900,'Stand mixer with various attachments','CAT00301',10,'2025-01-13 12:19:40','2025-01-13 12:19:40',0,0),(156,'Instant Pot','seller1',89000,'Electric pressure cooker','CAT00301',40,'2025-01-13 12:19:40','2025-01-13 12:19:40',0,0),(157,'Ninja Blender','seller1',120000,'High-speed blender for smoothies','CAT00301',30,'2025-01-13 12:19:40','2025-01-13 12:19:40',0,0),(158,'Cuisinart Toaster','seller2',60000,'4-slice toaster with browning control','CAT00301',25,'2025-01-13 12:19:40','2025-01-13 12:19:40',0,0),(159,'Breville Espresso Machine','seller2',599000,'Automatic espresso machine','CAT00301',12,'2025-01-13 12:19:40','2025-01-13 12:19:40',0,0),(160,'Hamilton Beach Slow Cooker','seller2',50000,'6-quart slow cooker','CAT00301',35,'2025-01-13 12:19:40','2025-01-13 12:19:40',0,0),(161,'Dyson Air Purifier','seller3',399000,'Removes allergens from air','CAT00301',15,'2025-01-13 12:19:40','2025-01-13 12:19:40',0,0),(162,'Philips Air Fryer','seller3',120000,'Compact air fryer','CAT00301',20,'2025-01-13 12:19:40','2025-01-13 12:19:40',0,0),(163,'Whirlpool Refrigerator','seller3',1500000,'Energy-efficient refrigerator','CAT00301',8,'2025-01-13 12:19:40','2025-01-13 12:19:40',0,0),(164,'Smeg Toaster','seller3',150000,'Retro design 2-slice toaster','CAT00301',18,'2025-01-13 12:19:40','2025-01-13 12:19:40',0,0),(165,'LG Washing Machine','seller1',1200000,'LG 15kg washing machine with smart inverter technology','CAT00302',10,'2025-01-13 12:19:41','2025-01-13 12:19:41',0,0),(166,'Samsung Microwave Oven','seller2',150000,'Compact microwave oven with smart features','CAT00302',15,'2025-01-13 12:19:41','2025-01-13 12:19:41',0,0),(167,'Dyson Vacuum Cleaner','seller3',550000,'Powerful cordless vacuum cleaner','CAT00302',20,'2025-01-13 12:19:41','2025-01-13 12:19:41',0,0),(168,'Philips Air Fryer','seller1',180000,'Compact air fryer with rapid air technology','CAT00302',12,'2025-01-13 12:19:41','2025-01-13 12:19:41',0,0),(169,'Cuckoo Rice Cooker','seller2',250000,'10-cup rice cooker with multi-function settings','CAT00302',25,'2025-01-13 12:19:41','2025-01-13 12:19:41',0,0),(170,'Hurom Slow Juicer','seller3',300000,'Slow juicer preserving more nutrients','CAT00302',18,'2025-01-13 12:19:41','2025-01-13 12:19:41',0,0),(171,'Whirlpool Refrigerator','seller1',1800000,'Energy-efficient double door refrigerator','CAT00302',8,'2025-01-13 12:19:41','2025-01-13 12:19:41',0,0),(172,'Cuchen Electric Stove','seller2',120000,'Compact electric stove with touch controls','CAT00302',10,'2025-01-13 12:19:41','2025-01-13 12:19:41',0,0),(173,'Breville Espresso Machine','seller3',950000,'Automatic espresso machine with frother','CAT00302',6,'2025-01-13 12:19:41','2025-01-13 12:19:41',0,0),(174,'Panasonic Blender','seller1',90000,'High-speed blender with stainless steel blades','CAT00302',20,'2025-01-13 12:19:41','2025-01-13 12:19:41',0,0),(175,'Treadmill','seller1',1500000,'High-speed treadmill with incline feature','CAT00401',5,'2025-01-13 12:19:43','2025-01-13 12:19:43',0,0),(176,'Dumbbell Set','seller2',50000,'Adjustable dumbbell set 5kg to 30kg','CAT00401',30,'2025-01-13 12:19:43','2025-01-13 12:19:43',0,0),(177,'Exercise Bike','seller3',250000,'Indoor exercise bike with digital display','CAT00401',10,'2025-01-13 12:19:43','2025-01-13 12:19:43',0,0),(178,'Yoga Mat','seller1',30000,'Non-slip yoga mat with extra thickness','CAT00401',50,'2025-01-13 12:19:43','2025-01-13 12:19:43',0,0),(179,'Resistance Bands','seller2',20000,'Set of 5 resistance bands for various exercises','CAT00401',40,'2025-01-13 12:19:43','2025-01-13 12:19:43',0,0),(180,'Foam Roller','seller3',25000,'High-density foam roller for muscle recovery','CAT00401',20,'2025-01-13 12:19:43','2025-01-13 12:19:43',0,0),(181,'Pull-Up Bar','seller1',40000,'Doorway pull-up bar with adjustable width','CAT00401',25,'2025-01-13 12:19:43','2025-01-13 12:19:43',0,0),(182,'Kettlebell','seller2',60000,'Cast iron kettlebell, 10kg','CAT00401',15,'2025-01-13 12:19:43','2025-01-13 12:19:43',0,0),(183,'Rowing Machine','seller3',350000,'Compact rowing machine with resistance settings','CAT00401',8,'2025-01-13 12:19:43','2025-01-13 12:19:43',0,0),(184,'Ab Roller','seller1',15000,'Ab roller for core strengthening','CAT00401',60,'2025-01-13 12:19:43','2025-01-13 12:19:43',0,0),(185,'Coleman Tent','seller1',250000,'4-person tent with waterproof coating','CAT00402',10,'2025-01-13 12:19:45','2025-01-13 12:19:45',0,0),(186,'Sleeping Bag','seller2',70000,'Lightweight sleeping bag for 3 seasons','CAT00402',30,'2025-01-13 12:19:45','2025-01-13 12:19:45',0,0),(187,'Hiking Backpack','seller3',90000,'50L capacity hiking backpack','CAT00402',25,'2025-01-13 12:19:45','2025-01-13 12:19:45',0,0),(188,'Trekking Poles','seller1',50000,'Adjustable trekking poles with anti-shock','CAT00402',40,'2025-01-13 12:19:45','2025-01-13 12:19:45',0,0),(189,'Portable Camping Stove','seller2',30000,'Compact gas stove for camping','CAT00402',15,'2025-01-13 12:19:45','2025-01-13 12:19:45',0,0),(190,'Hydration Pack','seller3',80000,'2L hydration pack for outdoor activities','CAT00402',20,'2025-01-13 12:19:45','2025-01-13 12:19:45',0,0),(191,'Camping Lantern','seller1',20000,'Rechargeable LED lantern','CAT00402',35,'2025-01-13 12:19:45','2025-01-13 12:19:45',0,0),(192,'Outdoor Folding Chair','seller2',25000,'Lightweight folding chair for camping','CAT00402',50,'2025-01-13 12:19:45','2025-01-13 12:19:45',0,0),(193,'Survival Kit','seller3',50000,'Essential survival tools in a compact kit','CAT00402',12,'2025-01-13 12:19:45','2025-01-13 12:19:45',0,0),(194,'Binoculars','seller1',85000,'10x50 binoculars for bird watching','CAT00402',18,'2025-01-13 12:19:45','2025-01-13 12:19:45',0,0),(195,'To Kill a Mockingbird','seller1',12000,'Classic novel by Harper Lee','CAT00501',100,'2025-01-13 12:19:47','2025-01-13 12:19:47',0,0),(196,'1984','seller2',9000,'Dystopian novel by George Orwell','CAT00501',80,'2025-01-13 12:19:47','2025-01-13 12:19:47',0,0),(197,'The Great Gatsby','seller3',10000,'Fiction novel by F. Scott Fitzgerald','CAT00501',60,'2025-01-13 12:19:47','2025-01-13 12:19:47',0,0),(198,'Pride and Prejudice','seller1',15000,'Romantic novel by Jane Austen','CAT00501',90,'2025-01-13 12:19:47','2025-01-13 12:19:47',0,0),(199,'Moby Dick','seller2',18000,'Adventure novel by Herman Melville','CAT00501',40,'2025-01-13 12:19:47','2025-01-13 12:19:47',0,0),(200,'War and Peace','seller3',22000,'Historical novel by Leo Tolstoy','CAT00501',20,'2025-01-13 12:19:47','2025-01-13 12:19:47',0,0),(201,'The Catcher in the Rye','seller1',12000,'Novel by J.D. Salinger','CAT00501',70,'2025-01-13 12:19:47','2025-01-13 12:19:47',0,0),(202,'The Hobbit','seller2',14000,'Fantasy novel by J.R.R. Tolkien','CAT00501',50,'2025-01-13 12:19:47','2025-01-13 12:19:47',0,0),(203,'Crime and Punishment','seller3',16000,'Novel by Fyodor Dostoevsky','CAT00501',30,'2025-01-13 12:19:47','2025-01-13 12:19:47',0,0),(204,'Jane Eyre','seller1',13000,'Novel by Charlotte Bronte','CAT00501',65,'2025-01-13 12:19:47','2025-01-13 12:19:47',0,0),(205,'Sapiens','seller1',20000,'Brief history of humankind by Yuval Noah Harari','CAT00502',50,'2025-01-13 12:19:49','2025-01-13 12:19:49',0,0),(206,'Educated','seller2',15000,'Memoir by Tara Westover','CAT00502',40,'2025-01-13 12:19:49','2025-01-13 12:19:49',0,0),(207,'Becoming','seller3',18000,'Memoir by Michelle Obama','CAT00502',35,'2025-01-13 12:19:49','2025-01-13 12:19:49',0,0),(208,'Atomic Habits','seller1',17000,'Guide to building good habits by James Clear','CAT00502',60,'2025-01-13 12:19:49','2025-01-13 12:19:49',0,0),(209,'Thinking, Fast and Slow','seller2',16000,'Psychology book by Daniel Kahneman','CAT00502',70,'2025-01-13 12:19:49','2025-01-13 12:19:49',0,0),(210,'The Power of Habit','seller3',14000,'Insights into habit formation by Charles Duhigg','CAT00502',45,'2025-01-13 12:19:49','2025-01-13 12:19:49',0,0),(211,'The Immortal Life of Henrietta Lacks','seller1',13000,'Biography by Rebecca Skloot','CAT00502',80,'2025-01-13 12:19:49','2025-01-13 12:19:49',0,0),(212,'Grit','seller2',12000,'Book on perseverance by Angela Duckworth','CAT00502',90,'2025-01-13 12:19:49','2025-01-13 12:19:49',0,0),(213,'Outliers','seller3',15000,'Book on success by Malcolm Gladwell','CAT00502',30,'2025-01-13 12:19:49','2025-01-13 12:19:49',0,0),(214,'The Wright Brothers','seller1',14000,'Biography by David McCullough','CAT00502',55,'2025-01-13 12:19:49','2025-01-13 12:19:49',0,0);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userid` varchar(15) NOT NULL,
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(18) DEFAULT NULL,
  `nickname` varchar(30) DEFAULT NULL,
  `phone` varchar(13) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `address1` varchar(100) DEFAULT NULL,
  `address2` varchar(100) DEFAULT NULL,
  `role` varchar(6) DEFAULT NULL,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `users_unique` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (24,'admin','1234','김철수','admin','01012345678','admin@example.com','서울특별시 강남구','삼성동 123-4','ADMIN','2024-10-07 01:07:34','2024-10-07 01:07:34'),(25,'seller1','1234','박영희','seller1','01023456789','seller1@example.com','부산광역시 해운대구','우동 567-8','SELLER','2024-10-07 01:07:34','2024-10-07 01:07:34'),(26,'seller2','1234','이민수','seller2','01034567890','seller2@example.com','인천광역시 남동구','논현동 432-1','SELLER','2024-10-07 01:07:34','2024-10-07 01:07:34'),(27,'seller3','1234','정하나','seller3','01045678901','seller3@example.com','대구광역시 수성구','범어동 321-6','SELLER','2024-10-07 01:07:34','2024-10-07 01:07:34'),(28,'normal1','1234','오준호','normal2','01067890123','normal2@example.com','대전광역시 유성구','궁동 456-7','NORMAL','2024-10-07 01:07:34','2024-10-07 01:07:34'),(29,'normal2','1234','윤지영','normal3','01078901234','normal3@example.com','광주광역시 북구','운암동 234-1','NORMAL','2024-10-07 01:07:34','2024-10-07 01:07:34'),(30,'normal3','1234','강석민','normal4','01089012345','normal4@example.com','경상남도 창원시 성산구','상남동 987-6','NORMAL','2024-10-07 01:07:34','2024-10-07 01:07:34'),(31,'normal4','1234','홍수연','normal5','01090123456','normal5@example.com','울산광역시 남구','삼산동 765-2','NORMAL','2024-10-07 01:07:34','2024-10-07 01:07:34'),(32,'normal5','1234','김진우','normal6','01001234567','normal6@example.com','경기도 용인시 기흥구','동백동 654-3','NORMAL','2024-10-07 01:07:34','2024-10-07 01:07:34');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zzim`
--

DROP TABLE IF EXISTS `zzim`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `zzim` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `userid` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  KEY `zzim_product_FK` (`product_id`),
  KEY `zzim_users_FK` (`userid`),
  CONSTRAINT `zzim_product_FK` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  CONSTRAINT `zzim_users_FK` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zzim`
--

LOCK TABLES `zzim` WRITE;
/*!40000 ALTER TABLE `zzim` DISABLE KEYS */;
/*!40000 ALTER TABLE `zzim` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-13 21:43:27
