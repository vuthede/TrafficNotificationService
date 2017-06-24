package edu.bku.cse.traffic.trafficnotion.RESTDbConnection;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import edu.bku.cse.traffic.trafficnotion.utils.Constant;

public class MongoDB {
	
	public static DB db;
	public static Boolean check =false;
	
	public static void openConnection(){
		MongoClient mongoClient;
		try {
			mongoClient = new MongoClient( Constant.IP_TRAFFIC_SERVER , 27017 );
			db = mongoClient.getDB(Constant.NAME_TRAFFIC_DATABASE); //ten database
			check =true;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
