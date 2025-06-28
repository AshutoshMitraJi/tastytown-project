package com.jt.tastytown.backend.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.jt.tastytown.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByUserEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found with Email: " + email));
        
        return new User(
            user.getUserEmail(),
            user.getUserPassword(),
            List.of(new SimpleGrantedAuthority(user.getRole().toString()))
        );
    }
    
}
