package com.chonchul.user.presentation;

import com.chonchul.common.response.ResponseEntityGenerator;
import com.chonchul.common.response.SuccessBody;
import com.chonchul.user.application.dto.UserInfoDto;
import com.chonchul.user.application.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<SuccessBody<UserInfoDto>> getUser(@PathVariable Long userId, Model model) {
        UserInfoDto userInfoDto = userService.findUser(userId);
        model.addAttribute("hello", "서버에서 보내준 값입니다");
        return ResponseEntityGenerator.success(userInfoDto, "회원 조회 성공", HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<SuccessBody<Void>> updateUser(@PathVariable Long userId, @Valid @RequestBody UserInfoDto userInfoDto) {
        userService.modifyUser(userId,userInfoDto);
        return ResponseEntityGenerator.success(null,"회원 수정 성공", HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<SuccessBody<Void>> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntityGenerator.success(null,"회원 삭제 성공", HttpStatus.OK);
    }
}
