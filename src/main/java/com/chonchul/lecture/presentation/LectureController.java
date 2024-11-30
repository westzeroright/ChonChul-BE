package com.chonchul.lecture.presentation;

import com.chonchul.lecture.application.dto.LectureInfoDto;
import com.chonchul.lecture.application.dto.SessionInfoDto;
import com.chonchul.lecture.application.service.LectureService;
import com.chonchul.lecture.application.service.SessionService;
import java.time.LocalDate;
import java.util.List;
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
    public List<LectureInfoDto> getLectures(@PathVariable Long studentId) {
        return lectureService.getLectures(studentId);
    }

    @GetMapping("/info/{lectureId}")
    public LectureInfoDto getLectureInfo(@PathVariable Long lectureId) {
        return lectureService.getLectureInfo(lectureId);
    }

    @GetMapping("/session")
    public SessionInfoDto getLectureSession(@RequestParam LocalDate localDate, @RequestParam Long lectureId) {
        return sessionService.getLectureSession(localDate,lectureId);
    }
}
