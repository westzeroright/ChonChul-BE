package com.chonchul.user.presentation;

import com.chonchul.auth.LoginUser;
import com.chonchul.auth.dto.LoginReqDto;
import com.chonchul.auth.dto.SignUpReqDto;
import com.chonchul.auth.token.AuthTokenDto;
import com.chonchul.common.response.ResponseEntityGenerator;
import com.chonchul.common.response.SuccessBody;
import com.chonchul.user.application.dto.UserInfoDto;
import com.chonchul.user.application.service.UserService;
import com.chonchul.user.persistence.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "회원 API", description = "회원 관련 API")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "회원 기본정보 조회", description = "마이페이지에서 회원의 기본 정보를 조회합니다.")
    @GetMapping
    public ResponseEntity<SuccessBody<UserInfoDto>> getUser(@LoginUser User user) {
        UserInfoDto userInfoDto = userService.findUser(user.getId());
        return ResponseEntityGenerator.success(userInfoDto, "회원 조회 성공", HttpStatus.OK);
    }

    @Operation(summary = "회원 정보 수정", description = "회원 정보를 수정합니다.")
    @PutMapping
    public ResponseEntity<SuccessBody<Void>> updateUser(@LoginUser User user,
                                                        @Valid @RequestBody UserInfoDto userInfoDto) {
        userService.modifyUser(user.getId(), userInfoDto);
        return ResponseEntityGenerator.success(null, "회원 수정 성공", HttpStatus.OK);
    }

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴를 수행합니다.")
    @DeleteMapping
    public ResponseEntity<SuccessBody<Void>> deleteUser(@LoginUser User user) {
        userService.deleteUser(user.getId());
        return ResponseEntityGenerator.success(null, "회원 삭제 성공", HttpStatus.OK);
    }
}
