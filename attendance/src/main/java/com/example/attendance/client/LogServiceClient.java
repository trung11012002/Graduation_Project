package com.example.attendance.client;

import com.example.attendance.dto.AttendanceLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;


@FeignClient(name = "attendance-history", url = "http://localhost:8070/log")
@Component
public interface LogServiceClient {
    @GetMapping("/all/{msv}")
    ResponseEntity<List<AttendanceLog>> getAllByStudent(@PathVariable String msv);

    @GetMapping("{id}")
    ResponseEntity<AttendanceLog> retrieve(@PathVariable Integer id);

    @PostMapping("create")
    ResponseEntity<AttendanceLog> create(AttendanceLog log);

//    @PutMapping("update")
//    ResponseEntity<String> update(Integer id, AttendanceLog log);

    @GetMapping("check-exist")
    ResponseEntity<List<AttendanceLog>> getLogExist();

}
