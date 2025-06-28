package com.jt.tastytown.backend.helper;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Component;

import com.jt.tastytown.backend.entity.User;
import com.jt.tastytown.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserServiceHelper {
    private final UserRepository repository;

    public User getUserById(String userId) {
        return repository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found with id " + userId));
    }
}