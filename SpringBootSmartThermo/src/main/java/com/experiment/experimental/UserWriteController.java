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
		AppMem.storeData("" + setTempRoom, AppMem.GUIFILENAME);
		String retStr = "" + CVProxy.getTempRoom() + "#" + setTempRoom;
		return retStr;

	}
	
}
