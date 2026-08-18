package academy.hub.app.enrollment.dtos;

import java.time.LocalDate;
import java.util.UUID;

public record EnrollmentCreateResponse(UUID enrollmentId, UUID studentId, UUID courseID, LocalDate createdAt) {
}
