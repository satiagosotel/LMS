package org.santiago.lms.app.dto.response;

import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
public class AuthResponse {
    private Long id;
    private String username;
    private Set<String> roles;
    private JwtResponse jwt;
}