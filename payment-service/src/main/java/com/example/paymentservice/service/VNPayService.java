package com.example.paymentservice.service;

import com.example.paymentservice.constants.VNPayConstants;
import com.example.paymentservice.constants.VNPayHelper;
import com.example.paymentservice.dto.OrderRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class VNPayService {
    public String createOrder(HttpServletRequest request, OrderRequest orderRequest) throws UnsupportedEncodingException {

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
            put("vnp_IpAddr", VNPayHelper.getIpAddress(request));
            put("vnp_CreateDate", VNPayHelper.generateDate(false));
            put("vnp_ExpireDate", VNPayHelper.generateDate(true));
        }};

        String queryUrl = getQueryUrl(payload).get("queryUrl")
                + "&vnp_SecureHash="
                + VNPayHelper.hmacSHA512(VNPayConstants.SECRET_KEY, getQueryUrl(payload).get("hashData"));

        String paymentUrl = VNPayConstants.VNP_PAY_URL + "?" + queryUrl;
        payload.put("redirect_url", paymentUrl);

        return paymentUrl;
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
