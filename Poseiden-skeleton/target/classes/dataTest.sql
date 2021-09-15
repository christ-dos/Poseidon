DROP DATABASE IF EXISTS demotest;

CREATE DATABASE demotest;

USE demotest;

CREATE TABLE bid_list
(
    bid_list_id    tinyint(4)  AUTO_INCREMENT NOT NULL,
    account      VARCHAR(30) NOT NULL,
    type         VARCHAR(30) NOT NULL,
    bid_quantity  DOUBLE,
    ask_quantity  DOUBLE,
    bid          DOUBLE,
    ask          DOUBLE,
    benchmark    VARCHAR(125),
    bid_list_date  TIMESTAMP,
    commentary   VARCHAR(125),
    security     VARCHAR(125),
    status       VARCHAR(10),
    trader       VARCHAR(125),
    book         VARCHAR(125),
    creation_name VARCHAR(125),
    creation_date TIMESTAMP,
    revision_name VARCHAR(125),
    revision_date TIMESTAMP,
    deal_name     VARCHAR(125),
    deal_type     VARCHAR(125),
    source_list_id VARCHAR(125),
    side         VARCHAR(125),

    PRIMARY KEY (bid_list_id)
);

INSERT INTO bid_list(account, type, bid_quantity)
VALUES ('Account', 'Type', 10.0),
       ('Account2', 'Type2', 15.0);

CREATE TABLE Trade
(
    trade_id     tinyint(4)  NOT NULL AUTO_INCREMENT,
    account      VARCHAR(30) NOT NULL,
    type         VARCHAR(30) NOT NULL,
    buy_quantity  DOUBLE,
    sell_quantity DOUBLE,
    buy_price     DOUBLE,
    sell_price    DOUBLE,
    trade_date    TIMESTAMP,
    security     VARCHAR(125),
    status       VARCHAR(10),
    trader       VARCHAR(125),
    benchmark    VARCHAR(125),
    book         VARCHAR(125),
    creation_name VARCHAR(125),
    creation_date TIMESTAMP,
    revision_name VARCHAR(125),
    revision_date TIMESTAMP,
    deal_name     VARCHAR(125),
    deal_type     VARCHAR(125),
    source_list_id VARCHAR(125),
    side         VARCHAR(125),

    PRIMARY KEY (trade_id)
);

INSERT INTO trade(account, type, buy_quantity)
 VALUES('Account','Type',10.0),
       ('Account1','Type1',20.0) ;

CREATE TABLE curve_point
(
    Id           tinyint(4) NOT NULL AUTO_INCREMENT,
    curve_id      tinyint,
    as_of_date     TIMESTAMP,
    term         DOUBLE,
    value        DOUBLE,
    creation_date TIMESTAMP,

    PRIMARY KEY (Id)
);

INSERT INTO curve_point(curve_id, term, value) VALUES (10,14.0,16.0),
                                                      (20,24.0,26.0);

CREATE TABLE Rating
(
    Id           tinyint(4) NOT NULL AUTO_INCREMENT,
    moodys_rating VARCHAR(125),
    sand_p_rating  VARCHAR(125),
    fitch_rating  VARCHAR(125),
    order_number  tinyint,

    PRIMARY KEY (Id)
);
INSERT INTO rating(moodys_rating, sand_p_rating, fitch_rating, order_number)
VALUES ('MoodysRating','SandPRating','Firchrating',10),
       ('MoodysRating1','SandPRating1','Firchrating1',20);

CREATE TABLE rule_name
(
    Id          tinyint(4) NOT NULL AUTO_INCREMENT,
    name        VARCHAR(125),
    description VARCHAR(125),
    json        VARCHAR(125),
    template    VARCHAR(512),
    sql_str      VARCHAR(125),
    sql_part     VARCHAR(125),

    PRIMARY KEY (Id)
);
INSERT INTO rule_name(name, description, json, template, sql_str, sql_part) VALUES
('Name','Description','Json','Template','SqlStr','SqlPart'),
('Name1','Description1','Json1','Template1','SqlStr1','SqlPart1');

CREATE TABLE Users
(
    Id       tinyint(4) NOT NULL AUTO_INCREMENT,
    username VARCHAR(125),
    password VARCHAR(125),
    fullname VARCHAR(125),
    role     VARCHAR(125),

    PRIMARY KEY (Id)
);

INSERT INTO Users(fullname, username, password, role)
VALUES ('Administrator', 'admin', '$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa', 'ADMIN'),
       ('User', 'user', '$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa', 'USER');
