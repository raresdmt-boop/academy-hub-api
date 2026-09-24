package academy.hub.app.course.services;

import academy.hub.app.course.dtos.CoursePerDepartmentCount;
import academy.hub.app.course.dtos.CourseResponse;
import academy.hub.app.course.dtos.CourseSummary;
import academy.hub.app.course.exceptions.CourseIdNotFound;
import academy.hub.app.course.exceptions.NoCourseFound;
import academy.hub.app.course.models.Course;
import academy.hub.app.course.repository.CourseRepository;
import academy.hub.app.course.services.interfaces.CourseQueryService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
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
    public List<CourseResponse> findAll() {
        List<CourseResponse> courseResponses = courseRepository.findAll()
                .stream()
                .map(CourseResponse::from)
                .toList();
        if (courseResponses.isEmpty()) {
            throw new NoCourseFound();
        }
        return courseResponses;
    }

    @Override
    public List<Course> findByDepartment(String department) {
        List<Course> cursuri = courseRepository.findByDepartment(department)
                .stream()
                .toList();
        if (cursuri.isEmpty()) {
            throw new NoCourseFound();
        }
        return cursuri;
    }

    @Override
    public long countByDepartment(String department) {

        return  courseRepository.countByDepartment(department);
    }

    @Override
    public List<CourseSummary> findByDepartmentOrderByNameAsc(String department) {
        List<CourseSummary> cursuri = courseRepository.findByDepartment(department)
                .stream()
                .map(CourseSummary::from)
                .toList();
        if (cursuri.isEmpty()) {
            throw new NoCourseFound();
        }
        return cursuri;
    }

    @Override
    public Course findById(UUID id) {

        return courseRepository.findById(id).orElseThrow(NoCourseFound::new);
    }

    @Override
    public Optional<Course> getById(UUID id) {
        return Optional.ofNullable(courseRepository.findById(id).orElseThrow(CourseIdNotFound::new));
    }

    @Override
    public List<CoursePerDepartmentCount> findAndCountPerDepartment() {
        List<CoursePerDepartmentCount> lista = courseRepository.findAndCountPerDepartment();
        if(lista.isEmpty()) {
            throw new NoCourseFound();
        }
        return lista;
    }
}
