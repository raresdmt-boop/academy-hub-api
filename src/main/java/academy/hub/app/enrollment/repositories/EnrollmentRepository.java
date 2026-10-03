package academy.hub.app.enrollment.repositories;

import academy.hub.app.enrollment.models.Enrollment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {

    boolean existsByStudentIdAndCourseId(UUID studentId, UUID courseId);
    boolean existsById(UUID id);
    boolean existsByCourseId(UUID courseId);

    Optional<Enrollment> findByStudentIdAndCourseId(UUID studentId, UUID courseId);

    long countByCourseId(UUID courseId);

    @EntityGraph(attributePaths = {"student", "course"})
    List<Enrollment> findAll();

    @EntityGraph(attributePaths = {"student", "course"})
    List<Enrollment> findByStudentId(UUID studentId);

    @EntityGraph(attributePaths = {"student", "course"})
    List<Enrollment> findByCourseId(UUID courseId);

}
