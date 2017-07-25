var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var session=require('express-session');
var passport=require('passport');
var LocalStrategy=require('passport-local').Strategy;
var multer=require('multer');
var flash=require('connect-flash');
var mongo=require('mongodb');
var expressValidator=require('express-validator');
var mongoose=require('mongoose');
var db=mongoose.connection;
var index = require('./routes/index');
var users = require('./routes/users');
var message = require('./routes/message');
var passport=require('passport');

var GoogleStrategy = require( 'passport-google-oauth2' ).Strategy;
var GOOGLE_CLIENT_ID      = "642531470417-hf8k9vbrhcis4q9u0i8pes6t75mdne8c.apps.googleusercontent.com"
  , GOOGLE_CLIENT_SECRET  = "_FShLpXzUfwwpnWl3iLV2Gym";

passport.use(new GoogleStrategy({
    clientID:     GOOGLE_CLIENT_ID,
    clientSecret: GOOGLE_CLIENT_SECRET,
    callbackURL: "http://localhost:3000/auth/google/callback",
    passReqToCallback   : true
  },
  function(request, accessToken, refreshToken, profile, done) {
    User.findOrCreate({ googleId: profile.id }, function (err, user) {
      return done(err, user);
    });
  }
));



var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

//pour les fichier
app.use(multer({dest:'./uploads/'}).single('file'));
// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));

//express session
app.use(session({
	secret:'secret',
	saveUnintialized : true,
	resave:true
}));

app.use(passport.initialize());
app.use(passport.session());

//express validator pourles erreurs
app.use(expressValidator({
errorFormatter: function(param,msg,value){
	var namespace=param.split('.')
	,root= namespace.shift()
	,formParam=root;

	while(namespace.length){
		formParam+='['+namespace.shift()+']';
	}
	return{
		param:formParam,
		msg:msg,
		value:value
	};
}
}));

app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

//envoyer des messages a la template 
app.use(flash());
app.use(function(req,res,next){
	res.locals.messages=require('express-messages')(req,res);
	next();
});

// routes... 
app.get('*',function(req,res,next){
	res.locals.user=req.user || null;
	next();
});


app.use('/', index);
app.use('/users', users);
app.use('/msg', message);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
