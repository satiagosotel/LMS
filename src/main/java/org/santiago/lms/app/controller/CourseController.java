package org.santiago.lms.app.controller;

import org.santiago.lms.app.dto.request.CourseRequest;
import org.santiago.lms.app.dto.response.ApiResponse;
import org.santiago.lms.app.models.Course;
import org.santiago.lms.app.service.course.CoursesService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CoursesService coursesService;

    public CourseController(CoursesService coursesService) {
        this.coursesService = coursesService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Course>>> getAllCourses() {
        List<Course> courses = coursesService.findAll();
        return ResponseEntity.ok(ApiResponse.success(courses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> getById(@PathVariable Long id) {
        Course course = coursesService.findById(id).orElseThrow();
        return ResponseEntity.ok(ApiResponse.success(course));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Course>> saveCourse(@RequestBody CourseRequest request) {
        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());

        return ResponseEntity.ok(ApiResponse.success(coursesService.save(course)));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Course>> updateCourse(@PathVariable Long id,
                                                            @RequestBody CourseRequest request) {
        Course course = new Course();
        course.setId(id);
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());

        return ResponseEntity.ok(ApiResponse.success(coursesService.save(course)));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCourse(@PathVariable Long id) {
        coursesService.remove(id);
        return ResponseEntity.ok(ApiResponse.success("Curso eliminado correctamente"));
    }
}
