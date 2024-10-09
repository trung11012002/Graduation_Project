package com.example.attendancehistory.controller;

import com.example.attendancehistory.entity.AttendanceLog;
import com.example.attendancehistory.service.AttendanceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("log")
public class AttendanceLogController {

    @Autowired
    private AttendanceLogService service;

    @GetMapping("/all/{msv}")
    public ResponseEntity<List<AttendanceLog>> getAllByStudent(@PathVariable String msv){
        return new ResponseEntity<>(service.findAllByMsv(msv), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<AttendanceLog> retrieve(@PathVariable Integer id){
        return new ResponseEntity<>(service.retrieve(id), HttpStatus.OK);
    }

    @GetMapping("check-exist")
    public ResponseEntity<List<AttendanceLog>> getLogExist(){
        return new ResponseEntity<>(service.getLog(), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<AttendanceLog> create(@RequestBody AttendanceLog log){
        return new ResponseEntity<>(service.create(log), HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity<String> update(Integer id, AttendanceLog log){
        service.update(id, log);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
