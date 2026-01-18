package org.santiago.lms.app.service.user;

import org.santiago.lms.app.models.User;
import org.santiago.lms.app.service.Service;

import java.util.Optional;

public interface UserService extends Service<User> {
    Optional<User> findByUsername(String username) ;
    void disableUser(Long id);
}
