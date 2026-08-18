package academy.hub.app.enrollment.dtos;

import java.util.UUID;

public record EnrollmentUpdateRequest(UUID studentId, UUID courseId) {
}
