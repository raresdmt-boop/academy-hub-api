package academy.hub.app.course.repository;

import academy.hub.app.course.dtos.CoursePerDepartmentCount;
import academy.hub.app.course.dtos.CourseSummary;
import academy.hub.app.course.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface CourseRepository extends JpaRepository<Course, UUID> {

    boolean existsByNameIgnoreCase(String name);
    boolean existsById(UUID id);
    Optional<Course> findById(UUID id);
    List<Course> findAll();
    List<Course> findByDepartment(String department);
    long countByDepartment(String department);
    List<CourseSummary> findByDepartmentOrderByNameAsc(String department);


    @Query("""
            select new academy.hub.app.course.dtos.CoursePerDepartmentCount(c.department, count(c.name))
            from Course c
            group by c.department
            order by count(c.name) desc
            """)
    List<CoursePerDepartmentCount> findAndCountPerDepartment();
}
