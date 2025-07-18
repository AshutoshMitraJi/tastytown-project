package com.jt.tastytown.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.tastytown.backend.dto.AuthRequest;
import com.jt.tastytown.backend.dto.AuthResponse;
import com.jt.tastytown.backend.entity.User;
import com.jt.tastytown.backend.service.IAuthService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody AuthRequest request) {
        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    // @PostMapping("/register-admin")
    // public ResponseEntity<User> registerUser(@RequestBody AuthRequest request) {
    //     return new ResponseEntity<>(authService.register(request, Role.ROLE_ADMIN), HttpStatus.CREATED);
    // }
}