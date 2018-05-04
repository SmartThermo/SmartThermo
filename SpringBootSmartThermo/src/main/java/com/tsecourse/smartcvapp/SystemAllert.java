package com.tsecourse.smartcvapp;

public class SystemAllert {
	
	static boolean checkIsAlive;
	
	static void reboot(boolean checkIsAlive) {
		
		if(checkIsAlive==true) {
			System.out.println("System is alive");
						
			}else {
				System.out.println("No connection to CV, system is trying to restart");
				CVProxy.turnOff();
				CVProxy.waitPauze(AppSettings.PAUZE30S);
				CVProxy.startUp();
				
		}
//		 if(TCPIPConnector.getSocket().isOutputShutdown() || TCPIPConnector.getSocket().isInputShutdown()) {
//			 System.out.println("TCP/IP connection lost");
//			 return false;
//		 }
//		 return true;
	}
}
