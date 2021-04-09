var usertype_model = require("../models/usertype_model");
var express = require("express");
var router = express.Router();

router.get("/:id?", function(req, res, next) {
  if (req.params.id) {
   usertype_model.getUserTypeById(req.params.id,function(err, rows) {
      if (err) {
        res.json(err);
      } else {
        res.json(rows);
      }
    });
  } else {
    usertype_model.getAllUserType(function(err, rows) {
      if (err) {
        res.json(err);
      } else {
        res.json(rows);
      }
    });
  }
});

router.delete("/:id", function(req, res, next) {
  usertype_model.deleteUserType(req.params.id, function(err, rows) {
    if (err) {
      res.json(err);
    } else {
      res.json(rows);
    }
  });
});

router.put("/:id", function(req, res, next) {
  usertype_model.updateUserTypeById(req.params.id,req.body, function(err, rows) {
    if (err) {
      res.json(err);
    } else {
      res.json(rows);
    }
  });
});
router.post("/", function(req, res, next) {
  usertype_model.addUserType(req.body, function(err, rows) {
    if (err) {
      res.json(err);
    } else {
      res.json(rows);
    }
  });
});

module.exports = router;
