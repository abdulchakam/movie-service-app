package com.abdulchakam.movieservice.repository;

import com.abdulchakam.movieservice.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findAll();
    Movie findByTitle(String title);
}
