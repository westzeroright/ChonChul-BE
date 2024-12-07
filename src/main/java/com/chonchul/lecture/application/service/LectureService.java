package com.chonchul.lecture.application.service;

import com.chonchul.lecture.application.dto.LectureInfoDto;
import com.chonchul.lecture.application.exception.NotFoundLecture;
import com.chonchul.lecture.persistence.entity.Lecture;
import com.chonchul.lecture.persistence.repository.LectureRepository;
import com.chonchul.lecture.persistence.repository.StudentLectureRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class LectureService {

    private final LectureRepository lectureRepository;
    private final StudentLectureRepository studentLectureRepository;

    public LectureService(LectureRepository lectureRepository, StudentLectureRepository studentLectureRepository) {
        this.lectureRepository = lectureRepository;
        this.studentLectureRepository = studentLectureRepository;
    }

    public List<LectureInfoDto> getLectures(Long studentId) {
        List<Long> lectureId = studentLectureRepository.findLectureIdByStudentId(studentId);
        List<Lecture> lectures = lectureRepository.findByIdIn(lectureId);
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
}
