package org.santiago.lms.app.dto.response;

import lombok.Data;
import org.santiago.lms.app.models.Lesson;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Data
public class CourseResponse {
    private Long id;
    private String title;
    private String description;
    private Page<Lesson> lessons;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
