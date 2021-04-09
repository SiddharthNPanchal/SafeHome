var everydayinout = require("../models/everydayinout_model");
var express = require("express");
var router = express.Router();

router.get("/:secretaryPhoneNumber?", function(req, res, next) {
    everydayinout.getAllInEntriesofSociety(req.params.secretaryPhoneNumber, function(err, rows) {
        if (err) {
            res.json(err);
        } else {
            res.json(rows);
        }
    });

});

module.exports = router;