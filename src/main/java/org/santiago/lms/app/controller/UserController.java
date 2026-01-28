package org.santiago.lms.app.controller;

import org.santiago.lms.app.dto.request.UserRequest;
import org.santiago.lms.app.dto.response.ApiResponse;
import org.santiago.lms.app.dto.response.UserResponse;
import org.santiago.lms.app.models.User;
import org.santiago.lms.app.service.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<User>>> getAllUsers(Pageable pageable) {
        Page<User> users = userService.findAll(pageable);
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getById(@PathVariable Long id) {
        UserResponse user = userService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<UserResponse>> saveUser(@RequestBody UserRequest request){


        return ResponseEntity.ok(ApiResponse.success(userService.save(request,null)));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable Long id,
                                                   @RequestBody UserRequest request) {

        return ResponseEntity.ok(ApiResponse.success(userService.save(request,id)));
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


}