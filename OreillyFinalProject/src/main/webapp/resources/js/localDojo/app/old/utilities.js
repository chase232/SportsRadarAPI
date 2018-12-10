// ---------------------------------------------------------
// file utilities.js
// ---------------------------------------------------------

define('local/app/utilities', function () {
	
	const module = {};
	
	module.generateId = function () {
		return Math.floor((Math.random() * 1000) + 1);
	}
	
	return module;
});