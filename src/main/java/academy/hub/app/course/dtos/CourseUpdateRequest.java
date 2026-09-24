package academy.hub.app.course.dtos;


import jakarta.validation.constraints.NotBlank;

public record CourseUpdateRequest(
        @NotBlank(message = "Course name required")
        String name,

        @NotBlank(message = "Course department required")
        String department
) {
}
