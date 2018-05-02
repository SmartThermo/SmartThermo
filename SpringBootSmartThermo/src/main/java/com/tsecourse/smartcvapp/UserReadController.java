package com.tsecourse.smartcvapp;

import org.springframework.web.bind.annotation.*;

@RestController
public class UserReadController {
	
	@RequestMapping("/read")
	public String read() {
		String retStr = "" + CVProxy.cvState.getTempRoom() + 
				"#" + AppMem.retrieveData(AppSettings.GUIFILENAME) + 
				"#" + AppMem.retrieveData(AppSettings.GUIFILENAMENIGHT);
		return retStr;
	}

}
