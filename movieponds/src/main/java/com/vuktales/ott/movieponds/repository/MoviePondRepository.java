package com.vuktales.ott.movieponds.repository;

import com.vuktales.ott.movieponds.entity.MoviePond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MoviePondRepository extends JpaRepository<MoviePond, Long > {

}
