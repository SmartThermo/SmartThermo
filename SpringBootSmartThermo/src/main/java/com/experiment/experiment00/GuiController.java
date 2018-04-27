package com.experiment.experiment00;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GuiController {

	private String appVersion = "0.1";
	private String headerStr = "<!DOCTYPE html><head><meta http-equiv='content-type' content='text/html; charset=UTF-8;charset=utf-8'><title>SMART CV APP</title></head><body>";
	private String footerStr = "</body></html>";

	private String scriptStr = "<script type='text/javascript'>" +
			"var ajaxRequest, ajaxDisplay, url = 'http://localhost:8080/', canAjax = false;" + 
			"function ajaxInit() { canAjax = false; try { ajaxRequest = new XMLHttpRequest(); } catch (e) { " + 
			"try {ajaxRequest = new ActiveXObject(\"Msxml2.XMLHTTP\");} catch (e) {try {ajaxRequest = new ActiveXObject(\"Microsoft.XMLHTTP\");} " + 
			"catch (e) {return false;}}} finally {ajaxRequest.onreadystatechange = function() { if(ajaxRequest.readyState == 4 && ajaxRequest.status == 200) {\r\n" + 
			"ajaxDisplay = document.getElementById('tf'); ajaxDisplay.value = ajaxRequest.responseText;\r\n" + 
			"} }; canAjax = true; } }" + 
			"" + 
			"function ajaxSend( sendType ) { var params = ''; if(canAjax) { ajaxRequest.open('GET', url + sendType, true); ajaxRequest.send(null); }}" + 
			"function setTemp(tt) { ajaxInit(); ajaxSend('write/' + ajaxDisplay.value); }" + 
			"function readTemp() { ajaxInit(); ajaxSend('read'); }" + 
			"readTemp();" + 
			"</script>";

	private String bodyStr = "" + 
			"<br><br><br>" + 
			"<center>" + 
			"<h3>SMART CV APP versie " + appVersion + "</h3>" + 
			"<input type='number' min='5.5' max='29.5' step='0.5' id='tf' value='' onclick=''></input>" + 
			"<!-- <input type='button' value='+' onclick='javascript:setTemp(1);'></input>" + 
			"<input type='button' value='-' onclick='javascript:setTemp(-1);'></input> &nbsp;&nbsp;&nbsp;&nbsp; -->" + 
			"<input type='button' value='versturen' onclick='javascript:setTemp();'></input>" + 
			"<div id='testdiv'></div>" + 
			"</center>";

	private String html = headerStr + bodyStr + scriptStr + footerStr; 
	
	@RequestMapping("/")
	public String controllerTest() {
		return html;
	}
	
}
