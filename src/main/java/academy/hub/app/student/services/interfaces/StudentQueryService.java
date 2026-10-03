package academy.hub.app.student.services.interfaces;

import academy.hub.app.student.dtos.StudentBookCount;
import academy.hub.app.student.dtos.StudentResponse;
import academy.hub.app.student.models.Student;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public interface StudentQueryService {

    List<Student> getStudents();

    StudentResponse getOldestStudent();

    List<StudentResponse> getStudentsWithAgeGreaterThan(int age);

    List<StudentResponse> getStudentsWithAgeLessThan(int age);

    List<StudentResponse> findByFirstNameOrderByAgeAsc(String firstName);

    Student getStudentById(UUID studentId);

    Student getById(UUID studentId);

    Student getByEmail(String email);

    //Comparator
    Student getBestStudentWithComparator(Comparator<Student> comparator);

    //Queries
    Student getByIdJoinFetchBooks(UUID id);

    List<Student> findAllStudentsWithBooks();

    List<StudentBookCount> getStudentBookCounts();

    List<StudentResponse> getStudentsOrderByBooksDesc();

    List<StudentResponse> getAll();

    StudentResponse getResponseById(UUID studentId);

    StudentResponse findByEmail(String email);

}
