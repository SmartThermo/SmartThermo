package com.tsecourse.smartcvapp.services;

import java.time.*;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tsecourse.smartcvapp.CVState;

@Service
public class CVProxyServiceBean implements CVProxyService {

	@Autowired
	private TranslatorService translator;
	
	@Autowired
	private DispatchService dispatcher;
	
	@Autowired
	private AppMemService am;
	
	@Autowired
	private SocketConnectorService socketConn;

	@Autowired
	private CVState cvState;

	private String lastData = null;
	
	@Override
	public void trySocketReconnect() {
		cvState.setTempRoom(-100.0);
		startUp();
	}
	
	@Override
	public CVState getState() {
		return cvState;
	}
	
	@Override
	public void waitPauze(int pauzeTime) {
		try {
			Thread.sleep(pauzeTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getCurrentData() {
		dispatcher.sendOut(translator.buildMessage("stat", ""));
		waitPauze(am.getPAUZEMILLIS());
		lastData = dispatcher.receiveIn();
	}

	@Override
	public void turnOn() {
		dispatcher.sendOut(translator.buildMessage("on", ""));
		waitPauze(am.getPAUZEMILLIS());
			lastData = dispatcher.receiveIn();
		if (!lastData.equals(am.getCONFIRMCMD())) {
			System.out.println("command not confirmed");
		}
	}

	@Override
	public void turnOff() {
		dispatcher.sendOut(translator.buildMessage("off", ""));
		waitPauze(am.getPAUZEMILLIS());
		lastData = dispatcher.receiveIn();
		if (!lastData.equals(am.getCONFIRMCMD())) {
			System.out.println("command not confirmed");
		}
	}

	@Override
	public void shutDown() {
		System.out.println("shutdown, socket disconnect");
		socketConn.disconnect();
	}

	@Override
	public void startSocketConnection() {

		socketConn.connect();
		
		while( !socketConn.isConnected() ) {
			System.out.println("Connection to CV could not be made, retry.");
			waitPauze(6 * am.getPAUZEMILLIS());
			socketConn.connect();
		}

		System.out.println(">>> startSocketConnection: socket connected !!");

		dispatcher.openOutbox();
		dispatcher.openInbox();
		
		dispatcher.sendOut(translator.buildMessage("connect", am.getSECRET()));
	}
	
	@Override
	public void startUp() { /* to do: move to TempManagerService class */
		System.out.println("CVProxyService.startup - AppMemService / SocketConnectorService: " + am + " / " + socketConn);
		
		startSocketConnection();
					
		lastData = dispatcher.receiveIn();
		
		CONTROLTEMPROOMLOOP:

		while(socketConn.isConnected()) {

			getCurrentData();
			
			translator.processData(cvState, lastData);
			am.storeData(lastData, am.getAPPFILENAME());

			LocalDateTime dt = LocalDateTime.ofEpochSecond(cvState.getTimestamp(), 0, ZoneOffset.UTC);
			DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("HH:mm");

			String dateStr = dt.format(dtFormatter);
			LocalTime lt = LocalTime.parse(dateStr, DateTimeFormatter.ofPattern("HH:mm"));
			LocalTime ltStart = LocalTime.parse(am.getDAYSTART(), DateTimeFormatter.ofPattern("HH:mm"));
			LocalTime ltEnd = LocalTime.parse(am.getDAYEND(), DateTimeFormatter.ofPattern("HH:mm"));

			System.out.println("Time on SmartCV side: " + dateStr + ", lt:  " + lt + ", " + ltStart + ", " + ltEnd);

			cvState.setSetTempRoom(Double.parseDouble(am.retrieveData(am.getGUIFILENAME())));
			cvState.setSetTempRoomNight(Double.parseDouble(am.retrieveData(am.getGUIFILENAMENIGHT())));

			double thresholdTemp;

			if (lt.isAfter(ltStart) && lt.isBefore(ltEnd)) {
				thresholdTemp = cvState.getSetTempRoom();
			} else {
				thresholdTemp = cvState.getSetTempRoomNight();
			}

			double minSwitchPoint = thresholdTemp - am.getTEMPDIFF();

			System.out.println("\n control loop:  tempRoom / setTemp / setTempNight = " + 
					cvState.getTempRoom() + " /  " + cvState.getSetTempRoom() + " /  " + cvState.getSetTempRoomNight());

			if (cvState.getTempRoom() <= minSwitchPoint) {
				turnOn();
				System.out.println(">>>> Turn CV on");
			} else {
				turnOff();
				System.out.println(">>>> Turn CV off");
			}

			continue CONTROLTEMPROOMLOOP;
		}

		shutDown();

		
	} // END startUp

}
