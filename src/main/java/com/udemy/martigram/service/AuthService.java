package com.udemy.martigram.service;

import com.udemy.martigram.dao.RoleRepository;
import com.udemy.martigram.dao.UserRepository;
import com.udemy.martigram.entity.GramRole;
import com.udemy.martigram.entity.GramUser;
import com.udemy.martigram.entity.RoleType;
import com.udemy.martigram.exception.AlreadyExistsException;
import com.udemy.martigram.exception.NotFoundException;
import com.udemy.martigram.model.AuthenticationResponse;
import com.udemy.martigram.model.LoginRequest;
import com.udemy.martigram.model.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request){
        GramRole role = roleRepository.findByRole(RoleType.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Issue generating roles!"));

        if(userRepository.findByUsername(request.getUsername()).isPresent())
            throw new AlreadyExistsException("User " + request.getUsername() + " already exists");

        if(userRepository.findByEmail(request.getEmail()).isPresent())
            throw new AlreadyExistsException("User with email " + request.getEmail() + " already exists");

        GramUser user = GramUser.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse login(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        GramUser user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();

        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
