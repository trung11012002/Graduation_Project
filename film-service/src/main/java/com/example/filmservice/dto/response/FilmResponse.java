package com.example.filmservice.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmResponse {
    private Integer id;
    private String name;
    private List<Integer> typeIds;
    private List<String> typeNames;
    private String description;
    private String releaseDate;
    private Integer duration;
    private List<String> thumnails;
    private Float score;
    private String urlTrailer;

}
