package com.lexho.user_service.service;

import com.lexho.user_service.entity.User;
import com.lexho.user_service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }
}