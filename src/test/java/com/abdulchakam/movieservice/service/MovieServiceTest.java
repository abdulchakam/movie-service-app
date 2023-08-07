package com.abdulchakam.movieservice.service;

import com.abdulchakam.movieservice.dto.BaseResponse;
import com.abdulchakam.movieservice.dto.MovieRequest;
import com.abdulchakam.movieservice.dto.MovieResponse;
import com.abdulchakam.movieservice.exception.DataAlreadyExistException;
import com.abdulchakam.movieservice.exception.DataNotFoundException;
import com.abdulchakam.movieservice.factory.MovieFactory;
import com.abdulchakam.movieservice.factory.MovieFactoryTest;
import com.abdulchakam.movieservice.model.Movie;
import com.abdulchakam.movieservice.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MovieServiceTest {

    @Mock
    private MovieFactory movieFactory;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
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

    private Movie movieMock2() {
        return Movie.builder()
                .id(2)
                .title("Movie title")
                .description("Description 2")
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
        when(movieFactory.requestToEntity(movieRequestMock())).thenReturn(movieMock());
        when(movieFactory.setResponse(any(), any(), any())).thenReturn(
                BaseResponse.builder()
                        .status(HttpStatus.CREATED.value())
                        .message(HttpStatus.CREATED.getReasonPhrase()).build());
        when(movieRepository.save(any(Movie.class))).thenReturn(movieMock());

        BaseResponse response = movieService.create(movieRequestMock());

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(HttpStatus.CREATED.getReasonPhrase(), response.getMessage());
    }

    @Test
    void testCreateMovie_expectAlreadyDataExist() {
        when(movieFactory.requestToEntity(movieRequestMock())).thenReturn(movieMock());
        when(movieRepository.findByTitle(anyString())).thenReturn(movieMock2());

        doThrow(DataAlreadyExistException.class).when(movieRepository).save(any(Movie.class));
        assertThrows(DataAlreadyExistException.class, () -> movieService.create(movieRequestMock()));
    }

    @Test
    void testShowAllMovie_expectSuccess() {
        when(movieFactory.entityToDto(List.of(movieMock()))).thenReturn(List.of(movieResponseMock()));
        when(movieFactory.setResponse(any(), any(), any())).thenReturn(baseResponseMock());
        when(movieRepository.findAll()).thenReturn(List.of(movieMock()));

        BaseResponse response = movieService.showAll();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(HttpStatus.OK.getReasonPhrase(), response.getMessage());
    }

    @Test
    void testShowMovie_expectSuccess() {
        when(movieFactory.entityToDto(movieMock())).thenReturn(movieResponseMock());
        when(movieFactory.setResponse(any(), any(), any())).thenReturn(baseResponseMock());
        when(movieRepository.findById(any())).thenReturn(Optional.of(movieMock()));

        BaseResponse response = movieService.show(1);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(HttpStatus.OK.getReasonPhrase(), response.getMessage());
    }

    @Test
    void testUpdateMovie_expectSuccess() {
        when(movieFactory.requestToEntity(movieRequestMock())).thenReturn(movieMock());
        when(movieRepository.findById(1)).thenReturn(Optional.of(movieMock()));
        when(movieRepository.save(any(Movie.class))).thenReturn(movieMock());
        when(movieFactory.setResponse(any(), any(), any())).thenReturn(
                BaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase()).build());

        BaseResponse response = movieService.update(movieRequestMock(), 1);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(HttpStatus.OK.getReasonPhrase(), response.getMessage());
    }

    @Test
    void testUpdateMovie_expectMovieNotFound() {
        when(movieFactory.requestToEntity(movieRequestMock())).thenReturn(movieMock());
        when(movieRepository.findById(1)).thenReturn(Optional.empty());
        when(movieRepository.save(any(Movie.class))).thenReturn(movieMock());
        when(movieFactory.setResponse(any(), any(), any())).thenReturn(baseResponseMock());

        assertThrows(DataNotFoundException.class, () -> movieService.update(movieRequestMock(), 1));
    }

    @Test
    void testUpdateMovie_expectMovieTitleAlreadyExist() {
        when(movieFactory.requestToEntity(movieRequestMock())).thenReturn(movieMock());
        when(movieRepository.findById(1)).thenReturn(Optional.of(movieMock()));
        when(movieRepository.findByTitle(anyString())).thenReturn(movieMock2());

        doThrow(DataAlreadyExistException.class).when(movieRepository).save(any(Movie.class));
        assertThrows(DataAlreadyExistException.class, () -> movieService.update(movieRequestMock(), 1));

    }

    @Test
    void testDeleteMovie_expectSuccess() {
        when(movieRepository.findById(1)).thenReturn(Optional.of(movieMock()));
        when(movieFactory.setResponse(any(), any())).thenReturn(
                BaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase()).build());

        BaseResponse response = movieService.delete(1);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(HttpStatus.OK.getReasonPhrase(), response.getMessage());
    }

    @Test
    void testDeleteMovie_expectMovieNotFound() {
        when(movieRepository.findById(1)).thenReturn(Optional.empty());
        when(movieFactory.setResponse(any(), any())).thenReturn(
                BaseResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase()).build());

        assertThrows(DataNotFoundException.class, () -> movieService.delete( 1));

    }
}
