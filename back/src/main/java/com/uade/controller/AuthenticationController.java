package com.uade.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.service.auth.AuthenticationService;
import com.uade.dto.auth.AuthenticationRequest;
import com.uade.dto.auth.RegisterRequest;
import com.uade.dto.auth.AuthenticationResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
    return ResponseEntity.ok(service.register(request));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
    return ResponseEntity.ok(service.authenticate(request));
  }
}
