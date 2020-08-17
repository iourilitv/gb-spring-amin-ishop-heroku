SET FOREIGN_KEY_CHECKS = 0;

drop table if exists orders;
drop table if exists deliveries;
drop table if exists order_item;
drop table if exists users_roles;
drop table if exists users;
drop table if exists addresses;
drop table if exists roles;

CREATE TABLE `roles` (
    `id` smallint NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `description` varchar(5000) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `addresses` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `country` varchar(50) NOT NULL,
    `city` varchar(50) NOT NULL,
    `address` varchar(500) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_city_address` (`city`, `address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `username` varchar(255) NOT NULL,
    `password` char(80) NOT NULL,
    `first_name` varchar(255) NOT NULL,
    `last_name` varchar(255) NOT NULL,
    `phone_number` varchar(255) NOT NULL,
    `email` varchar(255) NOT NULL,
    `delivery_address_id` bigint NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_username` (`username`),
    UNIQUE KEY `UK_email` (`email`),
    KEY `fk_users_delivery_address_id_idx` (`delivery_address_id`),
    CONSTRAINT `fk_users_delivery_address_id` FOREIGN KEY (`delivery_address_id`) REFERENCES `addresses` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users_roles` (
    `user_id` bigint NOT NULL,
    `role_id` smallint NOT NULL,
    PRIMARY KEY (`user_id`, `role_id`),
    CONSTRAINT `fk_ur_user_id` FOREIGN KEY (`user_id`)
        REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_ur_role_id` FOREIGN KEY (`role_id`)
        REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `order_item` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `order_id` bigint NOT NULL,
    `product_id` bigint NOT NULL,
    `item_price` decimal(19,2) NOT NULL,
    `quantity` int NOT NULL,
    `item_costs` decimal(19,2) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_oi_order_id_idx` (`order_id`),
    KEY `fk_oi_product_id_idx` (`product_id`),
    CONSTRAINT `fk_oi_order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
    CONSTRAINT `fk_oi_product_id` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `deliveries` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `order_id` bigint NOT NULL,
    `phone_number` varchar(255) NOT NULL,
    `delivery_address_id` bigint NOT NULL,
    `delivery_cost` decimal(19,2) NULL DEFAULT NULL,
    `delivery_expected_at` DATETIME NULL DEFAULT NULL,
    `delivered_at` DATETIME NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_del_order_id_idx` (`order_id`),
    KEY `fk_del_delivery_address_id01_idx` (`delivery_address_id`),
    CONSTRAINT `fk_del_order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
    CONSTRAINT `fk_del_delivery_address_id` FOREIGN KEY (`delivery_address_id`) REFERENCES `addresses` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `orders` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `status_id` smallint NOT NULL,
    `user_id` bigint NOT NULL,
    `total_items_costs` decimal(19,2) NOT NULL,
    `total_costs` decimal(19,2) NOT NULL,
    `delivery_id` bigint NULL DEFAULT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT NOW(),
    `updated_at` TIMESTAMP NOT NULL DEFAULT NOW(),
    PRIMARY KEY (`id`),
    KEY `fk_orders_status_id_idx` (`status_id`),
    KEY `fk_orders_user_id_idx` (`user_id`),
    KEY `fk_orders_delivery_id_idx` (`delivery_id`),
    CONSTRAINT `fk_orders_status_id` FOREIGN KEY (`status_id`) REFERENCES `order_statuses` (`id`),
    CONSTRAINT `fk_orders_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `fk_orders_delivery_id` FOREIGN KEY (`delivery_id`) REFERENCES `deliveries` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;