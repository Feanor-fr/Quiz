INSERT INTO roles (role_name)
VALUES ('USER');

INSERT INTO users (email, user_name, password, role)
VALUES
    (
        'anna@test.com',
        'Anna',
        '$2a$10$7EqJtq98hPqEX7fNZaFWoO5rKZ0qKXqQyV2pAfoyz1ME3iAFHfQ6W',
        (SELECT id FROM roles WHERE role_name = 'USER')
    ),
    (
        'bob@test.com',
        'Bob',
        '$2a$10$7EqJtq98hPqEX7fNZaFWoO5rKZ0qKXqQyV2pAfoyz1ME3iAFHfQ6W',
        (SELECT id FROM roles WHERE role_name = 'USER')
    );

INSERT INTO categories (name)
VALUES
    ('Природа'),
    ('Животные');

INSERT INTO quizzes (title, description, category_id, creator)
VALUES
    (
        'Окружающий мир',
        'Очень простые вопросы',
        (SELECT id FROM categories WHERE name = 'Природа'),
        'anna@test.com'
    ),
    (
        'Мир животных',
        'Кто есть кто',
        (SELECT id FROM categories WHERE name = 'Животные'),
        'bob@test.com'
    );

INSERT INTO questions (quiz_id, text)
VALUES
    (
        (SELECT id FROM quizzes WHERE title = 'Окружающий мир'),
        'Какого цвета небо?'
    ),
    (
        (SELECT id FROM quizzes WHERE title = 'Окружающий мир'),
        'Сколько дней в неделе?'
    ),
    (
        (SELECT id FROM quizzes WHERE title = 'Мир животных'),
        'Кто говорит "мяу"?'
    ),
    (
        (SELECT id FROM quizzes WHERE title = 'Мир животных'),
        'Кто самый большой на суше?'
    );

INSERT INTO options (question_id, text, is_correct)
VALUES

((SELECT id FROM questions WHERE text = 'Какого цвета небо?'), 'Синее', true),
((SELECT id FROM questions WHERE text = 'Какого цвета небо?'), 'Зелёное', false),
((SELECT id FROM questions WHERE text = 'Какого цвета небо?'), 'Красное', false),
((SELECT id FROM questions WHERE text = 'Какого цвета небо?'), 'Чёрное', false),

((SELECT id FROM questions WHERE text = 'Сколько дней в неделе?'), '7', true),
((SELECT id FROM questions WHERE text = 'Сколько дней в неделе?'), '5', false),
((SELECT id FROM questions WHERE text = 'Сколько дней в неделе?'), '10', false),
((SELECT id FROM questions WHERE text = 'Сколько дней в неделе?'), '3', false),

((SELECT id FROM questions WHERE text = 'Кто говорит "мяу"?'), 'Кошка', true),
((SELECT id FROM questions WHERE text = 'Кто говорит "мяу"?'), 'Собака', false),
((SELECT id FROM questions WHERE text = 'Кто говорит "мяу"?'), 'Корова', false),
((SELECT id FROM questions WHERE text = 'Кто говорит "мяу"?'), 'Лошадь', false),

((SELECT id FROM questions WHERE text = 'Кто самый большой на суше?'), 'Слон', true),
((SELECT id FROM questions WHERE text = 'Кто самый большой на суше?'), 'Кошка', false),
((SELECT id FROM questions WHERE text = 'Кто самый большой на суше?'), 'Волк', false),
((SELECT id FROM questions WHERE text = 'Кто самый большой на суше?'), 'Заяц', false);

INSERT INTO quiz_results (user_email, quiz_id, score, rate)
VALUES
    (
        'anna@test.com',
        (SELECT id FROM quizzes WHERE title = 'Мир животных'),
        1,
        3
    ),
    (
        'bob@test.com',
        (SELECT id FROM quizzes WHERE title = 'Окружающий мир'),
        2,
        5
    );





