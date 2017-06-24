package edu.bku.cse.traffic.trafficnotification.database;

import java.util.HashMap;

public class AllNearPlace {
	//<device_id, NearPlace>
	private static HashMap<Long, NearPlace> data = new HashMap<Long, NearPlace>();
	
	private AllNearPlace(){};
	
	public static HashMap<Long, NearPlace> getData(){
		return data;
	}
	
}
