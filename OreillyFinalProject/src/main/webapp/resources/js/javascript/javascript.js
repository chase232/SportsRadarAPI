(function() {
	'use strict';
	var btn = document.getElementById('request');
	var output = document.getElementById('output');

	// set up a request
	var request = new XMLHttpRequest();

	// keep track of the request
	request.onreadystatechange = function() {
		// check if the response data send back to us 
		if (request.readyState === 4) {
			// add a border
			output.style.border = '1px solid #e8e8e8';
			console.log(request);
			// check if the request is successful
			if (request.status === 200) {
				// update the HTML of the element
				output.innerHTML = request.responseText;
			} else {
				// otherwise display an error message
				output.innerHTML = 'An error occurred during your request: '
						+ request.status + ' ' + request.statusText;
			}
		}
	}

	// specify the type of request
	request.open('Get', 'http://localhost:8080/interns/javascript/ajaxTest');

	// register an event
	btn.addEventListener('click', function() {
		// hide the button
		this.style.display = 'none';
		// send the request
		request.send();
	});
})();
