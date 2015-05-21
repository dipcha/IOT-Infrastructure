var express = require('express');
var router = express.Router();



var Thermostat = function(deviceID) {
		 this.deviceName = "ThermoStat";
		 this.deviceID= deviceID;
		 this.getTemp = function(){
			
			return Math.floor(Math.random()*5 + 65);
		}
		this.getState = function(){
			
			return Math.floor(Math.random()*2)?true:false;
		}
		this.temperature = 60;
};
 
var thermoArray = [];
for (var i =0; i < 10; i++){
	
	thermoArray[i] = new Thermostat(i+1);
	//thermoArray[i].deviceID = i;
	
}
/* GET users listing. */
router.get('/getTemp', function(req, res, next) {
	var deviceID = req.baseUrl.substring(1) - 1 ;

	res.jsonp({
		deviceName: thermoArray[deviceID].deviceName,
		devID: thermoArray[deviceID].deviceID,
		temperature: thermoArray[deviceID].getTemp()
		});
	
});
router.get('/getTemperature', function(req, res, next) {
	var deviceID = req.baseUrl.substring(1) - 1 ;

	res.jsonp({
		deviceName: thermoArray[deviceID].deviceName,
		devID: thermoArray[deviceID].deviceID,
		temperature: thermoArray[deviceID].temperature
		});
	
});
router.get('/getState', function(req, res, next) {
	var deviceID = req.baseUrl.substring(1) - 1 ;

	res.jsonp({
		deviceName: thermoArray[deviceID].deviceName,
		devID: thermoArray[deviceID].deviceID,
		state: thermoArray[deviceID].getState()
		});
	
});
router.get('/getStatus', function(req, res, next) {
	var deviceID = req.baseUrl.substring(1) - 1 ;

	res.jsonp({
		deviceName: thermoArray[deviceID].deviceName,
		devID: thermoArray[deviceID].deviceID,
		state: thermoArray[deviceID].getState(),
		temperature: thermoArray[deviceID].getTemp()
		});
	
});
router.post('/setName', function(req, res, next) {
	var deviceID = req.baseUrl.substring(1) - 1 ;
	thermoArray[deviceID].deviceName = req.query.name;
	res.jsonp({
		success: true
		});
	
});
router.post('/setTemp', function(req, res, next) {
	var deviceID = req.baseUrl.substring(1) - 1 ;
	thermoArray[deviceID].temperature = parseInt(req.query.temperature);
	res.jsonp({
		success: true
		});
	
});
module.exports = router;
