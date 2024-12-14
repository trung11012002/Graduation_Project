package com.example.filmservice.dto.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmDto {
    private Integer id;
    private String name;
    private List<Integer> typeIds;
    private String description;
    private String releaseDate;
    private Integer duration;
    private List<MultipartFile> thumnails;
    private String urlTrailer;

}
