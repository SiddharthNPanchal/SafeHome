var category_model = require("../models/category_model");
var express = require("express");
var router = express.Router();

router.get("/:id?", function(req, res, next) {
    category_model.getAllCategory(function(err, rows) {
      if (err) {
        res.json(err);
      } else {
        res.json(rows);
      }
    });
});

module.exports = router;
