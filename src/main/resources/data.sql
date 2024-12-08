INSERT IGNORE INTO user (id, created_at, updated_at, deleted_at, number, name, department, phone_number, email, role)
VALUES (2, NOW(), NOW(), null, 0, '김명진', null, null, null, 'TEACHER'),
       (3, NOW(), NOW(), null, 0, '조종택', null, null, null, 'TEACHER'),
       (4, NOW(), NOW(), null, 0, '권도용', null, null, null, 'TEACHER');

INSERT IGNORE INTO teacher (id)
VALUES (2),
       (3),
       (4);

INSERT IGNORE INTO lecture (id, created_at, updated_at, deleted_at, name, code, time, place, teacher_id)
VALUES (1, NOW(), NOW(), null, '임베디드소프트웨어', 'ECE3088-2', '월 11:00-13:00, 수 11:00-13:00', '공7-219', 2),
       (2, NOW(), NOW(), null, '미분기하1및실습', 'MTH3012-1', '월 13:00-15:00, 수 13:00-15:00', '자1-437', 3),
       (3, NOW(), NOW(), null, '암호학입문', 'MTH4027-1', '월 15:00-17:00, 수 15:00-16:00', '자1-301', 4);

INSERT IGNORE INTO student_lecture (id, created_at, updated_at, deleted_at, student_id, lecture_id)
VALUES (1, NOW(), NOW(), null, 1, 1),
       (2, NOW(), NOW(), null, 1, 2),
       (3, NOW(), NOW(), null, 1, 3);

INSERT IGNORE INTO session (id, created_at, updated_at, deleted_at, week, date, lecture_id)
VALUES (1, NOW(), NOW(), null, 13, '2024-11-25', 1),
       (2, NOW(), NOW(), null, 13, '2024-11-27', 1);

ALTER TABLE user
    MODIFY COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    MODIFY COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    MODIFY COLUMN deleted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
