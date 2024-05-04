package com.jee.project.demo.controllers;

import com.jee.project.demo.payload.requests.LoginRequest;
import com.jee.project.demo.payload.requests.RegenerateOtpRequest;
import com.jee.project.demo.payload.requests.RegisterRequest;
import com.jee.project.demo.payload.requests.VerifyAccountRequest;
import com.jee.project.demo.services.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    @PutMapping("/verify-account")
    public ResponseEntity<Object> verifyAccount(@Valid @RequestBody VerifyAccountRequest request) {
        return ResponseEntity.ok(service.verifyAccount(request));
    }
    @PostMapping("/regenerate-otp")
    public ResponseEntity<Object> regenerateOtp(@Valid @RequestBody RegenerateOtpRequest request) {
        return ResponseEntity.ok(service.regenerateOtp(request));
    }
}
