CREATE TABLE user (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(45) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    hash_password VARCHAR(255),
    zip_code VARCHAR(10) NOT NULL,
    is_active TINYINT(1) DEFAULT 1
);