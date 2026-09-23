package academy.hub.app.enrollment.dtos;

import academy.hub.app.enrollment.models.Enrollment;

import java.time.LocalDate;
import java.util.UUID;

public record EnrollmentResponse(
        UUID id,
        String studentName,
        String courseName,
        LocalDate createdAt
) {

    public static EnrollmentResponse from(Enrollment enrollment) {
        return new EnrollmentResponse(
                enrollment.getId(),
                enrollment.getStudent().getFirstName() + " " + enrollment.getStudent().getLastName(),
                enrollment.getCourse().getName(),
                enrollment.getCreatedAt()
        );
    }

}
