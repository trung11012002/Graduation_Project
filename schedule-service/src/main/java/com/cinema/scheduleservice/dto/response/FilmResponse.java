package com.cinema.scheduleservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmResponse {
    private Integer id;
    private String name;
    private List<Integer> typeIds;
    private String description;
    private String releaseDate;
    private Integer duration;
    private List<String> thumnails;
}