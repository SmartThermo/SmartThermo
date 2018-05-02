package com.tsecourse.smartcvapp;

public class CVState {

	// cv measurements
	private double pressure;
	private double tempRoom;
	private double tempOutside;
	private boolean doorClosed;
	private int lastMovement;
	private double gasUsage;
	private long timestamp;

	// user preferences
	private double setTempRoom;
	private double setTempRoomNight;
	
	public double getPressure() {
		return pressure;
	}
	public void setPressure(double pressure) {
		this.pressure = pressure;
	}
	public double getTempRoom() {
		return tempRoom;
	}
	public void setTempRoom(double tempRoom) {
		this.tempRoom = tempRoom;
	}
	public double getTempOutside() {
		return tempOutside;
	}
	public void setTempOutside(double tempOutside) {
		this.tempOutside = tempOutside;
	}
	public boolean isDoorClosed() {
		return doorClosed;
	}
	public void setDoorClosed(boolean doorClosed) {
		this.doorClosed = doorClosed;
	}
	public int getLastMovement() {
		return lastMovement;
	}
	public void setLastMovement(int lastMovement) {
		this.lastMovement = lastMovement;
	}
	public double getGasUsage() {
		return gasUsage;
	}
	public void setGasUsage(double gasUsage) {
		this.gasUsage = gasUsage;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public double getSetTempRoom() {
		return setTempRoom;
	}
	public void setSetTempRoom(double setTempRoom) {
		this.setTempRoom = setTempRoom;
	}
	public double getSetTempRoomNight() {
		return setTempRoomNight;
	}
	public void setSetTempRoomNight(double setTempRoomNight) {
		this.setTempRoomNight = setTempRoomNight;
	}
	

}
