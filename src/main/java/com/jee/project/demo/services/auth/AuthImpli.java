package com.jee.project.demo.services.auth;

import com.jee.project.demo.Repositories.RoleRepo;
import com.jee.project.demo.Repositories.UserRepo;
import com.jee.project.demo.config.JWT.JwtService;
import com.jee.project.demo.config.Mail;
import com.jee.project.demo.config.OTP;
import com.jee.project.demo.entities.Role;
import com.jee.project.demo.entities.User;
import com.jee.project.demo.payload.requests.LoginRequest;
import com.jee.project.demo.payload.requests.RegisterRequest;
import com.jee.project.demo.payload.requests.VerifyAccountRequest;
import com.jee.project.demo.payload.response.ErrorResponse;
import com.jee.project.demo.payload.response.LoginResponse;
import com.jee.project.demo.payload.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthImpli implements AuthService {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Mail emailSenderCmp;
    private final RoleRepo roleRepository;

    public Object register(RegisterRequest request) {

        if(userRepository.findByEmail(request.getEmail()).isPresent())
            return MessageResponse.builder()
                    .message("Email already exists")
                    .http_code(HttpStatus.CONFLICT.value())
                    .build();

        Set<Role> roles = request.getRoles().stream()
                .map(
                        roleName -> roleRepository.findByName(roleName).orElseThrow(
                                () -> new RuntimeException("Role not found for name: " + roleName)
                        )
                )
                .collect(Collectors.toSet());

        if (roles.isEmpty()) {
            return MessageResponse.builder()
                    .message("Invalid roles provided")
                    .http_code(HttpStatus.UNAUTHORIZED.value())
                    .build();
        }

        String otpCode = OTP.generateOTP();

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .otp(otpCode)
                .otpGeneratedTime(LocalDateTime.now())
                .build();
        User savedUser = userRepository.save(user);

        emailSenderCmp.sendOtpVerification(savedUser.getEmail(), otpCode);
        return MessageResponse.builder()
                .message("Registration done, check your email to verify your account with the OTP code")
                .http_code(HttpStatus.OK.value())
                .build();
    }

    public Object login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            User user = userRepository.findByEmailWithRoles(request.getEmail()).orElseThrow(
                    () -> new RuntimeException("User not found for email: " + request.getEmail())
            );
            if (user.isVerified()) {
                String jwtToken = jwtService.generateToken(user);


                return LoginResponse.builder()
                        .token(jwtToken)
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .message("Welcome to our project")
                        .http_code(HttpStatus.OK.value())
                        .build();
            }
            return ErrorResponse.builder()
                    .errors(List.of("Your account is not verified"))
                    .http_code(HttpStatus.UNAUTHORIZED.value())
                    .build();
        } catch (BadCredentialsException e) {
            return ErrorResponse.builder()
                    .errors(List.of("Invalid email or password. Please try again"))
                    .http_code(HttpStatus.UNAUTHORIZED.value())
                    .build();
        }
    }


    public Object verifyAccount(VerifyAccountRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(
                        () -> new RuntimeException("User not found for email: " + request.getEmail())
                );
        LocalDateTime otpGeneratedTime = user.getOtpGeneratedTime();
        LocalDateTime currentTime = LocalDateTime.now();
        long secondsDifference = Duration.between(otpGeneratedTime, currentTime).getSeconds();
        boolean isNotExpired = secondsDifference < 60;

        if (user.getOtp().equals(request.getOtp())) {
            if (isNotExpired) {
                if (!user.isVerified()) {
                    user.setVerified(true);
                    userRepository.save(user);
                    return MessageResponse.builder()
                            .message("Your OTP has been successfully verified. You now have access to the platform")
                            .http_code(HttpStatus.OK.value())
                            .build();
                } else {
                    return ErrorResponse.builder()
                            .errors(List.of("Your account is already verified. You have access to the platform"))
                            .http_code(HttpStatus.UNAUTHORIZED.value())
                            .build();
                }
            } else {
                return ErrorResponse.builder()
                        .errors(List.of("OTP Code expired. Please regenerate another OTP code"))
                        .http_code(HttpStatus.UNAUTHORIZED.value())
                        .build();
            }
        } else {
            return ErrorResponse.builder()
                    .errors(List.of("Invalid OTP code"))
                    .http_code(HttpStatus.UNAUTHORIZED.value())
                    .build();
        }
    }


}
