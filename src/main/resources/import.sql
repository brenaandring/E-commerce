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


INSERT INTO photos (photo_id, file_name, item_id) VALUES ('1', '772f721c-24f1-4f0b-a332-2d04b68ed544.png', '4');
INSERT INTO photos (photo_id, file_name, item_id) VALUES ('2', '14d94d51-6623-41de-909f-84d1cda25bea.png', '3');
INSERT INTO photos (photo_id, file_name, item_id) VALUES ('3', 'dfaae724-5e4f-4252-af12-b452f5ef50f0.png', '2');
INSERT INTO photos (photo_id, file_name, item_id) VALUES ('4', '6382465f-615e-4947-b107-f5f5afd4ea92.png', '1');