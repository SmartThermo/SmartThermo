package com.experiment.experimental;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.*;
import java.time.format.*;



public class CVProxy {

	//private static double setTempRoom = 17.5; // temp fix
	
	final private static String DAYSTART = "07:00";
	final private static String DAYEND = "22:00";
	
	final private static double TEMPDIFF = 0.5;
	final private static String CONFIRMCMD = "#ACT-OK";
	final private static String SECRET = "123456";
	final private static String HOST = "localhost";
	final private static int PORT = 7777;
	final private static int PAUZEMILLIS = (1000 * 60 * 1) / 24;

	private static Socket socket = null;
	private static BufferedWriter bw = null;
	private static BufferedReader br = null;
	private static String lastData = null;

	private static boolean appRunning;

	private static double thresholdTemp;
	private static double setTempRoom;
	private static double setTempRoomNight;
	private static double pressure;
	private static double tempRoom;
	private static double tempOutside;
	private static boolean doorClosed;
	private static int lastMovement; // temp fix
	private static double gasUsage;
	private static long timestamp; // temp fix

	// alert system
	
	private static void systemAlert(String msg) {
		System.out.println("Alert >>>>>>> " + msg);
	}

	// accessors

	public static double getPressure() {
		return pressure;
	}

	public static double getTempRoom() {
		return tempRoom;
	}

	public static double getTempOutside() {
		return tempOutside;
	}

	public static boolean getDoorClosed() {
		return doorClosed;
	}

	public static int getLastMovement() {
		return lastMovement;
	}

	public static double getGasUsage() {
		return gasUsage;
	}

	public static long getTimestamp() {
		return timestamp;
	}

	// start control loop

	public static void cvStart() {

		appRunning = true;
		
		cvConnect();

		CONTROLTEMPROOMLOOP: 
			
			while(appRunning) {

				getData(); // needs connection
				processData();

				// thus known: time on SmartCV side

				// DATETIME
				ZonedDateTime dt = LocalDateTime.ofEpochSecond(timestamp, 0, OffsetDateTime.now(ZoneId.systemDefault()).getOffset()).atZone(ZoneId.systemDefault());
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
				String dateStr = dt.format(dtf);

				//LocalTime lt = LocalTime.of(Integer.parseInt(dateStr.split(":")[0]), Integer.parseInt(dateStr.split(":")[1]));				
				LocalTime lt = LocalTime.parse(dateStr,DateTimeFormatter.ofPattern("HH:mm"));
				  
				//LocalTime ltStart = LocalTime.of(Integer.parseInt(DAYSTART.split(":")[0]), Integer.parseInt(DAYSTART.split(":")[1]));
				LocalTime ltStart = LocalTime.parse(DAYSTART,DateTimeFormatter.ofPattern("HH:mm"));
				  				
				//LocalTime ltEnd = LocalTime.of(Integer.parseInt(DAYEND.split(":")[0]), Integer.parseInt(DAYEND.split(":")[1]));
				LocalTime ltEnd = LocalTime.parse(DAYEND,DateTimeFormatter.ofPattern("HH:mm"));
				
				// LocalDateTime dt = LocalDateTime.ofEpochSecond(timestamp, 0, );
				
				System.out.println("time on SmartCV side: " + dateStr + ", lt:  " + lt + ", " + ltStart + ", " + ltEnd);
				
				setTempRoom = Double.parseDouble(AppMem.retrieveData(AppMem.GUIFILENAME));
				setTempRoomNight = Double.parseDouble(AppMem.retrieveData(AppMem.GUIFILENAMENIGHT));

				// |--------Nacht--------|-------Dag---------|-------Nacht------------|
				
				if(lt.isAfter(ltStart) && lt.isBefore(ltEnd)) { 
					thresholdTemp = setTempRoom; 
				} else {
					thresholdTemp = setTempRoomNight; 					
				}

				// to do: IF drukTeHoog THEN appRunning = false; ENDIF

				double minSwitchPoint = thresholdTemp - TEMPDIFF;
	
				System.out.println("\ncontrol loop:  tempRoom / setTemp / setTempNight = " + tempRoom + " /  " + setTempRoom + " /  " + setTempRoomNight);
				
				if(tempRoom <= minSwitchPoint) {
					turnOn(); 
					System.out.println(">>>> turn on");
				} else {
					turnOff(); 
					System.out.println(">>>> turn off");
				}

				continue CONTROLTEMPROOMLOOP;
		}
		cvDisconnect();
	}

	// connect

	private static void cvConnect() {

		System.out.println(" ----> cvConnect");

		socket = null;

		try {
			InetAddress address = InetAddress.getByName(HOST);

			try {
				socket = new Socket(address, PORT);
				openOutbox();
				openInbox();
				sendOut(buildMessage("connect", ""));
				lastData = receiveIn();

			} catch (IOException e) {
				tempRoom = -100.0;
				systemAlert(" cvConnect !!! ---------- conn err: " + e);
			}
		} catch (UnknownHostException e) {
			System.out.println(e);
		}
	}

	// disconnect, private ?

	public static void cvDisconnect() {
		System.out.println(" ----> cvDisconnect");

		try {
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// cmd: cv on

	private static void turnOn() {
		sendOut(buildMessage("on", ""));
		waitPauze();
		lastData = receiveIn(); 
		if (!lastData.equals(CONFIRMCMD)) {
			System.out.println("command not confirmed");
		}
	}

	// cmd: cv off

	private static void turnOff() {
		sendOut(buildMessage("off", ""));
		waitPauze();
		lastData = receiveIn(); 
		if (!lastData.equals(CONFIRMCMD)) {
			System.out.println("command not confirmed");
		}
	}

	// process info (+ make available as clas variables and in "app memory")

	private static void processData() {

		//System.out.println(">>>>> processData");
		
		String[] arr = lastData.split("#");

		pressure = Double.parseDouble(arr[2]) / 100.0;
		tempRoom = Double.parseDouble(arr[3]);
		tempOutside = Double.parseDouble(arr[4]);
		doorClosed = (arr[5].equals("1") ? true : false);
		lastMovement = Integer.parseInt(arr[6]); // temp fix
		gasUsage = Double.parseDouble(arr[7]);
		timestamp = Long.parseLong(arr[8]); // temp fix

		AppMem.storeData(lastData, AppMem.APPFILENAME);
	}

	// cmd: cv give data

	private static void getData() {
		sendOut(buildMessage("stat", ""));
		waitPauze();
		lastData = receiveIn();
	}

	// pauze / wait

	private static void waitPauze() {
		try {
			Thread.sleep(PAUZEMILLIS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// inbox

	private static void openInbox() {

		try {
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
		} catch (IOException e) {
			tempRoom = -100.0;
			systemAlert(" openInbox !!! ---------- open inbox err: " + e);
		}

	}

	// outbox

	private static void openOutbox() {

		try {
			OutputStream os = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			bw = new BufferedWriter(osw);
		} catch (IOException e) {
			tempRoom = -100.0;
			systemAlert(" openOutbox !!! ---------- open outbox err: " + e);
		}

	}

	// receive

	private static String receiveIn() {
		String message = null;

		try {
			message = br.readLine();
		} catch (IOException e) {
			tempRoom = -100.0;
			systemAlert(" receiveIn !!! ---------- receive in err: " + e);
		}

		System.out.println("\tCV replies: " + message);

		return message;
	}

	// send

	private static void sendOut(String msg) {
		try {
			bw.write(msg);
			bw.flush();
		} catch (IOException e) {
			tempRoom = -100.0;
			systemAlert(" sendOut !!! ---------- send out err: " + e);
		}
		
		System.out.println("\n\tTell CV: " + msg);
	}

	// message to CV: type: on / off / connect / stat, appendix: for future use

	private static String buildMessage(String type, String appendix) {

		String msg = "$CV-";

		switch (type) {

		case "on":
			msg += "ACT-$50$70";
			break;
		case "off":
			msg += "ACT-$0$0";
			break;
		case "connect":
			msg += "CONNECT-$-" + SECRET;
			break;
		case "stat":
			msg += "STAT?";
			break;

		}

		return msg + "\n";
	}

}
