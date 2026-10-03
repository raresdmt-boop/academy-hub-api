package academy.hub.app.enrollment.services;

import academy.hub.app.course.services.interfaces.CourseQueryService;
import academy.hub.app.enrollment.dtos.EnrollmentResponse;
import academy.hub.app.enrollment.exceptions.EnrollmentNotFound;
import academy.hub.app.enrollment.repositories.EnrollmentRepository;
import academy.hub.app.enrollment.services.interfaces.EnrollmentQueryService;
import academy.hub.app.student.services.interfaces.StudentQueryService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Service
@Validated
public class EnrollmentQueryServiceImpl implements EnrollmentQueryService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentQueryService  studentQueryService;
    private final CourseQueryService courseQueryService;

    public EnrollmentQueryServiceImpl(EnrollmentRepository enrollmentRepository, StudentQueryService studentQueryService, CourseQueryService courseQueryService) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentQueryService = studentQueryService;
        this.courseQueryService = courseQueryService;
    }

    @Override
    public List<EnrollmentResponse> getAllEnrollments() {

        return enrollmentRepository.findAll()
                .stream()
                .map(EnrollmentResponse::from)
                .toList();
    }

    @Override
    public List<EnrollmentResponse> getEnrollmentsByStudentId(UUID studentId) {

        studentQueryService.getStudentById(studentId);

        return enrollmentRepository.findByStudentId(studentId)
                .stream()
                .map(EnrollmentResponse::from)
                .toList();
    }

    @Override
    public List<EnrollmentResponse> getEnrollmentsByCourseId(UUID courseId) {

        courseQueryService.getById(courseId);

        return enrollmentRepository.findByCourseId(courseId)
                .stream()
                .map(EnrollmentResponse::from)
                .toList();

    }

    @Override
    public long countEnrollmentsByCourseId(UUID courseId) {

        courseQueryService.getById(courseId);

        return enrollmentRepository.countByCourseId(courseId);

    }

    @Override
    public EnrollmentResponse getEnrollmentById(UUID id) {
        return EnrollmentResponse.from(enrollmentRepository.findById(id).orElseThrow(EnrollmentNotFound::new));
    }

}
