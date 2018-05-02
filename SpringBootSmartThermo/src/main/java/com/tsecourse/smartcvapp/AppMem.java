package com.tsecourse.smartcvapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*

- store
- retrieve

*/

public class AppMem {

	static String line;

	static public void init() {
		storeData("14.0", AppSettings.GUIFILENAME);
		storeData("12.0", AppSettings.GUIFILENAMENIGHT);
	}
	
	static public void storeData(String dataStr, String fileName) {
		try {
			FileWriter fw = new FileWriter(fileName);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(dataStr + "\r\n");
			bw.close();
		} catch (IOException e) {
			System.out.println("File IO exc: " + e.getMessage());
		}
	}

	static public String retrieveData(String fileName) {
		try {
			line = null;
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			line = br.readLine();
			br.close();
		} catch (IOException e) {
			System.out.println("File IO exc: " + e.getMessage());
		}
		return line;
	}
}
