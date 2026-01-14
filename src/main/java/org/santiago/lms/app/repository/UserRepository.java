package org.santiago.lms.app.repository;

import org.santiago.lms.app.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends CrudRepository<User,Long> {
    @Modifying
    @Query("UPDATE User u SET u.enabled = false WHERE u.id = :id")
    void disableUser(Long id);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findUserByUsername(@Param("username") String username);


}
