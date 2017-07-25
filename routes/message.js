var express = require('express');
var router = express.Router();
var Msg = require('../models/message');


router.get('/send=:id_1&rec=:id_2', function(req, res, next) {
	var id_1= req.params.id_1;
	var id_2= req.params.id_2;
	Msg.getMsgUsers(id_1,id_2,function(err,msgs){
		if (err) throw err;
		else{
			res.status(200);
			res.json(msgs);
		}
	});

});
router.post('/send',function(req,res,next){
	var message= req.body.msg;
	var id_1=req.body.Idsender;
	var id_2= req.body.Idreceiver;
	var newMsg=new Msg({
		msg:message,
		Idsender:id_1,
		Idreceiver: id_2,
		date:Date.now()
		
	});
	Msg.SendMessage(newMsg,function(err,msg){
    	if (err)
            throw err;
        else{
	        console.log(msg);
	        res.status(201);
	        res.json(msg);
    	}
	});
});

module.exports = router;