package com.ecommerce.project.services.impl;

import com.ecommerce.project.dtos.responses.UserResponseDTO;
import com.ecommerce.project.exceptions.ResourceAlreadyExistsException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.models.AppRole;
import com.ecommerce.project.models.Role;
import com.ecommerce.project.models.User;
import com.ecommerce.project.repositories.RoleRepository;
import com.ecommerce.project.repositories.UserRepository;
import com.ecommerce.project.security.dtos.requests.LoginRequest;
import com.ecommerce.project.security.dtos.requests.SignupRequest;
import com.ecommerce.project.security.dtos.responses.UserAuthResponse;
import com.ecommerce.project.security.jwt.JwtUtils;
import com.ecommerce.project.security.services.UserDetailsImpl;
import com.ecommerce.project.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Override
    public UserAuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return new UserAuthResponse(
                userDetails.getId(),
                jwtToken,
                userDetails.getUsername(),
                roles);
    }

    @Override
    public UserResponseDTO signup(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.username())){
            throw new ResourceAlreadyExistsException("Username is already taken.");
        }
        if (userRepository.existsByEmail(signupRequest.email())){
            throw new ResourceAlreadyExistsException("Email is already taken.");
        }

        User user = new User(
                signupRequest.username(),
                signupRequest.email(),
                passwordEncoder.encode(signupRequest.password())
        );

        Set<String> strRoles = signupRequest.role();

        Set<Role> roles = getRolesFromStrings(strRoles);

        user.setRoles(roles);
        User saved = userRepository.save(user);

        return new UserResponseDTO(
                saved.getUserId(),
                saved.getUsername(),
                saved.getEmail());
    }

    private Set<Role> getRolesFromStrings(Set<String> strRoles) {
        List<Role> allRoles = roleRepository.findAll();

        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            Role userRole = allRoles.stream()
                    .filter(r -> r.getRoleName() == AppRole.ROLE_USER)
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
            roles.add(userRole);
            return roles;
        }

        for (String role : strRoles) {
            AppRole appRole;
            switch (role.toLowerCase()) {
                case "admin":
                    appRole = AppRole.ROLE_ADMIN;
                    break;
                case "seller":
                    appRole = AppRole.ROLE_SELLER;
                    break;
                case "user":
                    appRole = AppRole.ROLE_USER;
                    break;
                default:
                    throw new IllegalArgumentException("Role " + role + " is not valid");
            }

            Role matchedRole = allRoles.stream()
                    .filter(r -> r.getRoleName() == appRole)
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

            roles.add(matchedRole);
        }

        return roles;
    }
}
