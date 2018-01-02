package com.sekulicd.boundary;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import com.sekulicd.control.command.PricingEngineCommandService;
import com.sekulicd.control.query.PricingEngineQueryService;
import com.sekulicd.util.StringUtility;

@Path("pricing")
public class PricingEngineResource {
	
	@Inject
	PricingEngineCommandService pricingEngineCommandService;
	
	@Inject
	PricingEngineQueryService pricingEngineQueryService;

	@Context
	UriInfo uriInfo;

	@GET
	@Path("rentPrice/{id}")
	public JsonObject getRentPrice(@PathParam("id") String orderId) {
		if(pricingEngineQueryService.getOrder(orderId) == null){
			return Json.createObjectBuilder().add("status", "provided orderid doesnt exit").build();
		}
		return Json.createObjectBuilder().add("rent price",pricingEngineQueryService.getRentPriceForOrderId(orderId)).build();
	}
	
	@POST
	@Path("payRentPrice")
	public JsonObject payRentPrice(JsonObject order) {
		final String orderId = order.getString("orderId", null);
		final String rentPrice = order.getString("rent_price", null);
		
		if (StringUtility.isStringEmptyOrNull(orderId) || StringUtility.isStringEmptyOrNull(rentPrice)) {
			return Json.createObjectBuilder().add("status", "orderId, rentPrice can't be null").build();
		}
		
		if(pricingEngineQueryService.getOrder(orderId) == null){
			return Json.createObjectBuilder().add("status", "provided orderid doesnt exit").build();
		}
		
		if (!rentPrice.equals(Integer.toString(pricingEngineQueryService.getOrder(orderId).getRentPrice()))) {
			return Json.createObjectBuilder().add("status", "provided rent price not ok, call api to check rent price").build();
		}
		
		pricingEngineCommandService.payRentPrice(orderId);
		return Json.createObjectBuilder().add("status", "ok").build();
	}
	
	@GET
	@Path("returnPrice/{id}")
	public JsonObject getReturnPrice(@PathParam("id") String orderId) {
		if(pricingEngineQueryService.getOrder(orderId) == null){
			return Json.createObjectBuilder().add("status", "provided orderid doesnt exit").build();
		}
		return Json.createObjectBuilder().add("return price",pricingEngineQueryService.getReturnPriceForOrderId(orderId)).build();
	}
	
	@POST
	@Path("payReturnPrice")
	public JsonObject payReturnPrice(JsonObject order) {
		final String orderId = order.getString("orderId", null);
		final String returnPrice = order.getString("return_price", null);
		 		
		
		if (StringUtility.isStringEmptyOrNull(orderId) || StringUtility.isStringEmptyOrNull(returnPrice)) {
			return Json.createObjectBuilder().add("status", "orderId or rentPrice cant be null").build();
		}
		
		if(pricingEngineQueryService.getOrder(orderId) == null){
			return Json.createObjectBuilder().add("status", "provided orderid doesnt exit").build();
		}
		
		if (!returnPrice.equals(Integer.toString(pricingEngineQueryService.getOrder(orderId).getReturnPrice()))) {
			return Json.createObjectBuilder().add("status", "provided return price not ok, call api to check return price").build();
		}
		
		pricingEngineCommandService.payReturntPrice(orderId);
		return Json.createObjectBuilder().add("status", "ok").build();
	}

}
