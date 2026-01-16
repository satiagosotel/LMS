package org.santiago.lms.app.service;

import org.santiago.lms.app.models.User;

import java.util.List;
import java.util.Optional;

public interface Service {
    User save(User user);
    void remove(Long id);
    List<User> findAll() ;
    Optional<User> findById(Long id);

}
