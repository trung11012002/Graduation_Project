package com.cinema.dto.response;

import java.util.List;

import com.cinema.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {

    private Integer id;

    private String name;

    @JsonIgnore
    private List<User> users;
}
