package edu.bku.cse.traffic.trafficnotification.model;

import edu.bku.cse.traffic.trafficnotion.utils.Constant;

public class SegmentState {
	private Constant.STATE state;
	private int num;
	private double distance;
	
	public SegmentState( Constant.STATE state, int num, double distance){
		this.state = state;
		this.num = num;
		this.distance = distance;
	}
	

	public Constant.STATE getState() {
		return state;
	}
	public void setState(Constant.STATE state) {
		this.state = state;
	}
	public int getNum() {
		return num;
	}
	public void setNumIncreaseBy_1() {
		this.num = this.num + 1;
	}

	public void setNum(int num){
		this.num = num;
	}

	public double getDistance() {
		return distance;
	}


	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	

}
