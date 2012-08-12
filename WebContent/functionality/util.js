var Starbucks = {
	constants : {},
	init : {
		placesAutocomplete : function() {
			
			var defaultBounds = new google.maps.LatLngBounds(
				new google.maps.LatLng(-33.8902, 151.1759), new google.maps.LatLng(-33.8474, 151.2631)
			);

			var input = document.getElementById('address');
			var options = {
				bounds : defaultBounds,
				types : ['establishment']
			};
			autocomplete = new google.maps.places.Autocomplete(input, options);
		},
		googleMap : function() {
			var mapOptions = {
				center : new google.maps.LatLng(-34.397, 150.644),
				zoom : 8,
				mapTypeId : google.maps.MapTypeId.ROADMAP
			};
			var map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
		}
	}
};
