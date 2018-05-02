package com.tsecourse.smartcvapp;

/*
- compile / build
- parse

*/

public class MessageTranslator {

	//private static String lastData = null;
		
	static String buildMessage(String type, String appendix) {

		String msg = "$CV-";

		switch (type) {

		case "connect":
			msg += "CONNECT-$-" + AppSettings.SECRET;
			break;
		case "stat":
			msg += "STAT?";
			break;
		case "on":
			msg += "ACT-$50$70";
			break;
		case "off":
			msg += "ACT-$0$0";
			break;

		}

		return msg + "\n";
	}
	
	static void processData() {

		String[] arr = CVProxy.lastData.split("#");

		CVProxy.cvState.setPressure(Double.parseDouble(arr[2]) / 100.0);
		CVProxy.cvState.setTempRoom(Double.parseDouble(arr[3]));
		CVProxy.cvState.setTempOutside(Double.parseDouble(arr[4]));
		CVProxy.cvState.setDoorClosed( (arr[5].equals("1") ? true : false) );
		CVProxy.cvState.setLastMovement(Integer.parseInt(arr[6])); 
		CVProxy.cvState.setGasUsage(Double.parseDouble(arr[7]));
		CVProxy.cvState.setTimestamp(Long.parseLong(arr[8]));

		AppMem.storeData(CVProxy.lastData, AppSettings.APPFILENAME);
	}
}
