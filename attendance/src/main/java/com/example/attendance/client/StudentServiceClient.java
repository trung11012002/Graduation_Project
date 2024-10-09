package com.example.attendance.client;

import com.example.attendance.dto.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
@FeignClient(name = "student", url = "http://localhost:8080/student")

public interface StudentServiceClient {
    @PostMapping()
    ResponseEntity<Student> create(@RequestBody Student register);

    @GetMapping("/{email}")
    ResponseEntity<Student> findByEmail(@PathVariable String email);

    @GetMapping()
    ResponseEntity<List<Student>> doRetrieveAll();
}
