var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser')
  , http = require('http')
  , sockets = require('socket.io')
  , path = require('path');

var routes = require('./routes/index');
var users = require('./routes/users');
var thermostat = require('./routes/thermostat');
var alarm = require('./routes/alarm');
var coffee = require('./routes/coffee');
var toaster = require('./routes/toaster');
//var app = express();
var app = express()
, server = http.createServer(app)
, io = sockets.listen(server);
// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

// uncomment after placing your favicon in /public
//app.use(favicon(__dirname + '/public/favicon.ico'));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
//
//app.configure(function(){
	  app.set('port', process.env.PORT || 3000);
//	  app.set('views', __dirname + '/views');
//	  app.set('view engine', 'ejs');
//	  app.use(express.favicon());
//	  app.use(express.logger('dev'));
//	  app.use(express.bodyParser());
//	  app.use(express.methodOverride());
//	  app.use(app.router);
//	  app.use(express.static(path.join(__dirname, 'public')));
//	});
//
//	app.configure('development', function(){
//	  app.use(express.errorHandler());
//	});


app.use('/', routes);
app.use('/users', users);
app.use(/\/10|\/[1-9]/,thermostat);
app.use(/\/20|\/1[1-9]/,alarm);
app.use(/\/30|\/2[1-9]/,coffee);
app.use(/\/40|\/3[1-9]/,toaster);
// catch 404 and forward to error handler
app.use(function(req, res, next) {
    var err = new Error('Not Found');
    err.status = 404;
    next(err);
});

// error handlers

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
    app.use(function(err, req, res, next) {
        res.status(err.status || 500);
        res.render('error', {
            message: err.message,
            error: err
        });
    });
}

// production error handler
// no stacktraces leaked to user
app.use(function(err, req, res, next) {
    res.status(err.status || 500);
    res.render('error', {
        message: err.message,
        error: {}
    });
});
var server = app.listen(app.get('port'), function () {

	  var host = server.address().address;
	  var port = server.address().port;

	  console.log('Example app listening at http://%s:%s', host, port);

	});
io.configure(function () { 
	  io.set("transports", ["xhr-polling"]); 
	  io.set("polling duration", 10); 
	});

	io.sockets.on('connection', function (socket) {
	  socket.emit('news', { hello: 'world' });
	  socket.on('my other event', function (data) {
	    console.log(data);
	  });
	});

//	server.listen(app.get('port'), function(){
//	  console.log("Express server listening on port " + app.get('port'));
//	});

module.exports = app;
