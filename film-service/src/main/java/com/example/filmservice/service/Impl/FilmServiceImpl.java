package com.example.filmservice.service.Impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.cloudinary.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.filmservice.Mapper.FilmMapper;
import com.example.filmservice.cloudinary.CloudinaryService;
import com.example.filmservice.dto.request.EditFilmDto;
import com.example.filmservice.dto.request.FilmDto;
import com.example.filmservice.dto.response.ApiResponse;
import com.example.filmservice.dto.response.FilmResponse;
import com.example.filmservice.entity.Film;
import com.example.filmservice.entity.Thumnail;
import com.example.filmservice.entity.Type;
import com.example.filmservice.repositories.FilmRepository;
import com.example.filmservice.repositories.FilmTypeRepository;
import com.example.filmservice.repositories.ThumbnailsRepository;
import com.example.filmservice.service.FilmService;

@Service
public class FilmServiceImpl implements FilmService {
    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ThumbnailsRepository thumbnailsRepository;

    @Autowired
    private FilmTypeRepository filmTypeRepository;

    private final FilmMapper filmMapper;

    public FilmServiceImpl(FilmMapper filmMapper) {
        this.filmMapper = filmMapper;
    }

    //    @Autowired
    //    public FilmServiceImpl(FilmMapper filmMapper) {
    //        this.filmMapper = filmMapper;
    //    }
    // TODO: Implement getFilms method ( THE METHOD IS GET_ALL FILMS WITH NOT PAGINATION)

    @Override
    public ApiResponse<List<FilmResponse>> getAllFilms() {
        List<Film> films = filmRepository.findAll();
        List<FilmResponse> filmResponses =
                films.stream().map(filmMapper::toFilmResponse).toList();

        return ApiResponse.<List<FilmResponse>>builder()
                .code(1000)
                .message("Success")
                .result(filmResponses)
                .build();
    }
    // TODO: Implement getFilms method ( THE METHOD IS GET_ALL FILMS WITH PAGINATION)
    @Override
    public ApiResponse<List<FilmResponse>> getFilms(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Film> films = filmRepository.findAll(pageable);
        List<FilmResponse> filmResponses =
                films.getContent().stream().map(filmMapper::toFilmResponse).collect(Collectors.toList());
        return ApiResponse.<List<FilmResponse>>builder()
                .code(1000)
                .message("Success")
                .result(filmResponses)
                .build();
    }
    // TODO: Implement createFilm method
    @Transactional(transactionManager = "transactionManager")
    @Override
    public ApiResponse<FilmResponse> createFilm(FilmDto filmDto) {
        Film film = new Film();
        film.setName(filmDto.getName());
        film.setDescription(filmDto.getDescription());
        List<Thumnail> thumnails = new ArrayList<>();

        try {
            // Chuyển đổi ngày phát hành
            Date releaseDate = convertToDate(filmDto.getReleaseDate());
            film.setReleaseDate(releaseDate);

            for (MultipartFile file : filmDto.getThumnails()) {
                Map<String, String> map = cloudinaryService.uploadFile(file);
                Thumnail thumnail = new Thumnail();
                thumnail.setUrl(map.get("url"));
                thumnail.setPublicId(map.get("publicId"));
                thumnail.setFilm(film);
                thumnails.add(thumnail);
            }
            film.setThumnails(thumnails);
        } catch (ParseException ex) {
            return ApiResponse.<FilmResponse>builder()
                    .code(1001)
                    .message("Ngày khởi chiếu không đúng định dạng")
                    .build();
        } catch (IOException ex) {
            return ApiResponse.<FilmResponse>builder()
                    .code(1002)
                    .message("Xảy ra lỗi trong quá trình upload ảnh")
                    .build();
        }

        film.setDuration(filmDto.getDuration());
        List<Type> types = getListFilmTypes(filmDto.getTypeIds());
        film.setTypes(types);

        filmRepository.save(film);
        thumbnailsRepository.saveAll(thumnails);

        FilmResponse resultDto = filmMapper.toFilmResponse(film);

        // Trả về kết quả thành công với FilmDto
        return ApiResponse.<FilmResponse>builder()
                .code(1000)
                .message("Success")
                .result(resultDto)
                .build();
    }
    // TODO: Implement editFilm method
    @Transactional(transactionManager = "transactionManager")
    @Override
    public ApiResponse<FilmResponse> editFilm(EditFilmDto editFilmDto) {
        Integer id = editFilmDto.getId();
        Optional<Film> op = filmRepository.findById(id);
        if (!op.isPresent()) {
            return ApiResponse.<FilmResponse>builder()
                    .code(1001)
                    .message("Không tìm thấy phim")
                    .build();
        }
        Film film = op.get();
        List<Thumnail> thumnails = film.getThumnails();
        if (editFilmDto.getDeleteThumbnails() != null) {
            List<Thumnail> thumnailsToRemove = new ArrayList<>();
            for (Integer idThum : editFilmDto.getDeleteThumbnails()) {
                thumnails.stream()
                        .filter(thumnail -> thumnail.getId().equals(idThum))
                        .findFirst()
                        .ifPresent(thumnail -> {
                            thumnailsToRemove.add(thumnail);
                            try {
                                cloudinaryService.deleteFile(thumnail.getPublicId());
                            } catch (IOException ex) {
                                // Handle exception
                            }
                            thumbnailsRepository.deleteById(idThum);
                        });
            }
            thumnails.removeAll(thumnailsToRemove);
        }
        film.setName(editFilmDto.getName());
        film.setDescription(editFilmDto.getDescription());
        try {
            Date releaseDate = convertToDate(editFilmDto.getReleaseDate());
            film.setReleaseDate(releaseDate);
            if (editFilmDto.getThumnails() != null) {
                for (MultipartFile file : editFilmDto.getThumnails()) {
                    Map<String, String> map = cloudinaryService.uploadFile(file);
                    Thumnail thumnail = new Thumnail();
                    thumnail.setUrl(map.get("url"));
                    thumnail.setPublicId(map.get("publicId"));
                    thumnail.setFilm(film);
                    thumnails.add(thumnail);
                }
            }
            film.setThumnails(thumnails);
            thumbnailsRepository.saveAll(thumnails);
        } catch (ParseException ex) {
            return ApiResponse.<FilmResponse>builder()
                    .code(1001)
                    .message("Ngày khởi chiếu không đúng định dạng")
                    .build();
        } catch (IOException ex) {
            return ApiResponse.<FilmResponse>builder()
                    .code(1002)
                    .message("Xảy ra lỗi trong quá trình upload ảnh")
                    .build();
        }
        film.setDuration(editFilmDto.getDuration());
        List<Type> types = getListFilmTypes(editFilmDto.getTypeIds());
        film.setTypes(types);
        filmRepository.save(film);
//        thumbnailsRepository.saveAll(thumnails);
        FilmResponse resultDto = filmMapper.toFilmResponse(film);
        return ApiResponse.<FilmResponse>builder()
                .code(1000)
                .result(resultDto)
                .message("Sửa ảnh thành công")
                .build();
    }
    // TODO: Implement deleteFilmById method
    @Transactional(transactionManager = "transactionManager")
    @Override
    public ApiResponse deleteFilmById(Integer id) {
        Optional<Film> op = filmRepository.findById(id);
        if (!op.isPresent())
            return ApiResponse.<FilmResponse>builder()
                    .code(1004)
                    .message("Không tìm thấy phim")
                    .build();
        Film film = op.get();
        List<Thumnail> thumnails = film.getThumnails();

        for(Thumnail thumnail: thumnails) {
            try {
                cloudinaryService.deleteFile(thumnail.getPublicId());
            } catch (IOException e) {
                return ApiResponse.<FilmResponse>builder()
                        .code(1004)
                        .message("Xa ra lỗi trong quá trình xóa ảnh")
                        .build();
            }
        }
            filmRepository.delete(film);
            return ApiResponse.<FilmResponse>builder()
                    .code(1000)
                    .message("Xóa phim thành công")
                    .build();
    }
    // TODO : Implement getFilmById method
    @Override
    public ApiResponse getFilmById(Integer id) {
        Optional<Film> op = filmRepository.findById(id);
        if (!op.isPresent())
            return  ApiResponse.<FilmResponse>builder()
                    .code(1004)
                    .message("Không tìm thấy phim")
                    .build();
        Film film = op.get();
        FilmResponse resultDto = filmMapper.toFilmResponse(film);
        return ApiResponse.<FilmResponse>builder()
                .code(1000)
                .message("Success")
                .result(resultDto)
                .build();
    }

    @Override
    public ApiResponse searchFilm(String name) {
        if (name == null) {
            return ApiResponse.<FilmResponse>builder()
                    .code(1001)
                    .message("Tên phim không được để trống")
                    .build();
        }
        List<Film> films = filmRepository.searchByName(name);
        if (films.isEmpty())
            return ApiResponse.<FilmResponse>builder()
                    .code(1004)
                    .message("Không tìm thấy phim")
                    .build();
        return ApiResponse.<List<FilmResponse>>builder()
                .code(1000)
                .message("Success")
                .result(films.stream().map(filmMapper::toFilmResponse).collect(Collectors.toList()))
                .build();
    }

//

    private List<Type> getListFilmTypes(List<Integer> ids) {
        List<Type> types = new ArrayList<>();
        for (Integer id : ids) {
            Optional<Type> op = filmTypeRepository.findById(id);
            op.ifPresent(types::add);
        }
        return types;
    }

    private Date convertToDate(String strDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(strDate);
    }

    @Override
    public Page<FilmResponse> getAllFilmsOrderByCreateDate(int page, int size) {
        return null;
    }
}
