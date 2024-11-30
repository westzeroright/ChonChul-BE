package com.chonchul.user.persistence.entity;

import com.chonchul.attend.Attend;
import com.chonchul.attend.StudentLecture;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User {
    @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
    private List<StudentLecture> studentLectures = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "student_id")
    private List<Attend> attends = new ArrayList<>();
}
