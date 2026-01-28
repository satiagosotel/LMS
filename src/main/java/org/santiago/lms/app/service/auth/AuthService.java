package org.santiago.lms.app.service.auth;

import org.santiago.lms.app.dto.request.AuthRequest;
import org.santiago.lms.app.dto.response.AuthResponse;
import org.santiago.lms.app.dto.response.JwtResponse;
import org.santiago.lms.app.models.User;
import org.santiago.lms.app.repository.UserRepository;
import org.santiago.lms.app.security.JwtTokenProvider;
import org.springframework.security.authentication.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findUserByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Usuario no encontrado"));

        HashMap<String,Object> jwtTokenProviderResponse = jwtTokenProvider.generateToken(user.getId(), user.getUsername());

        JwtResponse jwt = new JwtResponse(
                jwtTokenProviderResponse.get("token").toString(),
                (Long)jwtTokenProviderResponse.get("exp")
        );

        var roles = user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());

        return new AuthResponse(user.getId(), user.getUsername(), roles, jwt);
    }

}