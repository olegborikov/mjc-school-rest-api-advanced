INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('Spa house', 'Very good and not expensive', 100.1, 1, '2020-08-02 12:00:00', '2021-01-02 16:00:00');
INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('Cinema', 'Best cinema in the city', 50, 1, '2019-01-01 00:00:00', '2020-01-01 12:10:00');
INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('Travel to German', 'You will like it', 1000, 5, '2015-01-12 11:00:00', '2021-01-01 20:00:00');
INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('Museum', 'It is nice', 75, 2, '2018-05-16 12:00:00', '2019-01-01 21:00:00');

INSERT INTO tag (name)
VALUES ('home');
INSERT INTO tag (name)
VALUES ('school');
INSERT INTO tag (name)
VALUES ('work');
INSERT INTO tag (name)
VALUES ('holiday');

INSERT INTO gift_certificate_has_tag(gift_certificate_id, tag_id)
VALUES (1, 1);
INSERT INTO gift_certificate_has_tag(gift_certificate_id, tag_id)
VALUES (2, 3);
INSERT INTO gift_certificate_has_tag(gift_certificate_id, tag_id)
VALUES (1, 2);
INSERT INTO gift_certificate_has_tag(gift_certificate_id, tag_id)
VALUES (2, 2);

INSERT INTO user(name)
VALUES ('Oleg');
INSERT INTO user(name)
VALUES ('Gleb');
INSERT INTO user(name)
VALUES ('Ivan');
