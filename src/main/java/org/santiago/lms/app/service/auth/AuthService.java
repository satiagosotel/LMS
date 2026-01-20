package org.santiago.lms.app.service.auth;

import org.santiago.lms.app.dto.request.AuthRequest;
import org.santiago.lms.app.dto.response.JwtResponse;
import org.santiago.lms.app.repository.UserRepository;
import org.santiago.lms.app.security.JwtTokenProvider;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    private AuthenticationManager authenticationManager;


    private JwtTokenProvider jwtTokenProvider;


    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public JwtResponse login(AuthRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        HashMap<String,Object> jwtTokenProviderResponse = jwtTokenProvider.generateToken(authentication);

        return new JwtResponse(
                jwtTokenProviderResponse.get("token").toString(),
                (Long)jwtTokenProviderResponse.get("exp")
        );
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