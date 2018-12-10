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
				
				if (name == '') {
					resolve(0);
				}
				
				// Validate the construction of the name (only characters no spaces)
				var valid = this.nameRegEx.test(name);
				
				if (!valid) {
					reject("Name must be only characters, no spaces");
				}
				
				// Check if another intern exists with this name
				if (intern) {
					reject("Sorry, ID "+name+" is already in use. Please enter another name and try again.");
				} else {
					intern = new Intern(name);
				}
				
				let internId = intern.userId;
				let actionColumn = this.getActionColumn(internId);					
				grid.store.put({ "id":internId , "name":name, "programs":[], "action":actionColumn });
				grid.refresh();
	        	resolve(intern.userId);
			});
        },
        
        getActionColumn: function (inInternId) {
        	var actionColumn = "";
        	actionColumn += "<span data-id='"+inInternId+"' class='delete-intern'>Remove</span> | ";
        	actionColumn += "<span data-id='"+inInternId+"' class='add-program'> +P</span>";
        	return actionColumn;
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

        addProgramToIntern: function (inInternId, inProgram, inGrade, grid) {
			return new Promise((resolve, reject) => {
				let validGrade = false;
				
				// Validate the Grade (integer inclusively between 0 and 100)
				if (inGrade && Number.isInteger(+inGrade) && inGrade >=0 && inGrade <=100) {
					validGrade = true;
				}
				
				if (!validGrade) {
					reject("Invalid grade. Please enter a grade between 0 and 100.");
				}
				
				var user = grid.store.get(inInternId);
				
				if (user) {
					let text = "";
					let programs = user.programs;
					
					if (programs && "-" === programs) {
						user.programs = [];
						programs = [];
					}
					
					programs.push({
						"programName" : inProgram,
						"grade" : inGrade
					});
					
					user.programs = programs;
					resolve("Program '" + inProgram + "' Added Successfully");
				} else {
					reject("Sorry, can't find user with ID="+ inInternId + ".");
				}
			});
        },

        removeProgramFromIntern: function (inInternId, inProgram, grid) {
			return new Promise((resolve, reject) => {					
				var newPrograms = [];
				var user = grid.store.get(inInternId);
	
				if (user) {
					let programs = user.programs;
					
					if (programs) {
						for (let program in programs) {
			    			if (program.length > 0) {
				    			let programName = programs[program].programName;
				    			
				    			if (programName != inProgram)
				    				newPrograms.push(program);
			    			}	
			    		}
						
						user.programs = newPrograms;
						grid.store.put(user);
						resolve("Program '" + inProgram + "' Deleted Successfully");
					}
				}
			});
        },
        
        removeProgramFromInternOLD: function (inInternId, inProgram) {
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