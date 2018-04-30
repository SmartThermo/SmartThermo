package com.experiment.experimental;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserWriteController {
	@RequestMapping(value = "/write/{settemp}/{settempnight}", method = RequestMethod.GET) 
	public String write(@PathVariable("settemp") String settemp, @PathVariable("settempnight") String settempnight) {

		//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + settemp);
		//double setTempRoom = Double.parseDouble(settemp);

		double setTempRoom = Double.parseDouble(settemp); //.split("#")[0]);
		double setTempRoomNight = Double.parseDouble(settempnight); //settemp.split("#")[1]);

		if( (5.5 <= setTempRoom && setTempRoom <= 29.5) &&  (5.5 <= setTempRoomNight && setTempRoomNight <= 29.5)) { 
		
		//if( 5.5 <= setTempRoom && setTempRoom <= 29.5 ) { // corresponds with GUI controller range
			AppMem.storeData("" + setTempRoom, AppMem.GUIFILENAME);
			AppMem.storeData("" + setTempRoomNight, AppMem.GUIFILENAMENIGHT);
			//AppMem.storeData("" + setTempRoom, AppMem.GUIFILENAME);
			String retStr = "" + CVProxy.getTempRoom() + "#" + setTempRoom + "#" + setTempRoomNight;
			//String retStr = "" + CVProxy.getTempRoom() + "#" + setTempRoom;
			return retStr;			
		}

		String retStr = "" + CVProxy.getTempRoom() + 
				"#" + AppMem.retrieveData(AppMem.GUIFILENAME) + 
				"#" + AppMem.retrieveData(AppMem.GUIFILENAMENIGHT);		
		//String retStr = "" + CVProxy.getTempRoom() + "#" + AppMem.retrieveData(AppMem.GUIFILENAME);		
		return retStr;

	}
	
}


