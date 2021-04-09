var flat_model = require("../models/flat_model");
var express = require("express");
var router = express.Router();

router.get("/:id?/:secretaryPhoneNumber?", function(req, res, next) {
  if (req.params.id) {
    flat_model.getflatById(req.params.id,req.params.secretaryPhoneNumber,function(err, rows) {
      if (err) {
        res.json(err);
      } else {
        res.json(rows);
      }
    });
  } else {
    flat_model.getAllflat(function(err, rows) {
      if (err) {
        res.json(err);
      } else {
        res.json(rows);
      }
    });
  }
});
router.delete("/:flatNumber/:id/:secretaryPhoneNumber", function(req, res, next) {
    flat_model.deleteflat(req.params.flatNumber,req.params.id,req.params.secretaryPhoneNumber,function(err, rows) {
    if (err) {
      res.json(err);
    } else {
      res.json(rows);
    }
  });
});

router.put("/:flatNumber/:id/:secretaryPhoneNumber", function(req, res, next) {
    flat_model.updateflatById(req.params.flatNumber,req.params.id,req.params.secretaryPhoneNumber,req.body, function(err, rows) {
    if (err) {
      res.json(err);
    } else {
      res.json(rows);
    }
  });
});
router.post("/", function(req, res, next) {
    flat_model.addflat(req.body, function(err, rows) {
    if (err) {
      res.json(err);
    } else {
      res.json(rows);
    }
  });
});

module.exports = router;
