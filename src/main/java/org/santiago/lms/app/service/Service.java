package org.santiago.lms.app.service;

import org.santiago.lms.app.models.User;

import java.util.List;
import java.util.Optional;

public interface Service<T> {
    T save(T t);
    void remove(Long id);
    List<T> findAll() ;
    Optional<T> findById(Long id);

}
