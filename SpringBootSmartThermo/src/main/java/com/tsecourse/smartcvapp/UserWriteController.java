package com.tsecourse.smartcvapp;

import org.springframework.web.bind.annotation.*;

@RestController
public class UserWriteController {
	
	@RequestMapping(value = "/write/{settemp}/{settempnight}", method = RequestMethod.GET) 
	public String write(@PathVariable("settemp") String settemp, @PathVariable("settempnight") String settempnight) {

		double setTempRoom = Double.parseDouble(settemp);
		double setTempRoomNight = Double.parseDouble(settempnight);

		if( (5.5 <= setTempRoom && setTempRoom <= 29.5) &&  (5.5 <= setTempRoomNight && setTempRoomNight <= 29.5)) { 
		
			AppMem.storeData("" + setTempRoom, AppSettings.GUIFILENAME);
			AppMem.storeData("" + setTempRoomNight, AppSettings.GUIFILENAMENIGHT);
			String retStr = "" + CVProxy.cvState.getTempRoom() + "#" + CVProxy.cvState.getSetTempRoom() + "#" + CVProxy.cvState.getSetTempRoomNight();

			return retStr;			
		}

		String retStr = "" + CVProxy.cvState.getTempRoom() + 
				"#" + AppMem.retrieveData(AppSettings.GUIFILENAME) + 
				"#" + AppMem.retrieveData(AppSettings.GUIFILENAMENIGHT);		

		return retStr;
	}
	
}


