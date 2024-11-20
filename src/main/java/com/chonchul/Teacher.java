package com.chonchul;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int number;

    private String name;

    private String department;

    private String phoneNumber;

    private String email;

    @OneToMany
    @JoinColumn(name = "teacher_id")
    private List<Lecture> lectures = new ArrayList<>();
}
