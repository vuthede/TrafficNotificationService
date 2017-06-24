package edu.bku.cse.traffic.trafficnotification.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.sun.jersey.core.spi.component.ComponentScope;

import edu.bku.cse.traffic.trafficnotification.model.SegmentInfo;
import edu.bku.cse.traffic.trafficnotion.utils.Constant;
import edu.bku.cse.traffic.trafficnotion.utils.MyMath;
import edu.bku.cse.traffic.trafficnotion.utils.MyPredicate;

public class NearPlace {
	
	
	private long device_id;
	
	//Integer is state: {0,1,2} = {'passive','active', 'ready'}
	private HashMap<Long, Constant.STATE> segment_state;
	
	
	
	public NearPlace(long device_id){
		this.device_id = device_id;
		segment_state = new HashMap<Long,  Constant.STATE>();
	}
	
	public long getDevice_id() {
		return device_id;
	}
	
	public HashMap<Long,  Constant.STATE> getData() {
		return segment_state;
	}
	
	public void changeState(Long key, Constant.STATE newstate){
		segment_state.remove(key);
		segment_state.put(key, newstate);
	}
	
	public void removeSegment(Long segmentId){
		segment_state.remove(segmentId);
	}

	
	public void updateSegmentState(double curLat, double curLng, ArrayList<SegmentInfo> segInfos){
		
		/*
		 * Update state of segments and also remove some segments are far away from traveller 's position.
		 */
		
		HashMap<Long, Constant.STATE> segment_state_temp = new HashMap<Long, Constant.STATE>();
		for (SegmentInfo seg: segInfos){
			if (!this.segment_state.containsKey(seg.getId()))
				segment_state_temp.put((Long)seg.getId(), Constant.STATE.ACTIVE);
			else{
				if (this.segment_state.get(seg.getId()) == Constant.STATE.READY)
					segment_state_temp.put((Long)seg.getId(), Constant.STATE.PASSIVE);
			}
		}
		
		this.segment_state = segment_state_temp;
	
		
		
		/*
		 * Indicate segments that need to notify to people. 
		 */
		
		Set<Long> segIds = this.segment_state.keySet();
		for (Long segId: segIds){
			if (this.segment_state.get(segId) == Constant.STATE.ACTIVE){
				//Inside r
				SegmentInfo segInfo = MyPredicate.filterBySegmentInfoId(segInfos, segId).get(0);
				double LatOfmidPoint = (segInfo.getStart().getLat() + segInfo.getEnd().getLat())/2.0;
				double LngOfmidPoint = (segInfo.getStart().getLon() + segInfo.getEnd().getLon())/2.0;
				if (MyMath.distance(curLat, LatOfmidPoint, curLng, LngOfmidPoint, 0.0, 0.0) < Constant.r);
					changeState(segId, Constant.STATE.READY);
				
				
				//Indicate specific any dimension2
					
			}
		}
		
					
	}
	
	public ArrayList<SegmentInfo> getAllSegmentInfoByState(ArrayList<SegmentInfo> segInfos, Constant.STATE state){
		ArrayList<SegmentInfo> filteredSegInfos = new ArrayList<SegmentInfo>();
		for(SegmentInfo seg:segInfos){
			if (this.segment_state.get(seg.getId()) == state)
				filteredSegInfos.add(seg);
		}
		
		return filteredSegInfos;
	}
	
	
}
