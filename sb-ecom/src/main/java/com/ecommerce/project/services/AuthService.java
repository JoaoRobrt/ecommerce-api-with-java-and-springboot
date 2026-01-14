package com.ecommerce.project.services;

import com.ecommerce.project.dtos.responses.UserResponseDTO;
import com.ecommerce.project.security.dtos.requests.LoginRequest;
import com.ecommerce.project.security.dtos.requests.SignupRequest;
import com.ecommerce.project.security.dtos.responses.UserAuthResponse;
import com.ecommerce.project.security.services.UserDetailsImpl;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;

public interface AuthService {
    UserAuthResponse login(LoginRequest loginRequest);
    UserResponseDTO register(SignupRequest signupRequest);
    UserResponseDTO getUserResponse(UserDetailsImpl userDetails);
    String getUsername(Authentication authentication);
    ResponseCookie logout();
}
