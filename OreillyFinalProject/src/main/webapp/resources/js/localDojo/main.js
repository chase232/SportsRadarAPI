// ---------------------------------------------------------
// file main.js
// ---------------------------------------------------------

/*require([
	'local/app/internsApp'
], function (internsApp) {
    let johnId,
        davidId,
        ivanId;

    //console.log("Executing JavaScript Week 2 Lab 2 Intern Script...");
    		
    internsApp.addIntern('john')
       .then((userId) => {	   
           johnId = userId;
           return internsApp.addIntern('david');
       })
       .then((userId) => {
           davidId = userId;
           return internsApp.addIntern('ivan');
       })
       .then((userId) => {
           ivanId = userId;
           internsApp.addIntern('nick');
       })
       .then(() => internsApp.addIntern('zach'))
       
       .then(() => {
    	   //console.log(internsApp.findInternIndexById(johnId));
           //console.log(internsApp.findInternIndexByName('david'), davidId);
    	   internsApp.findInternIndexById(johnId);
           internsApp.findInternIndexByName('david');
           return internsApp.removeIntern(ivanId);
        })
       .then(() => internsApp.removeIntern('nick'))
       .then(() => {
            internsApp.sortInternsByName();
            internsApp.sortInternsByName(true);
            internsApp.addProgramToIntern(davidId, 'javascript');
            internsApp.removeProgramFromIntern(davidId, 'javascript');
            internsApp.addProgramToIntern(johnId, 'java');
            internsApp.displayInterns();
       })
       .catch((error) => {
           console.log(error);
       });
});*/
