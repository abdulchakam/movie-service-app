package com.abdulchakam.movieservice.controlller;

import com.abdulchakam.movieservice.dto.BaseResponse;
import com.abdulchakam.movieservice.dto.MovieRequest;
import com.abdulchakam.movieservice.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/Movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestBody @Valid MovieRequest movieRequest) {
        return new ResponseEntity<>(movieService.create(movieRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BaseResponse> showAll() {
        return new ResponseEntity<>(movieService.showAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> show(@PathVariable Integer id) {
        return new ResponseEntity<>(movieService.show(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BaseResponse> update(@RequestBody @Valid MovieRequest movieRequest, @PathVariable Integer id) {
        return new ResponseEntity<>(movieService.update(movieRequest, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable Integer id) {
        return new ResponseEntity<>(movieService.delete(id), HttpStatus.OK) ;
    }
}
