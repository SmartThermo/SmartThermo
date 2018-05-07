package com.tsecourse.smartcvapp.services;

public interface DispatchService {
	public void openInbox();

	public void openOutbox();

	public String receiveIn();

	public void sendOut(String msg);
}
