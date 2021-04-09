var users_model = require("../models/update_password_model");
var express = require("express");
var router = express.Router();

router.get("/:userphonenumber/:userType", function(req, res, next) {
    users_model.getuserpassword(req.params.userphonenumber,req.params.userType,function(err, rows) {
    if (err) {
      res.json(err);
    } else {
      res.json(rows);
    }
  });
  });
  router.put("/:userphonenumber/:usertypeid", function(req, res, next) {
    users_model.updatepassword(req.params.userphonenumber,req.params.usertypeid,req.body, function(err, rows) {
    if (err) {
      res.json(err);
    } else {
      res.json(rows);
    }
  });
});


module.exports = router;
