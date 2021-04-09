var society_model = require("../models/society_model");
var express = require("express");
var router = express.Router();

router.get("/:id?", function(req, res, next) {
  if (req.params.id) {
    society_model.getSocietyById(req.params.id,function(err, rows) {
      if (err) {
        res.json(err);
      } else {
        res.json(rows);
      }
    });
  } else {
    society_model.getAllSociety(function(err, rows) {
      if (err) {
        res.json(err);
      } else {
        res.json(rows);
      }
    });
  }
});
router.delete("/:id", function(req, res, next) {
    society_model.deleteSociety(req.params.id, function(err, rows) {
    if (err) {
      res.json(err);
    } else {
      res.json(rows);
    }
  });
});

router.put("/:id", function(req, res, next) {
    society_model.updateSocietyById(req.params.id,req.body, function(err, rows) {
    if (err) {
      res.json(err);
    } else {
      res.json(rows);
    }
  });
});
router.post("/", function(req, res, next) {
    society_model.addSociety(req.body, function(err, rows) {
    if (err) {
      res.json(err);
    } else {
      res.json(rows);
    }
  });
});

module.exports = router;
