package academy.hub.app.enrollment.controllers;

import academy.hub.app.enrollment.dtos.*;
import academy.hub.app.enrollment.services.interfaces.EnrollmentCommandService;
import academy.hub.app.enrollment.services.interfaces.EnrollmentQueryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<List<EnrollmentResponse>> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentQueryService.getAllEnrollments());
    }

    @PostMapping
    public ResponseEntity<EnrollmentCreateResponse> createEnrollment(@Valid @RequestBody EnrollmentCreateRequest request) {
        return ResponseEntity.ok(enrollmentCommandService.createEnrollment(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnrollmentUpdateResponse> updateEnrollment(
            @PathVariable("id") UUID enrollmentId,
            @Valid @RequestBody EnrollmentUpdateRequest request) {
        return ResponseEntity.ok(enrollmentCommandService.updateEnrollment(enrollmentId, request));
    }

    @DeleteMapping
    public ResponseEntity<EnrollmentDeleteResponse> deleteEnrollment(
            @Valid @RequestBody EnrollmentDeleteRequest request) {
        return ResponseEntity.ok(enrollmentCommandService.deleteEnrollment(request));
    }
}
