package academy.hub.app.course.services.interfaces;

import academy.hub.app.course.dtos.*;
import academy.hub.app.course.repository.CourseRepository;
import jakarta.validation.Valid;

import java.util.UUID;

public interface CourseCommandService{

    CourseCreateResponse createCourse(@Valid CourseCreateRequest request);
    CourseDeleteResponse deleteCourse(@Valid UUID id);
    CourseUpdateResponse updateCourse(@Valid UUID id, @Valid CourseUpdateRequest request);

}
