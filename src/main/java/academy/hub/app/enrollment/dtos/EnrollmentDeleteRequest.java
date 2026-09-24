package academy.hub.app.enrollment.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EnrollmentDeleteRequest(
        @NotNull
        UUID studentId,
        @NotNull
        UUID courseId) {
}
