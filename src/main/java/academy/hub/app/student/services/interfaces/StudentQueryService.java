package academy.hub.app.student.services.interfaces;

import academy.hub.app.student.dtos.StudentBookCount;
import academy.hub.app.student.dtos.StudentSummary;
import academy.hub.app.student.models.Student;
import org.springframework.core.annotation.Order;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentQueryService {

    List<Student> getStudents();
    Student getOldestStudent();
    List<Student> getStudentsWithAgeGreaterThan(int age);
    List<Student> getStudentsWithAgeLessThan(int age);
    List<StudentSummary> findByFirstNameOrderByAgeAsc(String firstName);
    Student getStudentById(UUID studentId);
    Optional<Student> getById(UUID studentId);
    Optional<Student> getByEmail(String email);

    //Comparator
    Student getBestStudentWithComparator(Comparator<Student> comparator);

    //Queries
    Student getByIdJoinFetchBooks(UUID id);
    List<Student> findAllStudentsWithBooks();
    List<StudentBookCount> getStudentBookCounts();
    List<Student> getStudentsOrderByBooksDesc();
}
