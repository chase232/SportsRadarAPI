// ---------------------------------------------------------
// file internsApp.js
// ---------------------------------------------------------

define([ 'dojo/ready',
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
function (ready, query, dom, domReady, domConstruct, NodeListManipulate, domProp, domAttr, on, win, Intern) {
	
    const internsApp = {
        interns: [],
        nameRegEx: /^[a-zA-Z]+$/,
        sortDirection: "ascending",
        addIntern: function (name) {
			return new Promise((resolve, reject) => {
				var intern = internsApp.interns.find(item => item.name == name);
				
				// Validate the construction of the name (only characters no spaces)
				let valid = this.nameRegEx.test(name);
				
				if (!valid) {
					reject("Name must be only characters, no spaces");
				}
				
				// Check if another intern exists with this name. If so, reject. 
				// Otherwise, create a new one.
				if (intern) {
					reject("Sorry, ID "+name+" is already in use. Please enter another name and try again.");
				} else {
					intern = new Intern(name);
				}
				
	        	this.interns.push(intern);
	        	this.buildTable();
	        	resolve(intern.userId);
			});
        },
        
        buildTable: function () {
        	let table = query("#interntable2");
        	
        	// Empty the table
			let tableBody2 = document.getElementById("table-body2");
			
			if (tableBody2)
				domConstruct.empty(tableBody2);
			
        	// Sort the interns
        	this.sortInternsByName(this.sortDirection == "descending");
        	
        	// Loop through the interns and add one row at a time
			for (let i=0; i < this.interns.length; i++) {
				this.addTableRow(this.interns[i]);
			}
        },
        
        addTableRow: function (intern) {
        	if (intern) {
        		let userId = intern.userId;
        		let name = intern.name;
        		let html = "";
        		let tableBody = query("#interntable2 tbody")[0];
        		html += "<td>"+userId+"</td>"; 
        		html += "<td>"+name+"</td>"; 
        		html += "<td><a href='#' data-id='"+userId+"' class='delete-intern hyperlink'>Remove</a></td>";        		
        		domConstruct.create("tr", { innerHTML: html }, tableBody, "last");
        	}
        },
        
        findInternIndexById: function (inInternId) {
			return new Promise((resolve, reject) => {
				let index = -1;
				
				for (var i=0; i < this.interns.length; i++) {
					let currentIntern = this.interns[i];
					
					if (currentIntern.userId == inInternId) {
						index = i;
						
						if (document.getElementById("labTwoTextarea")) {
							document.getElementById("labTwoTextarea").value += (i + "\n");
						}
						
						break;
					}
				}
				
				if (index === -1) {
					reject("Index not found for Intern ID = " + inInternId);
				} else {
					resolve(index);
				}
			});
        },
        
        findInternIndexByName: function (inInternName) {
			return new Promise((resolve, reject) => {
				let index = -1;
				
				for (var i=0; i < this.interns.length; i++) {
					let currentIntern = this.interns[i];
					
					if (currentIntern.name == inInternName) {
						index = i;
						let result = (i + ", " + currentIntern.userId + "\n");
						
						if (document.getElementById("labTwoTextarea")) {
							document.getElementById("labTwoTextarea").value += result;
						}
						
						break;
					}
				}
				
				if (index === -1) {
					reject("Index not found for Intern Name = " + inInternName);
				} else {
					resolve(index);
				}
			});
        },
        
        removeIntern: function (inInternIdOrName) {
			return new Promise((resolve, reject) => {
				let name = null;
				let found = false;
				let isInteger = Number.isInteger(+inInternIdOrName);
				
				for (var i = 0; i < this.interns.length; i++) {
					let currentIntern = this.interns[i];
				    
					if ((isInteger && currentIntern.userId == inInternIdOrName)
							|| (!isInteger && currentIntern.name == inInternIdOrName) ) {
				    	found = true;
				    	name = currentIntern.name;
				    	internsApp.interns.splice(i, 1);
				        break;							
					}
				}
			    
				if (found) {
					resolve("Intern with ID '"+inInternIdOrName+"' and name '"+name+"' Deleted Successfully");	
				} else {
					reject("Sorry, unable to find an intern with ID = "+inInternIdOrName+".");
				}
			});
        },
        
        sortInternsByName: function (reverse) {
			return new Promise((resolve, reject) => {
				if (reverse == true) {
					this.interns.sort(function(a,b) {return (a.name < b.name) ? 1 : ((b.name < a.name) ? -1 : 0);});
				} else {
					this.interns.sort(function(a,b) {return (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0);});
				}
				
				if (reverse == true) {
					resolve("Interns Sorted In Descending Order Successfully")
				} else {
					resolve("Interns Sorted In Ascending Order Successfully");
				}
			});
        },
        
        addProgramToIntern: function (inInternId, inProgram) {
			return new Promise((resolve, reject) => {
			    // Find the intern with ID = internId
				let intern = this.interns.find(item => item.userId == inInternId);
			    
			    // If the intern exists, we can add the program (if it doesn't already exist)
			    if (intern) {
			    	let programsCompleted = intern.programsCompleted;
			    	let addProgram = false;
			    	
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
				let intern = this.interns.find(item => item.userId == inInternId);
			    
			    // If the intern exists, we can add the program (if it doesn't already exist)
			    if (intern) {
			    	let programsCompleted = intern.programsCompleted;
			    	let removeProgram = false;
			    	
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
		    				throw "Sorry, you cannot remove that Program. The Intern has not yet " + 
	    					"completed the program '"+inProgram+"'.";
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
        
        displayInterns: function (printToConsole) {
			return new Promise((resolve, reject) => {
				for (let i = 0; i < this.interns.length; i++) {
					let buffer = "";
					let currentIntern = this.interns[i];
					buffer += (i == 0) ? "" : "\n";
					buffer += "Intern {id: ";
					buffer += currentIntern.userId;
					buffer += ", name: \"" + currentIntern.name + "\"";
					buffer += ", programsCompleted: Set("+currentIntern.programsCompleted.size+")";
					
					if (currentIntern.programsCompleted.size > 0) {
						buffer += " [";
						let programBuffer = "";
						
						for (let program of currentIntern.programsCompleted) {
							programBuffer += ((programBuffer.length == 0) ? ("'" + program + "'") : 
								(", '" + program + "'"));
						}
						
						buffer += programBuffer;
						buffer += "]";
					}
					
					buffer += "}";
					
					if (printToConsole == true) {
						console.log(buffer);
					}
					
					if (document.getElementById("labTwoTextarea")) {
						document.getElementById("labTwoTextarea").value += (buffer);
					}
					
					resolve("Interns Displayed Successfully");
				}
			});
        },
        
        purgeAllInterns: function () {
			return new Promise((resolve, reject) => {
				this.interns = [];
	        	resolve("Purge Successful");
			});
        },
        
        addUsers: function (users) {
			return new Promise((resolve, reject) => {
				users.sort();
				
				for (var i = 0; i < users.length; i++) {
	        		this.addIntern(users[i]);
	        	}
				
				resolve("Users Added Successfully");
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