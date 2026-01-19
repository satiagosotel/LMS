package org.santiago.lms.app.controller;

import org.santiago.lms.app.dto.request.LessonRequest;
import org.santiago.lms.app.dto.response.ApiResponse;
import org.santiago.lms.app.models.Course;
import org.santiago.lms.app.models.Lesson;
import org.santiago.lms.app.service.course.CoursesService;
import org.santiago.lms.app.service.lesson.LessonService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonService lessonService;
    private final CoursesService coursesService;

    public LessonController(LessonService lessonService, CoursesService coursesService) {
        this.lessonService = lessonService;
        this.coursesService = coursesService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Lesson>>> getAllLessons() {
        List<Lesson> lessons = lessonService.findAll();
        return ResponseEntity.ok(ApiResponse.success(lessons));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Lesson>> getById(@PathVariable Long id) {
        Lesson lesson = lessonService.findById(id).orElseThrow();
        return ResponseEntity.ok(ApiResponse.success(lesson));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Lesson>> saveLesson(@RequestBody LessonRequest request) {
        Lesson lesson = new Lesson();
        lesson.setTitle(request.getTitle());
        lesson.setDescription(request.getDescription());
        lesson.setYoutubeUrl(request.getYoutubeUrl());
        lesson.setOrderIndex(request.getOrderIndex());
        setCourse(request, lesson);

        return ResponseEntity.ok(ApiResponse.success(lessonService.save(lesson)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Lesson>> updateLesson(@PathVariable Long id,
                                                            @RequestBody LessonRequest request) {
        Lesson lesson = new Lesson();
        lesson.setId(id);
        lesson.setTitle(request.getTitle());
        lesson.setDescription(request.getDescription());
        lesson.setYoutubeUrl(request.getYoutubeUrl());
        lesson.setOrderIndex(request.getOrderIndex());
        setCourse(request, lesson);

        return ResponseEntity.ok(ApiResponse.success(lessonService.save(lesson)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteLesson(@PathVariable Long id) {
        lessonService.remove(id);
        return ResponseEntity.ok(ApiResponse.success("Leccion eliminada correctamente"));
    }

    private void setCourse(LessonRequest request, Lesson lesson) {
        Course course = coursesService.findById(request.getCourseId()).orElseThrow();
        lesson.setCourse(course);
    }
}