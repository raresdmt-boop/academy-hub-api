package academy.hub.app.student.services;

import academy.hub.app.student.dtos.StudentBookCount;
import academy.hub.app.student.dtos.StudentSummary;
import academy.hub.app.student.exceptions.NoStudentsFound;
import academy.hub.app.student.exceptions.StudentIdNotFound;
import academy.hub.app.student.exceptions.StudentNotFound;
import academy.hub.app.student.models.Student;
import academy.hub.app.student.repository.StudentRepository;
import academy.hub.app.student.services.interfaces.StudentQueryService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Validated
public class StudentQueryServiceImpl implements StudentQueryService {

    private final StudentRepository studentRepository;

    public StudentQueryServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getStudents() {
        if(studentRepository.findAll().isEmpty()) {
            throw new NoStudentsFound();
        }
        return studentRepository.findAll();
    }
    @Override
    public Student getOldestStudent() {
        if(studentRepository.findTop1ByOrderByAgeDesc() == null) {
            throw new StudentNotFound();
        }
        return studentRepository.findTop1ByOrderByAgeDesc();
    }
    @Override
    public List<Student> getStudentsWithAgeGreaterThan(int age) {
        if(studentRepository.findAllByAgeGreaterThan(age) == null){
            throw new StudentNotFound();
        }
        return  studentRepository.findAllByAgeGreaterThan(age);
    }
    @Override
    public List<Student> getStudentsWithAgeLessThan(int age) {
        if(studentRepository.findAllByAgeLessThan(age) == null){
            throw new StudentNotFound();
        }
        return studentRepository.findAllByAgeLessThan(age);
    }

    @Override
    public List<StudentSummary> findByFirstNameOrderByAgeAsc(String firstName) {
        if(studentRepository.findByFirstNameOrderByAgeAsc(firstName) == null){
            throw new NoStudentsFound();
        }
        return studentRepository.findByFirstNameOrderByAgeAsc(firstName);
    }

    @Override
    public Student getStudentById(UUID studentId) {
        if(studentRepository.findById(studentId).isEmpty()){
            throw new StudentNotFound();
        }
        return studentRepository.findById(studentId).orElseThrow();
    }

    @Override
    public Optional<Student> getById(UUID studentId) {
        if(studentRepository.findById(studentId).isEmpty()){
            throw new StudentIdNotFound();
        }
        return studentRepository.findById(studentId);
    }

    @Override
    public Student getBestStudentWithComparator(Comparator<Student> comparator) {
        return studentRepository.findAll().stream().max(comparator).orElseThrow(StudentNotFound::new);
    }
    @Override
    public Student getByIdJoinFetchBooks(UUID id) {
        return studentRepository.findByIdFetchBooks(id).orElseThrow(StudentIdNotFound::new);
    }
    @Override
    public List<Student> findAllStudentsWithBooks() {
        if(studentRepository.findAllWithBooks().isEmpty()) {
            throw new NoStudentsFound();
        }
        return studentRepository.findAllWithBooks();
    }
    @Override
    public List<StudentBookCount> getStudentBookCounts(){
        if(studentRepository.getStudentBookCounts().isEmpty()) {
            throw new NoStudentsFound();
        }
        return studentRepository.getStudentBookCounts();
    }
    @Override
    public List<Student> getStudentsOrderByBooksDesc() {
        if(studentRepository.getStudentsOrderByBooksDesc().isEmpty()) {
            throw new NoStudentsFound();
        }
        return studentRepository.getStudentsOrderByBooksDesc();
    }


}
