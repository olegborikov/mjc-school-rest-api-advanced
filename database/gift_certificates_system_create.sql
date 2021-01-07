CREATE SCHEMA gift_certificates_system DEFAULT CHARACTER SET utf8;
USE gift_certificates_system;

CREATE TABLE gift_certificate
(
    gift_certificate_id   BIGINT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    gift_certificate_name VARCHAR(100)   NOT NULL,
    description           VARCHAR(1000)  NOT NULL,
    price                 DECIMAL(8, 2) NOT NULL,
    duration              INT            NOT NULL,
    create_date           DATETIME       NOT NULL,
    last_update_date      DATETIME       NOT NULL
);

CREATE TABLE tag
(
    tag_id   BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    tag_name VARCHAR(100) NOT NULL
);

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
);
