package com.experiment.experiment00;

import java.io.*;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserWriteController {

	static private final String FILENAME = "settings.txt"; // temp fix

	
	@RequestMapping(value = "/write/{settemp}", method = RequestMethod.GET)
	public String write(@PathVariable String settemp) {

		String fileName = FILENAME;
		double setTempRoom = Double.parseDouble(settemp);

		try {
			FileWriter fw = new FileWriter(fileName);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(setTempRoom + "\r\n");
			bw.close();
		} catch (IOException e) {
			System.out.println("File IO exc: " + e.getMessage());
		}

		return ("" + setTempRoom);
	}
}
