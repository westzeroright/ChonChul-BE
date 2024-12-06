package com.chonchul.auth.application.service;

import com.chonchul.auth.application.dto.AuthTokenDto;
import com.chonchul.user.application.exception.NotFoundUserException;
import com.chonchul.user.persistence.UserRepository;
import com.chonchul.user.persistence.entity.Student;
import com.chonchul.user.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final AuthTokenService authTokenService;

    public AuthTokenDto signup(String name, int number, String department, String phoneNumber, String email, String password) {
        User user = createStudent(name,number,department,phoneNumber,email, password);
        return authTokenService.createAuthToken(user.getId());
    }

    public AuthTokenDto login(String email, String password) {
        User existUser = userRepository.findByEmailAndPassword(email,password)
                .orElseThrow(() -> new NotFoundUserException());
        return authTokenService.createAuthToken(existUser.getId());
    }

    public User createStudent(String name, int number, String department, String phoneNumber, String email, String password) {
        if (!emailService.isVerified(email)) {
            Student student = new Student(name, number, department, phoneNumber, email, password);
            User user = (User) student;
            userRepository.saveAndFlush(user);
            return user;
        }
        return null;
    }

    public AuthTokenDto reissue(String refreshToken) {
        return authTokenService.reissue(refreshToken);
    }
}
