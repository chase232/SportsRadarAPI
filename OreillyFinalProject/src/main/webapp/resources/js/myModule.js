
// AMD EXAMPLE
require([], function () {
	console.log('AMD Example!');
});

// VANILLA EXAMPLE
// module.js
/*export function myModule() {
    this.hello = function() {
        return 'hello!';
    }
    this.goodbye = function() {
        return 'goodbye!';
    }
}*/


// COMMONJS EXAMPLE
/*function myModule() {
    this.hello = function() {
        return 'hello!';
    }
    this.goodbye = function() {
        return 'goodbye!';
    }
}
module.exports = myModule;*/