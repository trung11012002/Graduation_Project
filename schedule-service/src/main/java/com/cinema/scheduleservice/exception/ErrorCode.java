package com.cinema.scheduleservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Invalid message key", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least 3 character", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Username must be at least 8 character", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "User Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    USER_NOT_IN_PROJECT(1009, "This user is not in project", HttpStatus.BAD_REQUEST),
    TASK_NOT_IN_PROJECT(1010, "This task is not in project", HttpStatus.BAD_REQUEST),
    TIMESHEET_NOT_EXISTED(1011, "Timesheet not existed", HttpStatus.BAD_REQUEST),
    TIMESHEET_NOT_OF_USER(1012, "Timesheet not of you", HttpStatus.BAD_REQUEST),
    WORKING_HOUR_FOR_DAY_BIGGER_16(1013, "Total working time can't > 16 hours", HttpStatus.BAD_REQUEST),
    WORKING_HOUR_LESS_ZERO(1014, "You can't log this time sheet with working time < 0", HttpStatus.BAD_REQUEST),
    INVALID_TYPE_MYREQUEST_OFF(1015, "Invalid type my request off", HttpStatus.BAD_REQUEST),
    REQUEST_INVALID(1016, "Request invalid", HttpStatus.BAD_REQUEST),
    MY_REQUEST_NOT_EXISTED(1017, "My request not existed", HttpStatus.BAD_REQUEST),
    TICKET_NOT_FOUND(1018, "Ticket not found", HttpStatus.NOT_FOUND),
    SEAT_ALREADY_BOOKED(1019, "Seat already booked", HttpStatus.BAD_REQUEST),
    BOOKING_NOT_FOUND(1020, "Booking not found", HttpStatus.NOT_FOUND),
    INVALID_TIME(1021, "Invalid time", HttpStatus.BAD_REQUEST),
    INVALID_TIME_CREATE_BEFORE_10_HOURS(1022, "Please create before 10 hour", HttpStatus.BAD_REQUEST),
    FILM_NOT_FOUND(1023, "Film not found", HttpStatus.NOT_FOUND),
    ROOM_NOT_FOUND(1024, "Room not found", HttpStatus.NOT_FOUND),
    ROOM_NOT_AVAILABLE(1025, "Room was occupied during requested time", HttpStatus.BAD_REQUEST),
    SCHEDULE_NOT_FOUND(1026, "Schedule not found", HttpStatus.NOT_FOUND),
    SCHEDULE_IS_BOOKED(1027, "Schedule conflict", HttpStatus.BAD_REQUEST),
    CINEMA_NOT_FOUND(1028, "Cinema not found", HttpStatus.NOT_FOUND),
    ;

    private int code;
    private String message;
    private HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
