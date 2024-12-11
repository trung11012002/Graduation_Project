package com.cinema.scheduleservice.dto.response;

import jakarta.persistence.Column;
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
    private List<String> typeNames;
    private String description;
    private String releaseDate;
    private Integer duration;
    private String urlTrailer;
    private List<String> thumnails;
}