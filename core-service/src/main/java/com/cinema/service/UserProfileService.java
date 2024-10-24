package com.cinema.service;

import com.cinema.dto.ApiResponse;
import com.cinema.dto.request.ProfileCreationRequest;
import com.cinema.dto.response.CinemaResponse;
import com.cinema.dto.response.UserProfileResponse;
import com.cinema.entity.UserProfile;
import com.cinema.neo4j.UserProfileRepository;
import com.cinema.repository.UserRepository;
import com.cinema.repository.httpclient.CinemaClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.cinema.mapper.UserProfileMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserProfileService {
    UserProfileRepository userProfileRepository;
    UserProfileMapper userProfileMapper;
    UserRepository userRepository;
    CinemaClient cinemaClient;

    public UserProfileResponse createProfile(ProfileCreationRequest request) {
        UserProfile userProfile = userProfileMapper.toUserProfile(request);
        userProfile = userProfileRepository.save(userProfile);

        return userProfileMapper.toUserProfileReponse(userProfile);
    }

    public UserProfileResponse getProfile(String id) {
        UserProfile userProfile =
                userProfileRepository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));

        return userProfileMapper.toUserProfileReponse(userProfile);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserProfileResponse> getAllProfiles() {
        var profiles = userRepository.findByUsername("test");
        return null;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<String> testFeignClient() {
        ApiResponse<List<CinemaResponse>> cinemas = cinemaClient.testFeignClient();
        List<String> cinemaNames = new ArrayList<>();
        for(CinemaResponse cinema : cinemas.getData()) {
            cinemaNames.add(cinema.getName());
        }
        return cinemaNames;
    }
}
