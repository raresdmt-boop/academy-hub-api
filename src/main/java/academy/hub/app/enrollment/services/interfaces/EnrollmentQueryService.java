package academy.hub.app.enrollment.services.interfaces;

import academy.hub.app.enrollment.dtos.EnrollmentResponse;
import academy.hub.app.enrollment.models.Enrollment;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

public interface EnrollmentQueryService {

    List<EnrollmentResponse> getAllEnrollments();

    EnrollmentResponse getEnrollmentById(UUID id);

}
