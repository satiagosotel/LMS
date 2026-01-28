package org.santiago.lms.app.repository;

import org.santiago.lms.app.models.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,Long> {
    @Query("SELECT c FROM Course c WHERE c.active = true")
    List<Course> findByActiveTrue();

    @Query("SELECT c FROM Course c WHERE c.active = true")
    Page<Course> findByActiveTrue(Pageable pageable);
}
