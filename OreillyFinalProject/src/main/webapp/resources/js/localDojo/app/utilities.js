// ---------------------------------------------------------
// file utilities.js
// ---------------------------------------------------------

define(['dijit/registry'
       ], 
function (registry) {
			
	let myam = registry.byId('myam');
	const module = {};
	
	module.generateId = function () {
		return Math.floor((Math.random() * 1000) + 1);
	}
	
	return module;	
});