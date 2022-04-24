INSERT INTO users (user_id, enabled, password, username)
VALUES (DEFAULT, true,'$2a$10$lTBRa6LF1UR5yoL7K7bIzOkWdn/BFB4h7hxy97CCcc/sKyHfDMLPS', 'test1');

INSERT INTO notices (notice_id, text_content, expiration_date, publication_date, title, author)
VALUES (DEFAULT,'test', '2021-10-01', '2021-10-01 01:00:00','test', 1);