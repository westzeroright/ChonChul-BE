package com.chonchul.lecture.persistence.entity;

import com.chonchul.attend.Attend;
import com.chonchul.common.persistence.BaseEntity;
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
public class Session extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int week;

    private LocalDate date;

    @OneToMany
    @JoinColumn(name = "session_id")
    private List<Attend> attends = new ArrayList<>();
}
