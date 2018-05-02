
var intID, connErr = false, Tset = false, isBusy = false; 
var ajaxRequest, url = 'http://localhost:8080/', canAjax = false; 

function ajaxInit() { 
	canAjax = false; 
	try { ajaxRequest = new XMLHttpRequest(); } 
	catch (e) { 
	try { ajaxRequest = new ActiveXObject("Msxml2.XMLHTTP"); } 
	catch (e) { 
		try {
			ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP");} 
			catch (e) { return false; }
		}
	} 
	finally { 
		ajaxRequest.onreadystatechange = function() { 
			if(ajaxRequest.readyState == 4 && ajaxRequest.status == 200) { 
				isBusy = false; 
				ajaxDisplay = document.getElementById('tf'); 
				document.getElementById('curtempdiv').innerHTML = ajaxRequest.responseText.split('#')[0];
				if(!Tset) { 
					Tset = true; document.getElementById('tf').value = ajaxRequest.responseText.split('#')[1]; 
					document.getElementById('tf2').value = ajaxRequest.responseText.split('#')[2]; 
				}
				if(connErr || ajaxRequest.responseText.split('#')[0] == '-100.0') { 
					document.getElementById('alertdiv').innerHTML = '<br>Kan geen verbinding maken met SmartCV .. <br>Bel a.u.b. het servicenummer.'; 
				}
			}
		}; 
		canAjax = true; 
	}
}


function ajaxSend( sendType ) { 
	if(canAjax) { 
		isBusy = true; 
		ajaxRequest.open('GET', url + sendType, true); ajaxRequest.send(null); 
	}
}

function setTemp(tt) { 
	ajaxInit(); 
	ajaxSend('write/' + document.getElementById('tf').value + '/' + document.getElementById('tf2').value); 
}

function readTemp() { 
	if(!isBusy) { ajaxInit(); ajaxSend('read'); } 
}

intID = setInterval(readTemp, 5000);
readTemp();

