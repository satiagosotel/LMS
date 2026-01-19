package org.santiago.lms.app.service.lesson;

import org.santiago.lms.app.exception.LMSException;
import org.santiago.lms.app.models.Lesson;
import org.santiago.lms.app.repository.LessonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.santiago.lms.app.utils.MensajesExcepciones.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;

    public LessonServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public List<Lesson> findAll() {
        List<Lesson> lessons = (List<Lesson>) lessonRepository.findAll();
        if (lessons.isEmpty()) {
            throw new LMSException(LECCIONES_VACIAS, NOT_FOUND);
        }
        return lessons;
    }

    @Override
    public Optional<Lesson> findById(Long id) {
        Optional<Lesson> optLesson = lessonRepository.findById(id);
        if (optLesson.isEmpty()) {
            throw new LMSException(LECCION_NO_EXISTE, NOT_FOUND);
        }
        return optLesson;
    }

    @Override
    public Lesson save(Lesson lesson) {
        Lesson lessonResponse;
        if (lesson.getId() != null && lesson.getId() > 0) {
            lessonResponse = lessonRepository.findById(lesson.getId())
                    .orElseThrow(() -> new LMSException(LECCION_NO_EXISTE, NOT_FOUND));
            lessonResponse.setTitle(lesson.getTitle());
            lessonResponse.setDescription(lesson.getDescription());
            lessonResponse.setYoutubeUrl(lesson.getYoutubeUrl());
            lessonResponse.setOrderIndex(lesson.getOrderIndex());
            lessonResponse.setCourse(lesson.getCourse());
        } else {
            lessonResponse = lesson;
        }
        return lessonRepository.save(lessonResponse);
    }

    @Override
    public void remove(Long id) {
        if (findById(id).isEmpty()) {
            throw new LMSException(LECCION_NO_EXISTE, NOT_FOUND);
        }
        lessonRepository.deleteById(id);
    }
}
