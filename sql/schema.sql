DROP DATABASE IF EXISTS motionpicture;

CREATE DATABASE motionpicture;

USE motionpicture;

CREATE TABLE users (
    username varchar(128) UNIQUE NOT NULL,
    password varchar(128) NOT NULL,

    PRIMARY KEY(username)
);

CREATE TABLE shows (
	sid int NOT NULL AUTO_INCREMENT,
	name varchar(256) NOT NULL,
    type varchar(16) NOT NULL,
    id int,
	year int NOT NULL,
	image_url varchar(512),

    username varchar(128),

    PRIMARY KEY(sid),

    CONSTRAINT fk_username
        FOREIGN KEY(username)
            REFERENCES users(username)
);

