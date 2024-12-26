package com.example.paymentservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.example.paymentservice.dto.BookingResponse;
import com.example.paymentservice.entity.Booking;
import com.example.paymentservice.entity.User;

@Configuration
@Component
@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(target = "userId", source = "user")
    BookingResponse toBookingResponse(Booking booking);

    default Integer mapUserToUserId(User user) {
        return user.getId();
    }
}
