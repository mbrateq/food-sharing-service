INSERT INTO roles (role_id, role_name) VALUES (DEFAULT, 'ROLE_USER');
INSERT INTO roles (role_id, role_name) VALUES (DEFAULT,'ROLE_ADMIN');

INSERT INTO users (user_id, enabled, password, username, phone)
VALUES (DEFAULT, true,'$2a$10$lTBRa6LF1UR5yoL7K7bIzOkWdn/BFB4h7hxy97CCcc/sKyHfDMLPS', 'user','555555555');
INSERT INTO users (user_id, enabled, password, username, phone)
VALUES (DEFAULT, true,'$2a$10$lTBRa6LF1UR5yoL7K7bIzOkWdn/BFB4h7hxy97CCcc/sKyHfDMLPS', 'admin','555555555');
INSERT INTO users (user_id, enabled, password, username, phone)
VALUES (DEFAULT, true,'$2a$10$lTBRa6LF1UR5yoL7K7bIzOkWdn/BFB4h7hxy97CCcc/sKyHfDMLPS', 'super','555555555');
INSERT INTO users (user_id, enabled, password, username, phone)
VALUES (DEFAULT, true,'$2a$10$lTBRa6LF1UR5yoL7K7bIzOkWdn/BFB4h7hxy97CCcc/sKyHfDMLPS', 'none','555555555');


INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO users_roles (user_id, role_id) VALUES (3, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (3, 2);