package com.belajar_restful_api.services;
import com.belajar_restful_api.models.Student;
import com.belajar_restful_api.repository.StudentRepository;
import com.belajar_restful_api.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Objects;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Response addStudent(Student student) {
        Optional<Student> optionalStudent = studentRepository.findStudentByEmail(student.getEmail());
        if (optionalStudent.isPresent()) {
            throw new IllegalStateException("already exist");
        }
        studentRepository.save(student);
        Response response = new Response();
        response.setData(student);
        response.setStatus(HttpStatus.CREATED.value());
        response.setMessage("Student created successfully");

        return  response;
    }

    public Response getStudent() {
        List<Student> listStudent = this.studentRepository.findAll();

        Response response = new Response();
        response.setData(listStudent);
        response.setStatus(HttpStatus.CREATED.value());
        response.setMessage("Get All Students successfully");

        return response;
    }

    public Response deleteStudent(Long id) {
        boolean find = studentRepository.existsById(id);
        if (!find) {
            throw new IllegalStateException("student not find");
        }
        studentRepository.deleteById(id);

        Response response = new Response();
        response.setStatus(HttpStatus.CREATED.value());
        response.setMessage("Student id " + id + " deleted successfully");

        return response;
    }

    public Response updateStudent(Long id, String name, String email) {
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("student not found")
        );

        if (name != null && name.length() > 0 && !student.getName().equals(name)) {
            student.setName(name);
        }

        if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {
            Optional<Student> student1 = studentRepository.findStudentByEmail(email);
            if (student1.isPresent()) {
                throw new IllegalStateException("email taken");
            }
            student.setEmail(email);
        }

        Student studentUpdated = studentRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("student not found")
        );

        Response response = new Response();
        response.setData(studentUpdated);
        response.setStatus(HttpStatus.CREATED.value());
        response.setMessage("Student id " + id + " updated successfully");

        return response;

    }
}
