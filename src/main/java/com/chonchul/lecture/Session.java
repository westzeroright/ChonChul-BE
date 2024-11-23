package com.chonchul.lecture;

import com.chonchul.attend.Attend;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String week;

    private LocalDate date;

    @OneToMany
    @JoinColumn(name = "session_id")
    private List<Attend> attends = new ArrayList<>();
}
