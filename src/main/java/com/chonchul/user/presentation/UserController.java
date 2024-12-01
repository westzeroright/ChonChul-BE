package com.chonchul.user.presentation;

import com.chonchul.common.response.ResponseEntityGenerator;
import com.chonchul.common.response.SuccessBody;
import com.chonchul.token.AuthTokenDto;
import com.chonchul.user.application.dto.UserInfoDto;
import com.chonchul.user.application.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<SuccessBody<UserInfoDto>> getUser(@PathVariable Long userId) {
        UserInfoDto userInfoDto = userService.findUser(userId);
        return ResponseEntityGenerator.success(userInfoDto, "회원 조회 성공", HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<SuccessBody<Void>> updateUser(@PathVariable Long userId,
                                                        @Valid @RequestBody UserInfoDto userInfoDto) {
        userService.modifyUser(userId, userInfoDto);
        return ResponseEntityGenerator.success(null, "회원 수정 성공", HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<SuccessBody<Void>> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntityGenerator.success(null, "회원 삭제 성공", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<SuccessBody<AuthTokenDto>> createUser(@RequestBody UserInfoDto userInfoDto) {
        AuthTokenDto authTokenDto = userService.signup(userInfoDto.name(),userInfoDto.number(),userInfoDto.department(),userInfoDto.phoneNumber(),userInfoDto.email());
        return ResponseEntityGenerator.success(authTokenDto, "회원 추가 성공", HttpStatus.OK);
    }
}
