package com.example.filmservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {
    private Integer totalItems;
    private Integer pageSize;
    private Integer totalPages;
}
