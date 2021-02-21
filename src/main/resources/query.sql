CREATE TABLE `product` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `carton_price` decimal(19,2) DEFAULT NULL,
                           `carton_size` int DEFAULT NULL,
                           `discount` double DEFAULT NULL,
                           `discount_level` int DEFAULT NULL,
                           `name` varchar(255) DEFAULT NULL,
                           `variable_cost_ratio` double DEFAULT NULL,
                           PRIMARY KEY (`id`)
);
