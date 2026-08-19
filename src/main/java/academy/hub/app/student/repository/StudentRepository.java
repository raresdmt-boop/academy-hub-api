package academy.hub.app.student.repository;

import academy.hub.app.student.dtos.StudentBookCount;
import academy.hub.app.student.dtos.StudentSummary;
import academy.hub.app.student.models.Student;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {


    Optional<Student> findByFirstName(String firstName);
    boolean existsByEmail(String email);
    boolean existsById(UUID id);
    Optional<Student> findById(UUID id);
    Student findTop1ByOrderByAgeDesc();
    List<Student> findAllByAgeGreaterThan(int age);
    List<Student> findAllByAgeLessThan(int age);
    Optional<Student> getByEmail(String email);

    List<StudentSummary> findByFirstNameOrderByAgeAsc(String firstName);

    @Query("select s from Student s left join fetch s.books where s.id = :id")
    Optional<Student> findByIdFetchBooks(@Param("id") UUID id);

    @EntityGraph(value = "Student.withBooks")
    @Query("select s from Student s")
    List<Student> findAllWithBooks();

    @Query("""
            select new academy.hub.app.student.dtos.StudentBookCount(s.firstName, s.lastName, count(b))
            from Student s left join s.books b
            group by s.id
            """)
    List<StudentBookCount> getStudentBookCounts();

    @Query("select s from Student s order by size(s.books) desc")
    List<Student> getStudentsOrderByBooksDesc();


}
