-- # Script MySql
-- # CREATE TABLE payment (
-- #     id bigint(20) NOT NULL AUTO_INCREMENT,
-- #     amount decimal(19,2) NOT NULL,
-- #     name varchar(100) DEFAULT NULL,
-- #     number varchar(19) DEFAULT NULL,
-- #     expiration varchar(7) DEFAULT NULL,
-- #     code varchar(3) DEFAULT NULL,
-- #     status varchar(255) NOT NULL,
-- #     order_id bigint(20) NOT NULL,
-- #     type_payment_Id bigint(20) NOT NULL,
-- #     PRIMARY KEY (id)
-- # );
-- # Script postgresql
CREATE TABLE payment (
     id bigserial NOT NULL,
     amount decimal(19,2) NOT NULL,
     name varchar(100) DEFAULT NULL,
     number varchar(19) DEFAULT NULL,
     expiration varchar(7) DEFAULT NULL,
     code varchar(3) DEFAULT NULL,
     status varchar(255) NOT NULL,
     order_id integer NOT NULL,
     type_payment_Id integer NOT NULL,
     CONSTRAINT payment_pk PRIMARY KEY (id) );
