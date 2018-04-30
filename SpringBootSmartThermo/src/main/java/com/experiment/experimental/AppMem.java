package com.experiment.experimental;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AppMem {

	static public final String GUIFILENAME = "settemp.txt";
	static public final String GUIFILENAMENIGHT = "settempnight.txt";
	static public final String APPFILENAME = "data.txt";

	static String line;

	static public void storeData(String dataStr, String fileName) {
		try {
			FileWriter fw = new FileWriter(fileName);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(dataStr + "\r\n");
			//System.out.println("\n++++ writeMem: " + dataStr);
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
			//System.out.println("\n++++ readMem: " + line);
			br.close();
		} catch (IOException e) {
			System.out.println("File IO exc: " + e.getMessage());
		}
		return line;
	}
}
