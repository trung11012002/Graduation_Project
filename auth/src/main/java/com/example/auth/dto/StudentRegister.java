package com.example.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRegister {
    private String name;
    private String msv;
    private String email;
    private String pass;
    private MultipartFile file;
    private String imgPath;
}
