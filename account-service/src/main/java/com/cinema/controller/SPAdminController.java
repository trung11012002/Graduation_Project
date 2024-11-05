package com.cinema.controller;

import com.cinema.dto.request.RegisterRequest;
import com.cinema.dto.response.ApiResponse;
import com.cinema.dto.response.UserResponse;
import com.cinema.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sp-admin")
public class SPAdminController {

    @Autowired
    private UserService userService;

    @PostMapping("/change-status")
    public ApiResponse<UserResponse> changeUserStatus(@RequestParam(name = "id") Integer id) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.changeUserStatus(id))
                .code(1000)
                .build();
    }

//    @PostMapping("/un-block")
//    public Result unBlockUser(@RequestParam(name = "id") Integer id) {
//        return userService.unBlockUser(id);
//    }
//
    @PostMapping("/create-admin-account")
    public ApiResponse<UserResponse> createAccount(@RequestBody RegisterRequest request) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.register(request, true))
                .code(1000)
                .build();
    }
}
