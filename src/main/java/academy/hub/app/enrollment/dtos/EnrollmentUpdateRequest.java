package academy.hub.app.enrollment.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EnrollmentUpdateRequest(
        @NotNull
        UUID studentId,
        @NotNull
        UUID courseId) {
}
