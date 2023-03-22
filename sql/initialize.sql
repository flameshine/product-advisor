DROP TABLE IF EXISTS product;

CREATE TABLE product(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(15) NOT NULL,
    price DECIMAL(15) NOT NULL,
    currency VARCHAR(5) NOT NULL,
    description VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (id),
    UNIQUE (name)
);

INSERT INTO product VALUES(1, 'Phone', 1500, 'USD', 'Modern smartphone');
INSERT INTO product VALUES(2, 'Tablet', 3000, 'USD', 'Cutting-edge tablet');
INSERT INTO product VALUES(3, 'Laptop', 4500, 'USD', 'Avant-garde laptop');