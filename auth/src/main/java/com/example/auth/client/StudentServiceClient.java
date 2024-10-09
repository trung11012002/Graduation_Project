package com.example.auth.client;

import com.example.auth.dto.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(name = "student", url = "http://localhost:8080/student")
public interface StudentServiceClient {
    @PostMapping()
    ResponseEntity<Student> create(@RequestBody Student register);

    @GetMapping("/{email}")
    ResponseEntity<Student> findByEmail(@PathVariable String email);
}
