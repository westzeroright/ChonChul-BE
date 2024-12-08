package com.chonchul.lecture.application.service;

import com.chonchul.lecture.application.dto.LectureInfoDto;
import com.chonchul.lecture.application.exception.NotFoundLecture;
import com.chonchul.lecture.persistence.entity.Lecture;
import com.chonchul.lecture.persistence.repository.LectureRepository;
import com.chonchul.lecture.persistence.repository.StudentLectureRepository;
import com.chonchul.user.application.service.UserService;
import com.chonchul.user.persistence.entity.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final UserService userService;
    private final LectureRepository lectureRepository;
    private final StudentLectureRepository studentLectureRepository;

    public List<LectureInfoDto> getLectures(Long userId) {
        Role role = userService.checkRole(userId);
        List<Lecture> lectures  = new ArrayList<>();
        ;
        if (role == Role.STUDENT) {
            lectures = getLecturesOfStudent(userId);
        }
        if (role == Role.TEACHER) {
            lectures = getLecturesOfTeacher(userId);
        }
        return lectures.stream()
                .map(lecture -> new LectureInfoDto(
                        lecture.getName(),
                        lecture.getTeacher().getName(),
                        lecture.getCode(),
                        lecture.getTime(),
                        lecture.getPlace()
                ))
                .collect(Collectors.toList());
    }

    public LectureInfoDto getLectureInfo(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new NotFoundLecture());
        return new LectureInfoDto(
                lecture.getName(),
                lecture.getTeacher().getName(),
                lecture.getCode(),
                lecture.getTime(),
                lecture.getPlace()
        );
    }

    public List<Lecture> getLecturesOfStudent(Long userId) {
        List<Long> lectureId = studentLectureRepository.findLectureIdByStudentId(userId);
        List<Lecture> lectures = lectureRepository.findByIdIn(lectureId);
        return lectures;
    }

    public List<Lecture> getLecturesOfTeacher(Long userId) {
        List<Lecture> lectures = lectureRepository.findByTeacherId(userId);
        return lectures;
    }
}
