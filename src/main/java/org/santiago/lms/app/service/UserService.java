package org.santiago.lms.app.service;

import org.santiago.lms.app.exception.UsuarioYaExisteException;
import org.santiago.lms.app.models.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll() ;
    Optional<User> findById(Long id) ;
    Optional<User> findByUsername(String username) ;

    User save(User user) throws UsuarioYaExisteException ;
    void remove(Long id);
    void disableUser(Long id);
}
