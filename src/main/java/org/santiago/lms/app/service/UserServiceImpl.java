package org.santiago.lms.app.service;

import org.santiago.lms.app.models.User;
import org.santiago.lms.app.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

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

    @Transactional
    @Override
    public User save(User user)  {
        User editUser;
        if(user.getId() != null && user.getId() > 0){
            editUser= userRepository.findById(user.getId()).orElseThrow();
            editUser.setUsername(user.getUsername() != null ? user.getUsername() : editUser.getUsername());
            editUser.setPassword(user.getPassword() != null ? user.getPassword() : editUser.getPassword());
            editUser.setEmail(user.getEmail() != null ? user.getEmail() : editUser.getEmail());
            editUser.setRoles(!user.getRoles().isEmpty() ? user.getRoles() : editUser.getRoles());
        } else {
            editUser = user;
        }

        return userRepository.save(editUser);
    }

    @Transactional
    @Override
    public void remove(Long id)  {
        userRepository.deleteById(id);
    }


    @Transactional
    @Override
    public void disableUser(Long id) {
        userRepository.disableUser(id);
    }
}
