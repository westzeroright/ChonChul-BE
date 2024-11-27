package com.chonchul.user.application.service;

import com.chonchul.user.application.dto.UserInfoDto;
import com.chonchul.user.application.exception.NotFoundUserException;
import com.chonchul.user.persistence.UserRepository;
import com.chonchul.user.persistence.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
}
