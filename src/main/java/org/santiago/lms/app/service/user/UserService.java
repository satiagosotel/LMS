package org.santiago.lms.app.service.user;

import org.santiago.lms.app.dto.request.UserRequest;
import org.santiago.lms.app.dto.response.UserResponse;
import org.santiago.lms.app.models.User;
import org.santiago.lms.app.service.Service;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void disableUser(Long id);
    UserResponse save(UserRequest userRequest,Long id);
    void remove(Long id);
    List<UserResponse> findAll() ;
    UserResponse findById(Long id);
}
