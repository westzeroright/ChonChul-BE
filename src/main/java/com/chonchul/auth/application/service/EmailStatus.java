package com.chonchul.auth.application.service;

public enum EmailStatus {
    PENDING("인증 대기중"),
    VERIFIED("인증 완료");

    private String code;

    EmailStatus(String code) {
        this.code = code;
    }
}
