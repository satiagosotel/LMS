package org.santiago.lms.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LessonRequest {
    @NotBlank
    private String title;

    private String description;

    @NotBlank
    private String youtubeUrl;

    @NotNull
    private Integer orderIndex;

    @NotNull
    private Long courseId;
}