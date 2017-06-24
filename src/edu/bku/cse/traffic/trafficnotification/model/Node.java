package edu.bku.cse.traffic.trafficnotification.model;

public class Node {
	private double lat;
	private double lon;
	private  Node(){}
	
	public Node(double lat, double lon){
		this.lat = lat;
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}
}
