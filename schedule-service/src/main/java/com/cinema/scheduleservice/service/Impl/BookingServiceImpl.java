package com.cinema.scheduleservice.service.Impl;

import com.cinema.scheduleservice.client.ReviewClient;
import com.cinema.scheduleservice.dto.request.BookingDto;
import com.cinema.scheduleservice.dto.response.*;
import com.cinema.scheduleservice.entity.Booking;
import com.cinema.scheduleservice.entity.Film;
import com.cinema.scheduleservice.entity.Ticket;
import com.cinema.scheduleservice.entity.User;
import com.cinema.scheduleservice.exception.AppException;
import com.cinema.scheduleservice.exception.ErrorCode;
import com.cinema.scheduleservice.mapper.CinemaMapper;
import com.cinema.scheduleservice.mapper.FilmMapper;
import com.cinema.scheduleservice.repositories.BookingRepository;
import com.cinema.scheduleservice.repositories.TicketRepository;
import com.cinema.scheduleservice.repositories.UserRepository;
import com.cinema.scheduleservice.service.BookingService;
import com.cinema.scheduleservice.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ReviewClient reviewClient;

    @Autowired
    private FilmMapper filmMapper;
    @Autowired
    private CinemaMapper cinemaMapper;
    @Override
    public BookingResponse checkAvailableSeats(BookingDto bookingDto) {
        List<Ticket> ticketsBooked = ticketRepository.findByScheduleId(bookingDto.getScheduleId())
                .orElseThrow(() -> new AppException(ErrorCode.TICKET_NOT_FOUND));
        if (!ticketsBooked.isEmpty()) {
            List<Seat> seats = bookingDto.getSeats();
            for (Ticket ticket : ticketsBooked) {
                for (Seat seat : seats) {
                    if (seat.getRow()
                            .equals(ticket.getSeatNumberHorizontal()) && seat.getColumn()
                            .equals(ticket.getSeatNumberVertical()))
                        throw new AppException(ErrorCode.SEAT_ALREADY_BOOKED);
                }
            }
        }
        BookingResponse response = new BookingResponse();
        response.setScheduleId(bookingDto.getScheduleId());
        response.setSeats(bookingDto.getSeats());
        List<Integer> price = new ArrayList<>();
        for (Seat seat : bookingDto.getSeats()) {
            if (seat.getRow() <= 4) {
                price.add(50000);
            } else {
                price.add(80000);
            }
        }
        response.setPrices(price);
        return response;
    }

    @Override
    public List<HistoryBookingResponse> HISTORY_BOOKING_RESPONSES(String token) {
        String username = tokenUtils.getUsernameFromToken(token);
        Optional<User> op = userRepository.findByUsername(username);
        if (!op.isPresent()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        User user = op.get();
        System.out.println(user.getId());
        List<Booking> bookings = bookingRepository.findAllByUserId(Long.valueOf(user.getId()))
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND));
        Collections.sort(bookings, Comparator.comparing(Booking::getBookingTime, Comparator.reverseOrder()));
        List<HistoryBookingResponse> responses = new ArrayList<>();
        for (Booking booking : bookings) {
            HistoryBookingResponse response = new HistoryBookingResponse();
            response.setTimeBooking(booking.getBookingTime());
            response.setId(booking.getId());
            List<Ticket> tickets = booking.getTickets();
            List<TicketResponse> ticketResponseList = TicketResponse.converToTicketResponse(tickets);
            response.setTickets(ticketResponseList);
            Film film = tickets.get(0).getSchedule().getFilm();
            FilmResponse filmResponse = filmMapper.toFilmResponse(film);
            response.setFilmResponse(filmResponse);
            response.setCinema(cinemaMapper.toCinemaResponse(tickets.get(0).getSchedule().getRoom().getCinema()));

            Long totalPaid = tickets.stream().mapToLong(Ticket::getPrice).sum();
            response.setTotalPrice(totalPaid);

            ApiResponse<RatingDtoRepsonse> apiResponse = reviewClient.checkUserRatingInAFilm(user.getId(), film.getId());
            if (apiResponse.getData() != null){
                RatingDtoRepsonse ratingDtoRepsonse = apiResponse.getData();
                response.setRated(true);
                response.setRatingDtoRepsonse(ratingDtoRepsonse);
            } else {
                response.setRated(false);
            }
            responses.add(response);
        }

        return responses;
    }
}
