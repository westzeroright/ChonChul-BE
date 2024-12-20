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
public class User extends BaseEntity {
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

    @Column(nullable = true)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    protected User() {
    }

    public User(String name, int number, String department, String phoneNumber, String email, String password, Role role) {
        this.name = name;
        this.number = number;
        this.department = department;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

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

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

//    public void grantRole(int number) {
//        if (String.valueOf(number).length() == 6) {
//            this.role = Role.STUDENT;
//        }
//        if (String.valueOf(number).length() != 6) {
//            this.role = Role.TEACHER;
//        }
//    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
