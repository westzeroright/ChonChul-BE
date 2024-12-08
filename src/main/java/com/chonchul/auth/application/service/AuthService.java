package com.chonchul.auth.application.service;

import com.chonchul.auth.application.dto.AuthTokenDto;
import com.chonchul.auth.application.exception.InvalidLoginException;
import com.chonchul.auth.application.exception.NotMatchCurrentPwException;
import com.chonchul.auth.application.exception.NotMatchEmailException;
import com.chonchul.auth.application.exception.NotMatchNewPasswordException;
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

    public void changePassword(Long userId, String currentPassword, String newPassword, String confirmPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundUserException());
        checkCurrentPassword(user.getPassword(),currentPassword);
        checkNewPassword(newPassword,confirmPassword);
        user.setPassword(hashPassword(newPassword));
        userRepository.saveAndFlush(user);
    }

    public void checkCurrentPassword(String password,String currentPassword) {
        if (!authenticateUser(currentPassword,password)) {
            throw new NotMatchCurrentPwException();
        }
    }

    public void checkNewPassword(String newPassword, String confirmPassword) {
        if(!newPassword.equals(confirmPassword)) {
            throw new NotMatchNewPasswordException();
        }
    }

    /*
    학교 메일인지 검사하고, 메일 인증되었는지 검사하고
     */
    public void changeEmail(Long userId, String email) {
        User user = userRepository.findById(userId)
                        .orElseThrow(()-> new NotFoundUserException());
        isEqualEmail(user.getEmail(),email);
        emailService.checkEmail(email);
        user.setEmail(email);
        userRepository.saveAndFlush(user);
    }

    public void isEqualEmail(String userEmail, String email) {
        if (!(userEmail.equals(email))) {
            throw new NotMatchEmailException();
        }
    }
}
