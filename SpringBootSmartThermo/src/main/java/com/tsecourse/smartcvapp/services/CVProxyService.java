package com.tsecourse.smartcvapp.services;

import com.tsecourse.smartcvapp.CVState;

public interface CVProxyService {

	public void waitPauze(int pauzeTime);
	
	public void shutDown();

	public void startUp();
	
	public void startSocketConnection();
	
	public void getCurrentData();

	public void turnOn();

	public void turnOff();
	
	public void trySocketReconnect();
	
	public CVState getState();

}
