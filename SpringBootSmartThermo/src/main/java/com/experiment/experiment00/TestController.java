package com.experiment.experiment00;

//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class TestController {

	private String headerStr = "<html><head><title>SMART CV APP</title></head>";
	private String bodyStr = "TO DO: basic GUI";
	private String footerStr = "</body></html>";
	
	@RequestMapping("/test")
	public String controllerTest() {
		return headerStr + bodyStr + footerStr;
	}
	
}
