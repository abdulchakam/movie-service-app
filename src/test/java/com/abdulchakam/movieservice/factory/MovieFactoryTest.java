package com.abdulchakam.movieservice.factory;

import com.abdulchakam.movieservice.dto.BaseResponse;
import com.abdulchakam.movieservice.dto.MovieRequest;
import com.abdulchakam.movieservice.dto.MovieResponse;
import com.abdulchakam.movieservice.exception.DataNotFoundException;
import com.abdulchakam.movieservice.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovieFactoryTest {

    @InjectMocks
    private MovieFactory movieFactory;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRequestToEntity_expectSuccess() {
        MovieRequest request = new MovieRequest();
        request.setTitle("Movie Title");
        request.setDescription("Description 1");
        request.setRanting(8.8f);
        request.setImage("image");

        Movie movie = movieFactory.requestToEntity(request);

        assertNotNull(movie);
        assertEquals("Movie Title", movie.getTitle());
        assertEquals("Description 1", movie.getDescription());
        assertEquals(8.8f, movie.getRanting());
        assertEquals("image", movie.getImage());
    }

    @Test
    void testRequestToEntity_expectDataNotFoundException() {
        assertThrows(DataNotFoundException.class, () -> movieFactory.requestToEntity(null));
    }

    @Test
    void testEntityToDto_expectSuccess() {
        Movie movie = new Movie();
        movie.setId(1);
        movie.setTitle("Movie 1");
        movie.setDescription("Description 1");
        movie.setRanting(8.8f);
        movie.setImage("image");
        movie.setCreatedAt(new Date());
        movie.setUpdatedAt(new Date());

        MovieResponse response = movieFactory.entityToDto(movie);

        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("Movie 1", response.getTitle());
        assertEquals("Description 1", response.getDescription());
        assertEquals(8.8f, response.getRanting());
        assertEquals("image", response.getImagePath());
        assertEquals(convertDateToLocalDateTime(movie.getCreatedAt()), response.getCreatedAt());
        assertEquals(convertDateToLocalDateTime(movie.getUpdatedAt()), response.getUpdatedAt());
    }

    @Test
    void testEntityToDtoList_expectSuccess() {
        List<Movie> movieList = new ArrayList<>();
        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setTitle("Movie 1");
        movieList.add(movie1);
        Movie movie2 = new Movie();
        movie2.setId(2);
        movie2.setTitle("Movie 2");
        movieList.add(movie2);

        List<MovieResponse> responseList = movieFactory.entityToDto(movieList);

        assertNotNull(responseList);
        assertEquals(2, responseList.size());
        assertEquals(1, responseList.get(0).getId());
        assertEquals("Movie 1", responseList.get(0).getTitle());
        assertEquals(2, responseList.get(1).getId());
        assertEquals("Movie 2", responseList.get(1).getTitle());
    }

    @Test
    void testSetResponse_expectSuccess() {
        BaseResponse response1 = movieFactory.setResponse(200, "Success", "Content");
        assertNotNull(response1);
        assertEquals(200, response1.getStatus());
        assertEquals("Success", response1.getMessage());
        assertEquals("Content", response1.getContent());

        BaseResponse response2 = movieFactory.setResponse(200, "Success", "Content1", "Content2");
        assertNotNull(response2);
        assertEquals(200, response2.getStatus());
        assertEquals("Success", response2.getMessage());
        assertArrayEquals(new Object[]{"Content1", "Content2"}, (Object[]) response2.getContent());
    }

    private String convertDateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).format(formatter);
    }
}
