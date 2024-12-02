package com.chonchul.user.presentation;

import com.chonchul.auth.LoginUser;
import com.chonchul.auth.dto.LoginReqDto;
import com.chonchul.auth.dto.SignUpReqDto;
import com.chonchul.common.response.ResponseEntityGenerator;
import com.chonchul.common.response.SuccessBody;
import com.chonchul.auth.token.AuthTokenDto;
import com.chonchul.user.application.dto.UserInfoDto;
import com.chonchul.user.application.service.UserService;
import com.chonchul.user.persistence.entity.User;
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

    @GetMapping
    public ResponseEntity<SuccessBody<UserInfoDto>> getUser(@LoginUser User user) {
        UserInfoDto userInfoDto = userService.findUser(user.getId());
        return ResponseEntityGenerator.success(userInfoDto, "회원 조회 성공", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<SuccessBody<Void>> updateUser(@LoginUser User user,
                                                        @Valid @RequestBody UserInfoDto userInfoDto) {
        userService.modifyUser(user.getId(), userInfoDto);
        return ResponseEntityGenerator.success(null, "회원 수정 성공", HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<SuccessBody<Void>> deleteUser(@LoginUser User user) {
        userService.deleteUser(user.getId());
        return ResponseEntityGenerator.success(null, "회원 삭제 성공", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<SuccessBody<AuthTokenDto>> signup(@Valid @RequestBody SignUpReqDto signUpReqDto) {
        AuthTokenDto authTokenDto = userService.signup(signUpReqDto.name(),signUpReqDto.number(),signUpReqDto.department(),signUpReqDto.phoneNumber(),signUpReqDto.email(),signUpReqDto.password());
        return ResponseEntityGenerator.success(authTokenDto, "회원 가입 성공", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessBody<AuthTokenDto>> login(@Valid @RequestBody LoginReqDto loginReqDto) {
        AuthTokenDto authTokenDto = userService.login(loginReqDto.email(), loginReqDto.password());
        return ResponseEntityGenerator.success(authTokenDto, "회원 로그인 성공", HttpStatus.OK);
    }
}
