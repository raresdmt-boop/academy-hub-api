package academy.hub.app.student.repository;

import academy.hub.app.student.dtos.StudentCreateRequest;
import academy.hub.app.student.dtos.StudentCreateResponse;
import academy.hub.app.student.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {

    Optional<Student> findById(UUID id);
    UUID findByFirstName(String firstName);
    Optional<Student> findByEmail(String email);


}
