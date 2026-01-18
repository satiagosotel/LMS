package org.santiago.lms.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CourseRequest {
    @NotBlank(message = "El título es obligatorio")
    private String title;

    private String description;
}