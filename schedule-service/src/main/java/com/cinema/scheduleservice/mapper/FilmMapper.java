package com.cinema.scheduleservice.mapper;

import com.cinema.scheduleservice.dto.response.FilmResponse;
import com.cinema.scheduleservice.entity.Film;
import com.cinema.scheduleservice.entity.Thumnail;
import com.cinema.scheduleservice.entity.Type;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@Mapper(componentModel = "spring")
public interface FilmMapper {
    //
    //    @Mapping(target = "typeIds", source = "types")
    //    FilmDto toFilmDto(Film film);
    @Mapping(target = "typeIds", source = "types")
    @Mapping(target = "thumnails", source = "thumnails")
    @Mapping(target = "typeNames", source = "types")
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

