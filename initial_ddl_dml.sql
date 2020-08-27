DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS products_images;
DROP TABLE IF EXISTS order_statuses;

CREATE TABLE `categories` (
                              `id` smallint NOT NULL AUTO_INCREMENT,
                              `title` varchar(255) NOT NULL,
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `title_UNIQUE` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE products_images (
                                 id bigint NOT NULL AUTO_INCREMENT,
                                 product_id bigint NOT NULL,
                                 path VARCHAR(250) NOT NULL,
                                 PRIMARY KEY (id),
                                 KEY fk_product_id_idx (product_id),
                                 CONSTRAINT fk_product_id_images FOREIGN KEY (product_id) REFERENCES products (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `order_statuses` (
                                  `id` smallint NOT NULL AUTO_INCREMENT,
                                  `title` varchar(255) NOT NULL,
                                  `description` text,
                                  PRIMARY KEY (`id`),
                                  UNIQUE KEY `title_UNIQUE` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci
    COMMENT='SMALLINT == short';

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `addresses` (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `country` varchar(50) NOT NULL,
                             `city` varchar(50) NOT NULL,
                             `address` varchar(500) NOT NULL,
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `UK_city_address` (`city`, `address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `users_roles` (
                               `user_id` bigint NOT NULL,
                               `role_id` smallint NOT NULL,
                               PRIMARY KEY (`user_id`, `role_id`),
                               CONSTRAINT `fk_ur_user_id` FOREIGN KEY (`user_id`)
                                   REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                               CONSTRAINT `fk_ur_role_id` FOREIGN KEY (`role_id`)
                                   REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

# filling database with some data

INSERT INTO `categories` (`title`) VALUES
('Электроника'), ('Офисная техника'), ('Бытовая техника'),
('Обувь'), ('Одежда'), ('Книги'), ('Pets');

INSERT INTO products (category_id, vendor_code, title, price, short_description, full_description)
VALUES
('1', '00000001', 'HP / Ноутбук 15-db1027ur Ryzen 3-3200U/8Gb/1Tb/15.6\"HD/AMD Vega 3/Win10', '35990', 'Качество, производительность и длительное время работы от аккумулятора для решения повседневных задач. Дисплей диагональю 39,6 см (15,6\"\") с антибликовым покрытием и белой светодиодной подсветкой выглядит так же потрясающе, как и работает. Благодаря процессору AMD и графическому адаптеру этот ноутбук предоставляет возможности для просмотра веб-страниц, трансляции видео и выполнения множества других задач. Вам больше не придется ждать зарядки ноутбука несколько часов. Аккумулятор этого ноутбука можно зарядить с 0 до 50 % всего за 45 минут. Проводите время с удовольствием и оставайтесь на связи до 10 часов благодаря аккумулятору с технологией быстрой зарядки HP Fast Charge. Кроме того, накопитель большого объема позволяет с легкостью сохранять любимые фильмы, фото и музыку.', 'add full_description'),
('2', '00000002', 'Pantum / Монохромный лазерный принтер P2207', '5095', 'Монохромный лазерный принтер. Компактный дизайн, низкая стоимость печати и оригинальные заправочные комплекты.', 'add full_description'),
('3', '00000003', 'Polaris / Робот-пылесос PVCR 0726W', '17999', 'В комплектации с роботом-пылесосом представлены контейнер для пыли, резервуар для воды, салфетка из микрофибры, 2 боковых щеточки, 2 фильтра, АКБ и зарядная станция. АКБ емкостью 2600 mAh обеспечивает работу без подзарядки-200 мин. На полную зарядку понадобится не более 5 часов. Высота робота-пылесоса всего 7,6 см, а диаметр 31 см. Размер пылесоса позволит ему проникать под мебель, куда не всегда доберется обычный пылесос. Для ориентации в пространстве прибор оборудован инфракрасными датчиками. Он свободно объезжает мебель и другие препятствия, а не упасть с лестницы ему помогают датчики антипадения. Небольшие изменения высоты, например, пороги или переход с пола на ковер, прибор легко преодолевает без каких-либо повреждений, так как его колеса имеют амортизаторы. Для влажной уборки контейнер для пыли нужно заменить на резервуар с водой, а на нижнюю поверхность пылесоса прикрепить салфетку. С помощью пульта ДУ можно установить текущее время и задать время начала ежедневной уборки.', 'add full_description'),
('3', '00000004', 'Polaris / Мясорубка PMG 3044 ProGear Inside', '9999', 'Мощность мясорубки составляет 3000Вт, что позволяет приготовить до 3кг фарша в минуту, отличается не только высокой мощностью, но и надежностью. Редуктор особой конструкции гарантирует долгую службу устройства за счет износостойкой стальной шестерни. Двигатель мясорубки защищен от перегрузок и перепадов электроэнергии благодаря технологии PROtect+ при опасности перегрева мясорубка автоматически отключается. Мясорубка оснащена функцией реверса-обратная прокрутка незаменима, когда шнек забивается жилами и мясными волокнами, замедляя работу мясорубки. В комплекте есть все необходимое для создания как оригинального праздничного ужина, так и обедов на каждый день-три стальные сетки (3,5,7мм), металлический мясоприемник, пластиковый толкатель для продуктов и две насадки-для приготовления колбас и кеббе. Все детали можно хранить в специальном отсеке внутри прибора. Компактная и стильная, не займет много места, а нескользящие ножки надежно зафиксируют ее на любой рабочей поверхности.', 'add full_description'),
('3', '00000005', 'Bosch / Кофемолка MKM 6000/6003', '1431', 'Очень удобная в повседневной жизни кофемолка. Она предназначена для семей, которые любят выпить по утрам чашечку свежесваренного кофе. Производительность: до 75 гр. кофейных зерен. Степень помола зависит от его продолжительности: чем дольше, тем мельче. Мощность: 180 Вт. Лезвие из нержавеющей стали для точного и качественного помола. Помол осуществляется только при закрытой крышке.', 'add full_description'),
('3', '00000006', 'Karcher / Минимойка K 5 basic', '22490', 'Модель мойки высокого давления K 5 basic это базовый аппарат, который справляется со стойкими загрязнениями и подходит как для очистки велосипедов или автомобилей так и для фасадов зданий, дорожек и прилегающей территории. При регистрации техники на сайте производителя Karcher срок гарантии увеличивается до 10 лет.', 'add full_description'),
('3', '00000007', 'FINISH / Таблетки для мытья посуды в посудомоечной машине 65 шт', '1086', 'Кислородсодержащие отбеливатели 100%', 'add full_description'),
('4', '00000008', 'CROCS / Сандалии', '2309', 'текстиль 100%', 'add full_description'),
('6', '00000009', 'Филипок и Ко / Скорочтение для детей от 6 до 9 лет.', '979', '"Скорочтение. Как научить ребенка быстро читать" - переработанное и усовершенствованное переиздание успешного бестселлера по методике скорочтения Ахмадуллина, разделенное на две книги по возрасту для более точечной проработки навыков. Шамиль Ахмадуллин - психолог-педагог, физик, PhD, разработчик более 40 методик эффективного обучения детей. Основатель школ скорочтения и развития памяти у детей. За 18 дней 20-30 минутных занятий Ваш ребенок научится читать в 2 раза быстрее, лучше понимать, запоминать и пересказывать прочитанное. А главное - вы заложите в ребенка один из самых важных навыков 21 века - быстрое усвоение любой текстовой информации. Это даст ребенку огромные преимущества во взрослой жизни! Книга-тренинг для младших школьников: 18-дневный тренинг; более 100 заданий; улучшение внимания; повысится успеваемость в школе; ребенок научится пересказывать; тренировка памяти; формирование навыка быстрого чтения; занятия по 30 минут в день.', 'add full_description'),
('7', '00000010', 'Зверье Моё / Когтеточка-столбик "Зверьё Моё" с полкой, джут, крем-брюле, 40*40*60 см', '904', 'Многофункциональный комплекс Зверье Мое: поточить, поиграть, полежать. Преимущества: - Джут - натуральное текстильное волокно, изготавливаемое из растений одноимённого рода; - серхняя полочка с бортиком, обтянутая премиальным мехом, подарит чудесные минуты отдыха; - пропитка - это наше собственное ноу-хау, неуловимое для человека и притягательное для кошки; - подвесная игрушка не оставит равнодушным питомца; в связи с отзывами о том, что шарик быстро отрывается, подвесную игрушку сделали из джута; - сборка за 20 секунд без инструментов и дополнительных деталей. Когтеточка-столбик "Зверье Мое" поможет сохранить мебель и ковры в доме в целостности. Во время царапания кошка выполняет сразу три жизненно важных процесса: стачивает отросшие когти, одновременно затачивая их, метит территорию и выполняет гимнастику тела.', 'add full_description'),
('5', '00000011', 'ТВОЕ / Платье', '999', 'Размер на модели: S. *Параметры в описании указаны для размера: S.', 'add full_description');

INSERT INTO products_images (product_id, path)
VALUES
(1, 'products/cg-1.jpg'),
(2, 'products/cg-2.jpg'),
(3, 'products/cg-3.jpg'),
(4, 'products/cg-4.jpg'),
(5, 'products/cg-5.jpg'),
(6, 'products/cg-6.jpg'),
(7, 'products/cg-7.jpg'),
(8, 'products/cg-8.jpg'),
(9, 'products/cg-9.jpg'),
(10, 'products/cg-10.jpg'),
(11, 'products/cg-1.jpg');

INSERT INTO `order_statuses` (`title`, `description`) VALUES
('Created', 'Создан: Заказ сформирован пользователем и сохранен в списке заказов'),
('Accepted to work', 'Принят в работу: Заказ обрабатывается'),
('Transferred to delivery', 'Передан в доставку: Заказ скомплектован и ждет доставки'),
('Paid', 'Оплачен: Заказ оплачен'),
('Delivered', 'Доставлен: Заказ получен пользователем'),
('Completed', 'Выполнен(Закрыт): Заказ выполнен полностью(товар доставлен и оплата получена)'),
('Canceled', 'Отменен: Заказ отменен');

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `roles` (`name`, `description`) VALUES
('ROLE_SUPERADMIN', 'Главный администратор интернет-магазина. Доступ ко всем разделам магазина и всем операциям'),
('ROLE_ADMIN', 'Администратор интернет-магазина. Доступ ко всем разделам магазина. Нет прав на создание и изменение администраторов'),
('ROLE_EMPLOYEE', 'Сотрудник организации. Общий уровень доступа к внутренним ресурсам интернет-магазина. Нет доступа к пользователям'),
('ROLE_MANAGER', 'Менеджер интернет-магазина. Доступ к заказам в магазине');

INSERT INTO `addresses` (`country`, `city`, `address`)
VALUES
('USA', 'New York', '18a Diagon Alley'),
('RF', 'Moscow', '12 Lenina');

