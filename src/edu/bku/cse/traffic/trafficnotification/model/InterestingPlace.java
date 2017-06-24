package edu.bku.cse.traffic.trafficnotification.model;

public class InterestingPlace {
	public int index;
	public double lat;
	public double lng;
	public String place;
	
	public InterestingPlace(int index, double lat, double lng, String place){
		this.index = index;
		this.lat = lat;
		this.lng = lng;
		this.place = place;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
}
