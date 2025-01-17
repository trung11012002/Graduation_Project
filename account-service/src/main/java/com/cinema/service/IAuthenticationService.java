package com.cinema.service;

import com.cinema.dto.request.AuthenticationResquest;
import com.cinema.dto.request.IntrospectRequest;
import com.cinema.dto.request.LogoutRequest;
import com.cinema.dto.request.RegisterRequest;
import com.cinema.dto.response.AuthenticationResponse;
import com.cinema.dto.response.IntrospectResponse;
import com.cinema.dto.response.LoginResponse;
import com.cinema.dto.response.UserResponse;

public interface IAuthenticationService {
    LoginResponse authencticate(AuthenticationResquest resquest);

    IntrospectResponse introspect(IntrospectRequest request);
    void logout(LogoutRequest request);
    UserResponse register(RegisterRequest request, boolean isAdmin);

    LoginResponse verifyToken(String token);
    
//    AuthenticationResponse refreshToken(RefreshRequest refreshRequest) throws ParseException, JOSEException;
}
