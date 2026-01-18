package org.santiago.lms.app.service.course;

import org.santiago.lms.app.exception.LMSException;
import org.santiago.lms.app.models.Course;
import org.santiago.lms.app.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.santiago.lms.app.utils.MensajesExcepciones.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class CoursesServiceImpl implements CoursesService {
    private final CourseRepository courseRepository;

    public CoursesServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> findAll() {
        List<Course> courses = this.courseRepository.findByActiveTrue();
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


}
