package edu.bku.cse.traffic.trafficnotification.service;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import edu.bku.cse.traffic.trafficnotificaion.query.QueryHelper;
import edu.bku.cse.traffic.trafficnotification.model.AllNearPlace;
import edu.bku.cse.traffic.trafficnotification.model.NearPlace;
import edu.bku.cse.traffic.trafficnotification.model.SegmentInfo;
import edu.bku.cse.traffic.trafficnotion.utils.Constant;

@Path("/notifyTrafficStatusNear")
public class NotifyTrafficStatusNear {
	public static void main(String []ảgs){
		ArrayList<SegmentInfo> segInfos = QueryHelper.getCongestedSegmentInfosInRetangle(10.7679619, 106.6531443, 10);
		//String json = new Gson().toJson(segInfos);
		System.out.println(segInfos.size());
	}
	
	@GET
	@Consumes({ MediaType.APPLICATION_JSON })
	public String notify(@QueryParam("device_id") long device_id, @QueryParam("lattitude") double lat, @QueryParam("longtitude") double lng){
		//Get all segmentInfo which are in congestion
		ArrayList<SegmentInfo> segInfos = QueryHelper.getCongestedSegmentInfosInRetangle(lat, lng, Constant.R);
		
		//If NearPlace of device_id haven't exist already. Then we create it.
		NearPlace np = AllNearPlace.getData().get(device_id);
		if(np == null){
			AllNearPlace.getData().put(device_id, new NearPlace(device_id));
		}
		
		//Update nearBy-segInfos in R of this device_id.
		np = AllNearPlace.getData().get(device_id);
		np.updateSegmentState(lat, lng, segInfos);
		
		//Get seginFos with "ready" state. there are 3 states {ready, passive, active}
		segInfos = np.getAllSegmentInfoByState(segInfos, Constant.STATE.READY);
		
		//Convert to json
		String json = new Gson().toJson(segInfos);
		return json;
	}
}
