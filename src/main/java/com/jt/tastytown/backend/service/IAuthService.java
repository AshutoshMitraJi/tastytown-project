package com.jt.tastytown.backend.service;

import com.jt.tastytown.backend.dto.AuthRequest;
import com.jt.tastytown.backend.dto.AuthResponse;
import com.jt.tastytown.backend.entity.User;

public interface IAuthService {
    User register(AuthRequest request);
    AuthResponse login(AuthRequest request);
}