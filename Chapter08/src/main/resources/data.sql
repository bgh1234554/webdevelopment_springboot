INSERT INTO member (id, name) VALUES (1, 'name 1')
INSERT INTO member (id, name) VALUES (2, 'name 2')
INSERT INTO member (id, name) VALUES (3, 'name 3')
-- 작은따옴표 안하면 오류난다.
INSERT INTO article (title,content,created_at,updated_at) VALUES ('제목 1', '내용 1', now(), now())
INSERT INTO article (title,content,created_at,updated_at) VALUES ('제목 2', '내용 2', now(), now())
INSERT INTO article (title,content,created_at,updated_at) VALUES ('제목 3', '내용 3', now(), now())