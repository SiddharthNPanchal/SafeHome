var userflat_model = require("../models/userflat_model");
var express = require("express");
var router = express.Router();

router.get("/:blockName/:secretaryPhoneNumber", function(req, res, next) {
  userflat_model.usersView(req.params.blockName,req.params.secretaryPhoneNumber,function(err, rows) {
  if (err) {
    res.json(err);
  } else {
    res.json(rows);
  }
});
});


router.get("/:flatNumber?/:blockName?/:secretaryPhoneNumber?", function(req, res, next) {
  if (req.params.flatNumber) {
    userflat_model.getuserflatById(req.params.flatNumber,req.params.blockName,req.params.secretaryPhoneNumber,function(err, rows) {
      if (err) {
        res.json(err);
      } else {
        res.json(rows);
      }
    });
  } else {
    userflat_model.getAlluserflat(function(err, rows) {
      if (err) {
        res.json(err);
      } else {
        res.json(rows);
      }
    });
  }
});
router.delete("/:userPhoneNumber/:flatNumber/:blockName/:secretaryPhoneNumber", function(req, res, next) {
    userflat_model.deleteuserflat(req.params.userPhoneNumber,req.params.flatNumber,req.params.blockName,req.params.secretaryPhoneNumber,function(err, rows) {
    if (err) {
      res.json(err);
    } else {
      res.json(rows);
    }
  });
});

router.put("/:flatNumber/:blockName/:secretaryPhoneNumber", function(req, res, next) {
    userflat_model.updateuserflatById(req.params.flatNumber,req.params.blockName,req.params.secretaryPhoneNumber,req.body, function(err, rows) {
    if (err) {
      res.json(err);
    } else {
      res.json(rows);
    }
  });
});
router.post("/", function(req, res, next) {
    userflat_model.adduserflat(req.body, function(err, rows) {
    if (err) {
      res.json(err);
    } else {
      res.json(rows);
    }
  });
});

module.exports = router;