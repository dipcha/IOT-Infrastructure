package com.model;

public class ToasterModel {
	private boolean isEmpty;
	private String poweredState;
	private String timer;
	private String heatTeamperature;
	
	@Override
	public String toString() {
		return "isEmpty=" + isEmpty + ", poweredState=" + poweredState + ", timer=" + timer + ", heatTeamperature=" + heatTeamperature +" ";
	}
	
	public boolean isEmpty() {
		return isEmpty;
	}
	
	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}
	
	public String getPoweredState() {
		return poweredState;
	}
	
	public void setPoweredState(String poweredState) {
		this.poweredState = poweredState;
	}
	
	public String getTimer() {
		return timer;
	}
	
	public void setTimer(String timer) {
		this.timer = timer;
	}
	
	public String getHeatTeamperature() {
		return heatTeamperature;
	}
	
	public void setHeatTeamperature(String heatTeamperature) {
		this.heatTeamperature = heatTeamperature;
	}
	
}
