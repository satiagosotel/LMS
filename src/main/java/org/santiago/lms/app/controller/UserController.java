package org.santiago.lms.app.controller;

import org.santiago.lms.app.dto.request.UserRequest;
import org.santiago.lms.app.dto.response.JwtResponse;
import org.santiago.lms.app.models.Role;
import org.santiago.lms.app.models.User;
import org.santiago.lms.app.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id){
        return ResponseEntity.ok(userService.findById(id).orElseThrow());
    }


    @PostMapping("/create")
    public ResponseEntity<User> saveUser(@RequestBody UserRequest request){
        User u = new User();
        u.setUsername(request.getUsername());
        u.setPassword(request.getPassword());
        u.setEmail(request.getEmail());


        setRoles(request, u);

        return ResponseEntity.ok(userService.save(u));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                                   @RequestBody UserRequest request) {
        User u = new User();
        u.setId(id);
        u.setUsername(request.getUsername());
        u.setPassword(request.getPassword());
        u.setEmail(request.getEmail());
        setRoles(request,u);

        return ResponseEntity.ok(userService.save(u));
    }

    private static void setRoles(UserRequest request, User u) {
        Set<Role> roles = new HashSet<>(request.getRoles());
        u.setRoles(roles);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/disable/{id}")
    public ResponseEntity<Void> disableUser(@PathVariable Long id) {
        userService.disableUser(id);
        return ResponseEntity.noContent().build();
    }
}