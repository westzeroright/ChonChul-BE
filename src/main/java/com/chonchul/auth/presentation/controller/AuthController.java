package com.chonchul.auth.presentation.controller;

import com.chonchul.auth.application.dto.LoginReqDto;
import com.chonchul.auth.application.dto.SignUpReqDto;
import com.chonchul.auth.application.service.AuthService;
import com.chonchul.auth.application.dto.AuthTokenDto;
import com.chonchul.common.response.ResponseEntityGenerator;
import com.chonchul.common.response.SuccessBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
}
