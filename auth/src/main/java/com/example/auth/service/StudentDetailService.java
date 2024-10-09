package com.example.auth.service;

import com.example.auth.client.StudentServiceClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StudentDetailService implements UserDetailsService {

    private final StudentServiceClient client;

    public StudentDetailService(StudentServiceClient client) {
        this.client = client;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var student = client.findByEmail(email).getBody();
        assert student != null;
        return new CustomStudentDetails(student);
    }
}
