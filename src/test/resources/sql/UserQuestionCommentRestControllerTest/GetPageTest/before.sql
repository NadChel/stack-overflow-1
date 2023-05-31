TRUNCATE TABLE permissions CASCADE;
TRUNCATE TABLE accounts CASCADE;
TRUNCATE TABLE questions CASCADE;
TRUNCATE TABLE tags CASCADE;
TRUNCATE TABLE question_comments CASCADE;

INSERT INTO permissions(id, name, created_date, modified_date)
VALUES (1, 'ADMIN', current_timestamp, current_timestamp),
       (2, 'MODERATOR', current_timestamp, current_timestamp),
       (3, 'USER', current_timestamp, current_timestamp);

INSERT INTO accounts(id, username, password, enabled, created_date, modified_date)
VALUES (1, 'mickey_m', '$2y$10$ZdqfOo1vwdHJJGnkmGrw/OUelZcU9ZfRFaX/RMN3XniXH96eTkB1e', true, current_timestamp,
        current_timestamp),
       (2, 'minerva_m', '$2y$10$PiZxGGi904rCLdTSGY1ycuQhcEtQrP1u74KvQ2IEuk5Jh18Ml.6xO', true, current_timestamp,
        current_timestamp);

INSERT INTO accounts_permissions(account_id, permission_id)
VALUES (1, 3),
       (2, 3);

INSERT INTO questions(id, created_date, modified_date, title, description, account_id)
VALUES (1, current_timestamp, current_timestamp, 'title', 'description', 2);

INSERT INTO tags(id, created_date, modified_date, name)
VALUES (1, current_timestamp, current_timestamp, 'tag1'),
       (2, current_timestamp, current_timestamp, 'tag2'),
       (3, current_timestamp, current_timestamp, 'tag3');

INSERT INTO questions_tags(question_id, tag_id)
VALUES (1, 1), (1, 2), (1, 3);

INSERT INTO question_comments(id, created_date, modified_date, text, account_id, question_id)
VALUES (1, current_timestamp, current_timestamp, 'text', 1, 1),
       (2, current_timestamp, current_timestamp, 'text', 1, 1),
       (3, current_timestamp, current_timestamp, 'text', 1, 1),
       (4, current_timestamp, current_timestamp, 'text', 1, 1),
       (5, current_timestamp, current_timestamp, 'text', 1, 1),
       (6, current_timestamp, current_timestamp, 'text', 1, 1),
       (7, current_timestamp, current_timestamp, 'text', 1, 1),
       (8, current_timestamp, current_timestamp, 'text', 1, 1),
       (9, current_timestamp, current_timestamp, 'text', 1, 1),
       (10, current_timestamp, current_timestamp, 'text', 1, 1);