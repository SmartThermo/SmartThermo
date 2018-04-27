package com.experiment.experiment00;

// import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestTempController {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	public void testTempController() throws Exception {
//		double actualTempRoom = 0.0;
//		double setTempRoom = -1.0;
		
		//actual / set
		Assert.assertEquals(TempController.tempController(15.0, 20.0), "CvOn");
		Assert.assertEquals(TempController.tempController(19.6, 20.0), "CvOn");
		Assert.assertEquals(TempController.tempController(20.4, 20.0), "CvOn");
		Assert.assertEquals(TempController.tempController(19., 20.0), "CvOn");
		Assert.assertEquals(TempController.tempController(20.6, 20.0), "CvOff");
		Assert.assertEquals(TempController.tempController(20.5, 20.0), "CvOff");
		
		//fail("Not yet implemented");
	}


	

}
