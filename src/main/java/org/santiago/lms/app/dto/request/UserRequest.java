package org.santiago.lms.app.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.santiago.lms.app.models.Role;

import java.util.HashSet;
import java.util.Set;
@Data
@AllArgsConstructor
public class UserRequest {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Set<Role> roles = new HashSet<>();

}
