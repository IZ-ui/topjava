DELETE
FROM user_roles;
DELETE
FROM meals;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (datetime, description, calories, user_id)
VALUES (timestamp'2020-01-31 20:00:00 CET', 'uzhinUser', 410, 100000),
       (timestamp'2020-01-31 13:00:00 CET', 'obedUser', 500, 100000),
       (timestamp'2020-01-31 10:00:00 CET', 'zavtrakUser', 1000, 100000),
       (timestamp'2020-01-30 20:00:00 CET', 'uzhinUser', 500, 100000),
       (timestamp'2020-01-30 13:00:00 CET', 'obedUser', 1000, 100000),
       (timestamp'2020-01-30 10:00:00 CET', 'zavtrakUser', 500, 100000),
       (timestamp'2020-01-30 00:00:00 CET', 'edanagraniUser', 100, 100000),
       (timestamp'2020-01-30 13:00:00 CET', 'obedAdmin', 500, 100001),
       (timestamp'2020-01-30 19:00:00 CET', 'uzhinAdmin', 1000, 100001);
