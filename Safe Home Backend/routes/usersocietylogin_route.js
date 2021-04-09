var userlogin=require('../models/usersocietylogin_model');
var express=require('express');
var router=express.Router();

router.post("/", function(req, res, next) {
    userlogin.addusersociety(req.body, function(err, rows) {
    if (err) {
      res.json(err);
    } else {
      res.json(rows);
    }
  });
});


router.delete("/:secretaryPhoneNumber/:userPhoneNumber", function(req, res, next) {
    userlogin.deleteusersociety(req.params.secretaryPhoneNumber,req.params.userPhoneNumber, function(err, rows) {
    if (err) {
      res.json(err);
    } else {
      res.json(rows);
    }
  });
});

module.exports=router;  
