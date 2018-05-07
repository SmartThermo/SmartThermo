package com.tsecourse.smartcvapp.services;

import com.tsecourse.smartcvapp.CVState;

public interface TranslatorService {

	public void processData(CVState cvState, String lastdata);
	
	public String buildMessage(String type, String appendix);
	
}
