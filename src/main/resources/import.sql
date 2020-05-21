INSERT INTO roles (role_id, name) VALUES ('1', 'ROLE_ADMIN');
INSERT INTO roles (role_id, name) VALUES ('2', 'ROLE_USER');

INSERT INTO users (user_id, email, first_name, last_name, password) VALUES ('1', 'brena@gmail.com', 'Brena', 'Andring', '$2a$10$y9e6FPktlBFaUfJSisIs8ekXrp2rVOMFnz.JcBaiprMorAFdS1xWa');
INSERT INTO users (user_id, email, first_name, last_name, password) VALUES ('2', 'barnes@gmail.com', 'Barnes', 'Angel', '$2a$10$ietdK5wFLS8j6dLhfr731e/YfgURsAZa1oUVnbEOEl1cjDlHrSbLi');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);

INSERT INTO items (item_id, description, price, quantity, title) VALUES ('1', 'Orange 32 oz water bottle', '25', '5', 'Nalgene Water Bottle');
INSERT INTO items (item_id, description, price, quantity, title) VALUES ('2', 'The best smartphone on the market', '1000', '5', 'Apple iPhone 11 Pro Max');
INSERT INTO items (item_id, description, price, quantity, title) VALUES ('3', 'A mouse loved by gamers', '55', '5', 'Razer Maramba Mouse');


INSERT INTO photos (photo_id, file_name, item_id) VALUES ('1', '7e0bb064-7e40-42c1-9ca9-c3db4ae5b601.jpg', '3');
INSERT INTO photos (photo_id, file_name, item_id) VALUES ('2', '665bc362-4129-4daa-a4e4-cb18bf58ad92.jpg', '2');
INSERT INTO photos (photo_id, file_name, item_id) VALUES ('3', 'f4e9d742-f025-48b2-97bb-d45f616a3a00.jpg', '1');