CREATE SCHEMA gift_certificates_system CHARACTER SET utf8 COLLATE utf8_bin;

USE gift_certificates_system;

CREATE TABLE gift_certificate
(
    gift_certificate_id          BIGINT        NOT NULL AUTO_INCREMENT,
    gift_certificate_name        VARCHAR(100)  NOT NULL,
    description                  VARCHAR(1000) NOT NULL,
    gift_certificate_price       DECIMAL(8, 2) NOT NULL,
    duration                     INT           NOT NULL,
    gift_certificate_create_date DATETIME      NOT NULL,
    last_update_date             DATETIME      NOT NULL,
    PRIMARY KEY (gift_certificate_id)
) ENGINE = InnoDB;

CREATE TABLE tag
(
    tag_id   BIGINT       NOT NULL AUTO_INCREMENT,
    tag_name VARCHAR(100) NOT NULL,
    PRIMARY KEY (tag_id)
) ENGINE = InnoDB;

CREATE TABLE gift_certificate_has_tag
(
    gift_certificate_id_fk BIGINT NOT NULL,
    tag_id_fk              BIGINT NOT NULL,
    PRIMARY KEY (gift_certificate_id_fk, tag_id_fk),
    INDEX fk_gift_certificate_has_tag_tag1_idx (tag_id_fk ASC) VISIBLE,
    INDEX fk_gift_certificate_has_tag_gift_certificate_idx (gift_certificate_id_fk ASC) VISIBLE,
    CONSTRAINT fk_gift_certificate_has_tag_gift_certificate
        FOREIGN KEY (gift_certificate_id_fk)
            REFERENCES gift_certificates_system.gift_certificate (gift_certificate_id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT fk_gift_certificate_has_tag_tag1
        FOREIGN KEY (tag_id_fk)
            REFERENCES gift_certificates_system.tag (tag_id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
) ENGINE = InnoDB;

CREATE TABLE user
(
    user_id   BIGINT       NOT NULL AUTO_INCREMENT,
    user_name VARCHAR(100) NOT NULL,
    PRIMARY KEY (user_id)
) ENGINE = InnoDB;

CREATE TABLE gift_certificate_order
(
    gift_certificate_order_id          BIGINT        NOT NULL AUTO_INCREMENT,
    gift_certificate_order_price       DECIMAL(8, 2) NOT NULL,
    gift_certificate_order_create_date DATETIME      NOT NULL,
    user_id_fk                         BIGINT        NOT NULL,
    gift_certificate_id_fk             BIGINT        NOT NULL,
    PRIMARY KEY (gift_certificate_order_id),
    INDEX fk_order_user_account1_idx (user_id_fk ASC) VISIBLE,
    INDEX fk_order_gift_certificate1_idx (gift_certificate_id_fk ASC) VISIBLE,
    CONSTRAINT fk_order_user_account1
        FOREIGN KEY (user_id_fk)
            REFERENCES gift_certificates_system.user (user_id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT fk_order_gift_certificate1
        FOREIGN KEY (gift_certificate_id_fk)
            REFERENCES gift_certificates_system.gift_certificate (gift_certificate_id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
) ENGINE = InnoDB;