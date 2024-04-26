package com.vuktales.ott.movieponds.dto;

import com.vuktales.ott.movieponds.entity.MoviePond;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoviePondEventGet {
    private String eventType;
    private List<MoviePond> moviePond;
    private long customerId;
}
