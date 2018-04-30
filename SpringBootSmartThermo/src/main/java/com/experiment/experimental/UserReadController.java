package com.experiment.experimental;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserReadController {
	
	@RequestMapping("/read")
	public String read() {
		String retStr = "" + CVProxy.getTempRoom() + 
				"#" + AppMem.retrieveData(AppMem.GUIFILENAME) + 
				"#" + AppMem.retrieveData(AppMem.GUIFILENAMENIGHT);
		return retStr;
	}

}
