package com.abdulchakam.movieservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequest {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id;

    @NotBlank(message = "Is mandatory")
    @Size(max = 150, message = "The maximum input title is 150 characters")
    private String title;

    @NotBlank(message = "Is mandatory")
    @Size(min = 15, message = "The minimum input title is 15 characters")
    private String description;

    @DecimalMax(value = "10", message = "The value exceeds the maximum input")
    private Float ranting;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String image;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String createdAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String updatedAt;
}
