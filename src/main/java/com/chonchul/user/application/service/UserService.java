package com.chonchul.user.application.service;

import com.chonchul.email.EmailService;
import com.chonchul.auth.token.AuthTokenDto;
import com.chonchul.auth.token.AuthTokenService;
import com.chonchul.user.application.dto.UserInfoDto;
import com.chonchul.user.application.exception.NotFoundUserException;
import com.chonchul.user.persistence.UserRepository;
import com.chonchul.user.persistence.entity.Student;
import com.chonchul.user.persistence.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final AuthTokenService authTokenService;

    @Transactional(readOnly = true)
    public UserInfoDto findUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException());
        return new UserInfoDto(
                user.getName(),
                user.getNumber(),
                user.getDepartment(),
                user.getPhoneNumber(),
                user.getEmail());
    }

    @Transactional
    public void modifyUser(Long userId, UserInfoDto userInfoDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException());
        user.update(userInfoDto.name(), userInfoDto.number(), userInfoDto.department(), userInfoDto.phoneNumber(),
                userInfoDto.email());
        userRepository.saveAndFlush(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException());
        userRepository.delete(user);
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

    public AuthTokenDto signup(String name, int number, String department, String phoneNumber, String email, String password) {
        User user = createStudent(name,number,department,phoneNumber,email, password);
        return authTokenService.createAuthToken(user.getId());
    }

    public AuthTokenDto login(String email, String password) {
        User existUser = userRepository.findByEmailAndPassword(email,password)
                .orElseThrow(() -> new NotFoundUserException());
        return authTokenService.createAuthToken(existUser.getId());
    }
}
