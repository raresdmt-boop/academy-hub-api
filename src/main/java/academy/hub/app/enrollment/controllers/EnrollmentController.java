package academy.hub.app.enrollment.controllers;

import academy.hub.app.enrollment.dtos.*;
import academy.hub.app.enrollment.services.interfaces.EnrollmentCommandService;
import academy.hub.app.enrollment.services.interfaces.EnrollmentQueryService;
import academy.hub.app.system.ApiError;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/enrollments")
@Validated
public class EnrollmentController {

    private final EnrollmentQueryService enrollmentQueryService;
    private final EnrollmentCommandService enrollmentCommandService;
    public EnrollmentController(EnrollmentQueryService enrollmentQueryService, EnrollmentCommandService enrollmentCommandService) {
        this.enrollmentQueryService = enrollmentQueryService;
        this.enrollmentCommandService = enrollmentCommandService;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get an enrollment by ID",
            description = "Returns the enrollment identified by the specified ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Enrollment retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Enrollment not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    public ResponseEntity<EnrollmentResponse> getEnrollmentById(@PathVariable UUID id) {
        return ResponseEntity.ok(enrollmentQueryService.getEnrollmentById(id));
    }

    @GetMapping
    @Operation(
            summary = "Get all enrollments",
            description = "Returns all enrollments. If no enrollments exist, returns an empty list."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Enrollments retrieved successfully"
    )
    public ResponseEntity<List<EnrollmentResponse>> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentQueryService.getAllEnrollments());
    }

    @PostMapping
    @Operation(
            summary = "Create an enrollment",
            description = "Enrolls a student in a course. On success, returns 201 Created " +
                    "and the Location header contains the URL of the created enrollment."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Enrollment created successfully"
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
                    description = "The specified student or course was not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "The student is already enrolled in this course",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    public ResponseEntity<EnrollmentCreateResponse> createEnrollment(@Valid @RequestBody EnrollmentCreateRequest request) {

        EnrollmentCreateResponse response = enrollmentCommandService.createEnrollment(request);

        URI location = URI.create("/api/enrollments/" + response.enrollmentId());

        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an enrollment",
            description = "Updates the enrollment identified by the specified ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Enrollment updated successfully"
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
                    description = "Enrollment, student, or course not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "The student is already enrolled in this course",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    public ResponseEntity<EnrollmentUpdateResponse> updateEnrollment(
            @PathVariable("id") UUID enrollmentId,
            @Valid @RequestBody EnrollmentUpdateRequest request) {
        return ResponseEntity.ok(
                enrollmentCommandService.updateEnrollment(
                        enrollmentId,
                        request));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete an enrollment",
            description = "Deletes an enrollment by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Enrollment deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Enrollment not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    public ResponseEntity<EnrollmentDeleteResponse> deleteEnrollment(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                enrollmentCommandService.deleteEnrollment(id)
        );
    }
}
