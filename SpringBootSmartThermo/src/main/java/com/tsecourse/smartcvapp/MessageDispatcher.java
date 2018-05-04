package com.tsecourse.smartcvapp;

import java.io.*;

/* aka POSTKANTOOR
 * 
- open inbox / outbox
- close inbox / outbox
- sendOut
- receiveIn

*/

public class MessageDispatcher {

	static BufferedWriter bw = null;
	static BufferedReader br = null;

	// inbox

	static void openInbox() {
		// if(connected == false) { return; } // is void

		try {
			InputStream is = TCPIPConnector.getSocket().getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
		} catch (IOException e) {
			CVProxy.cvState.setTempRoom(-100.0);
			CVProxy.startUp();
			// SystemAllert.reboot(false);
			// SystemAllert.checkIsAlive=false;
			System.out.println(" openInbox !!! ---------- open inbox err: " + e);
		}
	}

	// outbox

	static void openOutbox() {

		try {
			OutputStream os = TCPIPConnector.getSocket().getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			bw = new BufferedWriter(osw);
		} catch (IOException e) {
			CVProxy.cvState.setTempRoom(-100.0);
			CVProxy.startUp();
			// SystemAllert.reboot(false);
			// SystemAllert.checkIsAlive=false;
			System.out.println(" openOutbox !!! ---------- open outbox err: " + e);
		}

	}

	// receive

	static String receiveIn() {
		String message = null;

		try {
			message = br.readLine();
		} catch (IOException e) {
			CVProxy.cvState.setTempRoom(-100.0);
			CVProxy.startUp();
			// SystemAllert.reboot(false);
			// SystemAllert.checkIsAlive=false;
			System.out.println(" receiveIn !!! ---------- receive in err: " + e);
		}

		System.out.println("\tCV replies: " + message);

		return message;
	}

	// send

	static void sendOut(String msg) {
		//msg = MessageTranslator.buildMessage(msg, "");

		try {
			bw.write(msg);
			bw.flush();
		} catch (IOException e) {
			CVProxy.cvState.setTempRoom(-100.0);
			CVProxy.startUp();
			// SystemAllert.reboot(false);
			// SystemAllert.checkIsAlive=false;
			System.out.println(" sendOut !!! ---------- send out err: " + e);
		}
		System.out.println("\n\tTell CV: " + msg);
	}

}
