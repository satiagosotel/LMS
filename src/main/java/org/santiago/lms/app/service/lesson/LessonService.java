package org.santiago.lms.app.service.lesson;

import org.santiago.lms.app.models.Lesson;
import org.santiago.lms.app.service.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LessonService extends Service<Lesson>{
    Page<Lesson> findAll(Pageable pageable);

}
