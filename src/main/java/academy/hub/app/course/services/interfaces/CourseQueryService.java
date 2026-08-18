package academy.hub.app.course.services.interfaces;

import academy.hub.app.course.dtos.CoursePerDepartmentCount;
import academy.hub.app.course.dtos.CourseSummary;
import academy.hub.app.course.models.Course;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseQueryService {

    List<Course> findAll();
    List<Course> findByDepartment(String department);
    long countByDepartment(String department);
    List<CourseSummary> findByDepartmentOrderByNameAsc(String department);
    Course findById(UUID id);
    Optional<Course> getById(UUID id);

    List<CoursePerDepartmentCount> findAndCountPerDepartment();

}
