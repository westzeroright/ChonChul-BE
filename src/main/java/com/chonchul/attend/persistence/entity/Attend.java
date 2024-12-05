package com.chonchul.attend.persistence.entity;

import com.chonchul.common.persistence.BaseEntity;
import com.chonchul.lecture.persistence.entity.Session;
import com.chonchul.user.persistence.entity.Student;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Attend extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime time;

    private Status status;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    protected Attend() {}

    public Attend(LocalDateTime time, Status status, Session session, Student student) {
        this.time = time;
        this.status = status;
        this.session = session;
        this.student = student;
    }
}
