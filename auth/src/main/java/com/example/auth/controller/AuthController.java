package com.example.auth.controller;

import com.example.auth.client.StudentServiceClient;
import com.example.auth.config.JwtConfig;
import com.example.auth.dto.StudentLogin;
import com.example.auth.dto.Student;
import com.example.auth.dto.StudentRegister;
import com.example.auth.service.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private StudentServiceClient client;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private FilesStorageService filesStorageService;

    @PostMapping("/log-in")
    public ResponseEntity<?> authenticateUser(@RequestBody StudentLogin loginDTO){
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDTO.getEmail(), loginDTO.getPass()
            ));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            if(authentication.isAuthenticated()){
                Map<String, String> json = new HashMap<>();
                String bearerToken = jwtConfig.generateToken(loginDTO.getEmail());
                json.put("Login", "Success");
                json.put("Token", bearerToken);
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add("Authorization", bearerToken);
                return new ResponseEntity<>(json, HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Wrong pass or email", HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<>("Login Exception", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@ModelAttribute StudentRegister dto){
        Student student = client.findByEmail(dto.getEmail()).getBody();
        if(student != null){
            return new ResponseEntity<>("Tài khoản đã tồn tại", HttpStatus.OK);
        }else{
            MultipartFile studentImg = dto.getFile();
            String fileName = filesStorageService.save(studentImg);
            String loadUri = ServletUriComponentsBuilder.fromHttpUrl("http://localhost:8080")
                    .path("/student")
                    .path("/loadFile/")
                    .path(fileName)
                    .toUriString();
            dto.setImgPath(loadUri);
            dto.setPass(encoder.encode(dto.getPass()));
            Student entity = new Student();
            entity.setName(dto.getName());
            entity.setMsv(dto.getMsv());
            entity.setEmail(dto.getEmail());
            entity.setPass(dto.getPass());
            entity.setImgPath(dto.getImgPath());
            client.create(entity);
            return new ResponseEntity<>("Đăng ký thành công", HttpStatus.OK);
        }
    }
    
}
