/**
 * 
 */
package com.model;

public class AlarmModel {
	private String time;
	private String poweredState; //ON/OFF
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getState() {
		return poweredState;
	}
	
	@Override
	public String toString() {
		return "time=" + time + ", state=" + poweredState;
	}

	public void setState(String state) {
		this.poweredState = state;
	}
	
}
