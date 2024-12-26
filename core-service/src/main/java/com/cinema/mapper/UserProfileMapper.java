package com.cinema.mapper;

import org.mapstruct.Mapper;

import com.cinema.dto.request.ProfileCreationRequest;
import com.cinema.dto.response.UserProfileResponse;
import com.cinema.entity.UserProfile;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(ProfileCreationRequest request);

    UserProfileResponse toUserProfileReponse(UserProfile entity);
}
