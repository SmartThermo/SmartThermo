package com.experiment.experimental;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserWriteController {

	@RequestMapping(value = "/write/{settemp}", method = RequestMethod.GET)
	public String write(@PathVariable String settemp) {
		double setTempRoom = Double.parseDouble(settemp);

		if( 5.5 <= setTempRoom && setTempRoom <= 29.5 ) { // corresponds with GUI controller range
			AppMem.storeData("" + setTempRoom, AppMem.GUIFILENAME);
			String retStr = "" + CVProxy.getTempRoom() + "#" + setTempRoom;
			return retStr;			
		}

		String retStr = "" + CVProxy.getTempRoom() + "#" + AppMem.retrieveData(AppMem.GUIFILENAME);		
		return retStr;

	}
	
}
