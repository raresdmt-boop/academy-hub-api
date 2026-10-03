package academy.hub.app.course.controllers;

import academy.hub.app.course.dtos.*;
import academy.hub.app.course.services.interfaces.CourseCommandService;
import academy.hub.app.course.services.interfaces.CourseQueryService;
import academy.hub.app.enrollment.dtos.EnrollmentResponse;
import academy.hub.app.enrollment.services.interfaces.EnrollmentQueryService;
import academy.hub.app.system.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@Validated
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseQueryService courseQueryService;
    private final CourseCommandService courseCommandService;
    private final EnrollmentQueryService enrollmentQueryService;

    public CourseController(CourseQueryService courseQueryService, CourseCommandService courseCommandService, EnrollmentQueryService enrollmentQueryService) {
        this.courseQueryService = courseQueryService;
        this.courseCommandService = courseCommandService;
        this.enrollmentQueryService = enrollmentQueryService;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a course by ID",
            description = "Returns the course identified by the specified ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Course retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Course not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable UUID id) {
        return ResponseEntity.ok(courseQueryService.getResponseById(id));
    }

    @GetMapping("/{id}/enrollments")
    @Operation(
            summary = "Get a course's enrollments",
            description = "Returns all enrollments for the specified course. " +
                    "If the course exists but has no enrollments, an empty list is returned."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Enrollments retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Course not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    public ResponseEntity<List<EnrollmentResponse>> getCourseEnrollments(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                enrollmentQueryService.getEnrollmentsByCourseId(id)
        );
    }

    @GetMapping
    @Operation(
            summary = "Get courses",
            description = "Returns all courses. If no courses exist, returns an empty list. " +
                    "Optionally filters courses by department and sorts the filtered results by name."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Courses retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid query parameter",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    public ResponseEntity<List<CourseResponse>> findAll(

            @Parameter(
                    description = "Department used to filter courses",
                    example = "Computer Science"
            )
            @RequestParam(required = false) String department,

            @Parameter(
                    description = "Sort option for the filtered courses. Use 'name' to sort by course name ascending.",
                    example = "name"
            )
            @RequestParam(required = false) String sort) {

        if (department != null && "name".equalsIgnoreCase(sort)) {
            return ResponseEntity.ok(
                    courseQueryService.findByDepartmentOrderByNameAsc(department)
            );
        }

        if (department != null) {
            return ResponseEntity.ok(
                    courseQueryService.findByDepartment(department)
            );
        }

        return ResponseEntity.ok(
                courseQueryService.findAll()
        );
    }

    @GetMapping("/stats/per-department")
    @Operation(
            summary = "Get course count per department",
            description = "Returns the number of courses grouped by department."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Course statistics retrieved successfully"
            )
    })
    public ResponseEntity<List<CoursePerDepartmentCount>> getCourseCountPerDepartment() {

        return ResponseEntity.ok(
                courseQueryService.findAndCountPerDepartment()
        );
    }

    @GetMapping("/{id}/enrollments/count")
    @Operation(
            summary = "Count enrollments for a course",
            description = "Returns the number of enrollments associated with the specified course."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Enrollment count retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Course not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    public ResponseEntity<Long> countEnrollmentsByCourseId(
            @Parameter(
                    description = "ID of the course",
                    required = true
            )
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                enrollmentQueryService.countEnrollmentsByCourseId(id)
        );
    }

    @PostMapping
    @Operation(
            summary = "Create a course",
            description = "Creates a new course. On success, returns 201 Created " +
                    "and the Location header contains the URL of the created course."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Course created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "A course with the same name already exists",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    public ResponseEntity<CourseCreateResponse> createCourse(@Valid @RequestBody CourseCreateRequest courseCreateRequest) {

        CourseCreateResponse response = courseCommandService.createCourse(courseCreateRequest);

        URI location = URI.create("/api/courses/" + response.id());

        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a course",
            description = "Updates the course identified by the specified ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Course updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Course not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "A course with the same name already exists",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    public ResponseEntity<CourseUpdateResponse>  updateCourse(
            @PathVariable UUID id,
            @Valid @RequestBody CourseUpdateRequest courseUpdateRequest){
        return ResponseEntity.ok(courseCommandService.updateCourse(
                id,
                courseUpdateRequest)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a course",
            description = "Deletes a course by its ID. A course cannot be deleted " +
                    "while it has active enrollments."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Course deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Course not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Course cannot be deleted because it has active enrollments",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    public ResponseEntity<CourseDeleteResponse> deleteCourse(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                courseCommandService.deleteCourse(id)
        );
    }


}
