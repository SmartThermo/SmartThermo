package com.tsecourse.smartcvapp.services;

import java.io.IOException;
import java.net.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SocketConnectorServiceBean implements SocketConnectorService {

	@Autowired
	private AppMemService am;
	
	private Socket socket = null;
	private boolean connectOK = false;

	@Override
	public boolean isConnected() {
		return connectOK;
	}

	@Override
	public Socket getSocket() {
		return socket;
	}

	@Override
	public boolean connect() {
		
		System.out.println("SocketConnectorService.connect.AppMemService: " + am);

		socket = null;		
		try {
			InetAddress address = InetAddress.getByName(am.getHOST());
			try {
				socket = new Socket(address, am.getPORT());
				return connectOK=true;
			} catch (IOException e) {
				System.out.println("Can not connect to CV socket, err: " + e);
				return connectOK=false;
			}

		} catch (UnknownHostException e) {
			System.out.println(e);
			return connectOK=false;
		}	
	}

	@Override
	public void disconnect() {
		try {
			socket.close();
			System.out.println("socket disconnect");
		} catch (Exception e) {
			System.out.println("Error socket disconnect: " + e);
		}
	}

}
