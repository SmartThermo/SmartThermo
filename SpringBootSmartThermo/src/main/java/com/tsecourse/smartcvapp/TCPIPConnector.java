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

public class TCPIPConnector {

	private static Socket socket = null;
	static boolean connectOK = false;
	
	public static Socket getSocket() {
		return socket;
	}

	// IP/TCP checkAlive

	/*https://docs.oracle.com/javase/8/docs/api/java/net/Socket.html#isClosed--
	 * isOutputShutdown()
	 * isInputShutdown()
	 * isClosed()
	 * */	

	static boolean connect() {
	//static void connect() {
		socket = null;
					
		try {
			InetAddress address = InetAddress.getByName(AppSettings.HOST);
				
			try {
				socket = new Socket(address, AppSettings.PORT);
				return connectOK=true;
			} catch (IOException e) {
				System.out.println("Can not connect to CV, socket connect err: " + e);
				return connectOK=false;
			}

		} catch (UnknownHostException e) {
			System.out.println(e);
			return connectOK=false;
		}	
	}
	
	// IP/TCP socket disconnect

	static void disconnect() {

		System.out.println("socket disconnect");
		//System.out.println("value of socket: " + socket);
		try {
			socket.close();
			//System.out.println("value of socket2: " + socket);
			//socket = null; // autom. null by close ?
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
