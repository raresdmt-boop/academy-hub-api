package academy.hub.app.enrollment.services;

import academy.hub.app.course.exceptions.NoCourseFound;
import academy.hub.app.course.services.interfaces.CourseQueryService;
import academy.hub.app.enrollment.dtos.*;
import academy.hub.app.enrollment.exceptions.EnrollmentNotFound;
import academy.hub.app.enrollment.exceptions.StudentAlreadyEnrolledInThisCourse;
import academy.hub.app.enrollment.models.Enrollment;
import academy.hub.app.enrollment.repository.EnrollmentRepository;
import academy.hub.app.enrollment.services.interfaces.EnrollmentCommandService;
import academy.hub.app.student.exceptions.StudentNotFound;
import academy.hub.app.student.services.interfaces.StudentQueryService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.UUID;

@Service
@Validated
@Transactional
public class EnrollmentCommandServiceImpl implements EnrollmentCommandService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentQueryService studentQueryService;
    private final CourseQueryService courseQueryService;

    public EnrollmentCommandServiceImpl(EnrollmentRepository enrollmentRepository, StudentQueryService studentQueryService, CourseQueryService courseQueryService) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentQueryService = studentQueryService;
        this.courseQueryService = courseQueryService;
    }

    @Override
    public EnrollmentCreateResponse createEnrollment(EnrollmentCreateRequest enrollmentCreateRequest) {
        if(enrollmentRepository.existsByStudentIdAndCourseId(enrollmentCreateRequest.studentId(),
                enrollmentCreateRequest.courseId())) {
            throw new StudentAlreadyEnrolledInThisCourse();
        }

        LocalDate currentDate = LocalDate.now();
        Enrollment enrollment = new Enrollment(currentDate);
        enrollment.setStudent(studentQueryService.getStudentById(enrollmentCreateRequest.studentId()));
        enrollment.setCourse(courseQueryService.findById(enrollmentCreateRequest.courseId()));
        enrollmentRepository.save(enrollment);

        return new EnrollmentCreateResponse(enrollment.getId(), enrollment.getStudent().getId(),
                enrollment.getCourse().getId(), enrollment.getCreatedAt());

    }

    @Override
    public EnrollmentDeleteResponse deleteEnrollment(EnrollmentDeleteRequest edr) {
        if(!enrollmentRepository.existsByStudentIdAndCourseId(edr.studentId(), edr.courseId())) {
            throw new EnrollmentNotFound();
        }
        Enrollment toDelete = enrollmentRepository
                .findByStudentIdAndCourseId(edr.studentId(), edr.courseId())
                .orElseThrow(EnrollmentNotFound::new);
        enrollmentRepository.delete(toDelete);
        return new EnrollmentDeleteResponse(toDelete.getId(), toDelete.getStudent().getId(),
                toDelete.getCourse().getId());
    }

    @Override
    public EnrollmentUpdateResponse updateEnrollment(UUID id, EnrollmentUpdateRequest eur){
        if(!enrollmentRepository.existsById(id)) {
            throw new EnrollmentNotFound();
        }
        enrollmentRepository
                .findByStudentIdAndCourseId(eur.studentId(), eur.courseId())
                .filter(e -> !e.getId().equals(id))
                .ifPresent(e -> { throw new StudentAlreadyEnrolledInThisCourse(); });
        Enrollment enrollment = enrollmentRepository.findById(id).orElseThrow(EnrollmentNotFound::new);
        enrollment.setCourse(courseQueryService.getById(eur.courseId()).orElseThrow(NoCourseFound::new));
        enrollment.setStudent(studentQueryService.getById(eur.studentId()).orElseThrow(StudentNotFound::new));
        enrollmentRepository.save(enrollment);
        return new EnrollmentUpdateResponse(enrollment.getId(),
                enrollment.getStudent().getId(),
                enrollment.getCourse().getId());
    }

}
