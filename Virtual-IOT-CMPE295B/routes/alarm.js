var express = require('express');
var router = express.Router();



var Alarm = function(deviceID) {
		 this.deviceName = "Timer";
		 this.deviceID= deviceID;
		 this.getTime = function(){
			
			return Math.floor(Math.random()*5 + 5);
		}
		 this.getState = function(){
				
				return Math.floor(Math.random()*2)?true:false;
			}
};
 
var alarmArray = [];
for (var i =0; i < 10; i++){
	
	alarmArray[i] = new Alarm(i+11);
	//thermoArray[i].deviceID = i;
	
}
/* GET users listing. */
router.get('/getTimer', function(req, res, next) {
	var deviceID = req.baseUrl.substring(1) - 11;

	res.jsonp({
		deviceName : alarmArray[deviceID].deviceName,
		devID: alarmArray[deviceID].deviceID,
		timeSet: alarmArray[deviceID].getTime()
		});
	
});
router.get('/getState', function(req, res, next) {
	var deviceID = req.baseUrl.substring(1) - 11;

	res.jsonp({
		deviceName : alarmArray[deviceID].deviceName,
		devID: alarmArray[deviceID].deviceID,
		state: alarmArray[deviceID].getState()
		});
	
});
router.get('/getStatus', function(req, res, next) {
	var deviceID = req.baseUrl.substring(1) - 11;

	res.jsonp({
		deviceName : alarmArray[deviceID].deviceName,
		devID: alarmArray[deviceID].deviceID,
		timeSet: alarmArray[deviceID].getTime(),
		state: alarmArray[deviceID].getState()
		});
	
});
module.exports = router;
