package academy.hub.app.student.services.interfaces;

import academy.hub.app.student.dtos.StudentCreateRequest;
import academy.hub.app.student.dtos.StudentCreateResponse;
import academy.hub.app.student.models.Student;

public interface StudentCommandService {

    StudentCreateResponse addStudent(StudentCreateRequest student);
}
