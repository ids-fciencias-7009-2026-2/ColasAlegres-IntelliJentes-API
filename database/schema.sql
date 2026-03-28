CREATE DATABASE IF NOT EXISTS colasalegresdb;
USE colasalegresdb;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255),
    zip_code VARCHAR(10) NOT NULL,
    is_active TINYINT(1) DEFAULT 1,
    token VARCHAR(255),
    token_expires_at DATETIME(6),
    PRIMARY KEY (id)
);