package com.chonchul.device;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ClientIpController {

    @GetMapping("/client-ip")
    public String getClientIp(HttpServletRequest request) {
        String clientIp = IpUtil.getClientIp(request);
        return "Client IP: " + clientIp;
    }
}
