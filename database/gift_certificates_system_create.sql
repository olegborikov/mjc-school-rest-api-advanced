SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE SCHEMA IF NOT EXISTS `gift_certificates_system` DEFAULT CHARACTER SET utf8;
USE `gift_certificates_system`;

CREATE TABLE IF NOT EXISTS `gift_certificates_system`.`gift_certificate`
(
    `gift_certificate_id`          BIGINT        NOT NULL AUTO_INCREMENT,
    `gift_certificate_name`        VARCHAR(100)  NOT NULL,
    `description`                  VARCHAR(1000) NOT NULL,
    `gift_certificate_price`       DECIMAL(8, 2) NOT NULL,
    `duration`                     INT           NOT NULL,
    `gift_certificate_create_date` DATETIME      NOT NULL,
    `last_update_date`             DATETIME      NOT NULL,
    PRIMARY KEY (`gift_certificate_id`)
)
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `gift_certificates_system`.`tag`
(
    `tag_id`   BIGINT              NOT NULL AUTO_INCREMENT,
    `tag_name` VARCHAR(100) BINARY NOT NULL,
    PRIMARY KEY (`tag_id`)
)
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `gift_certificates_system`.`gift_certificate_has_tag`
(
    `gift_certificate_id_fk` BIGINT NOT NULL,
    `tag_id_fk`              BIGINT NOT NULL,
    PRIMARY KEY (`gift_certificate_id_fk`, `tag_id_fk`),
    INDEX `fk_gift_certificate_has_tag_tag1_idx` (`tag_id_fk` ASC) VISIBLE,
    INDEX `fk_gift_certificate_has_tag_gift_certificate_idx` (`gift_certificate_id_fk` ASC) VISIBLE,
    CONSTRAINT `fk_gift_certificate_has_tag_gift_certificate`
        FOREIGN KEY (`gift_certificate_id_fk`)
            REFERENCES `gift_certificates_system`.`gift_certificate` (`gift_certificate_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_gift_certificate_has_tag_tag1`
        FOREIGN KEY (`tag_id_fk`)
            REFERENCES `gift_certificates_system`.`tag` (`tag_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `gift_certificates_system`.`user`
(
    `user_id`   BIGINT       NOT NULL AUTO_INCREMENT,
    `user_name` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`user_id`)
)
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `gift_certificates_system`.`order`
(
    `order_id`               BIGINT        NOT NULL AUTO_INCREMENT,
    `order_price`            DECIMAL(8, 2) NOT NULL,
    `order_create_date`      DATETIME      NOT NULL,
    `user_id_fk`             BIGINT        NOT NULL,
    `gift_certificate_id_fk` BIGINT        NOT NULL,
    PRIMARY KEY (`order_id`),
    INDEX `fk_order_user_account1_idx` (`user_id_fk` ASC) VISIBLE,
    INDEX `fk_order_gift_certificate1_idx` (`gift_certificate_id_fk` ASC) VISIBLE,
    CONSTRAINT `fk_order_user_account1`
        FOREIGN KEY (`user_id_fk`)
            REFERENCES `gift_certificates_system`.`user` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_order_gift_certificate1`
        FOREIGN KEY (`gift_certificate_id_fk`)
            REFERENCES `gift_certificates_system`.`gift_certificate` (`gift_certificate_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
