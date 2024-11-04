package com.example.paymentservice.service.impl;

import com.example.paymentservice.dto.ApiResponse;
import com.example.paymentservice.dto.BookingResponse;
import com.example.paymentservice.dto.SeatDTO;
import com.example.paymentservice.dto.TransactionDTO;
import com.example.paymentservice.entity.Booking;
import com.example.paymentservice.entity.Ticket;
import com.example.paymentservice.entity.User;
import com.example.paymentservice.exception.AppException;
import com.example.paymentservice.exception.ErrorCode;
import com.example.paymentservice.mapper.BookingMapper;
import com.example.paymentservice.repository.BookingRepository;
import com.example.paymentservice.repository.UserRepository;
import com.example.paymentservice.service.BookingService;
import com.example.paymentservice.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private BookingMapper mapper;
    @Autowired
    private BookingRepository bookingrepository;
    @Transactional(transactionManager = "transactionManager")
    @Override
    public ApiResponse createBooking(TransactionDTO dto) {
        String error = errorTransactionResponseCode(dto.getResponseCode());
        if(error != null){
            return ApiResponse.builder().code(403).msg(error).build();
        }
        Booking booking = new Booking();
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        booking.setUser(user);
        booking.setBookingTime(LocalDateTime.now());
        booking = bookingrepository.save(booking);
        List<SeatDTO> seatDTOS = new ArrayList<>();
        for(String s : dto.getSeats()){
            String[] number = s.split("-");
            SeatDTO seatDTO = new SeatDTO();
            seatDTO.setRow(Integer.parseInt(number[0]));
            seatDTO.setColumn(Integer.parseInt(number[1]));
            seatDTOS.add(seatDTO);
        }
        List<Ticket> tickets = ticketService.createTickets(booking, dto.getScheduleId(), seatDTOS);
        booking.setTickets(tickets);
        booking = bookingrepository.save(booking);
        BookingResponse response = mapper.toBookingResponse(booking);
        return ApiResponse.builder().code(1000).msg("Success").data(response).build();
    }

    private String errorTransactionResponseCode(String responseCode) {
        String error = null;
        switch (responseCode) {
            case "07":
                error = "Trừ tiền thành công. Giao dịch bị nghi ngờ (liên quan tới lừa đảo, giao dịch bất thường).";
                break;
            case "09":
                error = "Giao dịch không thành công do: Thẻ/Tài khoản của khách hàng chưa đăng ký dịch vụ InternetBanking tại ngân hàng.";
                break;
            case "10":
                error = "Giao dịch không thành công do: Khách hàng xác thực thông tin thẻ/tài khoản không đúng quá 3 lần";
                break;
            case "11":
                error = "Giao dịch không thành công do: Đã hết hạn chờ thanh toán. Xin quý khách vui lòng thực hiện lại giao dịch.";
                break;
            case "12":
                error = "Giao dịch không thành công do: Thẻ/Tài khoản của khách hàng bị khóa.";
                break;
            case "13":
                error = "Giao dịch không thành công do Quý khách nhập sai mật khẩu xác thực giao dịch (OTP). Xin quý khách vui lòng thực hiện lại giao dịch.";
                break;
            case "24":
                error = "Giao dịch không thành công do: Khách hàng hủy giao dịch";
                break;
            case "51":
                error = "Giao dịch không thành công do: Tài khoản của quý khách không đủ số dư để thực hiện giao dịch.";
                break;
            case "65":
                error = "Giao dịch không thành công do: Tài khoản của Quý khách đã vượt quá hạn mức giao dịch trong ngày.";
                break;
            case "75":
                error = "Ngân hàng thanh toán đang bảo trì.";
                break;
            case "79":
                error = "Giao dịch không thành công do: KH nhập sai mật khẩu thanh toán quá số lần quy định. Xin quý khách vui lòng thực hiện lại giao dịch";
                break;
        }
        return error;
    }
}
