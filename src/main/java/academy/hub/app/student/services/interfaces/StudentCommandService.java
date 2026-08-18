package academy.hub.app.student.services.interfaces;

import academy.hub.app.student.dtos.*;
import jakarta.validation.Valid;

public interface StudentCommandService {

    StudentCreateResponse addStudent(@Valid StudentCreateRequest student);
    StudentDeleteResponse deleteStudent(@Valid StudentDeleteRequest studentEmail);
    StudentUpdateResponse updateStudent(@Valid StudentUpdateRequest studentUpdate);
}
