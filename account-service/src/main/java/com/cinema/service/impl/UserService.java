package com.cinema.service.impl;

import com.cinema.dto.request.ProfileEditRequest;
import com.cinema.dto.request.RegisterRequest;
import com.cinema.dto.response.ApiResponse;
import com.cinema.dto.response.LoginResponse;
import com.cinema.dto.response.RoleResponse;
import com.cinema.dto.response.UserResponse;
import com.cinema.entity.Role;
import com.cinema.entity.User;
import com.cinema.enums.RoleEnums;
import com.cinema.exception.AppException;
import com.cinema.exception.ErrorCode;
import com.cinema.mapper.UserMapper;
import com.cinema.repository.CinemaRepository;
import com.cinema.repository.RoleRepository;
import com.cinema.repository.UserRepository;
import com.cinema.service.IUserService;
import com.event.dto.NotificationEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService implements IUserService {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

//    @Autowired
//    private JwtUtils jwtUtils;

//    @Autowired
//    private EmailSenderService emailSenderService;

//    @Override
//    public User findByUsername(String username) {
//        Optional<User> op = userRepository.findByUsername(username);
//        if (op.isPresent()) {
//            return op.get();
//        }
//        return null;
//    }
//
//    @Override
//    public User findByEmail(String email) {
//        Optional<User> op = userRepository.findByUsername(email);
//        if (op.isPresent()) {
//            return op.get();
//        }
//        return null;
//    }

    @Override
    public LoginResponse editProfile(ProfileEditRequest dto) {
        User user = userRepository.findById(dto.getId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        String role = user.getRole().getName().toUpperCase();
        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setName(role);

        LoginResponse response = new LoginResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setPassword(user.getPassword() != null? user.getPassword() : null);
        response.setFullname(user.getFullname() != null? user.getFullname() : null);
        response.setDateOfBirth(user.getDateOfBirth() != null? user.getDateOfBirth() : null);
        response.setAddress(user.getAddress() != null? user.getAddress() : null);
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone() != null? user.getPhone() : null);
        response.setBlocked(user.isBlocked());
        response.setRole(roleResponse);
        response.setToken(null);
        response.setRefreshToken(null);
//        String error = errorUsername(dto.getUsername());
//        if (error != null)
//            return Result.fail(error);



        return response;
    }

//    @Override
//    public LoginResponse convertToLoginResp(User user, String token) {
//        LoginResponse response = new LoginResponse();
//        response.setId(user.getId());
//        response.setUsername(user.getUsername());
//        response.setPassword(user.getPassword() != null? user.getPassword() : null);
//        response.setFullname(user.getFullname() != null? user.getFullname() : null);
//        response.setDateOfBirth(user.getDateOfBirth() != null? user.getDateOfBirth() : null);
//        response.setAddress(user.getAddress() != null? user.getAddress() : null);
//        response.setEmail(user.getEmail());
//        response.setPhone(user.getPhone() != null? user.getPhone() : null);
//        response.setBlocked(user.isBlocked());
//        response.setRole(user.getRole());
//        response.setToken(token);
//        response.setRefreshToken(null);
//        return response;
//    }
//
    @Override
    public UserResponse register(RegisterRequest request, boolean isAdmin) {
//        String error = errorUsername(dto.getUsername());
//        if (error != null)
//            return Result.fail(error);

//        boolean isValid = validateEmail(dto.getEmail());
//        if (!isValid)
//            return Result.fail("Email đã được sử dụng");
        //check user exist
        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);
        User user = userMapper.toUser(request);

        Role role = isAdmin? roleRepository.findById(RoleEnums.ADMIN.getCode()).get() : roleRepository.findById(RoleEnums.CUSTOMER.getCode()).get();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        userRepository.save(user);
//        emailSenderService.sendMailRegister(dto.getEmail(), dto.getUsername(), dto.getPassword());
        return userMapper.toUserResponse(user);
    }
//
//    @Override
//    public Result loginByGoogle(RegisterDto dto) {
//        String username = UsernameGenerator.generateUsernameFromEmail(dto.getEmail());
//        while (userRepository.existsByUsername(username)) {
//            username = UsernameGenerator.generateUsernameFromEmail(dto.getEmail());
//        }
//        User user = mapper.convertValue(dto, User.class);
//        user.setUsername(username);
//        Role role = roleRepository.findById(RoleEnums.CUSTOMER.getCode()).get();
//        user.setRole(role);
//        user = userRepository.save(user);
//        String token = jwtUtils.generateTokenFromUsername(username);
//        LoginResponse response = convertToLoginResp(user, token);
//        //emailSenderService.sendMailRegister(dto.getEmail(), dto.getUsername(), dto.getPassword());
//        return new Result(200, "Success", response);
//    }
//
//    @Override
//    public Result verifyToken(String token) {
//
//        String username = jwtUtils.getUsernameFromJwtToken(token);
//        if (username == null)
//            return Result.fail("Invalid token");
//
//        Optional<User> op = userRepository.findByUsername(username);
//        if (!op.isPresent())
//            return Result.fail("Không tồn tại người dùng với username " + username);
//
//        User user = op.get();
//        LoginResponse response = new LoginResponse();
//        response.setId(user.getId());
//        response.setUsername(user.getUsername());
//        response.setPassword(user.getPassword() != null? user.getPassword() : null);
//        response.setFullname(user.getFullname() != null? user.getFullname() : null);
//        response.setDateOfBirth(user.getDateOfBirth() != null? user.getDateOfBirth() : null);
//        response.setAddress(user.getAddress() != null? user.getAddress() : null);
//        response.setEmail(user.getEmail());
//        response.setPhone(user.getPhone() != null? user.getPhone() : null);
//        response.setBlocked(user.isBlocked());
//        response.setRole(user.getRole());
//        response.setToken(token);
//        response.setRefreshToken(null);
//        return Result.success("Success", response);
//    }
//
    @Override
    public UserResponse changeUserStatus(Integer id) {
        Optional<User> op = userRepository.findById(id);
        if (!op.isPresent())
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        else {
            User user = op.get();
            boolean status = user.isBlocked();
            user.setBlocked(!status);
            userRepository.save(user);
            return userMapper.toUserResponse(user);
        }
    }

//    @Override
//    public Result unBlockUser(Integer id) {
//        Optional<User> op = userRepository.findById(id);
//        if (!op.isPresent())
//            return Result.fail("Người dùng không tồn tại!");
//        else {
//            User user = op.get();
//            user.setBlocked(false);
//            userRepository.save(user);
//            return new Result(200, "Success", user);
//        }
//    }
//
    @Override
    public List<UserResponse> findAllCustomerAccount() {
        List<User> users = userRepository.findAllByRoleId(RoleEnums.CUSTOMER.getCode());
        return userMapper.toUserResponses(users);
    }

    @Override
    public List<UserResponse> findAllAdminAccount() {
        List<User> users = userRepository.findAllByRoleId(RoleEnums.ADMIN.getCode());

        return userMapper.toUserResponses(users);
    }

    @Override
    public List<UserResponse> findAllAdminAccountWithoutCinema() {
        List<User> users = userRepository.findAllByRoleId(RoleEnums.ADMIN.getCode());
        List<User> response = new ArrayList<>();
        for (User user: users) {
            if (user.getManagedCinema() == null)
                response.add(user);
        }
        return userMapper.toUserResponses(response);
    }
//
    @Override
    public String forgotPassword(String email) {
        String jsonString = email;

        JSONObject jsonObject = new JSONObject(jsonString);
        email = jsonObject.getString("email");
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Random random = new Random();
        int randomNumber = 10000000 + random.nextInt(90000000);
        String newPassword = String.valueOf(randomNumber);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        //send new password to email
        NotificationEvent notificationEvent = NotificationEvent.builder()
                .channel("EMAIL")
                .recipient(user.getEmail())
                .subject("Cinema new password")
                .body(newPassword)
                .templateCode("forgot-password")
                .build();

        //Publish message to kafka
        kafkaTemplate.send("notification-delivery", notificationEvent);

        return "Success";
    }
//
//    public String errorUsername(String username) {
//        if (isContainsSpecialCharacter(username)) {
//            return "Tên đăng nhập không được chứa ký tự đặc biệt";
//        }
//
//        if (!Character.isLetter(username.charAt(0))) {
//            return "Tên đăng nhập không được bắt đầu bằng chữ số";
//        }
//
//        Optional<User> op = userRepository.findByUsername(username);
//        if (op.isPresent())
//            return "Tên đăng nhập đã được sử dụng";
//
//        return null;
//    }
//
//    private boolean isContainsSpecialCharacter(String data) {
//        String pattern = ".*[^a-zA-Z0-9].*";
//        return data.matches(pattern);
//    }
//
//    public boolean validateEmail(String email) {
//        Optional<User> op = userRepository.findByEmail(email);
//        if (op.isPresent())
//            return false;
//        return true;
//    }

    @Override
    public ApiResponse changePassword(Integer userId, String oldPassword, String newPassword) {
        Optional<User> op = userRepository.findById(userId);
        if (op.isPresent()) {
            User user = op.get();
            if (!passwordEncoder.matches(oldPassword, user.getPassword()))
                throw new AppException(ErrorCode.WRONG_PASSWORD);
            else {
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                return ApiResponse.<LoginResponse>builder()
                        .msg("Đổi mật khẩu thành công")
                        .code(1000)
                        .build();
            }
        }
        return ApiResponse.<LoginResponse>builder()
                .msg("Người dùng không tồn tại")
                .code(1000)
                .build();
    }

}
