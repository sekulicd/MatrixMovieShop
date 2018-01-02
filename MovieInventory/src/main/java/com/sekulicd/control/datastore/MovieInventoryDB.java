package com.sekulicd.control.datastore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Observes;
import javax.json.bind.spi.JsonbProvider;

import com.sekulicd.entity.events.MovieAddToInventory;
import com.sekulicd.entity.model.Movie;

/*
 * In-memory data representation
 */
@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class MovieInventoryDB {

	private Map<String, Movie> movies = new ConcurrentHashMap<>();
	
	public void apply(@Observes MovieAddToInventory movieAddedEvent) {
		movies.putIfAbsent(movieAddedEvent.getMovie().getTitle(), movieAddedEvent.getMovie());
    }

	public Movie getMovie(String title) {
		return movies.get(title);
	}
        
    public String getAll() {
		return JsonbProvider.provider().create().build().toJson(movies);
	}
    
    ///
//    public String getAllAvailable() {
//		return JsonbProvider.provider().create().build().toJson(movies.stream());
//	}
	
	public Movie addMovie(Movie movie) {
		return movies.put(movie.getTitle(), movie);
	}

}
