package com.example.student.controller;

import com.example.student.entity.Student;
import com.example.student.service.Impl.FilesStorageService;
import com.example.student.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {
    @Autowired
    private StudentService service;

    @Autowired
    private FilesStorageService filesStorageService;

    @GetMapping("home")
    public String getHome(){
        return "hello";
    }

    @PostMapping()
    public ResponseEntity<Student> doCreate(@RequestBody Student dto){
        return new ResponseEntity<>(service.create(dto), HttpStatus.OK);
    }

    @GetMapping("/loadFile/{fileName}")
    public ResponseEntity<Resource> loadImg(@PathVariable String fileName, HttpServletRequest request){
        Resource resource = filesStorageService.load(fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping()
    public ResponseEntity<List<Student>> doRetrieveAll(){
        List<Student> list = service.getAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("{email}")
    public ResponseEntity<Student> findByEmail(@PathVariable String email){
        Student student = service.findByEmail(email);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

}
