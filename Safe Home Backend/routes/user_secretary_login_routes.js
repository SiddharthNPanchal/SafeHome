var users_model = require("../models/users_model");
var express = require("express");
var router = express.Router();

router.post("/", function(req, res, next) {
    users_model.loginPost(req.body, function(err, rows) {
    if (err) {
      res.json(err);
    } else {
      res.json(rows);
    }
  });
});

module.exports = router;