DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS product;

CREATE TABLE user(
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(15) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (id),
    UNIQUE (username),
    UNIQUE (email)
);

CREATE TABLE product(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(15) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price DECIMAL(15) NOT NULL,
    currency VARCHAR(5) NOT NULL,
    quantity INTEGER NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (id),
    UNIQUE (name)
);

INSERT INTO user VALUES(1, 'user', 'user@user.com', '$2a$10$GmsBUqRWMVp79KyJfZLD5eKyQ3XnAF5SN0ZG.Y8roKURKzgVh5vJG', 'user', 'user');

INSERT INTO product VALUES(1, 'Phone', 1500, 'USD', 'Modern smartphone', 5);
INSERT INTO product VALUES(2, 'Tablet', 3000, 'USD', 'Cutting-edge tablet', 5);
INSERT INTO product VALUES(3, 'Laptop', 4500, 'USD', 'Avant-garde laptop', 5);