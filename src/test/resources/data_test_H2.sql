CREATE SCHEMA IF NOT EXISTS module_4;

CREATE TABLE IF NOT EXISTS module_4.gift_certificate
(
    id                SERIAL         NOT NULL,
    createdBy         VARCHAR(300)   NOT NULL,
    create_date_audit timestamp      NOT NULL,
    lastmodifiedby    VARCHAR(300)   NOT NULL,
    update_date_audit timestamp      NOT NULL,
    create_date       timestamp      NOT NULL,
    description       TEXT           NULL DEFAULT NULL,
    duration          INT            NOT NULL,
    is_active         boolean        NOT NULL,
    last_update_date  timestamp      NOT NULL,
    name              VARCHAR(300)   NOT NULL,
    price             DECIMAL(10, 2) NOT NULL
);


INSERT INTO module_4.gift_certificate (id, createdBy, create_date_audit, lastmodifiedby,
                                       update_date_audit,
                                       create_date, description, duration, is_active,
                                       last_update_date, name, price)
VALUES (1, 'null', '2021-10-30T10:10:10', 'null', '2021-09-30T10:10:10', '2021-10-30T10:10:10',
        'certificate of swimming pool', 31, true, '2021-09-30T10:10:10',
        'Visiting the pool',
        '20.5'),
       (2, 'null', '2021-10-30T10:10:10', 'null', '2021-09-30T10:10:10', '2021-10-30T10:10:10',
        'certificate of SPA', 40, true, '2021-09-30T10:10:10',
        'Visiting the spa', '50.0'),
       (3, 'null', '2021-10-30T10:10:10', 'null', '2021-09-30T10:10:10', '2021-10-30T10:10:10',
        'Certificate of Virtual Reality games', 10, true, '2021-09-30T10:10:10',
        'Visiting the Virtual Reality Place', '30.0'),
       (4, 'null', '2021-10-30T10:10:10', 'null', '2021-09-30T10:10:10', '2021-10-30T10:10:10',
        'Certificate of computer club', 10, true, '2021-09-30T10:10:10',
        'back to 19xx', '20.0'),
       (5, 'null', '2021-10-30T10:10:10', 'null', '2021-09-30T10:10:10', '2021-10-30T10:10:10',
        'Certificate of MASSAGE', 30, true, '2021-09-30T10:10:10',
        'Visiting the PLACE OF MASSAGE KINGS', '60.5'),
       (6, 'null', '2021-10-30T10:10:10', 'null', '2021-09-30T10:10:10', '2021-10-30T10:10:10',
        'Certificate of fitness', 90, true, '2021-09-30T10:10:10',
        'WORLD OF FITNESS', '220'),
       (7, 'null', '2021-10-30T10:10:10', 'null', '2021-09-30T10:10:10', '2021-10-30T10:10:10',
        'certificate of some food', 7, true, '2021-09-30T10:10:10',
        'You can buy some food for your cat', '15'),
       (8, 'null', '2021-10-30T10:10:10', 'null', '2021-09-30T10:10:10', '2021-10-30T10:10:10',
        'Certificate of fitness place', 85, true, '2021-09-30T10:10:10',
        'Make your body become more slimmer than before', '250');

CREATE TABLE IF NOT EXISTS module_4.tag
(
    id                SERIAL       NOT NULL,
    createdBy         VARCHAR(300) NOT NULL,
    create_date_audit timestamp    NOT NULL,
    lastmodifiedby    VARCHAR(300) NOT NULL,
    update_date_audit timestamp    NOT NULL,
    name              VARCHAR(300) NOT NULL
);

INSERT INTO module_4.tag
VALUES (1, 'null', '2021-10-30T10:10:10', 'null', '2021-09-30T10:10:10', 'SPA'),
       (2, 'null', '2021-10-30T10:10:10', 'null', '2021-09-30T10:10:10', 'games'),
       (3, 'null', '2021-10-30T10:10:10', 'null', '2021-09-30T10:10:10', 'massage'),
       (4, 'null', '2021-10-30T10:10:10', 'null', '2021-09-30T10:10:10', 'swimmingPool'),
       (5, 'null', '2021-10-30T10:10:10', 'null', '2021-09-30T10:10:10', 'fitness'),
       (6, 'null', '2021-10-30T10:10:10', 'null', '2021-09-30T10:10:10', 'buns'),
       (7, 'null', '2021-10-30T10:10:10', 'null', '2021-09-30T10:10:10', 'passage');

CREATE TABLE IF NOT EXISTS module_4.relationship_certificates_and_tags
(
    tag_id              BIGINT NOT NULL,
    gift_certificate_id BIGINT NOT NULL
);

INSERT INTO module_4.relationship_certificates_and_tags
VALUES (4, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (3, 5),
       (5, 6),
       (5, 7);

CREATE TABLE IF NOT EXISTS module_4.clientele
(
    id         SERIAL       NOT NULL,
    first_name VARCHAR(300) NOT NULL,
    last_name  VARCHAR(300) NOT NULL,
    login      VARCHAR(300) NOT NULL,
    password   VARCHAR(300) NOT NULL,
    role       VARCHAR(300) NOT NULL
);

INSERT INTO module_4.clientele
VALUES (1, 'Zhenya', 'Zhenya', 'ADMIN', '12345', 'ROLE_ADMIN'),
       (2, 'Sergey', 'Sergey', 'USER', '12345', 'ROLE_USER'),
       (3, 'Ura', 'Ura', 'USER', '12345', 'ROLE_USER');

CREATE TABLE IF NOT EXISTS module_4.gift_order
(
    id                SERIAL         NOT NULL,
    createdBy         VARCHAR(300)   NOT NULL,
    create_date_audit timestamp      NOT NULL,
    lastmodifiedby    VARCHAR(300)   NOT NULL,
    update_date_audit timestamp      NOT NULL,
    cost              DECIMAL(10, 2) NOT NULL,
    order_date        timestamp      NOT NULL,
    user_id           BIGINT         NOT NULL
);

INSERT INTO module_4.gift_order
VALUES (1, 'null', '2021-10-30T10:10:10', 'null', '2021-09-30T10:10:10', 5,
        '2021-10-30T10:10:10-03:00',
        1),
       (2, 'null', '2021-10-30T10:10:10', 'null', '2021-09-30T10:10:10', 50,
        '2021-10-30T10:10:10-03:00', 2);


CREATE TABLE module_4.order_has_gift_certificate
(
    gift_order_id       SERIAL NOT NULL,
    gift_certificate_id BIGINT NOT NULL
);

INSERT INTO module_4.order_has_gift_certificate
VALUES (1, 1),
       (2, 2);
