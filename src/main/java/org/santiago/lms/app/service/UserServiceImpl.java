package org.santiago.lms.app.service;

import org.santiago.lms.app.exception.UsuarioNoExisteException;
import org.santiago.lms.app.exception.UsuarioYaExisteException;
import org.santiago.lms.app.models.User;
import org.santiago.lms.app.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class    UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll()  {
        return (List<User>) this.userRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findById(Long id)  {
        return this.userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByUsername(String username)  {
        return this.userRepository.findUserByUsername(username);
    }


    /*
    * Actualiza o Crea un usuario.
    * userIn:       Usuario a crear/actualizar
    */
    @Transactional
    @Override
    public User save(User userIn)  throws UsuarioYaExisteException {
        User existsUser;
        if(userIn.getId() != null && userIn.getId() > 0){

            existsUser= userRepository.findById(userIn.getId())
                    .orElseThrow(() -> new UsuarioNoExisteException("Usuario no encontrado con id: " + userIn.getId()));

            existsUser.setUsername(userIn.getUsername() != null ? userIn.getUsername() : existsUser.getUsername());
            existsUser.setPassword(userIn.getPassword() != null ? userIn.getPassword() : existsUser.getPassword());
            existsUser.setEmail(userIn.getEmail() != null ? userIn.getEmail() : existsUser.getEmail());
            existsUser.setRoles(!userIn.getRoles().isEmpty() ? userIn.getRoles() : existsUser.getRoles());
        } else {

            // Validar que el username no exista
            if(userRepository.findUserByUsername(userIn.getUsername()).isPresent()) {
                throw new UsuarioYaExisteException("El username '" + userIn.getUsername() + "' ya está en uso");
            }

            // Validar que el email no exista
            if(userRepository.findUserByEmail(userIn.getEmail()).isPresent()) {
                throw new UsuarioYaExisteException("El email '" + userIn.getEmail() + "' ya está en uso");
            }

            existsUser = userIn;
        }

        return userRepository.save(existsUser);
    }

    @Transactional
    @Override
    public void remove(Long id)  {
        if(findById(id).isEmpty()){
            throw new RuntimeException();
        }
        userRepository.deleteById(id);
    }


    @Transactional
    @Override
    public void disableUser(Long id) {
        if(findById(id).isEmpty()){
            throw new RuntimeException();
        }
        userRepository.disableUser(id);
    }
}
