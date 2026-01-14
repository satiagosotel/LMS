package org.santiago.lms.app.controller;

import org.jspecify.annotations.NonNull;
import org.santiago.lms.app.dto.request.UserRequest;
import org.santiago.lms.app.models.Role;
import org.santiago.lms.app.models.User;
import org.santiago.lms.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    public ResponseEntity<?> getAllUsers() {
        try{
            List<User> users = userService.findAll();
            return ResponseEntity.ok(users);
        }catch (Exception e){
            return error();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try{
            return ResponseEntity.ok(userService.findById(id).orElseThrow());
        } catch (Exception e){
            return error();
        }
    }




    @PostMapping("/create")
    public ResponseEntity<?> saveUser(@RequestBody UserRequest request){
        try{
            User u = new User();
            u.setUsername(request.getUsername());
            u.setPassword(request.getPassword());
            u.setEmail(request.getEmail());
            setRoles(request, u);
            userService.save(u);

            return respuestaExitosa("EXITO","Operacion exitosa!", HttpStatus.OK);
        } catch (Exception e){
            return error();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id,
                                                   @RequestBody UserRequest request) {
        try{
            User u = new User();
            u.setId(id);
            u.setUsername(request.getUsername());
            u.setPassword(request.getPassword());
            u.setEmail(request.getEmail());
            setRoles(request,u);

            userService.save(u);

            return respuestaExitosa("EXITO","Operacion exitosa!", HttpStatus.OK);

        }catch (Exception e){
            return error();
        }
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try{
            userService.remove(id);
            return respuestaExitosa("EXITO","Operacion exitosa!", HttpStatus.OK);
        }
        catch (Exception e){
            return error();
        }
    }

    @PatchMapping("/disable/{id}")
    public ResponseEntity<?> disableUser(@PathVariable Long id) {
        try{
            userService.disableUser(id);
            return respuestaExitosa("EXITO","Operacion exitosa!", HttpStatus.OK);
        } catch (Exception e) {
            return error();
        }
    }


    /*
        Metodos privados para la clase.
     */
    private static void setRoles(UserRequest request, User u) {
        Set<Role> roles = new HashSet<>(request.getRoles());
        u.setRoles(roles);
    }


    private static @NonNull ResponseEntity<Map<String, Object>> error() {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("message", "Error interno del servidor");
        errorBody.put("status","ERROR");
        errorBody.put("timestamp", LocalDateTime.now());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorBody);
    }


    private static @NonNull ResponseEntity<Map<String, String>> respuestaExitosa(String status, String message, HttpStatus httpStatus) {
        Map<String,String> response = new HashMap<>();
        response.put("message",message);
        response.put("status",status);

        return ResponseEntity
                .status(httpStatus)
                .body(response);
    }
}