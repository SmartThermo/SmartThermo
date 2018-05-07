package com.tsecourse.smartcvapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tsecourse.smartcvapp.services.AppMemService;
import com.tsecourse.smartcvapp.services.CVProxyService;

@RestController
public class UserReadController {

	@Autowired
	private AppMemService am;
	
	@Autowired
	private CVProxyService cvProxy;
	
	@RequestMapping("/read")
	public String read() { 

		double tempRoom = cvProxy.getState().getTempRoom();
		
		String retStr = tempRoom + 
				"#" + am.retrieveData(am.getGUIFILENAME()) + 
				"#" + am.retrieveData(am.getGUIFILENAMENIGHT());

		System.out.println("UserReadController: " + retStr + " - " + am);

		return retStr;
	}

}
