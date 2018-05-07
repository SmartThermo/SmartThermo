package com.tsecourse.smartcvapp.services;

import java.io.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DispatchServiceBean implements DispatchService {

	private BufferedWriter bw ;
	private BufferedReader br;

	private SocketConnectorService socketConn;
	private CVProxyService cvProxy;

	@Autowired
	public DispatchServiceBean(SocketConnectorService socketConn, CVProxyService cvProxy) {
		
		this.socketConn = socketConn;
		this.cvProxy = cvProxy;
		System.out.println("DispatchServiceBean - SocketConnectorService / CVProxyService:\n\t" + socketConn + "\n\t" + cvProxy);
	}
	
	@Override
	public void openInbox() {

		try {
			InputStream is = socketConn.getSocket().getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
		} catch (IOException e) {
			cvProxy.trySocketReconnect();
			System.out.println(" Err openInbox !!!: " + e);
		}
	}

	@Override
	public void openOutbox() {
		try {
			OutputStream os = socketConn.getSocket().getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			bw = new BufferedWriter(osw);
		} catch (IOException e) {
			cvProxy.trySocketReconnect();
			System.out.println(" Err openOutbox !!!: " + e);
		}
	}

	@Override
	public String receiveIn() {
		String message = null;

		try {
			message = br.readLine();
		} catch (IOException e) {
			cvProxy.trySocketReconnect();
			System.out.println(" Err receiveIn !!!: " + e);
		}

		System.out.println("\tCV replies: " + message);

		return message;
	}

	@Override
	public void sendOut(String msg) {
		try {
			bw.write(msg);
			bw.flush();
		} catch (IOException e) {
			cvProxy.trySocketReconnect();
			System.out.println(" Err sendOut !!!: " + e);
		}
		System.out.println("\n\tTell CV: " + msg);
	}

}
