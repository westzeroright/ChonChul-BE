package com.chonchul.lecture.presentation;

import com.chonchul.lecture.application.LectureService;
import com.chonchul.lecture.dto.LectureInfoDto;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/lecture")
public class LectureController {

    private final LectureService lectureService;

    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @GetMapping("/{studentId}")
    public List<LectureInfoDto> getLectures(@PathVariable Long studentId) {
        return lectureService.getLectures(studentId);
    }
}
