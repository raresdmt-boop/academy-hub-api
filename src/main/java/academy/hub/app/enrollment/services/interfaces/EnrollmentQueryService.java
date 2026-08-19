package academy.hub.app.enrollment.services.interfaces;

import academy.hub.app.enrollment.models.Enrollment;

import java.util.List;

public interface EnrollmentQueryService {

    List<Enrollment> getAllEnrollments();

}
