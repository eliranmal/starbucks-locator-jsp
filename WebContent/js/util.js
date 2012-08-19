var Starbucks = {
	
	constants : {
		
		REQ_PARAM_NAME_COMMAND : false,
		REQ_PARAM_NAME_LAT : false,
		REQ_PARAM_NAME_LNG : false,
		REQ_PARAM_VALUE_LOCATE : false
	},
	
	variables : {
		placeLocation : false
	},
	
	init : {

		placesAutocomplete : function() {
			var defaultBounds = new google.maps.LatLngBounds(new google.maps.LatLng(-33.8902, 151.1759), new google.maps.LatLng(-33.8474, 151.2631));

			var input = document.getElementById('addr-input');
			var options = {
				bounds : defaultBounds,
				types : ['establishment']
			};
			autocomplete = new google.maps.places.Autocomplete(input, options);
			
			google.maps.event.addListener(autocomplete, 'place_changed', Starbucks.handlers.placeChangeListener);

		},
		
		googleMap : function() {
			var mapOptions = {
				center : new google.maps.LatLng(-34.397, 150.644),
				zoom : 8,
				mapTypeId : google.maps.MapTypeId.TERRAIN
			};
			var map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
		},
		
		attachListeners : function() {
			var addrInput = document.getElementById('addr-input');
			var addrSubmit = document.getElementById('addr-submit');
			addrSubmit.addEventListener('click', Starbucks.handlers.clickListener);
			addrInput.addEventListener('keydown', Starbucks.handlers.enterKeyListener);
		}
	},

	handlers : {
		
		enterKeyListener : function(e) {
			// 'enter' key code
			e.which === 13 && Starbucks.callbacks.actionCallbackWrapper();
		},
		
		clickListener : function(e) {
			// e.button === 0 && Starbucks.callbacks.requestLocations();
			Starbucks.callbacks.actionCallbackWrapper();
		},
		
		placeChangeListener : function() {
			
			var geo = autocomplete.getPlace().geometry;
			geo && (Starbucks.variables.placeLocation = geo.location);
		}
	},
	
	callbacks : {
		
		actionCallbackWrapper : function() {

			var pl = Starbucks.variables.placeLocation;
			if (pl) {
				var lat = pl.lat();
				var lng = pl.lng();
				Starbucks.callbacks.requestLocations(lat, lng);
			}
			
			// TODO clear input
		},
		
		requestLocations : function(lat, lng) {
			
			var url = 'AsyncService?';
			url += Starbucks.constants.REQ_PARAM_NAME_COMMAND;
			url += '=';
			url += Starbucks.constants.REQ_PARAM_VALUE_LOCATE;
			url += '&';
			url += Starbucks.constants.REQ_PARAM_NAME_LAT;
			url += '=';
			url += lat;
			url += '&';
			url += Starbucks.constants.REQ_PARAM_NAME_LNG;
			url += '=';
			url += lng;

			var sidebar = document.getElementById('sidebar');
			Starbucks.ajax.loadContent(url, sidebar);
		}
	},
	
	ajax : {
		
		loadContent : function(url, targetEl) {

			var xhr = Starbucks.ajax.getXHRObject();

			Starbucks.ajax.executeXHRCall(url, xhr, function() {
				var data = xhr.responseText;
				var jsonData = JSON.parse(data);
				var htmlData = Starbucks.parse.jsonToHtmlList(jsonData);
				// targetEl.innerHTML = htmlData;
				targetEl.innerHTML = '';
				targetEl.appendChild(htmlData);
			});
		},
		
		executeXHRCall : function(url, xhr, onSuccessCallback) {
					
			url += '&sid=' + Math.random();

			xhr.onreadystatechange = function() {
				if (xhr.readyState == 4) {
					onSuccessCallback();
				}
			};
			xhr.open('GET', url, true);
			xhr.send(null);
		},
		
		getXHRObject : function() {

			var xhr;
			if (window.XMLHttpRequest) {
				xhr = new XMLHttpRequest();
				if (xhr.overrideMimeType) {
					xhr.overrideMimeType('text/xml');
				}
				return xhr;
			} else if (window.ActiveXObject) {
				try {
					return new ActiveXObject('Msxml2.xhr');
				} catch (e) {
					try {
						return new ActiveXObject('Microsoft.xhr');
					} catch (e) {
					}
				}
			}
			if (!xhr) {
				alert('Your browser does not support AJAX!');
				return false;
			}
		}

	},
	
	parse : {
		
		jsonToHtmlList : function(data) {
			
			var ulEl = document.createElement('UL');
			ulEl.setAttribute('class', 'locations-list');
			for (var n in data) {
				var loc = data[n];
				var city = loc['City'];
				var address = loc['Address'];
				
				var liElTitle = document.createElement('LI');
				var liElContent = document.createElement('LI');
				
				liElTitle.setAttribute('class', 'll-title');
				liElContent.setAttribute('class', 'll-content');
				
				liElTitle.innerText = city;
				liElContent.innerText = address;
				
				ulEl.appendChild(liElTitle);
				ulEl.appendChild(liElContent);
			}
			
			return ulEl;
		}

	}
	
};
