package org.santiago.lms.app.service;

import org.santiago.lms.app.dto.request.AuthRequest;
import org.santiago.lms.app.dto.response.AuthResponse;
import org.santiago.lms.app.dto.response.JwtResponse;
import org.santiago.lms.app.models.Role;
import org.santiago.lms.app.models.User;
import org.santiago.lms.app.repository.UserRepository;
import org.santiago.lms.app.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private JwtTokenProvider jwtTokenProvider;


    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public JwtResponse login(AuthRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        String token = jwtTokenProvider.generateToken(authentication);
        Set<String> roles = new HashSet<>();
//        u.getRoles().forEach(r -> roles.add(r.toString()));

        return new JwtResponse(token, "Bearer", request.getUsername(), roles);
    }

//    public JwtResponse register(AuthRequest request) {
//
//        User u;
//        if(userRepository.findUserByUsername(request.getUsername()).isPresent()){
//            throw new RuntimeException("El usuario ya existe");
//        }
//        u = new User();
//        u.setUsername(request.getUsername());
//        u.setPassword(passwordEncoder.encode(request.getPassword()));
//        u.setEmail(request.getEmail());
//        u.setEnabled(true);
//
//
//        userRepository.save(u);
//    }
}