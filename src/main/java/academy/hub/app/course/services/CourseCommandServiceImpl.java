package academy.hub.app.course.services;

import academy.hub.app.course.dtos.*;
import academy.hub.app.course.exceptions.CourseNameAlreadyInUse;
import academy.hub.app.course.exceptions.NoCourseFound;
import academy.hub.app.course.models.Course;
import academy.hub.app.course.repository.CourseRepository;
import academy.hub.app.course.services.interfaces.CourseCommandService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Service
@Validated
@Transactional
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
        Course tobeDel  = courseRepository.findById(id).orElseThrow(NoCourseFound::new);
        courseRepository.delete(tobeDel);
        return new CourseDeleteResponse(tobeDel.getId(), tobeDel.getName());
    }

    @Override
    public CourseUpdateResponse updateCourse(UUID id, CourseUpdateRequest request) {

        Course tobeUp  = courseRepository.findById(id).orElseThrow(NoCourseFound::new);
        courseRepository.findByNameIgnoreCase(request.name())
                .filter(other -> !other.getId().equals(id))
                .ifPresent(other -> { throw new CourseNameAlreadyInUse(); });
        tobeUp.setName(request.name());
        tobeUp.setDepartment(request.department());
        courseRepository.save(tobeUp);
        return new CourseUpdateResponse(tobeUp.getId(), tobeUp.getName(), tobeUp.getDepartment());
    }
}
