package com.cinema.mapper;

import com.cinema.dto.request.ProfileCreationRequest;
import com.cinema.dto.response.UserProfileResponse;
import com.cinema.entity.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(ProfileCreationRequest request);

    UserProfileResponse toUserProfileReponse(UserProfile entity);
}
