package com.cinema.controller;

import com.cinema.dto.response.ApiResponse;
import com.cinema.dto.response.UserResponse;
import com.cinema.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

//    @Autowired
//    private JwtUtils jwtUtils;
//
//    @GetMapping("/customers")
//    public Result findAllCustomerAccount() {
//        return userService.findAllCustomerAccount();
//    }
//
//    @GetMapping("/admins")
//    public Result findAllAdminAccount() {
//        return userService.findAllAdminAccount();
//    }

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
