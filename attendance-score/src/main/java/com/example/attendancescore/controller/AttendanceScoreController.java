package com.example.attendancescore.controller;

import com.example.attendancescore.entity.AttendanceScore;
import com.example.attendancescore.service.Impl.AttendanceScoreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("score")
public class AttendanceScoreController {

    @Autowired
    private AttendanceScoreServiceImpl service;

    @GetMapping("all")
    public ResponseEntity<List<AttendanceScore>> getAll(){
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("{msv}")
    public ResponseEntity<AttendanceScore> retrieve(@PathVariable String msv){
        return new ResponseEntity<>(service.retrieve(msv), HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity<String> update(@RequestBody AttendanceScore score){
        service.update(score);
        return new ResponseEntity<>("Saved", HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<AttendanceScore> create(@RequestBody AttendanceScore entity){
        return new ResponseEntity<>(service.create(entity), HttpStatus.OK);
    }

}
