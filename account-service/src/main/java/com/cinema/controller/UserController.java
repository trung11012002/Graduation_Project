package com.cinema.controller;

import com.cinema.dto.response.ApiResponse;
import com.cinema.dto.response.UserResponse;
import com.cinema.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

//    @Autowired
//    private JwtUtils jwtUtils;
//
    @GetMapping("/customers")
    public ApiResponse<List<UserResponse>> findAllCustomerAccount() {
        return ApiResponse.<List<UserResponse>>builder()
                .data(userService.findAllCustomerAccount())
                .code(1000)
                .build();
    }

    @GetMapping("/admins")
    public ApiResponse<List<UserResponse>> findAllAdminAccount() {
        return ApiResponse.<List<UserResponse>>builder()
                .data(userService.findAllAdminAccount())
                .code(1000)
                .build();
    }

    @GetMapping("/available-admins")
    public ApiResponse<List<UserResponse>> findAllAdminAccountWithoutCinema() {
        return ApiResponse.<List<UserResponse>>builder()
                .code(1000)
                .data(userService.findAllAdminAccountWithoutCinema())
                .build();
    }

//    @PostMapping("/edit-profile")
//    public Result editProfile(@RequestBody ProfileDto dto) {
//        return userService.editProfile(dto);
//    }
}
