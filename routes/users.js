var express = require('express');
var router = express.Router();
var multer=require('multer');
var uploads = multer({dest: './uploads'});
var User= require('../models/user');
var passport=require('passport');
var LocalStrategy=require('passport-local').Strategy;


/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});
router.get('/find/:u', function(req, res, next) {
    User.getUserByUsername(req.params.u, function(err,user){
        if (err) throw err;
        else{
            res.status(200);
            res.json(user);
        }
    });
});

router.get('/login', function(req, res, next) {
  res.end("200");
});
router.get('/connect',function(req,res,next){
    User.getConnectedUsers(function(err,users){
        if (err) throw err;
        else{
            res.status(200);
            res.json(users);
        }

    });
});

router.post('/register',function(req,res,next){

    User.getUserByUsername(req.body.username,function(err,user){
        if (err) throw err;
        else{
            if (user){
                res.status(200);
                res.end("Existant username");
            }else{

                var name =req.body.name;
                var email = req.body.email;
                var username=req.body.username;
                var password= req.body.password;
                var password2= req.body.password2;
                var profileImageName = 'noImage.png';
                var connect='true';
                
                req.checkBody('name','Name field is required').notEmpty();
                req.checkBody('email','Email field is required').notEmpty();
                req.checkBody('email','Email is not valid').isEmail();
                req.checkBody('username','username field is required').notEmpty();
                req.checkBody('password','Password field is required').notEmpty();
                req.checkBody('password2','passwords do not match').equals(req.body.password);
                

                // check errors

                var errors= req.validationErrors();
                if (errors){
                    res.status(202);
                    res.json(errors);

                }else {
                    var newUser= new User({
                        name:name,
                        email:email,
                        username:username,
                        password:password,
                        profileimage:profileImageName,
                        connect:connect
                        
                    });

                    User.createUser(newUser,function(err,user){
                        if (err)
                            throw err;
                        console.log(user);
                        res.status(201);
                        res.json(user);

                    });
                }


            }


        }


    });

});



router.post('/login',function(req,res,next){
    var username=req.body.username;
    var password=req.body.password;
    User.getUserByUsername(username,function(err,user){
        if (err) throw err;
        else{
            if (!user){
                res.status(202);
                res.end('Invalid username');
            }else{
                User.comparePassword(password, user.password, function(err, isMatch){
                    if (isMatch){
                        User.updateUserConnect(user.username,"true",function(err,user){
                            if (err) throw err;
                            else{
                                res.status(201);
                                res.json(user);
                            }
                           
                        });
                        

                    }else{
                       
                        res.status(200);
                        res.end('Invalid Password');

                    }
                });

            }

        }
    });
                 
});


router.post('/logout', function(req,res,next){
    var username=req.body.username;
    User.updateUserConnect(username,"false",function(err,user){
        if (err) throw err;
        else{
            res.status(200);
            res.json(user);
        }
                           
    });
                        
});

module.exports = router;
