package com.chonchul.attend.persistence.entity;

public enum Status {
    ATTEND("출석"),
    LATE("지각"),
    ABSENT("결석");

    private String code;

    Status(String code) {
        this.code = code;
    }
}
