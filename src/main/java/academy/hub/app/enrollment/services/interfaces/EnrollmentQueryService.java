package academy.hub.app.enrollment.services.interfaces;

import academy.hub.app.enrollment.dtos.EnrollmentResponse;
import academy.hub.app.enrollment.models.Enrollment;
import java.util.List;
import java.util.UUID;

public interface EnrollmentQueryService {

    List<EnrollmentResponse> getAllEnrollments();
    List<EnrollmentResponse> getEnrollmentsByStudentId(UUID studentId);
    List<EnrollmentResponse> getEnrollmentsByCourseId(UUID courseId);

    long countEnrollmentsByCourseId(UUID courseId);

    EnrollmentResponse getEnrollmentById(UUID id);

}
