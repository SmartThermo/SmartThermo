package com.experiment.experiment00;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//import java.net.Socket;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserReadController {
	
	static private final String FILENAME = "settings.txt"; // temp fix

	@RequestMapping("/read")
	public String read() {

		String fileName = FILENAME;
		String line = null;
		
		double setTempRoom = -1.0;

		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			while ((line = br.readLine()) != null) {
				setTempRoom = Double.parseDouble(line);
			}
			System.out.println("setTemp = " + setTempRoom);
			br.close();

		} catch (IOException e) {
			System.out.println("File IO exc: " + e.getMessage());
		}

		return ("" + setTempRoom); //"REST API: user read request";
	}
}
