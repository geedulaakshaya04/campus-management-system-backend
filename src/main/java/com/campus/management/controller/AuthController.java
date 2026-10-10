package com.campus.management.controller;

import com.campus.management.dto.AuthDto;
import com.campus.management.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDto.LoginResponse> login(@RequestBody AuthDto.LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
