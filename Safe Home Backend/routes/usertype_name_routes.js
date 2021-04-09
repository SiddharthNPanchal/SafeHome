var usertype_model = require("../models/usertype_model");
var express = require("express");
var router = express.Router();

router.get("/:name", function(req, res, next) {
    usertype_model.getUserTypeByName(req.params.name, function(err, rows) {
      if (err) {
        res.json(err);
      } else {
        res.json(rows);
      }
    });
  });

  module.exports = router;