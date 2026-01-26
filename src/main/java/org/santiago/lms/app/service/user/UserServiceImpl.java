package org.santiago.lms.app.service.user;

import org.santiago.lms.app.dto.request.UserRequest;
import org.santiago.lms.app.dto.response.UserResponse;
import org.santiago.lms.app.exception.LMSException;
import org.santiago.lms.app.models.User;
import org.santiago.lms.app.repository.UserRepository;
import static org.springframework.http.HttpStatus.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.santiago.lms.app.utils.MensajesExcepciones.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /*
    Lista todos los usuarios
     */
    @Transactional(readOnly = true)
    @Override
    public List<UserResponse> findAll()  {
        List<User> users = (List<User>) this.userRepository.findAll();
        if(users.isEmpty()){
            throw new LMSException("", NOT_FOUND);
        }

        List<UserResponse> usersResponse = new ArrayList<>();
        for (User user : users) {
            usersResponse.add(new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEnabled(),
                user.getEmail(),
                user.getRoles()
            ));
        }

        return usersResponse;
    }

    /*
    Busca usuario por el ID del usuario.
     */
    @Transactional(readOnly = true)
    @Override
    public UserResponse findById(Long id)  {
        Optional<User> optUser =this.userRepository.findById(id);
        if(optUser.isEmpty()){
            throw new LMSException(USUARIO_NO_EXISTE,NOT_FOUND);
        }

        return new UserResponse(optUser.get().getId(), optUser.get().getUsername(),optUser.get().getEnabled(),optUser.get().getEmail(),optUser.get().getRoles());
    }

    /*
    * Actualiza o Crea un usuario.
    * userIn:       Usuario a crear/actualizar
    */
    @Transactional
    @Override
    public UserResponse save(UserRequest userIn, Long id){
        User user;

        if(id != null && id > 0){
            // UPDATE: buscar usuario existente
            user = userRepository.findById(id)
                    .orElseThrow(() -> new LMSException(USUARIO_NO_EXISTE, NOT_FOUND));

            if(userIn.getUsername() != null && !userIn.getUsername().isBlank()){
                user.setUsername(userIn.getUsername());
            }
            if(userIn.getPassword() != null && !userIn.getPassword().isBlank()){
                user.setPassword(passwordEncoder.encode(userIn.getPassword()));
            }
            if(userIn.getEmail() != null && !userIn.getEmail().isBlank()){
                user.setEmail(userIn.getEmail());
            }
            if(userIn.getRoles() != null && !userIn.getRoles().isEmpty()){
                user.setRoles(userIn.getRoles());
            }
            if(userIn.getEnabled() != null){
                user.setEnabled(userIn.getEnabled());
            }
        } else {
            // CREATE: validaciones y nuevo usuario
            if(userIn.getUsername() == null || userIn.getUsername().isBlank()){
                throw new LMSException("Debe ingresar el nombre de usuario", BAD_REQUEST);
            }
            if(userIn.getPassword() == null || userIn.getPassword().isBlank()){
                throw new LMSException("Debe ingresar la contrasenha", BAD_REQUEST);
            }
            if(userIn.getEmail() == null || userIn.getEmail().isBlank()){
                throw new LMSException("Debe ingresar el correo", BAD_REQUEST);
            }
            if(userIn.getRoles() == null || userIn.getRoles().isEmpty()){
                throw new LMSException("Debe tener por lo menos un rol", BAD_REQUEST);
            }

            if(userRepository.findUserByUsername(userIn.getUsername()).isPresent()) {
                throw new LMSException("El username '" + userIn.getUsername() + "' ya está en uso", BAD_REQUEST);
            }
            if(userRepository.findUserByEmail(userIn.getEmail()).isPresent()) {
                throw new LMSException("El email '" + userIn.getEmail() + "' ya está en uso", BAD_REQUEST);
            }

            user = new User();
            user.setUsername(userIn.getUsername());
            user.setPassword(passwordEncoder.encode(userIn.getPassword()));
            user.setEmail(userIn.getEmail());
            user.setEnabled(userIn.getEnabled() != null ? userIn.getEnabled() : true);
            user.setRoles(userIn.getRoles());
        }

        User savedUser = userRepository.save(user);

        return new UserResponse(
            savedUser.getId(),
            savedUser.getUsername(),
            savedUser.getEnabled(),
            savedUser.getEmail(),
            savedUser.getRoles()
        );
    }

    /*
        Remueve un usuario de la base de datos.
         */
    @Transactional
    @Override
    public void remove(Long id)  {
        if(findById(id) == null){
            throw new LMSException(USUARIO_NO_EXISTE,NOT_FOUND);
        }
        userRepository.deleteById(id);
    }


    @Transactional
    @Override
    public void disableUser(Long id) {
        if(findById(id) == null){
            throw new LMSException(USUARIO_NO_EXISTE,NOT_FOUND);
        }
        userRepository.disableUser(id);
    }
}
