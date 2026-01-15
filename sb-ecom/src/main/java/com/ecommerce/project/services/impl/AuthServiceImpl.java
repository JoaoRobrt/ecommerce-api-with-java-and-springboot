package com.ecommerce.project.services.impl;

import com.ecommerce.project.dtos.responses.UserResponseDTO;
import com.ecommerce.project.exceptions.api.ResourceAlreadyExistsException;
import com.ecommerce.project.exceptions.api.ResourceNotFoundException;
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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
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

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserAuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return new UserAuthResponse(
                userDetails.getId(),
                userDetails.getUsername(),
                roles,
                jwtCookie);
    }

    @Transactional
    @Override
    public UserResponseDTO register(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.username())){
            throw new ResourceAlreadyExistsException("Username");
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
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseThrow(() -> new ResourceNotFoundException("Role ROLE_USER"));
            roles.add(userRole);
        } else {
            for (String r : strRoles) {
                AppRole appRole = switch (r.toLowerCase()) {
                    case "admin" -> AppRole.ROLE_ADMIN;
                    case "seller" -> AppRole.ROLE_SELLER;
                    case "user" -> AppRole.ROLE_USER;
                    default -> throw new IllegalArgumentException("Role " + r + " is not valid");
                };
                Role role = roleRepository.findByRoleName(appRole)
                        .orElseThrow(() -> new ResourceNotFoundException("Role " + appRole ));

                // Garante que a entidade está gerenciada
                roles.add(roleRepository.getReferenceById(role.getRoleId()));
            }
        }

        user.setRoles(roles);
        User saved = userRepository.save(user);


        return new UserResponseDTO(
                saved.getUserId(),
                saved.getUsername(),
                saved.getEmail(),
                roles.stream()
                .map(role -> role.getRoleName().name())
                .toList());
    }

    @Override
    public UserResponseDTO getUserResponse(UserDetailsImpl userDetails) {
        if(userDetails == null) return null;
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return new UserResponseDTO(
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }
    @Override
    public String getUsername(Authentication authentication) {
        if(authentication != null) return authentication.getName();
        return "";
    }

    @Override
    public ResponseCookie logout() {
        return jwtUtils.getCleanJwtCookie();
    }


}
