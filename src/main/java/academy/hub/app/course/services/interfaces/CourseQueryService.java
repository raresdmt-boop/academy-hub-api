package academy.hub.app.course.services.interfaces;

import academy.hub.app.course.dtos.CoursePerDepartmentCount;
import academy.hub.app.course.dtos.CourseResponse;
import academy.hub.app.course.models.Course;

import java.util.List;
import java.util.UUID;

public interface CourseQueryService {

    List<CourseResponse> findAll();
    List<CourseResponse> findByDepartment(String department);
    long countByDepartment(String department);
    List<CourseResponse> findByDepartmentOrderByNameAsc(String department);
    Course findById(UUID id);
    Course getById(UUID id);

    List<CoursePerDepartmentCount> findAndCountPerDepartment();

    CourseResponse getResponseById(UUID id);

}
