package com.sekulicd.control.query;

import javax.inject.Inject;

import com.sekulicd.control.datastore.MovieInventoryDB;

public class MovieInventoryQueryService {

	@Inject
	MovieInventoryDB movieInventory;

	public String getAll() {
		return movieInventory.getAll();
	}
	
	public boolean movieExist(String title){
		if(movieInventory.getMovie(title) == null){
			return false;
		}
		return true;
	}

}
