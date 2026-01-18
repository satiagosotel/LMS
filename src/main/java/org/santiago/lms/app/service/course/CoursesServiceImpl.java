package org.santiago.lms.app.service.course;

import org.santiago.lms.app.models.Course;

import java.util.List;
import java.util.Optional;

public class CoursesServiceImpl implements CoursesService{
    @Override
    public Course save(Course course) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public List<Course> findAll() {
        return List.of();
    }

    @Override
    public Optional<Course> findById(Long id) {
        return Optional.empty();
    }
}
