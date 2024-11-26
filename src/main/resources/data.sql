INSERT IGNORE INTO student (id, number, name, department, phone_number, email) VALUES (1, 221965, '서영우', '자연과학대학 수학과', '010-7731-3160', 'me031007@jnu.ac.kr');

INSERT IGNORE INTO teacher (id, name) VALUES (1, '김명진'), (2, '조종택'), (3, '권도용');

INSERT IGNORE INTO lecture (id, name, code, place, teacher_id) VALUES
                                                            (1, '임베디드소프트웨어', 'ECE3088-2', '공7-219',1),
                                                            (2, '미분기하1및실습', 'MTH3012-1', '자1-437',2),
                                                            (3, '암호학입문', 'MTH4027-1', '자1-301',3);

INSERT IGNORE INTO student_lecture (id, student_id, lecture_id) VALUES
                                                             (1,1,1),
                                                             (2,1,2),
                                                             (3,1,3);

INSERT IGNORE INTO session (id, week, date, lecture_id) VALUES
                                         (1, 13, '2024-11-25',1),
                                         (2, 13, '2024-11-27',1);