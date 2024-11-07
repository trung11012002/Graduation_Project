package com.cinema.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileEditRequest {
    private Integer id;
    private String username;
    private String password;
    private String fullname;
    private String dateOfBirth;
    private String address;
    private String email;
    private String phone;
}
