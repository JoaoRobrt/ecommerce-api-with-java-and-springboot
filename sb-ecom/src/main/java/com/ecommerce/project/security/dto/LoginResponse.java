package com.ecommerce.project.security.dto;

import java.util.List;

public record LoginResponse (String jwtToken,
                             String username,
                             List<String> roles){
}


