package academy.hub.app.student.services;

import academy.hub.app.student.dtos.StudentBookCount;
import academy.hub.app.student.dtos.StudentResponse;
import academy.hub.app.student.exceptions.StudentNotFound;
import academy.hub.app.student.models.Student;
import academy.hub.app.student.repositories.StudentRepository;
import academy.hub.app.student.services.interfaces.StudentQueryService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.*;

@Service
@Validated
public class StudentQueryServiceImpl implements StudentQueryService {

    private final StudentRepository studentRepository;

    public StudentQueryServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }
    @Override
    public StudentResponse getOldestStudent() {

        Student student = studentRepository.findTop1ByOrderByAgeDesc();

        if(student == null) {
            throw new StudentNotFound();
        }
        return StudentResponse.from(student);
    }
    @Override
    public List<StudentResponse> getStudentsWithAgeGreaterThan(int age) {
        return  studentRepository.findAllByAgeGreaterThan(age)
                .stream()
                .map(StudentResponse::from)
                .toList();
    }
    @Override
    public List<StudentResponse> getStudentsWithAgeLessThan(int age) {
        return studentRepository.findAllByAgeLessThan(age)
                .stream()
                .map(StudentResponse::from)
                .toList();
    }

    @Override
    public List<StudentResponse> findByFirstNameOrderByAgeAsc(String firstName) {

        return studentRepository.findByFirstNameOrderByAgeAsc(firstName)
                .stream()
                .map(StudentResponse::from)
                .toList();
    }

    @Override
    public Student getStudentById(UUID studentId) {
        return studentRepository.findById(studentId).orElseThrow(StudentNotFound::new);
    }

    @Override
    public Student getById(UUID studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(StudentNotFound::new);
    }

    @Override
    public Student getByEmail(String email) {
        return studentRepository.findByEmail(email)
                .orElseThrow(StudentNotFound::new);
    }

    @Override
    public Student getBestStudentWithComparator(Comparator<Student> comparator) {
        return studentRepository.findAll().stream().max(comparator).orElseThrow(StudentNotFound::new);
    }
    @Override
    public Student getByIdJoinFetchBooks(UUID id) {
        return studentRepository.findByIdFetchBooks(id).orElseThrow(StudentNotFound::new);
    }
    @Override
    public List<Student> findAllStudentsWithBooks() {
        return studentRepository.findAllStudentsWithBooks();
    }
    @Override
    public List<StudentBookCount> getStudentBookCounts(){

        return studentRepository.getStudentBookCounts();
    }
    @Override
    public List<StudentResponse> getStudentsOrderByBooksDesc() {

        return studentRepository.getStudentsOrderByBooksDesc()
                .stream()
                .map(StudentResponse::from)
                .toList();
    }

    @Override
    public List<StudentResponse> getAll() {

        return studentRepository.findAll()
                .stream()
                .map(StudentResponse::from)
                .toList();
    }

    @Override
    public StudentResponse getResponseById(UUID studentId) {
        return StudentResponse.from(studentRepository.findById(studentId).orElseThrow(StudentNotFound::new));
    }
    @Override
    public StudentResponse findByEmail(String email) {
        return StudentResponse.from(studentRepository.findByEmail(email).orElseThrow(StudentNotFound::new));
    }

}
