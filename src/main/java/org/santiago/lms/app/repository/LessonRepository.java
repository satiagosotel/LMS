package org.santiago.lms.app.repository;

import org.santiago.lms.app.models.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson,Long> {
    Page<Lesson> findByCourseIdOrderByOrderIndexAsc(Long courseId, Pageable pageable);
}
