package com.abdulchakam.movieservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {

    private Integer id;
    private String title;
    private String description;
    private String ranting;
    private String imagePath;
    private String createdAt;
    private String updatedAt;
}
