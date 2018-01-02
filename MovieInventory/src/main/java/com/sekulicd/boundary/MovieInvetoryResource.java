package com.sekulicd.boundary;

import java.util.EnumSet;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import com.sekulicd.control.command.MovieInventoryCommandService;
import com.sekulicd.control.query.MovieInventoryQueryService;
import com.sekulicd.entity.model.Movie;
import com.sekulicd.entity.model.MovieType;
import com.sekulicd.util.StringUtility;

@Path("inventory")
public class MovieInvetoryResource {
	
	@Inject
	MovieInventoryCommandService movieInventoryCommandService;
	
	@Inject
	MovieInventoryQueryService movieQueryCommandService;

    @Context
    UriInfo uriInfo;

	@GET
	@Path("movies")
	public String getMovie() {
   		return movieQueryCommandService.getAll();
	}
	
	@POST
	@Path("add")
	public JsonObject addMovie(JsonObject order) {
		final String movieTitle = order.getString("title", null);
		final String movieType = order.getString("movie_type", null);
		
		if (StringUtility.isStringEmptyOrNull(movieTitle)  || StringUtility.isStringEmptyOrNull(movieType)){
			return Json.createObjectBuilder().add("status", "movieTitle, movieType can't be null").build();
		}
		
		if(!MovieType.enumContains(movieType)){
            return Json.createObjectBuilder().add("status", "provided movie type not correct, choose from following list:" + EnumSet.allOf(MovieType.class).toString()).build();
		}
		
		if (movieQueryCommandService.movieExist(movieTitle)){
			return Json.createObjectBuilder().add("status", movieTitle + " already in inventory").build();
		}
		
		movieInventoryCommandService.addMovieToInventory(new Movie(movieTitle, MovieType.valueOf(movieType)));
		return Json.createObjectBuilder().add("status", "ok").build();
	}

}
