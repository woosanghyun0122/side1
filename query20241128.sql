-- user 테이블 DDL

-- side.users definition

CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userid` varchar(255) NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `address1` varchar(255) DEFAULT NULL,
  `address2` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `users_unique` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- user 테이블 데이터

INSERT INTO users (userid,password,username,nickname,phone,email,address1,address2,`role`,created_at,updated_at) VALUES
	 ('admin','1234','김철수','admin','01012345678','admin@example.com','서울특별시 강남구','삼성동 123-4','ADMIN','2024-10-07 10:07:34','2024-10-07 10:07:34'),
	 ('seller1','1234','박영희','seller1','01023456789','seller1@example.com','부산광역시 해운대구','우동 567-8','SELLER','2024-10-07 10:07:34','2024-10-07 10:07:34'),
	 ('seller2','1234','이민수','seller2','01034567890','seller2@example.com','인천광역시 남동구','논현동 432-1','SELLER','2024-10-07 10:07:34','2024-10-07 10:07:34'),
	 ('seller3','1234','정하나','seller3','01045678901','seller3@example.com','대구광역시 수성구','범어동 321-6','SELLER','2024-10-07 10:07:34','2024-10-07 10:07:34'),
	 ('normal1','1234','오준호','normal2','01067890123','normal2@example.com','대전광역시 유성구','궁동 456-7','NORMAL','2024-10-07 10:07:34','2024-10-07 10:07:34'),
	 ('normal2','1234','윤지영','normal3','01078901234','normal3@example.com','광주광역시 북구','운암동 234-1','NORMAL','2024-10-07 10:07:34','2024-10-07 10:07:34'),
	 ('normal3','1234','강석민','normal4','01089012345','normal4@example.com','경상남도 창원시 성산구','상남동 987-6','NORMAL','2024-10-07 10:07:34','2024-10-07 10:07:34'),
	 ('normal4','1234','홍수연','normal5','01090123456','normal5@example.com','울산광역시 남구','삼산동 765-2','NORMAL','2024-10-07 10:07:34','2024-10-07 10:07:34'),
	 ('normal5','1234','김진우','normal6','01001234567','normal6@example.com','경기도 용인시 기흥구','동백동 654-3','NORMAL','2024-10-07 10:07:34','2024-10-07 10:07:34');

-- product Table DDL

-- side.product definition

CREATE TABLE `product` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `userid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '판매자 ID',
  `price` int DEFAULT NULL,
  `content` text,
  `category_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `quantity` int DEFAULT NULL,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  `sale_count` int DEFAULT '0',
  `view_count` int DEFAULT '0',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- product table DATABASE
-- Mobile Phones (하위 카테고리: CAT00101)
INSERT INTO `product` (`name`, `userid`, `price`, `content`, `category_id`, `quantity`, `created_at`, `updated_at`) VALUES
('iPhone 13 Pro', 'seller1', 1200000, 'Apple iPhone 13 Pro with 128GB storage', 'CAT00101', 50, NOW(), NOW()),
('Samsung Galaxy S21', 'seller1', 1100000, 'Samsung Galaxy S21 with 256GB storage', 'CAT00101', 30, NOW(), NOW()),
('Google Pixel 6', 'seller1', 1000000, 'Google Pixel 6 with Android 12', 'CAT00101', 40, NOW(), NOW()),
('OnePlus 9', 'seller2', 900000, 'OnePlus 9 with 8GB RAM and 128GB storage', 'CAT00101', 25, NOW(), NOW()),
('Xiaomi Mi 11', 'seller2', 700000, 'Xiaomi Mi 11 with 108MP camera', 'CAT00101', 20, NOW(), NOW()),
('Sony Xperia 5 III', 'seller2', 800000, 'Sony Xperia 5 III with OLED display', 'CAT00101', 15, NOW(), NOW()),
('Oppo Find X3', 'seller3', 1000000, 'Oppo Find X3 with Snapdragon processor', 'CAT00101', 10, NOW(), NOW()),
('Huawei P50 Pro', 'seller3', 700000, 'Huawei P50 Pro with Leica camera', 'CAT00101', 8, NOW(), NOW()),
('Asus ROG Phone 5', 'seller3', 1200000, 'Gaming phone with 144Hz display', 'CAT00101', 12, NOW(), NOW()),
('Nokia G50', 'seller3', 500000, 'Affordable smartphone with 5G support', 'CAT00101', 60, NOW(), NOW());

-- Laptops (하위 카테고리: CAT00102)
INSERT INTO `product` (`name`, `userid`, `price`, `content`, `category_id`, `quantity`, `created_at`, `updated_at`) VALUES
('MacBook Pro 16"', 'seller3', 2000000, 'Apple MacBook Pro with M1 Max chip', 'CAT00102', 10, NOW(), NOW()),
('Dell XPS 13', 'seller3', 13000000, 'Dell XPS 13 with 11th Gen Intel processor', 'CAT00102', 20, NOW(), NOW()),
('HP Spectre x360', 'seller3', 1000000, 'HP Spectre x360 convertible laptop', 'CAT00102', 15, NOW(), NOW()),
('Lenovo ThinkPad X1 Carbon', 'seller1', 1100000, 'Lenovo business laptop with Intel vPro', 'CAT00102', 18, NOW(), NOW()),
('Asus ZenBook 14', 'seller1', 1200000, 'Ultra-thin laptop with OLED display', 'CAT00102', 12, NOW(), NOW()),
('Microsoft Surface Laptop 4', 'seller1', 1000000, 'Microsoft Surface with AMD Ryzen', 'CAT00102', 17, NOW(), NOW()),
('Acer Swift 3', 'seller2', 8000000, 'Affordable laptop with long battery life', 'CAT00102', 22, NOW(), NOW()),
('Razer Blade 15', 'seller2', 1600000, 'Gaming laptop with GeForce RTX 3070', 'CAT00102', 8, NOW(), NOW()),
('LG Gram 17', 'seller2', 1900000, 'Ultra-lightweight 17-inch laptop', 'CAT00102', 11, NOW(), NOW()),
('Samsung Galaxy Book Pro', 'seller2', 1800000, 'Samsung laptop with AMOLED screen', 'CAT00102', 14, NOW(), NOW());

-- Men Clothing (하위 카테고리: CAT00201)
INSERT INTO `product` (`name`, `userid`, `price`, `content`, `category_id`, `quantity`, `created_at`, `updated_at`) VALUES
('Levi\'s Men\'s Jeans', 'seller2', 139000, 'Classic fit denim jeans', 'CAT00201', 100, NOW(), NOW()),
('Nike Men\'s Running Shoes', 'seller2', 120000, 'Lightweight and comfortable', 'CAT00201', 50, NOW(), NOW()),
('Adidas Men\'s Hoodie', 'seller2', 70000, 'Comfortable and stylish hoodie', 'CAT00201', 60, NOW(), NOW()),
('Polo Ralph Lauren T-Shirt', 'seller3', 179000, 'Soft cotton T-shirt', 'CAT00201', 80, NOW(), NOW()),
('Under Armour Shorts', 'seller3', 35000, 'Breathable and durable', 'CAT00201', 40, NOW(), NOW()),
('Calvin Klein Jacket', 'seller3', 129000, 'Stylish bomber jacket', 'CAT00201', 20, NOW(), NOW()),
('Vans Slip-On Sneakers', 'seller1', 59000, 'Classic slip-on sneakers', 'CAT00201', 90, NOW(), NOW()),
('Reebok Sweatpants', 'seller1', 55000, 'Comfortable for casual wear', 'CAT00201', 70, NOW(), NOW()),
('Hanes Men\'s Boxer Briefs', 'seller1', 25000, 'Pack of 5 boxer briefs', 'CAT00201', 110, NOW(), NOW()),
('The North Face Men\'s Parka', 'seller1', 219000, 'Insulated parka for cold weather', 'CAT00201', 15, NOW(), NOW());

-- Women Clothing (하위 카테고리: CAT00202)
INSERT INTO `product` (`name`, `userid`, `price`, `content`, `category_id`, `quantity`, `created_at`, `updated_at`) VALUES
('Zara Women\'s Dress', 'user031', 80000, 'Elegant floral dress', 'CAT00202', 50, NOW(), NOW()),
('H&M Women\'s Blazer', 'user032', 39000, 'Professional blazer for women', 'CAT00202', 40, NOW(), NOW()),
('Forever 21 Skirt', 'user033', 30000, 'A-line mini skirt', 'CAT00202', 70, NOW(), NOW()),
('Uniqlo Women\'s Sweater', 'user034', 39000, 'Soft and warm sweater', 'CAT00202', 90, NOW(), NOW()),
('Coach Handbag', 'user035', 250000, 'Leather handbag with spacious interior', 'CAT00202', 20, NOW(), NOW()),
('Michael Kors Watch', 'user036', 180000, 'Elegant wrist watch', 'CAT00202', 30, NOW(), NOW()),
('Aldo Women\'s Heels', 'user037', 89000, 'Stylish high heels', 'CAT00202', 25, NOW(), NOW()),
('Gucci Scarf', 'user038', 350000, 'Silk scarf with designer print', 'CAT00202', 10, NOW(), NOW()),
('Sephora Lipstick', 'user039', 25000, 'Long-lasting lipstick', 'CAT00202', 100, NOW(), NOW()),
('Ray-Ban Sunglasses', 'user040', 150000, 'UV-protection sunglasses', 'CAT00202', 35, NOW(), NOW());

-- Kitchen Appliances (하위 카테고리: CAT00301)
INSERT INTO `product` (`name`, `userid`, `price`, `content`, `category_id`, `quantity`, `created_at`, `updated_at`) VALUES
('KitchenAid Mixer', 'seller1', 39900, 'Stand mixer with various attachments', 'CAT00301', 10, NOW(), NOW()),
('Instant Pot', 'seller1', 89000, 'Electric pressure cooker', 'CAT00301', 40, NOW(), NOW()),
('Ninja Blender', 'seller1', 120000, 'High-speed blender for smoothies', 'CAT00301', 30, NOW(), NOW()),
('Cuisinart Toaster', 'seller2', 60000, '4-slice toaster with browning control', 'CAT00301', 25, NOW(), NOW()),
('Breville Espresso Machine', 'seller2', 599000, 'Automatic espresso machine', 'CAT00301', 12, NOW(), NOW()),
('Hamilton Beach Slow Cooker', 'seller2', 50000, '6-quart slow cooker', 'CAT00301', 35, NOW(), NOW()),
('Dyson Air Purifier', 'seller3', 399000, 'Removes allergens from air', 'CAT00301', 15, NOW(), NOW()),
('Philips Air Fryer', 'seller3', 120000, 'Compact air fryer', 'CAT00301', 20, NOW(), NOW()),
('Whirlpool Refrigerator', 'seller3', 1500000, 'Energy-efficient refrigerator', 'CAT00301', 8, NOW(), NOW()),
('Smeg Toaster', 'seller3', 150000, 'Retro design 2-slice toaster', 'CAT00301', 18, NOW(), NOW());

-- Appliances (하위 카테고리: CAT00302)
INSERT INTO `product` (`name`, `userid`, `price`, `content`, `category_id`, `quantity`, `created_at`, `updated_at`) VALUES
('LG Washing Machine', 'seller1', 1200000, 'LG 15kg washing machine with smart inverter technology', 'CAT00302', 10, NOW(), NOW()),
('Samsung Microwave Oven', 'seller2', 150000, 'Compact microwave oven with smart features', 'CAT00302', 15, NOW(), NOW()),
('Dyson Vacuum Cleaner', 'seller3', 550000, 'Powerful cordless vacuum cleaner', 'CAT00302', 20, NOW(), NOW()),
('Philips Air Fryer', 'seller1', 180000, 'Compact air fryer with rapid air technology', 'CAT00302', 12, NOW(), NOW()),
('Cuckoo Rice Cooker', 'seller2', 250000, '10-cup rice cooker with multi-function settings', 'CAT00302', 25, NOW(), NOW()),
('Hurom Slow Juicer', 'seller3', 300000, 'Slow juicer preserving more nutrients', 'CAT00302', 18, NOW(), NOW()),
('Whirlpool Refrigerator', 'seller1', 1800000, 'Energy-efficient double door refrigerator', 'CAT00302', 8, NOW(), NOW()),
('Cuchen Electric Stove', 'seller2', 120000, 'Compact electric stove with touch controls', 'CAT00302', 10, NOW(), NOW()),
('Breville Espresso Machine', 'seller3', 950000, 'Automatic espresso machine with frother', 'CAT00302', 6, NOW(), NOW()),
('Panasonic Blender', 'seller1', 90000, 'High-speed blender with stainless steel blades', 'CAT00302', 20, NOW(), NOW());

-- Exercise Equipment (하위 카테고리: CAT00401)
INSERT INTO `product` (`name`, `userid`, `price`, `content`, `category_id`, `quantity`, `created_at`, `updated_at`) VALUES
('Treadmill', 'seller1', 1500000, 'High-speed treadmill with incline feature', 'CAT00401', 5, NOW(), NOW()),
('Dumbbell Set', 'seller2', 50000, 'Adjustable dumbbell set 5kg to 30kg', 'CAT00401', 30, NOW(), NOW()),
('Exercise Bike', 'seller3', 250000, 'Indoor exercise bike with digital display', 'CAT00401', 10, NOW(), NOW()),
('Yoga Mat', 'seller1', 30000, 'Non-slip yoga mat with extra thickness', 'CAT00401', 50, NOW(), NOW()),
('Resistance Bands', 'seller2', 20000, 'Set of 5 resistance bands for various exercises', 'CAT00401', 40, NOW(), NOW()),
('Foam Roller', 'seller3', 25000, 'High-density foam roller for muscle recovery', 'CAT00401', 20, NOW(), NOW()),
('Pull-Up Bar', 'seller1', 40000, 'Doorway pull-up bar with adjustable width', 'CAT00401', 25, NOW(), NOW()),
('Kettlebell', 'seller2', 60000, 'Cast iron kettlebell, 10kg', 'CAT00401', 15, NOW(), NOW()),
('Rowing Machine', 'seller3', 350000, 'Compact rowing machine with resistance settings', 'CAT00401', 8, NOW(), NOW()),
('Ab Roller', 'seller1', 15000, 'Ab roller for core strengthening', 'CAT00401', 60, NOW(), NOW());

-- Outdoor Gear (하위 카테고리: CAT00402)
INSERT INTO `product` (`name`, `userid`, `price`, `content`, `category_id`, `quantity`, `created_at`, `updated_at`) VALUES
('Coleman Tent', 'seller1', 250000, '4-person tent with waterproof coating', 'CAT00402', 10, NOW(), NOW()),
('Sleeping Bag', 'seller2', 70000, 'Lightweight sleeping bag for 3 seasons', 'CAT00402', 30, NOW(), NOW()),
('Hiking Backpack', 'seller3', 90000, '50L capacity hiking backpack', 'CAT00402', 25, NOW(), NOW()),
('Trekking Poles', 'seller1', 50000, 'Adjustable trekking poles with anti-shock', 'CAT00402', 40, NOW(), NOW()),
('Portable Camping Stove', 'seller2', 30000, 'Compact gas stove for camping', 'CAT00402', 15, NOW(), NOW()),
('Hydration Pack', 'seller3', 80000, '2L hydration pack for outdoor activities', 'CAT00402', 20, NOW(), NOW()),
('Camping Lantern', 'seller1', 20000, 'Rechargeable LED lantern', 'CAT00402', 35, NOW(), NOW()),
('Outdoor Folding Chair', 'seller2', 25000, 'Lightweight folding chair for camping', 'CAT00402', 50, NOW(), NOW()),
('Survival Kit', 'seller3', 50000, 'Essential survival tools in a compact kit', 'CAT00402', 12, NOW(), NOW()),
('Binoculars', 'seller1', 85000, '10x50 binoculars for bird watching', 'CAT00402', 18, NOW(), NOW());

-- Fiction Books (하위 카테고리: CAT00501)
INSERT INTO `product` (`name`, `userid`, `price`, `content`, `category_id`, `quantity`, `created_at`, `updated_at`) VALUES
('To Kill a Mockingbird', 'seller1', 12000, 'Classic novel by Harper Lee', 'CAT00501', 100, NOW(), NOW()),
('1984', 'seller2', 9000, 'Dystopian novel by George Orwell', 'CAT00501', 80, NOW(), NOW()),
('The Great Gatsby', 'seller3', 10000, 'Fiction novel by F. Scott Fitzgerald', 'CAT00501', 60, NOW(), NOW()),
('Pride and Prejudice', 'seller1', 15000, 'Romantic novel by Jane Austen', 'CAT00501', 90, NOW(), NOW()),
('Moby Dick', 'seller2', 18000, 'Adventure novel by Herman Melville', 'CAT00501', 40, NOW(), NOW()),
('War and Peace', 'seller3', 22000, 'Historical novel by Leo Tolstoy', 'CAT00501', 20, NOW(), NOW()),
('The Catcher in the Rye', 'seller1', 12000, 'Novel by J.D. Salinger', 'CAT00501', 70, NOW(), NOW()),
('The Hobbit', 'seller2', 14000, 'Fantasy novel by J.R.R. Tolkien', 'CAT00501', 50, NOW(), NOW()),
('Crime and Punishment', 'seller3', 16000, 'Novel by Fyodor Dostoevsky', 'CAT00501', 30, NOW(), NOW()),
('Jane Eyre', 'seller1', 13000, 'Novel by Charlotte Bronte', 'CAT00501', 65, NOW(), NOW());

-- Non-Fiction Books (하위 카테고리: CAT00502)
INSERT INTO `product` (`name`, `userid`, `price`, `content`, `category_id`, `quantity`, `created_at`, `updated_at`) VALUES
('Sapiens', 'seller1', 20000, 'Brief history of humankind by Yuval Noah Harari', 'CAT00502', 50, NOW(), NOW()),
('Educated', 'seller2', 15000, 'Memoir by Tara Westover', 'CAT00502', 40, NOW(), NOW()),
('Becoming', 'seller3', 18000, 'Memoir by Michelle Obama', 'CAT00502', 35, NOW(), NOW()),
('Atomic Habits', 'seller1', 17000, 'Guide to building good habits by James Clear', 'CAT00502', 60, NOW(), NOW()),
('Thinking, Fast and Slow', 'seller2', 16000, 'Psychology book by Daniel Kahneman', 'CAT00502', 70, NOW(), NOW()),
('The Power of Habit', 'seller3', 14000, 'Insights into habit formation by Charles Duhigg', 'CAT00502', 45, NOW(), NOW()),
('The Immortal Life of Henrietta Lacks', 'seller1', 13000, 'Biography by Rebecca Skloot', 'CAT00502', 80, NOW(), NOW()),
('Grit', 'seller2', 12000, 'Book on perseverance by Angela Duckworth', 'CAT00502', 90, NOW(), NOW()),
('Outliers', 'seller3', 15000, 'Book on success by Malcolm Gladwell', 'CAT00502', 30, NOW(), NOW()),
('The Wright Brothers', 'seller1', 14000, 'Biography by David McCullough', 'CAT00502', 55, NOW(), NOW());

-- category Table

CREATE TABLE `category` (
  `category_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `depth` int NOT NULL,
  `parent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- category table data

INSERT INTO `category` (`category_id`, `category_name`, `depth`, `parent_id`, `created_at`, `updated_at`) VALUES
('CAT001', '전자제품', 1, NULL, NOW(), NOW()),
('CAT002', '패션', 1, NULL, NOW(), NOW()),
('CAT003', '홈&키친', 1, NULL, NOW(), NOW()),
('CAT004', '스포츠 & 아웃도어', 1, NULL, NOW(), NOW()),
('CAT005', '도서', 1, NULL, NOW(), NOW());

-- 하위 카테고리 2개씩 생성
INSERT INTO `category` (`category_id`, `category_name`, `depth`, `parent_id`, `created_at`, `updated_at`) VALUES
-- Electronics 하위 카테고리
('CAT00101', '핸드폰', 2, 'CAT001', NOW(), NOW()),
('CAT00102', '노트북', 2, 'CAT001', NOW(), NOW()),

-- Clothing 하위 카테고리
('CAT00201', '남성', 2, 'CAT002', NOW(), NOW()),
('CAT00202', '여성', 2, 'CAT002', NOW(), NOW()),

-- Home & Kitchen 하위 카테고리
('CAT00301', '가구', 2, 'CAT003', NOW(), NOW()),
('CAT00302', '가전', 2, 'CAT003', NOW(), NOW()),

-- Sports & Outdoors 하위 카테고리
('CAT00401', '스포츠용품', 2, 'CAT004', NOW(), NOW()),
('CAT00402', '아웃도어 제품', 2, 'CAT004', NOW(), NOW()),

-- Books 하위 카테고리
('CAT00501', '소설', 2, 'CAT005', NOW(), NOW()),
('CAT00502', '기타', 2, 'CAT005', NOW(), NOW());

-- side.orders definition

CREATE TABLE `orders` (
  `order_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `userid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `total_price` int DEFAULT NULL,
  `order_date` timestamp NOT NULL,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '구매자명',
  `address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `address_detail` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `phone` varchar(13) DEFAULT NULL,
  `total_count` int DEFAULT NULL,
  `req` varchar(100) DEFAULT NULL COMMENT '배송요청사항',
  `payment_method` varchar(100) DEFAULT NULL,
  `zip_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `payment_key` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`order_num`),
  KEY `order_users_FK` (`userid`),
  KEY `orders_payment_FK` (`payment_key`),
  CONSTRAINT `order_users_FK` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`),
  CONSTRAINT `orders_payment_FK` FOREIGN KEY (`payment_key`) REFERENCES `payment` (`payment_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- side.order_item definition


CREATE TABLE `order_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `amount` int DEFAULT NULL,
  `price` int DEFAULT NULL,
  `order_date` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `product_name` varchar(100) DEFAULT NULL,
  `total_price` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  `reason` blob COMMENT '환불 사유',
  PRIMARY KEY (`id`),
  KEY `order_item_order_fk` (`order_num`),
  KEY `order_item_product_FK` (`product_id`),
  CONSTRAINT `order_item_order_fk` FOREIGN KEY (`order_num`) REFERENCES `orders` (`order_num`),
  CONSTRAINT `order_item_product_FK` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- side.payment definition

CREATE TABLE `payment` (
  `id` varchar(255) NOT NULL,
  `order_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `userid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `pay_date` timestamp NOT NULL,
  `payment_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `payment_status` varchar(255) DEFAULT NULL,
  `payment_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `price` int DEFAULT NULL,
  `created_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  `pay_success_yn` varchar(1) DEFAULT NULL,
  `cancel_reason` varchar(100) DEFAULT NULL,
  `fail_reason` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `order_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `payment_unique` (`payment_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- side.image definition

CREATE TABLE `image` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `image_url` varchar(255) NOT NULL,
  `alt_text` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  KEY `image_product_fk` (`product_id`),
  CONSTRAINT `image_product_fk` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- side.cart definition

CREATE TABLE `cart` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `userid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created_at` timestamp NOT NULL,
  PRIMARY KEY (`product_id`),
  CONSTRAINT `cart_product_fk` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- side.commoncode definition

CREATE TABLE `commoncode` (
  `code_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '코드명',
  `code_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '코드값',
  `display_name` varchar(255) DEFAULT NULL,
  `create_at` timestamp NOT NULL,
  `updated_at` timestamp NOT NULL,
  PRIMARY KEY (`code_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;