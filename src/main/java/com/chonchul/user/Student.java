package com.chonchul.user;

import com.chonchul.attend.Attend;
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
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int number;

    private String name;

    private String department;

    private String phoneNumber;

    private String email;

    @OneToMany(mappedBy = "student")
    private List<StudentLecture> studentLectures = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "student_id")
    private List<Attend> attends = new ArrayList<>();
}