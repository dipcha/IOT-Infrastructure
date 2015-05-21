var express = require('express');
var router = express.Router();



var Toaster = function(deviceID) {
		this.deviceName = "Toaster";
		 this.deviceID= deviceID;
		 this.getTimer = function(){
			
			return Math.floor(Math.random()*5 + 5);
		}
		 this.isEmpty = function(){
			 
			 return Math.floor(Math.random()*2)?true:false;
		 }
			this.getState = function(){
				
				return Math.floor(Math.random()*2)?true:false;
			}
};
 
var toasterArray = [];
for (var i =0; i < 10; i++){
	
	toasterArray[i] = new Toaster(i+31);
	//thermoArray[i].deviceID = i;
	
}
/* GET users listing. */
router.get('/getTimer', function(req, res, next) {
	var deviceID = req.baseUrl.substring(1) - 31;

	res.jsonp({
		deviceName: toasterArray[deviceID].deviceName,
		devID: toasterArray[deviceID].deviceID,
		timerSet: toasterArray[deviceID].getTimer(),
		//isEmpty : toasterArray[deviceID].isEmpty()
		});
	
});
router.get('/isEmpty', function(req, res, next) {
	var deviceID = req.baseUrl.substring(1) - 31;

	res.jsonp({
		deviceName: toasterArray[deviceID].deviceName,
		devID: toasterArray[deviceID].deviceID,
		//timerSet: toasterArray[deviceID].getTimer(),
		isEmpty : toasterArray[deviceID].isEmpty()
		});
	
});
router.get('/getState', function(req, res, next) {
	var deviceID = req.baseUrl.substring(1) - 31;

	res.jsonp({
		deviceName: toasterArray[deviceID].deviceName,
		devID: toasterArray[deviceID].deviceID,
		//timerSet: toasterArray[deviceID].getTimer(),
		state : toasterArray[deviceID].getState()
		});
	
});
router.get('/getStatus', function(req, res, next) {
	var deviceID = req.baseUrl.substring(1) - 31;

	res.jsonp({
		deviceName: toasterArray[deviceID].deviceName,
		devID: toasterArray[deviceID].deviceID,
		timerSet: toasterArray[deviceID].getTimer(),
		isEmpty : toasterArray[deviceID].isEmpty(),
		state : toasterArray[deviceID].getState()
		});
	
});
module.exports = router;
