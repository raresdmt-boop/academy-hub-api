package academy.hub.app.enrollment.dtos;

import java.util.UUID;

public record EnrollmentDeleteResponse(UUID enrollmentID, UUID studentID, UUID courseID) {
}
