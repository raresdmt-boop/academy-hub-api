package academy.hub.app.student.services;


import academy.hub.app.student.dtos.*;
import academy.hub.app.student.exceptions.EmailAlreadyUsed;
import academy.hub.app.student.exceptions.EmailNotFound;
import academy.hub.app.student.exceptions.StudentIdNotFound;
import academy.hub.app.student.exceptions.StudentNotFound;
import academy.hub.app.student.models.Student;
import academy.hub.app.student.repository.StudentRepository;
import academy.hub.app.student.services.interfaces.StudentCommandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.UUID;

@Service
@Validated
@Transactional
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

    @Override
    @Transactional
    public StudentDeleteResponse deleteStudent(UUID id) {

        Student s = studentRepository.findById(id).orElseThrow(StudentIdNotFound::new);
        studentRepository.delete(s);
        return new StudentDeleteResponse(s.getId(), s.getFirstName(),
                s.getLastName(), s.getEmail());
    }

    @Override
    @Transactional
    public StudentUpdateResponse updateStudent(UUID id, StudentUpdateRequest studentUpdate) {

        Student s = studentRepository.findById(id).orElseThrow(StudentIdNotFound::new);

        if(studentRepository.existsByEmail(studentUpdate.email())) {
            throw new EmailAlreadyUsed();
        }

        s.setFirstName(studentUpdate.firstName());
        s.setLastName(studentUpdate.lastName());
        s.setEmail(studentUpdate.email());
        s.setAge(studentUpdate.age());

        return new StudentUpdateResponse(s.getId(), s.getFirstName(), s.getLastName(), s.getEmail(), s.getAge());

    }

}
