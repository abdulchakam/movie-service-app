package com.abdulchakam.movieservice.controller;

import com.abdulchakam.movieservice.controlller.MovieController;
import com.abdulchakam.movieservice.dto.BaseResponse;
import com.abdulchakam.movieservice.dto.MovieRequest;
import com.abdulchakam.movieservice.dto.MovieResponse;
import com.abdulchakam.movieservice.model.Movie;
import com.abdulchakam.movieservice.service.MovieServiceImpl;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class MovieControllerTest {

    @Mock
    private MovieServiceImpl movieService;

    @InjectMocks
    private MovieController movieController;

    private Validator validator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private Movie movieMock() {
        return Movie.builder()
                .id(1)
                .title("Movie title")
                .description("Description 1")
                .ranting(8.8f)
                .image("image")
                .build();
    }

    private MovieRequest movieRequestMock() {
        return MovieRequest.builder()
                .id(1)
                .title("Movie title")
                .description("description")
                .ranting(8.8f)
                .image("image")
                .build();
    }
    private MovieResponse movieResponseMock() {
        return MovieResponse.builder()
                .id(1)
                .title("Movie Title")
                .description("Movie Description")
                .ranting(5.5f)
                .createdAt(new Date().toString())
                .imagePath("Imaget path")
                .build();
    }

    private BaseResponse baseResponseMock() {
        return BaseResponse.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .content(movieResponseMock())
                .build();
    }


    @Test
    void testCreateMovie_expectSuccess() {
        when(movieService.create(movieRequestMock())).thenReturn(baseResponseMock());
        ResponseEntity<BaseResponse> response = movieController.create(movieRequestMock());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(movieResponseMock(), response.getBody().getContent());
    }

    @Test
    void testShowAllMovie_expectSuccess() {
        when(movieService.showAll()).thenReturn(baseResponseMock());
        ResponseEntity<BaseResponse> response = movieController.showAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movieResponseMock(), response.getBody().getContent());
    }

    @Test
    void testShowMovie_expectSuccess() {
        when(movieService.show(anyInt())).thenReturn(baseResponseMock());
        ResponseEntity<BaseResponse> response = movieController.show(anyInt());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movieResponseMock(), response.getBody().getContent());
    }

    @Test
    void testUpdateMovie_expectSuccess() {
        when(movieService.update(any(), any())).thenReturn(baseResponseMock());
        ResponseEntity<BaseResponse> response = movieController.update(any(), any());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movieResponseMock(), response.getBody().getContent());
    }

    @Test
    void testDeleteMovie_expectSuccess() {
        when(movieService.delete(any())).thenReturn(baseResponseMock());
        ResponseEntity<BaseResponse> response = movieController.delete(any());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movieResponseMock(), response.getBody().getContent());
    }
}
