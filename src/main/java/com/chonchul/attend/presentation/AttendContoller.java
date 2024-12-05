package com.chonchul.attend.presentation;

import com.chonchul.attend.application.AttendService;
import com.chonchul.auth.LoginUser;
import com.chonchul.common.response.ResponseEntityGenerator;
import com.chonchul.common.response.SuccessBody;
import com.chonchul.user.persistence.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/attend")
public class AttendContoller {
    private final AttendService attendService;

    public AttendContoller(AttendService attendService) {
        this.attendService = attendService;
    }

    @PostMapping("/student")
    public ResponseEntity<SuccessBody<Void>> attend(@LoginUser User user, @RequestBody String qrToken) {
        attendService.attend(user.getId(), qrToken);
        return ResponseEntityGenerator.success(null, "출석 처리 성공", HttpStatus.OK);
    }

    @PostMapping("/teacher")
    public ResponseEntity<SuccessBody<Void>> late(@LoginUser User user, @RequestParam Long studentId, @RequestParam Long sessionId) {
        attendService.late(studentId,sessionId);
        return ResponseEntityGenerator.success(null, "지각 처리 성공", HttpStatus.OK);
    }
}
