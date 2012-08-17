
function showInnerPage(obj) {
	executeDocumentUpdate(obj.id, [], [], 'GET', function() {
		var doc = xmlhttp.responseText;
		document.getElementById('innerPage').innerHTML = doc;
	});
	reportsMenuItemSelect(obj);
}

function executeDocumentUpdate(cmdValue, urlParamsArray, urlValuesArray,
		sendMethod, onReadyFunction) {
	var xmlhttp = GetXmlHttpObject();
	var url = 'http://localhost:8080/MBankWeb/Ajax';
	url += '?retriveCmd=' + cmdValue;
	for (i = 0; i < urlParamsArray.length; i++) {
		url += '&' + urlParamsArray[i] + '=' + urlValuesArray[i];
	}
	url += '&sid=' + Math.random();
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4) {
			onReadyFunction();
		}
	};
	xmlhttp.open(sendMethod, url, true);
	xmlhttp.send(null);
}

function GetXmlHttpObject() {
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
		if (xmlhttp.overrideMimeType) {
			xmlhttp.overrideMimeType('text/xml');
		}
		return xmlhttp;
	} else if (window.ActiveXObject) {
		try {
			return new ActiveXObject('Msxml2.XMLHTTP');
		} catch (e) {
			try {
				return new ActiveXObject('Microsoft.XMLHTTP');
			} catch (e) {
			}
		}
	}
	if (!xmlhttp) {
		alert('Your browser does not support AJAX!');
		return false;
	}
}