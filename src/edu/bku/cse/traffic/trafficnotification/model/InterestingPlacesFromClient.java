package edu.bku.cse.traffic.trafficnotification.model;

import java.util.List;

public class InterestingPlacesFromClient {
	public String device_id;
	public List<InterestingPlace> places;
	
	public InterestingPlacesFromClient(String device_id, List<InterestingPlace> places){
		this.device_id = device_id;
		this.places = places;
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public List<InterestingPlace> getPlaces() {
		return places;
	}

	public void setPlaces(List<InterestingPlace> places) {
		this.places = places;
	}
	
	
}
