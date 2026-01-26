package com.belajar_restful_api.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table
public class Student {

    // generate the id
    // this is use in when adding a new Student
    @Id
    @SequenceGenerator(
            name = "student_index",
            sequenceName = "student_index",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_index"
    )
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "dob", nullable = false)
    private String dob;

    @Column(name = "email", nullable = false)
    private String email;

    public Student() {
    }

    public Student(Long id, String name, Integer age, String dob, String email) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.email = email;
    }

    public Student(String name, Integer age, String dob, String email) {
        this.name = name;
        this.dob = dob;
        this.email = email;
    }

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//
//    public String getDob() {
//        return dob;
//    }
//
//    public void setDob(String dob) {
//        this.dob = dob;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
}