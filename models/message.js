var mongoose=require('mongoose');
mongoose.connect('mongodb://localhost/nodeauth');
var db=mongoose.connection;
//var Date=require('date');
var ObjectId = mongoose.Schema.ObjectId;

var MessageSchema=mongoose.Schema({
	msg:{
		type:String,
		index:true
	},
	date:{ 
		type: Date, 
		default: Date.now()
	 },
	Idsender:{
		type: ObjectId
	},
	Idreceiver: {
		type:ObjectId
	}
});
var Msg=module.exports=mongoose.model('Msg',MessageSchema);

module.exports.SendMessage=function(newMsg,callback){
   	newMsg.save(callback);
}

module.exports.getMsgById=function(id,callback){
	Msg.findById(id,callback);
}
module.exports.getMsgUsers=function(id1,id2,callback){
	
	Msg.find({$or:[
			{	$and:[	{Idsender: id1},{Idreceiver: id2}	]	}	,
			{	$and:[	{Idsender: id2},{Idreceiver: id1}	]	}
		]}).sort({date: 1}).exec(callback);
	
}
