DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS products_images;
DROP TABLE IF EXISTS order_statuses;

CREATE TABLE `categories` (
    `id` smallint NOT NULL AUTO_INCREMENT,
    `title` varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `title_UNIQUE` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
    COMMENT='SMALLINT == short';

CREATE TABLE products (
    id bigint NOT NULL AUTO_INCREMENT,
    category_id smallint NOT NULL,
    vendor_code VARCHAR(8) NOT NULL,
    title VARCHAR(255) NOT NULL,
    short_description VARCHAR(1000) NOT NULL,
    full_description VARCHAR(5000) NOT NULL,
    price DECIMAL(19,2) NOT NULL,
    create_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY UK_title_products (title),
    KEY fk_category_id_idx (category_id),
    CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES categories (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE products_images (
    id bigint NOT NULL AUTO_INCREMENT,
    product_id bigint NOT NULL,
    path VARCHAR(250) NOT NULL,
    PRIMARY KEY (id),
    KEY fk_product_id_idx (product_id),
    CONSTRAINT fk_product_id_images FOREIGN KEY (product_id) REFERENCES products (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `order_statuses` (
    `id` smallint NOT NULL AUTO_INCREMENT,
    `title` varchar(255) NOT NULL,
    `description` text,
    PRIMARY KEY (`id`),
    UNIQUE KEY `title_UNIQUE` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
    COMMENT='SMALLINT == short';