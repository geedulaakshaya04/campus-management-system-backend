package com.campus.management.service;

import com.campus.management.dto.AuthDto;
import com.campus.management.entity.User;
import com.campus.management.repository.UserRepository;
import com.campus.management.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthDto.LoginResponse login(AuthDto.LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + request.getUsername()));

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", user.getRole().name());
        extraClaims.put("fullName", user.getFullName());
        extraClaims.put("department", user.getDepartment());

        String jwtToken = jwtService.generateToken(user, extraClaims);

        return new AuthDto.LoginResponse(
                jwtToken,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFullName(),
                user.getRole().name(),
                user.getDepartment()
        );
    }
}
