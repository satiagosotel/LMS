package org.santiago.lms.app.dto.request;

import org.santiago.lms.app.models.Role;

import java.util.HashSet;
import java.util.Set;

public class UserRequest {
    private String username;
    private String password;
    private String email;
    private Set<Role> roles = new HashSet<>();


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
