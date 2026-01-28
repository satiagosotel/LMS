package org.santiago.lms.app.controller;

import jakarta.validation.Valid;
import org.santiago.lms.app.dto.request.AuthRequest;
import org.santiago.lms.app.dto.response.*;
import org.santiago.lms.app.service.auth.AuthService;
import org.santiago.lms.app.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService,UserService userService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}