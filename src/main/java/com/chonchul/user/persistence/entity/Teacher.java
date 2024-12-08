package com.chonchul.user.persistence.entity;

import com.chonchul.lecture.persistence.entity.Lecture;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("TEACHER")
public class Teacher extends User {
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "teacher_id")
    private List<Lecture> lectures = new ArrayList<>();

    public Teacher(String name, int number, String department, String phoneNumber, String email, String password, Role role) {
        super(name, number, department, phoneNumber, email, password, role);
    }
}
