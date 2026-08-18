package academy.hub.app.enrollment.services.interfaces;

import academy.hub.app.enrollment.dtos.*;
import jakarta.validation.Valid;

import java.util.UUID;

public interface EnrollmentCommandService {

    EnrollmentCreateResponse createEnrollment(@Valid EnrollmentCreateRequest enrollmentCreateRequest);
    EnrollmentDeleteResponse deleteEnrollment(@Valid EnrollmentDeleteRequest enrollmentDeleteRequest);
    EnrollmentUpdateResponse updateEnrollment(UUID id, @Valid EnrollmentUpdateRequest eur);

}
