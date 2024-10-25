package com.cinema.scheduleservice.service.Impl;

import com.cinema.scheduleservice.dto.request.ScheduleDto;
import com.cinema.scheduleservice.dto.response.*;
import com.cinema.scheduleservice.entity.*;
import com.cinema.scheduleservice.exception.AppException;
import com.cinema.scheduleservice.exception.ErrorCode;
import com.cinema.scheduleservice.mapper.FilmMapper;
import com.cinema.scheduleservice.mapper.ScheduleMapper;
import com.cinema.scheduleservice.repositories.*;
import com.cinema.scheduleservice.service.ScheduleService;
import com.cloudinary.api.exceptions.ApiException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduleResponseImpl implements ScheduleService {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ScheduleMapper scheduleMapper;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private FilmMapper filmMapper;

    @Transactional(transactionManager = "transactionManager")
    @Override
    public ScheduleCreateResponse createSchedule(ScheduleDto dto) {
        LocalDateTime startTime = convertToLocalDateTimeFromString(dto.getStartTime());
        checkTime(startTime);
        Optional<Film> op1 = filmRepository.findById(dto.getFilmId());
        if (!op1.isPresent())
            throw new AppException(ErrorCode.FILM_NOT_FOUND);
        Film film = op1.get();
        Integer durations = film.getDuration(); // in seconds
        LocalDateTime endTime = startTime.plusMinutes(durations);
        Optional<Room> op2 = roomRepository.findById(dto.getRoomId());
        if (!op2.isPresent())
            throw new AppException(ErrorCode.ROOM_NOT_FOUND);
        Room room = op2.get();
        boolean isRoomAvailable = isRoomAvailable(room, startTime, endTime);
        if (!isRoomAvailable) {
            throw new AppException(ErrorCode.ROOM_NOT_AVAILABLE);
        }
        Schedule schedule = new Schedule();
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);
        schedule.setFilm(film);
        schedule.setRoom(room);
        scheduleRepository.save(schedule);
        List<Schedule> schedules = room.getSchedules();
        if (schedules == null) {
            schedules = new ArrayList<>();
        }
        schedules.add(schedule);
        room.setSchedules(schedules);
        roomRepository.save(room);
        ScheduleCreateResponse response = scheduleMapper.toScheduleCreateResponse(schedule);
        return response;
    }

    @Override
    public ScheduleCreateResponse getScheduleById(Integer id) {
        Optional<Schedule> op = scheduleRepository.findById(id);
        if (!op.isPresent())
            throw new AppException(ErrorCode.SCHEDULE_NOT_FOUND);
        else
            return scheduleMapper.toScheduleCreateResponse(op.get());
    }

    @Override
    public ScheduleCreateResponse updateSchedule(ScheduleDto dto) {
        Optional<Schedule> optional = scheduleRepository.findById(dto.getId());
        if (!optional.isPresent())
            throw new AppException(ErrorCode.SCHEDULE_NOT_FOUND);
        if (isBooked(dto.getId()))
            throw new AppException(ErrorCode.SCHEDULE_IS_BOOKED);
        Schedule schedule = optional.get();
        LocalDateTime startTime = convertToLocalDateTimeFromString(dto.getStartTime());
        checkTime(startTime);
        Optional<Film> op1 = filmRepository.findById(dto.getFilmId());
        if (!op1.isPresent())
            throw new AppException(ErrorCode.FILM_NOT_FOUND);
        Film film = op1.get();
        Integer durations = film.getDuration(); // in seconds
        LocalDateTime endTime = startTime.plusMinutes(durations);
        Optional<Room> op2 = roomRepository.findById(dto.getRoomId());
        if (!op2.isPresent())
            throw new AppException(ErrorCode.ROOM_NOT_FOUND);
        Room room = op2.get();
        boolean isRoomAvailable = isRoomAvailable(room, startTime, endTime);
        if (!isRoomAvailable) {
            throw new AppException(ErrorCode.ROOM_NOT_AVAILABLE);
        }
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);
        schedule.setFilm(film);
        schedule.setRoom(room);
        scheduleRepository.save(schedule);
        return scheduleMapper.toScheduleCreateResponse(schedule);
    }

    @Override
    public ScheduleCreateResponse deleteSchedule(Integer id) {
        Optional<Schedule> optional = scheduleRepository.findById(id);
        if (!optional.isPresent())
            throw new AppException(ErrorCode.SCHEDULE_NOT_FOUND);
        if (isBooked(id))   // check if schedule is booked
            throw new AppException(ErrorCode.SCHEDULE_IS_BOOKED);
        scheduleRepository.deleteById(id);
        return scheduleMapper.toScheduleCreateResponse(optional.get());
    }

    @Override
    public List<ScheduleResponse> findAllCurrentScheduleInCinema(Integer cinemaId) {
        Optional<Cinema> op = cinemaRepository.findById(cinemaId);
        if (!op.isPresent())
            throw new AppException(ErrorCode.CINEMA_NOT_FOUND);
        List<Schedule> schedules = findScheduleByTimeOption(op.get(), false);
        Collections.sort(schedules, Comparator.comparing(Schedule::getStartTime));
        Collections.reverse(schedules);
        List<ScheduleResponse> responses = new ArrayList<>();
        for (Schedule schedule : schedules) {
            ScheduleResponse response = scheduleMapper.toScheduleResponse(schedule);

            Room room = schedule.getRoom();
            int totalSeats = room.getHorizontalSeats() * room.getVerticalSeats();
            response.setTotalSeats(totalSeats);
            int booked = ticketRepository.countByScheduleId(schedule.getId());
            response.setAvailables(totalSeats - booked);

            responses.add(response);
        }

        return responses;
    }

    @Override
    public ListScheduleResponseByPage findAllCurrentScheduleInCinemaByPage(Integer cinemaId, int page, int size) {
        Optional<Cinema> op = cinemaRepository.findById(cinemaId);
        if (!op.isPresent())
            throw new AppException(ErrorCode.CINEMA_NOT_FOUND);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("startTime").ascending());
        Page<Schedule> schedulesPage = findScheduleByTimeOption_withpage(op.get(), false, pageable);
        List<ScheduleResponse> responses = new ArrayList<>();
        for (Schedule schedule : schedulesPage.getContent()) {
            ScheduleResponse response = scheduleMapper.toScheduleResponse(schedule);
            Room room = schedule.getRoom();
            int totalSeats = room.getHorizontalSeats() * room.getVerticalSeats();
            response.setTotalSeats(totalSeats);
            int booked = ticketRepository.countByScheduleId(schedule.getId());
            response.setAvailables(totalSeats - booked);

            responses.add(response);
        }
        PageInfo pageInfo = new PageInfo((int) schedulesPage.getTotalElements(), size);
        ListScheduleResponseByPage res = new ListScheduleResponseByPage(responses, pageInfo);
        return res;
    }

    @Override
    public ListScheduleResponseByPage findAllHistoryScheduleInCinemaByPage(Integer cinemaId, int page, int size) {
        Optional<Cinema> op = cinemaRepository.findById(cinemaId);
        if (!op.isPresent())
            throw new AppException(ErrorCode.CINEMA_NOT_FOUND);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("startTime").ascending());
        Page<Schedule> schedules = findScheduleByTimeOption_withpage(op.get(), true,pageable);
        List<ScheduleResponse> responses = new ArrayList<>();
        for (Schedule schedule: schedules.getContent()) {
            ScheduleResponse response = scheduleMapper.toScheduleResponse(schedule);
            Room room = schedule.getRoom();
            int totalSeats = room.getHorizontalSeats() * room.getVerticalSeats();
            response.setTotalSeats(totalSeats);
            int booked = ticketRepository.countByScheduleId(schedule.getId());
            response.setAvailables(totalSeats - booked);

            responses.add(response);
        }
        PageInfo pageInfo = new PageInfo((int) schedules.getTotalElements(), size);
        ListScheduleResponseByPage res = new ListScheduleResponseByPage(responses, pageInfo);
        return res;

    }

    @Override
    public List<ShowFilmByDayResponse> findAllScheduleInCinemaByDay(Integer cinemaId, String date) {
        List<Room> rooms = roomRepository.findByCinemaId(cinemaId);
        LocalDateTime dateStart = convertToLocalDateTimeFromString(date + "T00:00:00");
        if (dateStart.isBefore(LocalDateTime.now())) {
            dateStart = LocalDateTime.now();
        }
        LocalDateTime endDate = convertToLocalDateTimeFromString(date + "T23:59:59");
        Map<Integer, Integer> map = new HashMap<>();
        List<ShowFilmByDayResponse> result = new ArrayList<>();
        int index = 0;
        for (Room room: rooms) {
            List<Schedule> schedules = scheduleRepository.findByDateRangeAndRoomId(dateStart, endDate, room.getId());
            for (Schedule schedule: schedules) {
                Film film = schedule.getFilm();
                FilmResponse filmResponse = filmMapper.toFilmResponse(film);
                if (map.containsKey(filmResponse.getId())) {
                    int position = map.get(film.getId());
                    ShowFilmByDayResponse response = result.get(position);
                    List<ScheduleResponse> scheduleResponseList = response.getScheduleResponseList();
                    scheduleResponseList.add(scheduleMapper.toScheduleResponse(schedule));
                    response.setScheduleResponseList(scheduleResponseList);
                    result.set(position, response);
                }
                else {
                    ShowFilmByDayResponse response = new ShowFilmByDayResponse();
                    response.setFilmResponse(filmResponse);
                    List<Schedule> list = new ArrayList<>();
                    list.add(schedule);
                    response.setScheduleResponseList(scheduleMapper.toScheduleResponseList(list));
                    result.add(response);
                    map.put(filmResponse.getId(), index);
                    index++;
                }
            }
        }
        return result;
    }

    @Override
    public List<OrderedResponse> findAllOrdered(Integer scheduleId) {
        List<Ticket> tickets = ticketRepository.findByScheduleId(scheduleId)
                .orElseThrow(() -> new AppException(ErrorCode.TICKET_NOT_FOUND));
        List<Booking> bookings = new ArrayList<>(tickets.stream().map(Ticket::getBooking).collect(Collectors.toSet()));
        List<OrderedResponse> responses = new ArrayList<>();
        Map<Integer, Integer> mark = new HashMap<>();
        int index = 0;
        for (Ticket ticket: tickets) {
            for (Booking booking : bookings) {
                if (ticket.getBooking().getId().equals(booking.getId())) {
                    if (mark.containsKey(booking.getId())) {
                        int currentIndex = mark.get(booking.getId());
                        OrderedResponse order = responses.get(currentIndex);
                        order.setNumberOfTicket(order.getNumberOfTicket() + 1);
                        if (ticket.getTicketClass().equals(0)) {
                            order.setRegulars(order.getRegulars() + 1);
                        } else {
                            order.setVips(order.getVips() + 1);
                        }
                        order.setSeats(order.getSeats() + ", " + ticket.getSeatNumberVertical() + "-" + ticket.getSeatNumberHorizontal());
                        order.setTotalPaid((order.getTotalPaid() + ticket.getPrice()));
                        responses.set(currentIndex, order);
                    } else {
                        OrderedResponse order = new OrderedResponse();
                        User user = booking.getUser();
                        order.setFullname(user.getFullname());
                        order.setEmail(user.getEmail());
                        order.setBookedTime(booking.getBookingTime());
                        order.setNumberOfTicket(1);
                        if (ticket.getTicketClass().equals(0)) {
                            order.setRegulars(1);
                            order.setVips(0);
                        } else {
                            order.setRegulars(0);
                            order.setVips(1);
                        }
                        order.setSeats(ticket.getSeatNumberVertical() + "-" + ticket.getSeatNumberHorizontal());
                        order.setTotalPaid(ticket.getPrice());
                        responses.add(order);
                        mark.put(booking.getId(), index++);
                    }
                }
            }
        }
        return responses;
    }

    @Override
    public RevenueStatisticResponse getRevenueStatistic(Integer cinemaId, String startDate, String endDate) {
        Optional<Cinema> op = cinemaRepository.findById(cinemaId);
        if (!op.isPresent())
            throw new AppException(ErrorCode.CINEMA_NOT_FOUND);
        Cinema cinema = op.get();
        LocalDateTime start = convertToLocalDateTimeFromString(startDate + "T00:00:00");
        LocalDateTime end = convertToLocalDateTimeFromString(endDate + "T23:59:59");
        if (end.isBefore(start)) {
            throw new AppException(ErrorCode.INVALID_TIME);
        }
        List<Schedule> schedules = scheduleRepository.findAllByRoom_CinemaAndStartTimeAfterAndStartTimeBefore(cinema, start, end);
        long totalRevenue = 0L;
        List<ScheduleRevenueStatistic> revenueSchedules = new ArrayList<>();
        for (Schedule schedule: schedules) {
            Film film = schedule.getFilm();
            FilmResponse filmResponse = filmMapper.toFilmResponse(film);
            ScheduleResponse response = scheduleMapper.toScheduleResponse(schedule);
            ScheduleRevenueStatistic revenueSchedule = new ScheduleRevenueStatistic();
            revenueSchedule.setFilmResponse(filmResponse);
            revenueSchedule.setRoomId(response.getRoomId());
            revenueSchedule.setShowDate(schedule.getStartTime());

            List<Ticket> tickets = ticketRepository.findByScheduleId(schedule.getId()).orElseThrow(
                    () -> new AppException(ErrorCode.TICKET_NOT_FOUND)
            );
            revenueSchedule.setTicketsSold(tickets.size());
            long revenue = tickets.stream()
                    .mapToLong(Ticket::getPrice).sum();
            revenueSchedule.setRevenue(revenue);
            revenueSchedules.add(revenueSchedule);
            totalRevenue += revenue;
        }
        RevenueStatisticResponse response = new RevenueStatisticResponse();
        response.setTotalRevenue(totalRevenue);
        response.setScheduleRevenueStatistic(revenueSchedules);
        return response;
    }

    @Override
    public SeatsStatus findAllBookedSeat(Integer scheduleId) {
        Optional<Schedule> op = scheduleRepository.findById(scheduleId);
        if (!op.isPresent())
            throw new AppException(ErrorCode.SCHEDULE_NOT_FOUND);
        Schedule schedule = op.get();
        ScheduleResponse scheduleResponse = scheduleMapper.toScheduleResponse(schedule);
        SeatsStatus seatsStatus = new SeatsStatus();
        Room room = schedule.getRoom();
        seatsStatus.setScheduleResponse(scheduleResponse);
        seatsStatus.setRow(room.getVerticalSeats());
        seatsStatus.setColumn(room.getHorizontalSeats());
        List<Ticket> tickets = ticketRepository.findByScheduleId(scheduleId).orElseThrow(
                () -> new AppException(ErrorCode.TICKET_NOT_FOUND)
        );
        List<String> responses = new ArrayList<>();
        for (Ticket ticket: tickets) {
            responses.add(ticket.getSeatNumberVertical() + "-" + ticket.getSeatNumberHorizontal());
        }
        seatsStatus.setBookedSeats(responses);
        return seatsStatus;
    }

    private List<Schedule> findScheduleByTimeOption(Cinema cinema, boolean history) {

        LocalDateTime now = LocalDateTime.now();
        List<Schedule> schedules;
        if (history) {
            schedules = scheduleRepository.findByRoom_CinemaAndEndTimeBefore(cinema, now);
        } else {
            schedules = scheduleRepository.findByRoom_CinemaAndEndTimeAfter(cinema, now);
        }

        return schedules;
    }

    //TODO: WITH PAGE
    public Page<Schedule> findScheduleByTimeOption_withpage(Cinema cinema, boolean history, Pageable pageable) {
        LocalDateTime now = LocalDateTime.now();
        Page<Schedule> schedules;

        if (history) {
            schedules = scheduleRepository.findByRoom_CinemaAndEndTimeBefore(cinema, now, pageable);
        } else {
            schedules = scheduleRepository.findByRoom_CinemaAndEndTimeAfter(cinema, now, pageable);
        }

        return schedules;
    }

    private boolean isBooked(int scheduleId) {
        int bookedNumber = ticketRepository.countByScheduleId(scheduleId);
        return bookedNumber > 0;
    }

    private LocalDateTime convertToLocalDateTimeFromString(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    private void checkTime(LocalDateTime dateTime) {
        // so sanh voi thoi gian hien tai
        LocalDateTime now = LocalDateTime.now();
        if (dateTime.isBefore(now))
            throw new AppException(ErrorCode.INVALID_TIME);
        // neu thoi gian start truoc 10 tieng
        if (dateTime.isBefore(now.plusHours(10)))
            throw new AppException(ErrorCode.INVALID_TIME_CREATE_BEFORE_6_HOURS);
    }

    private boolean isRoomAvailable(Room room, LocalDateTime startTime, LocalDateTime endTime) {
        List<Schedule> conflictingSchedules = scheduleRepository.findByRoomAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
                room, endTime, startTime);

        return conflictingSchedules.isEmpty();
    }
}
