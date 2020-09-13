DROP TABLE IF EXISTS action_types;
DROP TABLE IF EXISTS events;

CREATE TABLE action_types (
                              id smallint NOT NULL AUTO_INCREMENT,
                              title varchar(255) NOT NULL,
                              description varchar(5000) NOT NULL,
                              entity_type varchar(255) NOT NULL,
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `title_UNIQUE` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci
    COMMENT='SMALLINT == short';

CREATE TABLE events (
                          id bigint NOT NULL AUTO_INCREMENT,
                          action_type_id  smallint NOT NULL,
                          issuer VARCHAR(255) NOT NULL,
                          issuer_event_id bigint NOT NULL DEFAULT 0,
                          entity_type VARCHAR(255) NOT NULL DEFAULT 'NO ENTITY',
                          entity_id bigint NOT NULL DEFAULT 0,
                          issuer_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          recipient_accepted_at TIMESTAMP,
                          PRIMARY KEY (id),
                          KEY fk_action_type_id_idx (action_type_id),
                          CONSTRAINT fk_action_type_id FOREIGN KEY (action_type_id) REFERENCES action_types (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO action_types (title, description, entity_type) VALUES
('CREATED', 'Заказ сформирован пользователем и сохранен в списке заказов', 'Order'),
('STATUS_CHANGED', 'Изменился статус Заказа. Заказ переведен на следующий этап обработки', 'Order'),
('LOGGED_ID', 'Пользователь авторизовался', 'User'),
('LOGGED_OUT', 'Пользователь отлогинился', 'User'),
('ADDED_TO_CART', 'Товар добавлен в корзину', 'Product'),
('REMOVED_FROM_CART', 'Товар удален из корзины', 'Product');