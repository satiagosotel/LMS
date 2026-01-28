package org.santiago.lms.app.service.course;

import org.santiago.lms.app.dto.response.CourseResponse;
import org.santiago.lms.app.exception.LMSException;
import org.santiago.lms.app.models.Course;
import org.santiago.lms.app.models.Lesson;
import org.santiago.lms.app.repository.CourseRepository;
import org.santiago.lms.app.repository.LessonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.santiago.lms.app.utils.MensajesExcepciones.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class CoursesServiceImpl implements CoursesService {
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;

    public CoursesServiceImpl(CourseRepository courseRepository, LessonRepository lessonRepository) {
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
    }

    public List<Course> findAll() {
        List<Course> courses = (List<Course>)this.courseRepository.findAll();
        if(courses.isEmpty()){
            throw new LMSException(CURSOS_VACIOS, NOT_FOUND);
        }
        return courses;
    }


    public Optional<Course> findById(Long id) {
        Optional<Course> optUser =this.courseRepository.findById(id);
        if(optUser.isEmpty()){
            throw new LMSException(CURSO_NO_EXISTE,NOT_FOUND);
        }        
        return optUser;
    }

    public Course save(Course course) {
        Course courseResponse;
        if(course.getId() != null && course.getId() > 0 ){
            courseResponse = courseRepository.findById(course.getId()).orElseThrow(()->new LMSException(CURSO_NO_EXISTE,NOT_FOUND));
            courseResponse.setTitle(course.getTitle());
            courseResponse.setDescription(course.getDescription());
        }else{
            courseResponse = course;
        }
        return courseRepository.save(courseResponse);
    }

    public void remove(Long id) {
        if(findById(id).isEmpty()){
            throw new LMSException(CURSO_NO_EXISTE,NOT_FOUND);
        }
        courseRepository.deleteById(id);
    }

    public CourseResponse findByIdWithPaginatedLessons(Long id, Pageable pageable) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new LMSException(CURSO_NO_EXISTE, NOT_FOUND));

        Page<Lesson> lessons = lessonRepository.findByCourseIdOrderByOrderIndexAsc(id, pageable);

        CourseResponse response = new CourseResponse();
        response.setId(course.getId());
        response.setTitle(course.getTitle());
        response.setDescription(course.getDescription());
        response.setLessons(lessons);
        response.setCreatedAt(course.getCreatedAt());
        response.setUpdatedAt(course.getUpdatedAt());

        return response;
    }
}
