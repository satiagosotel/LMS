package org.santiago.lms.app.service;

import org.santiago.lms.app.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends Service {
    Optional<User> findByUsername(String username) ;
    void disableUser(Long id);
}
