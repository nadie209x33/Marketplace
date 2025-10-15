package com.uade.back.controller;

import com.uade.back.dto.OtpDTO;
import com.uade.back.dto.order.OrderDTO;
import com.uade.back.dto.user.AdminUserUpdateDTO;
import com.uade.back.dto.user.UserListDTO;
import com.uade.back.service.order.OrderService;
import com.uade.back.service.security.AuthenticationService;
import com.uade.back.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final OrderService orderService;

    @PutMapping("/users/{userId}")
    public ResponseEntity<Void> adminUpdateUser(@PathVariable Integer userId, @RequestBody AdminUserUpdateDTO adminUserUpdateDTO) {
        userService.adminUpdateUser(userId, adminUserUpdateDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{userId}/otp")
    public ResponseEntity<OtpDTO> adminCheckOtp(@PathVariable Integer userId) {
        return ResponseEntity.ok(authenticationService.adminCheckOtp(userId));
    }

    @PostMapping("/users/{userId}/regenerate-otp")
    public ResponseEntity<OtpDTO> adminRegenerateOtp(@PathVariable Integer userId) {
        return ResponseEntity.ok(authenticationService.adminRegenerateOtp(userId));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<AdminOrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrdersAdmin());
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserListDTO>> getAllUsers() {
        List<UserListDTO> users = userService.getAllUsers().stream()
                .map(UserListDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }
}
