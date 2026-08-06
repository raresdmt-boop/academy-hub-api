package academy.hub.app.student.services.interfaces;

import academy.hub.app.student.dtos.StudentCreateRequest;
import academy.hub.app.student.dtos.StudentCreateResponse;
import jakarta.validation.Valid;

public interface StudentCommandService {

    StudentCreateResponse addStudent(@Valid StudentCreateRequest student);
}
