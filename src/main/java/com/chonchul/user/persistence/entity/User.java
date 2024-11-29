package com.chonchul.user.persistence.entity;

import com.chonchul.common.persistence.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = true)
    private int number;

    @Column(nullable = true)
    private String department;

    @Column(nullable = true)
    private String phoneNumber;

    @Column(nullable = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    protected User() {}

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public String getDepartment() {
        return department;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void update(String name, int number, String department, String phoneNumber, String email) {
        this.name = name;
        this.number = number;
        this.department = department;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
