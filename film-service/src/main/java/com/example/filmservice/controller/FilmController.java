package com.example.filmservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.filmservice.dto.request.EditFilmDto;
import com.example.filmservice.dto.request.FilmDto;
import com.example.filmservice.dto.response.ApiResponse;
import com.example.filmservice.dto.response.FilmResponse;
import com.example.filmservice.service.FilmService;

// @CrossOrigin(origins = "*")
@RestController
@RequestMapping("/films")
public class FilmController {
    //    @Autowired
    //    private UserService userService;

    @Autowired
    private FilmService filmService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<FilmResponse>>> getAllFilms() {
        ApiResponse<List<FilmResponse>> response = filmService.getAllFilms();
        return ResponseEntity.ok(response);
    }
    // phan trang
    @GetMapping("/all_v2")
    public ResponseEntity<ApiResponse<List<FilmResponse>>> getAllFilms_v2(
            @RequestParam int page, @RequestParam int size) {
        ApiResponse<List<FilmResponse>> response = filmService.getFilms(page, size);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<FilmResponse>> createFilm(@ModelAttribute FilmDto filmDto) {
        ApiResponse<FilmResponse> response = filmService.createFilm(filmDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    public ResponseEntity<ApiResponse<FilmResponse>> editFilm(@ModelAttribute EditFilmDto editFilmDto) {
        ApiResponse<FilmResponse> response = filmService.editFilm(editFilmDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse<FilmResponse>> getFilmById(@PathVariable Integer id) {
        ApiResponse<FilmResponse> response = filmService.getFilmById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<FilmResponse>> deleteFilm(@PathVariable Integer id) {
        ApiResponse<FilmResponse> response = filmService.deleteFilmById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/search-film-by-name")
    public ResponseEntity<ApiResponse<List<FilmResponse>>> searchFilm(@RequestParam String keyword) {
        ApiResponse<List<FilmResponse>> response = filmService.searchFilm(keyword);
        return ResponseEntity.ok(response);
    }

}
