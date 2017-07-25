var mongoose=require('mongoose');
mongoose.connect('mongodb://localhost/nodeauth');
var db=mongoose.connection;
var bcrypt=require('bcrypt-nodejs');
var crypto = require('crypto'),
    algorithm = 'aes-256-ctr',
    password = 'd6F3Efeq';

function encrypt(text){
  var cipher = crypto.createCipher(algorithm,password)
  var crypted = cipher.update(text,'utf8','hex')
  crypted += cipher.final('hex');
  return crypted;
}

function decrypt(text){
  var decipher = crypto.createDecipher(algorithm,password)
  var dec = decipher.update(text,'hex','utf8')
  dec += decipher.final('utf8');
  return dec;
}

// User schema
var UserSchema=mongoose.Schema({
	username:{
		type:String,
		index:true
	},
	password:{
		type:String
	},
	email:{
		type:String
	},
	name: {
		type:String
	},
	profileimage:{
		type: String
	},
	connect:{
		type:String
	}
});
var User=module.exports=mongoose.model('User',UserSchema);

module.exports.createUser=function(newUser,callback){
   	newUser.password=encrypt(newUser.password);
   	newUser.save(callback);
}

module.exports.getUserByUsername=function(Username,callback){
	var query={username:Username};
	User.findOne(query,callback);
}
module.exports.getUserById=function(id,callback){
	User.findById(id,callback);
}
module.exports.getConnectedUsers=function(callback){
	var query={connect:"true"};
	User.find(query,callback);
}
module.exports.updateUserConnect=function(Username,Connect,callback){
	 var query= {username:Username};
	 var query2={connect:Connect};
	 User.findOneAndUpdate(query, query2, callback);
 
}

module.exports.comparePassword=function(password,hash,callback){
	/*bcrypt.compare(password,hash,function(err,isMatch){
		if (err) return callback(err);
		callback(null,isMatch);
	});*/
    if (encrypt(password)==hash){
    	callback(null,true);
    }else{
    	callback(null,false);
    }
}
