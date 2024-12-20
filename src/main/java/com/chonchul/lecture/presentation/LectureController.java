package com.chonchul.lecture.presentation;

import com.chonchul.auth.presentation.LoginUser;
import com.chonchul.common.response.ResponseEntityGenerator;
import com.chonchul.common.response.SuccessBody;
import com.chonchul.lecture.application.dto.LectureInfoDto;
import com.chonchul.lecture.application.dto.SessionInfoDto;
import com.chonchul.lecture.application.service.LectureService;
import com.chonchul.lecture.application.service.SessionService;
import com.chonchul.user.persistence.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/v1/lectures")
@Tag(name = "강의 API", description = "강의 관련 API")
public class LectureController {

    private final LectureService lectureService;
    private final SessionService sessionService;

    public LectureController(LectureService lectureService, SessionService sessionService) {
        this.lectureService = lectureService;
        this.sessionService = sessionService;
    }

    @Operation(summary = "강의 목록 조회", description = "강의 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<SuccessBody<List<LectureInfoDto>>> getLectures(@LoginUser User user) {
        List<LectureInfoDto> lectures = lectureService.getLectures(user.getId());
        return ResponseEntityGenerator.success(lectures, "수업 목록 조회 성공", HttpStatus.OK);
    }

    @Operation(summary = "강의 상세정보 조회", description = "강의의 상세정보를 조회합니다.")
    @GetMapping("/infos/{lectureId}")
    public ResponseEntity<SuccessBody<LectureInfoDto>> getLectureInfo(@PathVariable Long lectureId) {
        LectureInfoDto lectureInfoDto = lectureService.getLectureInfo(lectureId);
        return ResponseEntityGenerator.success(lectureInfoDto, "수업 상세정보 조회 성공", HttpStatus.OK);
    }

    @Operation(summary = "강의주차 조회", description = "강의의 강의주차를 조회합니다.")
    @GetMapping("/sessions")
    public ResponseEntity<SuccessBody<SessionInfoDto>> getLectureSession(@RequestParam LocalDate localDate, @RequestParam Long lectureId) {
        SessionInfoDto sessionInfoDto = sessionService.getLectureSession(localDate, lectureId);
        return ResponseEntityGenerator.success(sessionInfoDto, "강의주차 조회 성공", HttpStatus.OK);
    }
}
