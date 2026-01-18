package org.santiago.lms.app.service.user;

import org.santiago.lms.app.exception.UsuarioException;
import org.santiago.lms.app.models.User;
import org.santiago.lms.app.repository.UserRepository;
import static org.springframework.http.HttpStatus.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.santiago.lms.app.utils.MensajesExcepciones.*;

@Service
public class    UserServiceImpl implements UserService {

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
    public List<User> findAll()  {
        List<User> users = (List<User>) this.userRepository.findAll();
        if(users.isEmpty()){
            throw new UsuarioException("", NOT_FOUND);
        }
        return (List<User>) this.userRepository.findAll();
    }

    /*
    Busca usuario por el ID del usuario.
     */
    @Transactional(readOnly = true)
    @Override
    public Optional<User> findById(Long id)  {
        Optional<User> optUser =this.userRepository.findById(id);
        if(optUser.isEmpty()){
            throw new UsuarioException(USUARIO_NO_EXISTE,NOT_FOUND);
        }
        return optUser;
    }

    /*
    Busca usuario por el nombre del usuario.
     */
    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByUsername(String username)  {
        Optional<User> optUser =this.userRepository.findUserByUsername(username);
        if(optUser.isEmpty()){
            throw new UsuarioException(USUARIO_NO_EXISTE,NOT_FOUND);
        }
        return optUser;
    }


    /*
    * Actualiza o Crea un usuario.
    * userIn:       Usuario a crear/actualizar
    */
    @Transactional
    @Override
    public User save(User userIn){
        User userResponse;
        // Validar que el username no este en uso
        if(userRepository.findUserByUsername(userIn.getUsername()).isPresent()) {
            throw new UsuarioException("El username '" + userIn.getUsername() + "' ya está en uso",BAD_REQUEST);
        }

        // Validar que el correo no este en uso
        if(userRepository.findUserByEmail(userIn.getEmail()).isPresent()) {
            throw new UsuarioException("El email '" + userIn.getEmail() + "' ya está en uso",BAD_REQUEST);
        }

        if(userIn.getId() != null && userIn.getId() > 0){
            userResponse = findById(userIn.getId()).orElseThrow();
            if(userIn.getUsername() != null && !userIn.getUsername().isBlank()){
                userResponse.setUsername(userIn.getUsername());
            }
            if(userIn.getPassword() != null && !userIn.getPassword().isBlank()){
                userResponse.setPassword(passwordEncoder.encode(userIn.getPassword()));
            }
            if(userIn.getEmail() != null && !userIn.getEmail().isBlank()){
                userResponse.setEmail(userIn.getEmail());
            }
            if(!userIn.getRoles().isEmpty()){
                userResponse.setRoles(userIn.getRoles());
            }
        } else {
            if(userIn.getUsername() == null || userIn.getUsername().isBlank()){
                throw new UsuarioException("Debe ingresar el nombre de usuario",BAD_REQUEST);
            }
            if(userIn.getPassword() == null || userIn.getPassword().isBlank()){
                throw new UsuarioException("Debe ingresar la contrasenha",BAD_REQUEST);
            }
            if(userIn.getEmail() == null || userIn.getEmail().isBlank()){
                throw new UsuarioException("Debe ingresar el correo",BAD_REQUEST);
            }
            if(userIn.getRoles().isEmpty()){
                throw new UsuarioException("Debe tener por lo menos un rol",BAD_REQUEST);
            }

            userResponse = userIn;
            /*
                Encriptamos la contransenha
             */
            userResponse.setPassword(passwordEncoder.encode(userResponse.getPassword()));
        }


        return userRepository.save(userResponse);
    }

    /*
    Remueve un usuario de la base de datos.
     */
    @Transactional
    @Override
    public void remove(Long id)  {
        if(findById(id).isEmpty()){
            throw new UsuarioException(USUARIO_NO_EXISTE,NOT_FOUND);
        }
        userRepository.deleteById(id);
    }


    @Transactional
    @Override
    public void disableUser(Long id) {
        if(findById(id).isEmpty()){
            throw new UsuarioException(USUARIO_NO_EXISTE,NOT_FOUND);
        }
        userRepository.disableUser(id);
    }
}
