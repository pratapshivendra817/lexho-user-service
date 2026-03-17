package com.lexho.user_service.controller;

import com.lexho.user_service.entity.User;
import com.lexho.user_service.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @GetMapping("/{phone}")
    public User getUser(@PathVariable String phone) {
        return userService.getByPhone(phone).orElse(null);
    }
}