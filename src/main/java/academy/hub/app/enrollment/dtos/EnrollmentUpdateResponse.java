package academy.hub.app.enrollment.dtos;

import academy.hub.app.enrollment.models.Enrollment;

import java.util.UUID;

public record EnrollmentUpdateResponse(UUID id, UUID studentId, UUID courseId) {
}
