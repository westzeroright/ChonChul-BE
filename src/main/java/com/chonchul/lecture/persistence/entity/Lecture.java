package com.chonchul.lecture.persistence.entity;

import com.chonchul.common.persistence.BaseEntity;
import com.chonchul.user.persistence.entity.Teacher;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Lecture extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    private String time;

    private String place;

    @OneToMany(mappedBy = "lecture")
    private List<StudentLecture> studentLectures = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "lecture_id")
    private List<Session> sessions = new ArrayList<>();

    @ManyToOne
    private Teacher teacher;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getTime() {
        return time;
    }

    public String getPlace() {
        return place;
    }

    public Teacher getTeacher() {
        return teacher;
    }
}
