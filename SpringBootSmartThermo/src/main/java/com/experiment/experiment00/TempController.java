package com.experiment.experiment00;

public class TempController {

	public static String tempController(double actualTempRoom,double setTempRoom) {
//		actualTempRoom = 17.00;
//		setTempRoom = 22.00;
		
		double diffSwitchPoint = 0.50;
		double minSwitchPoint = setTempRoom-diffSwitchPoint;
		double maxSwitchPoint = setTempRoom+diffSwitchPoint;
		
		int timer = ((1000*60*1)/24);
		
		String turnCvOnOff = new String();	
		
		
		System.out.println("minSwitchPoint:"+ minSwitchPoint);
		System.out.println("maxSwitchPoint:"+ maxSwitchPoint);
		
		//turnCvOnOff = "CvOff";
//		try {
//			CONTROLTEMPROOMLOOP:
//			while (true) {
				if (actualTempRoom<setTempRoom || (minSwitchPoint < actualTempRoom && actualTempRoom < maxSwitchPoint)) {
					turnCvOnOff = "CvOn";
					//Thread.sleep(timer); 
				} else {
					turnCvOnOff = "CvOff";
					//Thread.sleep(timer); 
				}
//				continue CONTROLTEMPROOMLOOP;
//			}
			
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//			System.out.println("InterruptedException" + e);
//		}
//		System.out.println(turnCvOnOff);
//		
	return turnCvOnOff;
		}	
}

