package com.tsecourse.smartcvapp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/*
- connect
- disconnect
- checkAlive

*/

public class CVConnector {

	private static Socket socket = null;
	
	public static Socket getSocket() {
		return socket;
	}

	// IP/TCP checkAlive

	/*https://docs.oracle.com/javase/8/docs/api/java/net/Socket.html#isClosed--
	 * isOutputShutdown()
	 * isInputShutdown()
	 * isClosed()
	 * */	

	static boolean checkAlive() {
		 if(socket.isOutputShutdown() || socket.isInputShutdown()) {
			 return false;
		 }
		 return true;
	}
	
	// IP/TCP socket connect
	
	static void connect() {

		socket = null;

		try {
			InetAddress address = InetAddress.getByName(AppSettings.HOST);

			try {
				socket = new Socket(address, AppSettings.PORT);

			} catch (IOException e) {
				System.out.println("socket connect err: " + e);
			}

		} catch (UnknownHostException e) {
			System.out.println(e);
		}	
	}
	
	// IP/TCP socket disconnect

	static void disconnect() {

		System.out.println("socket disconnect");

		try {
			socket.close();
			//socket = null; // autom. null by close ?
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
