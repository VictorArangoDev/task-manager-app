-- seed.sql
-- =========================================
-- INSERT TABLA ROLE
-- =========================================
INSERT INTO role (name, active, created_at, updated_at) VALUES
('ADMIN', true, now(), now()),
('SCRUM', true, now(), now()),
('MEMBER', true, now(), now());

-- =========================================
-- TABLA USER
-- =========================================INSERT INTO "user"
(username, name, document, password, id_role, image, estado, created_at, updated_at)
VALUES

-- ADMIN
('admin1', 'Victor Admin', '100000001',
'$2a$10$Dow1xRjJ9xgXgK6XK6XK6eF6yYwWwYwWwYwWwYwWwYwWwYwWwYwWw', 
1, null, true, now(), now()),

-- SCRUM
('scrum1', 'Isabella Scrum', '100000002',
'$2a$10$Dow1xRjJ9xgXgK6XK6XK6eF6yYwWwYwWwYwWwYwWwYwWwYwWwYwWw', 
2, null, true, now(), now()),

('scrum2', 'Andres Scrum', '100000003',
'$2a$10$Dow1xRjJ9xgXgK6XK6XK6eF6yYwWwYwWwYwWwYwWwYwWwYwWwYwWw', 
2, null, true, now(), now()),

-- MEMBERS
('member1', 'Juan Perez', '100000004',
'$2a$10$Dow1xRjJ9xgXgK6XK6XK6eF6yYwWwYwWwYwWwYwWwYwWwYwWwYwWw', 
3, null, true, now(), now()),

('member2', 'Ana Gomez', '100000005',
'$2a$10$Dow1xRjJ9xgXgK6XK6XK6eF6yYwWwYwWwYwWwYwWwYwWwYwWwYwWw', 
3, null, true, now(), now()),

('member3', 'David Martinez', '100000006',
'$2a$10$Dow1xRjJ9xgXgK6XK6XK6eF6yYwWwYwWwYwWwYwWwYwWwYwWwYwWw', 
3, null, true, now(), now()),

('member4', 'Sofia Ramirez', '100000007',
'$2a$10$Dow1xRjJ9xgXgK6XK6XK6eF6yYwWwYwWwYwWwYwWwYwWwYwWwYwWw', 
3, null, true, now(), now()),

('member5', 'Andres Lopez', '100000008',
'$2a$10$Dow1xRjJ9xgXgK6XK6XK6eF6yYwWwYwWwYwWwYwWwYwWwYwWwYwWw', 
3, null, true, now(), now()),

('member6', 'Camila Torres', '100000009',
'$2a$10$Dow1xRjJ9xgXgK6XK6XK6eF6yYwWwYwWwYwWwYwWwYwWwYwWwYwWw', 
3, null, true, now(), now()),

('member7', 'Mateo Hernandez', '100000010',
'$2a$10$Dow1xRjJ9xgXgK6XK6XK6eF6yYwWwYwWwYwWwYwWwYwWwYwWwYwWw', 
3, null, true, now(), now());