package academy.hub.app.student.services;


import academy.hub.app.student.dtos.StudentCreateRequest;
import academy.hub.app.student.dtos.StudentCreateResponse;
import academy.hub.app.student.exceptions.EmailAlreadyUsed;
import academy.hub.app.student.models.Student;
import academy.hub.app.student.repository.StudentRepository;
import academy.hub.app.student.services.interfaces.StudentCommandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class StudentCommandServiceImpl implements StudentCommandService {

    private final StudentRepository studentRepository;

    public StudentCommandServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional
    public StudentCreateResponse addStudent(StudentCreateRequest studentCreateRequest) {

        if (studentRepository.existsByEmail(studentCreateRequest.email())) {
            throw new EmailAlreadyUsed();
        }

        Student newStudent = new Student(
                studentCreateRequest.firstName(),
                studentCreateRequest.lastName(),
                studentCreateRequest.email(),
                studentCreateRequest.age());

        Student saved = studentRepository.save(newStudent);

        return new StudentCreateResponse(
                saved.getId(),
                saved.getFirstName(),
                saved.getLastName(),
                saved.getEmail(),
                saved.getAge());
    }

}
