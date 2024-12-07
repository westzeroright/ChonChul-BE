package com.chonchul.device;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    public boolean checkAttendance(HttpServletRequest request) {
        String clientIp = getClientIps(request);
        String macAddress = getMacAddress(clientIp);

        if (macAddress != null) {
            System.out.println("Client IP: " + clientIp);
            System.out.println("MAC Address: " + macAddress);
            return true;
        } else {
            System.out.println("Client IP: " + clientIp + " is not in ARP table.");
            return false;
        }
    }

    public String getClientIps(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public String getMacAddress(String ipAddress) {
        try {
            Process process = Runtime.getRuntime().exec("arp-scan --localnet");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(ipAddress)) {
                    String[] tokens = line.split("\\s+");
                    if (tokens.length >= 3) {
                        return tokens[2];
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/check")
    public ResponseEntity<String> checkAttend(HttpServletRequest request) {
        boolean isPresent = checkAttendance(request);
        if (isPresent) {
            return ResponseEntity.ok("출석이 확인되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("출석 확인 실패: 네트워크에 연결되지 않았습니다.");
        }
    }
}
