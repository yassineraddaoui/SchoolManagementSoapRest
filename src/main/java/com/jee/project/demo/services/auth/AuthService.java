package com.jee.project.demo.services.auth;

import com.jee.project.demo.payload.requests.LoginRequest;
import com.jee.project.demo.payload.requests.RegenerateOtpRequest;
import com.jee.project.demo.payload.requests.RegisterRequest;
import com.jee.project.demo.payload.requests.VerifyAccountRequest;

public interface AuthService {

    Object register(RegisterRequest request);

    Object login(LoginRequest request);

    Object verifyAccount(VerifyAccountRequest request);

    Object regenerateOtp(RegenerateOtpRequest request);

}
