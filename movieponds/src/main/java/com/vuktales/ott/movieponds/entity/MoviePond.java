package com.vuktales.ott.movieponds.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@Table(name="MOVIE_COMMAND")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoviePond {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String category;
    private String genere;
    private String ottSource;
    private String reviews;
    private String imdbRating ;
    private String language;
    private String suitableAudience;
    private String subtitle_status;
    private String[] subtitle_language;

}
