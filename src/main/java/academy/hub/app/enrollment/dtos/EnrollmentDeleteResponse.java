package academy.hub.app.enrollment.dtos;

import java.util.UUID;

public record EnrollmentDeleteResponse(
        UUID enrollmentId,
        UUID studentId,
        UUID courseId
) {
}
