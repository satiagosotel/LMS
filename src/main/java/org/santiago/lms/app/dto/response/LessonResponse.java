package org.santiago.lms.app.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LessonResponse {
    private Long id;
    private String title;
    private String description;
    private String youtubeUrl;
    private Integer orderIndex;
    private Long courseId;
    private String courseTitle;
    private LocalDateTime createdAt;
}
