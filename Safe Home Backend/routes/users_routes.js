var users_model = require("../models/users_model");
var express = require("express");
var router = express.Router();


router.get("/:username/:userType", function(req, res, next) {
  users_model.loginUsingOTP(req.params.username,req.params.userType,function(err, rows) {
  if (err) {
    res.json(err);
  } else {
    res.json(rows);
  }
});
});


router.get("/:username/:password/:userType", function(req, res, next) {
  users_model.login(req.params.username,req.params.password,req.params.userType,function(err, rows) {
  if (err) {
    res.json(err);
  } else {
    res.json(rows);
  }
});
});

router.get("/:id?", function(req, res, next) {
  if (req.params.id) {
    users_model.getUsersById(req.params.id,function(err, rows) {
      if (err) {
        res.json(err);
      } else {
        res.json(rows);
      }
    });
  } else {
    users_model.getAllUsers(function(err, rows) {
      if (err) {
        res.json(err);
      } else {
        res.json(rows);
      }
    });
  }
});
router.delete("/:id", function(req, res, next) {
    users_model.deleteUsers(req.params.id, function(err, rows) {
    if (err) {
      res.json(err);
    } else {
      res.json(rows);
    }
  });
});

router.put("/:id", function(req, res, next) {
    users_model.updateUsersById(req.params.id,req.body, function(err, rows) {
    if (err) {
      res.json(err);
    } else {
      res.json(rows);
    }
  });
});
router.post("/", function(req, res, next) {
    users_model.addUsers(req.body, function(err, rows) {
    if (err) {
      res.json(err);
    } else {
      res.json(rows);
    }
  });
});



module.exports = router;
