package academy.hub.app.student.controllers;

import academy.hub.app.book.dtos.BookResponse;
import academy.hub.app.book.services.interfaces.BookQueryService;
import academy.hub.app.enrollment.dtos.EnrollmentResponse;
import academy.hub.app.enrollment.services.interfaces.EnrollmentQueryService;
import academy.hub.app.system.ApiError;
import academy.hub.app.student.dtos.*;
import academy.hub.app.student.services.interfaces.StudentCommandService;
import academy.hub.app.student.services.interfaces.StudentQueryService;
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
@RequestMapping("/api/students")
public class StudentController {

    private final StudentQueryService studentQueryService;
    private final StudentCommandService studentCommandService;
    private final BookQueryService bookQueryService;
    private final EnrollmentQueryService enrollmentQueryService;


    public StudentController(
            StudentQueryService studentQueryService,
            StudentCommandService studentCommandService,
            BookQueryService bookQueryService,
            EnrollmentQueryService enrollmentQueryService) {
        this.studentQueryService = studentQueryService;
        this.studentCommandService = studentCommandService;
        this.bookQueryService = bookQueryService;
        this.enrollmentQueryService = enrollmentQueryService;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a student by ID",
            description = "Returns the student identified by the specified ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Student retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Student not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable UUID id) {
        return ResponseEntity.ok(studentQueryService.getResponseById(id));
    }

    @GetMapping("/{id}/enrollments")
    @Operation(
            summary = "Get a student's enrollments",
            description = "Returns all enrollments for the specified student. " +
                    "If the student exists but has no enrollments, an empty list is returned."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Enrollments retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Student not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    public ResponseEntity<List<EnrollmentResponse>> getStudentEnrollments(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                enrollmentQueryService.getEnrollmentsByStudentId(id)
        );
    }

    @GetMapping("/{id}/books")
    @Operation(
            summary = "Get a student's books",
            description = "Returns all books assigned to the specified student. " +
                    "If the student exists but has no books, an empty list is returned."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Books retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Student not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    public ResponseEntity<List<BookResponse>> getStudentBooks(
            @PathVariable UUID id) {

        return ResponseEntity.ok(bookQueryService.getStudentBooks(id));
    }

    @GetMapping("/{id}/books/count")
    @Operation(
            summary = "Count a student's books",
            description = "Returns the number of books assigned to the specified student."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Book count retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Student not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    public ResponseEntity<Long> countStudentBooks(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                bookQueryService.countBooksByStudentId(id)
        );
    }

    @GetMapping("/search")
    @Operation(
            summary = "Get a student by email",
            description = "Returns the student associated with the specified email address."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Student retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Student not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    public ResponseEntity<StudentResponse> getStudentByEmail(
            @RequestParam("email") String email) {
        return ResponseEntity.ok(
                studentQueryService.findByEmail(email)
        );
    }

    @GetMapping
    @Operation(
            summary = "Get students",
            description = "Returns all students. If no students exist, returns an empty list. " +
                    "Optionally filters students by minimum age, maximum age, first name, or email using query parameters."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Students retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid query parameter",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Student not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    public ResponseEntity<List<StudentResponse>> findAll(

            @Parameter(
                    description = "Minimum age used to filter students",
                    example = "20"
            )
            @RequestParam(required = false) Integer minAge,

            @Parameter(
                    description = "Maximum age used to filter students",
                    example = "30"
            )
            @RequestParam(required = false) Integer maxAge,

            @Parameter(
                    description = "First name used to filter students. Results are ordered by age ascending.",
                    example = "Ana"
            )
            @RequestParam(required = false) String firstName,

            @Parameter(
                    description = "Email used to find a student",
                    example = "ana.dumitrescu@gmail.com"
            )
            @RequestParam(required = false) String email) {

        if (minAge != null) {
            return ResponseEntity.ok(
                    studentQueryService.getStudentsWithAgeGreaterThan(minAge)
            );
        }

        if (maxAge != null) {
            return ResponseEntity.ok(
                    studentQueryService.getStudentsWithAgeLessThan(maxAge)
            );
        }

        if (firstName != null) {
            return ResponseEntity.ok(
                    studentQueryService.findByFirstNameOrderByAgeAsc(firstName)
            );
        }

        if (email != null) {
            return ResponseEntity.ok(
                    List.of(StudentResponse.from(studentQueryService.getByEmail(email)))
            );
        }

        return ResponseEntity.ok(
                studentQueryService.getAll()
        );
    }

    @GetMapping("/stats/book-counts")
    @Operation(
            summary = "Get book count per student",
            description = "Returns each student with the number of books assigned to them."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Student book statistics retrieved successfully"
            )
    })
    public ResponseEntity<List<StudentBookCount>> getStudentBookCounts() {

        return ResponseEntity.ok(
                studentQueryService.getStudentBookCounts()
        );
    }

    @GetMapping("/stats/top-by-books")
    @Operation(
            summary = "Get students ordered by book count",
            description = "Returns all students ordered by their number of books in descending order."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Students retrieved successfully"
            )
    })
    public ResponseEntity<List<StudentResponse>> getStudentsOrderByBooksDesc() {

        return ResponseEntity.ok(
                studentQueryService.getStudentsOrderByBooksDesc()
        );
    }

    @GetMapping("/oldest")
    @Operation(
            summary = "Get the oldest student",
            description = "Returns the student with the highest age."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Oldest student retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No students found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    public ResponseEntity<StudentResponse> getOldestStudent() {

        return ResponseEntity.ok(
                studentQueryService.getOldestStudent()
        );
    }

    @PostMapping
    @Operation(
            summary = "Create a student",
            description = "Creates a new student. On success, returns 201 Created " +
                    "and the Location header contains the URL of the created student."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Student created successfully"
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
                    description = "Email address is already in use",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    public ResponseEntity<StudentCreateResponse> createStudent(
            @Valid @RequestBody StudentCreateRequest studentCreateRequest) {

        StudentCreateResponse response = studentCommandService.addStudent(studentCreateRequest);

        URI location = URI.create("/api/students/" + response.id());

        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a student",
            description = "Updates the student identified by the specified ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Student updated successfully"
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
                    description = "Student not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Email address is already in use",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    public ResponseEntity<StudentUpdateResponse> updateStudent(
            @PathVariable UUID id,
            @Valid @RequestBody StudentUpdateRequest studentUpdateRequest
    ){
        return ResponseEntity.ok(
                studentCommandService.updateStudent(
                        id,
                        studentUpdateRequest)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a student",
            description = "Deletes the student identified by the specified ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Student deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Student not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    public ResponseEntity<StudentDeleteResponse> deleteStudent(@PathVariable UUID id){
        return ResponseEntity.ok(studentCommandService.deleteStudent(id));
    }

}
