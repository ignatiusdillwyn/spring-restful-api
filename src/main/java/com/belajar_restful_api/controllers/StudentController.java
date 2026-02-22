package com.belajar_restful_api.controllers;
import com.belajar_restful_api.config.JwtService;
import com.belajar_restful_api.models.Student;
import com.belajar_restful_api.dto.Response;
import com.belajar_restful_api.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.Claims;

@RestController
@RequestMapping(path = "api/students")
public class StudentController {
    private final StudentService studentService;
    private final JwtService jwtService;

    @Autowired
    public StudentController(StudentService studentService, JwtService jwtService) {
        this.studentService = studentService;
        this.jwtService = jwtService;
    }

    @GetMapping(path = "allStudents")
    public ResponseEntity<?> getAllStudents(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            // Extract token dari header
            String token = null;
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7); // Hapus "Bearer " dari awal
            }

            // Log atau validasi token
            System.out.println("Token received: " + token);

            // Anda bisa validasi token disini
            if (token == null) {
                return ResponseEntity.status(401).body("Invalid token");
            }

            //Extra data from token
            Claims claims = jwtService.extractAllClaims(token);
            System.out.println("All claims: " + claims);
            System.out.println("FirstName: " + claims.get("firstName"));

            Response response = this.studentService.getStudent();
            return ResponseEntity
                    .status(response.getStatus())
                    .body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping(path = "addStudent")
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
        try {
            Response response = this.studentService.addStudent(student);

//            HttpStatus
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