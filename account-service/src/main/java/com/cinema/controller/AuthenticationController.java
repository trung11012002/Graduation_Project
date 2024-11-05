package com.cinema.controller;

import com.cinema.dto.request.AuthenticationResquest;
import com.cinema.dto.request.IntrospectRequest;
import com.cinema.dto.request.LogoutRequest;
import com.cinema.dto.request.RegisterRequest;
import com.cinema.dto.response.ApiResponse;
import com.cinema.dto.response.AuthenticationResponse;
import com.cinema.dto.response.IntrospectResponse;
import com.cinema.dto.response.LoginResponse;
import com.cinema.dto.response.UserResponse;
import com.cinema.service.IAuthenticationService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    IAuthenticationService authenticationService;

    @PostMapping("/signin")
    ApiResponse<LoginResponse> authenticate(@RequestBody AuthenticationResquest resquest) {
        var result = authenticationService.authencticate(resquest);
        return ApiResponse.<LoginResponse>builder()
                .data(result)
                .code(1000)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody @Valid IntrospectRequest resquest) {
        var result = authenticationService.introspect(resquest);
        return ApiResponse.<IntrospectResponse>builder()
                .data(result)
                .code(1000)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest resquest) {
        authenticationService.logout(resquest);
        return ApiResponse.<Void>builder().code(1000).build();
    }

    @PostMapping("/signup")
    public ApiResponse<UserResponse> signup(@RequestBody RegisterRequest request) {
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .data(authenticationService.register(request, false))
                .build();
    }

    @GetMapping("/verify-token")
    public ApiResponse<LoginResponse> refreshRequest(@RequestHeader(value = "Auth") String token) {
        return ApiResponse.<LoginResponse>builder()
                .data(authenticationService.verifyToken(token))
                .code(1000)
                .build();
    }


//    @PostMapping("/refresh")
//    ApiResponse<AuthenticationResponse> refresh(@RequestBody RefreshRequest resquest)
//            throws ParseException, JOSEException {
//        return ApiResponse.<AuthenticationResponse>builder()
//                .result(authenticationService.refreshToken(resquest))
//                .code(1000)
//                .build();
//    }


//    @GetMapping("/change-password")
//    public Result signup(@RequestParam Integer userId,
//                         @RequestParam String oldPassword,
//                         @RequestParam String newPassword) {
//        Result result = userService.changePassword(userId, oldPassword, newPassword);
//        return result ;
//    }
//
//    @PostMapping("/forgot-password")
//    public Result forgotPassword(@RequestBody String email) {
//        return userService.forgotPassword(email);
//    }
}
