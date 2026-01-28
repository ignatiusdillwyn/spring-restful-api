package com.belajar_restful_api.controllers;
import com.belajar_restful_api.models.Student;
import com.belajar_restful_api.dto.Response;
import com.belajar_restful_api.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(path = "allStudents")
    public ResponseEntity<?> getAllStudents() {
        try {
            Response response = this.studentService.getStudent();
            return ResponseEntity
                    .status(response.getStatus())  // HTTP 201 Created
                    .body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping(path = "addStudent")
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
        try {
            Response response = this.studentService.addStudent(student);

            return ResponseEntity
                    .status(response.getStatus())  // HTTP 201 Created
                    .body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping(path = "delete/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable("studentId") Long id) {
        try {
            Response response = studentService.deleteStudent(id);

            return ResponseEntity
                    .status(response.getStatus())  // HTTP 201 Created
                    .body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping(path = "updateStudent/{studentId}")
    public ResponseEntity<?> upDateStudent
            (@PathVariable("studentId") Long id,
             @RequestParam(required = false) String name,
             @RequestParam(required = false) String email) {
        try {
            Response response = studentService.updateStudent(id, name, email);

            return ResponseEntity
                    .status(response.getStatus())  // HTTP 201 Created
                    .body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}