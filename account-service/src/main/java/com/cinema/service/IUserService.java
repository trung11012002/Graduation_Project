package com.cinema.service;

import com.cinema.dto.request.ProfileEditRequest;
import com.cinema.dto.request.RegisterRequest;
import com.cinema.dto.response.ApiResponse;
import com.cinema.dto.response.LoginResponse;
import com.cinema.dto.response.UserResponse;

import java.util.List;

public interface IUserService {

//    User findByUsername(String username);
//
//    User findByEmail(String email);
//
    LoginResponse editProfile(ProfileEditRequest dto);
//
//    LoginResponse convertToLoginResp(User user, String token);
//
//    Result loginByGoogle(RegisterDto dto);
//
    UserResponse register(RegisterRequest request, boolean isAdmin);
//
//    Result verifyToken(String token);
//
    ApiResponse<LoginResponse> changePassword(Integer userId, String oldPassword, String newPassword);
//
    UserResponse changeUserStatus(Integer id);
//
//    Result unBlockUser(Integer id);
//
    List<UserResponse> findAllCustomerAccount();

    List<UserResponse> findAllAdminAccount();

    List<UserResponse> findAllAdminAccountWithoutCinema();


    String forgotPassword(String email);
}
