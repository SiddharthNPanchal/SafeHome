var userblockevery_model = require("../models/userblockeveryday_model");
var express = require("express");
var router = express.Router();

router.get("/:blockNumber/:flatName/:secretaryPhoneNumber", function(req, res, next) { 
    userblockevery_model.getUserblockeveryday(req.params.blockNumber,req.params.flatName,req.params.secretaryPhoneNumber,function(err, rows) {
      if (err) {
        res.json(err);
      } else {
        res.json(rows);
      }
    });
  
});


router.post('/',function(req,res,next)
{
    userblockevery_model.insertuserblockeveryday(req.body,function(err,rows)
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


router.delete("/:blockNumber/:flatName/:secretaryPhoneNumber/:everydayPhoneNumber", function(req, res, next) {
  userblockevery_model.deleteuserblockeveryday(req.params.blockNumber,req.params.flatName,req.params.secretaryPhoneNumber,req.params.everydayPhoneNumber, function(err, rows) {
  if (err) {
    res.json(err);
  } else {
    res.json(rows);
  }
});
}
);



module.exports = router;