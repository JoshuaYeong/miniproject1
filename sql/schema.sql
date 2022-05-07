DROP DATABASE IF EXISTS streaming;

CREATE DATABASE streaming;

USE streaming;

CREATE TABLE user (
    uid int NOT NULL AUTO_INCREMENT,
    username varchar(128) NOT NULL,
    email varchar(128) NOT NULL,

    PRIMARY KEY(uid)
);

CREATE TABLE show (
	sid int NOT NULL AUTO_INCREMENT,
	name varchar(256) NOT NULL,
    type varchar(16) NOT NULL,
    id int,
	year int NOT NULL,
	image_url varchar(512),

    uid int,

    PRIMARY KEY(sid),

    CONSTRAINT fk_uid
        FOREIGN KEY(uid)
            REFERENCES user(uid)
);

