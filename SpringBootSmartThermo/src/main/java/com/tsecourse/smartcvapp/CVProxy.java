package com.tsecourse.smartcvapp;

import java.time.*;
import java.time.format.*;

/*
- startUp
- controlTemp
- shutDown

*/

public class CVProxy {

	static CVState cvState = new CVState();

	static String lastData = null;

//	private static boolean appRunning;

		
	static void waitPauze(int pauzeTime) {
		try {
			Thread.sleep(pauzeTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	// clean up resources

	public static void shutDown	() {
		TCPIPConnector.disconnect();
	}
	
	
	// ... 

	public static void startUp() {
		
		//appRunning = true;
		
		StartCVConnection.startCVConnection();
					
		lastData = MessageDispatcher.receiveIn();
		
		AppMem.init(); 
		
		CONTROLTEMPROOMLOOP:
		while (TCPIPConnector.connectOK==true) {
		//while (appRunning) {

			getCurrentCVData();
			MessageTranslator.processData();

			LocalDateTime dt = LocalDateTime.ofEpochSecond(cvState.getTimestamp(), 0, ZoneOffset.UTC);
			DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("HH:mm");

			String dateStr = dt.format(dtFormatter);
			LocalTime lt = LocalTime.parse(dateStr, DateTimeFormatter.ofPattern("HH:mm"));
			LocalTime ltStart = LocalTime.parse(AppSettings.DAYSTART, DateTimeFormatter.ofPattern("HH:mm"));
			LocalTime ltEnd = LocalTime.parse(AppSettings.DAYEND, DateTimeFormatter.ofPattern("HH:mm"));

			System.out.println("time on SmartCV side: " + dateStr + ", lt:  " + lt + ", " + ltStart + ", " + ltEnd);

			cvState.setSetTempRoom(Double.parseDouble(AppMem.retrieveData(AppSettings.GUIFILENAME)));
			cvState.setSetTempRoomNight(Double.parseDouble(AppMem.retrieveData(AppSettings.GUIFILENAMENIGHT)));

			// |--------Nacht--------|-------Dag---------|-------Nacht------------|

			double thresholdTemp;

			if (lt.isAfter(ltStart) && lt.isBefore(ltEnd)) {
				thresholdTemp = cvState.getSetTempRoom();
			} else {
				thresholdTemp = cvState.getSetTempRoomNight();
			}

			double minSwitchPoint = thresholdTemp - AppSettings.TEMPDIFF;

			System.out.println("\n control loop:  tempRoom / setTemp / setTempNight = " + 
					cvState.getTempRoom() + " /  " + cvState.getSetTempRoom() + " /  " + cvState.getSetTempRoomNight());

			if (cvState.getTempRoom() <= minSwitchPoint) {
				turnOn();
				System.out.println(">>>> turn on");
			} else {
				turnOff();
				System.out.println(">>>> turn off");
			}

			continue CONTROLTEMPROOMLOOP;
		}
		shutDown();
		//SystemAllert.reboot(false);
	}

	// cmd: cv give data

	private static void getCurrentCVData() {
		MessageDispatcher.sendOut(MessageTranslator.buildMessage("stat", ""));
		waitPauze(AppSettings.PAUZEMILLIS);
		// waitPauze();
		lastData = MessageDispatcher.receiveIn();
	}

	// cmd: cv on

	static void turnOn() {
		MessageDispatcher.sendOut(MessageTranslator.buildMessage("on", ""));
		waitPauze(AppSettings.PAUZEMILLIS);
		// waitPauze();
			lastData = MessageDispatcher.receiveIn();
		if (!lastData.equals(AppSettings.CONFIRMCMD)) {
			System.out.println("command not confirmed");
		}
	}

	// cmd: cv off

	static void turnOff() {
		MessageDispatcher.sendOut(MessageTranslator.buildMessage("off", ""));
		waitPauze(AppSettings.PAUZEMILLIS);
		// waitPauze();
		lastData = MessageDispatcher.receiveIn();
		if (!lastData.equals(AppSettings.CONFIRMCMD)) {
			System.out.println("command not confirmed");
		}
	}



}
