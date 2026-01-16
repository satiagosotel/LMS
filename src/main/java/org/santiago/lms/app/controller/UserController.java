package org.santiago.lms.app.controller;

import org.santiago.lms.app.dto.request.UserRequest;
import org.santiago.lms.app.dto.response.ApiResponse;
import org.santiago.lms.app.models.Role;
import org.santiago.lms.app.models.User;
import org.santiago.lms.app.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getById(@PathVariable Long id) {
        User user = userService.findById(id).orElseThrow();
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<User>> saveUser(@RequestBody UserRequest request){
        User u = new User();
        u.setUsername(request.getUsername());
        u.setPassword(request.getPassword());
        u.setEmail(request.getEmail());
        setRoles(request, u);

        return ResponseEntity.ok(ApiResponse.success(userService.save(u)));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long id,
                                                   @RequestBody UserRequest request) {

        User u = new User();
        u.setId(id);
        u.setUsername(request.getUsername());
        u.setPassword(request.getPassword());
        u.setEmail(request.getEmail());
        setRoles(request,u);

        return ResponseEntity.ok(ApiResponse.success(userService.save(u)));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long id) {
        userService.remove(id);
        return ResponseEntity.ok(ApiResponse.success("Usuario eliminado correctamente"));
    }

    @PatchMapping("/disable/{id}")
    public ResponseEntity<ApiResponse<String>> disableUser(@PathVariable Long id) {
        userService.disableUser(id);
        return ResponseEntity.ok(ApiResponse.success("Usuario deshabilitado correctamente"));
    }


    /*
        Metodos privados para la clase.
     */
    private static void setRoles(UserRequest request, User u) {
        if(request.getRoles() != null){
            Set<Role> roles = new HashSet<>(request.getRoles());
            u.setRoles(roles);
        }
    }

}