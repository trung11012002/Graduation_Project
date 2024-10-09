package com.example.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String name;
    private String msv;
    private String email;
    private String pass;
    private String imgPath;
}
