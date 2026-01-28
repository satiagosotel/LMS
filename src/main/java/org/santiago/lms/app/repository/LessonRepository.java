package org.santiago.lms.app.repository;

import org.santiago.lms.app.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson,Long> {
}
