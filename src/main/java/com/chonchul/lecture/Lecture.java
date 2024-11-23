package com.chonchul.lecture;

import com.chonchul.attend.StudentLecture;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    private String place;

    @OneToMany(mappedBy = "lecture")
    private List<StudentLecture> studentLectures = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "lecture_id")
    private List<Session> sessions = new ArrayList<>();
}
