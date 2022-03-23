CREATE DATABASE IF NOT EXISTS `spring-boot-example`;

DROP TABLE IF EXISTS `spring-boot-example`.`errors`;

CREATE TABLE `spring-boot-example`.`errors`
(
    `id`        INT          NOT NULL AUTO_INCREMENT,
    `requestId` VARCHAR(255) NOT NULL,
    `errorCode` int          NOT NULL,
    CONSTRAINT `ERRORS_PRIMARY_KEY` PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 1
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;
