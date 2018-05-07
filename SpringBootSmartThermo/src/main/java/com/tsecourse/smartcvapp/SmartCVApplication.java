package com.tsecourse.smartcvapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tsecourse.smartcvapp.services.*;

@SpringBootApplication
public class SmartCVApplication {

	static private CVProxyService cvProxy;

		/* constructor injection */

	@Autowired
	public SmartCVApplication(AppMemService am, CVProxyService cvProxyServ) { 
		am.init();
		cvProxy = cvProxyServ;
		System.out.println("CVApplication - AppMemService / CVProxyService:\n\t" + am + "\n\t" + cvProxy);
	}

		/* main */

	public static void main(String[] args) {

			/* user facing */
		
		SpringApplication.run(SmartCVApplication.class, args);

			/* CV sim facing + managing */

		cvProxy.startUp();		
	}

}

