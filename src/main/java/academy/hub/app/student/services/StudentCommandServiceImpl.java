package academy.hub.app.student.services;


import academy.hub.app.student.dtos.StudentCreateRequest;
import academy.hub.app.student.dtos.StudentCreateResponse;
import academy.hub.app.student.models.Student;
import academy.hub.app.student.repository.StudentRepository;
import org.springframework.stereotype.Component;

@Component
public class StudentCommandServiceImpl {

    private final StudentRepository studentRepository;

    public StudentCommandServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    StudentCreateResponse addStudent(StudentCreateRequest studentCreateRequest){
        Student newStudent = new Student(studentCreateRequest.firstName(),
                studentCreateRequest.lastName(),
                studentCreateRequest.email(),
                studentCreateRequest.age());
        studentRepository.save(newStudent);

        return new StudentCreateResponse(newStudent.getId(), newStudent.getFirstName(), newStudent.getLastName(), newStudent.getEmail(), newStudent.getAge());

    }



}
