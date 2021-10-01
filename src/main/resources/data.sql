-- INSERT ROLES
INSERT INTO roles (role_id, role_name) VALUES (1, 'ROLE_USER');
INSERT INTO roles (role_id, role_name) VALUES (2,'ROLE_ADMIN');

-- INSERT USERS
INSERT INTO users (user_id, enabled, password, username)
VALUES (1, true,'$2a$10$mvRK1IH0TQ.dA2zy3.JwRezg2pGp7mC89bv4vhlE3MyI.kfo41pwK', 'test1');
INSERT INTO users (user_id, enabled, password, username)
VALUES (2, true,'$2a$10$SO4Yjd5sTrxP5.Eie./XY.SDuIq2XN/yhMe2Tg00ajfJ6cM8.EK2.', 'test2');
INSERT INTO users (user_id, enabled, password, username)
VALUES (3, true,'$2a$10$FkAlSkJ4N26vo6OXmiuff.HRzunHvTnFGMrajXQxxbg8Uj6Qt9gBa', 'test3');
INSERT INTO users (user_id, enabled, password, username)
VALUES (4, true,'$2a$10$pogC1lKN7NXJMXyRDXyLzOwjqxjsddMcRb0PrUwSmzM9qp0B7QYMu', 'test4');


INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO users_roles (user_id, role_id) VALUES (3, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (3, 2);