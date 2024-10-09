package com.example.attendance.client;

import com.example.attendance.dto.AttendanceScore;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(name = "score", url = "http://localhost:8090/score")

public interface ScoreServiceClient {
    @GetMapping("all")
    ResponseEntity<List<AttendanceScore>> getAll();

    @GetMapping("{msv}")
    ResponseEntity<AttendanceScore> retrieve(@PathVariable String msv);

    @PutMapping("update")
    ResponseEntity<String> update(@RequestBody AttendanceScore score);

    @PostMapping("create")
    ResponseEntity<AttendanceScore> create(@RequestBody AttendanceScore entity);
}
