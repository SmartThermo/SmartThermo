package com.tsecourse.smartcvapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tsecourse.smartcvapp.services.AppMemService;
import com.tsecourse.smartcvapp.services.CVProxyService;

@RestController
public class UserWriteController {

	@Autowired
	private AppMemService am;

	@Autowired
	private CVProxyService cvProxy;

	@RequestMapping(value = "/write/{settemp}/{settempnight}", method = RequestMethod.GET) 
	public String write(@PathVariable("settemp") String settemp, @PathVariable("settempnight") String settempnight) {

		double tempRoom = cvProxy.getState().getTempRoom();
		double setTempRoom = Double.parseDouble(settemp);
		double setTempRoomNight = Double.parseDouble(settempnight);
		double lb = 5.5;
		double ub = 29.5;

		if((lb <= setTempRoom && setTempRoom <= ub) &&  (lb <= setTempRoomNight && setTempRoomNight <= ub)) { 
		
			am.storeData("" + setTempRoom, am.getGUIFILENAME()); 
			am.storeData("" + setTempRoomNight, am.getGUIFILENAMENIGHT()); 

			String retStr = tempRoom + "#" + setTempRoom + "#" + setTempRoomNight;

			System.out.println("UserWriteController: " + retStr + " - " + am);

			return retStr;			
		}

		String retStr = tempRoom + 
				"#" + am.retrieveData(am.getGUIFILENAME()) + 
				"#" + am.retrieveData(am.getGUIFILENAMENIGHT()); 

		System.out.println("UserWriteController: " + retStr + " - " + am);
		
		return retStr;
	}
	
}


