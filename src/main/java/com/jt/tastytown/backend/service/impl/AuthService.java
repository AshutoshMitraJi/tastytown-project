package com.jt.tastytown.backend.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.jt.tastytown.backend.dto.AuthRequest;
import com.jt.tastytown.backend.dto.AuthResponse;
import com.jt.tastytown.backend.entity.User;
import com.jt.tastytown.backend.service.IAuthService;

import com.jt.tastytown.backend.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jt.tastytown.backend.constants.Role;
import com.jt.tastytown.backend.security.jwt.JwtUtils;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager manager;
    private final JwtUtils jwtUtils;

    @Override
    public User register(AuthRequest request) {
        if(repository.findByUserEmail(request.userEmail()).isPresent()) {
            throw new RuntimeException("Email already registered with us");
        }

        User user = User.builder()
                    .userEmail(request.userEmail())
                    .userPassword(encoder.encode(request.userPassword()))
                    .role(Role.ROLE_USER)
                    .build();
        var savedUser = repository.save(user);
        return savedUser;
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(request.userEmail(), request.userPassword()));

        var user = repository.findByUserEmail(request.userEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found with email"));

        String token = jwtUtils.generateToken(user.getUserId(), user.getRole().toString());

        return new AuthResponse(token);
    }
    
}