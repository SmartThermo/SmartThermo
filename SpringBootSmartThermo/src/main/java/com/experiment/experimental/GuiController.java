package com.experiment.experimental;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GuiController {

	private String appVersion = "0.2";
	private String url = "http://localhost:8080/";
	private String footerStr = "</body></html>";
	private String headerStr = "<!DOCTYPE html><head><meta http-equiv='content-type' " + 
			"content='text/html; charset=UTF-8;charset=utf-8'><title>SMART CV APP</title></head><body>";

	private String scriptStr = "<script type='text/javascript'>" +
			"var intID, connErr = false, Tset = false, isBusy = false, ajaxRequest, " + 
			"ajaxDisplay, url = '" + url + "', canAjax = false;" + 
			"function ajaxInit() { canAjax = false; try { ajaxRequest = new XMLHttpRequest(); } catch (e) { " + 
			"try {ajaxRequest = new ActiveXObject(\"Msxml2.XMLHTTP\");} catch (e) { " + 
			"try {ajaxRequest = new ActiveXObject(\"Microsoft.XMLHTTP\");} " + 
			"catch (e) {return false;}}} finally { " + 
			"ajaxRequest.onreadystatechange = function() { " + 
			"if(ajaxRequest.readyState == 4 && ajaxRequest.status == 200) { isBusy = false; " + 
			"ajaxDisplay = document.getElementById('tf'); " + 
			"document.getElementById('curtempdiv').innerHTML = ajaxRequest.responseText.split('#')[0];" + 
			"if(!Tset) { Tset = true; document.getElementById('tf').value = ajaxRequest.responseText.split('#')[1]; }" + 
			"if(connErr || ajaxRequest.responseText.split('#')[0] == '-100.0') { " + 
			"document.getElementById('alertdiv').innerHTML = '<br>Kan geen verbinding maken met SmartCV .. " + 
			"<br>Bel a.u.b. het servicenummer.'; } " + 
			"} }; canAjax = true; } }" + 
			"function ajaxSend( sendType ) { if(canAjax) { isBusy = true; " + 
			"ajaxRequest.open('GET', url + sendType, true); ajaxRequest.send(null); }}" + 
			"function setTemp(tt) { ajaxInit(); ajaxSend('write/' + ajaxDisplay.value); }" + 
			"function readTemp() { if(!isBusy) { ajaxInit(); ajaxSend('read'); } }" + 
			"intID = setInterval(readTemp, 5000);" + 
			"readTemp();" + 
			"</script>";

	private String bodyStr = "" + 
			"<br><br><br>" + 
			"<center>" + 
			"<h3>SMART CV APP versie " + appVersion + "</h3>" + 
			"<input type='number' min='5.5' max='29.5' step='0.5' id='tf' value='' onclick=''></input>" + 
			"<input type='button' value='versturen' onclick='javascript:setTemp();'></input>" + 
			"<div style='width:25vw;'><div style='float:left;'>temperatuur: "+
			"</div><div style='float:right;' id='curtempdiv'></div></div>" + 
			"<div style='color:red;' id='alertdiv'></div>" + 
			"</center>";

	private String html = headerStr + bodyStr + scriptStr + footerStr; 
	
	@RequestMapping("/")
	public String controllerTest() {
		return html;
	}
	
}
