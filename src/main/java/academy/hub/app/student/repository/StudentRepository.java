package academy.hub.app.student.repository;

import academy.hub.app.student.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {


}
