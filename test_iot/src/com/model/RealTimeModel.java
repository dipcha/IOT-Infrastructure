/**
 * 
 */
package com.model;

/**
 * @author dipti
 *
 */
public class RealTimeModel {

	private static String devID;
	private static String temperature;
	private static String date;
	private static String deviceType; 
	
	
	
	public static String getDeviceType() {
		return deviceType;
	}
	public static void setDeviceType(String deviceType) {
		RealTimeModel.deviceType = deviceType;
	}

	public static String getDevID() {
		return devID;
	}
	public static void setDevID(String devID) {
		RealTimeModel.devID = devID;
	}
	
	public static String getTemperature() {
		return temperature;
	}
	public static void setTemperature(String temperature) {
		RealTimeModel.temperature = temperature;
	}
	
	public static String getDate() {
		return date;
	}
	public static void setDate(String date) {
		RealTimeModel.date = date;
	}
	
	
}
