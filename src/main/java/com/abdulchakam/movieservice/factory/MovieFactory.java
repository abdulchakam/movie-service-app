package com.abdulchakam.movieservice.factory;

import com.abdulchakam.movieservice.dto.BaseResponse;
import com.abdulchakam.movieservice.dto.MovieRequest;
import com.abdulchakam.movieservice.dto.MovieResponse;
import com.abdulchakam.movieservice.exception.DataNotFoundException;
import com.abdulchakam.movieservice.model.Movie;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Component
public class MovieFactory {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Movie requestToEntity(MovieRequest request) {
        Movie movie = new Movie();

        if (!ObjectUtils.isEmpty(request)) {
            BeanUtils.copyProperties(request, movie);

        } else {
            throw new DataNotFoundException("Request not found!");
        }

        return movie;
    }

    public List<MovieResponse> entityToDto(List<Movie> movieList) {
        return movieList.stream()
                .map(this::entityToDto)
                .toList();
    }

    public MovieResponse entityToDto(Movie movie) {
        MovieResponse response = new MovieResponse();
        Date createdAt = movie.getCreatedAt();
        Date updatedAt = movie.getUpdatedAt();

        BeanUtils.copyProperties(movie, response);
        response.setImagePath(movie.getImage());
        response.setCreatedAt(createdAt != null ? convertDateToLocalDateTime(createdAt) : "");
        response.setUpdatedAt(updatedAt != null ? convertDateToLocalDateTime(updatedAt) : "");

        return response;
    }

    private String convertDateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).format(formatter);
    }

    public BaseResponse setResponse(Integer status, String message, Object... content) {
        BaseResponse response = new BaseResponse();
        response.setStatus(status);
        response.setMessage(message);

        if (content.length == 1) {
            response.setContent(content[0]);
        } else if (content.length > 1) {
            response.setContent(content);
        }

        return response;
    }
}