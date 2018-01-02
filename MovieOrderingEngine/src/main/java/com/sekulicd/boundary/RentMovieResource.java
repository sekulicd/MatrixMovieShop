package com.sekulicd.boundary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.sekulicd.control.command.MovieOrderCommandService;
import com.sekulicd.control.query.MovieOrderQueryService;
import com.sekulicd.entity.model.Movie;
import com.sekulicd.entity.model.MovieOrderInfo;
import com.sekulicd.entity.model.MovieOrderState;
import com.sekulicd.util.StringUtility;

@Path("ordering")
public class RentMovieResource {
	
	private final String dateFormat = "yyyy-MM-dd";

	@Inject
	MovieOrderCommandService movieOrderCommandService;

	@Inject
	MovieOrderQueryService movieOrderQueryService;

	@POST
	@Path("rent")
	public JsonObject rentMovie(JsonObject order) {
		final String movieTitle = order.getString("title", null);
		final String rentPeriod = order.getString("rent_period", null);
		final String customerName = order.getString("customer_name", null);

		
		if (StringUtility.isStringEmptyOrNull(movieTitle) || StringUtility.isStringEmptyOrNull(rentPeriod) || StringUtility.isStringEmptyOrNull(customerName)) {
			return Json.createObjectBuilder().add("status", "movieTitle, rentPrice, customer name can't be null").build();
		}

		final UUID orderId = UUID.randomUUID();
		final LocalDate rentDate = LocalDate.now();
		MovieOrderInfo movieOrder = new MovieOrderInfo(orderId.toString(), customerName, Integer.parseInt(rentPeriod),
				rentDate, new Movie(movieTitle), MovieOrderState.RENT_REQ_PLACED);

		movieOrderCommandService.placeOrder(movieOrder);

		//final URI uri = uriInfo.getRequestUriBuilder().path(RentMovieResource.class, "getOrder").build(orderId);
		return Json.createObjectBuilder().add("status", "chekck order status by calling api getOrder where order_id = " + orderId).build();
	}
	
	@POST
	@Path("return")
	public JsonObject returnMovie(JsonObject order) {
		final String orderId = order.getString("order_id", null);
		final String returnDateStr = order.getString("return_date", null);
				
		if (StringUtility.isStringEmptyOrNull(orderId) || StringUtility.isStringEmptyOrNull(returnDateStr)) {
			return Json.createObjectBuilder().add("status", "orderId, return date can't be null").build();
		}
		
		if(!isValidFormat(dateFormat, returnDateStr)){
			return Json.createObjectBuilder().add("status", "orderId, date format must be yyyy-MM-dd").build();
		}
		
		if(movieOrderQueryService.getOrder(orderId) == null){
			return Json.createObjectBuilder().add("status", "provided orderid doesnt exit").build();
		}
		
		final LocalDate returnDate = LocalDate.parse(returnDateStr, DateTimeFormatter.ofPattern(dateFormat));
		
		MovieOrderInfo movieOrderInfo = movieOrderQueryService.getOrder(orderId);
    	int daysBetween = (int)ChronoUnit.DAYS.between(movieOrderInfo.getRentDate(), returnDate);

		if(daysBetween > movieOrderInfo.getRentPeriod()){
			movieOrderCommandService.placeReturnRequest(movieOrderInfo.getOrderId(), movieOrderInfo.getMovie().getMovieType(), daysBetween);
			return Json.createObjectBuilder().add("status", "Return date exceeds rent period, please pay return charge").build();
		}
		movieOrderCommandService.closeOrder(movieOrderInfo.getOrderId());
		return Json.createObjectBuilder().add("status", "Order should be closed, check order status by calling API getOrder provided by MovieOrderingEngine").build();		
	}

	@GET
	@Path("{id}")
	public JsonObject getOrder(@PathParam("id") String orderId) {
		final MovieOrderInfo order = movieOrderQueryService.getOrder(orderId);

		if (order == null)
			return Json.createObjectBuilder().add("status", "provided orderid doesnt exit").build();

		return Json.createObjectBuilder().add("status", order.getMovieOrderStatus().name().toLowerCase()).build();
	}
	
	public static boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }

}
