INSERT INTO roles (role_id, name) VALUES ('1', 'ROLE_ADMIN');
INSERT INTO roles (role_id, name) VALUES ('2', 'ROLE_USER');

INSERT INTO users (user_id, email, first_name, last_name, password) VALUES ('1', 'brena@gmail.com', 'Brena', 'Andring', '$2a$10$y9e6FPktlBFaUfJSisIs8ekXrp2rVOMFnz.JcBaiprMorAFdS1xWa');
INSERT INTO users (user_id, email, first_name, last_name, password) VALUES ('2', 'barnes@gmail.com', 'Barnes', 'Angel', '$2a$10$ietdK5wFLS8j6dLhfr731e/YfgURsAZa1oUVnbEOEl1cjDlHrSbLi');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);

INSERT INTO items (item_id, description, price, quantity, title, user_id) VALUES ('1', 'face mask', '10', '5', 'AQUA', '1');
INSERT INTO items (item_id, description, price, quantity, title, user_id) VALUES ('2', 'face mask', '10', '5', 'RAINBOW', '1');
INSERT INTO items (item_id, description, price, quantity, title, user_id) VALUES ('3', 'face mask', '10', '5', 'DESERT', '1');
INSERT INTO items (item_id, description, price, quantity, title, user_id) VALUES ('4', 'face mask', '10', '5', 'SUMMER FESTIVAL', '1');


INSERT INTO photos (photo_id, file_name, item_id) VALUES ('1', 'imgonline-com-ua-CompressToSize-QHKwrszMz9.jpg', '4');
INSERT INTO photos (photo_id, file_name, item_id) VALUES ('2', 'imgonline-com-ua-CompressToSize-eNiL60H89AOZih.jpg', '3');
INSERT INTO photos (photo_id, file_name, item_id) VALUES ('3', 'imgonline-com-ua-CompressToSize-ZssOcYNChQ.jpg', '2');
INSERT INTO photos (photo_id, file_name, item_id) VALUES ('4', 'imgonline-com-ua-CompressToSize-6k2G26brzqBBwnp.jpg', '1');