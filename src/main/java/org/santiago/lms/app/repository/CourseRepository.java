package org.santiago.lms.app.repository;

import org.santiago.lms.app.models.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course,Long> {
}
