package org.santiago.lms.app.repository;

import org.santiago.lms.app.models.Lesson;
import org.springframework.data.repository.CrudRepository;

public interface LessonRepository extends CrudRepository<Lesson,Long> {
}
