// ---------------------------------------------------------
// file internsApp.js
// ---------------------------------------------------------

define([ 'dojo',
	     'dojo/ready',
	     'dojo/query', 
	     'dojo/dom',
	     'dojo/domReady!',
	     'dojo/dom-construct',
	     'dojo/NodeList-manipulate',
	     'dojo/dom-prop',
	     'dojo/dom-attr',
	     'dojo/on',
	     'dojo/_base/window',
         'local/app/Intern'
        ], 
function (dojo, ready, query, dom, domReady, domConstruct, NodeListManipulate, domProp, domAttr, on, win, Intern) {
	
    const internsApp = {
        interns: [],
        nameRegEx: /^[a-zA-Z]+$/,
        sortDirection: "ascending",
        
        addIntern: function (name, grid) {
			return new Promise((resolve, reject) => {
				var intern = internsApp.interns.find(item => item.name == name);
				
				// Validate the construction of the name (only characters no spaces)
				var valid = this.nameRegEx.test(name);
				
				if (!valid) {
					throw "Name must be only characters, no spaces";
				}
				
				// Check if another intern exists with this name				
				if (intern) {
					reject("Sorry, ID "+name+" is already in use. Please enter another name and try again.");
				} else {
					intern = new Intern(name);
				}
				
				let internId = intern.userId;
				grid.store.put({ "id": internId , "name": name, "action": "<span data-id='"+internId+"' class='delete-intern'>Remove</span>" })
				grid.refresh();		        	
	        	resolve(intern.userId);
			});
        },
        
        removeIntern: function (inInternId, grid) {
			return new Promise((resolve, reject) => {
				var isInteger = Number.isInteger(+inInternId);
	
				if (isInteger) {
					grid.store.remove(inInternId);
					grid.refresh();
					resolve("Intern with ID '"+inInternId+"' Deleted Successfully");
				} else {
					reject("Invalid Reference ID. Please try again.");
				}
			});
        },
        
        addProgramToIntern: function (inInternId, inProgram) {
			return new Promise((resolve, reject) => {
			    // Find the intern with ID = internId
			    var intern = this.interns.find(item => item.userId == inInternId);
			    
			    // If the intern exists, we can add the program (if it doesn't already exist)
			    if (intern) {
			    	var programsCompleted = intern.programsCompleted;
			    	var addProgram = false;
			    	
			    	if (programsCompleted && programsCompleted.size > 0) {
			    		found = false;
			    		
			    		for (let program of programsCompleted) {
			    			if (program.toLowerCase() == programName.toLowerCase()) {	
			    				found = true;
			    				break;
			    			}
				    	}
			    		
		    			if (found) {
		    				reject("Sorry, you cannot add that Program. The Intern already has " + 
		    					"already completed the program '"+programName+"'.");
		    			} else {
		    				addProgram = true;
		    			}
			    	} else {
			    		addProgram = true;
			    	}
			    	
			    	if (addProgram) {
	    				// Add the Program to the SET
	    				intern.programsCompleted.add(inProgram);			    		
			    	}
			    	
			    	resolve("Program '" + inProgram + "' Added Successfully");
			    	
			    } else {
			    	reject("Internal Error. Cannot find user with Intern ID "+inInternId+". " + 
			    		"Please contact Tech Support at 867-5309");
			    }
			});
        },
        
        removeProgramFromIntern: function (inInternId, inProgram) {
			return new Promise((resolve, reject) => {
			    // Find the intern with ID = internId
			    var intern = this.interns.find(item => item.userId == inInternId);
			    
			    // If the intern exists, we can add the program (if it doesn't already exist)
			    if (intern) {
			    	var programsCompleted = intern.programsCompleted;
			    	var removeProgram = false;
			    	
			    	if (programsCompleted && programsCompleted.size > 0) {
			    		found = false;
			    		
			    		for (let program of programsCompleted) {
			    			if (program.toLowerCase() == inProgram.toLowerCase()) {	
			    				found = true;
			    				break;
			    			}
				    	}
			    		
		    			if (found) {
		    				removeProgram = true;
		    			} else {
		    				reject("Sorry, you cannot remove that Program. The Intern has not yet " + 
	    					"completed the program '"+inProgram+"'.");
		    			}
			    	} else {
			    		removeProgram = true;
			    	}
			    	
			    	if (removeProgram) {
	    				// Remove the Program from the SET
	    				intern.programsCompleted.delete(inProgram);			    		
			    	}
			    	
			    	resolve("Program '" + inProgram + "' Removed Successfully");
			    	
			    } else {
			    	reject("Internal Error. Cannot find user with Intern ID "+inInternId+". " + 
			    		"Please contact Tech Support at 867-5309");
			    }
			});
        },
        
		displaySuccess: function displaySuccess(message) {
			const icon = "<span class='glyphicon glyphicon-ok strong'></span>&nbsp;";
			let successContainer = document.getElementById('success-container');
			let errorContainer = document.getElementById('error-container');
			let successMessage = document.getElementById('success-message');
			
			if (successContainer && errorContainer && successMessage) {
				// Hide any error messages in case they are showing 
				successContainer.style.display = "none";
				errorContainer.style.display = "none";
				
				// Show the success container and populate the success message
				setTimeout(function() {
					successContainer.style.display = "inline";
					successMessage.innerHTML = (icon + message);
				}, 300);
			}
		},
		
		displayError: function displayError(message) {
			const icon = "<span class='glyphicon glyphicon-remove strong'></span>&nbsp;";
			let successContainer = document.getElementById('success-container');
			let errorContainer = document.getElementById('error-container');
			let errorMessage = document.getElementById('error-message');
			
			if (successContainer && errorContainer && errorMessage) {
				// Hide any messages in case they are showing 
				successContainer.style.display = "none";
				errorContainer.style.display = "none";
				
				// Show the error container and populate the error message
	    		setTimeout(function() {
	    			errorContainer.style.display = "inline";
	    			errorMessage.innerHTML = (icon + message);	
	    		}, 300);	
			}
		}

    };
    
    return internsApp;
});