package com.ecommerce.project.services;

import com.ecommerce.project.dtos.responses.UserResponseDTO;
import com.ecommerce.project.security.dtos.requests.LoginRequest;
import com.ecommerce.project.security.dtos.requests.SignupRequest;
import com.ecommerce.project.security.dtos.responses.UserAuthResponse;

public interface AuthService {
    UserAuthResponse login(LoginRequest loginRequest);
    UserResponseDTO signup(SignupRequest signupRequest);
}
