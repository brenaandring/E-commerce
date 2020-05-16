INSERT INTO roles (role_id, name)
VALUES ('1', 'ROLE_ADMIN');
INSERT INTO roles (role_id, name)
VALUES ('2', 'ROLE_USER');

INSERT INTO users (user_id, email, first_name, last_name, password)
VALUES ('1', 'brena@gmail.com', 'Brena', 'Andring', '$2a$10$y9e6FPktlBFaUfJSisIs8ekXrp2rVOMFnz.JcBaiprMorAFdS1xWa');
INSERT INTO users (user_id, email, first_name, last_name, password)
VALUES ('2', 'barnes@gmail.com', 'Barnes', 'Angel', '$2a$10$ietdK5wFLS8j6dLhfr731e/YfgURsAZa1oUVnbEOEl1cjDlHrSbLi');

INSERT INTO users_roles (user_id, role_id)
VALUES ('1', '1');
INSERT INTO users_roles (user_id, role_id)
VALUES ('2', '2');


INSERT INTO items (item_id, description, price, quantity, title)
VALUES ('1', 'Apples best phone with telephoto lens! ', '1100', '5', 'iPhone 11 Pro Max');
INSERT INTO items (item_id, description, price, quantity, title)
VALUES ('2', '32 oz orange water bottle for your adventures', '15', '5', 'Nalgene Water Bottle');
INSERT INTO items (item_id, description, price, quantity, title)
VALUES ('3', 'Boses best headphones! Ignore everyone around you', '300', '5', 'Noise Cancelling Headphones');
INSERT INTO items (item_id, description, price, quantity, title)
VALUES ('4', 'Apples coolest iPad and it comes with a pencil!', '900', '5', 'iPad Pro');
INSERT INTO items (item_id, description, price, quantity, title)
VALUES ('5', 'Apples 15 in Macbook Pro from late 2011', '1200', '5', 'Macbook Pro');
INSERT INTO items (item_id, description, price, quantity, title)
VALUES ('6', 'Razer mouse is the next best item on your desk', '55', '5', 'Razer Maramba Mouse');

