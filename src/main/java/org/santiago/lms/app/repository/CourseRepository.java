package org.santiago.lms.app.repository;

import org.santiago.lms.app.models.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course,Long> {
    @Query("SELECT c FROM Course c WHERE c.active = true")
    List<Course> findByActiveTrue();

}
