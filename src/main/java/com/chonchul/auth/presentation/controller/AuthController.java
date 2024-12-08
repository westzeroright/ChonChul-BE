package com.chonchul.auth.presentation.controller;

import com.chonchul.auth.application.dto.AuthTokenDto;
import com.chonchul.auth.application.dto.ChangePasswordDto;
import com.chonchul.auth.application.dto.EmailFindDto;
import com.chonchul.auth.application.dto.EmailReqDto;
import com.chonchul.auth.application.dto.LoginReqDto;
import com.chonchul.auth.application.dto.SignUpReqDto;
import com.chonchul.auth.application.service.AuthService;
import com.chonchul.auth.presentation.LoginUser;
import com.chonchul.common.response.ResponseEntityGenerator;
import com.chonchul.common.response.SuccessBody;
import com.chonchul.user.persistence.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "인증 API", description = "인증 관련 API")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "회원가입", description = "회원가입을 진행합니다.")
    @PostMapping("/signup")
    public ResponseEntity<SuccessBody<AuthTokenDto>> signup(@Valid @RequestBody SignUpReqDto signUpReqDto) {
        AuthTokenDto authTokenDto = authService.signup(signUpReqDto.name(), signUpReqDto.number(),
                signUpReqDto.department(), signUpReqDto.phoneNumber(), signUpReqDto.email(), signUpReqDto.password());
        return ResponseEntityGenerator.success(authTokenDto, "회원 가입 성공", HttpStatus.OK);
    }

    @Operation(summary = "회원가입", description = "로그인을 진행합니다.")
    @PostMapping("/login")
    public ResponseEntity<SuccessBody<AuthTokenDto>> login(@Valid @RequestBody LoginReqDto loginReqDto) {
        AuthTokenDto authTokenDto = authService.login(loginReqDto.email(), loginReqDto.password());
        return ResponseEntityGenerator.success(authTokenDto, "회원 로그인 성공", HttpStatus.OK);
    }

    @PostMapping("/reissue")
    public ResponseEntity<SuccessBody<AuthTokenDto>> reissue(@RequestHeader String refreshToken) {
        AuthTokenDto authTokenDto = authService.reissue(refreshToken);
        return ResponseEntityGenerator.success(authTokenDto, "토큰 재발급 성공", HttpStatus.OK);
    }

    @GetMapping("/email")
    public ResponseEntity<SuccessBody<String>> findEamil(@RequestBody EmailFindDto emailFindDto) {
        String email = authService.findEmail(emailFindDto.name(),emailFindDto.phoneNumber());
        return ResponseEntityGenerator.success(email,"이메일 찾기 성공", HttpStatus.OK);
    }

    @PutMapping("/password")
    public ResponseEntity<SuccessBody<Void>> changePassword(@LoginUser User user, @RequestBody ChangePasswordDto changePasswordDto) {
        authService.changePassword(user.getId(), changePasswordDto.currentPassword(), changePasswordDto.newPassword(),
                changePasswordDto.confirmPassword());
        return ResponseEntityGenerator.success(null,"비밀번호 재설정 성공", HttpStatus.OK);
    }

    @PutMapping("/email")
    public ResponseEntity<SuccessBody<Void>> changeEmail(@LoginUser User user, @RequestBody EmailReqDto emailReqDto) {
        authService.changeEmail(user.getId(),emailReqDto.email());
        return ResponseEntityGenerator.success(null,"이메일 변경 성공", HttpStatus.OK);
    }
}
