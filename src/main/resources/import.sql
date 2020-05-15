INSERT INTO roles (role_id, name) VALUES ('1', 'ROLE_ADMIN');
INSERT INTO roles (role_id, name) VALUES ('2', 'ROLE_USER');

INSERT INTO users (user_id, email, first_name, last_name, password) VALUES ('1', 'brena@gmail.com', 'Brena', 'Andring', '$2a$10$y9e6FPktlBFaUfJSisIs8ekXrp2rVOMFnz.JcBaiprMorAFdS1xWa');
INSERT INTO users (user_id, email, first_name, last_name, password) VALUES ('2', 'barnes@gmail.com', 'Barnes', 'Angel', '$2a$10$ietdK5wFLS8j6dLhfr731e/YfgURsAZa1oUVnbEOEl1cjDlHrSbLi');

INSERT INTO users_roles (user_id, role_id) VALUES ('1', '1');
INSERT INTO users_roles (user_id, role_id) VALUES ('2', '2');


INSERT INTO items (item_id, description, price, quantity, title) VALUES ('1', 'test', '1', '2', 'test');
INSERT INTO items (item_id, description, price, quantity, title) VALUES ('2', 'test', '1', '2', 'test 2');
INSERT INTO items (item_id, description, price, quantity, title) VALUES ('3', 'test', '1', '2', 'test 3');

