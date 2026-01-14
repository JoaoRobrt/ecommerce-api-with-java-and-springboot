package com.ecommerce.project.security.dtos.responses;

import org.springframework.http.ResponseCookie;

import java.util.List;

public record UserAuthResponse(Long id,
                               String username,
                               List<String> roles,
                               ResponseCookie jwtCookie){


}


