package com.cinema.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cinema.dto.request.ProfileEditRequest;
import com.cinema.dto.response.ApiResponse;
import com.cinema.dto.response.LoginResponse;
import com.cinema.dto.response.UserResponse;
import com.cinema.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

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

    @PostMapping("/edit-profile")
    public ApiResponse<LoginResponse> editProfile(@RequestBody ProfileEditRequest dto) {
        return ApiResponse.<LoginResponse>builder()
                .data(userService.editProfile(dto))
                .code(1000)
                .build();
    }

    @GetMapping("/change-password")
    public ApiResponse change_password(
            @RequestParam Integer userId, @RequestParam String oldPassword, @RequestParam String newPassword) {

        return userService.changePassword(userId, oldPassword, newPassword);
    }
}
