package com.vuktales.ott.movieponds.command.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vuktales.ott.movieponds.dto.MoviePondEvent;
import com.vuktales.ott.movieponds.entity.MoviePond;
import com.vuktales.ott.movieponds.repository.MoviePondRepository;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.DataInput;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Service
public class MoviePondCommandService {

    @Autowired
    private MoviePondRepository movieprepo;

    @Autowired
    private KafkaTemplate<String,Object> kafkaMoviePondTemplate;

    public void insertMovieRecordToQueue(MoviePondEvent movieEvent){
        kafkaMoviePondTemplate.send("movie-record-event-topic",movieEvent.getEventType(),movieEvent);
    }

    public void getMoviesRequestToQueue(long id){
        MoviePondEvent movieEvent = new MoviePondEvent();
        movieEvent.setMoviePond(null);
        movieEvent.setEventType("getMovieRecord");
        movieEvent.setCustomerId(id);

        kafkaMoviePondTemplate.send("movie-record-event-topic","getMovieRecord",movieEvent);
    }

    public void updateMovieRecordToQueue(long id, MoviePondEvent movieEvent){
        movieEvent.setCustomerId(id);
        kafkaMoviePondTemplate.send("movie-record-event-topic",movieEvent.getEventType(),movieEvent );
    }
}
