package com.experiment.experimental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/* APPLICATION */

@SpringBootApplication
public class ExperimentalApplication {

	public static void main(String[] args) {
		
		AppMem.storeData("14.0", AppMem.GUIFILENAME);
		AppMem.storeData("12.0", AppMem.GUIFILENAMENIGHT);

		SpringApplication.run(ExperimentalApplication.class, args);
		
		CVProxy.cvStart();
		
	}

}

