var userflat_model = require("../models/userflat_model");
var express = require("express");
var router = express.Router();


router.get("/:flatName/:userPhoneNumber/:secretaryPhoneNumber", function(req, res, next) {
  userflat_model.getUserflatNumbers(req.params.flatName,req.params.userPhoneNumber,req.params.secretaryPhoneNumber,function(err, rows) {
  if (err) {
    res.json(err);
  } else {
    res.json(rows);
  }
});
});


router.get("/:userPhoneNumber/:secretaryPhoneNumber", function(req, res, next) {
  userflat_model.getUserbyUserPhoneNumberAndSecretaryPhoneNumber(req.params.userPhoneNumber,req.params.secretaryPhoneNumber,function(err, rows) {
  if (err) {
    res.json(err);
  } else {
    res.json(rows);
  }
});
});


router.get("/:userPhoneNumber", function(req, res, next) {
  userflat_model.getuserflatOfParticularUser(req.params.userPhoneNumber,function(err, rows) {
  if (err) {
    res.json(err);
  } else {
    res.json(rows);
  }
});
});









module.exports = router;
