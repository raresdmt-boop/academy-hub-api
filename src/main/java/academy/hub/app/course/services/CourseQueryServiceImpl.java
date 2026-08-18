package academy.hub.app.course.services;

import academy.hub.app.course.dtos.CoursePerDepartmentCount;
import academy.hub.app.course.dtos.CourseSummary;
import academy.hub.app.course.exceptions.CourseIdNotFound;
import academy.hub.app.course.exceptions.NoCourseFound;
import academy.hub.app.course.models.Course;
import academy.hub.app.course.repository.CourseRepository;
import academy.hub.app.course.services.interfaces.CourseQueryService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Validated
public class CourseQueryServiceImpl implements CourseQueryService {

    private final CourseRepository courseRepository;
    public CourseQueryServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }



    @Override
    public List<Course> findAll() {
        if(courseRepository.findAll().isEmpty()){
            throw new NoCourseFound();
        }
        return courseRepository.findAll();
    }

    @Override
    public List<Course> findByDepartment(String department) {
        if(courseRepository.findByDepartment(department).isEmpty()){
            throw new NoCourseFound();
        }
        return courseRepository.findByDepartment(department);
    }

    @Override
    public long countByDepartment(String department) {

        return  courseRepository.countByDepartment(department);
    }

    @Override
    public List<CourseSummary> findByDepartmentOrderByNameAsc(String department) {
        if(courseRepository.findByDepartmentOrderByNameAsc(department).isEmpty()){
            throw new NoCourseFound();
        }
        return courseRepository.findByDepartmentOrderByNameAsc(department);
    }

    @Override
    public Course findById(UUID id) {
        if(courseRepository.findById(id).isEmpty()){
            throw new NoCourseFound();
        }
        return courseRepository.findById(id).orElseThrow();
    }

    @Override
    public Optional<Course> getById(UUID id) {
        if (courseRepository.findById(id).isEmpty()) throw new CourseIdNotFound();
        return courseRepository.findById(id);
    }

    @Override
    public List<CoursePerDepartmentCount> findAndCountPerDepartment() {
        if(courseRepository.findAndCountPerDepartment().isEmpty()){
            throw new NoCourseFound();
        }
        return courseRepository.findAndCountPerDepartment();
    }
}
