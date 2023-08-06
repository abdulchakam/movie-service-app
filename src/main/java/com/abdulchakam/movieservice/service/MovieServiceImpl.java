package com.abdulchakam.movieservice.service;

import com.abdulchakam.movieservice.constant.ErrorMessage;
import com.abdulchakam.movieservice.dto.BaseResponse;
import com.abdulchakam.movieservice.dto.MovieRequest;
import com.abdulchakam.movieservice.dto.MovieResponse;
import com.abdulchakam.movieservice.exception.DataAlreadyExist;
import com.abdulchakam.movieservice.exception.DataNotFoundException;
import com.abdulchakam.movieservice.exception.InternalServerException;
import com.abdulchakam.movieservice.factory.MovieFactory;
import com.abdulchakam.movieservice.model.Movie;
import com.abdulchakam.movieservice.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ServerErrorException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieFactory movieFactory;

    @Autowired
    private MovieRepository movieRepository;

    // Create new Movie
    @Override
    public BaseResponse create(MovieRequest request) {
        log.info("Start create new movie");
        Movie movie;
        HttpStatus httpStatus;

        try {
            validateMovieTitle(request.getTitle(), null);
            movie = movieFactory.requestToEntity(request);
            movie.setCreatedAt(new Date());

            movieRepository.save(movie);
            httpStatus = HttpStatus.CREATED;
            log.info("Success create new movie");

        } catch (ServerErrorException e) {
            log.error("Error when create new movie : {}", e.getMessage());
            throw new InternalServerException(e.getMessage());
        }

        return movieFactory.setResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                movie);
    }

    // Show ALl Movie List from Database
    @Override
    public BaseResponse showAll() {
        log.error("Start get all movie");
        HttpStatus httpStatus;
        List<MovieResponse> movieResponseList = new ArrayList<>();

        try {
            List<Movie> movies = movieRepository.findAll();
            movieResponseList = movieFactory.entityToDto(movies);

            httpStatus = HttpStatus.OK;
            log.info("Success get all movie");

        } catch (ServerErrorException e) {
            log.error("Error when get all movie");
            throw new InternalServerException(e.getMessage());
        }

        return movieFactory.setResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                movieResponseList);
    }

    // Show Detail Movie By id
    @Override
    public BaseResponse show(Integer id) {
        log.error("Start get movie by id");
        HttpStatus httpStatus;
        MovieResponse movieResponse = new MovieResponse();

        try {
            Optional<Movie> movieOptional = getMovieById(id);

            if (movieOptional.isPresent()) {
                movieResponse = movieFactory.entityToDto(movieOptional.get());
            }

            httpStatus = HttpStatus.OK;
            log.info("Success get movie by id");

        } catch (ServerErrorException e) {
            log.error("Error when get movie by id");
            throw new InternalServerException(e.getMessage());
        }

        return movieFactory.setResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                movieResponse);

    }

    // Update Movie in the database
    @Override
    public BaseResponse update(MovieRequest request, Integer id) {
        log.info("Start update movie");
        Movie movie;
        HttpStatus httpStatus;

        try {
            validateMovieTitle(request.getTitle(), id);
            Optional<Movie> movieOptional = getMovieById(id);

            movie = movieFactory.requestToEntity(request);
            movie.setId(id);
            movie.setCreatedAt(movieOptional.get().getCreatedAt());
            movie.setUpdatedAt(new Date());

            movieRepository.save(movie);
            httpStatus = HttpStatus.OK;
            log.info("Success update movie");

        } catch (ServerErrorException e) {
            log.error("Error when update movie : {}", e.getMessage());
            throw new InternalServerException(e.getMessage());
        }

        return movieFactory.setResponse(
                httpStatus.value(),
                "Success update data",
                movie);
    }

    // Delete Movie by id
    @Override
    public BaseResponse delete(Integer id) {
        log.error("Start delete movie by id");
        String successMessage = "";
        HttpStatus httpStatus;

        try {
            getMovieById(id);
            movieRepository.deleteById(id);

            httpStatus = HttpStatus.OK;
            successMessage = "Success deleted movie";
            log.info("Success delete movie by id");

        } catch (ServerErrorException e) {
            log.error("Error when delete movie by id");
            throw new InternalServerException(e.getMessage());

        }

        return movieFactory.setResponse(
                httpStatus.value(),
                successMessage);
    }

    // Validation movie title
    private void validateMovieTitle(String title, Integer id) {
        Movie movieExist = movieRepository.findByTitle(title);
        if (!ObjectUtils.isEmpty(movieExist) && (id == null || !id.equals(movieExist.getId()))) {
            throw new DataAlreadyExist(ErrorMessage.MOVIE_TITLE_ALREADY_EXIST_MESSAGE);
        }
    }

    // Get Movie by id
    private Optional<Movie> getMovieById(Integer id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isEmpty()) {
            throw new DataNotFoundException(ErrorMessage.MOVIE_NOT_FOUND_MESSAGE);
        }
        return movie;
    }
}
