package com.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class DeviceModel {
	
	
	/// thermostat ///
	private String temperature;
	private int devID;
	private String devicetype;
	private String date;
	
	
	public DeviceModel() {
		super();
	}
	
	public DeviceModel(int id, String temperature, String date) {
		super();
		this.devID = id;
		this.temperature = temperature;
		this.date = date;
	}

	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDevicetype() {
		return devicetype;
	}
	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}

	public String toString() {
		return "temperature=" + temperature + ", devID=" + devID + ", devicetype=" + devicetype + ", date=" + date +" ";
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public int getDevID() {
		return devID;
	}
	public void setDevID(int devID) {
		this.devID = devID;
	}
	/// thermostat ///
	
	///////Alarm clock model methods and variables///////
	private String time;
	//private String State; //ON/OFF
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
//	public String getState() {
//		return State;
//	}
//	
//	public void setState(String state) {
//		this.State = state;
//	}
	//////Alarm clock model methods and variables///////
	
	
	
	///////CoffeeMaker model methods and variables///////
	private String waterLevel;
	//private String waterTemp;
	private String powderLevel;
	private String powerState; // ON/OFF
	private String changeFilter;
	
//	private String powerConsumption;
	
	public String getWaterLevel() {
		return waterLevel;
	}
	
	public void setWaterLevel(String waterLevel) {
		this.waterLevel = waterLevel;
	}
	
//	public String getWaterTemp() {
//		return waterTemp;
//	}
//	
//	public void setWaterTemp(String waterTemp) {
//		this.waterTemp = waterTemp;
//	}
	
	public String getPowderLevel() {
		return powderLevel;
	}
	
	public void setPowderLevel(String powderLevel) {
		this.powderLevel = powderLevel;
	}
	
	public String getPowerState() {
		return powerState;
	}
	
	public void setPowerState(String powerState) {
		this.powerState = powerState;
	}
	
	public String getchangeFilter() {
		return changeFilter;
	}
	
	public void setchangeFilter(String changeFilter) {
		this.changeFilter = changeFilter;
	}
	///////CoffeeMaker model methods and variables///////
	
	///////Toaster model methods and variables///////
	private boolean isEmpty;
	//private String poweredState;
	private String timer;
	//private String toasterTemperature;
	
	public boolean getIsEmpty() {
		return isEmpty;
	}
	
	public void setIsEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}
	
//	public String getPoweredState() {
//		return poweredState;
//	}
//	
//	public void setPoweredState(String poweredState) {
//		this.poweredState = poweredState;
//	}
	
	public String getTimer() {
		return timer;
	}
	
	public void setTimer(String timer) {
		this.timer = timer;
	}
	
/*	public String gettoasterTemperature() {
		return toasterTemperature;
	}
	
	public void settoasterTemperature(String toasterTemperature) {
		this.toasterTemperature = toasterTemperature;
	}
*/	
	///////Toaster model methods and variables///////
	
	
}
	
	
