package com.example.paymentservice.controller;

import com.example.paymentservice.constants.VNPayConstants;
import com.example.paymentservice.dto.ApiResponse;
import com.example.paymentservice.dto.OrderRequest;
import com.example.paymentservice.dto.TransactionDTO;
import com.example.paymentservice.exception.AppException;
import com.example.paymentservice.exception.ErrorCode;
import com.example.paymentservice.repository.UserRepository;
import com.example.paymentservice.service.BookingService;
import com.example.paymentservice.service.VNPayService;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.ParseException;
import java.util.*;

@RestController
public class PaymentController {

    @Autowired
    private VNPayService service;

    @Autowired
    private UserRepository repository;

    @Autowired
    private BookingService bookingService;

    @PostMapping("/create-payment")
    public ApiResponse createPayment(@RequestParam(name = "amount") Long amount,
                                     HttpServletRequest request) throws UnsupportedEncodingException, ParseException {
        String token = request.getHeader("Authorization").substring(7);
        SignedJWT signedJWT = SignedJWT.parse(token);
        String userName = signedJWT.getJWTClaimsSet().getSubject();
        String fullName = repository.findByUsername(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)).getFullname();
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setAmount(amount);
        orderRequest.setOrderInfo("Thanh toán vé xem phim của:" + fullName);
        String response = service.createOrder(request, orderRequest);
        return ApiResponse.builder().code(1000).data(response).msg("Success").build();
    }

    @GetMapping("/result-info")
    public ApiResponse transactionInfo(TransactionDTO dto){
        return bookingService.createBooking(dto);
    }




}
