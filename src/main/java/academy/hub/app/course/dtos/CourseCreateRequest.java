package academy.hub.app.course.dtos;

import jakarta.validation.constraints.NotBlank;

public record CourseCreateRequest(
        @NotBlank(message = "course name required")
        String name,

        @NotBlank(message = "course department required")
        String department
) {
}
