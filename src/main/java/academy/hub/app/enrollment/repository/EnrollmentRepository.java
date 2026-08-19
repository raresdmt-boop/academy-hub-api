package academy.hub.app.enrollment.repository;

import academy.hub.app.enrollment.models.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {

    boolean existsByStudentIdAndCourseId(UUID studentId, UUID courseId);
    boolean existsById(UUID id);
    Enrollment getById(UUID id);
    Optional<Enrollment> findByStudentIdAndCourseId(UUID studentId, UUID courseId);
    List<Enrollment> findAll();

}
