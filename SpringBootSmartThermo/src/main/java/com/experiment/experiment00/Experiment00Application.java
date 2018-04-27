package com.experiment.experiment00;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication; // CTL + SHIFT + F

import java.io.*; // file IO

import java.net.InetAddress; // socket
import java.net.Socket;
import java.net.UnknownHostException;

@SpringBootApplication
public class Experiment00Application {

	static private final String SECRET = "123456"; // temp fix
	static private final double DEFTEMP = -1.0; // temp fix
	static private final String FILENAME = "settings.txt"; // temp fix

	private static Socket socket;

	public static void main(String[] args) {

//		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		
		String fileName = FILENAME;
		String line = null;

		// testing purposes: SIM USER WANTS SETTEMP

		double setTempRoom = -1.0;
		double actualTempRoom = 0.0;

		// START spring boot app

		SpringApplication.run(Experiment00Application.class, args);

		// START control loop

		try {
			BufferedWriter bw = startCVconnection();
			getReturnMessageCV();
			getCVStatus(bw);
			getTempRoom();

			try {

				CONTROLTEMPROOMLOOP: while (true) {

					// what does user want ??: setTempRoom = readFile

					setTempRoom = readSetTempFromFile(fileName);

					while (setTempRoom > actualTempRoom) {
						turnCVon(bw);
						getReturnMessageCV();
						Thread.sleep((1000 * 60 * 1) / 24); // 1/24 minute
						getCVStatus(bw);
						actualTempRoom = getTempRoom();
						Thread.sleep((1000 * 60 * 1) / 24);
						setTempRoom = readSetTempFromFile(fileName);
					}
					// Turn cvOff
					turnCVoff(bw);
					getReturnMessageCV(); // Get the return message from CV server
					Thread.sleep((1000 * 60 * 1) / 24);
					getCVStatus(bw);
					actualTempRoom = getTempRoom();
					Thread.sleep((1000 * 60 * 1) / 24); // 1/24 minute
					setTempRoom = readSetTempFromFile(fileName);

					continue CONTROLTEMPROOMLOOP;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			getCVStatus(bw);
			getTempRoom();
		}

		catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			closeCVconnection();
		}
		
	}

	private static double readSetTempFromFile(String fileName) {
		String line;
		double setTempRoom = 0.0;
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			while ((line = br.readLine()) != null) {
				setTempRoom = Double.parseDouble(line);
			}

			System.out.println("setTemp = " + setTempRoom);

			br.close();

		} catch (IOException e) {
			System.out.println("File IO exc: " + e.getMessage());
		}
		return setTempRoom;
	}

	private static void getReturnMessageCV() throws IOException {
		InputStream is = socket.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String message = br.readLine();
		System.out.println("\t Message received from the server : " + message);
	}

	private static BufferedWriter startCVconnection() throws UnknownHostException, IOException {
		String host = "localhost";
		int port = 7777;
		InetAddress address = InetAddress.getByName(host);

		socket = new Socket(address, port);

		// Send the message to the server
		OutputStream os = socket.getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(os);
		BufferedWriter bw = new BufferedWriter(osw);

		String cvConnect = "$CV-CONNECT-$-" + SECRET;

		String sendMessage = cvConnect + "\n";
		bw.write(sendMessage);
		bw.flush();
		System.out.println("\t Connect to CV: " + sendMessage);

		return bw;
	}

	private static void getCVStatus(BufferedWriter bw) throws IOException {
		String cvStatus = "$CV-STAT?";
		String sendMessageStatus = cvStatus + "\n";
		bw.write(sendMessageStatus);
		bw.flush();
		System.out.println("\t Ask for current CV status: " + sendMessageStatus);
	}

	private static Double getTempRoom() {

		double getTempRoom = 0.0;

		try {
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String message = br.readLine();

			String[] arr = message.split("#");
			getTempRoom = Double.valueOf(arr[3]);

			System.out.println("Temperature in Room= " + getTempRoom);

		} catch (IOException e) {

			System.out.println("IOExc: " + e.getMessage());
		}

		return getTempRoom;
	}

	private static void turnCVon(BufferedWriter bw) {
		String cvOn = "$CV-ACT-$50$30"; // tested safe limits: turn CV on with pump 50, heater 30.
		String sendMessagecvOn = cvOn + "\n";

		try {
			bw.write(sendMessagecvOn);
			bw.flush();
		} catch (IOException e) {
			System.out.println("Err: " + e.getMessage());
		}
		System.out.println("\t Turn CV On: " + sendMessagecvOn);
	}

	private static void turnCVoff(BufferedWriter bw) {
		String cvOff = "$CV-ACT-$0$0"; // turn CV off with pump 0, heater 0.
		String sendMessagecvOff = cvOff + "\n";

		try {
			bw.write(sendMessagecvOff);
			bw.flush();
		} catch (IOException e) {
			System.out.println("Err: " + e.getMessage());
		}

		System.out.println("\t Turn CV Off: " + sendMessagecvOff);
	}

	private static void closeCVconnection() {
		try {
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
