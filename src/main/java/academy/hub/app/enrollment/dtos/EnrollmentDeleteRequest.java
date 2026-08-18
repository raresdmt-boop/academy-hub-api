package academy.hub.app.enrollment.dtos;

import java.util.UUID;

public record EnrollmentDeleteRequest(UUID studentId, UUID courseId) {
}
