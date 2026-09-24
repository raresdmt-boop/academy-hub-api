package academy.hub.app.student.services.interfaces;

import academy.hub.app.student.dtos.*;
import jakarta.validation.Valid;

import java.util.UUID;

public interface StudentCommandService {

    StudentCreateResponse addStudent(@Valid StudentCreateRequest student);
    StudentDeleteResponse deleteStudent(UUID id);
    StudentUpdateResponse updateStudent(UUID id, @Valid StudentUpdateRequest studentUpdate);
}
