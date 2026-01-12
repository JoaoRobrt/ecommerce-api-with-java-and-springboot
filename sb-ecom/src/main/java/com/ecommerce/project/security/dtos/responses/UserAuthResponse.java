package com.ecommerce.project.security.dtos.responses;

import java.util.List;

public record UserAuthResponse(Long id,
                               String jwtToken,
                               String username,
                               List<String> roles){

}


