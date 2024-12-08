package com.chonchul.auth.application.service;

import com.chonchul.auth.application.dto.AuthTokenDto;
import com.chonchul.auth.application.exception.InvalidLoginException;
import com.chonchul.auth.application.exception.NotVerifiedMailException;
import com.chonchul.user.application.exception.NotFoundUserException;
import com.chonchul.user.persistence.UserRepository;
import com.chonchul.user.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final AuthTokenService authTokenService;

    public AuthTokenDto signup(String name, int number, String department, String phoneNumber, String email,
                               String password) {
        if (!emailService.isVerified(email)) {
            throw new NotVerifiedMailException();
        }
        User newUser = createUser(name, number, department, phoneNumber, email, password);
        return authTokenService.createAuthToken(newUser.getId());
    }

    public AuthTokenDto login(String email, String password) {
        User existUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundUserException());
        boolean checkPassword = authenticateUser(password, existUser.getPassword());
        if (!checkPassword) {
            throw new InvalidLoginException();
        }

        return authTokenService.createAuthToken(existUser.getId());
    }

    public User createUser(String name, int number, String department, String phoneNumber, String email,
                           String password) {
        User user = new User(name, number, department, phoneNumber, email, hashPassword(password));
        userRepository.saveAndFlush(user);
        return user;
    }

    public AuthTokenDto reissue(String refreshToken) {
        return authTokenService.reissue(refreshToken);
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean authenticateUser(String inputPassword, String storedHashedPassword) {
        return BCrypt.checkpw(inputPassword, storedHashedPassword);
    }

    public String findEmail(String name, String phoneNumber) {
        User user = userRepository.findByNameAndPhoneNumber(name,phoneNumber)
                .orElseThrow(()->new NotFoundUserException());
        return user.getEmail();
    }
}
