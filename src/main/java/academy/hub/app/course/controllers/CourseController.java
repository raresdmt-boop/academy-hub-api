package academy.hub.app.course.controllers;

import academy.hub.app.course.dtos.CourseResponse;
import academy.hub.app.course.services.interfaces.CourseQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseQueryService courseQueryService;

    public CourseController(CourseQueryService courseQueryService) {
        this.courseQueryService = courseQueryService;
    }

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
    return ResponseEntity.ok(courseQueryService.findAll());
    }

}
