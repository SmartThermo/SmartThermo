package com.tsecourse.smartcvapp;

public class StartCVConnection {

	// IP/TCP socket connect
	static void startCVConnection() {
		TCPIPConnector.connect();
		
		// infinite loop

		while (TCPIPConnector.connectOK==false) {
			// for (int i=1; i<3; i++) {
			//TCPIPConnector.disconnect(); //not needed.
			System.out.println("Connection to CV could not be made.");// to SystemAllert
			CVProxy.waitPauze(AppSettings.PAUZE30S);
			TCPIPConnector.connect();
		}
		// (TCPIPConnector.connectOK==true)
		MessageDispatcher.openOutbox();
		MessageDispatcher.openInbox();
		// start conversation with SIM
		MessageDispatcher.sendOut(MessageTranslator.buildMessage("connect", ""));

		// System.out.println("Connection to CV can not be made. Please call"); //to
		// SystemAllert
	}
}
