package com.vuktales.ott.movieponds.command.controller;

import com.vuktales.ott.movieponds.command.service.MoviePondCommandService;
import com.vuktales.ott.movieponds.dto.MoviePondEvent;
import com.vuktales.ott.movieponds.entity.MoviePond;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movieponds")
public class MovieCommandController {

    @Autowired
    private MoviePondCommandService commandService;

    public static final String MOVIE_SERVICE="movieService";

    @PostMapping
    @CircuitBreaker(name=MOVIE_SERVICE, fallbackMethod= "getAvailableMovies")
    public ResponseEntity<MoviePond> createMovieRecord(@RequestBody MoviePondEvent moviePEvent){
        commandService.insertMovieRecordToQueue(moviePEvent);
        return new ResponseEntity("Recorded created", HttpStatus.CREATED);
    }


    public ResponseEntity<MoviePond> getAvailableMovies(){

        return new ResponseEntity("Recorded created", HttpStatus.BAD_GATEWAY);
    }
    @PutMapping("/{id}")
    public ResponseEntity<MoviePond> updateMovieRecord(@PathVariable(value = "id") Long id,  @RequestBody MoviePondEvent moviePEvent)
    {
        commandService.updateMovieRecordToQueue(id, moviePEvent);
        return new ResponseEntity("Recorded created", HttpStatus.CREATED);
    }

    @GetMapping
    @Retry(name= MOVIE_SERVICE, fallbackMethod = "getAvailableMovies" )
    public ResponseEntity<MoviePond[]> getMovieRecord(@RequestHeader("user-id") long userid){
        commandService.getMoviesRequestToQueue(userid);
        return new ResponseEntity("Recorded created", HttpStatus.OK);
    }

    @GetMapping("/getNotification")
    public ResponseEntity<MoviePond[]> getUserNotification(@RequestHeader("webSite") long userid){
          commandService.getMoviesRequestToQueue(userid);
        return new ResponseEntity("Recorded created", HttpStatus.OK);
    }

}
