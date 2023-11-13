-- Вставка данных в таблицу пользователей
INSERT INTO users (name, username, password)
VALUES
    ('John Doe', 'johndoe@gmail.com', '$2a$10$Qmrdk6xIAWFDVM.TVk9Ye.rcWp8MCUDd9nSMl4kZDK4l4zCUC3Chu'),
    ('Mike Smith', 'mikesmith@yahoo.com', '$2a$10$Qmrdk6xIAWFDVM.TVk9Ye.rcWp8MCUDd9nSMl4kZDK4l4zCUC3Chu');

-- Вставка данных в таблицу задач
INSERT INTO tasks (title, description, status, expiration_date)
VALUES
    ('Buy cheese', 'Food', 'TODO', '2023-01-29 12:00:00'),
    ('Do homework', 'Math, Physics, Literature', 'IN_PROGRESS', '2023-01-31 09:00:00'),
    ('Clean rooms', 'null', 'DONE', null),
    ('Call Mike', 'Ask about meeting', 'TODO', '2023-02-01 08:00:00');

-- Вставка данных в таблицу связи пользователей и задач
INSERT INTO users_tasks (task_id, user_id)
VALUES
    (1, 2),
    (2, 2),
    (3, 2),
    (4, 1);

-- Вставка данных в таблицу связи пользователей и ролей
INSERT INTO users_roles (user_id, role)
VALUES
    (1, 'ROLE_ADMIN'),
    (1, 'ROLE_USER'),
    (2, 'ROLE_USER');
