package com.example.paymentservice.service;

import com.example.paymentservice.configuration.RabbitMQConfig;
import com.example.paymentservice.constants.VNPayConstants;
import com.example.paymentservice.constants.VNPayHelper;
import com.example.paymentservice.dto.OrderRequest;
import com.example.paymentservice.dto.TransactionDTO;
import com.example.paymentservice.entity.User;
import com.example.paymentservice.exception.AppException;
import com.example.paymentservice.exception.ErrorCode;
import com.example.paymentservice.repository.UserRepository;
import com.example.paymentservice.repository.httpclient.NotificationClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@Slf4j
public class VNPayService {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private NotificationClient notificationClient;

    @Autowired
    private UserRepository userRepository;
    @RabbitHandler
    @RabbitListener(queues = RabbitMQConfig.QUEUE, ackMode = "AUTO")
    public void createOrder(String requestVnpay) throws UnsupportedEncodingException, JsonProcessingException {
//        bookingService.createBooking(requestVnpay);

        log.info("Order received: {}", requestVnpay);
        ObjectMapper objectMapper = new ObjectMapper();
        OrderRequest orderRequest = objectMapper.readValue(requestVnpay, OrderRequest.class);
        Map<String, Object> payload = new HashMap(){{
            put("vnp_Version", VNPayConstants.VNP_VERSION);
            put("vnp_Command", VNPayConstants.VNP_COMMAND_ORDER);
            put("vnp_TmnCode", VNPayConstants.VNP_TMN_CODE);
            put("vnp_Amount", String.valueOf(orderRequest.getAmount() * 100));
            put("vnp_CurrCode", VNPayConstants.VNP_CURRENCY_CODE);
            put("vnp_TxnRef",  VNPayHelper.getRandomNumber(8));
            put("vnp_OrderInfo", orderRequest.getOrderInfo());
            put("vnp_OrderType", VNPayConstants.ORDER_TYPE);
            put("vnp_Locale", VNPayConstants.VNP_LOCALE);
            put("vnp_ReturnUrl", VNPayConstants.VNP_RETURN_URL);
            put("vnp_IpAddr", orderRequest.getIpAddress());
            put("vnp_CreateDate", VNPayHelper.generateDate(false));
            put("vnp_ExpireDate", VNPayHelper.generateDate(true));
        }};

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setScheduleId(orderRequest.getScheduleId());
        transactionDTO.setUserId(orderRequest.getUserId());
        transactionDTO.setSeats(orderRequest.getSeats());

        bookingService.createBookingWithStatusPendingPayment(transactionDTO);

        String queryUrl = getQueryUrl(payload).get("queryUrl")
                + "&vnp_SecureHash="
                + VNPayHelper.hmacSHA512(VNPayConstants.SECRET_KEY, getQueryUrl(payload).get("hashData"));

        log.info("Query URL: {}", queryUrl);
        String paymentUrl = VNPayConstants.VNP_PAY_URL + "?" + queryUrl;
        payload.put("redirect_url", paymentUrl);

        log.info("Payment URL: {}", paymentUrl);

        // send payment url to client as websocket
        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        notificationClient.sendUrlPayment(paymentUrl, user.getUsername());
    }

    private Map<String, String> getQueryUrl(Map<String, Object> payload) throws UnsupportedEncodingException {

        List<String> fieldNames = new ArrayList<>(payload.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {

            String fieldName = (String) itr.next();
            String fieldValue = (String) payload.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {

                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {

                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        Map<String, String> result = new HashMap<>();
        result. put("queryUrl", query.toString());
        result. put("hashData", hashData.toString());
        return result;
    }
}
