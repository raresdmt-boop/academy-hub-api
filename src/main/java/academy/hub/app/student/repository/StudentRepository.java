package academy.hub.app.student.repository;

import academy.hub.app.student.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {

    Optional<Student> findByFirstName(String firstName);

    boolean existsByEmail(String email);

}
