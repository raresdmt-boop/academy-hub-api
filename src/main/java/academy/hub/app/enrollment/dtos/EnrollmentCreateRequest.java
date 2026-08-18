package academy.hub.app.enrollment.dtos;


import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EnrollmentCreateRequest(
        @NotNull(message = "Student ID required")
        UUID studentId,

        @NotNull(message = "Course ID required")
        UUID courseId) {
}
