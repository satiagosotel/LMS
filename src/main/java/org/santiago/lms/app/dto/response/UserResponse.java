package org.santiago.lms.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.santiago.lms.app.models.Role;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private Boolean enabled;
    private String email;
    private Set<Role> roles = new HashSet<>();

}
