var app = require('express')();
var mqtt = require('mqtt');
var http = require('http').Server(app);
var client = mqtt.connect('mqtt://54.191.51.27');
var io = require('socket.io')(http);
var isConnect = client.on('connect',function(){
	client.subscribe('devices_Tx$');

});
var Gpio = require('onoff').Gpio,
	led17 = new Gpio(17, 'out'),
	led18 = new Gpio(18, 'out'),
	led19 = new Gpio(19, 'out'),
	led20 = new Gpio(20, 'out');

app.get('/', function(req,res){
	res.sendFile(__dirname + '/index.html');
});

function toaster(isOn){
	console.log(isOn);
	led18.writeSync(isOn);
}

client.on('message',function(topic, message, packet){
	console.log("message:" + message);
	console.log("topic:" + topic);
	console.log("packet:" + JSON.stringify(packet));
	if (message == "toaster on"){
		console.log("turn on Toaster");
		toaster(1);
	}else if (message == "toaster off"){
		console.log("turn off Toaster");
		toaster(0);
	}


});


io.on('connection', function(socket){

	console.log('a user connected');
	socket.on('disconnect',function(){
		console.log('user disconnected');	
	
	});
	socket.on('chat message', function(msg){
		console.log('message' + msg);
		if (msg.toString() == "alarm on"){
			led17.writeSync(1);
			
		}else if(msg.toString() == "alarm off"){
			led17.writeSync(0);
		}else if(msg === "toaster on"){
			led18.writeSync(1);
		}else if(msg === "toaster off"){
			led18.writeSync(0);
		}else if(msg === "coffee on"){
			led19.writeSync(1);
		}else if(msg === "coffee off"){
			led19.writeSync(0);
		}else if(msg === "water low"){
			led20.writeSync(1);
		}else if(msg === "water high"){
			led20.writeSync(0);
		}else if (msg === "LED off"){
			led17.writeSync(0);
			led18.writeSync(0);
			led19.writeSync(0);
			led20.writeSync(0);

		}

	});
});

http.listen(3000, function(){
	console.log('listening on *: 3000');

});
