var everydayinout = require("../models/everydayinout_model");
var express = require("express");
var router = express.Router();

router.get("/:secretaryPhoneNumber?", function(req, res, next) {
    everydayinout.getAllOutEntriesofSociety(req.params.secretaryPhoneNumber, function(err, rows) {
        if (err) {
            res.json(err);
        } else {
            res.json(rows);
        }
    });

});

router.get("/:secretaryPhoneNumber/:categoryid", function(req, res, next) {
    everydayinout.getAllInEntriesofSocietyforEveryday(req.params.secretaryPhoneNumber, req.params.categoryid, function(err, rows) {
        if (err) {
            res.json(err);
        } else {
            res.json(rows);
        }
    });

});

module.exports = router;