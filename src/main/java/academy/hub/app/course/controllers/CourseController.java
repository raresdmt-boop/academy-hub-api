package academy.hub.app.course.controllers;

import academy.hub.app.course.dtos.*;
import academy.hub.app.course.services.interfaces.CourseCommandService;
import academy.hub.app.course.services.interfaces.CourseQueryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Validated
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseQueryService courseQueryService;
    private final CourseCommandService courseCommandService;

    public CourseController(CourseQueryService courseQueryService, CourseCommandService courseCommandService) {
        this.courseQueryService = courseQueryService;
        this.courseCommandService = courseCommandService;
    }

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
    return ResponseEntity.ok(courseQueryService.findAll());
    }

    @PostMapping
    public ResponseEntity<CourseCreateResponse>  createCourse(@Valid @RequestBody CourseCreateRequest courseCreateRequest){
        return ResponseEntity.ok(courseCommandService.createCourse(courseCreateRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseUpdateResponse>  updateCourse(
            @PathVariable UUID id,
            @Valid @RequestBody CourseUpdateRequest courseUpdateRequest){
        return ResponseEntity.ok(courseCommandService.updateCourse(id,courseUpdateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CourseDeleteResponse>  deleteCourse(@PathVariable UUID id){
        return ResponseEntity.ok(courseCommandService.deleteCourse(id));
    }


}
