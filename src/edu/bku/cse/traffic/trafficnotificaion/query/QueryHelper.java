
package edu.bku.cse.traffic.trafficnotificaion.query;


import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import edu.bku.cse.traffic.trafficnotification.model.InterestingPlace;
import edu.bku.cse.traffic.trafficnotification.model.Node;
import edu.bku.cse.traffic.trafficnotification.model.SegmentInfo;
import edu.bku.cse.traffic.trafficnotion.utils.Constant;
import edu.bku.cse.traffic.trafficnotion.utils.FindUtils;
import edu.bku.cse.traffic.trafficnotion.RESTDbConnection.*;


public class QueryHelper {
	public static ArrayList<SegmentInfo> getCongestedSegmentInfosInRetangle(double lat, double lon, double R) {
		ArrayList<SegmentInfo> segInfos = new ArrayList<SegmentInfo>();

		// Get date
		Date today = new Date();
		String ftdate = "";
		int hour, minute, frameTemp;
		hour = today.getHours();
		minute = today.getMinutes();
		frameTemp = hour * 4 + (int) (minute / 15);
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		ftdate = ft.format(today);

		// Get all cellIds belongto boundary
		ArrayList<Long> cellIds = FindUtils.Find_CellIds_In_Boundary(lat, lon, R, FindUtils.MAX_ZOOM);

		if (MongoDB.check == false)
			MongoDB.openConnection();

		DB db = MongoDB.db;
		DBCollection cellCol = db.getCollection("cell");
		DBCollection seginfoCol = db.getCollection("test_segmentinfo");

		for (int i = 0; i < cellIds.size(); i++) {
			long cellId = cellIds.get(i);
			BasicDBObject fields = new BasicDBObject();
			fields.put("cell_id", cellId);
			DBCursor cursor = cellCol.find(fields);

			if (cursor.hasNext()) {
				BasicDBObject cell = (BasicDBObject) cursor.next();
				int numSegment = cell.getInt("numSegment");
				BasicDBList segments = (BasicDBList) cell.get("segments");
				BasicDBObject[] segmentarr = segments.toArray(new BasicDBObject[0]);
				double latstart = 0, lonstart = 0, latend = 0, lonend = 0;

				for (BasicDBObject dbObj : segmentarr) {
					long idSeg = dbObj.getLong("segment_id");
					BasicDBObject fieldsSegInfo = new BasicDBObject();
					fieldsSegInfo.put("id", idSeg);
					DBCursor cursorSegInfo = seginfoCol.find(fieldsSegInfo);
					double speed = 0;
					int frame = 0;
					String street_name = "";

					while (cursorSegInfo.hasNext()) {
						BasicDBObject seginfo = (BasicDBObject) cursorSegInfo.next();
						speed = seginfo.getDouble("speed");
						frame = seginfo.getInt("frame");
						String dategps = seginfo.getString("date");
						BasicDBObject nodestart = (BasicDBObject) seginfo.get("start");
						BasicDBObject nodeend = (BasicDBObject) seginfo.get("end");
						BasicDBObject streetInfo = (BasicDBObject) seginfo.get("streetInfo");
						if(streetInfo != null)
							street_name = streetInfo.getString("street_name");
						latstart = nodestart.getDouble("lat");
						lonstart = nodestart.getDouble("lng");
						latend = nodeend.getDouble("lat");
						lonend = nodeend.getDouble("lng");
						double density = 0.0;
						double confidence = 0.0;

						// Check if segmentinfo is out-update or exist or speed
						// < 5
						if (speed > 0 && speed < Constant.CONGESTED_VELOCITY && latstart != 0 && lonstart != 0 && latend != 0 && lonend != 0
								) {

							SegmentInfo segment_info = new SegmentInfo(idSeg, dategps, frame,
									new Node(latstart, lonstart), new Node(latend, lonend), speed, density, confidence,
									street_name);
							segInfos.add(segment_info);
						}

					}

				}

			}

		}

		return segInfos;
	}

	public static ArrayList<InterestingPlace> getInterestingPlaces(long device_id) {
		ArrayList<InterestingPlace> placeList = new ArrayList<InterestingPlace>();

		if (MongoDB.check == false)
			MongoDB.openConnection();

		DB db = MongoDB.db;
		DBCollection interestPlace = db.getCollection("interesting_place");

		// Get document which has device_id in this collection.
		BasicDBObject data = new BasicDBObject();
		data.put("device_id", device_id);
		DBCursor cursorData = interestPlace.find(data);

		if (cursorData.hasNext()) {
			BasicDBObject realdata = (BasicDBObject) cursorData.next();
			BasicDBList places = (BasicDBList) realdata.get("interesting_places");
			BasicDBObject[] placearr = places.toArray(new BasicDBObject[0]);
			for (BasicDBObject dbObj : placearr) {
				int index = dbObj.getInt("index");
				double lat = dbObj.getDouble("lat");
				double lon = dbObj.getDouble("lon");
				String place = dbObj.getString("place");
				placeList.add(new InterestingPlace(index, lat, lon, place));
			}

			return placeList;
		}

		return null;
	}
	
	public static ArrayList<Long> getAllDeviceId(){
		ArrayList<Long> idList = new ArrayList<Long>();
		if (MongoDB.check == false)
			MongoDB.openConnection();

		DB db = MongoDB.db;
		DBCollection interestPlace = db.getCollection("interesting_place");
		DBCursor cursors = interestPlace.find();
		
		while (cursors.hasNext()){
			idList.add( ((BasicDBObject) cursors.next()).getLong("device_id"));
		}
		return idList;
	}
}
