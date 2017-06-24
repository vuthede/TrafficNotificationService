package edu.bku.cse.traffic.trafficnotification.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("notifyTrafficStatusForInterestingPlace")
public class NotifyTrafficStatusForInterestPlace {
	
	@GET
	@Consumes({ MediaType.APPLICATION_JSON })
	public String notify(@QueryParam("device_id") String device_id){
		// Find all places that there is traffic jam...
		
		return null;
	}
	

}
