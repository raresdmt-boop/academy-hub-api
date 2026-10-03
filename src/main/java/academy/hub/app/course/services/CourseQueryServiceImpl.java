package academy.hub.app.course.services;

import academy.hub.app.course.dtos.CoursePerDepartmentCount;
import academy.hub.app.course.dtos.CourseResponse;
import academy.hub.app.course.exceptions.NoCourseFound;
import academy.hub.app.course.models.Course;
import academy.hub.app.course.repositories.CourseRepository;
import academy.hub.app.course.services.interfaces.CourseQueryService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Service
@Validated
public class CourseQueryServiceImpl implements CourseQueryService {

    private final CourseRepository courseRepository;
    public CourseQueryServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }



    @Override
    public List<CourseResponse> findAll() {

        return courseRepository.findAll()
                .stream()
                .map(CourseResponse::from)
                .toList();
    }

    @Override
    public List<CourseResponse> findByDepartment(String department) {

        return courseRepository.findByDepartment(department)
                .stream()
                .map(CourseResponse::from)
                .toList();
    }

    @Override
    public long countByDepartment(String department) {

        return  courseRepository.countByDepartment(department);
    }

    @Override
    public List<CourseResponse> findByDepartmentOrderByNameAsc(String department) {

        return courseRepository.findByDepartmentOrderByNameAsc(department)
                .stream()
                .map(CourseResponse::from)
                .toList();
    }

    @Override
    public Course findById(UUID id) {
        return courseRepository.findById(id)
                .orElseThrow(NoCourseFound::new);
    }

    @Override
    public Course getById(UUID id) {
        return courseRepository.findById(id)
                .orElseThrow(NoCourseFound::new);
    }

    @Override
    public List<CoursePerDepartmentCount> findAndCountPerDepartment() {

        return courseRepository.findAndCountPerDepartment();
    }

    @Override
    public CourseResponse getResponseById(UUID id) {
        return CourseResponse.from(courseRepository.findById(id).orElseThrow(NoCourseFound::new));
    }

}
