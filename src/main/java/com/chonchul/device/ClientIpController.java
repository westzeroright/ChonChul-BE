package com.chonchul.device;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Network API", description = "네트워크 관련 API")
public class ClientIpController {

    @Operation(summary = "와이파이 연결 확인", description = "와이파이 연결이 되어있으면 출석 가능하도록 합니다.")
    @GetMapping("/client-ip")
    public String getClientIp(HttpServletRequest request) {
        String clientIp = IpUtil.getClientIp(request);
        return "Client IP: " + clientIp;
    }
}
