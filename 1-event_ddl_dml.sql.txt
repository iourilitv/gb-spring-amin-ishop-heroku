DROP TABLE IF EXISTS events;

CREATE TABLE events (
                          id bigint NOT NULL AUTO_INCREMENT,
                          action_type VARCHAR(255) NOT NULL,
                          title VARCHAR(255) NOT NULL,
                          description VARCHAR(500) NOT NULL,
                          entity_type VARCHAR(255) NOT NULL DEFAULT 'NO ENTITY',
                          entity_id bigint NOT NULL DEFAULT 0,
                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          server_accepted_at TIMESTAMP,
                          PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;