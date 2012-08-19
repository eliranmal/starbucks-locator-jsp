(function(w, d) {

	w.onload = function() {
		
		// google maps API
		Starbucks.init.placesAutocomplete();
		Starbucks.init.googleMap();
		
		// event listeners
		Starbucks.init.attachListeners();
		
	};

})(window, document);
