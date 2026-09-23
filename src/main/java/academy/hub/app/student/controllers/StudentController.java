package academy.hub.app.student.controllers;

import academy.hub.app.student.dtos.StudentResponse;
import academy.hub.app.student.services.interfaces.StudentQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/students")
public class StudentController {

    private final StudentQueryService studentQueryService;
    public StudentController(StudentQueryService studentQueryService) {
        this.studentQueryService = studentQueryService;
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> findAll(){
        return ResponseEntity.ok(studentQueryService.getAll());
    }

}
