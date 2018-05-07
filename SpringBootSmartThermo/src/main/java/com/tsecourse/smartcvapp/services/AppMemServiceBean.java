package com.tsecourse.smartcvapp.services;

import java.io.*;
import org.springframework.stereotype.Service;

@Service
public class AppMemServiceBean implements AppMemService {

		/* to be added to config.xml */

	final private String DAYSTART = "07:00";
	final private String DAYEND = "22:00";
	final private double TEMPDIFF = 0.5;

	final private String CONFIRMCMD = "#ACT-OK";
	
	final private String SECRET = "123456";
	final private String HOST = "localhost";
	final private int PORT = 7777;
	final private int PAUZEMILLIS = (1000 * 60 * 1) / 24;

	final private String GUIFILENAME = "settemp.txt";
	final private String GUIFILENAMENIGHT = "settempnight.txt";
	final private String APPFILENAME = "data.txt";
	
	@Override
	public void init() {
		storeData("14.0", GUIFILENAME);
		storeData("12.0", GUIFILENAMENIGHT);
	}

	@Override
	public void storeData(String dataStr, String fileName) {
		try {
			FileWriter fw = new FileWriter(fileName);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(dataStr + "\r\n");
			bw.close();
		} catch (IOException e) {
			System.out.println("File IO exc: " + e.getMessage());
		}
	}

	@Override
	public String retrieveData(String fileName) {
		String line = null;
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			line = br.readLine();
			br.close();
		} catch (IOException e) {
			System.out.println("File IO exc: " + e.getMessage());
		}
		return line;
	}
	
		// GETTERS for 'constants' (temp fix)

	@Override
	public String getDAYSTART() {
		return DAYSTART;
	}

	@Override
	public String getDAYEND() {
		return DAYEND;
	}

	@Override
	public double getTEMPDIFF() {
		return TEMPDIFF;
	}

	@Override
	public String getCONFIRMCMD() {
		return CONFIRMCMD;
	}

	@Override
	public String getSECRET() {
		return SECRET;
	}

	@Override
	public String getHOST() {
		return HOST;
	}

	@Override
	public int getPORT() {
		return PORT;
	}

	@Override
	public int getPAUZEMILLIS() {
		return PAUZEMILLIS;
	}

	@Override
	public String getGUIFILENAME() {
		return GUIFILENAME;
	}

	@Override
	public String getGUIFILENAMENIGHT() {
		return GUIFILENAMENIGHT;
	}

	@Override
	public String getAPPFILENAME() {
		return APPFILENAME;
	}

}
