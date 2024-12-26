package com.example.paymentservice.controller;

import java.text.ParseException;
import java.util.*;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.paymentservice.configuration.RabbitMQConfig;
import com.example.paymentservice.constants.VNPayHelper;
import com.example.paymentservice.dto.ApiResponse;
import com.example.paymentservice.dto.OrderRequest;
import com.example.paymentservice.dto.TransactionDTO;
import com.example.paymentservice.exception.AppException;
import com.example.paymentservice.exception.ErrorCode;
import com.example.paymentservice.repository.UserRepository;
import com.example.paymentservice.service.BookingService;
import com.example.paymentservice.service.VNPayService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.SignedJWT;

@RestController
public class PaymentController {

    @Autowired
    private VNPayService service;

    @Autowired
    private UserRepository repository;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/create-payment")
    public ApiResponse createPayment(@RequestBody OrderRequest orderRequest, HttpServletRequest request)
            throws ParseException, JsonProcessingException {
        String token = request.getHeader("Authorization").substring(7);
        SignedJWT signedJWT = SignedJWT.parse(token);
        String userName = signedJWT.getJWTClaimsSet().getSubject();
        String fullName = repository
                .findByUsername(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND))
                .getFullname();

        orderRequest.setOrderInfo("Thanh toán vé xem phim của:" + fullName);
        orderRequest.setIpAddress(VNPayHelper.getIpAddress(request));

        ObjectMapper objectMapper = new ObjectMapper();
        String requestVnPay = objectMapper.writeValueAsString(orderRequest);

        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE, requestVnPay);
        //        String response = service.createOrder(request, orderRequest);
        return ApiResponse.builder().code(1000).data("Success").msg("Success").build();
    }

    @PostMapping("/rabbit-test")
    public ApiResponse<String> createPayment(@RequestParam(name = "message") String message)
            throws JsonProcessingException {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setAmount(100000L);
        orderRequest.setOrderInfo("Thanh toán vé xem phim của:");
        ObjectMapper objectMapper = new ObjectMapper();
        String test = objectMapper.writeValueAsString(orderRequest);
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE, test);

        return ApiResponse.<String>builder().data("Success").build();
    }

    @PostMapping("/result-info")
    public ApiResponse transactionInfo(@RequestBody TransactionDTO dto) {
        return bookingService.createBooking(dto);
    }
}
