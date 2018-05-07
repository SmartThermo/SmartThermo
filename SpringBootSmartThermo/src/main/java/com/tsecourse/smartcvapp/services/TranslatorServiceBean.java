package com.tsecourse.smartcvapp.services;

import org.springframework.stereotype.Service;
import com.tsecourse.smartcvapp.CVState;

@Service
public class TranslatorServiceBean implements TranslatorService {

	@Override
	public void processData(CVState cvState, String lastData) { 
				
		String[] arr = lastData.split("#");

		cvState.setPressure(Double.parseDouble(arr[2]) / 100.0);
		cvState.setTempRoom(Double.parseDouble(arr[3]));
		cvState.setTempOutside(Double.parseDouble(arr[4]));
		cvState.setDoorClosed( (arr[5].equals("1") ? true : false) );
		cvState.setLastMovement(Integer.parseInt(arr[6])); 
		cvState.setGasUsage(Double.parseDouble(arr[7]));
		cvState.setTimestamp(Long.parseLong(arr[8]));
	}

	@Override
	public String buildMessage(String type, String appendix) {
		String msg = "$CV-";

		switch (type) {

		case "connect":
			msg += "CONNECT-$-" + appendix; // SECRET;
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

}
