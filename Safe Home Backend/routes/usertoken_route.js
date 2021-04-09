var usertoken=require('../models/usertoken_model');
var express=require('express');
var router=express.Router();

router.put('/:userToken',function(req,res,next)
{
    console.log(req.body.userToken + " HELLO");
    usertoken.updateUserToken(req.params.userToken,req.body,function(err,rows)
    {
        if(err)
        {
            res.json(err);
        }
        else{
            res.json(rows);
        }
    });
}
);

module.exports=router;  