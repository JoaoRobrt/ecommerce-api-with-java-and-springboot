package com.ecommerce.project.controllers;

import com.ecommerce.project.dtos.commoms.ApiResponse;
import com.ecommerce.project.dtos.responses.UserResponseDTO;
import com.ecommerce.project.security.dtos.requests.LoginRequest;
import com.ecommerce.project.security.dtos.requests.SignupRequest;
import com.ecommerce.project.security.dtos.responses.UserAuthResponse;
import com.ecommerce.project.security.jwt.JwtUtils;
import com.ecommerce.project.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private  JwtUtils jwtUtils;
    private AuthenticationManager authenticationManager;
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<UserAuthResponse>> authenticateUser(@RequestBody LoginRequest loginRequest) {
       UserAuthResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(
                ApiResponse.success("Login successful", response));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserResponseDTO>> registerUser(@Valid @RequestBody SignupRequest singupRequest){
        UserResponseDTO responseDTO = authService.signup(singupRequest);
        return ResponseEntity.ok(ApiResponse.success("User registered successfully", responseDTO));
    }
}
