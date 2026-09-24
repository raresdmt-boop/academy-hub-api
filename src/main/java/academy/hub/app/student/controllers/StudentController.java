package academy.hub.app.student.controllers;

import academy.hub.app.student.dtos.*;
import academy.hub.app.student.services.interfaces.StudentCommandService;
import academy.hub.app.student.services.interfaces.StudentQueryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Validated
@RequestMapping("/api/students")
public class StudentController {

    private final StudentQueryService studentQueryService;
    private final StudentCommandService studentCommandService;


    public StudentController(StudentQueryService studentQueryService, StudentCommandService studentCommandService) {
        this.studentQueryService = studentQueryService;
        this.studentCommandService = studentCommandService;
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> findAll(){
        return ResponseEntity.ok(studentQueryService.getAll());
    }

    @PostMapping
    public ResponseEntity<StudentCreateResponse> createStudent(@Valid @RequestBody StudentCreateRequest studentCreateRequest) {
        return ResponseEntity.ok(studentCommandService.addStudent(studentCreateRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentUpdateResponse> updateStudent(
            @PathVariable UUID id,
            @Valid @RequestBody StudentUpdateRequest studentUpdateRequest
    ){
        return ResponseEntity.ok(studentCommandService.updateStudent(id, studentUpdateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StudentDeleteResponse> deleteStudent(@PathVariable UUID id){
        return ResponseEntity.ok(studentCommandService.deleteStudent(id));
    }

}
