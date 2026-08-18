package academy.hub.app.course.services;

import academy.hub.app.course.dtos.*;
import academy.hub.app.course.exceptions.CourseIdNotFound;
import academy.hub.app.course.exceptions.CourseNameAlreadyInUse;
import academy.hub.app.course.models.Course;
import academy.hub.app.course.repository.CourseRepository;
import academy.hub.app.course.services.interfaces.CourseCommandService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Service
@Validated
public class CourseCommandServiceImpl implements CourseCommandService {

    private final CourseRepository courseRepository;
    public CourseCommandServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


    @Override
    public CourseCreateResponse createCourse(CourseCreateRequest request) {
        if(courseRepository.existsByNameIgnoreCase(request.name())) {
            throw new CourseNameAlreadyInUse();
        }
        Course newCourse = new Course(
                request.name(),
                request.department()
        );
        courseRepository.save(newCourse);
        return new CourseCreateResponse(
                newCourse.getId(),
                newCourse.getName(),
                newCourse.getDepartment()
        );
    }

    @Override
    public CourseDeleteResponse deleteCourse(UUID id) {
        if(!courseRepository.existsById(id)) {
            throw new CourseIdNotFound();
        }
        Course tobeDel  = courseRepository.findById(id).orElseThrow();
        courseRepository.delete(tobeDel);
        return new CourseDeleteResponse(tobeDel.getId(), tobeDel.getName());
    }

    @Override
    public CourseUpdateResponse updateCourse(UUID id, CourseUpdateRequest request) {
        if(!courseRepository.existsById(id)) {
            throw new CourseIdNotFound();
        }
        Course tobeUp  = courseRepository.findById(id).orElseThrow();
        tobeUp.setName(request.name());
        tobeUp.setDepartment(request.department());
        courseRepository.save(tobeUp);
        return new CourseUpdateResponse(tobeUp.getId(), tobeUp.getName(), tobeUp.getDepartment());
    }
}
