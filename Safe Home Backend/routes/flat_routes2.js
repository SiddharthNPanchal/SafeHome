var blocks_model = require("../models/blocks_model");
var express = require("express");
var router = express.Router();

router.get("/:secretaryPhoneNumber",function(req,res,next){
    blocks_model.getBlockBySecretaryPhoneNumber(req.params.secretaryPhoneNumber,function(err, rows) {
      if (err) {
        res.json(err);
      } else {
        res.json(rows);
      }
    });
  });

  module.exports = router;
