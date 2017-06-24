package edu.bku.cse.traffic.trafficnotion.utils;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import edu.bku.cse.traffic.trafficnotification.model.SegmentInfo;

public class MyPredicate {
	
	public static Predicate<SegmentInfo> isEqualId(Long id){
		return p -> p.getId() == id ;
	}
	public static ArrayList<SegmentInfo> filterBySegmentInfoId(ArrayList<SegmentInfo> segInfos, Long id){
		return (ArrayList<SegmentInfo>) segInfos.stream().filter(isEqualId(id)).collect(Collectors.<SegmentInfo>toList());
	}
}
