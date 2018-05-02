package com.tsecourse.smartcvapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartCVApplication {

	public static void main(String[] args) {
		
			/* user facing (thread) */
		
		SpringApplication.run(SmartCVApplication.class, args);
		
			/* CV sim facing */

		CVProxy.startUp();
		
	}

}

