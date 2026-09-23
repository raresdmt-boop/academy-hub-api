package academy.hub.app.enrollment.services;

import academy.hub.app.enrollment.dtos.EnrollmentResponse;
import academy.hub.app.enrollment.exceptions.NoEnrollmentsFound;
import academy.hub.app.enrollment.models.Enrollment;
import academy.hub.app.enrollment.repository.EnrollmentRepository;
import academy.hub.app.enrollment.services.interfaces.EnrollmentQueryService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class EnrollmentQueryServiceImpl implements EnrollmentQueryService {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentQueryServiceImpl(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public List<EnrollmentResponse> getAllEnrollments() {
        List<EnrollmentResponse> enrollments = enrollmentRepository.findAll()
                .stream()
                .map(EnrollmentResponse::from)
                .toList();
        if (enrollments.isEmpty()) {
            throw new NoEnrollmentsFound();
        }
        return enrollments;
    }
}
