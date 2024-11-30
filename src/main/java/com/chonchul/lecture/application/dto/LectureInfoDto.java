package com.chonchul.lecture.application.dto;

public record LectureInfoDto(
        String lectureName,
        String teacherName,
        String code,
        String time,
        String place) {
}
