package com.chonchul.lecture.presentation;

import com.chonchul.common.response.ResponseEntityGenerator;
import com.chonchul.common.response.SuccessBody;
import com.chonchul.lecture.application.dto.LectureInfoDto;
import com.chonchul.lecture.application.dto.SessionInfoDto;
import com.chonchul.lecture.application.service.LectureService;
import com.chonchul.lecture.application.service.SessionService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/lecture")
public class LectureController {

    private final LectureService lectureService;
    private final SessionService sessionService;

    public LectureController(LectureService lectureService, SessionService sessionService) {
        this.lectureService = lectureService;
        this.sessionService = sessionService;
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<SuccessBody<List<LectureInfoDto>>> getLectures(@PathVariable Long studentId) {
        List<LectureInfoDto> lectures = lectureService.getLectures(studentId);
        return ResponseEntityGenerator.success(lectures, "수업 목록 조회 성공", HttpStatus.OK);
    }

    @GetMapping("/info/{lectureId}")
    public ResponseEntity<SuccessBody<LectureInfoDto>> getLectureInfo(@PathVariable Long lectureId) {
        LectureInfoDto lectureInfoDto =  lectureService.getLectureInfo(lectureId);
        return ResponseEntityGenerator.success(lectureInfoDto, "수업 목록 조회 성공", HttpStatus.OK);
    }

    @GetMapping("/session")
    public ResponseEntity<SuccessBody<SessionInfoDto>> getLectureSession(@RequestParam LocalDate localDate, @RequestParam Long lectureId) {
        SessionInfoDto sessionInfoDto = sessionService.getLectureSession(localDate, lectureId);
        return ResponseEntityGenerator.success(sessionInfoDto,"강의주차 조회 성공", HttpStatus.OK);
    }
}
