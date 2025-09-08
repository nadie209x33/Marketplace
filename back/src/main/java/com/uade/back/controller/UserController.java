package com.uade.back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.back.service.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/{userId}/upgrade")
    public ResponseEntity<Void> upgradeToAdmin(@PathVariable Integer userId) {
        userService.upgradeToAdmin(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/downgrade")
    public ResponseEntity<Void> downgradeToUser(@PathVariable Integer userId) {
        userService.downgradeToUser(userId);
        return ResponseEntity.noContent().build();
    }
}
