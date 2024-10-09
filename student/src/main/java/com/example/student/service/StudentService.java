package com.example.student.service;

import com.example.student.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> getAll();
    Student findByEmail(String email);
    Student create(Student entity);
    void update(Student entity, Integer id);
}
