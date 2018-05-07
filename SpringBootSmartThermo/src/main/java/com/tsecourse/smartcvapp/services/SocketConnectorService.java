package com.tsecourse.smartcvapp.services;

import java.net.Socket;

public interface SocketConnectorService {

	public Socket getSocket();
	
	public boolean connect();
	
	public void disconnect();

	public boolean isConnected();
}
