var express = require('express');
var router = express.Router();



var Coffee = function(deviceID) {
		 this.deviceName = "Coffee";
		 this.deviceID= deviceID;
		 this.getWaterLevel = function(){
			 switch(Math.floor(Math.random()*3)){
				case 0:
					return "Empty";
				case 1:
					return "Half Full";
				case 2:
					return "Full";
				}
				return "Full";
			
		}
		 this.getPowderLevel = function(){
				switch(Math.floor(Math.random()*3)){
				case 0:
					return "Empty";
				case 1:
					return "Half Full";
				case 2:
					return "Full";
				}
				return "Full";
			}
		this.getState = function(){
				
				return Math.floor(Math.random()*2)?true:false;
		}
		this.getChangeFilter = function(){
			
			return Math.floor(Math.random()*2)?true:false;
	}
};
 
var coffeeArray = [];
for (var i =0; i < 10; i++){
	
	coffeeArray[i] = new Coffee(i+21);
	//thermoArray[i].deviceID = i;
	
}
/* GET users listing. */
router.get('/getWaterLevel', function(req, res, next) {
	var deviceID = req.baseUrl.substring(1) - 21;
	console.log(deviceID);
	res.jsonp({
		deviceName: coffeeArray[deviceID].deviceName,
		devID: coffeeArray[deviceID].deviceID,
		waterLevel: coffeeArray[deviceID].getWaterLevel(),
		//powerLevel: coffeeArray[deviceID].getPowderLevel()
		});
	
});
router.get('/getPowderLevel', function(req, res, next) {
	var deviceID = req.baseUrl.substring(1) - 21;
	console.log(deviceID);
	res.jsonp({
		deviceName: coffeeArray[deviceID].deviceName,
		devID: coffeeArray[deviceID].deviceID,
		//waterLevel: coffeeArray[deviceID].getWaterLevel(),
		powderLevel: coffeeArray[deviceID].getPowderLevel()
		});
	
});
router.get('/getState', function(req, res, next) {
	var deviceID = req.baseUrl.substring(1) - 21;
	console.log(deviceID);
	res.jsonp({
		deviceName: coffeeArray[deviceID].deviceName,
		devID: coffeeArray[deviceID].deviceID,
		//waterLevel: coffeeArray[deviceID].getWaterLevel(),
		state: coffeeArray[deviceID].getState()
		});
	
});
router.get('/getChangeFilter', function(req, res, next) {
	var deviceID = req.baseUrl.substring(1) - 21;
	console.log(deviceID);
	res.jsonp({
		deviceName: coffeeArray[deviceID].deviceName,
		devID: coffeeArray[deviceID].deviceID,
		//waterLevel: coffeeArray[deviceID].getWaterLevel(),
		isChangeFilter: coffeeArray[deviceID].getChangeFilter()
		});
	
});
router.get('/getStatus', function(req, res, next) {
	var deviceID = req.baseUrl.substring(1) - 21;
	console.log(deviceID);
	res.jsonp({
		deviceName: coffeeArray[deviceID].deviceName,
		devID: coffeeArray[deviceID].deviceID,
		waterLevel: coffeeArray[deviceID].getWaterLevel(),
		isChangeFilter: coffeeArray[deviceID].getChangeFilter(),
		powderLevel: coffeeArray[deviceID].getPowderLevel(),
		state: coffeeArray[deviceID].getState()
		});
	
});
module.exports = router;
