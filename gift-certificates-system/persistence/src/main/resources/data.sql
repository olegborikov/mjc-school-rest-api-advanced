USE gift_certificates_system;

INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date)
VALUES ('Horse ride', 'Horseback ride for lovers - a Hollywood-style date', 999.99, 2, '2017-05-22 12:46:31',
        '2020-03-01 16:11:07');
INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date)
VALUES ('quest Space', 'The country first team quest in virtual reality Space', 1500, 1, '2019-08-10 09:27:26',
        '2020-10-15 09:03:12');
INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date)
VALUES ('Yoga', 'Relaxing yoga in hammocks', 500, 8, '2015-07-01 13:44:09', '2021-01-03 17:41:17');
INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date)
VALUES ('Diving with dolphins',
        'Diving with dolphins - an incredible meeting with the underwater world and its inhabitants', 1999.99, 1,
        '2020-03-20 16:34:49', '2020-12-01 16:42:59');
INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date)
VALUES ('Jeep Quest', 'Jeep quest - cool off-road and interesting riddles', 2500, 3, '2021-01-10 12:22:53',
        '2021-01-11 12:15:51');
INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date)
VALUES ('Excursion', 'Excursion immersion in the gentry culture with a lunch of national dishes', 2999.99, 5,
        '2018-11-18 17:33:42', '2020-09-01 10:00:23');

INSERT INTO tag(name)
VALUES ('Sport');
INSERT INTO tag(name)
VALUES ('Quest');
INSERT INTO tag(name)
VALUES ('Animals');
INSERT INTO tag(name)
VALUES ('Travel');

INSERT INTO gift_certificate_has_tag(gift_certificate_id, tag_id)
VALUES (1, 1);
INSERT INTO gift_certificate_has_tag(gift_certificate_id, tag_id)
VALUES (1, 3);
INSERT INTO gift_certificate_has_tag(gift_certificate_id, tag_id)
VALUES (1, 4);
INSERT INTO gift_certificate_has_tag(gift_certificate_id, tag_id)
VALUES (2, 2);
INSERT INTO gift_certificate_has_tag(gift_certificate_id, tag_id)
VALUES (3, 1);
INSERT INTO gift_certificate_has_tag(gift_certificate_id, tag_id)
VALUES (4, 1);
INSERT INTO gift_certificate_has_tag(gift_certificate_id, tag_id)
VALUES (4, 3);
INSERT INTO gift_certificate_has_tag(gift_certificate_id, tag_id)
VALUES (5, 2);
INSERT INTO gift_certificate_has_tag(gift_certificate_id, tag_id)
VALUES (5, 4);
INSERT INTO gift_certificate_has_tag(gift_certificate_id, tag_id)
VALUES (6, 4);

INSERT INTO user(name)
VALUES ('Oleg');
INSERT INTO user(name)
VALUES ('Gleb');
INSERT INTO user(name)
VALUES ('Ivan');
INSERT INTO user(name)
VALUES ('Alex');
