package com.cinema.service;

import com.cinema.dto.response.UserResponse;

import java.util.List;

public interface IUserService {

//    User findByUsername(String username);
//
//    User findByEmail(String email);
//
//    Result editProfile(ProfileDto dto);
//
//    LoginResponse convertToLoginResp(User user, String token);
//
//    Result loginByGoogle(RegisterDto dto);
//
//    Result register(RegisterDto dto, boolean isAdmin);
//
//    Result verifyToken(String token);
//
//    Result changePassword(Integer userId, String oldPassword, String newPassword);
//
//    Result changeUserStatus(Integer id);
//
//    Result unBlockUser(Integer id);
//
//    Result findAllCustomerAccount();

//    Result findAllAdminAccount();

    List<UserResponse> findAllAdminAccountWithoutCinema();

//    Result forgotPassword(String email);
}
