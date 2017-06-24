package edu.bku.cse.traffic.trafficnotification.model;

import java.util.ArrayList;

public class SegmentInfo {
	private long id;
	private String date = "";
	private int frame ;
	private Node start;
	private Node end;
	private double velocity;
	private double density;
	private double confidence;
	private String nameStreets;
	
	private SegmentInfo() {}
	public SegmentInfo(long id, String date, int frame, Node start, Node end, 
					   double velocity, double density, double confidence, String nameStreets){
		this.id = id;
		this.date = date;
		this.frame = frame;
		this.start = start;
		this.end = end;
		this.velocity = velocity;
		this.density = density;
		this.confidence = confidence;
		this.nameStreets = nameStreets;
	}
	
	public SegmentInfo(long id){
		this.id = id;
	}

	public String getNameStreets() {
		return nameStreets;
	}

	public long getId() {
		return id;
	}
	public String getDate() {
		return date;
	}
	
	public int getFrame(){
		return frame;
	}
	
	public double getVelocity() {
		return velocity;
	}
	public double getDensity() {
		return density;
	}
	public double getConfidence() {
		return confidence;
	}

	public Node getStart() {
		return start;
	}

	public Node getEnd() {
		return end;
	}

	
	
}
