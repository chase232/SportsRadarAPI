// ---------------------------------------------------------
// file example.js
// ---------------------------------------------------------

define([ /*'dojo/ready',
	     'dojo/query', 
	     'dojo/dom',
	     'dojo/domReady!',
	     'dojo/dom-construct',
	     'dojo/NodeList-manipulate',
	     'dojo/dom-prop',
	     'dojo/dom-attr',
	     'dojo/on',
	     'dojo/_base/window'*/
	     'local/app/intern'
        ], 
function (/*ready, query, dom, domReady, domConstruct, NodeListManipulate, domProp, domAttr, on, win*/) {
	
    const internsApp = {
        interns: [],
        nameRegEx: /^[a-zA-Z]+$/,
        sortDirection: "ascending",
        
        sayHello: function () {
			return new Promise((resolve, reject) => {
				console.log('Hello World from the example module!');
	        	resolve('Good To Go!');
			});
        },
        
        sayGoodBye: function () {
			return new Promise((resolve, reject) => {
				console.log('Goodbye from the example module!');
	        	resolve('Good To Go!');
			});
        }
    
    };
    
    return internsApp;
});



/*
*//**
 * Set the selected intern into local storage so you can retrieve it later
 * @param intern
 * @returns
 *//*
function setSelectedIntern(intern) {
	localStorage.setItem("selectedIntern", intern);
}

		
*//**
 * Add User Message
 * 
 * @param type
 * @param message
 * @returns
 *//*
function addUserMessage(type, message) {
	// Add switch to manage the type of message that we need to display
	// Get an instance of the "message id" and make it visible
	// (element.classList.remove("hidden");)
	// Get an instance of the "message list" and append the message using proper
	// DOM manipulation

	
	 * switch(type) { case 'success' : const div =
	 * document.querySelector("#successMessage"); const ul =
	 * document.querySelector("#successMessageList"); const msg =
	 * document.createTextNode(message); ul.appendChild(msg);
	 * div.classList.remove("hidden"); break; }
	 
}

function enrollIntern() {
	alert("Enroll Intern Clicked!");
}

*//**
 * "Extra Credit" Can you loop through your interns in local storage and find
 * interns similar to the search text?
 *//*
function filterInterns() {
	alert("key pressed!");
	// Check if the keypress is the proper "char code"
	// If so, filter the intern list and present results
	// If no results are found with the given text, present "No Records Found"
	// (colspan="2")
	// If the text is empty, clear the filter and present all interns
}



*//**
 * Handle NAV Menu Clicks
 * @param linkName
 * @returns
 *//*
function setActiveLink(linkName) {
	// Remove the "active" class from any and all NAV list items
	// Add "active" to the list item that was clicked
	try {
		Array.prototype.forEach.call( document.querySelectorAll('.navLink'), function(el) {
			el.classList.remove("active");
		});
		
		document.getElementById(linkName).classList.add("active");	
	} catch (err) {
		console.log("Error setting active link. err=" + err);
	}
}*/


