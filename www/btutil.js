var exec = require('cordova/exec');

function BTUtil() {
	console.log("btutil.js: is created");
}

BTUtil.prototype.scan = function (callback, fallback, options) {
	var service = "BTUtilPlugin";
	var action = "scan";
	var argArray = [options];

	function win(winParam) {
		if (typeof callback === "function") {
			callback(winParam);
		}
	}

	function fail(error) {
		if (typeof fallback === "function") {
			fallback(error);
		}
	}
	
	exec(win, fail, service, action, argArray);
};

module.exports = new BTUtil();