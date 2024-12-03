package com.example.filmservice.service.Impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.cloudinary.Api;
import com.cloudinary.api.exceptions.ApiException;
import com.example.filmservice.dto.response.ListFilmResponse;
import com.example.filmservice.dto.response.PageInfo;
import com.example.filmservice.entity.Rating;
import com.example.filmservice.exception.AppException;
import com.example.filmservice.exception.ErrorCode;
import com.example.filmservice.repositories.*;
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
import com.example.filmservice.service.FilmService;

@Service
public class FilmServiceImpl implements FilmService {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ThumbnailsRepository thumbnailsRepository;

    @Autowired
    private FilmTypeRepository filmTypeRepository;
    @Autowired
    private RatingRepository ratingRepository;
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
                .msg("Success")
                .data(filmResponses)
                .build();
    }

    // TODO: Implement getFilms method ( THE METHOD IS GET_ALL FILMS WITH PAGINATION)
    @Override
    public ApiResponse<ListFilmResponse> getFilms(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Film> films = filmRepository.findAll(pageable);
        List<FilmResponse> filmResponses =
                films.getContent().stream().map(filmMapper::toFilmResponse).collect(Collectors.toList());
        PageInfo pageInfo = new PageInfo((int) films.getTotalElements(), size);
        ListFilmResponse filmPageResponse = new ListFilmResponse(filmResponses, pageInfo);


        return ApiResponse.<ListFilmResponse>builder()
                .code(1000)
                .msg("Success")
                .data(filmPageResponse)  // Trả về FilmPageResponse chứa danh sách phim và thông tin phân trang
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
            if (filmDto.getThumnails() != null) {
                for (MultipartFile file : filmDto.getThumnails()) {
                    Map<String, String> map = cloudinaryService.uploadFile(file);
                    Thumnail thumnail = new Thumnail();
                    thumnail.setUrl(map.get("url"));
                    thumnail.setPublicId(map.get("publicId"));
                    thumnail.setFilm(film);
                    thumnails.add(thumnail);
                }
                film.setThumnails(thumnails);
            }
        } catch (ParseException ex) {
            return ApiResponse.<FilmResponse>builder()
                    .code(1001)
                    .msg("Ngày khởi chiếu không đúng định dạng")
                    .build();
        } catch (IOException ex) {
            return ApiResponse.<FilmResponse>builder()
                    .code(1002)
                    .msg("Xảy ra lỗi trong quá trình upload ảnh")
                    .build();
        }
        film.setDuration(filmDto.getDuration());
        List<Type> types = getListFilmTypes(filmDto.getTypeIds());
        film.setTypes(types);
        film.setUrlTrailer(filmDto.getUrlTrailer());
        filmRepository.save(film);
        thumbnailsRepository.saveAll(thumnails);

        FilmResponse dataDto = filmMapper.toFilmResponse(film);

        // Trả về kết quả thành công với FilmDto
        return ApiResponse.<FilmResponse>builder()
                .code(1000)
                .msg("Success")
                .data(dataDto)
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
                    .msg("Không tìm thấy phim")
                    .build();
        }
        Film film = op.get();
        List<Thumnail> thumnails = film.getThumnails();
        if (editFilmDto.getDeleteThumbnails() != null) {
            List<Thumnail> thumnailsToRemove = new ArrayList<>();
            boolean foundThumbnail = false; // Biến kiểm tra thumbnail có tồn tại

            for (Integer idThum : editFilmDto.getDeleteThumbnails()) {
                Optional<Thumnail> thumnailOpt = thumnails.stream()
                        .filter(thumnail -> thumnail.getId().equals(idThum))
                        .findFirst();

                if (thumnailOpt.isPresent()) {
                    foundThumbnail = true; // Có thumbnail được tìm thấy
                    Thumnail thumnail = thumnailOpt.get();
                    thumnailsToRemove.add(thumnail);
                    try {
                        cloudinaryService.deleteFile(thumnail.getPublicId());
                        System.out.println("Đã xóa trên cloudinary");
                    } catch (IOException ex) {
                        throw new AppException(ErrorCode.THUMBNAIL_NOT_FOUND);
                    }
                    thumbnailsRepository.deleteById(idThum);
                    System.out.println("Đã xóa trên db");
                } else {
                    throw new AppException(ErrorCode.THUMBNAIL_NOT_FOUND);
                }
            }
            thumnails.removeAll(thumnailsToRemove);
            System.out.println("đã xóa xong");

        }
        film.setUrlTrailer(editFilmDto.getUrlTrailer());
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
                    .msg("Ngày khởi chiếu không đúng định dạng")
                    .build();
        } catch (IOException ex) {
            return ApiResponse.<FilmResponse>builder()
                    .code(1002)
                    .msg("Xảy ra lỗi trong quá trình upload ảnh")
                    .build();
        }
        film.setDuration(editFilmDto.getDuration());
        if (editFilmDto.getTypeIds() != null) {
            List<Type> types = getListFilmTypes(editFilmDto.getTypeIds());
            film.setTypes(types);
        }
        filmRepository.save(film);
//        thumbnailsRepository.saveAll(thumnails);
        FilmResponse dataDto = filmMapper.toFilmResponse(film);
        return ApiResponse.<FilmResponse>builder()
                .code(1000)
                .data(dataDto)
                .msg("Sửa ảnh thành công")
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
                    .msg("Không tìm thấy phim")
                    .build();
        Film film = op.get();
        List<Thumnail> thumnails = film.getThumnails();

        for (Thumnail thumnail : thumnails) {
            try {
                cloudinaryService.deleteFile(thumnail.getPublicId());
            } catch (IOException e) {
                return ApiResponse.<FilmResponse>builder()
                        .code(1004)
                        .msg("Xa ra lỗi trong quá trình xóa ảnh")
                        .build();
            }
        }
        filmRepository.delete(film);
        return ApiResponse.<FilmResponse>builder()
                .code(1000)
                .msg("Xóa phim thành công")
                .build();
    }

    // TODO : Implement getFilmById method
    @Override
    public ApiResponse getFilmById(Integer id) {
        Optional<Film> op = filmRepository.findById(id);
        if (!op.isPresent())
            return ApiResponse.<FilmResponse>builder()
                    .code(1004)
                    .msg("Không tìm thấy phim")
                    .build();
        Film film = op.get();
        FilmResponse dataDto = filmMapper.toFilmResponse(film);
        return ApiResponse.<Film>builder()
                .code(1000)
                .msg("Success")
                .data(film)
                .build();
    }

    @Override
    public ApiResponse<ListFilmResponse> searchFilm(String name, int page, int size) {
        if (name == null) {
            return ApiResponse.<ListFilmResponse>builder()
                    .code(1001)
                    .msg("Tên phim không được để trống")
                    .build();
        }
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Film> films = filmRepository.searchByName(name,pageable);
        List<FilmResponse> filmResponses =
                films.getContent().stream().map(filmMapper::toFilmResponse).collect(Collectors.toList());
        PageInfo pageInfo = new PageInfo((int) films.getTotalElements(), size);
        ListFilmResponse filmPageResponse = new ListFilmResponse(filmResponses, pageInfo);
        if (films.isEmpty())
            return ApiResponse.<ListFilmResponse>builder()
                    .code(1004)
                    .msg("Không tìm thấy phim")
                    .build();
        return ApiResponse.<ListFilmResponse>builder()
                .code(1000)
                .msg("Success")
                .data(filmPageResponse)
                .build();
    }

    @Override
    public FilmResponse updateScore(Integer filmId) {
        Film op = filmRepository.findById(filmId).orElseThrow(
                () -> new AppException(ErrorCode.FILM_NOT_FOUND)
        );
        List<Rating> ratings = ratingRepository.findAllByFilm(op);
        if (ratings == null || ratings.isEmpty())
            throw new AppException(ErrorCode.RATING_IS_EMPTY);
        List<Integer> stars = ratings.stream().map(Rating::getStar).collect(Collectors.toList());
        int sum = 0;
        for (Integer star: stars) {
            sum += star;
        }
        float avgScore = (float) sum / stars.size();
        avgScore = (float) (Math.round(avgScore * Math.pow(10, 1)) / Math.pow(10, 1));
        op.setScore(avgScore);
        filmRepository.save(op);

        return filmMapper.toFilmResponse(op);
    }

//

    private List<Type> getListFilmTypes(List<Integer> ids) {
        List<Type> types = new ArrayList<>();
        for (Integer id : ids) {
            Type type = filmTypeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Loại phim không tồn tại"));
            types.add(type);
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
