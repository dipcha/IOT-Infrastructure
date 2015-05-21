/**
 * 
 */
package com.model;

/**
 * @author dipti
 *
 */
public class CoffeeMakerModel {
	private String waterLevel;
	private String waterTemp;
	private String powderLevel;
	private String poweredState; // ON/OFF
//	private String powerConsumption;
	
	public String getWaterLevel() {
		return waterLevel;
	}
	
	public void setWaterLevel(String waterLevel) {
		this.waterLevel = waterLevel;
	}
	
	public String getWaterTemp() {
		return waterTemp;
	}
	
	public void setWaterTemp(String waterTemp) {
		this.waterTemp = waterTemp;
	}
	
	public String getPowderLevel() {
		return powderLevel;
	}
	
	public void setPowderLevel(String powderLevel) {
		this.powderLevel = powderLevel;
	}
	
	public String getPoweredState() {
		return poweredState;
	}
	
	public void setPoweredState(String poweredState) {
		this.poweredState = poweredState;
	}
}
