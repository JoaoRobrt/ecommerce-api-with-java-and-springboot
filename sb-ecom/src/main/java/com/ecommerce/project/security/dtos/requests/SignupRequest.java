package com.ecommerce.project.security.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record SignupRequest (@NotBlank
                             @Size(min = 3, max = 20, message = "Username name must be between 3 and 20 characters.")
                             String username,
                             @NotBlank
                             @Email
                             String email,

                             Set<String> role,

                             @NotBlank
                             @Size(min = 6, message = "Password must hhave atleast 6 characters.")
                             String password){
}
