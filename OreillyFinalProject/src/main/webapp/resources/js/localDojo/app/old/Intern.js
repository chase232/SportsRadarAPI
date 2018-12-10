// ---------------------------------------------------------
// file Intern.js
// ---------------------------------------------------------

define([
    'local/app/utilities'
], function (utilities) {
	
    function Intern(name) {
        this.userId = utilities.generateId();
        this.name = name;
        this.programsCompleted = new Set();
    }

    return Intern;
});