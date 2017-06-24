package edu.bku.cse.traffic.trafficnotion.utils;

import java.util.ArrayList;


public final class FindUtils {
	public static int MAX_ZOOM = 19;
	public static int BIT = 24;
	
	private FindUtils(){};
	
	private static double Meter2Degree(double R, double lat, double lon){
		//TODO
		
		return R/11000.0;
	}
	
	private static double tileY(double lat, int zoom){
		return ((1-Math.log(Math.tan(Math.toRadians(lat))+1/ Math.cos(Math.toRadians(lat)))/Math.PI)/2*(1<<zoom));
	}
	
	private static double tileX(double lon, int zoom){
		return ((lon + 180)/360*(1<<zoom));
	}
	
	public static long getSingleCellId(int tileX, int tileY, int zoom){
		int size = 1<<zoom;
		
		if (tileX<0){
			tileX = 0;
		}
		if (tileX >= (size)){
			tileX = (size)-1;
		}
		if (tileY<0){
			tileY = 0;
		}
		if (tileY >= (size)){
			tileY = (size)-1;
		}
		return (long)((long)zoom<<(BIT*2))| ((long)tileX<<BIT)|(long)tileY;
	}
	
	public static ArrayList<Long> Find_CellIds_In_Boundary(double latTL, double longTL, double latTR, double longTR,
												  double latBL, double longBL, double latBR, double longBR,
												  int zoom){
		
		ArrayList<Long> cellIds = new ArrayList<Long>();
		
		//Find latmin, lat max, longmin, longmax
		double latMin, latMax, longMin, longMax;
		latMin = latTL; latMax=latTL; longMin= longTL; longMax = longTL;
				
		if (latTR < latMin) latMin = latTR;
		if (latBL < latMin) latMin = latBL;
		if (latBR < latMin) latMin = latBR;
		if (latTR > latMax) latMax = latTR;
		if (latBL > latMax) latMax = latBL;
		if (latBR > latMax) latMax = latBR;
		
		if (longTR < longMin) longMin = longTR;
		if (longBL < longMin) longMin = longBL;
		if (longBR < longMin) longMin = longBR;
		if (longTR > longMax) longMax = longTR;
		if (longBL > longMax) longMax = longBL;
		if (longBR > longMax) longMax = longBR;
		System.out.println("latmin, latmax, lonmin, lon max: " + latMin + ", " + latMax + ", " + longMin + ", " + longMax);
		int iLatMin = (int)Math.floor(tileY(latMax, zoom)), iLatMax=(int)Math.ceil(tileY(latMin, zoom)), 
				iLongMin= (int)Math.floor(tileX(longMin, zoom)), iLongMax = (int)Math.ceil(tileX(longMax, zoom));
		//int iLatMin = (int)(latMin), iLatMax=(int)(latTL), iLongMin= (int)(longTL), iLongMax = (int)(longTL);
		System.out.println("latmin, latmax, lonmin, lon max: " + iLatMin + ", " + iLatMax + ", " + iLongMin + ", " + iLongMax );
	
		for (int i = iLatMin ; i <= iLatMax; i++){
			for (int j = iLongMin; j <= iLongMax; j++){
				
				cellIds.add(getSingleCellId(j, i, zoom));
				// System.out.println("check cell id "+i+" "+j+" "+ zoom+" "+getSingleCellId(j, i, zoom));
			}
		}
		
		// System.out.println("hello hai dep trai 1234");
		
		return cellIds;
	}
	
	public static ArrayList<Long> Find_CellIds_In_Boundary(double latS, double longS, double latE, double longE, double R, int zoom){
		double midLat = (latS+latE)/2;
		double midLon = (longS+longE)/2;
		
		double latTL, longTL, latTR, longTR, latBL, longBL, latBR, longBR;
		latTL = midLat - (double)Meter2Degree(R, midLat, midLon);
		longTL = midLon - (double)Meter2Degree(R, midLat, midLon);
		latTR = latTL;
		longTR = midLon + (double)Meter2Degree(R, midLat, midLon);
		latBL = midLat + (double)Meter2Degree(R, midLat, midLon);
		longBL = longTL;
		latBR = latBL;
		longBR = longTR;
		
		return  Find_CellIds_In_Boundary(latTL, longTL, latTR, longTR, latBL, longBL, latBR, longBR, zoom);
				
	}

	public static ArrayList<Long> Find_CellIds_In_Boundary(double lat, double lon, double R, int zoom){
		double latTL, longTL, latTR, longTR, latBL, longBL, latBR, longBR;
		latTL = lat - (double)Meter2Degree(R, lat, lon);
		longTL = lon - (double)Meter2Degree(R, lat, lon);
		latTR = latTL;
		longTR = lon + (double)Meter2Degree(R, lat, lon);
		latBL = lat + (double)Meter2Degree(R, lat, lon);
		longBL = longTL;
		latBR = latBL;
		longBR = longTR;
		return  Find_CellIds_In_Boundary(latTL, longTL, latTR, longTR, latBL, longBL, latBR, longBR, zoom);
	}
	
	public static ArrayList<Long> Find_CellIds_In_Boundary(double latS, double longS, double latE, double longE, int zoom){
		double midLat = (latS+latE)/2;
		double midLon = (longS+longE)/2;
		double a1 = midLat - latS;
		double b1 = midLon - longS;
		double R = (double) Math.sqrt(Math.pow(a1,2)+Math.pow(b1,2));
		
		double latTL, longTL, latTR, longTR, latBL, longBL, latBR, longBR;
		latTL = latS - (double)Meter2Degree(R, midLat, midLon);
		longTL = longS - (double)Meter2Degree(R, midLat, midLon);
		latTR = latTL;
		longTR = longS + (double)Meter2Degree(R, midLat, midLon);
		latBL = latS + (double)Meter2Degree(R, midLat, midLon);
		longBL = longTL;
		latBR = latBL;
		longBR = longTR;
		
		return  Find_CellIds_In_Boundary(latTL, longTL, latTR, longTR, latBL, longBL, latBR, longBR, zoom);
		
		
	}
	
	
}
