package com.ecommerce.project.controllers;

import com.ecommerce.project.dtos.commoms.ApiResponse;
import com.ecommerce.project.dtos.responses.UserResponseDTO;
import com.ecommerce.project.security.dtos.requests.LoginRequest;
import com.ecommerce.project.security.dtos.requests.SignupRequest;
import com.ecommerce.project.security.dtos.responses.UserAuthResponse;
import com.ecommerce.project.security.services.UserDetailsImpl;
import com.ecommerce.project.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserAuthResponse>> authenticateUser(@RequestBody LoginRequest loginRequest) {
       UserAuthResponse response = authService.login(loginRequest);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, response.jwtCookie().toString())
                .body(ApiResponse.success("Login successful", response));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDTO>> registerUser(@Valid @RequestBody SignupRequest singupRequest){
        UserResponseDTO responseDTO = authService.register(singupRequest);
        return ResponseEntity.ok(ApiResponse.success("User registered successfully", responseDTO));
    }

    @GetMapping("/username")
    public String currentUseName(Authentication authentication){
        return authService.getUsername(authentication);
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserDetails(Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserResponseDTO response = authService.getUserResponse(userDetails);
        return ResponseEntity.ok(ApiResponse.success("User retrieved successfully", response));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<?>> logout(){
        ResponseCookie cookie = authService.logout();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(ApiResponse.success("Logout successfully", null));
    }

}

