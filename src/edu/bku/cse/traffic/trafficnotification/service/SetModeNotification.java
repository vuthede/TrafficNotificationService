package edu.bku.cse.traffic.trafficnotification.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/setModeNotification")
public class SetModeNotification {

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	public String setMode(String typemode, String status) {
		try {
			if (typemode.equals("interestingPlaceNotification") && status.equals("on")) {
				// Todo
			} else if (typemode.equals("interestingPlaceNotification") && status.equals("off")) {
				// Todo
			} else if (typemode.equals("radiusNotification") && status.equals("on")) {
				// Todo
			} else if (typemode.equals("radiusNotification") && status.equals("off")) {
				// Todo
			} else if (typemode.equals("habbitNotification") && status.equals("on")) {
				// Todo
			} else if (typemode.equals("habbitNotification") && status.equals("off")) {
				// Todo
			}

			return "success";

		} catch (Exception e) {
			System.out.println("error on set mode interesting place");
			return "error";
		}
	}

}
