package org.santiago.lms.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CourseRequest {
    @NotBlank
    private String title;

    private String description;
}
