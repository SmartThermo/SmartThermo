package com.tsecourse.smartcvapp.services;

public interface AppMemService {

	public void init();

	public void storeData(String dataStr, String fileName);

	public String retrieveData(String fileName);

		// GETTERS for constants (temp fix)

	public String getDAYSTART();

	public String getDAYEND();

	public double getTEMPDIFF();

	public String getCONFIRMCMD();

	public String getSECRET();

	public String getHOST();

	public int getPORT();

	public int getPAUZEMILLIS();

	public String getGUIFILENAME();

	public String getGUIFILENAMENIGHT();

	public String getAPPFILENAME();

}
