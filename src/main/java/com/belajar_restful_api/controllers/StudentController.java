package com.belajar_restful_api.controllers;
import com.belajar_restful_api.models.Student;
import com.belajar_restful_api.response.Response;
import com.belajar_restful_api.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(path = "allStudents")
    public List<Student> getAllStudents() {
        return this.studentService.getStudent();
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
    public void deleteStudent(@PathVariable("studentId") Long id) {
        studentService.deleteStudent(id);
    }

    @PutMapping(path = "updateStudent/{studentId}")
    public void upDateStudent
            (@PathVariable("studentId") Long id,
             @RequestParam(required = false) String name,
             @RequestParam(required = false) String email) {
        studentService.updateStudent(id, name, email);
    }
}