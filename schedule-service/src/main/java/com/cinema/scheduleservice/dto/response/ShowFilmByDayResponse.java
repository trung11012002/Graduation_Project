package com.cinema.scheduleservice.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowFilmByDayResponse {
    private FilmResponse filmResponse;
    private List<ScheduleResponse> scheduleResponseList;
}
