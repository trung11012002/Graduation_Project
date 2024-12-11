package com.example.filmservice.Mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Configuration;

import com.example.filmservice.dto.request.FilmDto;
import com.example.filmservice.dto.response.FilmResponse;
import com.example.filmservice.entity.Film;
import com.example.filmservice.entity.Thumnail;
import com.example.filmservice.entity.Type;

@Configuration
@Mapper(componentModel = "spring")
public interface FilmMapper {
    FilmMapper INSTANCE = Mappers.getMapper(FilmMapper.class);

    @Mapping(target = "typeIds", source = "types")
    @Mapping(target = "thumnails", ignore = true)
    FilmDto toFilmDto(Film film);
    //
    //    @Mapping(target = "typeIds", source = "types")
    //    FilmDto toFilmDto(Film film);
    @Mapping(target = "typeIds", source = "types")
    @Mapping(target = "thumnails", source = "thumnails")
    @Mapping(target = "typeNames", source = "types")
    @Mapping(target = "score", source = "score")
    @Mapping(target = "urlTrailer", source = "urlTrailer")
    FilmResponse toFilmResponse(Film film);

    default List<Integer> mapTypesToTypeIds(List<Type> types) {
        if (types == null) {
            return null;
        }
        return types.stream().map(Type::getId).collect(Collectors.toList());
    }

    default List<Thumnail> mapStringsToThumbnails(List<String> thumbnails) {
        if (thumbnails == null) {
            return null;
        }
        return thumbnails.stream().map(url -> new Thumnail(url)).collect(Collectors.toList());
    }

    default List<String> mapThumbnails(List<Thumnail> thumbnails) {
        if (thumbnails == null) {
            return null;
        }
        return thumbnails.stream().map(Thumnail::getUrl).collect(Collectors.toList());
    }
    default List<String> mapTypeNames(List<Type> types) {
        return types.stream().map(Type::getName).collect(Collectors.toList());
    }
}
