package com.vuktales.ott.movieponds.query.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vuktales.ott.movieponds.dto.MoviePondEvent;
import com.vuktales.ott.movieponds.dto.MoviePondEventGet;
import com.vuktales.ott.movieponds.entity.MoviePond;
import com.vuktales.ott.movieponds.repository.MoviePondRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = false)
public class MoviePondQueryService {

    @Autowired
    private MoviePondRepository movieprepo;

    @Autowired
    private KafkaTemplate<String, Object> kafkaMoviePondTemplate;


    @KafkaListener(topics="movie-record-event-topic", groupId="movie-record-group")
    public void executeQueryFromTopic(ConsumerRecord<String, String> record, String jsonValue){
        ObjectMapper mapper = new ObjectMapper();
        MoviePondEvent oviePondObj =null;
        try{
            oviePondObj = mapper.readValue(jsonValue, MoviePondEvent.class);
        }
        catch(Exception e){
            e.printStackTrace();
        }

            if(record.key().equalsIgnoreCase("createMovieRecord")){
                createMovieRecord(oviePondObj.getMoviePond());
            }
            else if(record.key().equalsIgnoreCase("updateMovieRecord")){
                updateMovieRecord(oviePondObj.getCustomerId(),oviePondObj.getMoviePond());
            }
            else if(record.key().equalsIgnoreCase("getMovieRecord")){
                getMovieRecord(oviePondObj.getCustomerId());
            }
    }

    @KafkaListener(topics="movie-event-user-notification", groupId="movie-record-group")
    public void usernotificationtopic(ConsumerRecord<String, String> record, String jsonValue){

    }
    public MoviePond createMovieRecord(MoviePond moviePondObj){
        return movieprepo.save(moviePondObj);
    }


    public MoviePond updateMovieRecord(long id, MoviePond moviePondObj){
        MoviePond mpExistingObj=null;
        mpExistingObj = movieprepo.getReferenceById(id);

        mpExistingObj.setId(id);
        mpExistingObj.setName(moviePondObj.getName());
        mpExistingObj.setCategory(moviePondObj.getCategory());
        mpExistingObj.setGenere(moviePondObj.getGenere());
        mpExistingObj.setLanguage(moviePondObj.getLanguage());
        mpExistingObj.setReviews(moviePondObj.getReviews());
        mpExistingObj.setImdbRating(moviePondObj.getImdbRating());
        mpExistingObj.setOttSource(moviePondObj.getOttSource());
        mpExistingObj.setSubtitle_language(moviePondObj.getSubtitle_language());
        mpExistingObj.setSuitableAudience(moviePondObj.getSuitableAudience());
         return movieprepo.save(moviePondObj);
    }

    public void getMovieRecord(long userid){
        MoviePondEventGet mpv = new MoviePondEventGet();
        mpv.setCustomerId(userid);
        mpv.setEventType("getMovieRecord");
        mpv.setMoviePond(movieprepo.findAll() );
        kafkaMoviePondTemplate.send("movie-event-user-notification",mpv.getEventType(),mpv);

    }
}
