package com.sekulicd.boundary;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import com.sekulicd.control.query.BonusClubQueryService;

@Path("bonusclub")
public class BonusClubResource {
	
	@Inject
	BonusClubQueryService bonusClubQueryService;

    @Context
    UriInfo uriInfo;

	@GET
	@Path("customer/{id}")
	public JsonObject getCustomerBonus(@PathParam("id") String customerName) {
		if(bonusClubQueryService.getCustomer(customerName) == null){
			return Json.createObjectBuilder().add("status", "provided customer doesnt exit").build();
		}
		return Json.createObjectBuilder().add("bonus", customerName + ":" + bonusClubQueryService.getCustomerBonus(customerName)).build();
	}
	
}
