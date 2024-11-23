package com.chonchul;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
