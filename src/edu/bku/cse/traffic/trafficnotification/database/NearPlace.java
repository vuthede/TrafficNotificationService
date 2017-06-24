package edu.bku.cse.traffic.trafficnotification.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.sun.jersey.core.spi.component.ComponentScope;

import edu.bku.cse.traffic.trafficnotification.model.SegmentInfo;
import edu.bku.cse.traffic.trafficnotification.model.SegmentState;
import edu.bku.cse.traffic.trafficnotion.utils.Constant;
import edu.bku.cse.traffic.trafficnotion.utils.MyMath;
import edu.bku.cse.traffic.trafficnotion.utils.MyPredicate;

public class NearPlace {
	
	
	private long device_id;
	
	//Integer is state: {0,1,2} = {'passive','active', 'ready'}
	private HashMap<Long, SegmentState> segment_state;
	
	
	
	public NearPlace(long device_id){
		this.device_id = device_id;
		segment_state = new HashMap<Long,  SegmentState>();
	}
	
	public long getDevice_id() {
		return device_id;
	}
	
	public HashMap<Long,  SegmentState> getData() {
		return segment_state;
	}
	
	public void changeState(Long key, Constant.STATE newstate, double distance){
		SegmentState st = new SegmentState(newstate, segment_state.get(key).getNum(), distance);
		segment_state.remove(key);
		segment_state.put(key, st);
	}
	
	public void removeSegment(Long segmentId){
		segment_state.remove(segmentId);
	}

	
	public void updateSegmentState(double curLat, double curLng, ArrayList<SegmentInfo> segInfos){
		
		/*
		 * Update state of segments and also remove some segments are far away from traveller 's position.
		 */
		
		HashMap<Long, SegmentState> segment_state_temp = new HashMap<Long, SegmentState>();
		for (SegmentInfo seg: segInfos){
			if (!this.segment_state.containsKey(seg.getId()))
				segment_state_temp.put((Long)seg.getId(), new SegmentState(Constant.STATE.ACTIVE, 0, Double.MAX_VALUE));
			else{
				if (this.segment_state.get(seg.getId()).getState() == Constant.STATE.READY)
					segment_state_temp.put((Long)seg.getId(), new SegmentState(Constant.STATE.PASSIVE, 0, Double.MAX_VALUE));
			}
		}
		
		this.segment_state = segment_state_temp;
	
		
		
		/*
		 * Indicate segments that need to notify to people. 
		 */
		
		Set<Long> segIds = this.segment_state.keySet();
		for (Long segId: segIds){
			if (this.segment_state.get(segId).getState() == Constant.STATE.ACTIVE){
				//Inside r
				SegmentInfo segInfo = MyPredicate.filterBySegmentInfoId(segInfos, segId).get(0);
				double LatOfmidPoint = (segInfo.getStart().getLat() + segInfo.getEnd().getLat())/2.0;
				double LngOfmidPoint = (segInfo.getStart().getLon() + segInfo.getEnd().getLon())/2.0;
				double newDistance = MyMath.distance(curLat, LatOfmidPoint, curLng, LngOfmidPoint, 0.0, 0.0);
				if (newDistance < Constant.r)
					changeState(segId, Constant.STATE.READY, newDistance);
				else {
					//Indicate specific any dimension
					if(newDistance - this.segment_state.get(segId).getDistance() <= Constant.MIN_DENLTA_DISTANCE){
						this.segment_state.get(segId).setNumIncreaseBy_1();
					}
					else this.segment_state.get(segId).setNum(0);
					
					if(this.segment_state.get(segId).getNum() >= Constant.THRESHOLD)
						changeState(segId, Constant.STATE.READY, newDistance);
						
				}
				
						
			}
		}
		
					
	}
	
	public ArrayList<SegmentInfo> getAllSegmentInfoByState(ArrayList<SegmentInfo> segInfos, Constant.STATE state){
		ArrayList<SegmentInfo> filteredSegInfos = new ArrayList<SegmentInfo>();
		for(SegmentInfo seg:segInfos){
			if (this.segment_state.get(seg.getId()).getState() == state)
				filteredSegInfos.add(seg);
		}
		
		return filteredSegInfos;
	}
	
	
}
