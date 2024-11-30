package com.chonchul.lecture.application;

import com.chonchul.lecture.dto.LectureInfoDto;
import com.chonchul.lecture.persistence.LectureRepository;
import com.chonchul.lecture.persistence.StudentLectureRepository;
import com.chonchul.lecture.persistence.entity.Lecture;
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
        List<Long> lectureId =  studentLectureRepository.findLectureIdByStudentId(studentId);
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
}
