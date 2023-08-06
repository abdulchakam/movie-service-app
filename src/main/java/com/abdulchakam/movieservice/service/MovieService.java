package com.abdulchakam.movieservice.service;

import com.abdulchakam.movieservice.dto.BaseResponse;
import com.abdulchakam.movieservice.dto.MovieRequest;

public interface MovieService {

    BaseResponse create(MovieRequest request);
    BaseResponse showAll();
    BaseResponse update(MovieRequest request, Integer id);
    BaseResponse show(Integer id);
    BaseResponse delete(Integer id);
}
