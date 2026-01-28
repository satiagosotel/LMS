package org.santiago.lms.app.service.user;

import org.santiago.lms.app.dto.request.UserRequest;
import org.santiago.lms.app.dto.response.UserResponse;
import org.santiago.lms.app.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {
    void disableUser(Long id);
    UserResponse save(UserRequest userRequest,Long id);
    void remove(Long id);
    Page<User> findAll(Pageable pageable) ;
    UserResponse findById(Long id);
}
