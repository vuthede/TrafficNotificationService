package edu.bku.cse.traffic.trafficnotification.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("updateInterestingPlace")
public class UpdateInterestingPlace {
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	public String update(String interestingPlace_Json){
		// Access to MongoDB and change the interesting place of a person.
		
		return "success";
	}
}
