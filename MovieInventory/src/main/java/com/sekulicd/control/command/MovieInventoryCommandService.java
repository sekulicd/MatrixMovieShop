package com.sekulicd.control.command;

import javax.inject.Inject;

import com.sekulicd.control.datastore.MovieInventoryDB;
import com.sekulicd.entity.events.MovieAddToInventory;
import com.sekulicd.entity.events.MovieInInventory;
import com.sekulicd.entity.events.MovieNotInInventory;
import com.sekulicd.entity.model.Movie;
import com.sekulicd.entity.model.MovieOrderInfo;
import com.sekulicd.entity.model.MovieType;
import com.sekulicd.kafka.boundary.EventProducer;

public class MovieInventoryCommandService {

    @Inject
    EventProducer eventProducer;

    @Inject
    MovieInventoryDB movieInventory;

    public void addMovieToInventory(Movie movie) {
        eventProducer.publish(new MovieAddToInventory(movie));
    }
    
    public void checkIsMovieInInventory(MovieOrderInfo movieOrder) {
        if (movieInventory.getMovie(movieOrder.getMovie().getTitle()).isMovieInInventory())
            eventProducer.publish(new MovieInInventory(movieOrder.getOrderId(), movieInventory.getMovie(movieOrder.getMovie().getTitle()).getMovieType()));
        else
            eventProducer.publish(new MovieNotInInventory(movieOrder.getOrderId()));
    }
    
    public void takeFromInventory(Movie movie){
    	movieInventory.getMovie(movie.getTitle()).setMovieInInventory(false);
    }
    
    public void returnToInventory(String title){
    	movieInventory.getMovie(title).setMovieInInventory(true);
    }

}
